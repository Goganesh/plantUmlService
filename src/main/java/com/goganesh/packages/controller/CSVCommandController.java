package com.goganesh.packages.controller;

import lombok.AllArgsConstructor;
import com.goganesh.packages.model.Column;
import com.goganesh.packages.model.Connection;
import com.goganesh.packages.model.dto.TemplateDto;
import org.springframework.shell.standard.*;
import com.goganesh.packages.service.CsvReaderService;
import com.goganesh.packages.service.DtoMapper;
import com.goganesh.packages.service.FileWriterService;
import com.goganesh.packages.service.PlantUmlService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ShellComponent
@AllArgsConstructor
@ShellCommandGroup(value = "erd com.goganesh.packages.model from csv")
public class CSVCommandController {

    private static final String CSV_TABLE_NAME = "template.csv";
    private static final String TEMPLATE_DIRECTORY = "classpath:templates/erd/";

    private final CsvReaderService csvReaderService;
    private final PlantUmlService plantUmlService;
    private final DtoMapper dtoMapper;
    private final FileWriterService fileWriterService;

    @ShellMethod(value = "Get CSV template for erd generation\n" +
            "\t\t\tCommand examples:\n" +
            "\t\t\tct /Users/georgijbasiladze/Desktop/src\n" +
            "\t\t\tcsv-template -T /Users/georgijbasiladze/Desktop/src\n",
            key = {"ct", "csv-template"})
    public String getCsvTemplate(
            @ShellOption(value = {"-T", "--target"},help = "path to target directory for template", arity = 1) String targetDirectory) throws IOException
    {
        fileWriterService.writeFile(TEMPLATE_DIRECTORY + CSV_TABLE_NAME, targetDirectory + File.separator + CSV_TABLE_NAME);

        return CSV_TABLE_NAME + " download to " + targetDirectory;
    }

    @ShellMethod(value = "Generate text for plantuml erd com.goganesh.packages.model from csv template\n" +
            "\t\t\tCommand examples:\n" +
            "\t\t\tcsv /Users/georgijbasiladze/Desktop/src\n" +
            "\t\t\tc -P /Users/georgijbasiladze/Desktop/src\n" +
            "\t\t\tcsv --path /Users/georgijbasiladze/Desktop/src --delimiter ,\n",
            key = {"c", "csv"})
    public String callCsvMode(
            @ShellOption(value = {"-P", "--path"}, help = "path to source template directory", arity = 1) String sourceDirectory,
            @ShellOption(value = {"-D", "--delimiter"}, help = "csv delimiter", arity = 1, defaultValue = ",") char delimiter) throws IOException
    {
        String tablesPath = sourceDirectory +File.separator + CSV_TABLE_NAME;

        List<TemplateDto> templateDtos = csvReaderService.readCsv(tablesPath, delimiter, TemplateDto.class);

        List<Column> columns = dtoMapper.getColumnsByDtos(templateDtos);
        List<Connection> connections = dtoMapper.getConnectionsByDto(templateDtos, columns);

        String plantUmlText = plantUmlService.generateErdPlantUml(columns, connections);

        Files.writeString(Path.of(sourceDirectory + File.separator + "plant_uml.txt"), plantUmlText);

        return plantUmlText;
    }
}

package com.goganesh.packages.controller;

import lombok.AllArgsConstructor;
import com.goganesh.packages.model.Column;
import com.goganesh.packages.model.Connection;
import com.goganesh.packages.model.dto.TemplateDto;
import org.springframework.shell.standard.*;
import com.goganesh.packages.service.CsvFileReaderService;
import com.goganesh.packages.service.DtoMapper;
import com.goganesh.packages.service.FileWriterService;
import com.goganesh.packages.service.PlantUmlService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ShellComponent
@AllArgsConstructor
@ShellCommandGroup(value = "Генерация скрипта ERD PlantUml по шаблону CSV")
public class CSVCommandController {

    private static final String CSV_TABLE_NAME = "template.csv";
    private static final String TEMPLATE_DIRECTORY = "classpath:templates/erd/";

    private final CsvFileReaderService csvFileReaderService;
    private final PlantUmlService plantUmlService;
    private final DtoMapper dtoMapper;
    private final FileWriterService fileWriterService;

    @ShellMethod(value = "Получить CSV шаблон для генерации скрипта ERD PlantUml\n" +
            "\t\t\tПример для Windows:\n" +
            "\t\t\terd-template 'C:\\\\Users\\\\gbasiladze\\\\Desktop'\n" +
            "\t\t\terd-template -T 'C:\\\\Users\\\\gbasiladze\\\\Desktop'\n" +
            "\t\t\tПример для Mac:\n" +
            "\t\t\terd-template /Users/georgijbasiladze/Desktop/src\n" +
            "\t\t\terd-template -T /Users/georgijbasiladze/Desktop/src'\n",
            key = {"erd-template"})
    public String getCsvTemplate(
            @ShellOption(value = {"-T"},help = "путь для создания файла шаблона", arity = 1) String targetDirectory) throws IOException
    {
        fileWriterService.writeFile(TEMPLATE_DIRECTORY + CSV_TABLE_NAME, targetDirectory + File.separator + CSV_TABLE_NAME);

        return "Шаблон " + CSV_TABLE_NAME + " загружен в директорию " + targetDirectory;
    }

    @ShellMethod(value = "Генерация скрипта ERD PlantUml по CSV шаблону\n" +
            "\t\t\tПримеры для Windows:\n" +
            "\t\t\terd 'C:\\\\Users\\\\gbasiladze\\\\Desktop'\n" +
            "\t\t\terd -P 'C:\\\\Users\\\\gbasiladze\\\\Desktop'\n" +
            "\t\t\tcsv -P 'C:\\\\Users\\\\gbasiladze\\\\Desktop' -D ,\n" +
            "\t\t\tПримеры для Mac:\n" +
            "\t\t\terd /Users/georgijbasiladze/Desktop/src\n" +
            "\t\t\terd -P /Users/georgijbasiladze/Desktop/src\n" +
            "\t\t\tcsv -P /Users/georgijbasiladze/Desktop/src -D ,\n",
            key = {"erd"})
    public String callCsvMode(
            @ShellOption(value = {"-P"}, help = "путь к шаблону CSV", arity = 1) String sourceDirectory,
            @ShellOption(value = {"-D"}, help = "разделитель в CSV", arity = 1, defaultValue = ",") char delimiter) throws IOException
    {
        String tablesPath = sourceDirectory +File.separator + CSV_TABLE_NAME;

        List<TemplateDto> templateDtos = csvFileReaderService.readCsv(tablesPath, delimiter, TemplateDto.class);

        List<Column> columns = dtoMapper.getColumnsByDtos(templateDtos);
        List<Connection> connections = dtoMapper.getConnectionsByDto(templateDtos, columns);

        String plantUmlText = plantUmlService.generateErdPlantUml(columns, connections);

        Files.writeString(Path.of(sourceDirectory + File.separator + "plant_uml.txt"), plantUmlText);

        return plantUmlText;
    }
}

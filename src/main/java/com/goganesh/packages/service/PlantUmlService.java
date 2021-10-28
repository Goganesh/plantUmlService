package com.goganesh.packages.service;

import com.goganesh.packages.model.Connection;
import com.goganesh.packages.model.Column;
import com.goganesh.packages.model.Table;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlantUmlService {

    public String generateErdPlantUml(List<Column> columns, List<Connection> connections) {
        StringBuilder erdPlantUmlText = new StringBuilder("@startuml" + System.lineSeparator())
                .append("' hide the spot\n" +
                        "hide circle\n" +
                        "\n" +
                        "' avoid problems with angled crows feet\n" +
                        "skinparam linetype ortho" + System.lineSeparator());

        erdPlantUmlText.append(getTablesPlantUmlText(columns));
        erdPlantUmlText.append(getConnectionPlantUmlText(connections));
        
        erdPlantUmlText.append("@enduml");
        return erdPlantUmlText.toString();
    }

    private StringBuilder getTablesPlantUmlText(List<Column> columns){
        StringBuilder text = new StringBuilder();

        Map<Table, List<Column>> tables = columns.stream()
                .collect(Collectors.groupingBy(
                        Column::getTable,
                        Collectors.toList()
                ));

        tables.entrySet()
                .forEach( table -> {
                    text.append("entity \"**" )
                            .append(table.getKey().getName())
                            .append("**\" as ")
                            .append(table.getKey().getName())
                            .append(" {" + System.lineSeparator())
                            .append("**name : type : key : comment**" + System.lineSeparator());

                    table.getValue()
                            .stream()
                            .sorted(Comparator.comparing(Column::getPosition))
                            .forEach(column -> {
                                text.append("\t" + column.getName())
                                        .append(" : ")
                                        .append(column.getDataType())
                                        .append(" : ")
                                        .append(column.getKey());

                                if (!column.getDescription().isBlank()){
                                    text.append(" : ")
                                            .append(column.getDescription() + System.lineSeparator());
                                } else {
                                    text.append(" : ")
                                            .append("Нет описания" + System.lineSeparator());
                                }
                            });

                    text.append("}" + System.lineSeparator());
                });

        return text;
    }

    private StringBuilder getConnectionPlantUmlText(List<Connection> connections){
        StringBuilder text = new StringBuilder();

        connections.stream()
                .forEach(connection -> {
                    if (connection.getRefColumn() == null)
                        return;

                    text.append(connection.getColumn().getTable().getName());

                    if (connection.getColumn().getKey().equals("PK")){
                        text.append(" ||");
                    } else if (connection.getColumn().getKey().equals("FK")){
                        text.append(" }o");
                    } else {
                        System.out.println("some mistake");
                    }
                    text.append("..");

                    if (connection.getRefColumn().getKey().equals("PK")){
                        text.append("|| ");
                    } else if (connection.getRefColumn().getKey().equals("FK")){
                        text.append("o{ ");
                    } else {
                        System.out.println("some mistake");
                    }

                    text.append(connection.getRefColumn().getTable().getName());
                    text.append(System.lineSeparator());

                });

        return text;
    }
}

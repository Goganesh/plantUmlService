package com.goganesh.packages.service;

import com.goganesh.packages.model.Column;
import com.goganesh.packages.model.Connection;
import com.goganesh.packages.model.Table;
import com.goganesh.packages.model.dto.TemplateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoMapper {

    public List<Column> getColumnsByDtos(List<TemplateDto> templateDtos){
        List<Column> columns = templateDtos.stream()
                .map(dto -> {
                    Table table = Table.builder()
                            .database(dto.getDatabase())
                            .schema(dto.getSchema())
                            .name(dto.getTableName())
                            .description(dto.getTableDescription())
                            .build();

                    return Column.builder()
                            .name(dto.getColumnName())
                            .description(dto.getColumnDescription())
                            .key(dto.getKey())
                            .position(dto.getPosition())
                            .dataType(dto.getDataType())
                            .table(table)
                            .build();
                        })

                .distinct()
                .collect(Collectors.toList());

        return columns;
    }

    public List<Connection> getConnectionsByDto(List<TemplateDto> templateDtos, List<Column> columns) {
        return templateDtos.stream()
                .filter(dto -> !dto.getRefTableName().isBlank() && !dto.getRefColumnName().isBlank())
                .map(dto -> {
                    String database = dto.getDatabase();
                    String schema = dto.getSchema();

                    String tableName = dto.getTableName();
                    String columnName = dto.getColumnName();

                    String refTableName = dto.getRefTableName();
                    String refColumnName = dto.getRefColumnName();

                    String connectionName = dto.getConnectionName();

                    Column column = columns.stream()
                            .filter(col -> col.getTable().getDatabase().equals(database)
                                            && col.getTable().getSchema().equals(schema)
                                            && col.getName().equals(columnName)
                                    && col.getTable().getName().equals(tableName)
                            )
                            .findFirst()
                            .get();

                    Column refColumn = columns.stream()
                            .filter(col -> col.getTable().getDatabase().equals(database)
                                    && col.getTable().getSchema().equals(schema)
                                    && col.getName().equals(refColumnName)
                                    && col.getTable().getName().equals(refTableName)
                            )
                            .findFirst()
                            .orElse(null);

                    return Connection.builder()
                            .column(column)
                            .refColumn(refColumn)
                            .connectionName(connectionName)
                            .build();
                })
                .distinct()
                .collect(Collectors.toList());
    }
}

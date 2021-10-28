package com.goganesh.packages.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TemplateDto {

    @CsvBindByName(column = "database")
    private String database;

    @CsvBindByName(column = "schema")
    private String schema;

    @CsvBindByName(column = "table")
    private String tableName;

    @CsvBindByName(column = "table_description")
    private String tableDescription;

    @CsvBindByName(column = "column")
    private String columnName;

    @CsvBindByName(column = "column_description")
    private String columnDescription;

    @CsvBindByName(column = "ordinal_position")
    private int position;

    @CsvBindByName(column = "data_type")
    private String dataType;

    @CsvBindByName(column = "key")
    private String key;

    @CsvBindByName(column = "connection_name")
    private String connectionName;

    @CsvBindByName(column = "ref_table")
    private String refTableName;

    @CsvBindByName(column = "ref_column")
    private String refColumnName;

}

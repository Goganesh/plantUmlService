package com.goganesh.packages.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Table {
    private String database;
    private String schema;
    private String name;
    private String description;
}

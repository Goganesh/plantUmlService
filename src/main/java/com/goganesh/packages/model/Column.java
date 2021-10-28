package com.goganesh.packages.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Column {

    private Table table;
    private String name;
    private String description;
    private String key;
    private int position;
    private String dataType;
}

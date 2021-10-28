package com.goganesh.packages.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Connection {

    private Column column;
    private Column refColumn;
    private String connectionName;

}

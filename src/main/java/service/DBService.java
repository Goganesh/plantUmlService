package service;

import model.dto.TemplateDto;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBService {

    private Connection connection;

    public void createConnection (String connectionLine, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(connectionLine, username, password);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    /*public List<ConnectionDto> getKeyDtoListByExecutedQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        statement.close();
        List<ConnectionDto> connectionDtos = new ArrayList<>();
        while (rs.next()) {
            connectionDtos.add(
                    new ConnectionDto()
                    *//*KeyDto.builder()
                            .tableCatalog(rs.getString("table_catalog"))
                            .columnName(rs.getString("column_name"))
                            .constraintType(rs.getString("constraint_type"))
                            .refColumnName(rs.getString("column_name_2"))
                            .refTableCatalog(rs.getString("table_catalog_2"))
                            .refTableName(rs.getString("table_name_2"))
                            .refTableSchema(rs.getString("table_schema_2"))
                            .tableName(rs.getString("table_name"))
                            .tableSchema(rs.getString("table_schema"))
                            .build()*//*
            );
        }
        return connectionDtos;
    }*/

    public List<TemplateDto> getTableAndColumnDtoByExecutedQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        statement.close();
        List<TemplateDto> templateDtos = new ArrayList<>();
        while (rs.next()) {
            templateDtos.add(
                    new TemplateDto()
                    /*TableAndColumnDto.builder()
                            .catalogName(rs.getString("catalog_name"))
                            .columnDescription(rs.getString("column_description"))
                            .columnName(rs.getString("column_name"))
                            .dataType(rs.getString("data_type"))
                            .oid(rs.getString("oid"))
                            .position(rs.getInt("position"))
                            .schemaName(rs.getString("schema_name"))
                            .tableDescription(rs.getString("table_description"))
                            .tableName(rs.getString("table_name"))
                            .build()*/
            );
        }
        return templateDtos;
    }
}

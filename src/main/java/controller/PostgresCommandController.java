package controller;

import lombok.AllArgsConstructor;
import model.dto.TemplateDto;
import org.springframework.shell.standard.*;
import service.DBService;
import service.DtoMapper;
import service.PlantUmlService;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@ShellComponent
@AllArgsConstructor
@ShellCommandGroup(value = "erd model by postgres connection")
public class PostgresCommandController {

    private final DBService dbService;
    private final DtoMapper dtoMapper;
    private PlantUmlService plantUmlService;

    private static final String CONNECTION_QUERY =
            "select tc.constraint_name, tc.constraint_type, tc.table_catalog, tc.table_schema, tc.table_name, kcu.column_name, kcu1.table_catalog as  table_catalog_2, kcu1.table_schema as table_schema_2, kcu1.table_name as table_name_2, kcu1.column_name as column_name_2\n" +
            "FROM information_schema.table_constraints AS tc\n" +
            "JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name AND tc.table_schema = kcu.table_schema and tc.table_name = kcu.table_name and tc.constraint_type in ('PRIMARY KEY', 'FOREIGN KEY')\n" +
            "left join INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS as rc on rc.constraint_name = tc.constraint_name and rc.constraint_schema = tc.constraint_schema and rc.constraint_catalog = tc.constraint_catalog\n" +
            "left join information_schema.key_column_usage as kcu1 on kcu1.constraint_catalog = rc.unique_constraint_catalog and kcu1.constraint_schema = rc.unique_constraint_schema and kcu1.constraint_name = rc.unique_constraint_name";
    private static final String TABLE_QUERY =
            "select ct.table_catalog, ct.table_schema, ct.oid, ct.table_name, dt.description as table_description, ct.ordinal_position, ct.column_name, dc.description as column_description, ct.udt_name as data_type\n" +
            "from (select distinct cl.oid, t.schemaname, t.tablename, c.*\n" +
            "from pg_catalog.pg_class cl\n" +
            "join pg_catalog.pg_namespace n on n.oid = cl.relnamespace --find schema\n" +
            "join pg_catalog.pg_tables t on cl.relname = t.tablename and n.nspname = t.schemaname --join class to table\n" +
            "join INFORMATION_SCHEMA.columns c on t.schemaname = c.table_schema and t.tablename = c.table_name\n" +
            ") ct --table and column information\n" +
            "left join pg_description dc on dc.objoid = ct.oid and dc.objsubid = ct.ordinal_position --join column description\n" +
            "left join pg_description dt on dt.objoid = ct.oid and dt.objsubid = 0 --join table description\n" +
            "where ct.table_name in ('teacher_payouts_tranches', 'teacher_payouts_expenditures', 'teacher_payouts_expenditure_categories','teacher_payouts_payments')\n" +
            "order by ct.table_schema, ct.table_name, ct.ordinal_position";

    @ShellMethod(
            value = "Generate text for plantuml erd model from postgres db connection\n" +
                    "\t\t\tCommand Examples:\n" +
                    "\t\t\tp -C localhost:3456 -B mybase -U admin -P qwerty -T /Users/georgijbasiladze/Desktop/src\n" +
                    "\t\t\tpostgres --connection localhost:3456 --base mybase --user admin --passworf qwerty --target /Users/georgijbasiladze/Desktop/src\n",
            key = {"p", "postgres"}
    )
    public String callPostgresMode(
            @ShellOption(value = {"-C", "--connection"}, help = "db connection string", arity = 1) String connectionLine,
            @ShellOption(value = {"-B", "--base"}, help = "database name", arity = 1) String database,
            @ShellOption(value = {"-U", "--user"}, help = "username", arity = 1) String username,
            @ShellOption(value = {"-P", "--password"}, help = "user password", arity = 1) String password,
            @ShellOption(value = {"-T", "--target"},help = "path to target directory for result", arity = 1) String targetDirectory
    ) throws SQLException, IOException {

        dbService.createConnection(connectionLine, username, password);
        List<TemplateDto> templateDtos = dbService.getTableAndColumnDtoByExecutedQuery(TABLE_QUERY);
        dbService.closeConnection();

        /*List<Table> tables = dtoMapper.getColumnsByDtos(templateDtos);
        List<Connection> connections = dtoMapper.getConnectionsByDto(templateDtos, tables);*/

        //String plantUmlText = plantUmlService.generateErdPlantUml(tables, connections);

        //Files.writeString(Path.of(targetDirectory + File.separator + "Plant_Uml.wsd"), plantUmlText);

        return null;//plantUmlText;
    }
}

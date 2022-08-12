package executor;

import parser.QueryParser;
import java.util.Scanner;

public class WriteQuery {
    static String database = null;

    public static void sqlQueries() throws Exception {

        System.out.println("Please enter the query you want to execute");
        Scanner sc = new Scanner(System.in);
        String query = sc.nextLine();
        String lcQuery = query.toLowerCase();
        boolean result;
        QueryExecutor qe = new QueryExecutor();
        if (lcQuery.contains("create database")) {
            result = QueryParser.parser(query);
            if (!result) {
                throw new Exception("Please check the query");

            }
            qe.ExecuteCreateDatabase(query);
            System.out.println("created database successfully!");

        } else if (lcQuery.contains("use database")) {
            result = QueryParser.parser(query);
            if (!result) {
                throw new Exception("Please check the query");
            }
            database = qe.ExecuteUseDatabase(query);
        } else if (lcQuery.contains("create table")) {
            result = QueryParser.parser(query);
            if (database == null) {
                System.out.println("please select the database");
            }
            qe.ExecuteCreateTable(database, query);
            System.out.println("table created successfully!");
        } else if (lcQuery.contains("insert into")) {
            result = QueryParser.parser(query);
            if (database == null) {
                System.out.println("please select the database");

            }
            qe.ExecuteInsertStatement(database, query);
            System.out.println("record inserted successfully!");
        } else if (lcQuery.contains("select")) {
            result = QueryParser.parser(query);
            if (database == null) {
                System.out.println("please select the database");
            }
            qe.ExecuteSelectStatement(database, query);
        }


    }
}

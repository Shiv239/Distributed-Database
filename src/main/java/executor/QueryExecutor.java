package executor;

import logs.QueryLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;


public class QueryExecutor {

    public static void ExecuteCreateDatabase(String query) throws Exception {
        QueryLog log = new QueryLog();
        String databaseString = query.replace(";", "");
        String[] databaseArray = databaseString.split(" ");
        String databaseName = databaseArray[databaseArray.length - 1];
        String databaseDirectory = "Database/" + databaseName;
        File database = new File(databaseDirectory);
        if (database.exists()) {
            throw new Exception("The database already exists!");
        } else {
            database.mkdir();
            System.out.println("Database created successfully!");
            log.writeQueryLog(query);
        }
    }
    public static String ExecuteUseDatabase(String query) throws Exception
    {
        String databaseString = query.replace(";", "");
        String[] databaseArray = databaseString.split(" ");
        String databaseName = databaseArray[databaseArray.length - 1];
        String databaseDirectory = "Database/" + databaseName;
        String currentDatabase;
        File database = new File(databaseDirectory);
        if (database.exists()) {
            currentDatabase = database.toString() + "/";

        } else {
            throw new Exception("The database already exists!");
        }
        return currentDatabase;
    }
    public static void ExecuteCreateTable(String db, String query) throws Exception {
        query = query.replace(");", " ");
        String[] columnNames = query.split("\\(");
        if (columnNames.length >= 2) {
            String[] tableTokens = columnNames[0].split(" ");
            String tableName = tableTokens[2];
            String filePath =  db + "/" + tableName + ".txt";
            String metafilePath = db + "/" + tableName + "_meta" + ".txt";
            FileWriter metawriter = new FileWriter(metafilePath);
            File f = new File(filePath);
            if(f.exists()){
                throw new Exception("Table with same name already exists.");

            }
            try (FileWriter writer = new FileWriter(filePath)) {
                StringBuilder fileheader = new StringBuilder();
                String[] headerList = columnNames[1].split(",");
                for (int i = 0; i < headerList.length; i++)
                {
                    String[] headers = headerList[i].toString().trim().split(" ");
                    metawriter.write(Arrays.toString(headers).replace("[", "")
                            .replace("]", "").replace(",", "|")
                            .replace("| KEY", ""));
                    metawriter.write("\n");
                    if (headers.length == 2) {
                        fileheader.append(headers[0]).append("(").append(headers[1]).append(")").append("|");
                    }
                    if (headers.length == 4 && headers[2].equalsIgnoreCase("PRIMARY")) {
                        fileheader.append(headers[0]).append("(").append(headers[1]).append("@").append("PK")
                                .append(")").append("|");
                    }
                    if (headers.length == 4 && headers[2].equalsIgnoreCase("REFERENCES")) {
                        String fkTable = headers[3].split("\\(")[0];
                        String fKcol = headers[3].split("\\(")[1].replaceAll("\\)", "");
                        fileheader.append(headers[0]).append("(").append(headers[1]).append("@")
                                .append("FK").append("@").append(fkTable).append("@").append(fKcol).append(")")
                                .append("|");
                    }
                }
                writer.write(fileheader.toString());
                writer.close();
                metawriter.close();

            }

        }

    }


    public static void ExecuteInsertStatement(String db, String query) throws Exception {
        String q = query.replace(")", "");
        String[] q1 = q.split("\\(");
        String tableName = q1[0].split(" ")[2];
        String filePath =  db + "/" + tableName + ".txt";
        File f = new File(filePath);
        String[] columnNames = q1[1].split(" VALUES");
        String[] finalColumnNames = columnNames[0].split(",");
        String[] values = q1[2].replace(";", "").split(",");
        BufferedReader brTest = new BufferedReader(new FileReader(filePath));
        String[] headers = brTest.readLine().split("\\)");
        FileWriter writer = new FileWriter(filePath, true);
        if (finalColumnNames.length == headers.length - 1) {
            writer.append("\n");
            writer.append(Arrays.toString(values).replace("[", "").replace("]", "")
                    .replace(",", "|"));
        } else {
            throw new Exception("Column doesn't match the inserted value");
        }
        writer.close();


    }

    public static void  ExecuteSelectStatement(String db, String query) throws Exception {
        query = query.toLowerCase();
        if(query.contains("where")){
            String[] q = query.split("where");
            String[] FetchtableName = q[0].split(" ");
            String tableName = FetchtableName[3];
            String[] FetchColumnName = q[1].split("=");
            String columnName = FetchColumnName[0].replace(" ","");
            System.out.println(columnName);
            BufferedReader br = new BufferedReader(new FileReader("Database/"+db + "/" + tableName + ".txt"));
            String line = br.readLine();
            int getColumns = line.indexOf(columnName);
            System.out.println(getColumns);
            System.out.println(FetchColumnName[1].replace(";","|")
                    .replace("\"",""));
            while ((line = br.readLine()) != null){
                boolean record = line.contains(FetchColumnName[1].replace(";","|").replace("\"",""));
                if(record){
                    System.out.println(line);
                }
            }
        }else{
            String[] q = query.split(" ");
            String tableName = q[3].replace(";","");
            BufferedReader br = new BufferedReader(new FileReader("Database/"+db + "/" + tableName + ".txt"));
            String line = br.readLine();
            while (line!=null){
                line = br.readLine();
                System.out.println(line);
            }
        }



    }




}

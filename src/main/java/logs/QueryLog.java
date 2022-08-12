package logs;

import authentication.Session;

import java.io.*;

public class QueryLog {
    public static void writeQueryLog(String query) throws IOException {
        Session s = new Session();
        File queryLog = new File("Logs");
        if(!queryLog.exists()) {
            queryLog.mkdir();
        }
        FileWriter fw = new FileWriter("Logs/QueryLog.txt",true);
        fw.append(s.getUser()+"||");
        fw.append(System.currentTimeMillis()+"||");
        fw.append(query);
        fw.append("\n");
        fw.close();
        }
    }


package logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EventLog {
    public static void writeEventLog(String query) throws IOException {
        File log = new File("Logs");
        if(!log.exists()) {
            log.mkdir();
        }
        FileWriter fw = new FileWriter("Logs/EventLog.txt",true);
        fw.append(query+"||"+System.currentTimeMillis());
        fw.append("\n");
        fw.close();
    }
}


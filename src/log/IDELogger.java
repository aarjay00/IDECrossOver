package log;


/**
 * Created by aarjay on 12/08/16.
 */

import com.google.gson.Gson;
import com.intellij.openapi.project.ProjectManager;

import java.io.File;

//import com.intellij.openapi.diagnostic.Logger;


import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class IDELogger {

    private static Logger LOGGER;
    private static Gson gson;

    private static IDELogger ideLogger = null;

    private IDELogger() {
    }

    public static IDELogger getInstance() {
        if(ideLogger==null) {
            ideLogger = new IDELogger();
//            ideLogger.LOGGER=Logger.getInstance("IDECrossOver");
            ideLogger.LOGGER = Logger.getLogger("IDECrossOver");
            try {
                Handler[] handlers = LOGGER.getHandlers();
                new File(System.getProperty("user.dir")+"/.IDECrossOverLogs").mkdir();
                System.out.println("Logs in "+System.getProperty("user.dir"));
                FileHandler fileHandler = new FileHandler(System.getProperty("user.dir")+"/.IDECrossOverLogs/log");
                fileHandler.setFormatter(new SimpleFormatter());
                LOGGER.addHandler(fileHandler);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            ideLogger.gson= new Gson();
        }
        return ideLogger;
    }

    public void log(String logEntry) {
        LOGGER.info(logEntry);
//        System.out.println(logEntry);
    }
    public void log(Map<String,String> logEntry){
        logEntry.put("timeStamp",Long.toString(System.currentTimeMillis()/1000L));
        String jsonString = gson.toJson(logEntry);
        LOGGER.info(jsonString);
        System.out.println(jsonString);
    }
    public static String toString(Object object)
    {
        return gson.toJson(object);
    }

}
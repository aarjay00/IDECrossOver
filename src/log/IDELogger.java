package log;


/**
 * Created by aarjay on 12/08/16.
 */

import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;

import java.util.Map;

public class IDELogger {

    private static Logger LOGGER;
    private static Gson gson;

    private static IDELogger ideLogger = null;

    private IDELogger() {
    }

    public static IDELogger getInstance() {

        if(ideLogger==null) {
            ideLogger = new IDELogger();
            ideLogger.LOGGER=Logger.getInstance("IDECrossOver");
            ideLogger.gson= new Gson();
        }
        return ideLogger;
    }

    public void log(String logEntry) {
        LOGGER.info(logEntry);
        System.out.println(logEntry);
    }
    public void log(Map<String,String> logEntry){
        String jsonString = gson.toJson(logEntry);
        LOGGER.info(jsonString);
        System.out.println(jsonString);
    }
    public static String toString(Map<String,String> mp)
    {
        return gson.toJson(mp);
    }
}

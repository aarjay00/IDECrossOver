package log;


/**
 * Created by aarjay on 12/08/16.
 */

import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.sun.jna.platform.win32.Sspi;

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
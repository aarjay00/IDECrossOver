package log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.intellij.notification.EventLog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.psi.tree.xml.IDTDElementType;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aarjay on 13/08/16.
 */
public class ActionLogger {


    private static ActionLogger actionLogger = null;

    public static ActionLogger getInstance(){
        if(actionLogger==null) {
            actionLogger  =  new ActionLogger();
        }
        return actionLogger;
    }

    public  void logAction(AnAction action, DataContext dataContext, AnActionEvent event){
        String description = action.getTemplatePresentation().getDescription();
        String inputEvent = event.getInputEvent().toString();
        String myText=action.getTemplatePresentation().getText();
        Map<String,String> logEntry= new HashMap<String,String>();
        logEntry.put("Description",description);
        logEntry.put("inputEvent",inputEvent);
        logEntry.put("myText",myText);
        IDELogger.getInstance().log(logEntry);
    }
    public void logEditorMouseEvent(EditorMouseEvent e)
    {
        Map<String,String> logEntry = new HashMap<String,String>();
        logEntry.put("mouseEvent",IDELogger.toString(logMouseEvent(e.getMouseEvent())));
        try{
            logEntry.put("mouseEventArea",e.getArea().toString());
        }
        catch (NullPointerException err)
        {
/*
            err.printStackTrace();
*/
        }
        IDELogger.getInstance().log(logEntry);
    }

    public Map<String,String> logMouseEvent(MouseEvent mouseEvent)
    {
        Map<String,String> logEntry= new HashMap<String,String>();
        logEntry.put("mouseEvent",mouseEvent.paramString());
        logEntry.put("clickCount", Integer.toString(mouseEvent.getClickCount()));
        logEntry.put("keyboardHotkeys",mouseEvent.getMouseModifiersText(mouseEvent.getModifiers()));
        return logEntry;
    }


}

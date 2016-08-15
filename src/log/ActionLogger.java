package log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.intellij.notification.EventLog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.psi.tree.xml.IDTDElementType;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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

        String inputEvent = event.getInputEvent().toString();
        String myText=action.getTemplatePresentation().getText();
        Map<String,String> logEntry= new HashMap<String,String>();
        logEntry.put("description",action.getTemplatePresentation().getDescription());
        logEntry.put("inputEvent",event.getInputEvent().toString());
        logEntry.put("myText",action.getTemplatePresentation().getText());
        logEntry.put("eventPlace",event.getPlace());
        logEntry.put("inputEvent",IDELogger.toString(logInputEvent(event.getInputEvent())));
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

    public Map<String,String> logInputEvent(InputEvent inputEvent)
    {
        if(inputEvent instanceof MouseEvent)
            return logMouseEvent((MouseEvent)inputEvent);
        else if(inputEvent instanceof KeyEvent)
            return logKeyEvent((KeyEvent)inputEvent);
        else
            return new HashMap<String,String>();
    }

    public Map<String,String> logMouseEvent(MouseEvent mouseEvent)
    {
        Map<String,String> logEntry= new HashMap<String,String>();
        logEntry.put("MouseEvent",mouseEvent.paramString());
        logEntry.put("clickCount", Integer.toString(mouseEvent.getClickCount()));
        logEntry.put("keyboardHotkeys",mouseEvent.getMouseModifiersText(mouseEvent.getModifiers()));
        return logEntry;
    }
    public Map<String,String> logKeyEvent(KeyEvent keyEvent)
    {
        Map<String,String> logEntry=new HashMap<>();
        logEntry.put("KeyText",keyEvent.getKeyText(keyEvent.getKeyCode()));
        logEntry.put("KeyEvent",keyEvent.paramString());
        return logEntry;
    }

}

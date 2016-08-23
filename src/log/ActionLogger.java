package log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.intellij.notification.EventLog;
import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.psi.tree.xml.IDTDElementType;
import com.intellij.tools.Tool;

import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aarjay on 13/08/16.
 */
public class ActionLogger {


    private static ActionLogger actionLogger = null;

    private ToolWindowImpl currentToolWindow ;
    private Integer mouseOnTool;

    public static ActionLogger getInstance(){
        if(actionLogger==null) {
            actionLogger  =  new ActionLogger(null,0);
        }
        return actionLogger;
    }

    public ActionLogger(ToolWindowImpl toolWindow, Integer mouseOnTool) {
        this.currentToolWindow = toolWindow;
        this.mouseOnTool = mouseOnTool;
    }

    public  void logAction(AnAction action, DataContext dataContext, AnActionEvent event){

        String inputEvent = event.getInputEvent().toString();
        String myText=action.getTemplatePresentation().getText();
        Map<String,String> logEntry= new HashMap<String,String>();
        logEntry.put("logType","Action");
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
        logEntry.put("logType","EditorMouseEvent");
        logEntry.put("mouseEvent",IDELogger.toString(logMouseEvent(e.getMouseEvent())));
        EditorImpl editor = (EditorImpl)e.getSource();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
        logEntry.put("fileName",virtualFile.getPath());
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
        logEntry.put("logType","MouseEvent");
        logEntry.put("MouseEvent",mouseEvent.paramString());
        logEntry.put("clickCount", Integer.toString(mouseEvent.getClickCount()));
        logEntry.put("keyboardHotkeys",mouseEvent.getMouseModifiersText(mouseEvent.getModifiers()));
        return logEntry;
    }
    public Map<String,String> logKeyEvent(KeyEvent keyEvent)
    {
        Map<String,String> logEntry=new HashMap<>();
        logEntry.put("logType","KeyEvent");
        logEntry.put("KeyText",keyEvent.getKeyText(keyEvent.getKeyCode()));
        logEntry.put("KeyEvent",keyEvent.paramString());
        return logEntry;
    }

    public void logDocumentEvent(DocumentEvent event)
    {
        Map<String ,String> logEntry = new HashMap<>();
        logEntry.put("logType","DocumentEvent");
        logEntry.put("description",event.toString());
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(event.getDocument());
        logEntry.put("fileName",virtualFile.getPath());
        logEntry.put("oldText",event.getOldFragment().toString());
        logEntry.put("newText",event.getNewFragment().toString());
        logEntry.put("offSet",((Integer)event.getOffset()).toString());
        IDELogger.getInstance().log(logEntry);
    }

    public void logFileOpenClose(VirtualFile virtualFile,Boolean open)
    {
        Map<String,String> logEntry= logVirtualFile(virtualFile);
        logEntry.put("logType","FileOpenClose");
        logEntry.put("fileAction",open.toString());
        IDELogger.getInstance().log(logEntry);
    }

    public void logFileEditorChange(VirtualFile virtualFile)
    {
        Map<String,String> logEntry = logVirtualFile(virtualFile);
        logEntry.remove("fileContent");
        logEntry.put("logType","FileEditorChange");
        IDELogger.getInstance().log(logEntry);
    }

    public Map<String,String> logVirtualFile(VirtualFile virtualFile)
    {
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        Map<String,String> logEntry= new HashMap<>();
        logEntry.put("fileContent",document.getText());
        logEntry.put("fileName",virtualFile.getPath());
        return logEntry;
    }
    public void logProjectOpenClose(Project project, Boolean open)
    {
        Map<String,String> logEntry = new HashMap<String,String>();
        logEntry.put("logType","ProjectOpenClose");
        logEntry.put("projectAction",open.toString());
        logEntry.put("projectDetails",project.toString());
        IDELogger.getInstance().log(logEntry);
    }
    public void logFocusComponent(PropertyChangeEvent event){
        Map<String,String> logEntry  = new HashMap<>();
        logEntry.put("logType","focusComponent");
        if(event.getOldValue()!=null)
            logEntry.put("oldEvent",event.getOldValue().toString());
        if(event.getNewValue()!=null)
            logEntry.put("newEvent",event.getNewValue().toString());
        if(logEntry.size()>1)
            IDELogger.getInstance().log(logEntry);
    }
    public void logToolMouseMovement(ToolWindowImpl newToolWindow){
        if(newToolWindow==null) return;
        if(this.currentToolWindow!=null && newToolWindow.getId().equals(this.currentToolWindow.getId())){
            this.mouseOnTool+=1;
        }
        else
        {
            if(this.currentToolWindow!=null)
                logNewToolWindow(this.currentToolWindow,false);
            this.currentToolWindow=newToolWindow;
            this.mouseOnTool=0;
            logNewToolWindow(this.currentToolWindow,true);
        }
    }
    public void logNewToolWindow(ToolWindowImpl toolWindow, Boolean leaveTool){
        Map<String,String> logEntry = new HashMap<>();
        logEntry.put("logType","ToolEntry");
        logEntry.put("toolEnter",leaveTool.toString());
        logEntry.put("tooName",toolWindow.getId());
        logEntry.put("toolDetails",this.currentToolWindow.toString());
        logEntry.put("mouseMovementNum",this.mouseOnTool.toString());
        IDELogger.getInstance().log(logEntry);
    }
    public void logFocus(FocusEvent e,ToolWindowImpl toolWindow,Boolean focusGained){
        Map<String,String> logEntry = new HashMap<>();
        logEntry.put("logType","FocusEvent");
        logEntry.put("focusDetails",e.paramString());
        logEntry.put("focusGained",focusGained.toString());
        logEntry.put("toolName",toolWindow.getId());
        IDELogger.getInstance().log(logEntry);
    }
    public void  logNotification(Notification notification){
        System.out.println(notification.toString());
        Map<String,String> logEntry = new HashMap<>();
        logEntry.put("logType","Notification");
        logEntry.put("groupId",notification.getGroupId());
        logEntry.put("title",notification.getTitle());
        logEntry.put("content",notification.getContent());
        IDELogger.getInstance().log(logEntry);
    }
    public void logCompilationStatus(CompileContext compileContext , Integer errors, Boolean aborted, Integer warnings){
        HashMap<String,String> logEntry =  new HashMap<>();
        logEntry.put("logType","CompilationStatus");
        logEntry.put("errors",errors.toString());
        logEntry.put("aborted",aborted.toString());
        logEntry.put("warnings",warnings.toString());
        logEntry.put("compileScope",compileContext.getCompileScope().toString());
        IDELogger.getInstance().log(logEntry);
    }
}

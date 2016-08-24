import Listeners.*;
import com.intellij.notification.EventLog;
import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;
import com.intellij.notification.NotificationsAdapter;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.*;
import com.intellij.openapi.wm.impl.FocusManagerImpl;
import com.intellij.openapi.wm.impl.ToolWindowManagerImpl;
import com.intellij.psi.PsiManager;
import com.intellij.ui.content.MessageView;
import com.intellij.util.messages.MessageBus;
import log.ActionLogger;
import log.IDELogger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;


/**
 * Created by aarjay on 12/08/16.
 */
public class PluginStartUp implements ProjectComponent {
    public PluginStartUp(Project project) {
    }

    @Override
    public void initComponent() {
        IDELogger.getInstance().log("PluginStarted");
        System.out.println("Plugin Started!!!!\n");
        ActionManager actionManager = ActionManager.getInstance();
        actionManager.addAnActionListener(ActionListener.getInstance());
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {

        return "PluginStartUp";
    }

    @Override
    public void projectOpened() {

        Properties properties = System.getProperties();
        Application application = ApplicationManager.getApplication();
        Project[] projectList  = ProjectManager.getInstance().getOpenProjects();

        ProjectManager.getInstance().addProjectManagerListener(ProjectListener.getInstance());

            EditorFactory editorFactory = EditorFactory.getInstance();
        editorFactory.addEditorFactoryListener(EditorFactoryHook.getInstance());

        WindowManager windowManager = WindowManager.getInstance();
        windowManager.addListener(WindowManagerHook.getInstance());

        String na = ToolWindowId.MESSAGES_WINDOW;
        for(Project project : projectList )
        {
            PsiManager psiManager = PsiManager.getInstance(project);
            project.getMessageBus().connect(project).subscribe(Notifications.TOPIC, new NotificationsAdapter() {
                @Override
                public void notify(@NotNull Notification notification) {
                    ActionLogger.getInstance().logNotification(notification);
                }
            });

            MessageView messageView = MessageView.SERVICE.getInstance(project);
            ToolWindow eventLog = EventLog.getEventLog(project);
            ActionLogger.getInstance().logProjectOpenClose(project,true);
            MessageBus messageBus = project.getMessageBus();
//            KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new PropertyChangeListener() {
//                @Override
//                public void propertyChange(PropertyChangeEvent evt) {
//                    ActionLogger.getInstance().logFocusComponent(evt);
//                }
//            });
            FileEditorManager fileEditorManager= FileEditorManager.getInstance(project);
            ToolWindowManagerImpl toolWindowManager  =(ToolWindowManagerImpl)ToolWindowManager.getInstance(project);
            toolWindowManager.addToolWindowManagerListener(ToolWindowManagerHook.getInstance());
            String[] toolWindowIDList = toolWindowManager.getToolWindowIds();

            for(String ID : toolWindowIDList){
                ToolWindow toolWindow = toolWindowManager.getToolWindow(ID);
                JComponent jComponent = toolWindow.getComponent();
            }
            ToolWindowManager.getInstance(project).getToolWindowIds();
            IdeFocusManager focusManager = toolWindowManager.getFocusManager();
            CompilerManager compilerManager =  CompilerManager.getInstance(project);
            compilerManager.addCompilationStatusListener(CompileStatusListener.getInstance());
            fileEditorManager.addFileEditorManagerListener(EditorManagerListener.getInstance());//deprecated
        }
        System.out.println("Plugin opened!!!!\n");
        getInFocusApplication();

        // called when project is opened
    }

    @Override
    public void projectClosed() {
        System.out.println("Plugin closed!!!!\n");

        // called when project is being closed
    }

    private void getInFocusApplication()
    {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {

                String osName= System.getProperty("os.name").toLowerCase();
                if(osName.contains("mac"))
                    runLinux();
                else if(osName.contains("windows"))
                    runWindows();

            }
            private void runLinux(){
                while(true) {
                    String command= "lsappinfo";
                    StringBuffer output = new StringBuffer();
                    Process p;
                    try {
                        p = Runtime.getRuntime().exec(command);
                        p.waitFor();
                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            if(line.contains("in front"))
                                output.append(line + "\n");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Command Output---" + output.toString());
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            private void runWindows(){
                while(true){

                    char[] buffer = new char[1024 * 2];
                    HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd, buffer, 1024   );
                    System.out.println("Active window title: " + Native.toString(buffer));
                    RECT rect = new RECT();
                    User32.INSTANCE.GetWindowRect(hwnd, rect);
                    System.out.println("rect = " + rect);
                    try{
                        Thread.sleep(30000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

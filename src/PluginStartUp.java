import Listeners.*;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.impl.ServiceManagerImpl;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.openapi.wm.impl.FocusManagerImpl;
import com.intellij.openapi.wm.impl.ToolWindowManagerImpl;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.impl.ContentManagerImpl;
import com.intellij.util.messages.MessageBus;
import log.ActionLogger;
import log.IDELogger;
import org.apache.batik.bridge.FocusManager;
import org.jetbrains.annotations.NotNull;

import javax.jnlp.ServiceManager;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        Project[] projectList  = ProjectManager.getInstance().getOpenProjects();

        ProjectManager.getInstance().addProjectManagerListener(ProjectListener.getInstance());

        EditorFactory editorFactory = EditorFactory.getInstance();
        editorFactory.addEditorFactoryListener(EditorFactoryHook.getInstance());

        WindowManager windowManager = WindowManager.getInstance();
        windowManager.addListener(WindowManagerHook.getInstance());


        for(Project project : projectList )
        {

            ActionLogger.getInstance().logProjectOpenClose(project,true);
            MessageBus messageBus = project.getMessageBus();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    ActionLogger.getInstance().logFocusComponent(evt);
                    /*if(ProjectManager.getInstance().getOpenProjects()!=null && ProjectManager.getInstance().getOpenProjects().length>0) {
                        Component component = FocusManagerImpl.getInstance(ProjectManager.getInstance().getOpenProjects()[0]).getFocusOwner();
                        if (component != null) {
                            System.out.println("gaaa" + component.toString());
                            while (component.getParent() != null) {
                                component = component.getParent();
                                System.out.println("gaaparent" + component.toString());
                            }
                        }
                    }
                    else
                        return;
                    System.out.println("yipee!!");
                    if(evt.getOldValue()!=null){
                        System.out.println("oo"+evt.getOldValue().toString());
                        System.out.println("Old Value"+evt.getOldValue().getClass().getName());
                    }
                    if(evt.getNewValue()!=null){
                        System.out.println("nn"+evt.getNewValue().toString());
                        System.out.println("New Value"+evt.getNewValue().getClass().getName());

                    }*/
                }
            });
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

        // called when project is opened
    }

    @Override
    public void projectClosed() {
        System.out.println("Plugin closed!!!!\n");

        // called when project is being closed
    }

    private void InFocusComponent(Project project)
    {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runReadAction(new Runnable() {
                    @Override
                    public void run() {

                        Integer i =0;
                        while(i<100)
                        {
                            System.out.println("haha");
                            Component component = FocusManagerImpl.getInstance().getFocusOwner();
                            System.out.println(component.toString());
                            try {
                                Thread.sleep(5000);
                                i++;
                            }
                            catch (InterruptedException e)
                            {
                                IDELogger.getInstance().log("Error in Thread Sleeping");
                            }
                        }
                    }
                });
            }
        });
    }
}

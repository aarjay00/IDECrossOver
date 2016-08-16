import Listeners.*;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.FocusManagerImpl;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.impl.ContentManagerImpl;
import com.intellij.util.messages.MessageBus;
import log.IDELogger;
import org.apache.batik.bridge.FocusManager;
import org.jetbrains.annotations.NotNull;

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
            MessageBus messageBus = project.getMessageBus();
            FileEditorManager fileEditorManager= FileEditorManager.getInstance(project);
            ToolWindowManager toolWindowManager  = ToolWindowManager.getInstance(project);
            String[] toolWindowIDList = toolWindowManager.getToolWindowIds();

            for(String ID : toolWindowIDList){
                toolWindowManager.getToolWindow(ID);
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
}

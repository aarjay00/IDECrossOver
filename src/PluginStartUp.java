import Listeners.ActionListener;
import Listeners.EditorManagerListener;
import Listeners.ProjectListener;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.util.messages.MessageBus;
import log.IDELogger;
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

        for(Project project : projectList )
        {
            MessageBus messageBus = project.getMessageBus();
            FileEditorManager fileEditorManager= FileEditorManager.getInstance(project);
            //deprecated
            fileEditorManager.addFileEditorManagerListener(EditorManagerListener.getInstance());
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

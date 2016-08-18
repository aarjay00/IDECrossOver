package Listeners;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Created by aarjay on 17/08/16.
 */
public class ToolWindowManagerHook implements ToolWindowManagerListener {

    private static ToolWindowManagerHook toolWindowManagerHook = null;

    public static ToolWindowManagerHook getInstance(){
        if(toolWindowManagerHook==null){
            toolWindowManagerHook = new ToolWindowManagerHook();
        }
        return toolWindowManagerHook;
    }
    @Override
    public void toolWindowRegistered(@NotNull String s) {
        Project [] projects = ProjectManager.getInstance().getOpenProjects();
        for(Project project: projects)
        {
            ToolWindow toolWindow= ToolWindowManager.getInstance(project).getToolWindow(s);
            Class cl=toolWindow.getClass();
        }
    }

    @Override
    public void stateChanged() {

    }
}

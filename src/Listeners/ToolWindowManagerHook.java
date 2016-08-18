package Listeners;

import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.impl.ContentManagerImpl;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
            ToolWindowImpl toolWindow= (ToolWindowImpl)ToolWindowManager.getInstance(project).getToolWindow(s);
            /*toolWindow.addPropertyChangeListener();*/
            ContentManager contentManager = toolWindow.getContentManager();
            if(s=="Event Log"){
                NonOpaquePanel nonOpaquePanel = (NonOpaquePanel) toolWindow.getComponent();
            }
            JComponent component  = toolWindow.getComponent();
            component.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    System.out.println("here");
                }
                @Override
                public void focusLost(FocusEvent e) {
                    System.out.println("there");
                }
            });
            Class cl  = component.getClass();
//            toolWindow.getType()
            Class toolClass=component.getClass();
            String name  = component.getClass().getName();
//            component.addFocusListener(ComponentFocusListener.getInstance());
            contentManager.addContentManagerListener(new ContentManagerHook(toolWindow));

       /*     Content[] contents = contentManager.getContents();
            for(Content content:contents){
                String st= content.getToolwindowTitle();
                System.out.println(st);
            }*/
        }
    }

    @Override
    public void stateChanged() {

    }
}

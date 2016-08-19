package Listeners;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.impl.SystemDock;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.ContentManagerListener;
import com.intellij.ui.content.impl.ContentImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


/**
 * Created by aarjay on 18/08/16.
 */
public class ContentManagerHook implements ContentManagerListener {


    private ToolWindowImpl toolWindow;
    private JComponent component;
    ComponentMouseListener componentMouseListener;

    public ContentManagerHook(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
        this.component = toolWindow.getComponent();
        this.componentMouseListener =  new ComponentMouseListener(toolWindow);
        addMouseListenerToComponent(this.component);
    }

    @Override
    public void contentAdded(ContentManagerEvent contentManagerEvent) {
        Content []  contents = (Content[]) toolWindow.getContentManager().getContents();
        System.out.println(toolWindow.getTitle());
        for(Content content: contents){

            JComponent component = content.getComponent();
            this.component=component;
            addMouseListenerToComponent(component);
        }
    }

    private void addMouseListenerToComponent(JComponent component){
        component.addMouseMotionListener(this.componentMouseListener);
        for(Component component1 : component.getComponents()){
            component1.addMouseMotionListener(this.componentMouseListener);
            addMouseListenerToComponent((JComponent)component1);
        }
    }

    @Override
    public void contentRemoved(ContentManagerEvent contentManagerEvent) {

    }

    @Override
    public void contentRemoveQuery(ContentManagerEvent contentManagerEvent) {

    }

    @Override
    public void selectionChanged(ContentManagerEvent contentManagerEvent) {

    }
}

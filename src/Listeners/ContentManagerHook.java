package Listeners;

import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.ContentManagerListener;

import javax.swing.*;
import java.awt.*;


/**
 * Created by aarjay on 18/08/16.
 */
public class ContentManagerHook implements ContentManagerListener {


    private ToolWindowImpl toolWindow;
    private JComponent component;
    ComponentMouseMotionListener componentMouseMotionListener;
    ComponentFocusListener componentFocusListener;

    public ContentManagerHook(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
        this.component = toolWindow.getComponent();
//        component.add
        this.componentMouseMotionListener =  new ComponentMouseMotionListener(toolWindow);
        this.componentFocusListener  =  new ComponentFocusListener(toolWindow);
        addMouseListenerToComponent(this.component);
        addFocusListenerToComponent(this.component);
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
        component.addMouseMotionListener(this.componentMouseMotionListener);
        for(Component component1 : component.getComponents()){
            component1.addMouseMotionListener(this.componentMouseMotionListener);
            addMouseListenerToComponent((JComponent)component1);
        }
    }


    private void addFocusListenerToComponent(JComponent component) {
        component.addFocusListener(this.componentFocusListener);
        for (Component component1 : component.getComponents()) {
            component.addFocusListener(this.componentFocusListener);
            addFocusListenerToComponent((JComponent) component1);
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

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
    ComponentInputMethodListener componentInputMethodListener;
    ComponentKeyListener componentKeyListener;

    public ContentManagerHook(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
        this.component = toolWindow.getComponent();
//        component.add
        this.componentMouseMotionListener =  new ComponentMouseMotionListener(toolWindow);
        this.componentFocusListener  =  new ComponentFocusListener(toolWindow);
        this.componentInputMethodListener= new ComponentInputMethodListener(toolWindow);
        this.addKeyListenerToComponent(this.component);
        addMouseListenerToComponent(this.component);
//        addFocusListenerToComponent(this.component);
        addInputMethodListenerToComponent(this.component);
        addKeyListenerToComponent(this.component);
    }

    @Override
    public void contentAdded(ContentManagerEvent contentManagerEvent) {
            Content []  contents = (Content[]) toolWindow.getContentManager().getContents();
        System.out.println(toolWindow.getTitle());
        for(Content content: contents){

            JComponent component = content.getComponent();
            this.component=component;
            addMouseListenerToComponent(component);
//            addFocusListenerToComponent(component);
            addInputMethodListenerToComponent(component);
            addKeyListenerToComponent(component);
        }
    }

    private void addMouseListenerToComponent(JComponent component){
        component.addMouseMotionListener(this.componentMouseMotionListener);
        for(Component component1 : component.getComponents()){
            component1.addMouseMotionListener(this.componentMouseMotionListener);
                if(component1 instanceof  JComponent) {
                    addMouseListenerToComponent((JComponent) component1);
                }
                else if(component1 instanceof JPanel){
                    JPanel jPanel = (JPanel) component1;
                }
                else {
                    System.out.println("Not JComponent/Jpanel"+component.getClass().getName());
                }
        }
    }


    private void addFocusListenerToComponent(JComponent component) {
        component.addFocusListener(this.componentFocusListener);
        for (Component component1 : component.getComponents()) {
            component1.addFocusListener(this.componentFocusListener);
            try{
                if(component1 instanceof JComponent)
                    addFocusListenerToComponent((JComponent) component1);
            }
            catch (ClassCastException e){
                continue;
            }

        }
    }

    private void addInputMethodListenerToComponent(JComponent component){
        component.addInputMethodListener(this.componentInputMethodListener);
        for(Component component1 : component.getComponents()){
            component1.addInputMethodListener(this.componentInputMethodListener);
            try {
                if(component1 instanceof  JComponent)
                addInputMethodListenerToComponent((JComponent) component1);
            }
            catch (ClassCastException e){
                continue;
            }
        }
    }
    private  void addKeyListenerToComponent(JComponent component){
        component.addKeyListener(this.componentKeyListener);
        for(Component component1 : component.getComponents()){
            component1.addKeyListener(this.componentKeyListener);
            if(component1 instanceof JComponent){
                addKeyListenerToComponent((JComponent) component1);
            }
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

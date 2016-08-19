package Listeners;

import com.intellij.openapi.wm.impl.ToolWindowImpl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by aarjay on 18/08/16.
 */
public class ComponentFocusListener implements FocusListener {



    private ToolWindowImpl toolWindow;


    public ComponentFocusListener(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
    }

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e
     */
    @Override
    public void focusGained(FocusEvent e) {
        System.out.println(e.toString());
        System.out.println(e.getComponent().toString());
        System.out.println(e.getComponent().getName());
    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e
     */
    @Override
    public void focusLost(FocusEvent e) {

        System.out.println(e.toString());
        System.out.println(e.getComponent().toString());
        System.out.println(e.getComponent().getName());
    }

}

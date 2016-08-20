package Listeners;

import com.intellij.openapi.wm.impl.ToolWindowImpl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import log.ActionLogger;

/**
 * Created by aarjay on 18/08/16.
 */
public class    ComponentFocusListener implements FocusListener {



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
    public void focusGained(FocusEvent e){
        ActionLogger.getInstance().logFocus(e,this.toolWindow,true);
    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e
     */
    @Override
    public void focusLost(FocusEvent e) {
        ActionLogger.getInstance().logFocus(e,this.toolWindow,false);
    }
}

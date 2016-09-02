package Listeners;

import com.intellij.openapi.wm.impl.ToolWindowImpl;

import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

/**
 * Created by aarjay on 19/08/16.
 */
public class ComponentInputMethodListener implements InputMethodListener {
    /**
     * Invoked when the text entered through an input method has changed.
     *
     * @param event
     */

    ToolWindowImpl toolWindow;

    public ComponentInputMethodListener(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
    }

    @Override
    public void inputMethodTextChanged(InputMethodEvent event) {
        System.out.println("input-tc-"+toolWindow.getId()+"-"+event.toString());
    }

    /**
     * Invoked when the caret within composed text has changed.
     *
     * @param event
     */
    @Override
    public void caretPositionChanged(InputMethodEvent event) {
        System.out.println("caret-pc-"+toolWindow.getId()+"-"+event.toString());
    }
}

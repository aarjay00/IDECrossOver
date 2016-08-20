package Listeners;

import com.intellij.openapi.wm.impl.ToolWindowImpl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by aarjay on 19/08/16.
 */
public class ComponentKeyListener implements KeyListener {
    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */

    ToolWindowImpl toolWindow;

    public ComponentKeyListener(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped-"+toolWindow.getId());
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed-"+toolWindow.getId());

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased-"+e.paramString());

    }
}

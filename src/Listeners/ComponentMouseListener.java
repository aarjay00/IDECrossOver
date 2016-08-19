package Listeners;

import com.intellij.openapi.wm.impl.ToolWindowImpl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by aarjay on 19/08/16.
 */
public class ComponentMouseListener implements MouseMotionListener {


    ToolWindowImpl toolWindow;

    public ComponentMouseListener(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(toolWindow.getId());
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("moved");
    }
}

package Listeners;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import log.ActionLogger;

/**
 * Created by aarjay on 13/08/16.
 */
public class EditorListener implements EditorMouseListener {
    /**
     * Called when a mouse button is pressed over the editor.
     * <p/>
     * <b>Note:</b> this callback is assumed to be at the very start of 'mouse press' processing, i.e. common actions
     * like 'caret position change', 'selection change' etc implied by the 'mouse press' have not been performed yet.
     *
     * @param e the event containing information about the mouse press.
     */

    private static EditorListener editorListener =null;

    public static EditorListener getInstance(){
        if(editorListener==null) {
            editorListener = new EditorListener();
        }
        return editorListener;
    }


    @Override
    public void mousePressed(EditorMouseEvent e) {/*
        System.out.println("1");
        ActionLogger.getInstance().logEditorMouseEvent(e);*/
    }

    /**
     * Called when a mouse button is clicked over the editor.
     *
     * @param e the event containing information about the mouse click.
     */
    @Override
    public void mouseClicked(EditorMouseEvent e) {
     /*   System.out.println("2");
        ActionLogger.getInstance().logEditorMouseEvent(e);*/
    }

    /**
     * Called when a mouse button is released over the editor.
     *
     * @param e the event containing information about the mouse release.
     */
    @Override
    public void mouseReleased(EditorMouseEvent e) {/*
        System.out.println("3");
        ActionLogger.getInstance().logEditorMouseEvent(e);*/
    }

    /**
     * Called when the mouse enters the editor.
     *
     * @param e the event containing information about the mouse movement.
     */
    @Override
    public void mouseEntered(EditorMouseEvent e) {
//        System.out.println("4");
            ActionLogger.getInstance().logEditorMouseEvent(e);
         }

    /**
     * Called when the mouse exits the editor.
     *
     * @param e the event containing information about the mouse movement.
     */
    @Override
    public void mouseExited(EditorMouseEvent e) {
//        System.out.println("5");
        ActionLogger.getInstance().logEditorMouseEvent(e);
    }
}

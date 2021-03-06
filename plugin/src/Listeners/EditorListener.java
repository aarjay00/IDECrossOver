package Listeners;

import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.editor.event.EditorMouseMotionListener;
import log.ActionLogger;

/**
 * Created by aarjay on 13/08/16.
 */
public class EditorListener implements EditorMouseListener,EditorMouseMotionListener {
    /**
     * Called when a mouse button is pressed over the editor.
     * <p/>
     * <b>Note:</b> this callback is assumed to be at the very start of 'mouse press' processing, i.e. common actions
     * like 'caret position change', 'selection change' etc implied by the 'mouse press' have not been performed yet.
     *
     * @param e the event containing information about the mouse press.
     */

    private static EditorListener editorListener =null;

    private static Integer editorMoveCount =0 ;

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
        editorMoveCount=0;
//        System.out.println("4");
            ActionLogger.getInstance().logEditorMouseEvent(e,editorMoveCount);
         }

    /**
     * Called when the mouse exits the editor.
     *
     * @param e the event containing information about the mouse movement.
     */
    @Override
    public void mouseExited(EditorMouseEvent e) {
//        System.out.println("5");
        ActionLogger.getInstance().logEditorMouseEvent(e,editorMoveCount);
        editorMoveCount=0;
    }

    /**
     * Called when the mouse is moved over the editor and no mouse buttons are pressed.
     *
     * @param e the event containing information about the mouse movement.
     */
    @Override
    public void mouseMoved(EditorMouseEvent e) {
        editorMoveCount+=1;
    }

    /**
     * Called when the mouse is moved over the editor and a mouse button is pressed.
     *
     * @param e the event containing information about the mouse movement.
     */
    @Override
    public void mouseDragged(EditorMouseEvent e) {
        editorMoveCount+=1;
    }
}

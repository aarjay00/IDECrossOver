package Listeners;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import org.jetbrains.annotations.NotNull;

/**
 * Created by aarjay on 16/08/16.
 */
public class EditorFactoryHook implements EditorFactoryListener {
    /**
     * Called after {@link Editor} instance has been created.
     *
     * @param event
     */

    private static EditorFactoryHook editorFactoryHook = null;

    public static EditorFactoryHook getInstance(){
        if(editorFactoryHook ==null) {
            editorFactoryHook= new EditorFactoryHook();
        }
        return editorFactoryHook;
    }

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {

    }

    /**
     * Called before {@link Editor} instance will be released.
     *
     * @param event
     */
    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {

    }
}

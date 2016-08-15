package Listeners;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;
import log.ActionLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by aarjay on 13/08/16.
 */
public class EditorManagerListener implements FileEditorManagerListener {

    private static EditorManagerListener editorManagerListener = null;

    public static EditorManagerListener getInstance()
    {
        if(editorManagerListener==null){
            editorManagerListener = new EditorManagerListener();
        }
        return editorManagerListener;
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

        FileDocumentManager fileDocumentManager  = FileDocumentManager.getInstance();
        Document document = fileDocumentManager.getDocument(file);

        document.addDocumentListener(DocListener.getInstance());
        ActionLogger.getInstance().logFileOpenClose(file,true);

        Editor[] editorList = EditorFactory.getInstance().getEditors(document);
//        FileEditor[] editorList= source.getAllEditors(file);
        for(Editor editor : editorList) {
            editor.addEditorMouseListener(EditorListener.getInstance());
        }
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

        FileDocumentManager fileDocumentManager  = FileDocumentManager.getInstance();
        Document document = fileDocumentManager.getDocument(file);

        document.removeDocumentListener(DocListener.getInstance());

        ActionLogger.getInstance().logFileOpenClose(file,false);

        Editor[] editorList = EditorFactory.getInstance().getEditors(document);
        for(Editor editor : editorList) {
            editor.removeEditorMouseListener(EditorListener.getInstance());
        }

    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {

    }
}

package Listeners;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;
import log.ActionLogger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aarjay on 13/08/16.
 */
public class EditorManagerListener implements FileEditorManagerListener {

    private static EditorManagerListener editorManagerListener = null;

    private static List<Document> registered_documents  = new ArrayList<Document>();

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
        try {
            addDocumentListener(document);
        }
        catch (Throwable e){

        }
        ActionLogger.getInstance().logFileOpenClose(file,true);

        Editor[] editorList = EditorFactory.getInstance().getEditors(document);
//        FileEditor[] editorList= source.getAllEditors(file);
        for(Editor editor : editorList) {
            editor.addEditorMouseListener(EditorListener.getInstance());
            editor.addEditorMouseMotionListener(EditorListener.getInstance());
        }
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

        FileDocumentManager fileDocumentManager  = FileDocumentManager.getInstance();
        Document document = fileDocumentManager.getDocument(file);

        try {
            removeDocumentListener(document);
        }
        catch (Throwable e){
            return;
        }

        ActionLogger.getInstance().logFileOpenClose(file,false);

        Editor[] editorList = EditorFactory.getInstance().getEditors(document);
        for(Editor editor : editorList) {
            editor.removeEditorMouseListener(EditorListener.getInstance());
        }

    }
    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
            ActionLogger.getInstance().logFileEditorChange(event.getNewFile());
        }
    private void addDocumentListener(Document document){
        if(registered_documents.contains(document))
            return;
        registered_documents.add(document);
        document.addDocumentListener(DocListener.getInstance());
    }
    private void removeDocumentListener(Document document){
        if(!registered_documents.contains(document))
            return;
        document.removeDocumentListener(DocListener.getInstance());
        registered_documents.remove(document);
    }
}
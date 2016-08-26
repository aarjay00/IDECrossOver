package Listeners;

import com.intellij.openapi.editor.event.DocumentEvent;

import com.intellij.openapi.editor.event.DocumentListener;
import log.ActionLogger;

/**
 * Created by aarjay on 15/08/16.
 */
public class DocListener implements DocumentListener {

    private static DocListener docListener = null;

    public static DocListener getInstance(){
        if(docListener==null) {
            docListener =  new DocListener();
        }
        return docListener;
    }

    /**
     * Called before the text of the document is changed.
     *
     * @param event the event containing the information about the change.
     */
    @Override
    public void beforeDocumentChange(DocumentEvent event) {

    }

    /**
     * Called after the text of the document has been changed.
     *
     * @param event the event containing the information about the change.
     */
    @Override
    public void documentChanged(DocumentEvent event) {

        ActionLogger.getInstance().logDocumentEvent(event);
    }
}

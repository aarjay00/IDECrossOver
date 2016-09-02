package Listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Created by aarjay on 15/08/16.
 */
public class FileEditorListener implements PropertyChangeListener {


    private static FileEditorListener propertyChangeListener = null;

    public static FileEditorListener getInstance(){
        if(propertyChangeListener==null){
            propertyChangeListener = new FileEditorListener();
        }
        return propertyChangeListener;
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

package Listeners;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.impl.SystemDock;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.ContentManagerListener;
import com.intellij.ui.content.impl.ContentImpl;

import javax.swing.*;
import java.awt.event.FocusListener;

/**
 * Created by aarjay on 18/08/16.
 */
public class ContentManagerHook implements ContentManagerListener {

    private ToolWindowImpl toolWindow;


    public ContentManagerHook(ToolWindowImpl toolWindow) {
        this.toolWindow = toolWindow;
    }

    @Override
    public void contentAdded(ContentManagerEvent contentManagerEvent) {
        Content []  contents = (Content[]) toolWindow.getContentManager().getContents();
        System.out.println(toolWindow.getTitle());
        for(Content content: contents){

            JComponent componentFocus = content.getPreferredFocusableComponent();
            JComponent component = content.getComponent();
            JComponent componentAction =  content.getActionsContextComponent();
            JComponent jComponentSearch = content.getSearchComponent();
            component.addFocusListener(ComponentFocusListener.getInstance());
            System.out.println(content.getDisplayName());
//            System.out.println(component.getName());
//            System.out.println(component.toString());
            System.out.println(component.getClass().getName());
        }
    }

    @Override
    public void contentRemoved(ContentManagerEvent contentManagerEvent) {

    }

    @Override
    public void contentRemoveQuery(ContentManagerEvent contentManagerEvent) {

    }

    @Override
    public void selectionChanged(ContentManagerEvent contentManagerEvent) {

    }
}

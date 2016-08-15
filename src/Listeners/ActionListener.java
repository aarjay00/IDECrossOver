package Listeners;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import log.ActionLogger;

/**
 * Created by aarjay on 13/08/16.
 */
public class ActionListener implements AnActionListener {



    private static ActionListener IdeActionListener = null;

    public static AnActionListener getInstance() {
        if(IdeActionListener ==null)
        {
            IdeActionListener = new ActionListener();
        }
        return IdeActionListener;
    }

    @Override
    public void beforeActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {

        event.getData(PlatformDataKeys.FILE_EDITOR);

        System.out.println(action.getTemplatePresentation().getDescription());
        ActionLogger.getInstance().logAction(action,dataContext,event);
    }

    /**
     * Note that using <code>dataContext</code> in implementing methods is unsafe - it could have been invalidated by the performed action.
     *
     * @param action
     * @param dataContext
     * @param event
     */
    @Override
    public void afterActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {

    }

    @Override
    public void beforeEditorTyping(char c, DataContext dataContext) {

    }
}

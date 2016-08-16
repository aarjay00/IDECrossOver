package Listeners;

import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Created by aarjay on 15/08/16.
 */
public class ToolWindowListener implements ToolWindowManagerListener {
    /**
     * Invoked when tool window with specified <code>id</code> is registered in {@link ToolWindowManagerEx}.
     *
     * @param id <code>id</code> of registered tool window.
     */
    @Override
    public void toolWindowRegistered(@NotNull String id) {

    }

    @Override
    public void stateChanged() {

    }
}

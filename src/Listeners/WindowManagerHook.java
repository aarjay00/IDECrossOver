package Listeners;

import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.WindowManagerListener;

/**
 * Created by aarjay on 16/08/16.
 */
public class WindowManagerHook implements WindowManagerListener {

    private static WindowManagerHook windowManagerHook = null;

    public static WindowManagerHook getInstance(){
        if(windowManagerHook==null){
            windowManagerHook = new WindowManagerHook();
        }
        return windowManagerHook;
    }

    @Override
    public void frameCreated(IdeFrame frame) {

    }

    @Override
    public void beforeFrameReleased(IdeFrame frame) {

    }
}

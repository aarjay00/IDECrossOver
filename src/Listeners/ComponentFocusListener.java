package Listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by aarjay on 18/08/16.
 */
public class ComponentFocusListener implements FocusListener {


    private static  ComponentFocusListener componentFocusListener  = null;

    public static ComponentFocusListener getInstance(){
        if(componentFocusListener==null){
            componentFocusListener = new ComponentFocusListener();
        }
        return componentFocusListener;
    }


    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e
     */
    @Override
    public void focusGained(FocusEvent e) {
        System.out.println(e.toString());
        System.out.println(e.getComponent().toString());
        System.out.println(e.getComponent().getName());
    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e
     */
    @Override
    public void focusLost(FocusEvent e) {

        System.out.println(e.toString());
        System.out.println(e.getComponent().toString());
        System.out.println(e.getComponent().getName());
    }

}

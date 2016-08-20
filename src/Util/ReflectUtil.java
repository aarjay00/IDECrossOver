package Util;

import Listeners.ContentManagerHook;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.impl.ContentManagerImpl;

import java.lang.reflect.Field;

/**
 * Created by aarjay on 19/08/16.
 */
public class ReflectUtil {


    private static ReflectUtil reflect = null;

    public static ReflectUtil getInstance(){
        if(reflect==null){
            reflect = new ReflectUtil();
        }
        return reflect;
    }

    public void getAllFieldValues(Object object,ToolWindowImpl toolWindow){
        for(Field field : object.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if(value instanceof ContentManagerImpl){
                    ContentManagerImpl contentManager = (ContentManagerImpl)value;
                    contentManager.addContentManagerListener(new ContentManagerHook(toolWindow));
                }
//                System.out.println(field.getName()+"="+value.getClass().getName());
            }
            catch (Exception e){
                System.out.println("err");
            }
        }
    }
}

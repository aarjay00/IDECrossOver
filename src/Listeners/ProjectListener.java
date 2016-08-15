package Listeners;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aarjay on 13/08/16.
 */
public class ProjectListener implements ProjectManagerListener {

    private static ProjectManagerListener projectListener = null;

    private List<Project> projectList = null;

    public static ProjectManagerListener getInstance()
    {
        if(projectListener==null){
            projectListener =  new ProjectListener();
        }
        return projectListener;
    }

    /**
     * Invoked on project open.
     *
     * @param project opening project
     */
    @Override
    public void projectOpened(Project project) {
        projectList.add(project);
    }

    /**
     * Checks whether the project can be closed.
     *
     * @param project project to check
     * @return true or false
     */
    @Override
    public boolean canCloseProject(Project project) {
        return false;
    }

    /**
     * Invoked on project close.
     *
     * @param project closing project
     */
    @Override
    public void projectClosed(Project project) {
        project=null;
    }

    /**
     * Invoked on project close before any closing activities
     *
     * @param project
     */
    @Override
    public void projectClosing(Project project) {
        projectList.remove(project);
    }
}

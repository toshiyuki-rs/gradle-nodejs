package net.ocsoft.nodejs

import org.gradle.api.Project
import java.lang.ref.WeakReference

/**
 * resolve path, executable or directory
 */
class Resolver {
    
    /**
     * project reference
     */
    private WeakReference<Project> projectRef
    
    /**
     * constructor
     */
    Resolver(Project project) {
        projectRef = new WeakReference<Project>(project)
    }

    /**
     * get project
     */
    private Project getProject() {
        return projectRef.get()
    }
    

    /**
     * resolve node_module parent drectory
     */
    File resolveNodeModule() {
        return NodeModules.findModuleDir(project)
    }

    /**
     * init node module director if is not present.
     */
    File initNodeModuleIfNot() {
        return NodeModules.installIfNot(project)
    }
     
}

// vi: se ts=4 sw=4 et:

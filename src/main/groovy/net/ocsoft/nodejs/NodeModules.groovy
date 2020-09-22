package net.ocsoft.nodejs

import org.gradle.api.Project

public class NodeModules {

    /**
     * create directory list
     */
    static List<File> createDirectoryEntries(Project project) {
        def result = new ArrayList<File>()
        def proj = project
        while (true) {
            result.add(proj.projectDir)
            if (project.rootProject == proj) {
                break
            }
            proj = proj.parent
        } 
        return result
    }
    
    /**
     * find module director
     */ 
    static File findModuleDir(Project project, Closure<File> closure = null) {
        def directories = createDirectoryEntries(project)
        def settings = project.extensions.findByType(Settings.class) 
        if (settings.moduleDirectory != null) {
            directories.add(0, settings.moduleDirectory)
        }
        
        File result = null
        for (dir in directories) {
            def modDir = new File(dir, "node_modules")
            if (modDir.directory) {
                if (closure(modDir)) {
                    result = modDir
                    break
                }
            }
        }
        return result
    }

    /**
     * install node_modules
     */
    static void installIfNot(Project project, File moduleParent) {
             
        def nodeModules
        if (moduleParent != null) {
            nodeModules = new File(moduleParent, "node_modules")
        } else {
            nodeModules = new File(project.projectDir, "node_modules") 
        }
        if (!nodeModules.exists()) {
            PackageJson.installIfNot(project, moduleParent)
        }
         
    }
}

// vi: se ts=4 sw=4 et:

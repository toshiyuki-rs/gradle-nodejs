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
     * install node_module or package.json
     */
    static File installIfNot(Project project) {
        def setting = project.extensions.findByType(Settings.class)
        def dir = setting.moduleDirectory
        def result = installIfNot(project, dir)
        return result
    }
 
    /**
     * install node_modules
     */
    static File installIfNot(Project project, File moduleParent) {
             
        def nodeModules
        if (moduleParent != null) {
            nodeModules = new File(moduleParent, "node_modules")
        } else {
            nodeModules = new File(project.projectDir, "node_modules") 
        }
        File result = null
        if (!nodeModules.exists()) {
            def jsonPath = PackageJson.installIfNot(project, moduleParent)
            if (jsonPath != null) {
                result = jsonPath.parentFile
            } 
        } else {
            if (nodeModules.directory) {
                result = nodeModules.parentFile
            }
        }
        return result 
    }
}

// vi: se ts=4 sw=4 et:

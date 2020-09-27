package net.ocsoft.nodejs

import org.gradle.api.Project
import java.lang.ref.WeakReference
import org.gradle.api.DefaultTask
import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

public class NodeModules extends DefaultTask {

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
                if (closure != null && closure(modDir)) {
                    result = modDir
                    break
                } else {
                    result = modDir
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

    /**
     * node modules
     */
    private List<Object> modules

    
    /**
     * constructor
     */
    NodeModules() {
        modules = []
    }

   
    /**
     * register instal module
     */
    void install(String module) {
        modules += module
    }

    /**
     * register install module
     */
    void install(File module) {
        modules += module
    }

    /**
     * run tasks
     */
    @TaskAction
    void run() {
        installAll()
    }

    /**
     * do install all npm modules
     */
    private void installAll() {
        def project = this.project
        if (project != null) { 
            def resolver = project.extensions.nodejsResolver
            def settings = project.extensions.nodejsSettings

    
            def moduleDir = resolver.resolveNodeModule()
            def moduleParent = null
            if (moduleDir == null) {
                if (settings.installNodeModules) {
                    moduleParent = resolver.initNodeModuleIfNot()
                }
            } else {
                moduleParent = moduleDir.parentFile
            }

            if (moduleParent != null) { 
                modules.forEach {
                    def module = it
                
                    def res = project.exec {
                        executable = "npm"
                        args "install", "${module}"
                        workingDir = moduleParent
                    } 
                } 
            }
        }
    }
}

// vi: se ts=4 sw=4 et:

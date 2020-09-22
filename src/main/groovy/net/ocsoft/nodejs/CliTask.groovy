package net.ocsoft.nodejs

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.Project

/**
 * command line task
 */
class CliTask extends AbstractExecTask<CliTask> {

    /**
     * create command task
     */
    static File resolveExec(String module, String cmd, Project project) {

        def result = CliResolver.resolve(cmd, project) 
        if (result == null) {
            def setting = project.extensions.findByType(Settings.class)
            if (setting.installNodeModules) {
                def dir = setting.moduleDirectory
                NodeModules.installIfNot(project, dir)
                Npm.install(project, module, dir) 
                result = CliResolver.resolve(cmd, project)
            }
        }
        return result
    }

    /**
     * constructor
     */
    CliTask() {
        super(CliTask)
    } 

    void setupArgs() {
        def settings = project.extensions.findByName('nodejsCliSettings') 
        
        def cliSettings = settings.findByName(name) 
        if (cliSettings != null
            && cliSettings.args != null) {
            args = cliSettings.args
        }
    }
    
    protected void exec() {
        setupArgs() 
        super.exec()
    }
}


// vi: se ts=4 sw=4 et:

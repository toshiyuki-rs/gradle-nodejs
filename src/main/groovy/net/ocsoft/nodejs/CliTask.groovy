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
            def moduleParent = NodeModules.installIfNot(project)
            def status = Npm.install(project, module, moduleParent) 
            result = CliResolver.resolve(cmd, project)
        }
        return result
    }

    /**
     * register task if the name task is not exists.
     */
    static boolean registerTaskIfNot(Project project, String taskName) {
        def prefix = "${Constants.CLI_PREFIX}_"
        def pos = taskName.indexOf(Constants.CLI_PREFIX) 
        
        def result = false
        if (pos == 0) {
            def moduleCommandName = taskName.substring(prefix.length()) 
            def elems  = moduleCommandName.split("_")
            String[] moduleCommand
            if (elems.length > 1) {
                moduleCommand = [
                    elems[0],
                    elems[1]
                ]
            } else {
                moduleCommand = [
                    elems[0], 
                    elems[0]
                ]
            }
            def task = project.tasks.findByPath(taskName) 
            if (task == null) {
                result = registerTask(project, moduleCommand, taskName)
            }
        } 
        return result
    }

    /**
     * register task
     */
    static boolean registerTask(Project project,
        String[] moduleCommand,  String taskName) {
        def rootSettings = project.extensions.findByType(Settings.class)
        def cliSetting = rootSettings.findCliSetting(taskName)
        def moduleName
        if (cliSetting != null && cliSetting.repository != null) {
            moduleName = cliSetting.reposity 
        } else {
            moduleName = moduleCommand[0]
        }
        def exec = resolveExec(
            moduleName, moduleCommand[1], 
            project)
        def result = false
        if (exec != null) {
            project.tasks.create(taskName, CliTask) {
                executable = exec
            } 
            result = true
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
        def settings = project.extensions.findByType(Settings.class)
        def cliSettings = settings.cliSettings  
        def cliSetting = cliSettings.findByName(name) 
        if (cliSetting != null
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

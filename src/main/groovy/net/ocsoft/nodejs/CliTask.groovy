package net.ocsoft.nodejs

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.Project
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile

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
        def pos = taskName.indexOf(prefix) 
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
        def result = false
        project.tasks.create(taskName, CliTask)
        result = true
        return result
    }

    /**
     * node module repository
     */
    @Optional
    @InputFile
    File repository 

    /**
     * constructor
     */
    CliTask() {
        super(CliTask)
    } 

    void setupExecutable() {

        if (executable == null) {
            def taskName = name
            def moduleName
            def prefix = "${Constants.CLI_PREFIX}_"
            
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
     
            if (repository != null) {
                moduleName = repository
            } else {
                moduleName = moduleCommand[0]
            }
            
            def exec = resolveExec(
                moduleName, moduleCommand[1], 
                project)
            if (exec != null) {
                executable = exec
            } else {
                def message = "${taskName} is aborted for "
                message += "not found ${moduleCommand[1]} in ${moduleName}."
                println message 
                throw new StopExecutionException(message)
            }
        }
    }

    
    protected void exec() {
        setupExecutable()
        super.exec()
    }
}


// vi: se ts=4 sw=4 et:

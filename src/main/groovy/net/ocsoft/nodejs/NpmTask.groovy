package net.ocsoft.nodejs

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.Internal

/**
 * npm task
 */
class NpmTask extends AbstractExecTask<NpmTask> {

    /**
     * register task if task name is not registered
     */
    static boolean registerTaskIfNot(Project project, String taskName) {
        def prefix = "${Constants.NPM_PREFIX}_"
        def pos = taskName.indexOf(prefix) 
        def result = false 
        if (pos == 0) {
            def task = project.tasks.findByPath(taskName) 
            if (task == null) {
                project.tasks.create(taskName, NpmTask)
                result = true
            }
        }
        return result
    }

    {
        executable = 'npm'
    }

    /**
     * script
     */
    @Optional
    @Input
    private String command 


    /**
     * constructor
     */
    NpmTask() {
        super(NpmTask)
    } 

    /**
     * get command
     */
    public String getCommand() {
        return command
    }
    /**
     * set command
     */
    public void setCommand(String command) {
        this.command = command
    }

    /**
     * you get true if task is having script
     */
    @Internal
    String getCommandForNpm() {

        def result
        if (command == null) { 
            def prefix = "${Constants.NPM_PREFIX}_"
            def pos = name.indexOf(prefix)
            commandAndId = name.substring(pos).split('_')
            result = commandAndId[0]
        } else {
            result = command
        }

        return result
    }

    /**
     * setup argument
     */
    void setupArgs(def originalArgs) {
        args commandForNpm 
        args originalArgs 
    }
    /**
     * execute command
     */ 
    protected void exec() {
        def savedArgs = args
        args = []
        setupArgs(savedArgs) 
        super.exec()
        args = savedArgs
    }
}


// vi: se ts=4 sw=4 et:

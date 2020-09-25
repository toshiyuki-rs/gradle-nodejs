package net.ocsoft.nodejs

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.Project

/**
 * command line task
 */
class NodeTask extends AbstractExecTask<NodeTask> {

    /**
     * register task if task name is not registered
     */
    static boolean registerTaskIfNot(Project project, String taskName) {
        def prefix = "${Constants.NODE_PREFIX}_"
        def pos = taskName.indexOf(Constants.NODE_PREFIX) 
        def result = false 
        if (pos == 0) {
            def task = project.tasks.findByPath(taskName) 
            if (task == null) {
                result = project.tasks.create(taskName, NodeTask)
            }
        }
        return result
    }

    {
        executable = 'node'
    }

    public String nodeScript

    /**
     * constructor
     */
    NodeTask() {
        super(NodeTask)
    } 

    void setupArgs() {
        def settings = project.extensions.findByType(Settings.class)
        def nodeSettings = settings.nodeSettings  
        def nodeSetting = nodeSettings.findByName(name) 
        if (nodeSetting != null) {
            args nodeSettings.nodeArgsForCommand
 
            if (nodeSetting.havingScript) {
                def script = nodeSetting.nodeScript                
                standardInput = new ByteArrayInputStream(
                     script.getBytes('UTF-8'))
            } else {
                if (scriptPath != null) {
                    args nodeSetting.scriptPath
                }
            }
            args nodeSetting.args 
        }
    }
    
    protected void exec() {
        setupArgs() 
        super.exec()
    }
}


// vi: se ts=4 sw=4 et:

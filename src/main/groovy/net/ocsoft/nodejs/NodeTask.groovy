package net.ocsoft.nodejs

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.Internal

/**
 * command line task
 */
class NodeTask extends AbstractExecTask<NodeTask> {

    /**
     * register task if task name is not registered
     */
    static boolean registerTaskIfNot(Project project, String taskName) {
        def prefix = "${Constants.NODE_PREFIX}_"
        def pos = taskName.indexOf(prefix) 
        def result = false 
        if (pos == 0) {
            def task = project.tasks.findByPath(taskName) 
            if (task == null) {
                project.tasks.create(taskName, NodeTask)
                result = true
            }
        }
        return result
    }

    {
        executable = 'node'
    }

    /**
     * script
     */

    @Optional
    @InputFile
    private File scriptPath

    /**
     *  script
     */
    @Optional
    @Input 
    String nodeScript

    /**
     * options for node
     */
    @Input
    private List<String> nodeArgs


    /**
     * constructor
     */
    NodeTask() {
        super(NodeTask)
        this.nodeArgs = []
    } 

    /**
     * get argments for command 
     */
    public List<String> getNodeArgs() {
        return this.nodeArgs
    }

    /**
     * set argments for command
     */
    public void setNodeArgs(Collection<String> args) {
        this.nodeArgs.clear()
        this.nodeArgs.addAll(args) 
    }

    /**
     * adds arguments for command
     */
    public nodeArgs(String... args) {
        this.nodeArgs.addAll(this.nodeArgs.size(), args)
        return this 
    }
    /**
     * adds argumets for command
     */
    public nodeArgs(Iterable<?> args) {
        def thisArgs = this.nodeArgs
        args.forEach {
            thisArgs.add it
        } 
        return this
    }

    /**
     * you get true if task is having script
     */
    @Internal
    boolean isHavingScript() {
        return this.scriptPath == null && this.nodeScript != null
    }

    /**
     * node argument array for executable
     */
    @Internal
    protected String[] getNodeArgsForCommand() {
        def options = []
        options += nodeArgs
        if (havingScript) {
            def stdInOpt = nodeArgs.find { '-'.equals(it) } 
            if (stdInOpt == null) {
                options += '-'       
            }
        }
        def result = options
        return result 
    }

    /**
     * script path
     */
    File getScriptPath() {
        return this.scriptPath
    }

    /**
     * script path
     */
    void setScriptPath(File scriptPath) {
        this.scriptPath = scriptPath
    }

 
    /**
     * setup argument
     */
    void setupArgs(def originalArgs) {
        args nodeArgsForCommand
        if (havingScript) {
            def script = nodeScript                
            standardInput = new ByteArrayInputStream(
                 script.getBytes('UTF-8'))
        } else {
            if (scriptPath != null) {
                args scriptPath
            }
        }
        args originalArgs 
    }
    
    protected void exec() {
        def savedArgs = args
        args = []
        setupArgs(savedArgs) 
        super.exec()
        args = savedArgs
    }
}


// vi: se ts=4 sw=4 et:

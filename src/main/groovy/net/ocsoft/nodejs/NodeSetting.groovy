package net.ocsoft.nodejs


import org.gradle.api.provider.ListProperty
import org.gradle.api.model.ObjectFactory

public class NodeSetting {
    
    /**
     * name
     */
    private String name

    /**
     * working directory 
     */
    public File workingDirectory 


    /**
     * script
     */
    public File scriptPath

    /**
     *  script
     */
    private String nodeScript

    /**
     * options for node
     */
    private List<String> nodeArgs

    /**
     * command line argument
     */
    private List<String> args


    /**
     * constructor
     */
    NodeSetting(String name) {
        this.name = name
        this.args = new ArrayList<String>()
        this.nodeArgs = new ArrayList<String>()
    }

    /**
     * get name
     */
    public getName() {
        return name
    }

    /**
     * get argments for command 
     */
    public List<String> getArgs() {
        return this.args
    }

    /**
     * set argments for command
     */
    public void setArgs(Collection<String> args) {
        this.args.clear()
        this.args.addAll(args) 
    }

    /**
     * adds arguments for command
     */
    public args(String... args) {
        this.args.addAll(this.args.size(), args)
        return this 
    }
    /**
     * adds argumets for command
     */
    public args(Iterable<?> args) {
        def thisArgs = this.args
        args.forEach {
            thisArgs.add it
        } 
        return this
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

    boolean isHavingScript() {
        return this.scriptPath == null && this.nodeScript != null
    }

    String[] getNodeArgsForCommand() {
        String[] options = []
        options.addAll(nodeArgs)
        if (havingScript) {
            def stdInOpt = nodeArgs.find { '-'.equals(it) } 
            if (stdInOpt == null) {
                options.add('-')        
            }
        }
        return result
    }

    public String getNodeScript() {
        return nodeScript
    }
    public void setNodeScript(String nodeScript) {
        this.nodeScript = nodeScript
    }
    
}

// vi: se ts=4 sw=4 et:

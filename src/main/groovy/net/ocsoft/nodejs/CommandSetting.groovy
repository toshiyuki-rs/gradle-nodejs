package net.ocsoft.nodejs


import org.gradle.api.provider.ListProperty
import org.gradle.api.model.ObjectFactory

public class CommandSetting {
    
    /**
     * name
     */
    private String name

    /**
     * repository
     */
    public String repository

    /**
     * command line argument
     */
    private List<String> args


    /**
     * constructor
     */
    CommandSetting(String name) {
        this.name = name
        // this.args = new ArrayList<String>()
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
    public CommandSetting args(String... args) {
        this.args.addAll(this.args.size(), args)
        return this 
    }
    /**
     * adds argumets for command
     */
    public CommandSetting args(Iterable<?> args) {
        def thisArgs = this.args
        args.forEach {
            thisArgs.add it
        } 
        return this
    }
}

// vi: se ts=4 sw=4 et:

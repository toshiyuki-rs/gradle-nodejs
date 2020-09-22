package net.ocsoft.nodejs


import org.gradle.api.provider.ListProperty
import org.gradle.api.model.ObjectFactory

public class CliSetting {
    
    /**
     * name
     */
    private String name

    /**
     * command line argument
     */
    private List<String> args


    /**
     * constructor
     */
    CliSetting(String name) {
        this.name = name
        this.args = new ArrayList<String>()
    }

    /**
     * get name
     */
    public getName() {
        return name
    }

    public List<String> getArgs() {
        return this.args
    }

    public void setArgs(Collection<String> args) {
        this.args.clear()
        this.args.addAll(args) 
    }

    public args(String... args) {
        this.args.addAll(this.args.size(), args)
        return this 
    }
    public args(Iterable<?> args) {
        def thisArgs = this.args
        args.forEach {
            thisArgs.add it
        } 
        return this
    }
}

// vi: se ts=4 sw=4 et:

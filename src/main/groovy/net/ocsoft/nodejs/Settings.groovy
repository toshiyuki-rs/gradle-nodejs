package net.ocsoft.nodejs

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.model.ObjectFactory

/**
 * plugin settings
 */
class Settings {

    /**
     * install package.json and node_modules if the plugin could not find.
     */
    public boolean installNodeModules = false 

    /**
     * node_moudles parent directory
     */
    public File moduleDirectory

    /**
     * cli settings
     */
    NamedDomainObjectContainer<CommandSetting> cliSettings
    
    /**
     * node settings
     */
    NamedDomainObjectContainer<NodeSetting> nodeSettings
    


    /**
     * constructor
     */
    Settings(ObjectFactory objectFactory) {
        cliSettings = objectFactory.domainObjectContainer(CommandSetting.class)
        nodeSettings = objectFactory.domainObjectContainer(NodeSetting.class)
        setupCliSettings()
        setupNodeSettings()
    }

    void setupCliSettings() {
        cliSettings.all {
            def taskName = it.name
            CliTask.registerTaskIfNot(project, taskName) 
        }
    } 
    
    void setupNodeSettings() {
        nodeSettings.all {
            NodeTask.registerIfNot(project, it.name)
        } 
        
    }

    /**
     * cli setting
     */
    NamedDomainObjectContainer<CommandSetting> cliSettings(Closure closure) {
        def savedDelegate = closure.delegate
        closure.delegate = this.cliSettings
        closure()
        closure.delegate = savedDelegate
        return cliSettings
    }

    /**
     * cli setting
     */
    CommandSetting findCliSetting(String name) {
        return this.cliSettings.findByName(name)
    }

    /**
     * node setting
     */
    NamedDomainObjectContainer<NodeSetting> nodeSettings(Closure closure) {
        def savedDelegate = closure.delegate
        closure.delegate = this.nodeSettings
        closure()
        closure.delegate = savedDelegate
        return nodeSettings
    }

    /**
     * node setting
     */
    NodeSetting findNodeSetting(String name) {
        return this.nodeSettings.findByName(name)
    }
}

// vi: se ts=4 sw=4 et:

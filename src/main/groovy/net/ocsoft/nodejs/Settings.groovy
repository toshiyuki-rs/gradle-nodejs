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
     * constructor
     */
    Settings() {

    }

}

// vi: se ts=4 sw=4 et:

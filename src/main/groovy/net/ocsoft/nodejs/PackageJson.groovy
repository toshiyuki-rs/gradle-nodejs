package net.ocsoft.nodejs

import org.gradle.api.Project

public class PackageJson {

    /**
     * install node_modules
     */
    static void installIfNot(Project project, File moduleParent = null) {
        def packParent = moduleParent
        if (moduleParent == null) {
            packParent = project.projectDir
        }
        
        def packJson = new File(packParent, "package.json")
        if (!packJson.exists()) {
            project.exec {
                executable = 'npm'
                args = ['init', '-y']
                if (moduleParent != null) {
                    workingDir = moduleParent
                }
            }
        }
        
    }
}

// vi: se ts=4 sw=4 et:

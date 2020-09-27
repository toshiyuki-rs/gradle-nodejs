package net.ocsoft.nodejs

import org.gradle.api.Project

public class PackageJson {

    /**
     * install node_modules
     */
    static File installIfNot(Project project, File moduleParent = null) {
        def packParent = moduleParent
        if (moduleParent == null) {
            packParent = project.projectDir
        }
        
        File result = null
        def packJson = new File(packParent, "package.json")
        if (!packJson.exists()) {
            project.exec {
                executable = 'npm'
                args = ['init', '-y']
                if (packParent != null) {
                    workingDir = packParent
                }
            }
            if (packJson.exists()) {
                result = packJson
            }
        } else {
            if (packJson.file) {
                result = packJson
            }
        }
        return result 
    }
}

// vi: se ts=4 sw=4 et:

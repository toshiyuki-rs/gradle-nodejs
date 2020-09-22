package net.ocsoft.nodejs

import org.gradle.api.Project

class Npm {

    /**
     * install node package module
     */
    static boolean install(
        Project project, String module, File moduleParentDir) {
        def res = project.exec {
            executable = 'npm'
            args = ['install', module]
            if (moduleParentDir != null) {
                workingDir = moduleParentDir
            }
        }
        def result = res.exitValue == 0
        return result 
    }
}

// vi: se ts=4 sw=4 et:

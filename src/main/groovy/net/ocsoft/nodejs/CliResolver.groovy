package net.ocsoft.nodejs

import org.gradle.api.Project


public class CliResolver {



    /**
     * resolve less compiler
     */
    static File resolve(String command, Project project) {
        def proj = project
        File result = null
        NodeModules.findModuleDir(project) {
            def binDir = new File(it, ".bin")
            def cmdPath = new File(binDir, command)
            def res = false
            if (binDir.directory) {
                res = cmdPath.file
                if (res) {
                    result = cmdPath
                }
            }
            res
        }
        return result
    }

}

// vi: se ts=4 sw=4 et:

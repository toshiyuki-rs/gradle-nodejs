package net.ocsoft.nodejs

import spock.lang.Specification
import org.gradle.testfixtures.ProjectBuilder


public class CliTaskTest extends Specification {
    def "cli task resolve"() {
        def projectDir = new File("build/test/cli-task") 
        projectDir.mkdirs()
        when:
        def project = ProjectBuilder.builder().withProjectDir(
            projectDir).build()
        project.extensions.add(
            'nodejsSettings', new Settings()) 
        project.extensions.configure('nodejsSettings') {
            it.installNodeModules = true
        }
        def execPath = CliTask.resolveExec('less', 'lessc', project) 
        then:
        execPath.exists() 
    }
}

// vi: se ts=4 sw=4 et:

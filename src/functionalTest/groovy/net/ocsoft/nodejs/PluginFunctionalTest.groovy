/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package net.ocsoft.nodejs

import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'gradle.nodejs.greeting' plugin.
 */
public class PluginFunctionalTest extends Specification {
    def "is nodeCli task registered"() {
        given:
        def projectDir = new File("build/functionalTest/registration")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = """
    plugins {
        id('net.ocsoft.nodejs')
    }
"""

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("tasks")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("nodeCli_<Module>_<Command>_<Id>")
    }
    def "can run cli task"() {
        given:
        def projectDir = new File("build/functionalTest/task-run")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = '''
    plugins {
        id('net.ocsoft.nodejs')
    }
    nodejsSettings {
        installNodeModules = true
    }

    tasks.findByName('nodeCli_less_lessc_1').configure {
        args = ['--no-color', "${projectDir}/src/index.less",
            "${buildDir}/index.css"] 
    }
    
    task lessc {
        dependsOn nodeCli_less_lessc_1
    }
'''
        def srcDir = new File(projectDir, "src")
        srcDir.mkdirs()

        new File(srcDir, "index.less").text = """
@back-color: rgb(0, 0, 0);

body {
    background-color: @back-color
}
"""
        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments(["lessc"])
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        def destFile = new File(projectDir, 'build/index.css')
        destFile.text.contains("background-color")
    }

    def "can run node task 1"() {
        given:
        def projectDir = new File("build/functionalTest/node-run-1")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = '''
    plugins {
        id('net.ocsoft.nodejs')
    }
    tasks.findByName('node_scriptTest').configure {
        args = ["'gradle-message'"]
        nodeScript = "console.log(\\"got \\" + process.argv[2] +  \\"from build.gradle\\")"
    }
 
    task node_test_1 {
        dependsOn node_scriptTest
    }
'''
        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments(["node_test_1"])
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains('got \'gradle-message\'')
    }

     def "can run node task with depend"() {
        given:
        def projectDir = new File("build/functionalTest/node-run-2")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = '''
    plugins {
        id('net.ocsoft.nodejs')
    }
    nodejsSettings {
        installNodeModules = true
    }
    tasks.nodejsModules.configure {
        install 'yaml'
    }

    tasks.findByName('node_scriptTest').configure {
        nodeScript = ["const yaml = require('yaml')",
            "console.log(yaml.parse('yaml-gradle-test'))"].join(\"\\n\")
        dependsOn tasks.nodejsModules
    }
    task node_test_1 {
        dependsOn node_scriptTest
    }
'''
        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments(["node_test_1"])
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains('yaml-gradle-test')
    }

   

}
// vi: se ts=4 sw=4 et:

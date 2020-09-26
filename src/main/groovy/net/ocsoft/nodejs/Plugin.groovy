/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package net.ocsoft.nodejs

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.NamedDomainObjectFactory

/**
 * A simple 'hello world' plugin.
 */
public class Plugin implements org.gradle.api.Plugin<Project> {

    public void apply(Project project) {
        def settings = new Settings()
        project.extensions.add("nodejsSettings", settings)
        project.tasks.addRule(
            "${Constants.CLI_PREFIX}_<Module>_<Command>_<Id> Run node cli",
            createCliRuleHandler(project))
        project.tasks.addRule(
            "${Constants.NODE_PREFIX}_<Id> Run node command",
            createNodeRuleHandler(project))
    }

    /**
     * cli rule handler
     */
    Closure createCliRuleHandler(Project project) {
        return {
            CliTask.registerTaskIfNot(project, it)
        }
    }

    /**
     * node rule handler
     */
    Closure createNodeRuleHandler(Project project) {
        return {
            NodeTask.registerTaskIfNot(project, it)
        }
    }
}

// vi: se ts=4 sw=4 et:

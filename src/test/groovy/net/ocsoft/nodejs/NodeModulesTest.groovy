package net.ocsoft.nodejs

import spock.lang.Specification
import org.gradle.testfixtures.ProjectBuilder



class NodeModulesTest extends Specification {
    def "npm install"() {
        def projectDir = new File("build/test/npm-install") 
        projectDir.mkdirs()
        def packFile = new File(projectDir, "package.json")
        packFile.delete() 
        packFile = new File(projectDir, "package.json")
        packFile.delete() 
        def nodeMod = new File(projectDir, "node_modules")
        nodeMod.deleteDir()
 
        def buffer = new ByteArrayOutputStream()
        def savedoutput = System.out
        System.out = new PrintStream(buffer)
        when:
          
        def project = ProjectBuilder.builder().withProjectDir(
            projectDir).build()
        project.apply {
            plugin(Plugin.class)
        }
        project.extensions.nodejsSettings.installNodeModules = true
       
        project.extensions.nodejsModules.configure {
            install "yaml"
        }
        
        project.tasks.findByName('node_scriptTest').configure {
            dependsOn project.tasks.findByName('nodejsModules')
           
            nodeScript = '''
    const yaml = require('yaml')
    console.log(yaml.parse('hello world')) 
'''
        }
        project.tasks.nodejsModules.actions.forEach {
            it.execute project.tasks.nodejsModules
        }

         
        project.tasks.node_scriptTest.actions.forEach {
            it.execute project.tasks.node_scriptTest
        }
        then:
        buffer.toString().contains('hello world') 
    }
}
// vi: se ts=4 sw=4 et:

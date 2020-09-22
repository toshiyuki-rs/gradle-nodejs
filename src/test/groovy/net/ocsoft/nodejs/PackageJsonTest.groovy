package net.ocsoft.nodejs

import spock.lang.Specification
import org.gradle.testfixtures.ProjectBuilder


public class PackageJsonTest extends Specification {
    def "install package.json"() {
        def projectDir = new File("build/test/package-json") 
        projectDir.mkdirs()
        def packFile = new File(projectDir, "package.json")
        packFile.delete() 
        when:
          
        def project = ProjectBuilder.builder().withProjectDir(
            projectDir).build()
        println projectDir
        PackageJson.installIfNot(project) 
        then:
        packFile.exists() 
    }
}

// vi: se ts=4 sw=4 et:

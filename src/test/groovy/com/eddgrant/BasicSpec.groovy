package com.eddgrant

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.plugin.JenkinsJobManagement
import org.jvnet.hudson.test.JenkinsRule
import org.jvnet.hudson.test.junit.jupiter.WithJenkins
import spock.lang.Shared
import spock.lang.Specification

@WithJenkins
class BasicSpec extends Specification {

    @Shared
    JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

    def "Jenkins instance should exist"(final JenkinsRule jr) {
        expect:
        1 == 1
        jr.jenkins != null
    }

    def "it should process a DSL script"() {
        final String dslScript = """
final String myVariable = "This is a very contrived script which does nothing yet."
// More to come here...
"""
        when:
        new DslScriptLoader(jobManagement).runScript(dslScript)

        then:
        noExceptionThrown()
    }
}

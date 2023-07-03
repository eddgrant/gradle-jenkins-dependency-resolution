# Gradle Transitive Dependencies Question

This repository has been created as a reproduction case to support a Gradle forum question.

The crux of the question is:

> Why doesn't Gradle download transitive dependencies when a file extension e.g. `@jar` is specified on a dependency?

This is demonstrated as follows:

1. Clone this repository.
2. Run `./gradlew test`. The task will fail with a compilation error because `javaposse.jobdsl.dsl.JobManagement` is not available on the test compilation classpath.
3. Uncomment line `59` in `build.gradle`
4. Run `./gradlew test` again. This time compilation will succeed and the `BasicSpec` test will pass.
5. Help me understand why this happens?

## Hypothesis

1. `javaposse.jobdsl.dsl.JobManagement` resides in the `org.jenkins-ci.plugins:job-dsl-core` dependency, which is a transitive dependency of the `org.jenkins-ci.plugins:job-dsl` dependency.
2. The `packaging` element in the `org.jenkins-ci.plugins:job-dsl` pom.xml is set to `hpi`. If we declare this dependency as `testImplementation "org.jenkins-ci.plugins:job-dsl:${jobDslVersion}"` then Gradle will also download all of its transitive dependencies (which include `job-dsl-core`) and will add them to the relevant classpath.
3. However, I specifically want the `jar` artefact of `job-dsl`, as it contains classes that I want to use. When the `hpi` artefact gets pulled in those classes are unavailable to me.
4. I can obtain the `jar` artefact of `job-dsl` by specifying `@jar` as the extension. When I do this Gradle downloads the `jar` artefact, however it also then no longer downloads any of the transitive dependencies.
5. The only solution I can think of is to then explicitly depend on each of the, now absent, transitive dependencies. However this feels wrong and brittle.
6. Question: Is there a way I can tell Gradle to download the `job-dsl` `jar` artefact _and_ to also download all of its transitive dependencies?

Very grateful for any help on this one!

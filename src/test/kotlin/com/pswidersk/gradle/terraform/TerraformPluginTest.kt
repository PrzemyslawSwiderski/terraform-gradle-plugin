package com.pswidersk.gradle.terraform

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class TerraformPluginTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `test if main external plugins were successfully applied`() {
        val project: Project = ProjectBuilder.builder().build()
        project.pluginManager.apply(TerraformPlugin::class.java)

        assertEquals(1, project.plugins.size)
        assertEquals(1, project.tasks.size)
    }

    @Test
    fun `test if terraform setup and version check was successful`() {
        // given
        val expectedOutputMsg = "Terraform v0.12.28"
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText("""
            import com.pswidersk.gradle.terraform.TerraformTask
            
            plugins {
                id("com.pswidersk.terraform-plugin")
            }
            
            tasks {
                register<TerraformTask>("runTerraformVersionCheck") {
                    args("--version")
                }
            }
        """.trimIndent())
        val runner = GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(tempDir)
                .forwardOutput()
                .withArguments(":runTerraformVersionCheck")

        // when
        val firstRunResult = runner.build()
        val secondRunResult = runner.build()

        // then
        with(firstRunResult) {
            assertEquals(TaskOutcome.SUCCESS, task(":terraformSetup")!!.outcome)
            assertEquals(TaskOutcome.SUCCESS, task(":runTerraformVersionCheck")!!.outcome)
            Assertions.assertTrue { output.contains(expectedOutputMsg) }
        }
        with(secondRunResult) {
            assertEquals(TaskOutcome.SKIPPED, task(":terraformSetup")!!.outcome)
            assertEquals(TaskOutcome.SUCCESS, task(":runTerraformVersionCheck")!!.outcome)
            Assertions.assertTrue { output.contains(expectedOutputMsg) }
        }
    }
}
package com.pswidersk.gradle.terraform

import org.apache.tools.ant.taskdefs.condition.Os
import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ListPropertiesTest {
    @TempDir
    lateinit var tempDir: File

    @Test
    fun `test if default properties were correctly set`() {
        // given
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText(
            """
            plugins {
                id("com.pswidersk.terraform-plugin")
            }
        """.trimIndent()
        )
        val expectedVersion = "1.13.5"
        val runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(tempDir)
            .forwardOutput()
            .withArguments(":listPluginProperties")
        val expectedSetupPath = tempDir.resolve(".gradle").resolve("terraformClient-v$expectedVersion")
        val expectedPackageName = "terraform_${expectedVersion}_${os()}_${arch()}.zip"

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Terraform version: $expectedVersion",
                "Setup directory: ${sysDepPath(expectedSetupPath)}",
                "Terraform package: $expectedPackageName",
                "Terraform download URL: https://releases.hashicorp.com/terraform/$expectedVersion/$expectedPackageName"
            )
        }
    }

    @Test
    fun `test if defaults are overridden by user`() {
        // given
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText(
            """
            plugins {
                id("com.pswidersk.terraform-plugin")
            }
            terraformPlugin {
                terraformVersion = "1.10.0-alpha20240807"
                terraformSetupDir = File("setupDir")
                terraformPackage = "customTerraform.zip"
                terraformDownloadUrl = "https://proxy-terraform/terraform.zip"
            }
        """.trimIndent()
        )
        val runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(tempDir)
            .forwardOutput()
            .withArguments("--configuration-cache", ":listPluginProperties")
        val expectedSetupPath = tempDir.resolve("setupDir")

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Terraform version: 1.10.0-alpha20240807",
                "Setup directory: ${sysDepPath(expectedSetupPath)}",
                "Terraform package: customTerraform.zip",
                "Terraform download URL: https://proxy-terraform/terraform.zip"
            )
        }
    }

    @Test
    fun `test if properties are properly lazy loaded`() {
        // given
        val buildFile = File(tempDir, "build.gradle.kts")
        buildFile.writeText(
            """
            plugins {
                id("com.pswidersk.terraform-plugin")
            }
            terraformPlugin {
                terraformVersion = "1.10.0"
            }
        """.trimIndent()
        )
        val runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(tempDir)
            .forwardOutput()
            .withArguments("--configuration-cache", ":listPluginProperties")
        val expectedSetupPath = tempDir.resolve(".gradle").resolve("terraformClient-v1.10.0")
        val expectedPackageName = "terraform_1.10.0_${os()}_${arch()}.zip"

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Terraform version: 1.10.0",
                "Setup directory: ${sysDepPath(expectedSetupPath)}",
                "Terraform package: $expectedPackageName",
                "Terraform download URL: https://releases.hashicorp.com/terraform/1.10.0/$expectedPackageName"
            )
        }
    }

    private fun sysDepPath(inputFile: File): String {
        return if (Os.isFamily(Os.FAMILY_MAC)) {
            "/private${inputFile.path}"
        } else {
            inputFile.path
        }
    }

}

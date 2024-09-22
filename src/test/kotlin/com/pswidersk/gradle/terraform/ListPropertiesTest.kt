package com.pswidersk.gradle.terraform

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.io.writeText
import kotlin.text.trimIndent

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
        val runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(tempDir)
            .forwardOutput()
            .withArguments(":listPluginProperties")
        val expectedSetupPath = tempDir.resolve(".gradle").resolve("terraformClient-v1.9.6").absolutePath

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Terraform version: 1.9.6",
                "Setup directory: $expectedSetupPath",
                "Terraform package: terraform_1.9.6_windows_amd64.zip",
                "Terraform download URL: https://releases.hashicorp.com/terraform/1.9.6/terraform_1.9.6_windows_amd64.zip"
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
        val expectedSetupPath = tempDir.resolve("setupDir").absolutePath

        // when
        val runResult = runner.build()

        // then
        with(runResult) {
            assertThat(task(":listPluginProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
            assertThat(output).contains(
                "Terraform version: 1.10.0-alpha20240807",
                "Setup directory: $expectedSetupPath",
                "Terraform package: customTerraform.zip",
                "Terraform download URL: https://proxy-terraform/terraform.zip"
            )
        }
    }

}

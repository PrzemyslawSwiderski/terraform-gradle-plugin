package com.pswidersk.gradle.terraform

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import kotlin.text.trimIndent

abstract class ListPropertiesTask : DefaultTask() {

    private val terraformExtension: TerraformPluginExtension = project.terraformPlugin

    init {
        group = "terraform"
        description = "List basic plugin properties."
    }

    @TaskAction
    fun setup() {
        with(terraformExtension) {
            logger.lifecycle(
                """
                Terraform version: ${terraformVersion.get()}
                Setup directory: ${terraformSetupDir.get()}
                Terraform package: ${terraformPackage.get()}
                Terraform download URL: ${terraformDownloadUrl.get()}
            """.trimIndent()
            )
        }
    }
}

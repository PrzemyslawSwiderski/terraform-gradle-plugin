package com.pswidersk.gradle.terraform

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.net.URI

open class TerraformSetupTask : DefaultTask() {

    private val terraformSetupDir = project.terraformPlugin.terraformSetupDir.get().asFile

    init {
        group = "terraform"
        description = "Terraform setup task to install terraform client"
        onlyIf {
            !terraformSetupDir.exists()
        }
    }

    @TaskAction
    fun setup() = with(project) {
        terraformSetupDir.mkdirs()
        val terraformPackage = terraformSetupDir.resolve(terraformPlugin.terraformPackage.get())
        val terraformArchiveInputStream = URI.create(terraformPlugin.terraformDownloadUrl.get()).toURL()
        logger.quiet("Downloading terraform from: $terraformArchiveInputStream ...")
        terraformArchiveInputStream.openStream().use { inputStream ->
            terraformPackage.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        logger.quiet("Unzipping package to setup dir: $terraformSetupDir ...")
        copy {
            it.from(zipTree(terraformPackage))
            it.into(terraformSetupDir)
        }
        logger.quiet("Terraform setup complete.")
    }

}

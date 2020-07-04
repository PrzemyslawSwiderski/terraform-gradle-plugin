package com.pswidersk.gradle.terraform

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URL

open class TerraformSetupTask : DefaultTask() {

    private val terraformSetupDir = terraformSetupDir(project, project.terraformPlugin.terraformVersion.get())

    init {
        group = "terraform"
        description = "Terraform setup task to install terraform client"
        onlyIf {
            !terraformSetupDir.exists()
        }
    }

    @TaskAction
    fun setup() {
        val os = os()
        val arch = arch()
        val terraformVersion = project.terraformPlugin.terraformVersion.get()
        terraformSetupDir.mkdirs()
        val terraformPackage = terraformSetupDir.resolve("terraform_${terraformVersion}_${os}_${arch}.zip")
        downloadTerraformPackage(terraformPackage, terraformVersion)
        project.logger.quiet("Unzipping package to setup dir: $terraformSetupDir ...")
        project.copy {
            it.from(project.zipTree(terraformPackage))
            it.into(terraformSetupDir)
        }
        terraformPackage.delete()
        project.logger.quiet("Terraform setup complete.")
    }

    private fun downloadTerraformPackage(terraformPackage: File, terraformVersion: String) {
        val terraformArchiveInputStream = URL("https://releases.hashicorp.com/terraform/${terraformVersion}/${terraformPackage.name}")
        project.logger.quiet("Downloading terraform from: $terraformArchiveInputStream ...")
        terraformArchiveInputStream.openStream().use { inputStream ->
            terraformPackage.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}
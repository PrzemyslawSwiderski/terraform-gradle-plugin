package com.pswidersk.gradle.terraform

import org.gradle.api.DefaultTask
import org.gradle.api.internal.file.FileOperations
import org.gradle.api.tasks.TaskAction
import java.net.URI
import javax.inject.Inject

abstract class TerraformSetupTask @Inject constructor(
    private val fileOperations: FileOperations
) : DefaultTask() {

    private val terraformPluginExtension = project.terraformPlugin

    init {
        group = "terraform"
        description = "Terraform setup task to install terraform client"
        onlyIf {
            !terraformPluginExtension.terraformSetupDir.get().asFile.exists()
        }
    }

    @TaskAction
    fun setup() = with(terraformPluginExtension) {
        val setupDir = terraformSetupDir.get().asFile
        logger.quiet("Terraform Setup DIR: $setupDir")
        setupDir.mkdirs()

        val terraformArchiveInputStream = URI.create(terraformDownloadUrl.get()).toURL()
        logger.quiet("Downloading terraform from: $terraformArchiveInputStream")
        val packageFile = terraformPackageFile.get().asFile
        terraformArchiveInputStream.openStream().use { inputStream ->
            packageFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        logger.quiet("Unzipping package to setup dir: ${terraformSetupDir.get()}")
        fileOperations.copy {
            it.from(fileOperations.zipTree(terraformPackageFile))
            it.into(terraformSetupDir)
        }
        logger.quiet("Terraform setup complete.")
    }

}

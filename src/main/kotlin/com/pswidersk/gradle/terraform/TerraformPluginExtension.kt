package com.pswidersk.gradle.terraform

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.internal.file.FileFactory
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER")
open class TerraformPluginExtension @Inject constructor(
    project: Project,
    providerFactory: ProviderFactory,
    objects: ObjectFactory,
    fileFactory: FileFactory
) {

    val terraformVersion: Property<String> = objects.property<String>().convention(DEFAULT_TERRAFORM_VERSION)

    val terraformSetupDir: DirectoryProperty = objects.directoryProperty().convention(
        providerFactory.provider {
            fileFactory.dir(
                project.rootDir
                    .resolve(GRADLE_FILES_DIR)
                    .resolve("$TERRAFORM_SETUP_DIR-v${terraformVersion.get()}")
            )
        }
    )

    val terraformPackage: Property<String> = objects.property<String>().convention(
        "terraform_${terraformVersion.get()}_${os()}_${arch()}.zip"
    )

    val terraformDownloadUrl: Property<String> = objects.property<String>().convention(
        "https://releases.hashicorp.com/terraform/${terraformVersion.get()}/${terraformPackage.get()}"
    )

    internal val terraformExec: RegularFileProperty = objects.fileProperty().convention(
        providerFactory.provider {
            terraformSetupDir.get().file("terraform")
        }
    )

}

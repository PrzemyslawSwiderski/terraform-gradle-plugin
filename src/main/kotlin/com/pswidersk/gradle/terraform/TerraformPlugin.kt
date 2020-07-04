package com.pswidersk.gradle.terraform

import org.gradle.api.Plugin
import org.gradle.api.Project

class TerraformPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(TERRAFORM_PLUGIN_EXTENSION_NAME, TerraformPluginExtension::class.java, project)
        project.tasks.register("terraformSetup", TerraformSetupTask::class.java)
    }

}

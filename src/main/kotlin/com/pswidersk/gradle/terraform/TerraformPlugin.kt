package com.pswidersk.gradle.terraform

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class TerraformPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        extensions.create(TERRAFORM_PLUGIN_EXTENSION_NAME, TerraformPluginExtension::class.java, project)
        tasks.register<ListPropertiesTask>("listPluginProperties")
        tasks.register<TerraformSetupTask>(TERRAFORM_SETUP_TASK_NAME)
    }

}

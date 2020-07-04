@file:JvmName("TerraformPluginConstants")

package com.pswidersk.gradle.terraform

/**
 * Name of terraform plugin extension in projects.
 */
const val TERRAFORM_PLUGIN_EXTENSION_NAME = "terraformPlugin"

/**
 * Directory where gradle specific files are stored.
 */
const val GRADLE_FILES_DIR = ".gradle"

/**
 * Directory where terraform client will be downloaded and stored.
 */
const val TERRAFORM_SETUP_DIR = "terraformClient"

/**
 * Plugin tasks group name.
 */
const val PLUGIN_TASKS_GROUP_NAME = "terraform"

/**
 * Name of task to setup terraform.
 */
const val TERRAFORM_SETUP_TASK_NAME = "terraformSetup"


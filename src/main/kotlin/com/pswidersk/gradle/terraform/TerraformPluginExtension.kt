package com.pswidersk.gradle.terraform

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER")
open class TerraformPluginExtension @Inject constructor(project: Project,
                                                        objects: ObjectFactory) {

    val terraformVersion: Property<String> = objects.property<String>().convention("0.12.28")

}
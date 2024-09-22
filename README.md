# [Gradle Terraform plugin](https://plugins.gradle.org/plugin/com.pswidersk.terraform-plugin)

## About

Simple Gradle plugin to wrap [Terraform](https://www.terraform.io/) executable as tasks.

By using this plugin there is no need to download and install terraform client.
Downloading Terraform executable and unpacking is done by `terraformSetup` task.
All major operating systems such as Linux, Windows, Mac OS are supported.

## Requirements

* JDK 8 or higher and `JAVA_HOME` properly set to run gradle wrapper

## Usage

### Steps to run terraform commands by using Gradle

1. Apply a plugin to a project as described
   on [gradle portal](https://plugins.gradle.org/plugin/com.pswidersk.terraform-plugin).
2. Configure a plugin by specifying desired terraform version in build script:
    ```kotlin
    terraformPlugin {
        terraformVersion.set("1.9.6")
    }
    ```
3. Define a task to run desired terraform client command, for example:
    ```kotlin
    tasks {
        register<TerraformTask>("terraformHelp") {
            args("--help")
        }
    }
    ```
4. Run terraform command from gradle:
    ```shell script
    # Linux
    ./gradlew :terraformHelp
    # Windows
    gradlew.bat :terraformHelp
    ```

### Additional examples can be found in `examples` module in this project.

## Properties

Plugin behavior can be adjusted by the following properties:

- `terraformVersion` - version of Terraform to be downloaded, `1.9.6` by default
- `terraformSetupDir` - Terraform installation directory, by default
  `<project_dir>/.gradle/terraformClient-v<terraformVersion>`
- `terraformPackage` - name of the package to download and save as, by default
  `terraform_${terraformVersion.get()}_${os()}_${arch()}.zip`
- `terraformDownloadUrl` - can be used to adjust the download URL directly,
  e.g. `https://releases.hashicorp.com/terraform/1.9.6/terraform_1.9.6_linux_arm64.zip`, by default it is resolved as
  `https://releases.hashicorp.com/terraform/${terraformVersion.get()}/${terraformPackage.get()}`

Sample configuration block can look like:
```kotlin
terraformPlugin {
    terraformVersion = "1.10.0-alpha20240807"
    terraformSetupDir = File("setupDir")
    terraformPackage = "customTerraform.zip"
    terraformDownloadUrl = "https://proxy-terraform/terraform.zip"
}
```

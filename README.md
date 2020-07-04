# Gradle Terraform plugin
## About
Simple Gradle plugin to wrap [Terraform](https://www.terraform.io/) executable as tasks. 

By using this plugin there is no need to download and install terraform client.
All major operating systems such as Linux, Windows, Mac OS are supported.

## Requirements
* JRE 8 or higher to run gradle wrapper

## Usage
### Steps to run terraform commands by using Gradle
1. Apply a plugin to a project as described on [gradle portal](https://plugins.gradle.org/plugin/com.pswidersk.terraform-plugin).
2. Configure a plugin by specifying desired terraform version in build script:
    ```kotlin
    terraformPlugin {
        terraformVersion.set("0.12.28")
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

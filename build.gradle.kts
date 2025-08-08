// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.detekt) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Гарантируем наличие актуальной версии для classpath плагинов
        classpath("com.squareup:javapoet:1.13.0")
    }
    configurations["classpath"].resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}

subprojects {
    configurations.all {
        resolutionStrategy {
            force("com.squareup:javapoet:1.13.0")
        }
    }
}
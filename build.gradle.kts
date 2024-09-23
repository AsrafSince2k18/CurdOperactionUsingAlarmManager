// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    id("androidx.room") version "2.6.1" apply false
    id ("io.realm.kotlin") version "1.16.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
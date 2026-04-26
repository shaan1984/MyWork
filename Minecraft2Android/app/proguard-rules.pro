# Minecraft 2 ProGuard Rules
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.minecraft2.android.** { *; }
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

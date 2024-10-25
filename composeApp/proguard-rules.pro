
# Suppress warnings (optional)
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn io.invertase.firebase.**

# Keep Firebase classes and annotations
-keep class com.google.firebase.database.** { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes *Annotation*
-keepclassmembers enum * { *; }
-keepclassmembers class * { @com.google.firebase.database.annotations.NotNull *; }
-keepclassmembers class * { @com.google.firebase.database.annotations.Nullable *; }

-keep class com.google.firebase.database.**$** { *; }

# Keep your custom model classes
-keep class com.hardihood.two_square_game.android.** { *; }

# Optional: Keep Firebase Ads classes if needed
-keep class com.google.android.gms.internal.ads.** { *; }

# Optional (for debugging): Uncomment if needed
# -keepresources public class * { public **;}
# -keeplines sourcefile=<your.package.name>/**/*.java
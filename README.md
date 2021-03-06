![App Icon](artwork/icon-store.png)

# Fixate File Explorer

This is **version 5** of the application, and yes, all 5 versions were complete rewrites! Versions 3-5 all have similarities though, I started to settle down in my style at that point, but 1, 2, and 3 are completely different.

- [Version 4](https://github.com/aklinker1/fixate4) - Android Architecture Components came out
- [Version 3](https://github.com/aklinker1/Fixate3) - I kinda knew what I was doing
- [Version 2](https://github.com/aklinker1/Fixate-File-Manager) - Lol
- Version 1 (_Not on GitHub_) - I'm glad it's not on GitHub

> It's fun to see how I've grown over the years

## Development

### Prerequisites

- [Android Studio](https://developer.android.com/studio/install)

### Useful Commands

There are 2 flavors of the app: `beta` and `prod`. `beta` has a different package name, icon, and app name, all of which include "beta"

```bash
# Run it locally
./gradlew installBetaDebug

# Build debug APK
./gradlew assembleProdDebug

# Package .aab's
./gradlew bundleRelease

# List all available tasks
./gradlew tasks
```

## Deployments

TODO - Eventually these will all be automated, but that's a bit far off in the future right now!

**Note** This repository will no longer be maintained, please head to the [OpenIntents project](http://code.google.com/p/openintents/) for updates.

The Android operating system encourages developers to [integrate Apps via Intents](http://android-developers.blogspot.com/2009/11/integrating-application-with-intents.html). While re-using code in this fashion is very desirable, it can clash with the aim of providing great user experience. Users should not be presented with error messages if the app they use depends on another app, which happens not to be installed.

Clearly, there's room for [dependency management in Android](http://groups.google.com/group/openintents/browse_thread/thread/04304a3c1c3fb201). This project aims to provide the necessary infrastructure that:
  * Allows developers of apps to handle missing dependencies more smartly, i.e. by offering a list of applications to install.
  * Allows developers of app stores (like Android Market, AndAppStore, etc.) to provide that list of applications to the rest of the system.
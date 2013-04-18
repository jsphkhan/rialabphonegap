# HTML5 Canvas – toDataURL() plugin for Android 

by [Joseph](http://jbkflex.wordpress.com).

Modified from the original plugin by [Ryan Gillespie](http://ryangillespie.com/phonegap.php).

For older Android devices, 2.2 , 2.3(in general Android < 4.0) the native HTML5 Canvas- toDataURL() function 
does not work.  That means older Android web-kits (a Phonegap app runs on the Android webview which is actually the webkit browser
inside a native wrapper) does not support the native method of converting a HTML5 Canvas to an image (.png or .jpeg) through toDataURL() 
generated base64 encoded strings. This plugin enables you add functionality for older Android devices and provides a 
base64 encoded string of the HTML5 Canvas image. You can use this plugin with the newer (>4.0) Android's as well.

Find the complete tutorial and instructions in this [blog post](http://jbkflex.wordpress.com/2012/12/21/html5-canvas-todataurl-support-for-android-devices-working-phonegap-2-2-0-plugin/).




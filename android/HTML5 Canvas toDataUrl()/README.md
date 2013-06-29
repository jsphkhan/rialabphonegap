# HTML5 Canvas toDataURL() - Base64 encoded data string Phonegap Android plugin

by [Joseph](http://jbkflex.wordpress.com).

Modified from the original plugin by [Ryan Gillespie](http://ryangillespie.com/phonegap.php).

For older Android devices, 2.2 , 2.3(in general Android < 4.0) the native HTML5 Canvas- toDataURL() function (that gives a base64 encoded image data)
does not work.  That means older Android web-kits (a Phonegap app runs on the Android webview which is actually the webkit browser
inside a native wrapper) does not support the native method of converting a HTML5 Canvas to an image (.png or .jpeg) through toDataURL() 
generated base64 encoded strings. This plugin enables you add functionality for older Android devices and provides a 
base64 encoded string of the HTML5 Canvas image. You can use this plugin with the newer (>4.0) Android's as well.

Find the complete tutorial and instructions in this [blog post](http://jbkflex.wordpress.com/2012/12/21/html5-canvas-todataurl-support-for-android-devices-working-phonegap-2-2-0-plugin/).

Note: The plugin was specifically developed for Phoengap 2.2.0. I have not tested it with higher versions of Phonegap. But
it should work. Although you may want to check the official documentation for any changes to the Phonegap plugin architecture. 

###Adding the plugin to your project

1) Copy the CanvasPlugin.js file inside assets/www/ folder and provide a reference to it in your index.html file, like this

```
<script type="text/javascript" charset="utf-8" src="CanvasPlugin.js"></script>
```

2) Copy the CanvasPlugin.java file. Create a directory inside your project’s src folder that matches the package name of CanvasPlugin.java class. 
For our case make a directory – /org/apache/cordova/plugin inside src and then paste CanvasPlugin.java inside it. If 
you change the package name, make sure to change the directory structure as well. The package name can be found at the 
top of CanvasPlugin.java file.

3) Next thing to do is to register the plugin in the config file – open res/xml/config.xml and then add the plugin 
details given below to the <plugins></plugins> section of the Config XML file. The name attribute is the Java class name and 
the value is the path of the class. This should match the package name.

```
<plugin name="CanvasPlugin" value="org.apache.cordova.plugin.CanvasPlugin"/>
```

So these 3 steps should help you to set up the plugin with your project. Now let's see how to start using it.

###Using the plugin

Make a call to the plugin inside your javascript (your script.js file or so, whichever you want it to) code like this. 
(You can call this inside a button click handler or so),

```
var canvas = document.getElementById("myCanvas");
window.canvasplugin(canvas,function(val){
  document.getElementById("myImg").src = val.data;
});
```

where myCanvas is the ID of the HTML Canvas. The callback function receives the Base64 encode image string inside the 
data property of the returned value. Make sure to access it through val.data. Now you can use this as a source for an 
HTML image tag and you can get a preview image of your canvas.

 If you need to change the mimeType (.jpg or .png), you can do that inside the CanvasPlugin.js file. Also you can pass that as a 
 parameter to the javascript interface rather than hard code inside the CanvasPlugin.js file (which I did mistakenly). 
 So pass it from your script.js to window.canvasplugin() call along with the canvas reference and the callback 
 reference, as the third parameter.


##License

###The MIT License (MIT)

Copyright (c) 2013 Joseph Khan [http://jbkflex.wordpress.com](http://jbkflex.wordpress.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.




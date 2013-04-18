window.canvasplugin = function(canvasEl,callback){
	var canvasProps = {
		mimeType: "image/png",
		xpos: canvasEl.offsetLeft,
		ypos: canvasEl.offsetTop,
		width: canvasEl.width,
		height: canvasEl.height,
		screenWidth: window.innerWidth // no WebView.getContentWidth(), use this instead
	};
	
	//call the Plugin execute method()
	cordova.exec(callback,function(err){
		callback('Error: ' + err);	
	},"CanvasPlugin","toDataURL",[canvasProps.mimeType,
							canvasProps.xpos,
							canvasProps.ypos,
							canvasProps.width,
							canvasProps.height,
							canvasProps.screenWidth]);	
}
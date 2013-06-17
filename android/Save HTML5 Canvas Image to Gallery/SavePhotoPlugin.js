window.savephotoplugin = function(canvasEl,mimeType,appVersion,callback){
	var canvasProps = {
		mimeType: mimeType,
		xpos: canvasEl.offsetLeft,
		ypos: canvasEl.offsetTop,
		width: canvasEl.width,
		height: canvasEl.height,
		screenWidth: window.innerWidth
	};
	
	//call the Plugin execute method()
	cordova.exec(callback,function(err){
		callback('Error: ' + err);	
	},"SavePhotoPlugin","savePhoto",[canvasProps.mimeType,
							canvasProps.xpos,
							canvasProps.ypos,
							canvasProps.width,
							canvasProps.height,
							canvasProps.screenWidth,appVersion]);	
}
/**
 * SavePhotoPlugin - saves the HTML5 Canvas Drawing to the devices memory (SD Card)
 * with MIME Types jpeg or png. After successfull saving it calls the Media Scanner
 * to scan for the new photo and make it available in the gallery .
 * Returns - the path of the saved file to javascript
 * Uses code from my plugin - HTML5 Canvas toDataURL - http://jbkflex.wordpress.com/2012/12/21/html5-canvas-todataurl-support-for-android-devices-working-phonegap-2-2-0-plugin/
 * Thanks to Ryan Gillespie http://ryangillespie.com and Jared Sheets.
 */
package org.apache.cordova.plugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import android.webkit.WebView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
//import org.apache.commons.codec.binary.Base64;
import java.io.FileOutputStream;
import java.util.Calendar;

public class SavePhotoPlugin extends CordovaPlugin {
	
	public static final String ACTION = "toDataURL";
	
	public static final String MIME_PNG = "image/png";
	public static final String MIME_JPEG = "image/jpeg";

	private WebView mAppView;
	private Bitmap bmp;
	//private JSONObject imgData = new JSONObject();
	//private DisplayMetrics metrics = new DisplayMetrics();
	private String appVersion = ""; //getting the Android version from javascript now

	@Override	
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		mAppView = this.webView;		
		String mimeType = "image/png"; //default mime type
		int xpos = 0;
		int ypos = 0;
		int width = 0;
		int height = 0;
		int screenWidth = 0;		

		if (action.equals("savePhoto")) {
			try {
				mimeType = data.getString(0);
				xpos = data.getInt(1);
				ypos = data.getInt(2);
				width = data.getInt(3);
				height = data.getInt(4);
				screenWidth = data.getInt(5);
				appVersion = data.getString(6); //get the Android version								
				
				//first get the bitmap image created from the HTML5 Canvas passed
			 	Bitmap b = this.getImageBitmapFromHTML5Canvas(mimeType, xpos, ypos, width, height, screenWidth);
			 	//now save the photo in device gallery
			 	String path = this.savePhoto(b);
				
				callbackContext.success(path);				

			} catch (JSONException jsonEx) {				
				callbackContext.error("Could not save the image");
			}
			return true;
		}
		return false;
	}

	private Bitmap getImageBitmapFromHTML5Canvas(String mimeType, int xpos, int ypos, int width, int height, int screenWidth) throws JSONException {

    	//ByteArrayOutputStream out = new ByteArrayOutputStream();		
    	//String encodedImg = "";
    	
    	//get the density of the screen (low = 0.75, medium = 1, high = 1.5)
    	float appScale = mAppView.getScale();
	// System.out.println("AppScale: " + appScale);
    	
    	/* Draw the entire WebView - then crop the bitmap to the x,y & width,height of the canvas position in the WebView */
    	/* Use Javascript to get the full width of the WebView - no WebView.getContentWidth() */

    	try {    		
    		int webViewHeight = mAppView.getContentHeight();  
    		int webViewWidth = (screenWidth == 0) ? mAppView.getWidth() : screenWidth;
    		
    		//scale the webView dimensions by the screen density
    		int scaledViewHeight = (int)(webViewHeight * appScale);
    		int scaledViewWidth = (int)(webViewWidth * appScale);
    		
    		bmp = Bitmap.createBitmap(scaledViewWidth, scaledViewHeight, Bitmap.Config.RGB_565);
    		
    		System.out.println("App Dimensions: " + webViewWidth + " x " + webViewHeight);
    		System.out.println("Scaled Dimensions: " + scaledViewWidth + " x " + scaledViewHeight);
    		
    		//scale the canvas coordinates by the screen density
    		int scaledXpos = (int)(xpos * appScale);
    		int scaledYpos = (int)(ypos * appScale);
    		int scaledWidth = (int)(width * appScale);
    		int scaledHeight = (int)(height * appScale);
    		
    		mAppView.draw(new Canvas(bmp));
    		bmp = Bitmap.createBitmap(bmp, scaledXpos, scaledYpos, scaledWidth, scaledHeight);    		    		
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}	
    	return bmp;
	}
	
	private String savePhoto(Bitmap bmp)
	{
		String retVal = "";
		try
		{
		//File path = new File(Environment.getExternalStorageDirectory(),"Rotate");
		/*File path = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_PICTURES
	    ); //this throws error in Android 2.2*/
		//path.mkdir();			
			File imageFileName = null;
			FileOutputStream out = null;
			Calendar c = Calendar.getInstance();
			String date = fromInt(c.get(Calendar.MONTH))
		            + fromInt(c.get(Calendar.DAY_OF_MONTH))
		            + fromInt(c.get(Calendar.YEAR))
		            + fromInt(c.get(Calendar.HOUR_OF_DAY))
		            + fromInt(c.get(Calendar.MINUTE))
		            + fromInt(c.get(Calendar.SECOND));
		
			//Double f = Double.valueOf(appVersion); //convert android version to double float type			
			int check = appVersion.compareTo("2.3.3"); //if 1 or greater, then Android version is > 2.3.3, if 0 or -1 then Android version is 2.3.3 or lesser
			//System.out.println("###################$$$$$$$$$$ Check: " + check);
			
			if(check >= 1){ //for Android > 2.3 For eg. 2.3 or higher				
				//this throws error in Android 2.2, hence I have a corresponding else block
				File path = Environment.getExternalStoragePublicDirectory(
						Environment.DIRECTORY_PICTURES
				); 
				imageFileName = new File(path, date.toString() + ".jpg"); 
				//System.out.println("$$$$$$$$$$ Greater: " + path);
			}else{ //for Android = 2.3.3 or lesser
				//System.out.println("$$$$$$$$$$ Lesser");				
				imageFileName = new File(Environment.getExternalStorageDirectory(), date.toString() + ".jpg"); 	
			}
			
			 out = new FileOutputStream(imageFileName);
			 bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			 out.flush();
			 out.close();
			 scanPhoto(imageFileName.toString());
			 out = null;
			 retVal = imageFileName.toString();
		} catch (Exception e)
		{
			e.printStackTrace();
			retVal = "Something went wrong while saving the image!!";
		}		
		return retVal;
	}


	private String fromInt(int val)
	{
		return String.valueOf(val);
	}

	/*  invoke the system's media scanner to add your photo to the Media Provider's database, 
	 * making it available in the Android Gallery application and to other apps. */
	private void scanPhoto(String imageFileName)
	{
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(imageFileName);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);	      		  
	    //this.cordova.getContext().sendBroadcast(mediaScanIntent);
	    //this.cordova.getActivity().startActivity(mediaScanIntent);
	    this.cordova.getActivity().sendBroadcast(mediaScanIntent);
	} 
}
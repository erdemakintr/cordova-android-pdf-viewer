package com.anz.pdfviewer;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PdfViewerPlugin extends CordovaPlugin {
    public static final String ACTION_ADD_CALENDAR_ENTRY = "addCalendarEntry"; 

    @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
//		    if (ACTION_ADD_CALENDAR_ENTRY.equals(action)) { 
//		    JSONObject arg_object = args.getJSONObject(0);
//		    Intent calIntent = new Intent(Intent.ACTION_EDIT)
//		        .setType("vnd.android.cursor.item/event")
//		        .putExtra("beginTime", arg_object.getLong("startTimeMillis"))
//		        .putExtra("endTime", arg_object.getLong("endTimeMillis"))
//		        .putExtra("title", arg_object.getString("title"))
//		        .putExtra("description", arg_object.getString("description"))
//		        .putExtra("eventLocation", arg_object.getString("eventLocation"));
//		 
//		       this.cordova.getActivity().startActivity(calIntent);
//		       callbackContext.success();
//		       return true;
//		    }
//		    callbackContext.error("Invalid action");
//		    return false;

			JSONObject arg_object = args.getJSONObject(0);
			String file = arg_object.getString("file");
			
	    	PdfHandler pdf = new PdfHandler(this.cordova.getActivity());
	    	pdf.openPdf(file);
	    	callbackContext.success();
	    	return true;
			
		} catch(Exception e) {
		    System.err.println("Exception: " + e.getMessage());
		    callbackContext.error(e.getMessage());
		    return false;
		} 		 
	}
}
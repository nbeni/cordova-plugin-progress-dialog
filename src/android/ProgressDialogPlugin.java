package ar.com.beni.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException; 

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.util.Log;
import android.R;

public class ProgressDialogPlugin extends CordovaPlugin {
    private static final String LOG_TAG = "ProgressDialog";
    private static ProgressDialog progressDialog;
  
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.progressDialog = new ProgressDialog(cordova.getActivity(), this.parseTheme());
        
        // TODO: make these configurable
        this.progressDialog.setMessage("Loading...");
        this.progressDialog.setCancelable(false);
  }
 
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("show".equals(action)) {
            Log.v(LOG_TAG, "Show action executed, showing progress dialog");
            
            // TODO: move to onMessage to support cross-plugin invocation
            this.progressDialog.show();
            callbackContext.success();
            return true;
        } else if ("hide".equals(action)) {
            Log.v(LOG_TAG, "Hide action executed, hiding progress dialog");
            
            // TODO: move to onMessage to support cross-plugin invocation
            this.progressDialog.hide();
            callbackContext.success();
            return true;
        }
    
        return false;
    }
    
    @Override
    public Object onMessage(String id, Object data) {
        // TODO: implement cross-plugin support for displaying/hiding the progress dialog.
        return null;
    }

    private int parseTheme() {
        // Get Theme value from preferences (config.xml)
        String value = this.preferences.getString("ProgressDialogTheme", null);
        Log.v(LOG_TAG, "ProgressDialogTheme value: " + value == null ? "not set" : value);
        Log.v(LOG_TAG, "Android SDK Version: " + android.os.Build.VERSION.SDK_INT);
        
        // SDK Version 23 (M) deprecated all AlertDialog constants and added new ones
        // SDK Version 14 (ICE_CREAM_SANDWICH) introduced THEME_DEVICE_DEFAULT_DARK and THEME_DEVICE_DEFAULT_LIGHT AlertDialog constants
        // SDK Version 11 (HONEYCOMB) introduced THEME_HOLO_DARK, THEME_HOLO_LIGHT and THEME_TRADITIONAL AlertDialog constants
        // See Details: http://developer.android.com/reference/android/app/AlertDialog.html
        if ("Theme_DeviceDefault_Dialog_Alert".equalsIgnoreCase(value) || "THEME_DEVICE_DEFAULT_DARK".equalsIgnoreCase(value)) {
            if (android.os.Build.VERSION.SDK_INT >= 23) { //android.os.Build.VERSION_CODES.M) {
                return R.style.Theme_DeviceDefault_Dialog_Alert;
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            }
        } else if ("Theme_DeviceDefault_Light_Dialog_Alert".equalsIgnoreCase(value) || "THEME_DEVICE_DEFAULT_LIGHT".equalsIgnoreCase(value)) {
            if (android.os.Build.VERSION.SDK_INT >= 23) { // android.os.Build.VERSION_CODES.M) {
                return R.style.Theme_DeviceDefault_Light_Dialog_Alert;
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            }
        } else if ("Theme_Material_Dialog_Alert".equalsIgnoreCase(value) || "THEME_HOLO_DARK".equalsIgnoreCase(value)) {
            if (android.os.Build.VERSION.SDK_INT >= 23) { // android.os.Build.VERSION_CODES.M) {
                return R.style.Theme_Material_Dialog_Alert;
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                return AlertDialog.THEME_HOLO_DARK;
            }
        } else if ("Theme_Material_Light_Dialog_Alert".equalsIgnoreCase(value) || "THEME_HOLO_LIGHT".equalsIgnoreCase(value)) {
            if (android.os.Build.VERSION.SDK_INT >= 23) { // android.os.Build.VERSION_CODES.M) {
                return R.style.Theme_Material_Light_Dialog_Alert;
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                return AlertDialog.THEME_HOLO_LIGHT;
            }
        } else if ("Theme_Material_Dialog_Alert".equalsIgnoreCase(value) || "THEME_TRADITIONAL".equalsIgnoreCase(value)) {
            if (android.os.Build.VERSION.SDK_INT >= 23) { // android.os.Build.VERSION_CODES.M) {
                return R.style.Theme_Material_Dialog_Alert;
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                return AlertDialog.THEME_TRADITIONAL;
            }
        }
        
        // By default use the parent context's default alert dialog theme
        return 0;
    }
}
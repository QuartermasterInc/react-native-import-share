
package com.reactlibrary;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import android.os.Bundle;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.support.annotation.Nullable;
import android.graphics.Bitmap;
import java.io.InputStream;

public class RNReactNativeImportShareModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener, Application.ActivityLifecycleCallbacks {

  private final ReactApplicationContext reactContext;
  public static final String LOG_TAG = "RNImportShareModule";

  Promise promise;

  public RNReactNativeImportShareModule(ReactApplicationContext reactContext, Application application) {

      super(reactContext);

      this.reactContext = reactContext;
      this.reactContext.addActivityEventListener(this);
      this.reactContext.addLifecycleEventListener(this);

      application.registerActivityLifecycleCallbacks(this);

  }

    @Override
    public void onActivityStarted(Activity activity) {
        // Log.d(LOG_TAG, "MODULE GOT -> onActivityStarted Activity : " + activity + " has Intent -> " + activity.getIntent() + " -> " + getCurrentActivity().getIntent() );
        /// checkIntent(activity.getIntent());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // Log.d(LOG_TAG, "MODULE GOT -> onActivityStarted" );
        // Activity `onPause`
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // Activity `onPause`
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // Activity `onPause`
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // Activity `onPause`
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        // Activity `onPause`
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "MODULE GOT -> onActivityCreated Activity -> " + getCurrentActivity() );
    }


  @Override
  public String getName() {
    return "RNReactNativeImportShare";
  }

  @ReactMethod
	void getTest(Callback callback) {
	    callback.invoke("JAVA - RAN");
	}

	// removed @Override temporarily just to get it working on different versions of RN
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        // this.promise.resolve( data.getDataString() );
        Log.d(LOG_TAG, "MODULE GOT -> onActivityResult Activity -> " + activity + " : Intent -> " + data );
        _onActivityResult(requestCode, resultCode, data);
    }

    // removed @Override temporarily just to get it working on different versions of RN
    public void _onActivityResult(int requestCode, int resultCode, Intent data) {
        // Ignored, required to implement ActivityEventListener for RN 0.33
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(LOG_TAG, "MODULE GOT -> ON NEW INTENT -> " + Intent.ACTION_SEND + " - " + intent);
        checkIntent(intent);
    }


    @Override
    public void onHostPause() {
        // Activity `onPause`
    }

    @Override
    public void onHostResume() {
        // Activity `onResume`
    }

    @Override
    public void onHostDestroy() {
        // Activity `onDestroy`
    }

    // PREP for pas to JS land.
    // NOTE: need a promise
    private void checkIntent(@Nullable Intent intent) {
        if (intent != null) {

            String action = intent.getAction();
            String type = intent.getType();

            Log.d(LOG_TAG, "Converting to a WritableMap for JS to use - " + processIntent( intent ) );

            // Log.d(LOG_TAG, "MODULE GOT -> ON NEW INTENT -> ACTION : " + Intent.ACTION_SEND + " : " + action );

            /*
            switch (action) {
                case "com.example.HANDLE_AUTHORIZATION_RESPONSE":
                    ...
                    break;
                default:
                    // do nothing
            }
            */
        }
    }

    public WritableMap processIntent( Intent intent ) {

        WritableMap map = Arguments.createMap();

        String value = "";
        String type = "";
        String action = "";

        // I think this is a mistake we already have an intent.
        Activity currentActivity = getCurrentActivity();

        // REFACTOR this.
        if (currentActivity != null) {

            //Intent intent = currentActivity.getIntent();
            action = intent.getAction();
            type = intent.getType();

            if (type == null) {
                type = "";
            }
            if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
                value = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
            else if (Intent.ACTION_SEND.equals(action) && ("image/*".equals(type) || "image/jpeg".equals(type) || "image/png".equals(type) || "image/jpg".equals(type) ) ) {
                Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                value = "file://" + RealPathUtil.getRealPathFromURI(currentActivity, uri);

            } else {
                value = "";
            }
        } else {
            value = "";
            type = "";
        }

        map.putString("type", type);
        map.putString("value",value);

        return map;
    }
}
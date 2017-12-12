
package com.reactlibrary;

import android.app.Application;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.ReactPackage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNReactNativeImportSharePackage implements ReactPackage {

    final Application mApplication;

    public RNReactNativeImportSharePackage(Application application) {
        mApplication = application;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        // return Arrays.<NativeModule>asList(new RNReactNativeImportShareModule(reactContext));
        // return Collections.<NativeModule>singletonList(new RNReactNativeImportShareModule( mApplication, reactContext ));
        return Arrays.<NativeModule>asList(new RNReactNativeImportShareModule( reactContext, mApplication ));
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
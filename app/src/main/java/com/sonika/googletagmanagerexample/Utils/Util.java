package com.sonika.googletagmanagerexample.Utils;


import android.content.Context;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

public class Util {

    public static void pushOpenScreenEvent(Context context, String screenName){

        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();

        dataLayer.pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));

    }
}

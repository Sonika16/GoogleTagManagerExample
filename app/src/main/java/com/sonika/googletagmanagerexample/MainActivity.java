package com.sonika.googletagmanagerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.sonika.googletagmanagerexample.callbacks.ContainerLoadedCallback;
import com.sonika.googletagmanagerexample.singleton.ContainerHolderSingleton;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String CONTAINER_ID = "GTM-56458HW";
    private static final long TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TagManager tagManager = TagManager.getInstance(this);

        PendingResult<ContainerHolder> pending = tagManager.loadContainerPreferNonDefault
                (CONTAINER_ID, R.raw.default_container);

        // The onResult method will be called as soon as one of the following happens:
//     1. a saved container is loaded
//     2. if there is no saved container, a network container is loaded
//     3. the 2-second timeout occurs
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = containerHolder.getContainer();
                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("CuteAnimals", "failure loading container");
//                    displayErrorToUser(R.string.load_error);
                    return;
                }
                ContainerLoadedCallback.registerCallbacksForContainer(container);
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
//                startMainActivity();
            }
        }, TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS, TimeUnit.MILLISECONDS);
    }
}

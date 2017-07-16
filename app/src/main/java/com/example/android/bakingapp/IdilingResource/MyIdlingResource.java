package com.example.android.bakingapp.IdilingResource;

/**
 * Created by mina essam on 16-Jul-17.
 */

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class MyIdlingResource implements IdlingResource  {
    @Nullable private volatile ResourceCallback callback;
    private AtomicBoolean atomicBoolean=new AtomicBoolean(false);
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return atomicBoolean.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback=callback;
    }

    public void setIdleState(boolean state){
        atomicBoolean.set(state);
        if(callback!=null&&state) callback.onTransitionToIdle();
    }
}

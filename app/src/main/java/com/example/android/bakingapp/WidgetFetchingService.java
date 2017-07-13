package com.example.android.bakingapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by mina essam on 10-Jul-17.
 */

public class WidgetFetchingService extends IntentService {
     String stringResponse=null;
    static final String tag="WidgetFetchingService";
    public WidgetFetchingService(){
        super(tag);
    }


    @Override
    protected void onHandleIntent( Intent intent) {

        getResponse();
        if(stringResponse!=null){

        }

    }

    void getResponse(){


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(MainActivity.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stringResponse=response;
                Log.d("onResponse",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        //return stringResponse;
    }


}

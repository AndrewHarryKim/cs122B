package com.example.jonathan.fromscratch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
    }

    public void connectToTomcat(View view){

        //

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String url = "http://192.168.0.9:8080/fabflix/AppMovieList";

        final JSONObject json = new JSONObject();
        try {
            json.put("title", ((EditText) findViewById(R.id.movie_title)).getText());
        } catch ( JSONException e ){
            e.printStackTrace();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response);

                            Log.d("response", response);
                            {
                                Iterator<String> keys = jsonResponse.keys();
                                if(keys.hasNext())
                                {
                                    ((TextView) findViewById(R.id.movie_list_response)).setText("\n"+jsonResponse.getString(keys.next()));
                                }
                                while( keys.hasNext() ) {
                                    ((TextView) findViewById(R.id.movie_list_response)).append("\n"+jsonResponse.getString(keys.next()));
                                }
                            }
                        } catch ( JSONException e ){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                params.put("json", json.toString());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(postRequest);



        return ;
    }
}

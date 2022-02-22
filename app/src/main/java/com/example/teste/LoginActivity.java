package com.example.teste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue = Volley.newRequestQueue(this);

        EditText edt_email = (EditText) findViewById(R.id.edt_email);

        EditText edt_senha = (EditText) findViewById(R.id.edt_senha);

        TextView txt_message = (TextView) findViewById(R.id.txt_message);

        Button btn_login = (Button) findViewById(R.id.btn_login);

        Intent switchActivityIntent = new Intent(this, HomeActivity.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_email.getText().toString();
                String password = edt_senha.getText().toString();

                String url = "http://192.168.15.152:8080/cliente/login";

                ////////////////////////////////////////////////

                if(email.length() == 0 || password.length() == 0) {
                    txt_message.setText("* Preencha todos os campos!");
                    return;
                }

                JSONObject jsonBodyObj = new JSONObject();
                try{
                    jsonBodyObj.put("email", email);
                    jsonBodyObj.put("password", password);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                final String requestBody = jsonBodyObj.toString();

                ////////////////////////////////////////////////

                JsonObjectRequest myjsonrequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (Boolean.parseBoolean(response.getString("auth").toString()) == true) {

                                        SharedPreferences preferences = getSharedPreferences("secret", MODE_PRIVATE);

                                        SharedPreferences.Editor editor = preferences.edit();

                                        editor.putString("token", response.getString("token").toString());
                                        editor.putString("email", response.getJSONObject("result").getString("email").toString());
                                        editor.putString("name", response.getJSONObject("result").getString("name").toString());

                                        txt_message.setText(response.getJSONObject("result").getString("name").toString());

                                        editor.apply();

                                        startActivity(switchActivityIntent);
                                    } else {
                                        txt_message.setText("* " + response.getString("message"));
                                    }
                                    //populateLessonDetails(myActiveLessonURLFiltered);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.e("Error: ", error.getMessage());
                            }
                        })
                        {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json");
                                return headers;
                            }

                            @Override
                            public byte[] getBody() {
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                            requestBody, "utf-8");
                                    return null;
                                }
                            }
                        };

                        queue.add(myjsonrequest);

                };


        });}

}

package com.example.teste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    RequestQueue queue;

    EditText edt_name;
    EditText edt_lastname;
    EditText edt_email;
    EditText edt_senha;

    Button btn_cadastro;

    TextView txt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Intent switchActivityIntent = new Intent(this, HomeActivity.class);

        queue = Volley.newRequestQueue(this);

        edt_name = (EditText) findViewById(R.id.edt_nome);
        edt_lastname = (EditText) findViewById(R.id.edt_lastname);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);

        txt_message = (TextView) findViewById(R.id.txt_message);

        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.15.152:8080/cliente/cadastro";

                String name = edt_name.getText().toString();
                String lastname = edt_lastname.getText().toString();
                String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();

                JSONObject jsonBodyObj = new JSONObject();
                try{
                    jsonBodyObj.put("name", name);
                    jsonBodyObj.put("lastname", lastname);
                    jsonBodyObj.put("email", email);
                    jsonBodyObj.put("password", senha);
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

        });

    }

}

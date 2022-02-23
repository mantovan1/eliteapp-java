package com.example.teste;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.teste.adapters.AdapterEmpresasList;
import com.example.teste.models.Empresa;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RequestQueue queue;

    TextView txt_welcome;
    Button btn_explorar;

    ListView lv_empresas;

    ArrayList<Empresa> empresas = new ArrayList<Empresa>();
    AdapterEmpresasList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        queue = Volley.newRequestQueue(this);

        txt_welcome = (TextView) findViewById(R.id.txt_welcome);
        btn_explorar = (Button) findViewById(R.id.btn_explorar);

        lv_empresas = (ListView) findViewById(R.id.lv_empresas);

        ////////////////////////////////////////////////

        SharedPreferences sharedPreferences = getSharedPreferences("secret", MODE_PRIVATE);
        String text = sharedPreferences.getString("name", "");

        txt_welcome.setText(text);

        String url = "http://192.168.15.152:8080/empresas/consulta/destaque";

        JsonArrayRequest myjsonrequest = new JsonArrayRequest(
            Request.Method.GET,
            url,
           null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject json = response.getJSONObject(i);

                            String nome_empresa = json.getString("nome_empresa");
                            String categoria = json.getString("categoria");
                            String foto_perfil = json.getString("foto_perfil");

                            Empresa empresa = new Empresa(nome_empresa, categoria, foto_perfil);

                            empresas.add(empresa);

                            saveAdapter();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    txt_welcome.setText("erro");
                }
            }
        );

        queue.add(myjsonrequest);

    };

    public void saveAdapter() {
        adapter = new AdapterEmpresasList(this, empresas);
        lv_empresas.setAdapter(adapter);
    }

}

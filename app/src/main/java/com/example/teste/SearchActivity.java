package com.example.teste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.teste.adapters.AdapterEmpresasList;
import com.example.teste.models.Empresa;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RequestQueue queue;

    TextView txt_welcome;

    ListView lv_empresas;

    ArrayList<Empresa> empresas = new ArrayList<Empresa>();
    AdapterEmpresasList adapter;

    Intent i;

    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        queue = Volley.newRequestQueue(this);
        i = new Intent(this, EmpresaDetalhesActivity.class);

        txt_welcome = (TextView) findViewById(R.id.txt_welcome);

        lv_empresas = (ListView) findViewById(R.id.lv_empresas);

        ////////////////////////////////////////////////

        /*SharedPreferences sharedPreferences = getSharedPreferences("secret", MODE_PRIVATE);
        String text = sharedPreferences.getString("name", "");

        txt_welcome.setText(text);*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoria = extras.getString("categoria");
        }

        String url = "http://192.168.15.152:8080/empresas/lista/" + categoria;

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

        lv_empresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Empresa empresa = (Empresa) adapter.getItem(position);

                i.putExtra("nome_empresa", empresa.getNomeEmpresa());
                i.putExtra("foto_perfil", empresa.getFoto_perfil());

                startActivity(i);

            }

        });

    };

    public void saveAdapter() {
        adapter = new AdapterEmpresasList(this, empresas);
        lv_empresas.setAdapter(adapter);
    }

}

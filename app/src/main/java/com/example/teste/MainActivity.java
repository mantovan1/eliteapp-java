package com.example.teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("secret", MODE_PRIVATE);
        String text = sharedPreferences.getString("name", "");

        if(text != "") {
            Intent switchActivityIntent = new Intent(this, HomeActivity.class);
            //switchActivityIntent.setFlags(switchActivityIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            switchActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(switchActivityIntent);
        } else {
            setContentView(R.layout.activity_main);
        }

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });

        Button btn_pular = (Button) findViewById(R.id.btn_pular);
        btn_pular.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCategories();
            }
        });

        Button btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        btn_cadastro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCadastro();
            }
        });

    }

    private void switchToLogin() {
        Intent switchActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchToCategories() {
        Intent switchActivityIntent = new Intent(this, CategoriesActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchToCadastro() {
        Intent switchActivityIntent = new Intent(this, CadastroActivity.class);
        startActivity(switchActivityIntent);
    }

}
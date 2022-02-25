package com.example.teste;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CategoriesActivity extends AppCompatActivity {

    ImageButton btn_moda;
    ImageButton btn_alimentacao;
    ImageButton btn_tecnologia;
    ImageButton btn_maquiagem;
    ImageButton btn_outros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Intent i = new Intent(this, SearchActivity.class);

        btn_moda = (ImageButton) findViewById(R.id.btn_moda);
        btn_alimentacao = (ImageButton) findViewById(R.id.btn_alimentacao);
        btn_tecnologia = (ImageButton) findViewById(R.id.btn_tecnologia);
        btn_maquiagem = (ImageButton) findViewById(R.id.btn_maquiagem);
        btn_outros = (ImageButton) findViewById(R.id.btn_outros);

        btn_moda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("categoria", "Moda");
                startActivity(i);
            }
        });

        btn_alimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("categoria", "Alimentos");
                startActivity(i);
            }
        });

        btn_tecnologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("categoria", "Tecnologia");
                startActivity(i);
            }
        });

        btn_maquiagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("categoria", "Moda");
                startActivity(i);
            }
        });

        btn_outros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("categoria", "Outro");
                startActivity(i);
            }
        });

    }

}

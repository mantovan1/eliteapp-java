package com.example.teste;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    TextView txt_welcome;
    ImageView img_empresa;
    ImageView img_empresa_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_welcome = (TextView) findViewById(R.id.txt_welcome);
        img_empresa = (ImageView) findViewById(R.id.img_empresa);
        img_empresa_2 = (ImageView) findViewById(R.id.img_empresa_2);

        SharedPreferences sharedPreferences = getSharedPreferences("secret", MODE_PRIVATE);

        String text = sharedPreferences.getString("name", "");

        txt_welcome.setText(text);

        String imageUri = "http://192.168.15.152:8080/92e400b7-266a-497f-9400-6f63deee17db-a-torre-de-pisa-edificios-monumentos-1292986%20(1).jpg";

        Picasso.with(this).load(imageUri).into(img_empresa);

        Picasso.with(this).load(imageUri).into(img_empresa_2);

    }

}

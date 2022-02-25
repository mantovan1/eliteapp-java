package com.example.teste;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teste.helper.RoundedCornersTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class EmpresaDetalhesActivity extends AppCompatActivity {

    ImageView img_empresa;
    TextView txt_nome_empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detalhes);

        img_empresa = (ImageView) findViewById(R.id.img_empresa);
        txt_nome_empresa = (TextView) findViewById(R.id.txt_nome_empresa);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nome_empresa = extras.getString("nome_empresa");
            String foto_perfil = extras.getString("foto_perfil");

            final Transformation transformation = new MaskTransformation(this, R.drawable.circle);

            Picasso.get()
                    .load("http://192.168.15.152:8080/" + foto_perfil)
                    .resize(500, 500)
                    .transform(transformation)
                    .into(img_empresa);

            txt_nome_empresa.setText(nome_empresa);
            //The key argument here must match that used in the other activity
        }

    }

}

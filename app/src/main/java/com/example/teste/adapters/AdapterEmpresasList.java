package com.example.teste.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.teste.R;

import com.example.teste.models.Empresa;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterEmpresasList extends BaseAdapter {

    private Context context;
    private ArrayList<Empresa> empresasList;

    public AdapterEmpresasList(Context context, ArrayList<Empresa>  empresasList) {

        this.context = context;
        this.empresasList = empresasList;

    }

    @Override
    public int getCount() {

        return this.empresasList.size();

    }

    @Override
    public Object getItem(int position) {

        return this.empresasList.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_empresa, null);
        ImageView img_empresa = (ImageView) v.findViewById(R.id.img_empresa);
        TextView tvProductName = (TextView) v.findViewById(R.id.txt_nome_empresa);
        TextView tvProductPrice = (TextView) v.findViewById(R.id.txt_categoria);

        Picasso.with(v.getContext())
                .load("http://192.168.15.152:8080/" + empresasList.get(position).getFoto_perfil())
                .resize(500, 500)
                .into(img_empresa);

        tvProductName.setText(empresasList.get(position).getNomeEmpresa());
        tvProductPrice.setText(empresasList.get(position).getCategoria().toString());

        return v;

    }
}
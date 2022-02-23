package com.example.teste.models;

public class Empresa {

    public String nomeEmpresa;
    public String categoria;
    public String foto_perfil;

    public Empresa(String nomeEmpresa, String categoria, String foto_perfil) {
        this.nomeEmpresa = nomeEmpresa;
        this.categoria = categoria;
        this.foto_perfil = foto_perfil;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }
}

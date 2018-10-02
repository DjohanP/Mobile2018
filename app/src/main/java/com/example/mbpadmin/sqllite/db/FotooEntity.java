package com.example.mbpadmin.sqllite.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fotoo")
public class FotooEntity implements Fotoo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String judul;
    private String waktu;
    private String foto;

    /*public FotooEntity(String judul, String waktu, String foto) {
        this.judul = judul;
        this.waktu = waktu;
        this.foto = foto;
    }*/

    @Override
    public int getId() {
        return id;
    }
    @Override
    public String getJudul()
    {
        return judul;
    }
    @Override
    public String getWaktu()
    {
        return waktu;
    }
    @Override
    public String getFoto()
    {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public FotooEntity() {
    }

    public FotooEntity(String judul, String waktu, String foto) {
        this.judul = judul;
        this.waktu = waktu;
        this.foto = foto;
    }

    public FotooEntity(Fotoo fotoo) {
        this.id = fotoo.getId();
        this.judul = fotoo.getJudul();
        this.waktu = fotoo.getWaktu();
        this.foto = fotoo.getFoto();
    }

}

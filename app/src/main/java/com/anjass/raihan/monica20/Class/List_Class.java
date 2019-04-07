package com.anjass.raihan.monica20.Class;

public class List_Class {

    String id,
            divisi = "",
            isiPesan = "";
    boolean isChecked;

    public List_Class() {
    }

    public List_Class(String id, String division, String message, boolean isChecked) {
        this.id = id;
        this.divisi = division;
        this.isiPesan = message;
        this.isChecked = isChecked;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public String getDivisi() {
        return divisi;
    }

    public String getIsiPesan() {
        return isiPesan;
    }

    public boolean getIsChecked() {
        return isChecked;
    }
}
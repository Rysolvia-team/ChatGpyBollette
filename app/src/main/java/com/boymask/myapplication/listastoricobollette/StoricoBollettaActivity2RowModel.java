package com.boymask.myapplication.listastoricobollette;


import com.boymask.myapplication.database.Bolletta;

public class StoricoBollettaActivity2RowModel {

    private final int id;



    private String titolo;
    private boolean selected;


    public StoricoBollettaActivity2RowModel(Bolletta b) {
        this.id=b.uid;
        this.titolo = ""+b.datap;
        this.selected = false;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public boolean isSelected() {
        return selected;
    }
    public int getId() {
        return id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

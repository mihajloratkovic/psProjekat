/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Prodavac;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;

public class ModelTabeleProdavci extends AbstractTableModel {

    private ArrayList<OpstiDomenskiObjekat> listaProdavaca;
    private String[] kolone = new String[]{"ID", "Ime i prezime", "Korisničko ime", "Datum zaposlenja"};
    
    
    
    public ModelTabeleProdavci() {
        this.listaProdavaca = new ArrayList<>();
    }

    public ModelTabeleProdavci(ArrayList<OpstiDomenskiObjekat> listaProdavaca) {
        this.listaProdavaca = listaProdavaca;
    }
    
    @Override
    public int getRowCount() {
        return listaProdavaca.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Prodavac prodavac = (Prodavac) listaProdavaca.get(rowIndex);

        switch(columnIndex) {
            case 0:
                return prodavac.getIdProdavac();
            case 1:
                return prodavac.getIme() + " " + prodavac.getPrezime();
            case 2:
                return prodavac.getUsername();
            case 3:
                // Formatiraj datum
                if (prodavac.getDatumZaposlenja() != null) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
                    return sdf.format(prodavac.getDatumZaposlenja());
                } else {
                    return "";
                }
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    public OpstiDomenskiObjekat vratiProdavca(int rowIndex) {
        return listaProdavaca.get(rowIndex);
    }
    
    public void dodajProdavca(Prodavac prodavac) {
        listaProdavaca.add(prodavac);
        fireTableDataChanged();
    }
    
    public void obrisiProdavca(Prodavac prodavac) {
        listaProdavaca.remove(prodavac);
        fireTableDataChanged();
    }
    
    public void osveziTabelu() {
        fireTableDataChanged();
    }
    
    public void postaviListu(ArrayList<OpstiDomenskiObjekat> novaLista) {
        this.listaProdavaca = novaLista;
        fireTableDataChanged();
    }
}
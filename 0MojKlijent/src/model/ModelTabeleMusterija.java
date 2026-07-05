package model;

import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleMusterija extends AbstractTableModel {
    
    private List<Musterija> musterije;
    private String[] kolone = {"ID", "Ime", "Prezime", "Email", "Telefon", "Tip"};
    
    public ModelTabeleMusterija() {
        musterije = new ArrayList<>();
    }
    
    public ModelTabeleMusterija(List<Musterija> musterije) {
        this.musterije = musterije;
    }
    
    @Override
    public int getRowCount() {
        return musterije.size();
    }
    
    @Override
    public int getColumnCount() {
        return kolone.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Musterija m = musterije.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return m.getIdMusterija();
            case 1: return m.getIme();
            case 2: return m.getPrezime();
            case 3: return m.getEmail();
            case 4: return m.getBrojTelefona();
            case 5: return m.getTipMusterije();
            default: return "";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    public Musterija vratiMusteriju(int row) {
        return musterije.get(row);
    }
    
    public void postaviListu(List<Musterija> novaLista) {
        this.musterije = novaLista;
        fireTableDataChanged();
    }
    
    public void obrisiMusteriju(Musterija m) {
        musterije.remove(m);
        fireTableDataChanged();
    }
    
    public void dodajMusteriju(Musterija m) {
        musterije.add(m);
        fireTableDataChanged();
    }
    
    public void clear() {
        musterije.clear();
        fireTableDataChanged();
    }
}
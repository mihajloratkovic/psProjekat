package model;

import domen.Ljubimac;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleLjubimciZaRacun extends AbstractTableModel {
    
    private List<Ljubimac> ljubimci;
    private String[] kolone = {"ID", "Naziv", "Kategorija", "Cena"};
    
    public ModelTabeleLjubimciZaRacun() {
        ljubimci = new ArrayList<>();
    }
    
    public ModelTabeleLjubimciZaRacun(List<Ljubimac> ljubimci) {
        this.ljubimci = ljubimci;
    }
    
    @Override
    public int getRowCount() {
        return ljubimci.size();
    }
    
    @Override
    public int getColumnCount() {
        return kolone.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ljubimac l = ljubimci.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return l.getIdLjubimac();
            case 1: return l.getNaziv();
            case 2: return l.getKategorija();
            case 3: return l.getCena();
            default: return "";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    public Ljubimac vratiLjubimca(int row) {
        return ljubimci.get(row);
    }
    
    public void postaviListu(List<Ljubimac> novaLista) {
        this.ljubimci = novaLista;
        fireTableDataChanged();
    }
    
    public void clear() {
        ljubimci.clear();
        fireTableDataChanged();
    }
}
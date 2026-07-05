package model;

import domen.Racun;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleRacuni extends AbstractTableModel {
    
    private List<Racun> racuni;
    private String[] kolone = {"ID", "Datum", "Prodavac", "Musterija", "Ukupno", "Popust", "Za uplatu"};
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    
    public ModelTabeleRacuni() {
        racuni = new ArrayList<>();
    }
    
    public ModelTabeleRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }
    
    @Override
    public int getRowCount() {
        return racuni.size();
    }
    
    @Override
    public int getColumnCount() {
        return kolone.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Racun r = racuni.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return r.getIdRacun();
            case 1: return sdf.format(r.getDatumIzdavanja());
            case 2: return r.getProdavac().toString();
            case 3: return r.getMusterija().toString();
            case 4: return String.format("%.2f", r.getUkupanIznos());
            case 5: return String.format("%.1f", r.getPopust()) + "%";
            case 6: return String.format("%.2f", r.getIznosSaPopustom());
            default: return "";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    public Racun vratiRacun(int row) {
        return racuni.get(row);
    }
    
    public void postaviListu(List<Racun> novaLista) {
        this.racuni = novaLista;
        fireTableDataChanged();
    }
    
    public void clear() {
        racuni.clear();
        fireTableDataChanged();
    }
}
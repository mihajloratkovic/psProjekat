package model;

import domen.StavkaRacuna;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleStavkeRacuna extends AbstractTableModel {
    
    private List<StavkaRacuna> stavke;
    private String[] kolone = {"Rb", "Ljubimac", "Količina", "Cena", "Iznos"};
    
    public ModelTabeleStavkeRacuna() {
        this.stavke = new ArrayList<>(); // OBAVEZNO inicijalizuj listu
    }
    
    @Override
    public int getRowCount() {
        return stavke == null ? 0 : stavke.size(); // Zaštita od null
    }
    
    @Override
    public int getColumnCount() {
        return kolone.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaRacuna s = stavke.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return s.getRb();
            case 1: return s.getLjubimac().getNaziv();
            case 2: return s.getKolicina();
            case 3: return String.format("%.2f", s.getCena());
            case 4: return String.format("%.2f", s.getIznos());
            default: return "";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    public void dodajStavku(StavkaRacuna s) {
        if (stavke == null) stavke = new ArrayList<>();
        stavke.add(s);
        fireTableDataChanged();
    }
    
    public void obrisiStavku(int row) {
        if (row >= 0 && row < stavke.size()) {
            stavke.remove(row);
            // Osveži redne brojeve
            for (int i = 0; i < stavke.size(); i++) {
                stavke.get(i).setRb(i + 1);
            }
            fireTableDataChanged();
        }
    }
    
    public List<StavkaRacuna> getStavke() {
        if (stavke == null) stavke = new ArrayList<>();
        return stavke;
    }
    
    public void postaviListu(List<StavkaRacuna> novaLista) {
        this.stavke = novaLista != null ? novaLista : new ArrayList<>();
        fireTableDataChanged();
    }
    
    public void clear() {
        if (stavke != null) stavke.clear();
        fireTableDataChanged();
    }
    
    public double getUkupanIznos() {
        if (stavke == null || stavke.isEmpty()) return 0;
        double ukupno = 0;
        for (StavkaRacuna s : stavke) {
            ukupno += s.getIznos();
        }
        return ukupno;
    }
    
    public void azurirajStavku(int row, StavkaRacuna novaStavka) {
    if (row >= 0 && row < stavke.size()) {
        stavke.set(row, novaStavka);
        fireTableDataChanged();
    }
}
}
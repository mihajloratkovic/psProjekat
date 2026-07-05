package domen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StavkaRacuna implements OpstiDomenskiObjekat{

    private int rb;
    private int kolicina;
    private double cena;
    private double iznos;
    private int idRacun;
    private Ljubimac ljubimac;

    public StavkaRacuna() {
    }

    public StavkaRacuna(int rb, int kolicina, double cena, double iznos, Ljubimac ljubimac) {
        this.rb = rb;
        this.kolicina = kolicina;
        this.cena = cena;
        this.iznos = iznos;
        this.ljubimac = ljubimac;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Ljubimac getLjubimac() {
        return ljubimac;
    }

    public void setLjubimac(Ljubimac ljubimac) {
        this.ljubimac = ljubimac;
    }

    public int getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(int idRacun) {
        this.idRacun = idRacun;
    }
    
    
    
    @Override
    public String vratiNazivTabele() {
        return "stavkaracuna";
    }

     @Override
     public String vratiVrednostiZaInsert() {
         return idRacun + "," + rb + "," + kolicina + "," + cena + "," + iznos + "," + ljubimac.getIdLjubimac();
     }

    @Override
    public String vratiVrednostiZaUpdate() {
        return "kolicina=" + kolicina + ", cena=" + cena + ", iznos=" + iznos;
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "rb=" + rb;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "rb=" + rb;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){
            StavkaRacuna s = new StavkaRacuna();
            s.setIdRacun(rs.getInt("idRacun"));
            s.setRb(rs.getInt("rb"));
            s.setKolicina(rs.getInt("kolicina"));
            s.setCena(rs.getDouble("cena"));
            s.setIznos(rs.getDouble("iznos"));

            // Učitaj ljubimca
            Ljubimac l = new Ljubimac();
            l.setIdLjubimac(rs.getInt("idLjubimac"));
            try {
                l.setNaziv(rs.getString("naziv"));
                l.setKategorija(rs.getString("kategorija"));
                l.setCena(rs.getDouble("l_cena"));
            } catch (SQLException e) {
                // Ako nema u result setu, ostavi prazno
            }
            s.setLjubimac(l);

            lista.add(s);
        }
        return lista;
    }


}
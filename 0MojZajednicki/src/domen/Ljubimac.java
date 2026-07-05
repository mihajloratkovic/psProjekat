package domen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Ljubimac implements OpstiDomenskiObjekat{

    private int idLjubimac;
    private String naziv;
    private String kategorija;
    private double cena;
    

    public Ljubimac() {
    }

    public Ljubimac(int idLjubimac, String naziv, String kategorija, double cena) {
        this.idLjubimac = idLjubimac;
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.cena = cena;
    }

    
    

    public int getIdLjubimac() {
        return idLjubimac;
    }

    public void setIdLjubimac(int idLjubimac) {
        this.idLjubimac = idLjubimac;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return naziv;
    }

     @Override
    public String vratiNazivTabele() {
        return "ljubimac";
    }

    @Override
    public String vratiVrednostiZaInsert() {
        return "'" + naziv + "','" + kategorija + "'," + cena;
    }

    @Override
    public String vratiVrednostiZaUpdate() {
        return "naziv='" + naziv + "', kategorija='" + kategorija + "', cena=" + cena;
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idLjubimac=" + idLjubimac;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idLjubimac=" + idLjubimac;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){

            Ljubimac l = new Ljubimac();

            l.idLjubimac = rs.getInt("idLjubimac");
            l.naziv = rs.getString("naziv");
            l.kategorija = rs.getString("kategorija");
            l.cena = rs.getDouble("cena");

            lista.add(l);
        }

        return lista;
    }
}

package domen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RadnaSmena implements OpstiDomenskiObjekat{

    private int idRadnaSmena;
    private String naziv;
    private String vremePocetka;
    private String vremeZavrsetka;

    public RadnaSmena() {
    }

    public RadnaSmena(int idRadnaSmena, String naziv, String vremePocetka, String vremeZavrsetka) {
        this.idRadnaSmena = idRadnaSmena;
        this.naziv = naziv;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public int getIdRadnaSmena() {
        return idRadnaSmena;
    }

    public void setIdRadnaSmena(int idRadnaSmena) {
        this.idRadnaSmena = idRadnaSmena;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(String vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public String getVremeZavrsetka() {
        return vremeZavrsetka;
    }

    public void setVremeZavrsetka(String vremeZavrsetka) {
        this.vremeZavrsetka = vremeZavrsetka;
    }
    
     @Override
    public String vratiNazivTabele() {
        return "radnasmena";
    }

    @Override
    public String vratiVrednostiZaInsert() {
        return "'" + naziv + "','" + vremePocetka + "','" + vremeZavrsetka + "'";
    }

    @Override
    public String vratiVrednostiZaUpdate() {
        return "naziv='" + naziv + "', vremePocetka='" + vremePocetka + "', vremeZavrsetka='" + vremeZavrsetka + "'";
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idRadnaSmena=" + idRadnaSmena;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idRadnaSmena=" + idRadnaSmena;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){

            RadnaSmena s = new RadnaSmena();

            s.idRadnaSmena = rs.getInt("idRadnaSmena");
            s.naziv = rs.getString("naziv");
            s.vremePocetka = rs.getString("vremePocetka");
            s.vremeZavrsetka = rs.getString("vremeZavrsetka");

            lista.add(s);
        }

        return lista;
    }


}
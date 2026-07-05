package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Musterija implements OpstiDomenskiObjekat{

    private int idMusterija;
    private String ime;
    private String prezime;
    private String brojTelefona;
    private String email;
    private TipMusterije tipMusterije;
    private String kriterijumPretrage;
    

    public Musterija() {
    }

    public Musterija(int idMusterija, String ime, String prezime, 
            String brojTelefona, String email, TipMusterije tipMusterije, String kriterijumPretrage) {
        this.idMusterija = idMusterija;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.tipMusterije = tipMusterije;
        this.kriterijumPretrage = kriterijumPretrage;
        
    }
    
    

    // GETERI I SETERI
    public int getIdMusterija() {
        return idMusterija;
    }

    public void setIdMusterija(int idMusterija) {
        this.idMusterija = idMusterija;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipMusterije getTipMusterije() {
        return tipMusterije;
    }

    public void setTipMusterije(TipMusterije tipMusterije) {
        this.tipMusterije = tipMusterije;
    }

    public String getKriterijumPretrage() {
        return kriterijumPretrage;
    }

    public void setKriterijumPretrage(String kriterijumPretrage) {
        this.kriterijumPretrage = kriterijumPretrage;
    }

    
    

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

   @Override
public String vratiNazivTabele() {
    return "musterija";
}

@Override
public String vratiVrednostiZaInsert() {
    return "NULL,'" + ime + "','" + prezime + "','" + brojTelefona + "','" + email + "'," + tipMusterije.getIdTipMusterije();
}

    @Override
    public String vratiVrednostiZaUpdate() {
        return "ime='" + ime + "', prezime='" + prezime + "', brojTelefona='" + brojTelefona + "', email='" + email + "', idTipMusterije=" + tipMusterije.getIdTipMusterije();
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idMusterija=" + idMusterija;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idMusterija=" + idMusterija;
    }
    
    public String vratiUslovZaJoin() {
    return "musterija.idTipMusterije = tipmusterije.idTipMusterije";
}

    public String vratiJoinTabelu() {
        return "tipmusterije";
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){
            TipMusterije tm = new TipMusterije();
            tm.setIdTipMusterije(rs.getInt("idTipMusterije"));

            // Pokušaj da učitaš podatke o tipu ako postoje u ResultSet-u
            try {
                tm.setNaziv(rs.getString("naziv"));
                tm.setPopust(rs.getDouble("popust"));
            } catch (SQLException e) {
                // Ako nema podataka o tipu u ResultSet-u, ostavi prazno
                System.out.println("Nema podataka o tipu: " + e.getMessage());
            }

            Musterija m = new Musterija();
            m.setIdMusterija(rs.getInt("idMusterija"));
            m.setIme(rs.getString("ime"));
            m.setPrezime(rs.getString("prezime"));
            m.setBrojTelefona(rs.getString("brojTelefona"));
            m.setEmail(rs.getString("email"));
            m.setTipMusterije(tm);

            lista.add(m);
        }
        return lista;
    }
}
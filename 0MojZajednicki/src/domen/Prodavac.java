package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prodavac implements OpstiDomenskiObjekat{

    private int idProdavac;
    private String ime;
    private String prezime;
    private String username;
    private String password;
    private String jmbg;
    private Date datumZaposlenja;

    public Prodavac() {
    }

    public Prodavac(int idProdavac, String ime, String prezime, 
            String username, String password, String jmbg, Date datumZaposlenja) {
        this.idProdavac = idProdavac;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
        this.jmbg = jmbg;
        this.datumZaposlenja = datumZaposlenja;
    }

    public Date getDatumZaposlenja() {
        return datumZaposlenja;
    }

    public void setDatumZaposlenja(Date datumZaposlenja) {
        this.datumZaposlenja = datumZaposlenja;
    }

   

    public int getIdProdavac() {
        return idProdavac;
    }

    public void setIdProdavac(int idProdavac) {
        this.idProdavac = idProdavac;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }
    

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Prodavac)) return false;
        Prodavac p = (Prodavac) obj;
        return idProdavac == p.idProdavac;
    }
@Override
    public String vratiNazivTabele() {
        return "prodavac";
    }

    @Override
    public String vratiVrednostiZaInsert() {
        return "'" + ime + "','" + prezime + "','" + username + "','" + password + "','" + jmbg + "'";
    }

    @Override
    public String vratiVrednostiZaUpdate() {
        return "ime='" + ime + "', prezime='" + prezime + "', username='" + username + "', password='" + password + "', jmbg='" + jmbg + "'";
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idProdavac=" + idProdavac;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idProdavac=" + idProdavac;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){

            Prodavac p = new Prodavac();

            p.idProdavac = rs.getInt("idProdavac");
            p.ime = rs.getString("ime");
            p.prezime = rs.getString("prezime");
            p.username = rs.getString("username");
            p.password = rs.getString("password");
            p.jmbg = rs.getString("jmbg");
            p.datumZaposlenja = rs.getDate("datumZaposlenja");

            lista.add(p);
        }

        return lista;
    }
    
    


}
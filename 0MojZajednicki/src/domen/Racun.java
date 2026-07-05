package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Date;
import java.util.List;

public class Racun implements OpstiDomenskiObjekat{

    private int idRacun;
    private Date datumIzdavanja;
    private double ukupanIznos;
    private double popust;
    private double iznosSaPopustom;
    private String kriterijumPretrage;
    private int tipPretrage;
    private Prodavac prodavac;
    private Musterija musterija;
    private List<StavkaRacuna> stavke;

    public Racun() {
    }

    public Racun(int idRacun, Date datumIzdavanja, double ukupanIznos, 
            double popust, double iznosSaPopustom, Prodavac prodavac, Musterija musterija, List<StavkaRacuna> stavke) {
        this.idRacun = idRacun;
        this.datumIzdavanja = datumIzdavanja;
        this.ukupanIznos = ukupanIznos;
        this.popust = popust;
        this.iznosSaPopustom = iznosSaPopustom;
        this.prodavac = prodavac;
        this.musterija = musterija;
        this.stavke = stavke;
    }

    public int getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(int idRacun) {
        this.idRacun = idRacun;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public double getIznosSaPopustom() {
        return iznosSaPopustom;
    }

    public void setIznosSaPopustom(double iznosSaPopustom) {
        this.iznosSaPopustom = iznosSaPopustom;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public Musterija getMusterija() {
        return musterija;
    }

    public void setMusterija(Musterija musterija) {
        this.musterija = musterija;
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    public String getKriterijumPretrage() {
        return kriterijumPretrage;
    }

    public void setKriterijumPretrage(String kriterijumPretrage) {
        this.kriterijumPretrage = kriterijumPretrage;
    }

    public int getTipPretrage() {
        return tipPretrage;
    }

    public void setTipPretrage(int tipPretrage) {
        this.tipPretrage = tipPretrage;
    }

    
     @Override
    public String vratiNazivTabele() {
        return "racun";
    }

    @Override
    public String vratiVrednostiZaInsert() {

        return "'" + new java.sql.Date(datumIzdavanja.getTime()) + "',"
                + ukupanIznos + ","
                + popust + ","
                + iznosSaPopustom + ","
                + prodavac.getIdProdavac() + ","
                + musterija.getIdMusterija();
    }

    @Override
    public String vratiVrednostiZaUpdate() {

        return "ukupanIznos=" + ukupanIznos
                + ", popust=" + popust
                + ", iznosSaPopustom=" + iznosSaPopustom;
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idRacun=" + idRacun;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idRacun=" + idRacun;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){
            Racun r = new Racun();
            r.setIdRacun(rs.getInt("idRacun"));
            r.setDatumIzdavanja(rs.getDate("datumIzdavanja"));
            r.setUkupanIznos(rs.getDouble("ukupanIznos"));
            r.setPopust(rs.getDouble("popust"));
            r.setIznosSaPopustom(rs.getDouble("iznosSaPopustom"));

            // Učitaj prodavca
            Prodavac p = new Prodavac();
            p.setIdProdavac(rs.getInt("idProdavac"));
            try {
                p.setIme(rs.getString("p_ime"));
                p.setPrezime(rs.getString("p_prezime"));
            } catch (SQLException e) {
                // Ako nema u result setu, ostavi prazno
            }
            r.setProdavac(p);

            // Učitaj musteriju
            Musterija m = new Musterija();
            m.setIdMusterija(rs.getInt("idMusterija"));
            try {
                m.setIme(rs.getString("m_ime"));
                m.setPrezime(rs.getString("m_prezime"));

                TipMusterije tm = new TipMusterije();
                tm.setNaziv(rs.getString("naziv"));
                tm.setPopust(rs.getDouble("popust"));
                m.setTipMusterije(tm);
            } catch (SQLException e) {
                // Ako nema u result setu, ostavi prazno
            }
            r.setMusterija(m);

            lista.add(r);
        }
        return lista;
    }

}
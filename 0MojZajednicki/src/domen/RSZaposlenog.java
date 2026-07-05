package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RSZaposlenog implements OpstiDomenskiObjekat {

    private int idRSZaposlenog;
    private Date datum;
    private Prodavac prodavac;
    private RadnaSmena radnaSmena;

    public RSZaposlenog() {
    }

    public RSZaposlenog(int idRSZaposlenog, Date datum, Prodavac prodavac, RadnaSmena radnaSmena) {
        this.idRSZaposlenog = idRSZaposlenog;
        this.datum = datum;
        this.prodavac = prodavac;
        this.radnaSmena = radnaSmena;
    }

    public int getIdRSZaposlenog() {
        return idRSZaposlenog;
    }

    public void setIdRSZaposlenog(int idRSZaposlenog) {
        this.idRSZaposlenog = idRSZaposlenog;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public RadnaSmena getRadnaSmena() {
        return radnaSmena;
    }

    public void setRadnaSmena(RadnaSmena radnaSmena) {
        this.radnaSmena = radnaSmena;
    }

    @Override
    public String vratiNazivTabele() {
        return "rszaposlenog";
    }

    @Override
    public String vratiVrednostiZaInsert() {

        return "'" + new java.sql.Date(datum.getTime()) + "',"
                + prodavac.getIdProdavac() + ","
                + radnaSmena.getIdRadnaSmena();
    }

    @Override
    public String vratiVrednostiZaUpdate() {

        return "datum='" + new java.sql.Date(datum.getTime()) + "', "
                + "idProdavac=" + prodavac.getIdProdavac() + ", "
                + "idRadnaSmena=" + radnaSmena.getIdRadnaSmena();
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idRSZaposlenog=" + idRSZaposlenog;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idRSZaposlenog=" + idRSZaposlenog;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {

            RSZaposlenog r = new RSZaposlenog();

            r.setIdRSZaposlenog(rs.getInt("idRSZaposlenog"));
            r.setDatum(rs.getDate("datum"));

            Prodavac p = new Prodavac();
            p.setIdProdavac(rs.getInt("idProdavac"));

            RadnaSmena s = new RadnaSmena();
            s.setIdRadnaSmena(rs.getInt("idRadnaSmena"));

            r.setProdavac(p);
            r.setRadnaSmena(s);

            lista.add(r);
        }

        return lista;
    }

}
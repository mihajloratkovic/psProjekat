package domen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipMusterije implements OpstiDomenskiObjekat{

    private int idTipMusterije;
    private String naziv;
    private double popust;

    public TipMusterije() {
    }

    public TipMusterije(int idTipMusterije, String naziv, double popust) {
        this.idTipMusterije = idTipMusterije;
        this.naziv = naziv;
        this.popust = popust;
    }

    public int getIdTipMusterije() {
        return idTipMusterije;
    }

    public void setIdTipMusterije(int idTipMusterije) {
        this.idTipMusterije = idTipMusterije;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    @Override
    public String toString() {
        return naziv + " (" + popust + "%)";
    }
    
    @Override
    public String vratiNazivTabele() {
        return "tipmusterije";
    }

    @Override
    public String vratiVrednostiZaInsert() {
        return "'" + naziv + "'," + popust;
    }

    @Override
    public String vratiVrednostiZaUpdate() {
        return "naziv='" + naziv + "', popust=" + popust;
    }

    @Override
    public String vratiUslovZaUpdate() {
        return "idTipMusterije=" + idTipMusterije;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "idTipMusterije=" + idTipMusterije;
    }

    @Override
    public List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        while(rs.next()){

            TipMusterije tm = new TipMusterije();

            tm.idTipMusterije = rs.getInt("idTipMusterije");
            tm.naziv = rs.getString("naziv");
            tm.popust = rs.getDouble("popust");

            lista.add(tm);
        }

        return lista;
    }


}
package so.racun;

import db.DBBroker;
import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import domen.Prodavac;
import domen.Racun;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOPretraziRacune extends OpstaSistemskaOperacija {
    
    private List<OpstiDomenskiObjekat> lista;
    private String kriterijum;
    private int tipPretrage;
    
    public SOPretraziRacune(String kriterijum, int tipPretrage) {
        this.kriterijum = kriterijum;
        this.tipPretrage = tipPretrage;
    }
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        // Nema preduslova
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        Racun r = (Racun) odo;
        Prodavac prodavac = r.getProdavac();
        Musterija musterija = r.getMusterija();
        double iznos = r.getIznosSaPopustom(); // Ovo je uneti iznos za pretragu
        String kriterijum = r.getKriterijumPretrage(); // Ako ima i tekstualni kriterijum

        String upit = "SELECT r.*, p.ime as p_ime, p.prezime as p_prezime, "
                    + "m.ime as m_ime, m.prezime as m_prezime, "
                    + "t.naziv, t.popust "
                    + "FROM racun r "
                    + "JOIN prodavac p ON r.idProdavac = p.idProdavac "
                    + "JOIN musterija m ON r.idMusterija = m.idMusterija "
                    + "LEFT JOIN tipmusterije t ON m.idTipMusterije = t.idTipMusterije "
                    + "WHERE 1=1 ";

        if (prodavac != null) {
            upit += "AND r.idProdavac = " + prodavac.getIdProdavac() + " ";
        }

        if (musterija != null) {
            upit += "AND r.idMusterija = " + musterija.getIdMusterija() + " ";
        }

        if (iznos > 0) {
            upit += "AND r.iznosSaPopustom = " + iznos + " ";
        }

        upit += "ORDER BY r.datumIzdavanja DESC";

        System.out.println("SQL pretraga racuna: " + upit);

        java.sql.Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        java.sql.ResultSet rs = st.executeQuery(upit);

        lista = new Racun().ucitajListu(rs);

        rs.close();
        st.close();
    }
}
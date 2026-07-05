package so.musterija;

import db.DBBroker;
import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import domen.TipMusterije;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOPretraziMusterije extends OpstaSistemskaOperacija {
    
    private List<OpstiDomenskiObjekat> lista;
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        // Nema preduslova
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        Musterija m = (Musterija) odo;
        String kriterijum = m.getKriterijumPretrage();
        TipMusterije tip = m.getTipMusterije(); // Dodato

        String upit = "SELECT m.*, t.naziv, t.popust FROM musterija m "
                    + "LEFT JOIN tipmusterije t ON m.idTipMusterije = t.idTipMusterije "
                    + "WHERE 1=1 "; // Uvek tačno, dodajemo uslove

        // Dodaj uslov za tekstualnu pretragu ako postoji
        if (kriterijum != null && !kriterijum.isEmpty()) {
            upit += "AND (m.ime LIKE '%" + kriterijum + "%' "
                  + "OR m.prezime LIKE '%" + kriterijum + "%' "
                  + "OR m.email LIKE '%" + kriterijum + "%' "
                  + "OR m.brojTelefona LIKE '%" + kriterijum + "%') ";
        }

        // Dodaj uslov za tip ako je izabran (i nije "Svi tipovi")
        if (tip != null && tip.getIdTipMusterije() > 0) {
            upit += "AND m.idTipMusterije = " + tip.getIdTipMusterije() + " ";
        }

        System.out.println("SQL pretraga musterija: " + upit);

        java.sql.Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        java.sql.ResultSet rs = st.executeQuery(upit);

        lista = new Musterija().ucitajListu(rs);

        rs.close();
        st.close();
    }
}
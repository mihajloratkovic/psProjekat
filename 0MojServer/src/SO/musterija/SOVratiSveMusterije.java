/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.musterija;

import db.DBBroker;
import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOVratiSveMusterije extends OpstaSistemskaOperacija {
    
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
        String upit = "SELECT m.*, t.naziv, t.popust FROM musterija m "
                    + "LEFT JOIN tipmusterije t ON m.idTipMusterije = t.idTipMusterije";

        System.out.println("SQL vrati sve musterije: " + upit);

        java.sql.Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        java.sql.ResultSet rs = st.executeQuery(upit);

        lista = new Musterija().ucitajListu(rs);

        rs.close();
        st.close();
    }
}
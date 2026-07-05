/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.musterija;

import db.DBBroker;
import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import domen.Racun;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOObrisiMusteriju extends OpstaSistemskaOperacija {
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        Musterija m = (Musterija) odo;

        // Provera da li musterija ima račune
        String whereUslov = "idMusterija = " + m.getIdMusterija();
        List<OpstiDomenskiObjekat> lista = DBBroker.getInstance().vratiSaUslovom(new Racun(), whereUslov);
        if (!lista.isEmpty()) {
            throw new Exception("Ne može se obrisati musterija koja ima račune!");
        }
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().obrisi(odo);
    }
}
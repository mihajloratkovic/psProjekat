/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.tipmusterije;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.TipMusterije;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOVratiSveTipoveMusterije extends OpstaSistemskaOperacija {
    
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
        lista = DBBroker.getInstance().vratiSve(new TipMusterije());
    }
}
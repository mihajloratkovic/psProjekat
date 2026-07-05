/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.prodavac;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Prodavac;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOPrijaviProdavca extends OpstaSistemskaOperacija {
    
    private List<OpstiDomenskiObjekat> lista;
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        // Nema preduslova za prijavu
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        Prodavac p = (Prodavac) odo;
        String whereUslov = "username='" + p.getUsername() + "' AND password='" + p.getPassword() + "'";
        lista = DBBroker.getInstance().vratiSaUslovom(new Prodavac(), whereUslov);
    }
}
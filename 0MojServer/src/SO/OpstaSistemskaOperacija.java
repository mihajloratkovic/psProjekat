/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;


public abstract class OpstaSistemskaOperacija {
    
    public void izvrsi(OpstiDomenskiObjekat odo) throws Exception {
        try {
          
           proveriPreduslov(odo);
            izvrsiKonkretnuOperaciju(odo);
            potvrdi();
        } catch (Exception ex) {
            ponisti();
            throw new Exception("Greska kod izvrsenja SO: " + ex.getMessage());
        }
    }

    
    protected abstract void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception;

    protected abstract void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception;

    
     
    private void potvrdi() throws Exception {
        db.DBBroker.getInstance().potvrdiTransakciju();
    }

    private void ponisti() throws Exception {
        db.DBBroker.getInstance().ponistiTransakciju();
    }

    
}

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

public class SOKreirajMusteriju extends OpstaSistemskaOperacija {
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        Musterija m = (Musterija) odo;

        // NOT NULL provere
        if (m.getIme() == null || m.getIme().trim().isEmpty()) {
            throw new Exception("Ime musterije je obavezno!");
        }
        if (m.getPrezime() == null || m.getPrezime().trim().isEmpty()) {
            throw new Exception("Prezime musterije je obavezno!");
        }
        if (m.getBrojTelefona() == null || m.getBrojTelefona().trim().isEmpty()) {
            throw new Exception("Broj telefona je obavezan!");
        }
        if (m.getEmail() == null || m.getEmail().trim().isEmpty()) {
            throw new Exception("Email je obavezan!");
        }
        if (m.getTipMusterije() == null) {
            throw new Exception("Tip musterije je obavezan!");
        }

        // Format email-a
        if (!m.getEmail().contains("@")) {
            throw new Exception("Email mora da sadrži @!");
        }

        // Provera da li već postoji musterija sa istim email-om
        String whereUslov = "email = '" + m.getEmail() + "'";
        List<OpstiDomenskiObjekat> lista = DBBroker.getInstance().vratiSaUslovom(new Musterija(), whereUslov);
        if (!lista.isEmpty()) {
            throw new Exception("Musterija sa ovim email-om već postoji!");
        }
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().ubaci(odo);
    }
}
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

public class SOIzmeniMusteriju extends OpstaSistemskaOperacija {
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        Musterija m = (Musterija) odo;

        // Iste NOT NULL provere kao za kreiranje
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
        if (!m.getEmail().contains("@")) {
            throw new Exception("Email mora da sadrži @!");
        }

        // Provera da li musterija postoji u bazi
        String whereUslov = "idMusterija = " + m.getIdMusterija();
        List<OpstiDomenskiObjekat> lista = DBBroker.getInstance().vratiSaUslovom(new Musterija(), whereUslov);
        if (lista.isEmpty()) {
            throw new Exception("Musterija ne postoji u bazi!");
        }
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().izmeni(odo);
    }
}
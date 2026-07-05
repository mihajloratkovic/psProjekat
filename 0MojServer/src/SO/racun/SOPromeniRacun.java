package so.racun;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Racun;
import domen.StavkaRacuna;
import java.sql.Statement;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOPromeniRacun extends OpstaSistemskaOperacija {
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        Racun r = (Racun) odo;

        // Iste provere kao za kreiranje
        if (r.getProdavac() == null) {
            throw new Exception("Prodavac je obavezan!");
        }
        if (r.getMusterija() == null) {
            throw new Exception("Musterija je obavezna!");
        }
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora imati bar jednu stavku!");
        }

        // Provera da li račun postoji
        String whereUslov = "idRacun = " + r.getIdRacun();
        List<OpstiDomenskiObjekat> lista = DBBroker.getInstance().vratiSaUslovom(new Racun(), whereUslov);
        if (lista.isEmpty()) {
            throw new Exception("Račun ne postoji u bazi!");
        }

        // Provere stavki (isto kao kod kreiranja)
        for (StavkaRacuna s : r.getStavke()) {
            if (s.getLjubimac() == null) {
                throw new Exception("Stavka mora imati ljubimca!");
            }
            if (s.getKolicina() <= 0) {
                throw new Exception("Količina mora biti veća od 0!");
            }
            double ocekivaniIznos = s.getKolicina() * s.getCena();
            if (Math.abs(s.getIznos() - ocekivaniIznos) > 0.01) {
                throw new Exception("Iznos stavke nije ispravno izračunat!");
            }
        }
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        Racun r = (Racun) odo;
        
        Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        
        // 1. Izmeni osnovne podatke računa
        String upitRacun = "UPDATE racun SET "
                + "idProdavac = " + r.getProdavac().getIdProdavac() + ", "
                + "idMusterija = " + r.getMusterija().getIdMusterija() + ", "
                + "ukupanIznos = " + r.getUkupanIznos() + ", "
                + "popust = " + r.getPopust() + ", "
                + "iznosSaPopustom = " + r.getIznosSaPopustom() + " "
                + "WHERE idRacun = " + r.getIdRacun();
        
        System.out.println("SQL izmeni racun: " + upitRacun);
        st.executeUpdate(upitRacun);
        
        // 2. Obriši sve stare stavke
        String upitBrisiStavke = "DELETE FROM stavkaracuna WHERE idRacun = " + r.getIdRacun();
        System.out.println("SQL brisi stavke: " + upitBrisiStavke);
        st.executeUpdate(upitBrisiStavke);
        
        // 3. Dodaj nove stavke
        int rb = 1;
        for (StavkaRacuna stavka : r.getStavke()) {
            String upitStavka = "INSERT INTO stavkaracuna (idRacun, rb, kolicina, cena, iznos, idLjubimac) VALUES ("
                    + r.getIdRacun() + "," + rb + "," 
                    + stavka.getKolicina() + "," 
                    + stavka.getCena() + "," 
                    + stavka.getIznos() + "," 
                    + stavka.getLjubimac().getIdLjubimac() + ")";
            
            System.out.println("SQL stavka: " + upitStavka);
            st.executeUpdate(upitStavka);
            rb++;
        }
        
        st.close();
    }
}
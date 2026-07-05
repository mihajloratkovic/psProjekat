/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.racun;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Racun;
import domen.StavkaRacuna;
import so.OpstaSistemskaOperacija;
import java.sql.Statement;
import java.sql.ResultSet;

public class SOKreirajRacun extends OpstaSistemskaOperacija {
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        Racun r = (Racun) odo;

        if (r.getProdavac() == null) {
            throw new Exception("Prodavac je obavezan!");
        }
        if (r.getMusterija() == null) {
            throw new Exception("Musterija je obavezna!");
        }
        if (r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Račun mora imati bar jednu stavku!");
        }

        // Provera svake stavke
        for (StavkaRacuna s : r.getStavke()) {
            if (s.getLjubimac() == null) {
                throw new Exception("Stavka mora imati ljubimca!");
            }
            if (s.getKolicina() <= 0) {
                throw new Exception("Količina mora biti veća od 0!");
            }
            if (s.getCena() <= 0) {
                throw new Exception("Cena mora biti veća od 0!");
            }
            // Provera da li se cena poklapa sa cenom ljubimca
            if (s.getCena() != s.getLjubimac().getCena()) {
                throw new Exception("Cena stavke ne odgovara ceni ljubimca!");
            }
            // Provera iznosa
            double ocekivaniIznos = s.getKolicina() * s.getCena();
            if (Math.abs(s.getIznos() - ocekivaniIznos) > 0.01) {
                throw new Exception("Iznos stavke nije ispravno izračunat!");
            }
        }
    
    // Provera ukupnog iznosa
    double sumaStavki = r.getStavke().stream().mapToDouble(StavkaRacuna::getIznos).sum();
    if (Math.abs(r.getUkupanIznos() - sumaStavki) > 0.01) {
        throw new Exception("Ukupan iznos ne odgovara sumi stavki!");
    }
    
    // Provera popusta
    double ocekivaniIznosSaPopustom = r.getUkupanIznos() - (r.getUkupanIznos() * r.getPopust() / 100);
    if (Math.abs(r.getIznosSaPopustom() - ocekivaniIznosSaPopustom) > 0.01) {
        throw new Exception("Iznos sa popustom nije ispravno izračunat!");
    }
}
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        Racun r = (Racun) odo;

        // Prvo proveri koji sve ID-evi postoje
        String upitProvera = "SELECT MAX(idRacun) FROM racun";
        Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        ResultSet rs = st.executeQuery(upitProvera);
        int maxId = 0;
        if (rs.next()) {
            maxId = rs.getInt(1);
        }
        rs.close();
        System.out.println("Trenutni max ID u racun: " + maxId);

        // INSERT sa eksplicitnim ID-om (pošto nije auto_increment)
        int noviId = maxId + 1;
        String upitRacun = "INSERT INTO racun (idRacun, datumIzdavanja, ukupanIznos, popust, iznosSaPopustom, idProdavac, idMusterija) VALUES ("
                    + noviId + ",'" 
                    + new java.sql.Date(r.getDatumIzdavanja().getTime()) + "'," 
                    + r.getUkupanIznos() + ","
                    + r.getPopust() + ","
                    + r.getIznosSaPopustom() + ","
                    + r.getProdavac().getIdProdavac() + ","
                    + r.getMusterija().getIdMusterija() + ")";

        System.out.println("SQL racun: " + upitRacun);

        st.executeUpdate(upitRacun);
        r.setIdRacun(noviId);

        // Sačuvaj stavke
        int rb = 1;
        for (StavkaRacuna stavka : r.getStavke()) {
            String upitStavka = "INSERT INTO stavkaracuna (idRacun, rb, kolicina, cena, iznos, idLjubimac) VALUES ("
                        + noviId + "," + rb + "," 
                        + stavka.getKolicina() + "," 
                        + stavka.getCena() + "," 
                        + stavka.getIznos() + "," 
                        + stavka.getLjubimac().getIdLjubimac() + ")";

            System.out.println("SQL stavka " + rb + ": " + upitStavka);
            st.executeUpdate(upitStavka);
            rb++;
        }

        st.close();
    }
}
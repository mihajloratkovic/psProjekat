package so.radnasmena;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.RadnaSmena;
import so.OpstaSistemskaOperacija;
import java.sql.Statement;
import java.util.List;

public class SOUbaciRadnuSmenu extends OpstaSistemskaOperacija {
    
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        RadnaSmena rs = (RadnaSmena) odo;

        if (rs.getNaziv() == null || rs.getNaziv().trim().isEmpty()) {
            throw new Exception("Naziv smene je obavezan!");
        }
        if (rs.getVremePocetka() == null || rs.getVremePocetka().trim().isEmpty()) {
            throw new Exception("Vreme početka je obavezno!");
        }
        if (rs.getVremeZavrsetka() == null || rs.getVremeZavrsetka().trim().isEmpty()) {
            throw new Exception("Vreme završetka je obavezno!");
        }

        // Provera formata vremena (HH:MM)
        if (!rs.getVremePocetka().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            throw new Exception("Vreme početka mora biti u formatu HH:MM!");
        }
        if (!rs.getVremeZavrsetka().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            throw new Exception("Vreme završetka mora biti u formatu HH:MM!");
        }

        // Provera da li već postoji smena sa istim nazivom
        String whereUslov = "naziv = '" + rs.getNaziv() + "'";
        List<OpstiDomenskiObjekat> lista = DBBroker.getInstance().vratiSaUslovom(new RadnaSmena(), whereUslov);
        if (!lista.isEmpty()) {
            throw new Exception("Radna smena sa ovim nazivom već postoji!");
        }
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        RadnaSmena r = (RadnaSmena) odo;
        
        String upit = "INSERT INTO radnasmena (naziv, vremePocetka, vremeZavrsetka) VALUES ('" 
                    + r.getNaziv() + "','" 
                    + r.getVremePocetka() + "','" 
                    + r.getVremeZavrsetka() + "')";
        
        System.out.println("SQL: " + upit);
        
        Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        st.executeUpdate(upit);
        st.close();
    }
}
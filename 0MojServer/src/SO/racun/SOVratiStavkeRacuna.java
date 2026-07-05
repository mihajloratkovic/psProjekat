package so.racun;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.StavkaRacuna;
import java.util.List;
import so.OpstaSistemskaOperacija;

public class SOVratiStavkeRacuna extends OpstaSistemskaOperacija {
    
    private List<OpstiDomenskiObjekat> lista;
    private int idRacuna;
    
    public SOVratiStavkeRacuna(int idRacuna) {
        this.idRacuna = idRacuna;
    }
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }
    
    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        // Nema preduslova
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        String upit = "SELECT s.*, l.naziv, l.kategorija, l.cena as l_cena "
                    + "FROM stavkaracuna s "
                    + "JOIN ljubimac l ON s.idLjubimac = l.idLjubimac "
                    + "WHERE s.idRacun = " + idRacuna + " "
                    + "ORDER BY s.rb";
        
        System.out.println("SQL vrati stavke: " + upit);
        
        java.sql.Statement st = DBBroker.getInstance().getKonekcija().createStatement();
        java.sql.ResultSet rs = st.executeQuery(upit);
        
        lista = new StavkaRacuna().ucitajListu(rs);
        
        rs.close();
        st.close();
    }
}
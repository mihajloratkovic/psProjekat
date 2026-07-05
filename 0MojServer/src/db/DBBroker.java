package db;

import domen.OpstiDomenskiObjekat;
import java.sql.*;
import java.util.List;

public class DBBroker {

    private static DBBroker instance;
    private Connection konekcija;

    private DBBroker() throws Exception {
        try {
            // Učitavanje MySQL drajvera
            Class.forName("com.mysql.jdbc.Driver");
            
            // Učitavanje konfiguracije iz db.config fajla
            DBProperties dbProperties = new DBProperties();
            String url = dbProperties.vratiDBURL();
            String user = dbProperties.vratiDBKorisnik();
            String password = dbProperties.vratiDBSifra();
            
            // Uspostavljanje konekcije
            konekcija = DriverManager.getConnection(url, user, password);
            konekcija.setAutoCommit(false); // Ručno upravljanje transakcijama
            
            System.out.println("Konekcija sa bazom uspostavljena.");
            
        } catch (ClassNotFoundException e) {
            throw new Exception("MySQL drajver nije pronađen: " + e.getMessage());
        } catch (SQLException e) {
            throw new Exception("Greška pri povezivanju sa bazom: " + e.getMessage());
        }
    }

    public static DBBroker getInstance() throws Exception {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }

    public Connection getKonekcija() {
        return konekcija;
    }

    // --- Transakcije ---
    public void potvrdiTransakciju() throws SQLException {
        konekcija.commit();
    }

    public void ponistiTransakciju() throws SQLException {
        konekcija.rollback();
    }

    // --- SELECT (vrati sve) ---
    public List<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat odo) throws Exception {
        String upit = "SELECT * FROM " + odo.vratiNazivTabele();
        System.out.println(upit);
        try (Statement st = konekcija.createStatement();
             ResultSet rs = st.executeQuery(upit)) {
            return odo.ucitajListu(rs);
        }
    }

    // --- SELECT (sa uslovom) ---
    public List<OpstiDomenskiObjekat> vratiSaUslovom(OpstiDomenskiObjekat odo, String whereUslov) throws Exception {
        String upit = "SELECT * FROM " + odo.vratiNazivTabele() + " WHERE " + whereUslov;
        System.out.println(upit);
        try (Statement st = konekcija.createStatement();
             ResultSet rs = st.executeQuery(upit)) {
            return odo.ucitajListu(rs);
        }
    }

    // --- INSERT (osnovni) ---
    public void ubaci(OpstiDomenskiObjekat odo) throws Exception {
        String upit = "INSERT INTO " + odo.vratiNazivTabele() + " VALUES (" + odo.vratiVrednostiZaInsert() + ")";
        System.out.println(upit);
        try (Statement st = konekcija.createStatement()) {
            st.executeUpdate(upit);
        }
    }

    // --- INSERT (koji vraća generisani ključ) ---
    public int ubaciIVratiKljuc(OpstiDomenskiObjekat odo) throws Exception {
        String upit = "INSERT INTO " + odo.vratiNazivTabele() + " VALUES (" + odo.vratiVrednostiZaInsert() + ")";
        System.out.println(upit);
        try (PreparedStatement ps = konekcija.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    // --- UPDATE ---
    public void izmeni(OpstiDomenskiObjekat odo) throws Exception {
        String upit = "UPDATE " + odo.vratiNazivTabele() + " SET " + odo.vratiVrednostiZaUpdate() + " WHERE " + odo.vratiUslovZaUpdate();
        System.out.println(upit);
        try (Statement st = konekcija.createStatement()) {
            st.executeUpdate(upit);
        }
    }

    // --- DELETE ---
    public void obrisi(OpstiDomenskiObjekat odo) throws Exception {
        String upit = "DELETE FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslovZaBrisanje();
        System.out.println(upit);
        try (Statement st = konekcija.createStatement()) {
            st.executeUpdate(upit);
        }
    }
}
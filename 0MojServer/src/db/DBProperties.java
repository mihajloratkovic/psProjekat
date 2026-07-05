package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
    
    private Properties properties;
    
    public DBProperties() throws IOException {
        properties = new Properties();
        // Učitavanje iz fajla db.config 
        try (FileInputStream fis = new FileInputStream("db.config")) {
            properties.load(fis);
        }
    }

    public String vratiDBURL() {
        return properties.getProperty(DBKonstante.URL);
    }
    
    public String vratiDBKorisnik() {
        return properties.getProperty(DBKonstante.USERNAME);
    }
     
    public String vratiDBSifra() {
        return properties.getProperty(DBKonstante.PASSWORD);
    }
      
    public String vratiDBPort() {
        return properties.getProperty(DBKonstante.PORT);
    }
}
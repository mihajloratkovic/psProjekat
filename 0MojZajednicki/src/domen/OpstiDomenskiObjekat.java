package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OpstiDomenskiObjekat extends Serializable{

    String vratiNazivTabele();

    String vratiVrednostiZaInsert();

    String vratiVrednostiZaUpdate();

    String vratiUslovZaUpdate();

    String vratiUslovZaBrisanje();

    List<OpstiDomenskiObjekat> ucitajListu(ResultSet rs) throws SQLException;

}
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.KlijentskiZahtevObjekat;
import transfer.ServerskiOdgovorObjekat;

public class Komunikacija {

    private boolean zauzetPrijem = false;
    private static Komunikacija instanca;
    private Socket s;

    private Komunikacija() {
        try {
            s = new Socket("localhost", 9000);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Komunikacija getInstanca() {
        if (instanca == null) {
            instanca = new Komunikacija();
        }
        return instanca;
    }

    public ServerskiOdgovorObjekat primi() {
        zauzetPrijem = true;
        ServerskiOdgovorObjekat soo = new ServerskiOdgovorObjekat();

        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            soo = (ServerskiOdgovorObjekat) ois.readObject();
            zauzetPrijem = false;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soo;
    }

    public void posalji(KlijentskiZahtevObjekat kzo) throws InterruptedException {
        try {
            if (zauzetPrijem) {
                sleep(1000);
            }
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(kzo);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isZauzetPrijem() {
        return zauzetPrijem;
    }

    public void setZauzetPrijem(boolean zauzetPrijem) {
        this.zauzetPrijem = zauzetPrijem;
    }
}
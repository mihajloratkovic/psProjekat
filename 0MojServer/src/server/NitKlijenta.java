package server;

import db.DBBroker;
import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import domen.Prodavac;
import domen.Racun;
import domen.Ljubimac;
import domen.RadnaSmena;
import domen.TipMusterije;
import domen.StavkaRacuna;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Kontroler;
import operacije.Operacije;
import transfer.KlijentskiZahtevObjekat;
import transfer.ServerskiOdgovorObjekat;
import java.sql.Statement;
import java.sql.ResultSet;

public class NitKlijenta extends Thread {

    private List<NitKlijenta> klijenti;
    private Socket socket;
    private Prodavac prijavljeniProdavac;
    private boolean kraj;

    public NitKlijenta(List<NitKlijenta> klijenti, Socket socket) {
        this.klijenti = klijenti;
        this.socket = socket;
        this.prijavljeniProdavac = null;
        this.kraj = false;
    }

    @Override
    public void run() {
        while (!kraj) {
            try {
                KlijentskiZahtevObjekat kzo = primiZahtev();
                ServerskiOdgovorObjekat soo = new ServerskiOdgovorObjekat();

                switch (kzo.getOperacija()) {
                    
                    // --- PRIJAVA PRODAVCA (SK8) ---
                    case Operacije.LOGIN_PRODAVAC:
                        try {
                            Prodavac prodavacZaLlogin = (Prodavac) kzo.getObjekatOperacije();

                            List<Prodavac> listaProdavaca = Kontroler.getInstance().pronadjiProdavca(prodavacZaLlogin);

                            if (listaProdavaca.isEmpty()) {
                                throw new Exception("Pogrešno korisničko ime ili lozinka.");
                            }

                            Prodavac pronadjeni = listaProdavaca.get(0);

                            // Provera da li je već ulogovan
                            boolean vecUlogovan = false;
                            for (NitKlijenta nit : klijenti) {
                                if (nit.prijavljeniProdavac != null && 
                                    nit.prijavljeniProdavac.getIdProdavac() == pronadjeni.getIdProdavac()) {
                                    vecUlogovan = true;
                                    break;
                                }
                            }

                            if (vecUlogovan) {
                                soo.setPoruka("Prodavac je već prijavljen na sistem.");
                                soo.setUspesno(false);
                            } else {
                                System.out.println("Datum zaposlenja: " + pronadjeni.getDatumZaposlenja());
                                this.prijavljeniProdavac = pronadjeni;

                                
                                Kontroler.getInstance().dodajUlogovanogProdavca(pronadjeni);

                                soo.setObjekatOperacija(pronadjeni);
                                soo.setPoruka("Uspešno ste se prijavili, " + pronadjeni.getIme() + " " + pronadjeni.getPrezime());
                                soo.setUspesno(true);
                            }

                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da Vas prijavi: " + ex.getMessage());
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- VRATI SVE PRODAVCE (za combo box) ---
                    case Operacije.VRATI_SVE_PRODAVCE:
                        try {
                            List<Prodavac> listaProdavaca = Kontroler.getInstance().vratiSveProdavce();
                            soo.setObjekatOperacija(listaProdavaca);
                            soo.setPoruka("Sistem je pronašao prodavce.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da pronađe prodavce.");
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- VRATI SVE MUSTERIJE (za combo box) ---
                    case Operacije.VRATI_SVE_MUSTERIJE:
                        try {
                            List<Musterija> listaMusterija = Kontroler.getInstance().vratiSveMusterije();
                            soo.setObjekatOperacija(listaMusterija);
                            soo.setPoruka("Sistem je pronašao musterije.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da pronađe musterije.");
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- VRATI SVE LJUBIMCE (za combo box) ---
                    case Operacije.VRATI_SVE_LJUBIMCE:
                        try {
                            List<Ljubimac> listaLjubimaca = Kontroler.getInstance().vratiSveLjubimce();
                            soo.setObjekatOperacija(listaLjubimaca);
                            soo.setPoruka("Sistem je pronašao ljubimce.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da pronađe ljubimce.");
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- VRATI SVE TIPOVE MUSTERIJE (za combo box) ---
                    case Operacije.VRATI_SVE_TIPOVE_MUSTERIJE:
                        try {
                            List<TipMusterije> listaTipova = Kontroler.getInstance().vratiSveTipoveMusterije();
                            soo.setObjekatOperacija(listaTipova);
                            soo.setPoruka("Sistem je pronašao tipove musterije.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da pronađe tipove musterije.");
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- VRATI SVE RADNE SMENE (za combo box) ---
                    case Operacije.VRATI_SVE_RADNE_SMENE:
                        try {
                            List<RadnaSmena> listaSmena = Kontroler.getInstance().vratiSveRadneSmene();
                            soo.setObjekatOperacija(listaSmena);
                            soo.setPoruka("Sistem je pronašao radne smene.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da pronađe radne smene.");
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- KREIRAJ RACUN (SK1) ---
                    case Operacije.KREIRAJ_RACUN:
                        try {
                            Racun noviRacun = (Racun) kzo.getObjekatOperacije();
                            
                            // Postavi prodavca koji kreira racun (ako nije postavljen)
                            if (noviRacun.getProdavac() == null) {
                                noviRacun.setProdavac(prijavljeniProdavac);
                            }
                            
                            Kontroler.getInstance().zapamtiRacun(noviRacun);
                            
                            soo.setObjekatOperacija(noviRacun);
                            soo.setPoruka("Sistem je zapamtio račun.");
                            soo.setUspesno(true);
                            
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da zapamti račun: " + ex.getMessage());
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                        
                        case Operacije.PRETRAZI_RACUN:
                        try {
                            Racun racunZaPretragu = (Racun) kzo.getObjekatOperacije();
                            List<Racun> nadjeniRacuni = Kontroler.getInstance().pretraziRacune(racunZaPretragu);
                            soo.setObjekatOperacija(nadjeniRacuni);
                            soo.setPoruka("Sistem je našao račune po zadatom kriterijumu.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da nađe račune: " + ex.getMessage());
                            soo.setUspesno(false);
                        }
                        break;
                    
                        
                        case Operacije.PROMENI_RACUN:
                            try {
                                Racun racunZaIzmenu = (Racun) kzo.getObjekatOperacije();
                                Kontroler.getInstance().izmeniRacun(racunZaIzmenu);
                                soo.setPoruka("Sistem je izmenio račun.");
                                soo.setUspesno(true);
                            } catch (Exception ex) {
                                soo.setPoruka("Sistem ne može da izmeni račun: " + ex.getMessage());
                                soo.setUspesno(false);
                            }
                            break;
                    // --- KREIRAJ MUSTERIJU (SK4) ---
                    case Operacije.KREIRAJ_MUSTERIJU:
                        try {
                            Musterija novaMusterija = (Musterija) kzo.getObjekatOperacije();
                            Kontroler.getInstance().zapamtiMusteriju(novaMusterija);
                            
                            soo.setObjekatOperacija(novaMusterija);
                            soo.setPoruka("Sistem je zapamtio musteriju.");
                            soo.setUspesno(true);
                            
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da zapamti musteriju: " + ex.getMessage());
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- UBACI RADNU SMENU (SK21) ---
                    case Operacije.UBACI_RADNU_SMENU:
                        try {
                            RadnaSmena smena = (RadnaSmena) kzo.getObjekatOperacije();

                            String upitMax = "SELECT MAX(idRadnaSmena) FROM radnasmena";
                            Statement st = DBBroker.getInstance().getKonekcija().createStatement();
                            ResultSet rs = st.executeQuery(upitMax);
                            int noviId = 1;
                            if (rs.next()) {
                                noviId = rs.getInt(1) + 1;
                            }
                            rs.close();

                            String upit = "INSERT INTO radnasmena (idRadnaSmena, naziv, vremePocetka, vremeZavrsetka) VALUES ("
                                        + noviId + ",'" + smena.getNaziv() + "','" 
                                        + smena.getVremePocetka() + "','" 
                                        + smena.getVremeZavrsetka() + "')";

                            st.executeUpdate(upit);
                            st.close();

                            soo.setPoruka("Sistem je zapamtio radnu smenu.");
                            soo.setUspesno(true);
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da zapamti radnu smenu: " + ex.getMessage());
                            soo.setUspesno(false);
                        }
                        break;
                    
                    // --- PRETRAZI MUSTERIJU (SK5) ---
                    case Operacije.PRETRAZI_MUSTERIJU:
                        try {
                            Musterija musterijaZaPretragu = (Musterija) kzo.getObjekatOperacije();
                            List<Musterija> nadjeneMusterije = Kontroler.getInstance().pretraziMusterije(musterijaZaPretragu);
                            
                            if (nadjeneMusterije.isEmpty()) {
                                throw new Exception("Nema musterija po zadatom kriterijumu.");
                            }
                            
                            soo.setObjekatOperacija(nadjeneMusterije);
                            soo.setPoruka("Sistem je našao musterije po zadatom kriterijumu.");
                            soo.setUspesno(true);
                            
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da nađe musterije: " + ex.getMessage());
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- PROMENI MUSTERIJU (SK6) ---
                    case Operacije.PROMENI_MUSTERIJU:
                        try {
                            Musterija musterijaZaIzmenu = (Musterija) kzo.getObjekatOperacije();
                            Kontroler.getInstance().izmeniMusteriju(musterijaZaIzmenu);
                            
                            soo.setPoruka("Sistem je izmenio musteriju.");
                            soo.setUspesno(true);
                            
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da izmeni musteriju: " + ex.getMessage());
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    
                    // --- OBRISI MUSTERIJU (SK7) ---
                    case Operacije.OBRISI_MUSTERIJU:
                        try {
                            Musterija musterijaZaBrisanje = (Musterija) kzo.getObjekatOperacije();
                            Kontroler.getInstance().obrisiMusteriju(musterijaZaBrisanje);
                            
                            soo.setPoruka("Sistem je obrisao musteriju.");
                            soo.setUspesno(true);
                            
                        } catch (Exception ex) {
                            soo.setPoruka("Sistem ne može da obriše musteriju: " + ex.getMessage());
                            soo.setUspesno(false);
                            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                        
                        
                            
                            //--vrati stavke--
                            
                        case Operacije.VRATI_STAVKE_RACUNA:
                                try {
                                    int idRacuna = (int) kzo.getObjekatOperacije();
                                    List<StavkaRacuna> stavke = Kontroler.getInstance().vratiStavkeRacuna(idRacuna);
                                    soo.setObjekatOperacija(stavke);
                                    soo.setPoruka("Sistem je našao stavke računa.");
                                    soo.setUspesno(true);
                                } catch (Exception ex) {
                                    soo.setPoruka("Sistem ne može da nađe stavke računa: " + ex.getMessage());
                                    soo.setUspesno(false);
                                }
                                break;
                                
                                
                              
                    
                    // --- ODLJAVI SE ---
                    case Operacije.ODJAVA:
                        try {
                            // DODAJ OVO - ukloni iz tabele pre odjave
                            Kontroler.getInstance().obrisiUlogovanogProdavca(this.prijavljeniProdavac);

                            this.prijavljeniProdavac = null;
                            this.kraj = true;
                            soo.setPoruka("Uspešno ste se odjavili.");
                            soo.setUspesno(true);

                        } catch (Exception ex) {
                            soo.setPoruka("Greška pri odjavi.");
                            soo.setUspesno(false);
                        }
                        break;
                    
                    default:
                        soo.setPoruka("Nepoznata operacija.");
                        soo.setUspesno(false);
                        break;
                }
                
                posaljiOdgovor(soo);
                
            } catch (Exception ex) {
                Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
                kraj = true; // Prekini nit ako dođe do greške u komunikaciji
            }
        }
        
        // Zatvori socket i ukloni iz liste
        try {
            socket.close();
            klijenti.remove(this);
            System.out.println("Klijent se odjavio i uklonjen je iz liste.");
        } catch (IOException ex) {
            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void posaljiOdgovor(ServerskiOdgovorObjekat soo) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(soo);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(NitKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private KlijentskiZahtevObjekat primiZahtev() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (KlijentskiZahtevObjekat) ois.readObject();
    }
}
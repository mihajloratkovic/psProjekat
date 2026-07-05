package logic;

import db.DBBroker;
import domen.Musterija;
import domen.OpstiDomenskiObjekat;
import domen.Prodavac;
import domen.Racun;
import domen.Ljubimac;
import domen.RadnaSmena;
import domen.TipMusterije;
import domen.StavkaRacuna;
import forme.FormaServer;
import java.util.ArrayList;
import java.util.List;
import so.OpstaSistemskaOperacija;
import so.musterija.SOIzmeniMusteriju;
import so.musterija.SOKreirajMusteriju;
import so.musterija.SOObrisiMusteriju;
import so.musterija.SOPretraziMusterije;
import so.musterija.SOVratiSveMusterije;
import so.prodavac.SOPrijaviProdavca;
import so.prodavac.SOVratiSveProdavce;
import so.ljubimac.SOVratiSveLjubimce;
import so.tipmusterije.SOVratiSveTipoveMusterije;
import so.radnasmena.SOUbaciRadnuSmenu;
import so.radnasmena.SOVratiSveRadneSmene;
import so.racun.SOKreirajRacun;
import so.racun.SOPromeniRacun;
import so.racun.SOPretraziRacune;
import so.racun.SOVratiStavkeRacuna;


public class Kontroler {
    
    private static Kontroler instance;
    private DBBroker dbBroker;
    private FormaServer forma;
    
    private Kontroler() throws Exception {
        dbBroker = DBBroker.getInstance();
    }
    
    public static Kontroler getInstance() throws Exception {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }
    
    public void setForma(FormaServer forma) {
        this.forma = forma;
    }
    
    public FormaServer getForma() {
        return forma;
    }
    
    // --- PRODAVAC ---
    
    public List<Prodavac> pronadjiProdavca(Prodavac prodavac) throws Exception {
        SOPrijaviProdavca so = new SOPrijaviProdavca();
        so.izvrsi(prodavac);
        
        List<Prodavac> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((Prodavac) odo);
        }
        return rezultat;
    }
    
    public List<Prodavac> vratiSveProdavce() throws Exception {
        SOVratiSveProdavce so = new SOVratiSveProdavce();
        so.izvrsi(new Prodavac());
        
        List<Prodavac> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((Prodavac) odo);
        }
        return rezultat;
    }
    
    // --- MUSTERIJA ---
    
    public void zapamtiMusteriju(Musterija musterija) throws Exception {
        SOKreirajMusteriju so = new SOKreirajMusteriju();
        so.izvrsi(musterija);
    }
    
    public List<Musterija> vratiSveMusterije() throws Exception {
        SOVratiSveMusterije so = new SOVratiSveMusterije();
        so.izvrsi(new Musterija());
        
        List<Musterija> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((Musterija) odo);
        }
        return rezultat;
    }
    
    public List<Musterija> pretraziMusterije(Musterija musterija) throws Exception {
        SOPretraziMusterije so = new SOPretraziMusterije();
        so.izvrsi(musterija);
        
        List<Musterija> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((Musterija) odo);
        }
        return rezultat;
    }
    
    public void izmeniMusteriju(Musterija musterija) throws Exception {
        SOIzmeniMusteriju so = new SOIzmeniMusteriju();
        so.izvrsi(musterija);
    }
    
    public void obrisiMusteriju(Musterija musterija) throws Exception {
        SOObrisiMusteriju so = new SOObrisiMusteriju();
        so.izvrsi(musterija);
    }
    
    // --- LJUBIMAC ---
    
    public List<Ljubimac> vratiSveLjubimce() throws Exception {
        SOVratiSveLjubimce so = new SOVratiSveLjubimce();
        so.izvrsi(new Ljubimac());
        
        List<Ljubimac> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((Ljubimac) odo);
        }
        return rezultat;
    }
    
    
    
    
    // --- TIP MUSTERIJE ---
    
    public List<TipMusterije> vratiSveTipoveMusterije() throws Exception {
        SOVratiSveTipoveMusterije so = new SOVratiSveTipoveMusterije();
        so.izvrsi(new TipMusterije());
        
        List<TipMusterije> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((TipMusterije) odo);
        }
        return rezultat;
    }
    
    // --- RADNA SMENA ---
    
    public void zapamtiRadnuSmenu(RadnaSmena radnaSmena) throws Exception {
        SOUbaciRadnuSmenu so = new SOUbaciRadnuSmenu();
        so.izvrsi(radnaSmena);
    }
    
    public List<RadnaSmena> vratiSveRadneSmene() throws Exception {
        SOVratiSveRadneSmene so = new SOVratiSveRadneSmene();
        so.izvrsi(new RadnaSmena());
        
        List<RadnaSmena> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((RadnaSmena) odo);
        }
        return rezultat;
    }
    
    // --- RACUN ---
    
    public void zapamtiRacun(Racun racun) throws Exception {
        SOKreirajRacun so = new SOKreirajRacun();
        so.izvrsi(racun);
    }
    
    public void izmeniRacun(Racun racun) throws Exception {
        SOPromeniRacun so = new SOPromeniRacun();
        so.izvrsi(racun);
    }
    
    public List<Racun> pretraziRacune(Racun racun) throws Exception {
        SOPretraziRacune so = new SOPretraziRacune(racun.getKriterijumPretrage(), racun.getTipPretrage());
        so.izvrsi(racun);

        List<Racun> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((Racun) odo);
        }
        return rezultat;
    }
    
    // --- ZA TABELU ULOGOVANIH PRODAVACA ---
    
    public void dodajUlogovanogProdavca(Prodavac prodavac) {
        if (forma != null) {
            forma.vratiTabeluProdavaca().dodajProdavca(prodavac);
        }
    }
    
    public void obrisiUlogovanogProdavca(Prodavac prodavac) {
        if (forma != null) {
            forma.vratiTabeluProdavaca().obrisiProdavca(prodavac);
        }
    }
    
    public List<StavkaRacuna> vratiStavkeRacuna(int idRacuna) throws Exception {
        SOVratiStavkeRacuna so = new SOVratiStavkeRacuna(idRacuna);
        so.izvrsi(new StavkaRacuna());

        List<StavkaRacuna> rezultat = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : so.getLista()) {
            rezultat.add((StavkaRacuna) odo);
        }
        return rezultat;
}
    
        
}
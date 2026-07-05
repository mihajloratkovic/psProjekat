package operacije;

import java.io.Serializable;

public class Operacije implements Serializable{

    public static final int LOGIN_PRODAVAC = 1;
    public static final int ODJAVA = 2;

    public static final int VRATI_SVE_PRODAVCE = 3;
    public static final int VRATI_SVE_MUSTERIJE = 4;
    public static final int VRATI_SVE_LJUBIMCE = 5;
    public static final int VRATI_SVE_TIPOVE_MUSTERIJE = 6;
    public static final int VRATI_SVE_RADNE_SMENE = 7;

    public static final int KREIRAJ_RACUN = 8;
    public static final int PROMENI_RACUN = 9;
    public static final int PRETRAZI_RACUN = 10;

    public static final int KREIRAJ_MUSTERIJU = 11;
    public static final int PROMENI_MUSTERIJU = 12;
    public static final int OBRISI_MUSTERIJU = 13;
    public static final int PRETRAZI_MUSTERIJU = 14;

    public static final int UBACI_RADNU_SMENU = 15;
    
    public static final int VRATI_STAVKE_RACUNA = 16;
    
       
}
package logic;

import domen.Prodavac;

public class Kontroler {

    private static Kontroler instanca;
    private Prodavac prijavljeniProdavac;

    private Kontroler() {
    }

    public static Kontroler getInstanca() {
        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }

    public Prodavac getPrijavljeniProdavac() {
        return prijavljeniProdavac;
    }

    public void setPrijavljeniProdavac(Prodavac prijavljeniProdavac) {
        this.prijavljeniProdavac = prijavljeniProdavac;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package transfer;

import java.io.Serializable;


public class KlijentskiZahtevObjekat implements Serializable {
    
    private int operacija;
    private Object objekatOperacije;

    public KlijentskiZahtevObjekat() {
    }
    
    
    public int getOperacija() {
        return operacija;
    }

    public void setOperacija(int operacija) {
        this.operacija = operacija;
    }

    public Object getObjekatOperacije() {
        return objekatOperacije;
    }

    public void setObjekatOperacije(Object objekatOperacije) {
        this.objekatOperacije = objekatOperacije;
    }
    
    
    
}

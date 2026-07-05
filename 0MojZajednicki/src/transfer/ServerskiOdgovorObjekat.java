/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package transfer;

import java.io.Serializable;


public class ServerskiOdgovorObjekat implements Serializable {
    
    private String poruka;
    private Object objekatOperacija;
    private boolean uspesno;
    private int operacija;
    
    public ServerskiOdgovorObjekat() {
    }

    public ServerskiOdgovorObjekat(String poruka, Object objekatOperacija, boolean uspesno) {
        this.poruka = poruka;
        this.objekatOperacija = objekatOperacija;
        this.uspesno = uspesno;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public Object getObjekatOperacija() {
        return objekatOperacija;
    }

    public void setObjekatOperacija(Object objekatOperacija) {
        this.objekatOperacija = objekatOperacija;
    }

    public boolean isUspesno() {
        return uspesno;
    }

    public void setUspesno(boolean uspesno) {
        this.uspesno = uspesno;
    }

    public int getOperacija() {
        return operacija;
    }

    public void setOperacija(int operacija) {
        this.operacija = operacija;
    }
    
   
    
    
}

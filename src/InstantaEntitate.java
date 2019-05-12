/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vld
 */
public class InstantaEntitate {

    String numeEntitate;
    String primaryKey;
    int RF;
    List<String> listaValoriAtribute;
    List<String> listNumeAtribute;
    List<String> listTipAtribute;

    public InstantaEntitate() {
        listaValoriAtribute = new ArrayList<>();
        listNumeAtribute= new ArrayList<>();
        listTipAtribute = new ArrayList<>();
    }

    public void setNumeEntitate(String numeEntitate) {
        this.numeEntitate = numeEntitate;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setRF(int RF) {
        this.RF = RF;
    }

    public String getNumeEntitate() {
        return numeEntitate;
    }
    
}

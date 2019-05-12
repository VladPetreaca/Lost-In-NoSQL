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
public class Entitate {

    String numeEntitate;
    int RF;
    List<String> Types;
    List<String> Atributes;

    public Entitate(String nume, int RF, int nrAtribute) {
        numeEntitate = nume;
        this.RF = RF;
        Types = new ArrayList<String>(nrAtribute);
        Atributes = new ArrayList<String>(nrAtribute);
    }

}

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
public class DataBase {
    String numeBaza;
    int capacitateMax;
    List<Nod> ListaNoduri;
    
    /**
     *
     * @param nume
     * @param nrNoduri
     * @param Cap
     */
    public DataBase(String nume,int nrNoduri,int Cap) {
        numeBaza=nume;
        ListaNoduri = new ArrayList<Nod>(nrNoduri);
        capacitateMax=Cap;
    }
    
    /**
     *
     * @param ins
     * @param db
     */
    public void adaugareInstanta(InstantaEntitate ins,DataBase db) {
       int auxRF = ins.RF;//RF este factor de replicare si arata numarul de aparitii pe noduri 
       for(int i=0;i<auxRF;i++) {
           if(db.ListaNoduri.get(i).listaInstanteEntitati.size() < capacitateMax) {//daca se atingea capacitatea maxima se incrementeaza RF auxuliar pentru a putea trece la alt nod
               db.ListaNoduri.get(i).listaInstanteEntitati.add(ins);
           }
           else {
              auxRF++;
           }
       }
   }
    
    

   
    
    
}

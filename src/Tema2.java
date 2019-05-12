/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vld
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArraySet;

public class Tema2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        FileReader file = new FileReader(args[0]);
        FileWriter out = new FileWriter(args[0] + "_out");
        BufferedReader bf = new BufferedReader(file);
        BufferedWriter writer = new BufferedWriter(out);

        Scanner lineScanner;
        String line;
        line = bf.readLine();
        String lineSelect;
        String numeBazaDate = null;

        int nrAtribute = 0;
        int NrNoduri = 0;
        int CapacitateMax = 0;
        int RF;

        DataBase db = null;
        Nod nod = null;
        Entitate entitate;
        List<Entitate> listaEntitati = new ArrayList<Entitate>();

        while (line != null) {
            lineSelect = line;
            lineScanner = new Scanner(lineSelect);
            if (lineSelect.contains("CREATEDB ")) {
                lineScanner.next();
                if (lineScanner.hasNext()) {
                    numeBazaDate = lineScanner.next();
                    if (lineScanner.hasNextInt()) {
                        NrNoduri = lineScanner.nextInt();
                        if (lineScanner.hasNextInt()) {
                            CapacitateMax = lineScanner.nextInt();
                        }
                    }
                }
                db = new DataBase(numeBazaDate, NrNoduri, CapacitateMax);//creez baza de date cu numele,capacitatea maxima de noduri si numarul respectiv de noduri
                for (int i = 0; i < NrNoduri; i++) { // creez dimensiunea listei de noduri
                    nod = new Nod();
                    db.ListaNoduri.add(nod);
                }
            } else if (lineSelect.contains("CREATE ")) {
                if (lineScanner.hasNext()) {
                    lineScanner.next();
                    if (lineScanner.hasNext()) {
                        String numeEntitate = lineScanner.next();
                        if (lineScanner.hasNextInt()) {
                            RF = lineScanner.nextInt();
                            if (lineScanner.hasNextInt()) {
                                nrAtribute = lineScanner.nextInt();
                                entitate = new Entitate(numeEntitate, RF, nrAtribute);//creez o entitate cu datele respective
                                for (int i = 0; i < nrAtribute * 2; i++) {//Stiind numarul de atribute,retin in 2 liste:tipul atributelor si numele lor
                                    if (lineScanner.hasNext()) {
                                        String aux = lineScanner.next();
                                        if (i % 2 == 0) {
                                            entitate.Atributes.add(aux);
                                        } else {
                                            entitate.Types.add(aux);
                                        }
                                    }
                                }
                                listaEntitati.add(entitate);//adaug enitatea in lista de entitati
                            }
                        }
                    }
                }
            } else if (lineSelect.contains("INSERT ")) {

                lineScanner.next();
                if (lineScanner.hasNext()) {
                    InstantaEntitate instanta = new InstantaEntitate();
                    instanta.setNumeEntitate(lineScanner.next());
                    for (int i = 0; i < listaEntitati.size(); i++) {
                        if (instanta.numeEntitate.equals(listaEntitati.get(i).numeEntitate)) {// caut in lista de entitati daca exista o entitate cu numele citit din fisier
                            instanta.setRF(listaEntitati.get(i).RF);
                            for (int j = 0; j < listaEntitati.get(i).Atributes.size(); j++) {//salvez datele instantei gasite intr-o instanta temporara
                                if (j == 0) {
                                    instanta.listNumeAtribute.add(listaEntitati.get(i).Atributes.get(j));
                                    instanta.setPrimaryKey(listaEntitati.get(i).Atributes.get(0));
                                    instanta.listTipAtribute.add(listaEntitati.get(i).Types.get(j));
                                } else {
                                    instanta.listNumeAtribute.add(listaEntitati.get(i).Atributes.get(j));
                                    instanta.listTipAtribute.add(listaEntitati.get(i).Types.get(j));
                                }
                            }
                        }
                    }
                    while (lineScanner.hasNext()) {
                        instanta.listaValoriAtribute.add(lineScanner.next());

                    }
                    db.adaugareInstanta(instanta, db);//adaug instanta entitatii in baza de date
                    instanta = null;
                }

            } else if (lineSelect.contains("SNAPSHOTDB")) {//Fac afisarea ceruta in cerinta temei
                if (db.ListaNoduri.get(0).listaInstanteEntitati.size() != 0) {
                    for (int i = 0; i < db.ListaNoduri.size(); i++) {
                        if (db.ListaNoduri.get(i).listaInstanteEntitati.size() != 0) {
                            writer.write("Nod" + (i + 1));
                            writer.newLine();
                            for (int j = db.ListaNoduri.get(i).listaInstanteEntitati.size() - 1; j >= 0; j--) {
                                writer.write(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).getNumeEntitate() + " ");
                                int poz = db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listTipAtribute.indexOf("Float");
                                for (int k = 0; k < db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listaValoriAtribute.size(); k++) {
                                    if (k != poz) {
                                        writer.write(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listNumeAtribute.get(k) + ":");
                                        writer.write(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listaValoriAtribute.get(k) + " ");
                                    } else {
                                        writer.write(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listNumeAtribute.get(k) + ":");
                                        Float auxFloat = Float.parseFloat(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listaValoriAtribute.get(k));
                                        Integer auxInt = Math.round(auxFloat);
                                        if (auxFloat - auxInt == 0) {
                                            writer.write(auxInt + " ");
                                        } else {
                                            writer.write(auxFloat.toString() + " ");
                                        }
                                    }
                                }
                                writer.newLine();
                            }
                        }
                    }
                } else {
                    writer.write("EMPTY DB");
                    writer.newLine();
                }
            } else if (lineSelect.contains("GET ")) {
                lineScanner.next();
                List<Integer> listaPozitii = new ArrayList<>();
                if (lineScanner.hasNext()) {
                    String nume = lineScanner.next();
                    int poz1 = -1;
                    int poz2 = -1;
                    if (lineScanner.hasNext()) {
                        String pKey = lineScanner.next();

                        for (int i = 0; i < listaEntitati.size(); i++) {
                            if (nume.equals(listaEntitati.get(i).numeEntitate)) {//caut numele in lista de entitati
                                for (int j = 0; j < db.ListaNoduri.size(); j++) {
                                    for (int k = 0; k < db.ListaNoduri.get(j).listaInstanteEntitati.size(); k++) {//caut in fiecare nod daca exista o instanta cu cheia primara si numele citite din fisier
                                        if (db.ListaNoduri.get(j).listaInstanteEntitati.get(k).listaValoriAtribute.get(0).equals(pKey) && db.ListaNoduri.get(j).listaInstanteEntitati.get(k).numeEntitate.equals(nume)) {
                                            listaPozitii.add(j);
                                            poz2 = j;//retin pozitia nodului
                                            poz1 = k;//retin pozitia instantei entitatii
                                        }
                                    }
                                }
                            }
                        }
                        if (!listaPozitii.isEmpty()) {//afisez mai intai nodurile

                            for (int i = 0; i < listaPozitii.size(); i++) {
                                writer.write("Nod" + (listaPozitii.get(i) + 1) + " ");

                            }
                            //fac afisarea ceruta in cerinta
                            int aux = poz2;
                            writer.write(nume + " ");
                            writer.write(db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listNumeAtribute.get(0) + ":");
                            writer.write(pKey + " ");
                            for (int i = 1; i < db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listNumeAtribute.size(); i++) {
                                if (i < db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listNumeAtribute.size() - 1) {
                                    writer.write(db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listNumeAtribute.get(i) + ":");
                                    writer.write(db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listaValoriAtribute.get(i) + " ");
                                } else {
                                    writer.write(db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listNumeAtribute.get(i) + ":");
                                    writer.write(db.ListaNoduri.get(aux).listaInstanteEntitati.get(poz1).listaValoriAtribute.get(i));
                                }
                            }
                            writer.newLine();
                        } else {
                            writer.write("NO INSTANCE FOUND");
                            writer.newLine();
                        }
                    }
                }
            } else if (lineSelect.contains("UPDATE ")) {
                lineScanner.next();
                InstantaEntitate instanta = new InstantaEntitate();
                if (lineScanner.hasNext()) {
                    String numeEntitate = lineScanner.next();
                    instanta.setNumeEntitate(numeEntitate);
                    List<String> noiValoriAtribute = new ArrayList<>();
                    List<String> numeAtribute = new ArrayList<>();
                    if (lineScanner.hasNext()) {
                        String pKey = lineScanner.next();
                        instanta.setPrimaryKey(pKey);

                        int contor = 0;
                        while (lineScanner.hasNext()) {//retin valorile si numele atributelor pe care le citesc din fisier

                            if (contor % 2 != 0) {
                                noiValoriAtribute.add(lineScanner.next());
                            } else {
                                numeAtribute.add(lineScanner.next());
                            }
                            contor++;
                        }

                        //copiez intr-o instanta temporara,instanta cu numele si cheia primara pe care le citesc
                        int contorNod = 0;
                        int conditieOprire = 0;
                        for (int j = 0; j < db.ListaNoduri.get(contorNod).listaInstanteEntitati.size(); j++) {
                            if (numeEntitate.equals(db.ListaNoduri.get(contorNod).listaInstanteEntitati.get(j).numeEntitate) && pKey.equals(db.ListaNoduri.get(contorNod).listaInstanteEntitati.get(j).listaValoriAtribute.get(0))) {

                                for (int k = 0; k < db.ListaNoduri.get(contorNod).listaInstanteEntitati.get(j).listTipAtribute.size(); k++) {
                                    instanta.listTipAtribute.add(db.ListaNoduri.get(contorNod).listaInstanteEntitati.get(j).listTipAtribute.get(k));
                                    instanta.listaValoriAtribute.add(db.ListaNoduri.get(contorNod).listaInstanteEntitati.get(j).listaValoriAtribute.get(k));
                                    instanta.listNumeAtribute.add(db.ListaNoduri.get(contorNod).listaInstanteEntitati.get(j).listNumeAtribute.get(k));
                                }
                            } else {

                                if (conditieOprire == db.ListaNoduri.get(contorNod).listaInstanteEntitati.size()) {
                                    contorNod++;
                                    conditieOprire = 0;
                                } else {
                                    conditieOprire++;
                                }
                            }
                        }

                        for (int j = 0; j < db.ListaNoduri.size(); j++) {
                            for (int k = 0; k < db.ListaNoduri.get(j).listaInstanteEntitati.size(); k++) {//caut instanta in fiecare nod 
                                if (db.ListaNoduri.get(j).listaInstanteEntitati.get(k).listaValoriAtribute.get(0).equals(pKey) && db.ListaNoduri.get(j).listaInstanteEntitati.get(k).numeEntitate.equals(numeEntitate)) {

                                    db.ListaNoduri.get(j).listaInstanteEntitati.remove(k);//elimin instanta cand o gasesc
                                    for (int q = 0; q < noiValoriAtribute.size(); q++) {
                                        int index = instanta.listNumeAtribute.indexOf(numeAtribute.get(q));//retin pozitia tipului de atribut pentru a actualiza valoarea
                                        if (index == instanta.listTipAtribute.indexOf("Float")) {//daca este float,se face conversie la int
                                            System.out.println(instanta.listTipAtribute.indexOf("Float"));
                                            Float conversFloat = Float.parseFloat(noiValoriAtribute.get(q));
                                            Integer auxInt = Math.round(conversFloat);
                                            System.out.println(auxInt);
                                            System.out.println(conversFloat);
                                            if (conversFloat - auxInt == 0) {
                                                instanta.listaValoriAtribute.set(index, auxInt.toString());
                                            } else {
                                                instanta.listaValoriAtribute.set(index, conversFloat.toString());
                                            }
                                        } else {
                                            instanta.listaValoriAtribute.set(index, noiValoriAtribute.get(q));
                                        }
                                    }
                                    db.ListaNoduri.get(j).listaInstanteEntitati.add(instanta);//adaug instanta in lista instante in nodurile din care s-a sters
                                }
                            }
                        }
                    }
                }
            } else if (lineSelect.contains("DELETE ")) {
                lineScanner.next();
                if (lineScanner.hasNext()) {
                    String numeEntitate = lineScanner.next();
                    if (lineScanner.hasNext()) {
                        String pKey = lineScanner.next();
                        int cont = 0;
                        for (int i = 0; i < db.ListaNoduri.size(); i++) {//caut in fiecare nod instana cu numele si cheia primara respective,iar cand o gasesc o elimin
                            for (int j = 0; j < db.ListaNoduri.get(i).listaInstanteEntitati.size(); j++) {
                                if (numeEntitate.equals(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).numeEntitate) && pKey.equals(db.ListaNoduri.get(i).listaInstanteEntitati.get(j).listaValoriAtribute.get(0))) {
                                    db.ListaNoduri.get(i).listaInstanteEntitati.remove(j);
                                    cont++;
                                }
                            }
                        }
                        if (cont == 0) {
                            writer.write("NO INSTANCE TO DELETE");
                            writer.newLine();
                        }
                    }
                }
            }
            line = bf.readLine();
        }
        writer.close();
    }
}

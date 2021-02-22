package model.reader;

import Controller.GestVendas;
import model.utils.Crono;
import view.IView;

import java.io.*;


public class ObjectStream implements IObjectStream {

    /**
     * Faz load do gestVendas e View
     * @param gest GestVendas
     * @param view View
     */
    public void loadGestVendas(GestVendas gest, IView view) {
        String fileName = view.printCommand("Nome do ficheiro");

        if (isEmpty(fileName)){
            fileName = "gestVendas.dat";
        }


        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream out= new ObjectInputStream(file);
            Object a = out.readObject();
            GestVendas cc = (GestVendas) a;
            gest = cc;
            } catch (IOException |ClassNotFoundException e) {
            e.printStackTrace();
            }
    }

    /**
     * Faz save do gestVendas e View
     * @param gest GestVendas
     * @param view View
     */
    public void saveGestVendas(GestVendas gest, IView view) {
        String fileName = view.printCommand("Nome do ficheiro");

        if (isEmpty(fileName)){
            fileName = "gestVendas.dat";
        }

        File f = new File(fileName);
        try {
            f.createNewFile();
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out= new ObjectOutputStream(file);
            out.writeObject(gest);
        } catch (IOException e) {
            return;
        }
    }

    /**
     * String e vazia ou nao
     * @param string string a ser comparada
     * @return True se for vazia, False se nao
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}


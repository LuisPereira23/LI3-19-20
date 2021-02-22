package model.data;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

public class Cliente implements Serializable {
    private static final long serialVersionUID = -6886002578446086944L;
    private char letter;
    private int number;

    /**
     * Constructor for client
     * @param clientLine string client "Z5000"
     */
    public Cliente(String clientLine){
        this.letter = clientLine.charAt(0);
        this.number = parseInt(clientLine.substring(1));
    }


    public Cliente(Cliente c){
        this.letter = c.getLetter();
        this.number = c.getNumber();
    }

    /**
     * Clone Client
     * @return Cloned client
     */
    public Cliente clone(){
        return new Cliente(this);
    }

    /**
     * Get da string Codigo Cliente
     * @return Client string
     */
    public String toString() {
        return ""+ letter + number;
    }

    /**
     * Get da Letra do codigo cliente
     * @return client letter
     */
    public char getLetter() {
        return letter;
    }

    /**
     * get do numero do codigo de cliente
     * @return client number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Valida Codigo de Cliente
     * @param line Client string "Z5000"
     * @return Boolen of client validation
     */
    public static boolean validateClient(String line){
        if(line.length() != 5)
            return false;

        if(!Character.isAlphabetic(line.charAt(0)))
            return false;

        for(int i = 1 ; i < 5 ; i++)
            if(!Character.isDigit(line.charAt(i)))
                return false;

        int number = parseInt(line.substring(1));
        if(number < 1000 || number > 5000)
            return false;

        return true;
    }
}

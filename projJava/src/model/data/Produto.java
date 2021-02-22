package model.data;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

public class Produto implements Serializable {
    private static final long serialVersionUID = 506806007340081623L;
    private char[] letters;
    private int number;

    /**
     * Constructor for Product from string
     * @param productLine Product String "AA1001"
     */
    public Produto(String productLine) {
        this.letters = new char[2];
        this.letters[0] = productLine.charAt(0);
        this.letters[1] = productLine.charAt(1);
        this.number = parseInt(productLine.substring(2));
    }

    /**
     * Constructor for product from product
     * @param product Product
     */
    public Produto(Produto product) {
        this.letters = product.getLetters().clone();
        this.number = product.getNumber();
    }

    /**
     * Clone Product
     * @return The clone of a product
     */
    public Produto clone() {
        return new Produto(this);
    }

    /**
     * @return Product string
     */
    public String toString() {
        return "" + letters[0] + letters[1] + number;
    }

    /**
     * Get Letras produto
     * @return Product letters
     */
    public char[] getLetters() {
        return letters;
    }

    /**
     * Get Numero produtos
     * @return Product number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Validates product string
     * @param productLine Product String "AA1001
     * @return Boolean if product is valid
     */
    public static boolean validateProduct(String productLine) {
        if (productLine.length() != 6)
            return false;

        if (!Character.isAlphabetic(productLine.charAt(0)) || !Character.isAlphabetic(productLine.charAt(1)))
            return false;

        for (int i = 2; i < 6; i++)
            if (!Character.isDigit(productLine.charAt(i)))
                return false;

        int number = parseInt(productLine.substring(2));
        if (number < 1000 || number >= 10000)
            return false;

        return true;
    }
}
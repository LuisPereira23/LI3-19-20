package model.data;

import java.io.Serializable;
import java.util.StringTokenizer;

public class Venda implements Serializable {
    private static final long serialVersionUID = -4050066379233655807L;
    private String client;
    private String product;
    private double unitPrice;
    private int quantity;
    private boolean mode;
    private int month;
    private int filial;

    /**
     * Constructor for sale from string
     * @param saleLine sale String "KR1583 77.72 128 P L4891 2 1"
     */
    public Venda(String saleLine){
        StringTokenizer tokenizer = new StringTokenizer(saleLine, " ");

        this.product = tokenizer.nextToken();
        String price        = tokenizer.nextToken();
        String quantity   = tokenizer.nextToken();
        String mode         = tokenizer.nextToken();
        this.client = tokenizer.nextToken();
        String month          = tokenizer.nextToken();
        String filial       = tokenizer.nextToken();

        this.unitPrice = Double.parseDouble(price);
        this.quantity = Integer.parseInt(quantity);
        this.month = Integer.parseInt(month);
        this.filial             = Integer.parseInt(filial);
        this.mode = mode.charAt(0) == 'P';
    }

    /**
     * get Client from sale
     * @return Client
     */
    public String getClient() {
        return client;
    }

    /**
     * get Product from sale
     * @return Product
     */
    public String getProduct() {
        return product;
    }

    /**
     * get Unit Price from sale
     * @return Price per unit
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * get product quantity from sale
     * @return Quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * get Mode from sale
     * @return Mode
     */
    public boolean getMode() {
        return mode;
    }

    /**
     * get sale month
     * @return month of sale
     */
    public int getMonth() {
        return month;
    }

    /**
     * get Filial of sale
     * @return filial
     */
    public int getFilial() {
        return filial;
    }

    /**
     * Validation of sales
     * @return Boolean validating sale
     * @param saleLine sale String "KR1583 77.72 128 P L4891 2 1"
     */
    public static boolean validateSale(String saleLine) {
        StringTokenizer tokenizer = new StringTokenizer(saleLine, " ");
        if(tokenizer.countTokens() != 7)
            return false;

        String productString      = tokenizer.nextToken();
        String priceString        = tokenizer.nextToken();
        String quantityString   = tokenizer.nextToken();
        String modeString         = tokenizer.nextToken();
        String clientString      = tokenizer.nextToken();
        String monthString          = tokenizer.nextToken();
        String filialString       = tokenizer.nextToken();

        if(!Produto.validateProduct(productString))
            return false;

        if(!Cliente.validateClient(clientString))
            return false;

        double  price;
        int     quantity;
        int     month;
        int     filial;

        try{
            price = Double.parseDouble(priceString);
            quantity = Integer.parseInt(quantityString);
            month = Integer.parseInt(monthString);
            filial = Integer.parseInt(filialString);
        }catch (NumberFormatException ignored){
            return false;
        }

        if(modeString.length() == 0 || (modeString.charAt(0) != 'N' && modeString.charAt(0) != 'P'))
            return false;


        if(price < 0 || price >= 1000)
            return false;

        if(quantity < 1 || quantity > 200)
            return false;

        if(month < 1 || month > 12)
            return false;

        if(filial < 1 || filial > 3)
            return false;

        return true;
    }
}
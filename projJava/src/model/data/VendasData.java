package model.data;

import java.io.Serializable;

public class VendasData implements Serializable {

    private static final long serialVersionUID = -1172502720278407900L;
    private int quantity;
    private double unitPrice;
    private String client;
    private String product;

    /**
     * Constructor for sales Data from sale
     * @param sale Sale
     */
    public VendasData(Venda sale) {
        this.quantity = sale.getQuantity();
        this.unitPrice = sale.getUnitPrice();
        this.client = sale.getClient();
        this.product = sale.getProduct();
    }

    /**
     * get product quantity from sale
     * @return Quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * get Unit Price from sale
     * @return Price per unit
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * get Client from sale
     * @return Client
     */
    public String getClient(){
        return client;
    }

    /**
     * get Product from sale
     * @return Product
     */
    public String getProduct(){
        return product;
    }

    /**
     * get Total Price of sale
     * @return Price
     */
    public double getTotalPrice(){
        return unitPrice * quantity;
    }
}

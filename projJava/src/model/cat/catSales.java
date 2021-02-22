package model.Cat;

import model.data.Venda;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class catSales implements ICatVendas, Serializable {

    private static final long serialVersionUID = 4575202977665119181L;
    private Set<Venda> sales;

    /**
     * Constructor for the sales catalog
     */
    public catSales() {
        this.sales = new HashSet<>();
    }

    /**
     * Removes invalid sales from catalog
     * @param catCli Client catalog
     * @param catProd Product catalog
     */
    public void removeInvalidSales(ICat catCli, ICat catProd){
        Set<Venda> invalidSales = new HashSet<>();
        for(Venda v : sales)
            if(!catCli.isInCat(v.getClient()) || !catProd.isInCat(v.getProduct()))
                invalidSales.add(v);
        sales.removeAll(invalidSales);
    }

    /**
     * Adds sale in string form to catalog
     * @param sale Sale string "KR1583 77.72 128 P L4891 2 1"
     */
    public void add(String sale) {
        sales.add(new Venda(sale));
    }

    /**
     * calculates size of catalog
     * @return Int Size of catalog
     */
    public int size() {
        return sales.size();
    }

    /**
     * Get Vendas
     * @return sales catalog
     */
    public Collection<Venda> getSales() {
        return sales;
    }
}
package model.Cat;

import model.data.Venda;

import java.util.Collection;

public interface ICatVendas {
    void add(String venda);
    int size();
    void removeInvalidSales(ICat catalogoClientes, ICat catalogoProdutos);
    Collection<Venda> getSales();
}

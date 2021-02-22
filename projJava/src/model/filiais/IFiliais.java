package model.filiais;

import model.Cat.ICat;
import model.data.Par;
import model.data.Venda;

import java.util.Collection;
import java.util.Map;

public interface IFiliais {

    void addSales(Collection<Venda> vendas);

    int nFiliais();
    int clientsWithPurchases();
    int clientsWithPurchases(int mes, int filial);
    int compras(String cliente, int mes);
    int difProductsPerClient(String cliente, int mes);
    double clientSpent(String cliente, int mes);

    ICat produtosComprados(String cliente);
    Map<String, Double> topClients(int filial);
    Map<String, Integer> mapClientsDifProducts(int x);
    Map<String, Par<Integer,Double>> topClientsForeachProduct(String produto, int x);
}

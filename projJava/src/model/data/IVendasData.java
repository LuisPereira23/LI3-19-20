package model.data;

import java.util.List;

public interface IVendasData {
    void addVenda(Venda v);
    double faturacaoTotal(int mes, int filial);
    double faturacaoTotal(int mes);
    double faturacaoTotalF(int filial);
    double faturacaoTotal();
    int salesTotal(int mes, int filial);
    int salesTotal(int mes);
    int purchaseTotal(int mes, int filial);
    int purchaseTotal(int mes);
    int purchaseTotal();
    Par<Integer, Double> purchaseTotal(String produto);
    int boughtProducts();
    List<String> boughtProducts(int mes);
    int ClientsWithPurchases(int mes);
    int ClientsWithPurchases();
    List<String> ClientsWithPurchases(int mes, int filial);
    int purchaseEqualZero();
    int salesSize();
}

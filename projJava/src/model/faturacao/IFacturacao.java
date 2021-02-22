package model.faturacao;

import model.data.Par;
import model.data.Venda;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IFacturacao {

    void adicionaVendas(Collection<Venda> vendas);

    int PurchasesValueZero();
    int numeroProdutos();
    double faturacao();
    double faturacao(int mes, int filial);
    double faturacao(String produto, int mes);
    int purchases();
    int purchases(int mes);
    int purchases(int mes, int filial);
    int purchases(String produto, int mes);
    int numberClientsForProduct(String produto, int mes);

    Map<String, Par<Integer,Integer>> mostBoughtProducts(int x);
    List<double[]> faturacaoPorProduto(String produto);

    List<String> getProducts();
}

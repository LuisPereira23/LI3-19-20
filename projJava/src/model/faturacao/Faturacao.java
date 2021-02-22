package model.faturacao;

import model.Cat.ICat;
import model.Cat.CatProdutos;
import model.data.IVendasData;
import model.data.Par;
import model.data.Venda;
import model.data.VendasFunctions;

import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Faturacao implements IFacturacao, Serializable {

    private static final long serialVersionUID = 9163510312412255474L;
    private ICat products;

    /**
     * Construtor Faturacao
     */
    public Faturacao(){
        products = new CatProdutos();
    }

    /**
     * Adicionar vendas a faturacao
     * @param sales Catalogo de Vendas
     */
    public void adicionaVendas(Collection<Venda> sales) {
        for(Venda v : sales){
            IVendasData productSales;
            if(products.isInCat(v.getProduct()))
                productSales = ((IVendasData) products.getValue(v.getProduct()));
            else{
                productSales = new VendasFunctions();
                products.add(v.getProduct(),productSales);
            }
            productSales.addVenda(v);
        }
    }

    /**
     * Compras com valor 0
     * @return Int Numero de compras com faturacao 0
     */
    public int PurchasesValueZero() {
        int t = 0;
        for(Object v : products.getAllValues())
            t += ((IVendasData) v).purchaseEqualZero();
        return t;
    }

    /**
     * @return Int Numero total de produtos
     */
    public int numeroProdutos() {
        return products.catSize();
    }

    /**
     * Faturacao total
     * @return Faturacao total
     */
    public double faturacao() {
        double t = 0;
        for(Object v : products.getAllValues())
            t += ((IVendasData) v).faturacaoTotal();
        return t;
    }

    /**
     * Faturacao por mes e filial
     * @param month Int do mes
     * @param filial Int numero de filial
     * @return double com faturacao para um certo mes e filial
     */
    public double faturacao(int month, int filial) {
        double t = 0;
        for(Object v : products.getAllValues())
            t += ((IVendasData) v).faturacaoTotal(month, filial);
        return t;
    }

    /**
     * Faturacao por mes e produto
     * @param month Int do mes
     * @param product String do produto "AA1001"
     * @return double com faturacao de um produto para tal mes
     */
    public double faturacao(String product, int month) {
        if(!products.isInCat(product))
            return 0;
        IVendasData sales = (IVendasData) products.getValue(product);
        return sales.faturacaoTotal(month);
    }

    /**
     * Compras total
     * @return Int total de compras
     */
    public int purchases() {
        int t = 0;
        for(Object v : products.getAllValues())
            t += ((IVendasData) v).purchaseTotal();
        return t;
    }

    /**
     * Compras por mes
     * @param month Int do mes
     * @return Int com total de compras para certo mes
     */
    public int purchases(int month) {
        int total = 0;
        for(Object v : products.getAllValues())
            total += ((IVendasData) v).purchaseTotal(month);
        return total;
    }

    /**
     * Compras por mes e filial
     * @param month Int do mes
     * @param filial Numero da filial
     * @return Int com total de compras para certo mes em certa filial
     */
    public int purchases(int month, int filial) {
        int t = 0;
        for(Object v : products.getAllValues())
            t += ((IVendasData) v).salesTotal(month,filial);
        return t;
    }

    /**
     * Compras por produto e mes
     * @param month Int do mes
     * @param product String do produto "AA1001"
     * @return Int com total de compras para certo produto em certo mes
     */
    public int purchases(String product, int month) {
        if(!products.isInCat(product))
            return 0;
        IVendasData sales = (IVendasData) products.getValue(product);
        return sales.purchaseTotal(month);
    }

    /**
     * Numero de clientes que compraram certo produto
     * @param month Int do mes
     * @param product String Produto "AA1001"
     * @return Int Numero de clientes que compraram certo produto em certo mes
     */
    public int numberClientsForProduct(String product, int month) {
        if(!products.isInCat(product))
            return 0;
        IVendasData sales = (IVendasData) products.getValue(product);
        return sales.ClientsWithPurchases(month);
    }

    /**
     * Produtos mais comprados
     * @param nProducts Numero de produtos pedido
     * @return Map de codigo produto "AA1001" e par Total compras,Numero de clientes distintos
     */
    public Map<String, Par<Integer, Integer>> mostBoughtProducts(int nProducts) {
        ICat cat = new CatProdutos();
        for(Map.Entry<String, Object> entry : products.getAll().entrySet()){
            IVendasData vendas = ((IVendasData) entry.getValue());
            int purchases = vendas.purchaseTotal();
            int numberOfClients = vendas.ClientsWithPurchases();
            Par<Integer, Integer> par = new Par<>(purchases, numberOfClients);
            cat.add(entry.getKey(), par);
        }

        Map<String, Par<Integer, Integer>> productMap = new HashMap<>();

        for(Map.Entry<String, Object> entry : cat.getAll().entrySet()){
            productMap.put(entry.getKey() ,(Par<Integer, Integer>) entry.getValue());
        }

        productMap = productMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(c2 -> c2.getValue().getX())))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        productMap = productMap.entrySet().stream()
                .limit(nProducts)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        productMap = productMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(c2 -> c2.getValue().getX())))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return productMap;
    }

    /**
     * Faturacao de cada produto
     * @param product String Codigo do produto "AA1001"
     * @return Lista de double com a faturacao de cada produto
     */
    public List<double[]> faturacaoPorProduto(String product) {
        List<double[]> a = new ArrayList<>(12);
        if(!products.isInCat(product))
            return a;

        IVendasData sales = (IVendasData) products.getValue(product);
        for(int i = 0 ; i < 12 ; i++){
            double[] filiais = new double[sales.salesSize()];
            for(int j = 0 ; j < filiais.length ; j++){
                filiais[j] = sales.faturacaoTotal(i+1, j+1);
            }
            a.add(filiais);
        }
        return a;
    }

    /**
     * Get de produtos
     * @return Lista de todas as strings de codigos de produtos
     */
    public List<String> getProducts() {
        List<String> l = new ArrayList<>(products.getAllKeys()) ;
        l.sort(String::compareTo);
        return l;
    }
}

package model.filiais;

import model.Cat.CatClientes;
import model.Cat.ICat;
import model.Cat.CatProdutos;
import model.data.IVendasData;
import model.data.Par;
import model.data.Venda;
import model.data.VendasFunctions;

import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Filiais implements IFiliais, Serializable {

    private static final long serialVersionUID = -2739410884449452298L;
    private ICat clients;
    private int nFiliais;

    /**
     * Construtor Filiais
     * @param nFiliais Numero de filiais
     */
    public Filiais(int nFiliais){
        clients = new CatClientes();
        this.nFiliais = nFiliais;
    }

    /**
     * Adiciona vendas ao cliente
     * @param sales Catalogo Vendas
     */
    public void addSales(Collection<Venda> sales) {
        for(Venda v : sales){
            IVendasData clientSales;
            if(clients.isInCat(v.getClient()))
                clientSales = ((IVendasData) clients.getValue(v.getClient()));
            else{
                clientSales = new VendasFunctions();
                clients.add(v.getClient(),clientSales);
            }
            clientSales.addVenda(v);
        }
    }

    /**
     * Numero de filiais
     * @return numero das filiais
     */
    public int nFiliais() {
        return nFiliais;
    }

    /**
     *Clientes com compras
     * @return Tamanho do catalogo de clientes com compas
     */
    public int clientsWithPurchases() {
        return clients.catSize();
    }

    /**
     * Clientes com compras por mes e filial
     * @param month Int
     * @param filial Int numero da filial
     * @return Int Numero de clientes com compras em certo mes e filial
     */
    public int clientsWithPurchases(int month, int filial) {
        ICat clients = new CatClientes();
        for(Object v : this.clients.getAllValues())
            for(String cliente : ((IVendasData) v).ClientsWithPurchases(month, filial))
                clients.add(cliente);
        return clients.catSize();
    }

    /**
     * Numero de compras total por cliente e mes
     * @param client String codigo cliente "Z5000"
     * @param month Int mes
     * @return Int numero de compras de um cliente em certo mes
     */
    public int compras(String client, int month) {
        if(!clients.isInCat(client))
            return 0;
        IVendasData sales = ((IVendasData) clients.getValue(client));
        return sales.salesTotal(month);
    }

    /**
     * Numero de produtos diferentes comprados por um cliente em certo mes
     * @param client String codigo cliente "Z5000"
     * @param month Int mes
     * @return Int numero de produtos diferentes comprados por um cliente em certo mes
     */
    public int difProductsPerClient(String client, int month) {
        ICat products = new CatProdutos();
        Object v = clients.getValue(client);
        if(v == null)
            return 0;
        for(String produto : ((IVendasData) v).boughtProducts(month))
            products.add(produto);
        return products.catSize();
    }

    /**
     * Gastos de um cliente em certo mes
     * @param client String codigo cliente "Z5000"
     * @param month Int mes
     * @return double total que um cliente gastour em certo mes
     */
    public double clientSpent(String client, int month) {
        if(!clients.isInCat(client))
            return 0;
        IVendasData sales = ((IVendasData) clients.getValue(client));
        return sales.faturacaoTotal(month);
    }

    /**
     * Produtos comprados por certo cliente
     * @param client String codigo cliente "Z5000"
     * @return Catalogo de produtos comprados por certo cliente
     */
    public ICat produtosComprados(String client) {
        ICat catProd = new CatProdutos();
        if(!this.clients.isInCat(client))
            return catProd;
        IVendasData vendas = ((IVendasData) this.clients.getValue(client));

        for(int i = 0 ; i < 12 ; i++)
            for(String s : vendas.boughtProducts(i+1))
                catProd.add(s,vendas.purchaseTotal(s).getX());
        return catProd;
    }

    /**
     * 3 Maiores compradores por filial
     * @param filial Numero da filial
     * @return Map codigo cliente,total gasto dos 3 maiores compradores para certa filial
     */
    public Map<String, Double> topClients(int filial) {
        ICat catCli = clients.clone();

        for(String cliente : catCli.getAllKeys()){
            IVendasData vendas = ((IVendasData)catCli.getValue(cliente));
            catCli.add(cliente, vendas.faturacaoTotalF(filial));
        }

        Map<String, Double> mapCli = new HashMap<>();

        for(Map.Entry<String, Object> entry : catCli.getAll().entrySet()){
            mapCli.put(entry.getKey() ,(Double) entry.getValue());
        }

        mapCli = mapCli.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        mapCli = mapCli.entrySet().stream()
                .limit(3)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        mapCli = mapCli.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return mapCli;
    }

    /**Produtos diferentes comprados por cada cliente
     * @param nClients Numero de clientes pedido
     * @return Map codigo cliente,numero de produtos diferentes
     */
    public Map<String, Integer> mapClientsDifProducts(int nClients) {
        ICat catCli = new CatClientes();

        for(String cliente : clients.getAllKeys()){
            IVendasData vendas = ((IVendasData) clients.getValue(cliente));
            catCli.add(cliente, vendas.boughtProducts());
        }

        Map<String, Integer> mapCli = new HashMap<>();

        for(Map.Entry<String, Object> entry : catCli.getAll().entrySet()){
            mapCli.put(entry.getKey() , (Integer)entry.getValue() );
        }

        mapCli = mapCli.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        mapCli = mapCli.entrySet().stream()
                .limit(nClients)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        mapCli = mapCli.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return mapCli;
    }

    /**
     * Clientes que mais compraram cada produto
     * @param nClients Numero de clientes pedido
     * @param product Codigo do produto "AA1001"
     * @return Map Codigo Cliente, Par Numero compras do produto,Total faturado do produto
     */
    public Map<String, Par<Integer,Double>> topClientsForeachProduct(String product, int nClients) {
        ICat catCli = new CatClientes();

        for(String cliente : clients.getAllKeys()){
            IVendasData sales = ((IVendasData) clients.getValue(cliente));
            Par<Integer,Double> par = sales.purchaseTotal(product);
            if(par.getX() > 0)
                catCli.add(cliente, par);
        }

        Map<String, Par<Integer,Double>> mapCli = new HashMap<>();

        for(Map.Entry<String, Object> entry : catCli.getAll().entrySet()){
            mapCli.put(entry.getKey() , (Par<Integer,Double>)entry.getValue() );
        }

        mapCli = mapCli.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(c2 -> c2.getValue().getX())))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        mapCli = mapCli.entrySet().stream()
                .limit(nClients)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        mapCli = mapCli.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(c2 -> c2.getValue().getX())))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        return mapCli;
    }
}

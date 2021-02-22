package model.main;

import model.Cat.*;
import model.data.Par;
import model.faturacao.Faturacao;
import model.faturacao.IFacturacao;
import model.filiais.Filiais;
import model.filiais.IFiliais;
import model.reader.IReader;
import model.reader.Reader;
import model.utils.Crono;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Model implements IModel, Serializable {

    private static final long serialVersionUID = 4749162034592494950L;
    private ICat clients;
    private ICat products;
    private IFacturacao faturacao;
    private IFiliais filiais;
    private int invalidSales;

    /**
     * Carrega os ficheiros
     * @return path do txt com os ficheiros a usar
     */
    public String[] loadData() {
        int numFiliais;
        String[] pathFile;
        IReader reader = new Reader();
        try {
            pathFile = reader.readPath();
        } catch (IOException ignored) {
            return null;
        }
        try {
            numFiliais = Integer.parseInt(pathFile[0]);
        } catch (NumberFormatException ignored) {
            return null;
        }

        products = new CatProdutos();
        clients = new CatClientes();
        ICatVendas sales = new catSales();
        faturacao = new Faturacao();
        filiais = new Filiais(numFiliais);
        try {
            Crono.start();
            reader.readCat(products, "data/"+pathFile[2]);
        } catch (IOException ignored) {}

        try {
            reader.readCat(clients, "data/"+pathFile[1]);
        } catch (IOException ignored) {}

        try {

            reader.readVendas(sales, "data/"+pathFile[3]);
        } catch (IOException ignored) {}

        int totalSales = sales.size();
        sales.removeInvalidSales(clients, products);
        invalidSales = totalSales - sales.size();
        faturacao.adicionaVendas(sales.getSales());
        filiais.addSales(sales.getSales());

        Crono.stop();
        System.out.println(Crono.print()+" sec");

        return pathFile;
    }

    /**
     * popula estatisticas
     * @return estatisticas dos ficheiros carregados
     */
    public int[] estatisticas(){
        int[] estaticas = new int[10];
        estaticas[0] = invalidSales;
        estaticas[1] = products.catSize();
        estaticas[2] = faturacao.numeroProdutos();
        estaticas[3] = estaticas[1] - estaticas[2];
        estaticas[4] = clients.catSize();
        estaticas[5] = filiais.clientsWithPurchases();
        estaticas[6] = estaticas[4] - estaticas[5];
        estaticas[7] = faturacao.PurchasesValueZero();
        estaticas[8] = (int) faturacao.faturacao();
        estaticas[9] = faturacao.purchases();
        return estaticas;
    }

    /**
     * Faturacao global
     * @return total faturado
     */
    public double faturacaoTotal(){
        return faturacao.faturacao();
    }

    /**
     * Compras por mes
     * @return lista de numero de compras por mes
     */
    public int[] purchasesPerMonth() {
        int[] purchases = new int[12];
        for(int i = 0 ; i < purchases.length ; i++)
            purchases[i] = faturacao.purchases(i+1);
        return purchases;
    }

    /**
     *Faturacao por mes e filial
     * @return lista de faturacoes por cada mes e por cada filial
     */
    public double[][] faturacaoPorMesPorFilial() {
        double[][] fat = new double[12][filiais.nFiliais()];
        for(int i = 0 ; i < 12 ; i++)
            for(int j = 0; j < filiais.nFiliais() ; j++)
                fat[i][j] = faturacao.faturacao(i+1, j+1);
        return fat;
    }

    /**
     * Clientes com compras em certo mes e filial
     * @return Numero de clientes que compraram em certo mes em certa filial
     */
    public int[][] nClientsPurchaseMonthFilial() {
        int[][] clients = new int[12][filiais.nFiliais()];
        for(int i = 0 ; i < 12 ; i++)
            for(int j = 0; j < filiais.nFiliais() ; j++)
                clients[i][j] = filiais.clientsWithPurchases(i+1, j+1);
        return clients;
    }


    /**
     * Produtos nunca comprados
     * @return Lista ordenada alfabeticamente com os códigos dos produtos nunca comprados e o seu respetivo total
     */
    public List<String> query1() {
        Crono.start();
        ICat catProd = products.clone();
        catProd.remove(faturacao.getProducts());
        List<String> listProd = new ArrayList<>(catProd.getAllKeys());
        Collections.sort(listProd);
        Crono.stop();
        return listProd;
    }

    /**
     * Numero total de vendas e numero total de clientes distintos para cada filial
     * @param month Int Mes
     * @return Numero total de vendas e numero total de clientes distintos para cada filial
     */
    public List<Par<Integer, Integer>> query2(int month) {
        Crono.start();
        List<Par<Integer, Integer>> array = new ArrayList<>(filiais.nFiliais());
        for (int i = 0; i < filiais.nFiliais() ; i++){
            Integer totalSales = faturacao.purchases(month, i+1);
            Integer totalClients = filiais.clientsWithPurchases(month, i+1);
            array.add(new Par<>(totalSales,totalClients));
        }
        Crono.stop();
        return array;
    }

    /**
     * Para cada cliente em cada mes, quantas compras distintas fez e total gasto
     * @param client String Codigo Cliente "Z5000"
     * @return Lista formada por pares em que o primeiro elememento do par e o numero de compras e o segundo elemento outro pare cujo o primeiro elemento sao produtos distintos que comprou e o segundo quanto gastou no total
     */
    public List<Par<Integer, Par<Integer,Double>>> query3(String client) {
        Crono.start();
        List<Par<Integer, Par<Integer,Double>>> array = new ArrayList<>(12);
        for (int i = 0 ; i < 12 ; i++){
            Double spent = filiais.clientSpent(client, i+1);
            Integer nPurchases = filiais.compras(client, i+1);
            Integer nProducts = filiais.difProductsPerClient(client, i+1);
            Par<Integer, Double> productsSpentPair = new Par<>(nProducts,spent);
            Par<Integer, Par<Integer, Double>> par = new Par<>(nPurchases,productsSpentPair);
            array.add(par);
        }
        Crono.stop();
        return array;
    }

    /**
     * Quantas vezes foi comprado , por clientes diferentes e total gasto
     * @param product String codigo de produto "AA1001"
     * @return lista de pares em que o primeiro elemento do par e numero de compras do produto e o segundo o par em que o primeiro elemento e o numero de vezes que foi comprado, o segundo o total faturado
     */
    public List<Par<Integer, Par<Integer,Double>>> query4(String product) {
        Crono.start();
        List<Par<Integer, Par<Integer,Double>>> array = new ArrayList<>(12);
        for (int i = 0 ; i < 12 ; i++){
            Double totalFat = faturacao.faturacao(product, i+1);
            Integer nPurchases = faturacao.purchases(product, i+1);
            Integer nClients = faturacao.numberClientsForProduct(product, i+1);
            Par<Integer, Double> produtosCusto = new Par<>(nClients,totalFat);
            Par<Integer, Par<Integer, Double>> par = new Par<>(nPurchases,produtosCusto);
            array.add(par);
        }
        Crono.stop();
        return array;
    }

    /**
     * Produtos mais comprados por um cliente
     * @param client String codigo cliente "Z5000"
     * @return Map ordenado por ordem decrescente de quantidade formado por uma String do codigo do produto e Integer com numero de compras de tal produto
     */
    public Map<String, Integer> query5(String client) {
        Crono.start();
        Map<String, Integer> mapQuery = new HashMap<>();
        for(Map.Entry<String, Object> entry : filiais.produtosComprados(client).getAll().entrySet()){
            String codProd = entry.getKey();
            Integer value = (Integer) entry.getValue();
            mapQuery.put(codProd,value);
        }
        mapQuery = mapQuery.entrySet()
                .stream()
                .sorted((o1, o2) -> o1.getValue().equals(o2.getValue())
                        ? o2.getKey().compareTo(o1.getKey())
                        : o1.getValue().compareTo(o2.getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        Crono.stop();
        return mapQuery;
    }

    /**
     * Produtos mais vendidos com indicacao de clientes distintos
     * @param nProducts Int Numero de produtos pedido
     * @return Map de codigo produto "AA1001" e par Total compras,Numero de clientes distintos
     */
    public Map<String, Par<Integer,Integer>> query6(int nProducts) {
        Crono.start();
        Map<String, Par<Integer,Integer>> mapQuery = faturacao.mostBoughtProducts(nProducts);
        Crono.stop();
        return mapQuery;
    }

    /**
     * Maiores compradores para cada filial
     * @return lista de map Codido do cliente,faturado dos clientes que mais faturaram, por cada filial
     */
    public List<Map<String, Double>> query7() {
        Crono.start();
        List<Map<String, Double>> arrayQuery = new ArrayList<>(filiais.nFiliais());
        for (int i = 0; i < filiais.nFiliais() ; i++)
            arrayQuery.add(filiais.topClients(i+1));
        Crono.stop();
        return arrayQuery;
    }

    /**
     * Clientes que compraram mais produtos diferentes
     * @param nClients Int Numero de clientes pedido
     * @return Map de codigo cliente "Z5000" e numero de produtos diferentes comprados ordenado descrescente
     */
    public Map<String, Integer> query8(int nClients) {
        Crono.start();
        Map<String, Integer> mapQuery = filiais.mapClientsDifProducts(nClients);
        Crono.stop();
        return mapQuery;
    }

    /**
     * Que clientes mais compraram certo produto e quanto gastaram
     * @param nClients Int Numero de clientes pedido
     * @param product String Codigo produto "AA1001"
     * @return Map com String Codigo de cliente e um par Numero compras do produto, Total faturado. ordenado decrescentemente de numero de compras
     */
    public Map<String, Par<Integer,Double>> query9(String product, int nClients) {
        Crono.start();
        Map<String, Par<Integer,Double>> mapQuery = filiais.topClientsForeachProduct(product,nClients);
        Crono.stop();
        return mapQuery;
    }

    /**
     * Fatura total de cada produto por mes e filial
     * @param product String Codigo produto "AA1001"
     * @return Lista de doubles com a faturacao de cada produto mes a mes por cada filial
     */
    public List<double[]> query10(String product) {
        Crono.start();
        List<double[]> listQuery = faturacao.faturacaoPorProduto(product);
        Crono.stop();
        return listQuery;
    }
}


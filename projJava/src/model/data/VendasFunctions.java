package model.data;

import model.Cat.CatClientes;
import model.Cat.ICat;
import model.Cat.CatProdutos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VendasFunctions implements IVendasData, Serializable {

    private static final long serialVersionUID = -1268006755188756401L;
    private List<List<List<VendasData>>> sales; // filial, month and list

    /**
     * Contructor for VendasFunctions
     */
    public VendasFunctions(){
        sales = new ArrayList<>();
    }

    /**
     * Adds Sale to Vendas
     * @param sale Sale
     */
    public void addVenda(Venda sale){
        int filial = sale.getFilial();
        int month = sale.getMonth();

        for(int i = sales.size(); i < filial ; i++){
            List<List<VendasData>> l = new ArrayList<>(12);
            for (int j = 0 ; j < 12 ; j++)
                l.add(new ArrayList<>());
            sales.add(l);
        }
        sales.get(filial-1).get(month-1).add(new VendasData(sale));
    }

    /**
     * Faturacao total de todas vendas em certo mes e filial
     * @return double total de todas vendas em certo mes e filial
     * @param filial filial da venda
     * @param month mes da venda
     */
    public double faturacaoTotal(int month, int filial){
        if(filial > sales.size() || month > sales.get(filial-1).size())
            return 0;

        double t = 0;
        List<VendasData> l = sales.get(filial-1).get(month-1);
        for(VendasData v : l)
            t += v.getTotalPrice();
        return t;
    }

    /**
     * Faturacao total de todas venda em certo mes
     * @return double total faturacao de todas vendas em certo mes
     * @param month mes da venda
     */
    public double faturacaoTotal(int month){
        if(month < 1 || month > 12)
            return 0;

        double t = 0;
        for(List<List<VendasData>> l : sales)
            for(VendasData v : l.get(month-1))
                t+= v.getTotalPrice();
        return t;
    }

    /**
     * Faturacao total de todas vendas em certo mes
     * @return double total de faturacao de todas vendas para certa filial
     * @param filial filial das vendas
     */
    public double faturacaoTotalF(int filial){
        double t = 0;
            for(List<VendasData> v : sales.get(filial-1))
                for(VendasData vv : v)
                    t+= vv.getTotalPrice();
        return t;
    }

    /**
     * Faturacao total de todas vendas
     * @return double Faturacao total de todas vendas
     *
     */
    public double faturacaoTotal(){
        double t = 0;
        for(List<List<VendasData>> l : sales)
            for(List<VendasData> ll : l)
                for(VendasData v : ll)
                    t+= v.getTotalPrice();
        return t;
    }

    /**
     * Total de vendas em certo mes e filial
     * @return Int Total de vendas em certo mes e filial
     * @param filial numero da filial
     * @param month mes
     */
    public int salesTotal(int month, int filial){
        if(filial > sales.size() || month > sales.get(filial-1).size())
            return 0;
        return sales.get(filial-1).get(month-1).size();
    }

    /**
     * Total de vendas em certo mes
     * @return Int Total de vendas em certo mes
     * @param month mes
     */
    public int salesTotal(int month){
        if(month < 1 || month > 12)
            return 0;
        int t = 0;
        for(List<List<VendasData>> l : sales)
            t += l.get(month-1).size();
        return t;
    }

    /**
     * Total das compras em certo mes e filial
     * @return Int Total de compras em certo mes e filial
     * @param filial numero da filial
     * @param month mes
     */
    public int purchaseTotal(int month, int filial){
        if(filial > sales.size() || month > sales.get(filial-1).size())
            return 0;
        int t = 0;
        for(VendasData v : sales.get(filial-1).get(month-1))
            t+= v.getQuantity();
        return t;
    }

    /**
     * Total das compras em certo mes
     * @return Int Total de compras em certo mes
     * @param month mes
     */
    public int purchaseTotal(int month){
        if(month < 1 || month > 12)
            return 0;
        int t = 0;
        for(List<List<VendasData>> l : sales)
        for(VendasData v : l.get(month-1))
            t+= v.getQuantity();
        return t;
    }

    /**
     * Total das compras para todas vendas
     * @return Int Total das compras para todas vendas
     */
    public int purchaseTotal(){
        int t = 0;
        for(List<List<VendasData>> l : sales)
            for(List<VendasData> ll : l)
                for(VendasData v : ll)
                    t += v.getQuantity();
        return t;
    }

    /**
     * Numero de compras e faturacao de certo produto
     * @param produto String de um produto "AA1001"
     * @return Par Numero compras,faturacao
     */
    public Par<Integer, Double> purchaseTotal(String produto){
        int t = 0;
        double tf = 0;
        for(List<List<VendasData>> l : sales)
            for(List<VendasData> ll : l)
                for(VendasData v : ll)
                    if(v.getProduct().equals(produto)){
                        t += v.getQuantity();
                        tf += v.getTotalPrice();
                    }
        return new Par<>(t, tf);
    }

    /**
     * Numero de compras com faturacao 0
     * @return Int Numero de compras com faturacao 0
     */
    public int purchaseEqualZero(){
        int t = 0;
        for(List<List<VendasData>> l : sales)
            for(List<VendasData> ll : l)
                for(VendasData v : ll)
                    if(v.getTotalPrice()==0)
                        t++;
        return t;
    }

    /**
     * Cliente que compraram em certo mes
     * @return Array de clientes que compraram em certo mes
     */
    public List<String> boughtProducts(int month){
        if(month < 1 || month > 12)
            return null;
        List<String> client = new ArrayList<>();
        for(List<List<VendasData>> l : sales)
            for(VendasData v : l.get(month-1))
                client.add(v.getProduct());
        return client;
    }

    /**
     * Todos os produtos com compras
     * @return Catalogo de produtos comprados
     */
    public int boughtProducts(){
        ICat c = new CatProdutos();
        for(List<List<VendasData>> l : sales)
            for(List<VendasData> ll : l)
                for(VendasData v : ll)
                    c.add(v.getProduct());
        return c.catSize();
    }

    /**
     *Clientes Com compras por mes e filial
     * @return ArrayList de clientes que compraram em certo mes e filial
     * @param filial Numero da filial
     * @param month numero do mes
     */
    public List<String> ClientsWithPurchases(int month, int filial){
        if(month < 1 || month > 12)
            return null;
        List<String> c = new ArrayList<>();
        for(VendasData v : sales.get(filial-1).get(month-1))
            c.add(v.getClient());
        return c;
    }


    /**
     * Clintes com compras por mes
     * @return ArrayList de clientes que compraram em certo mes
     * @param month numero do mes
     */
    public int ClientsWithPurchases(int month){
        if(month < 1 || month > 12)
            return 0;
        ICat c = new CatClientes();
        for(List<List<VendasData>> l : sales)
            for(VendasData v : l.get(month-1))
                c.add(v.getClient());
        return c.catSize();
    }

    /**
     *Clientes com compras global
     * @return ArrayList de clientes com compras
     */
    public int ClientsWithPurchases(){
        ICat c = new CatClientes();
        for(List<List<VendasData>> l : sales)
            for(List<VendasData> ll : l)
                for(VendasData v : ll)
                    c.add(v.getClient());
        return c.catSize();
    }

    /**
     *Tamanho do catalogo vendas
     * @return int Tamanho do ArrayList
     */
    public int salesSize(){
        return sales.size();
    }
    
}
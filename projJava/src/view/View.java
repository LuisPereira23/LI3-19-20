package view;
import model.data.Par;
import model.faturacao.Faturacao;

import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;

public class View implements IView,Serializable {

    private static final long serialVersionUID = 6457696671959175381L;

    /**
     * Faz Print de linhas para limpar terminal
     */
    public void printNewLines() {
        for (int i = 0 ; i < 20 ; i++)
            out.println();
    }

    /**
     * Faz print de espacos para organizacao
     */
    public void printSpaces() {
        for (int i = 0 ; i < 3 ; i++)
            out.println();
    }

    /**
     * Faz print de uma mensagem caso input seja invalido
     */
    public void printInputError() {
            out.println("Invalid input");
    }

    /**
     * Faz print das estatisticas
     * @param pathFile caminho dos ficheiros
     * @param faturacao Double com a faturacao total
     * @param stats lista com as varias estatisticas
     */
    public void printEstatisticas(String[] pathFile, int[] stats, double faturacao) {
        out.println("############## Estatisticas ############### ");
        out.println("# Numero de filiais : "+pathFile[0]+"                   #");
        out.println("# Ficheiro clientes : "+pathFile[1]+"        #");
        out.println("# Ficheiro produtos : "+pathFile[2]+"        #");
        out.println("# Ficheiro vendas : "+pathFile[3]+"         #");
        out.println("# Vendas Validas : "+stats[9]+"               #");
        out.println("# Vendas Invalidas : "+stats[0]+"               #");
        out.println("# Numero de produtos : "+stats[1]+"             #");
        out.println("# Produtos comprados : "+stats[2]+"             #");
        out.println("# Produtos nao comprados : "+stats[3]+"            #");
        out.println("# Numero de clientes : "+stats[4]+"              #");
        out.println("# Clientes com compras : "+stats[5]+"            #");
        out.println("# Clientes sem compras : "+stats[6]+"                #");
        out.println("# Compras com valor total 0.0 : "+stats[7]+"        #");
        out.println("# Faturacao total : "+faturacao+"  #");
        out.println("############################################");
        out.println();
    }

    /**
     * Faz print de compras por mes
     */
    public void printMonthlyPurchases(int[] purchases) {
        out.println("### Compras por mes ###");
        out.println("# Mes | Total Compras");
        for(int i = 0 ; i < purchases.length ; i++)
            out.println("# Mes "+(i+1)+" | "+purchases[i]);
        out.println();
    }

    /**
     * Faz print da faturacao por mes por filial
     */
    public void printFaturacaoPerMonthPerFilial(double[][] fat) {
        out.println("###### Faturacao #######");
        out.println("Mes | Filial | Faturacao");
        double t= 0;
        for(int i = 0 ; i < fat.length ; i++){
            for(int j = 0 ; j < fat[i].length ; j++) {
                out.println((i + 1) + " | " + (j + 1) + " | " + fat[i][j]);
                t += fat[i][j];
            }
        }
        out.println("Global Faturado: "+t);
        out.println();
    }

    /**
     * Faz print do numero de clientes distintos por mes por cada filial
     */
    public void printDifClientsPerMonthPerFilial(int[][] clients) {
        out.println("#### Clientes Distintos ####");
        out.println("Mes | Filial | Numero Clientes");
        for(int i = 0 ; i < clients.length ; i++){
            for(int j = 0 ; j < clients[i].length ; j++)
                out.println((i+1)+" | "+(j+1)+" | "+clients[i][j]);
        }
        out.println();
    }

    /**
     * Faz print da lista com resposta Query 1
     * @param productList Lista de produtos
     */
    public void printQuery1(List<String> productList) {
        listController(productList, "QUERY 1");
    }

    /**
     * Faz print da resposta Query 2
     * @param list Lista Numero total de vendas e numero total de clientes distintos para cada filial
     */
    public void printQuery2(List<Par<Integer, Integer>> list) {
        out.println("QUERY 2 \n");
        for(int i = 0 ; i < list.size() ; i++){
            out.println("Filial "+(i+1)+" : Compras : "+list.get(i).getX()+" | Clientes : "+list.get(i).getY());
        }
        out.println();
    }

    /**
     * Faz print da resposta Query 3
     * @param list Lista formada por pares em que o primeiro elememento do par e o numero de compras e o segundo elemento outro pare cujo o primeiro elemento sao produtos distintos que comprou e o segundo quanto gastou no total
     *
     */
    public void printQuery3(List<Par<Integer, Par<Integer, Double>>> list) {
        for(int i = 0 ; i < list.size() ; i++)
            if(list.get(i).getX() != 0)
                out.println("Mes "+(i+1)+" : Compras : "+list.get(i).getX()+" | Produtos : "+list.get(i).getY().getX()+" | Custo : "+list.get(i).getY().getY());
    }

    /**
     * Faz print da resposta Query 4
     * @param list lista de pares em que o primeiro elemento do par e numero de compras do produto e o segundo o par em que o primeiro elemento e o numero de vezes que foi comprado, o segundo o total faturado
     *
     */
    public void printQuery4(List<Par<Integer, Par<Integer, Double>>> list) {
        out.println("QUERY 4 : \n");
        int totalSale = 0;
        double totalFatura = 0;
        for(int i = 0 ; i < 12 ; i++){
            Par<Integer, Par<Integer, Double>> par = list.get(i);
            totalFatura += par.getY().getY();
            if(par.getX() != 0)
                out.printf("Mes %d | Vendas %d | Clientes : %d | Total Faturado %f\n", (i+1), par.getX(), par.getY().getX(), par.getY().getY());
        }
        out.printf("\nTotal | Vendas %d | Total Faturado %f\n", totalSale, totalFatura);
    }

    /**
     * faz print da resposta Query 5
     * @param map Map ordenado por ordem decrescente de quantidade formado por uma String do codigo do produto e Integer com numero de compras de tal produto
     */
    public void printQuery5(Map<String, Integer> map) {
        List<String> newList = new LinkedList<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            String s = entry.getKey();
            int i = entry.getValue();
            newList.add(0, s+" Quantidade: "+i);
        }
        listController(newList, "QUERY 5");
    }

    /**
     * faz print da resposta Query 6
     * @param map Map de codigo produto "AA1001" e par Total compras,Numero de clientes distintos
     */
    public void printQuery6(Map<String, Par<Integer, Integer>> map) {
        out.println("QUERY 6 : \n");
        for(Map.Entry<String, Par<Integer, Integer>> entry : map.entrySet()){
            Par<Integer, Integer> par = entry.getValue();
            String product = entry.getKey();
            out.println(product+" : "+ par.getX()+" vendas | "+ par.getY()+" clientes.");
        }
    }

    /**
     * faz print da resposta Query 7
     * @param list lista de map Codido do cliente,faturado  dos clientes que mais faturaram, por cada filial
     */
    public void printQuery7(List<Map<String, Double>> list) {
        out.println("QUERY 7 : \n");
        for(int i = 0 ; i < list.size() ; i++){
            out.println("Filial "+(i+1));
            for(Map.Entry<String, Double> entry : list.get(i).entrySet()){
                Double d = entry.getValue();
                String cliente = entry.getKey();
                out.println(cliente+" : "+d+" €");
            }
            out.println();
        }
    }

    /**
     * faz print da resposta Query 8
     * @param map Map de codigo cliente "Z5000" e numero de produtos diferentes comprados ordenado descrescente
     */
    public void printQuery8(Map<String, Integer> map) {
        out.println("QUERY 8 : \n");
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            Integer d = entry.getValue();
            String cliente = entry.getKey();
            out.println(cliente+" : "+d+" produtos diferentes comprados.");
        }
        out.println();
    }

    /**
     * faz print da resposta Query 9
     * @param map Map com String Codigo de cliente e um par Numero compras do produto, Total faturado. ordenado decrescentemente de numero de compras
     */
    public void printQuery9(Map<String, Par<Integer,Double>> map) {
        out.println("QUERY 9 : \n");
        for(Map.Entry<String, Par<Integer,Double>> entry : map.entrySet()){
            Par<Integer,Double> d = entry.getValue();
            String client = entry.getKey();
            out.println(client+" : "+d.getX()+" produtos vendidos | "+d.getY()+" € gastos.");
        }
        out.println();
    }

    /**
     * faz print da resposta Query 10
     * @param list Lista de doubles com a faturacao de cada produto mes a mes por cada filial
     */
    public void printQuery10(List<double[]> list) {
        out.println("QUERY 10 :\n");
        if(list.size() > 0)
        for(int i = 0 ; i < 12 ; i++){
            for(int j = 0 ; j < list.get(i).length; j++){
                double fatura = list.get(i)[j];
                if(fatura != 0)
                    out.printf("Mes %d | Filial %d | Fatura %f\n", (i+1) , (j+1), fatura);
            }
        }
        out.println();
    }

    /**
     *  Continua menu
     */
    public void proceed() {
        proceed(null);
    }

    /**
     *  Continua menu
     */
    public void proceed(String toContinue) {
        out.println("Continue by pressing Enter..");
        Scanner input = new Scanner(System.in);
        boolean state = false;
        while(!state) {
            try {
                input.nextLine();
                state = true;
            }
            catch(InputMismatchException e)
            {
                proceed();
            }
        }
    }

    /**
     *  Get Comando no menu
     */
    public int getCommand(int min, int max) {
        Scanner input = new Scanner(System.in);
        int txt =0;
        try {
            out.print("Command : ");
            txt = input.nextInt();
            if (txt>13 || txt<0){
                out.print("Wrong input...\n");
            }
        } catch (InputMismatchException e) {
            printInputError();
        }

        return txt;
    }

    /**
     *  Get input Int
     */
    public int getInt(int min, int max) {
        Scanner input = new Scanner(System.in);
        int txt =0;
        try {
            out.print("Introduzir Quantidade de resultados : ");
            txt = input.nextInt();
        } catch (InputMismatchException e) {
            printInputError();
        }

        return txt;
    }

    /**
     *  Get input Mes
     */
    public int getMes() {
        Scanner input = new Scanner(System.in);
        int txt=0;
        try {
        out.print("Introduzir um mes (1-12) : ");
         txt = input.nextInt();
        }
        catch (InputMismatchException e){

        }
        return txt;
    }

    /**
     *  Get input Codigo cliente
     */
    public String getCodCli() {
        Scanner input = new Scanner(System.in);
        String txt="";
        try {
            out.print("Introduzir Codigo de Cliente : ");
             txt = input.nextLine();
        } catch (InputMismatchException e) {

        }
        return txt;
    }

     /**
     *  Get input Codigo produto
     */
    public String getCodProd() {
        Scanner input = new Scanner(System.in);
        String txt ="";
        try {
            out.print("Introduzir Codigo de Produto : ");
             txt = input.nextLine();
        } catch (InputMismatchException e) {
        }

        return txt;
    }

    /**
     *  Print do menu apresentado ao user
     */
    public void printMenu() {
        out.println("############################## GestVendas Menu ###############################");
        out.println("# Press 1  | Lista de produtos nunca comprados e total.                      #");
        out.println("# Press 2  | Total compras realizadas e número de clientes distintos.        #");
        out.println("# Press 3  | Numero compras, produtos distintos e despesas de um cliente.    #");
        out.println("# Press 4  | Quantidade,Clientes distintos e total faturado por produto.     #");
        out.println("# Press 5  | Lista de produtos mais comprados por um cliente.                #");
        out.println("# Press 6  | Produtos mais vendidos e clientes distintos.                    #");
        out.println("# Press 7  | Tres maiores compradores para cada filial.                      #");
        out.println("# Press 8  | Clientes com mais compras de produtos distintos e quantidade.   #");
        out.println("# Press 9  | Clientes que mais compraram certo produto e valor gasto.        #");
        out.println("# Press 10 | Total faturado de cada produto por mes e filial.                #");
        out.println("# Press 11 | Estatisticas.                                                   #");
        out.println("# Press 12 | save the state.                                                 #");
        out.println("# Press 13 | load the state.                                                 #");
        out.println("# Press 0  | quit.                                                           #");
        out.println("##############################################################################\n");
    }

    /**
     *  Print Elapsed time
     */
    public void printQueryTime(String time) {
        out.println("Elapsed time : "+time +" sec");
    }

    /**
     *  Print quit aplicacao
     */
    public void printClose() {
        out.println("Closing Application");
    }

    private static final int pageElements = 10;

    /**
     *  Controller e formato de apresentacao de listas
     * @param list Lista a apresentar
     * @param title Query a apresentar
     */
    public void listController(List<String> list, String title){
        int page = 1;
        while(true){
            printNewLines();
            out.println(title.toUpperCase()+" Total : "+list.size()+".\n");
            if(list.size() == 0){
                out.println("Empty List.\n");
                proceed();
            }
            int index;
            out.println("##################");
            for (int i = 0; i < pageElements && (index = (page-1)* pageElements + i)< list.size() ; i++)
                out.println(list.get(index));
            out.println("##################");

            out.println("\nBackward = h | Forward = l  | q = quit");

            String input = printCommand("Command").toLowerCase();

            if(input.equals("l")&& page* pageElements < list.size())
                page++;
            else if(input.equals("h") && page >= 2)
                page--;
            else if(input.equals("q"))
                return;
        }
    }

    /**
     *  Print zone input command
     */
    public String printCommand(String command) {
        out.print(command + " : ");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}

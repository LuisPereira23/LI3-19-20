package view;

import model.data.Par;

import java.util.List;
import java.util.Map;

public interface IView {

    void printNewLines();
    void printSpaces();
    void printInputError();
    void proceed();
    void proceed(String message);

    void printEstatisticas(String[] config, int[] estaticas, double faturacao);
    void printMonthlyPurchases(int[] compras);
    void printFaturacaoPerMonthPerFilial(double[][] faturacao);
    void printDifClientsPerMonthPerFilial(int[][] clientes);

    void printQuery1(List<String> a);
    void printQuery2(List<Par<Integer,Integer>> a);
    void printQuery3(List<Par<Integer, Par<Integer,Double>>> a);
    void printQuery4(List<Par<Integer, Par<Integer,Double>>> a);
    void printQuery5(Map<String, Integer> a);
    void printQuery6(Map<String, Par<Integer,Integer>> a);
    void printQuery7(List<Map<String, Double>> a);
    void printQuery8(Map<String, Integer> a);
    void printQuery9(Map<String, Par<Integer,Double>> a);
    void printQuery10(List<double[]> a);
    void printQueryTime(String time);

    int getCommand(int min, int max);
    int getInt(int min, int max);
    int getMes();
    String getCodCli();
    String getCodProd();
    void printMenu();
    void printClose();
    String printCommand(String say);
}

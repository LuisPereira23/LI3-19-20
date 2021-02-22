package model.main;

import model.data.Par;

import java.util.List;
import java.util.Map;

public interface IModel {
    String[] loadData();

    int[] estatisticas();
    double faturacaoTotal();
    int[] purchasesPerMonth();
    double[][] faturacaoPorMesPorFilial();
    int[][] nClientsPurchaseMonthFilial();

    List<String> query1();
    List<Par<Integer,Integer>> query2(int mes);
    List<Par<Integer, Par<Integer,Double>>> query3(String cliente);
    List<Par<Integer, Par<Integer,Double>>> query4(String produto);
    Map<String, Integer> query5(String cliente);
    Map<String, Par<Integer,Integer>>query6(int x);
    List<Map<String, Double>> query7();
    Map<String, Integer> query8(int x);
    Map<String, Par<Integer, Double>> query9(String produto, int x);
    List<double[]> query10(String produto);
}



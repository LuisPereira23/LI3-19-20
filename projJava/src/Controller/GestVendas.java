package Controller;

import model.data.Cliente;
import model.data.Produto;
import model.main.IModel;
import model.reader.IObjectStream;
import model.reader.ObjectStream;
import model.utils.Crono;
import view.IView;

import java.io.Serializable;

import static java.lang.System.out;

public class GestVendas implements IGestVendas, Serializable {

    private static final long serialVersionUID = -6470129348405081428L;
    private IModel model;
    private IView view;
    private String[] pathFile;

    /**
     * Construtor GestVendas
     * @param pathFile Caminho dos ficheiros
     */
    public GestVendas(String[] pathFile){
        this.pathFile = pathFile;
    }

    /**
     * Adiciona modelo
     * @param model Modelo
     */
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * Adiciona view
     * @param view View
     */
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Da inicio ao Menu
     */
    public void start() {
        caseMenu();
    }

    /**
     * Efetua print das estatisticas no case 11
     */
    private void estaticas(){
        view.printNewLines();
        view.printEstatisticas(pathFile, model.estatisticas(), model.faturacaoTotal());
        view.printMonthlyPurchases(model.purchasesPerMonth());
        view.printFaturacaoPerMonthPerFilial(model.faturacaoPorMesPorFilial());
        view.printDifClientsPerMonthPerFilial(model.nClientsPurchaseMonthFilial());
    }

    /**
     * Controla o programa e as escolhas do utilizador
     */
    private void caseMenu() {
        view.printNewLines();
        view.printMenu();

        int option = view.getCommand(0, 13);
        switch (option){
            case 1:
                query1();
                break;
            case 2:
                query2();
                break;
            case 3:
                query3();
                break;
            case 4:
                query4();
                break;
            case 5:
                query5();
                break;
            case 6:
                query6();
                break;
            case 7:
                query7();
                break;
            case 8:
                query8();
                break;
            case 9:
                query9();
                break;
            case 10:
                query10();
                break;
            case 11:
                estaticas();
                break;
            case 12:
                saveState();
                break;
            case 13:
                loadState();
                break;
            default:
                break;
        }
        if(option <= 10 && option !=0)
            view.printQueryTime(Crono.print());
        view.proceed();
        if(option != 0)
            caseMenu();
        else
            view.printClose();
    }

    /**
     * Retoma o state de um programa gravado Default: gestVendas.dat
     */
    private void loadState() {
        IObjectStream a = new ObjectStream();
        a.loadGestVendas(this, view);

    }

    /**
     * grava o state do programa Default: gestVendas.dat
     */
    private void saveState(){
        IObjectStream a = new ObjectStream();
        a.saveGestVendas(this, view);
    }

    /**
     * Limpa o terminal e faz print da query1
     */
    private void query1(){
        view.printNewLines();
        view.printQuery1(model.query1());
    }

    /**
     * Pede input mes,valida,limpa o terminal e faz print da query2
     */
    private void query2(){
        int month = view.getMes();

        if (month > 12 || month<1 ){
            view.printNewLines();
            view.printInputError();
        }
        else {
            view.printNewLines();
            view.printQuery2(model.query2(month));
        }
    }

    /**
     * Pede input do codigo do cliente ex: "Z5000",valida,limpa o terminal e faz print da query3
     */
    private void query3(){
        String codCli = view.getCodCli();

        if (Cliente.validateClient(codCli)){
            view.printNewLines();
            view.printQuery3(model.query3(codCli));
        }
        else{
            view.printNewLines();
            view.printInputError();
        }
    }

    /**
     * Pede input do codigo do produto ex: "AA1001",valida,limpa o terminal e faz print da query4
     */
    private void query4(){
        String codProd = view.getCodProd();
        if (Produto.validateProduct(codProd)) {
            view.printNewLines();
            view.printQuery4(model.query4(codProd));
        }
        else {
            view.printNewLines();
            view.printInputError();
        }
    }

    /**
     * Pede input do codigo do Cliente ex: "Z5000",valida,limpa o terminal e faz print da query5
     */
    private void query5(){
        String codCli = view.getCodCli();

        if (Cliente.validateClient(codCli)) {
            view.printNewLines();
            view.printQuery5(model.query5(codCli));
        }
        else {
            view.printNewLines();
            view.printInputError();
        }

    }

    /**
     * Pede input do numero de produtos ,limpa o terminal e faz print da query6
     */
    private void query6() {
        out.println();
        int x = view.getInt(0, 0);
        view.printQuery6(model.query6(x));
    }
    /**
     * limpa o terminal e faz print da query7
     */
    private void query7(){
        out.println();
        view.printNewLines();
        view.printQuery7(model.query7());
    }

    /**
     * Pede input do numero de Clientes ,limpa o terminal e faz print da query8
     */
    private void query8(){
        out.println();
        int x = view.getInt(0,0);
        view.printQuery8(model.query8(x));
    }

    /**
     * Pede input do codigo de produto e numero de Clientes,valida,limpa o terminal e faz print da query9
     */
    private void query9(){
        String codProd = view.getCodProd();
        if (Produto.validateProduct(codProd)) {
            out.println();
            int x = view.getInt(0, 0);
            view.printQuery9(model.query9(codProd, x));
        }
        else{
            view.printNewLines();
            view.printInputError();
        }
    }

    /**
     * Pede input do codigo de produto,limpa o terminal e faz print da query9
     */
    private void query10(){
        String codProd = view.getCodProd();
        if (Produto.validateProduct(codProd)) {
            view.printNewLines();
            view.printQuery10(model.query10(codProd));
        }
        else{
            view.printNewLines();
            view.printInputError();
        }
    }

}

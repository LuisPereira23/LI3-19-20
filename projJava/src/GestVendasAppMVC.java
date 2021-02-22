import Controller.IGestVendas;
import Controller.GestVendas;
import model.main.IModel;
import model.main.Model;
import view.IView;
import view.View;

import java.io.IOException;

import static java.lang.System.out;

public class GestVendasAppMVC {

    public static void main(String[] args) throws IOException {
        IModel model = new Model();
        String[] pathFile = model.loadData();
        if(pathFile == null){
            out.println("Data creation error");
            System.exit(-1);
        }
        IView view = new View();
        IGestVendas gestor = new GestVendas(pathFile);
        gestor.setModel(model);
        gestor.setView(view);
        gestor.start();
        System.exit(0);
    }
}

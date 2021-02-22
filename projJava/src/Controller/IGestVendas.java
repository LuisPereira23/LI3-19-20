package Controller;

import model.main.IModel;
import view.IView;

import java.io.IOException;

public interface IGestVendas {
    void setModel(IModel model);
    void setView(IView view);
    void start() throws IOException;
}

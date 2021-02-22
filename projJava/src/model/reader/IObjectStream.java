package model.reader;

import Controller.GestVendas;
import view.IView;

public interface IObjectStream {
    void loadGestVendas(GestVendas c, IView view);
    void saveGestVendas(GestVendas c, IView view);
}


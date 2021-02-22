package model.reader;

import model.Cat.ICatVendas;
import model.Cat.ICat;

import java.io.IOException;

public interface IReader {
    void readVendas(ICatVendas vendas, String pathFile) throws IOException;
    void readCat(ICat cat, String pathFile) throws IOException;
    String[] readPath() throws IOException;
}


package model.reader;

import model.Cat.ICat;
import model.Cat.CatClientes;
import model.Cat.ICatVendas;
import model.data.Cliente;
import model.data.Produto;
import model.data.Venda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements IReader {

    /**
     * Le o txt com os nomes dos ficheiros a fazer load
     * @return string com os filenames
     */
    public String[] readPath() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("data/config.txt"));
        String[] lines = new String[4];
        lines[0] = br.readLine();
        lines[1] = br.readLine();
        lines[2] = br.readLine();
        lines[3] = br.readLine();
        return lines;
    }

    /**
     * Le o txt vendas e adiciona as validas as vendas
     * @param pathFile caminho do ficheiro
     * @param sales Catalogo de vendas
     */
    public void readVendas(ICatVendas sales, String pathFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathFile));
        String line;
        while ((line = br.readLine()) != null)
            if(Venda.validateSale(line))
                sales.add(line);
    }

    /**
     * Le o txt vendas e adiciona as validas as vendas
     * @param pathFile caminho do ficheiro
     * @param cat Catalogo
     */
    public void readCat(ICat cat, String pathFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathFile));
        String line;
        boolean clientes = cat instanceof CatClientes;
        while ((line = br.readLine()) != null)
            if(clientes && Cliente.validateClient(line)
                    || Produto.validateProduct(line))
                cat.add(line);
    }
}


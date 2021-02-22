package model.Cat;

import java.io.Serializable;
import java.util.*;

public class CatClientes implements ICat, Serializable {

    private static final long serialVersionUID = 1701417010762562615L;
    private List<Map<Integer, Object>> cat;

    /**
     * Constructor for the client catalog
     */
    public CatClientes(){
        this.cat = new ArrayList<>();
        for(int i = 0 ; i < 26 ; i++)
            cat.add(new TreeMap<>());
    }

    /**
     * Clones the client catalog
     * @return Cloned client catalog
     */
    public ICat clone() {
        ICat cat = new CatClientes();
        for(int i = 0 ; i < 26 ; i++){
            for(int cod : this.cat.get(i).keySet()){
                char letter = (char) (i+'A');
                cat.add(letter+""+cod, getValue(letter+""+cod));
            }
        }
        return cat;
    }

    /**
     * Adds client with no value/null value
     * @param key Clients key to add
     */
    public void add(String key) {
        add(key, null);
    }

    /**
     * Add value to a client
     * @param key Clients key to put in
     * @param value Value to store inside client
     */
    public void add(String key, Object value) {
        int posicao = key.charAt(0) - 'A';
        int numero = Integer.valueOf(key.substring(1));
        cat.get(posicao).put(numero, value);
    }

    /**
     * Searches and returns the value of a client
     * @param clientLine String with client identity "Z5000"
     */
    public Object getValue(String clientLine) {
        if(!isInCat(clientLine))
            return null;
        int position = clientLine.charAt(0) - 'A';
        int number = Integer.valueOf(clientLine.substring(1));
        return cat.get(position).get(number);
    }

    /**
     * travels the array and returns all values
     * @return ArrayList with all client values
     */
    public Collection<Object> getAllValues() {
        Collection<Object> a = new ArrayList<>();
        for(int i = 0 ; i < 26 ; i++)
            a.addAll(cat.get(i).values());
        return a;
    }

    /**
     * travels the array and returns the key for each client
     * @return ArrayList with all client keys
     */
    public Collection<String> getAllKeys() {
        Collection<String> a = new ArrayList<>();
        for(int i = 0 ; i < 26 ; i++){
            for(int number : cat.get(i).keySet()){
                char letter = (char) (i+'A');
                a.add(letter+""+number);
            }
        }
        return a;
    }

    /**
     * travels the array and returns treemap with all clients
     * @return TreeMap with all clients
     */
    public Map<String, Object> getAll() {
        Map<String, Object> t = new TreeMap<>();
        for(int i = 0 ; i < 26 ; i++){
            for(int number : cat.get(i).keySet()){
                char letter = (char) (i+'A');
                    t.put(letter+""+number, getValue(letter+""+number));
            }
        }
        return t;
    }

    /**
     * Removes a client from the catalog using a string
     * @param clientLine String with client identity "Z5000"
     */
    public void remove(String clientLine) {
        int position = clientLine.charAt(0) - 'A';
        int number = Integer.valueOf(clientLine.substring(1));
        cat.get(position).remove(number);
    }

    /**
     * Removes a client from the catalog using the key
     * @param clientKeys Client key to remove
     */
    public void remove(Collection<String> clientKeys) {
        for(String s : clientKeys)
            remove(s);
    }

    /**
     * Searches the Hashmap for a client
     * @param clientLine String with client identity "Z5000"
     * @return True if client exists, False otherwise
     */
    public boolean isInCat(String clientLine) {
        int position = clientLine.charAt(0) - 'A';
        int number = Integer.valueOf(clientLine.substring(1));
        return cat.get(position).containsKey(number);
    }

    /**
     * Calculates and returns current size of client catalog
     * @return size of catalog
     */
    public int catSize() {
        int s = 0;
        for(int i = 0 ; i < 26 ; i++)
            s += cat.get(i).size();
        return s;
    }
}
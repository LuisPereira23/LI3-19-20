package model.Cat;

import java.io.Serializable;
import java.util.*;

public class CatProdutos implements ICat, Serializable {

    private static final long serialVersionUID = 743963785172488563L;
    private List<List<Map<Integer, Object>>> cat;

    /**
     * Constructor for the product catalog
     */
    public CatProdutos(){
        this.cat = new ArrayList<>();
        for(int i = 0 ; i < 26 ; i++){
            List<Map<Integer, Object>> l = new ArrayList<>();
            cat.add(l);
            for(int j = 0 ; j < 26 ; j++)
                l.add(new TreeMap<>());
        }
    }

    /**
     * Clones the product catalog
     * @return Cloned product catalog
     */
    public ICat clone() {
        ICat c = new CatProdutos();
        for(int i = 0 ; i < 26 ; i++){
            for(int j = 0 ; j < 26 ; j++){
                for(int number : cat.get(i).get(j).keySet()){
                    char letter1 = (char) (i+'A');
                    char letter2 = (char) (j+'A');
                    c.add(letter1+""+letter2+""+number, getValue(letter1+""+letter2+""+number));
                }
            }
        }
        return c;
    }

    /**
     * Adds product with no value/null value
     * @param key Procuct  key to add
     */
    public void add(String key) {
        add(key, null);
    }

    /**
     * Add value to a product
     * @param productKey Products key to put in
     * @param value Value to store inside product
     */
    public void add(String productKey, Object value) {
        int index1 = productKey.charAt(0) - 'A';
        int index2 = productKey.charAt(1) - 'A';
        int number = Integer.valueOf(productKey.substring(2));
        cat.get(index1).get(index2).put(number, value);
    }

    /**
     * Removes a product from the catalog using a string
     * @param productKey String with product identity "AA1001"
     */
    public void remove(String productKey) {
        int index1 = productKey.charAt(0) - 'A';
        int index2 = productKey.charAt(1) - 'A';
        int number = Integer.valueOf(productKey.substring(2));
        cat.get(index1).get(index2).remove(number);
    }

    /**
     * Removes a product from the catalog using the key
     * @param productKeys Product key to remove
     */
    public void remove(Collection<String> productKeys) {
        for(String s : productKeys)
            remove(s);
    }

    /**
     * Searches the Hashmap for a product
     * @param productLine  String with product identity "AA1001"
     * @return True if product exists, False otherwise
     */
    public boolean isInCat(String productLine) {
        int index1 = productLine.charAt(0) - 'A';
        int index2 = productLine.charAt(1) - 'A';
        int number = Integer.valueOf(productLine.substring(2));
        return cat.get(index1).get(index2).containsKey(number);
    }

    /**
     * Calculates and returns current size of product catalog
     * @return size of catalog
     */
    public int catSize() {
        int s = 0;
        for(int i = 0 ; i < 26 ; i++)
            for(int j = 0 ; j < 26 ; j++)
                s += cat.get(i).get(j).size();
        return s;
    }

    /**
     * Searches and returns the value of a product
     * @param productLine  String with product identity "AA1001"
     */
    public Object getValue(String productLine) {
        if(!isInCat(productLine))
            return null;
        int index1 = productLine.charAt(0) - 'A';
        int index2 = productLine.charAt(1) - 'A';
        int number = Integer.valueOf(productLine.substring(2));
        return cat.get(index1).get(index2).get(number);
    }

    /**
     * travels the matrix and returns all values
     * @return ArrayList with all product values
     */
    public Collection<Object> getAllValues() {
        Collection<Object> a = new ArrayList<>();
        for(int i = 0 ; i < 26 ; i++)
            for(int j = 0 ; j < 26 ; j++)
                a.addAll(cat.get(i).get(j).values());
        return a;
    }

    /**
     * travels the matrix and returns the key for each product
     * @return ArrayList with all products keys
     */
    public Collection<String> getAllKeys() {
        Collection<String> a = new ArrayList<>();
        for(int i = 0 ; i < 26 ; i++){
            for(int j = 0 ; j < 26 ; j++){
                for(int number : cat.get(i).get(j).keySet()){
                    char index1 = (char) (i+'A');
                    char index2 = (char) (j+'A');
                    a.add(index1+""+index2+""+number);
                }
            }
        }
        return a;
    }

    /**
     * travels the array and returns treemap with all products
     * @return TreeMap with all products
     */
    public Map<String, Object> getAll() {
        Map<String, Object> a = new TreeMap<>();
        for(int i = 0 ; i < 26 ; i++){
            for(int j = 0 ; j < 26 ; j++){
                for(int number : cat.get(i).get(j).keySet()){
                    char index1 = (char) (i+'A');
                    char index2 = (char) (j+'A');
                    a.put(index1+""+index2+""+number, getValue(index1+""+index2+""+number));
                }
            }
        }
        return a;
    }

}

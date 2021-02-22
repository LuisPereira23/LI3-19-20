package model.Cat;

import java.util.Collection;
import java.util.Map;

public interface ICat {
    void add(String key);
    void add(String key, Object value);
    void remove(String key);
    void remove(Collection<String> keys);
    boolean isInCat(String key);
    int catSize();
    Object getValue(String key);
    Collection<Object> getAllValues();
    Collection<String> getAllKeys();
    Map<String, Object> getAll();
    ICat clone();
}

package io.tatav.learningpc.utils;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tatav on 15/08/2017.
 */
public final class PairMap<K, V> extends ArrayList<KeyValuePair<K, V>>
        implements Serializable, Iterable<KeyValuePair<K, V>> {
  public PairMap(PairMap<K, V> pairMap) {
    for (KeyValuePair<K, V> pair : pairMap)
      this.add(pair);
  }

  public PairMap(int initialCapacity) {
    super(initialCapacity);
  }

  @Override
  public Iterator<KeyValuePair<K, V>> iterator() {
    return new PairMapIterator(this);
  }

  public boolean containsKey(K key) {
    for (KeyValuePair<K, V> pair : this)
      if (pair.getKey().equals(key))
        return true;
    return false;
  }

  public boolean containsValue(V value) {
    for (KeyValuePair<K, V> pair : this)
      if (pair.getValue().equals(value))
        return true;
    return false;
  }

  public V put(KeyValuePair<K, V> pair) {
    if (pair != null) {
      if (!containsKey(pair.getKey()))
        add(pair);
      else update(pair.getKey(), pair.getValue());
      return pair.getValue();
    }
    return null;
  }

  public V put(K key, V value) {
    add(new KeyValuePair(key, value));
    return value;
  }

  public int indexOfKey(K key) {
    for (int i = 0; i < size(); ++i)
      if (get(i).getKey().equals(key))
        return i;
    return -1;
  }

  public void update(K key, V value) {
    if (containsKey(key))
      get(indexOfKey(key)).setValue(value);
    else
      put(key, value);
  }

  public void update(KeyValuePair<K, V> pair) {
    if (pair != null) {
      if (containsKey(pair.getKey()))
        get(indexOfKey(pair.getKey())).setValue(pair.getValue());
      else
        put(pair);
    }
  }

  public int indexOfValue(V value) {
    for (int i = 0; i < size(); ++i)
      if (get(i).getValue().equals(value))
        return i;
    return -1;
  }

  @Nullable
  public V get(K key) {
    return this.containsKey(key) ? get(indexOfKey(key)).getValue() : null;
  }
}

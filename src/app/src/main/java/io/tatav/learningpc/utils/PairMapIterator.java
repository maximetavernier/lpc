package io.tatav.learningpc.utils;

import java.util.Iterator;

/**
 * Created by Tatav on 15/08/2017.
 */

public class PairMapIterator<K, V> implements Iterator<KeyValuePair<K, V>> {
  private PairMap<K, V> pairMap;
  private int cursor;

  public PairMapIterator(PairMap<K, V> pairMap) {
    this.pairMap = pairMap;
    cursor = 0;
  }

  @Override
  public boolean hasNext() {
    return this.cursor < pairMap.size();
  }

  @Override
  public KeyValuePair<K, V> next() {
    if (this.hasNext()) {
      KeyValuePair<K, V> current = pairMap.get(cursor);
      ++cursor;
      return current;
    }
    return null;
  }
}

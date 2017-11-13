package io.tatav.learningpc.utils;

import java.io.Serializable;
import java.util.Random;

/**
 * Guid Class to generate id for and from the database.
 * @author Tatav
 * @version 0.1
 */
public final class Guid implements Serializable, Comparable<Guid> {
  public static final transient Guid Empty = new Guid("00000000-0000-0000-0000-000000000000");

  private char[] internal_guid;

  public Guid(String g) {
    this.internal_guid = isParsable(g)
            ? g.toUpperCase().toCharArray()
            : "00000000-0000-0000-0000-000000000000".toCharArray();
  }

  public Guid(char[] c) {
    this.internal_guid = c;
  }

  public static Guid NewGuid() {
    StringBuilder str = new StringBuilder();
    Random r = new Random();

    for (int i = 0; i < 8; ++i)
      str.append(String.format("%01X", positive(r.nextInt() % 16)).toUpperCase());
    str.append('-');
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 4; ++j)
        str.append(String.format("%01X", positive(r.nextInt() % 16)).toUpperCase());
      str.append('-');
    }
    for (int i = 0; i < 12; ++i)
      str.append(String.format("%01X", positive(r.nextInt() % 16)).toUpperCase());

    return new Guid(str.toString());
  }

  private static boolean isParsable(String input) {
    /**
     * Format : F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4
     */
    if (input.length() != 36)
      return false;
    for (int i = 0; i < input.length(); ++i) {
      char c = input.charAt(i);
      if ((i == 8 || i == 13 || i == 18 || i == 23) && c == '-')
        continue;
      if (c >= 65 && c <= 90 || c >= 48 && c <= 57 || c >= 97 && c <= 122)
        continue;
      return false;
    }
    return true;
  }

  public static Guid TryParse(String input) throws GuidException {
    if (isParsable(input))
      return new Guid(input);
    throw new GuidException("Unvalid input format");
  }

  private char charAt(int i) {
    return i >= 0 && i < 36 ? internal_guid[i] : 0;
  }

  @Override
  public int compareTo(Guid o) {
    int result = 0;
    for (int i = 0; i < 36; ++i)
      if ((result = o.internal_guid[i] - internal_guid[i]) != 0)
        break;
    return result;
  }

  @Override
  public String toString() {
    return new String(internal_guid);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Guid ? equals((Guid) obj)
            : obj instanceof String ? equals(new Guid((String) obj))
            : obj instanceof char[] ? equals(new Guid((char[]) obj))
            : false;
  }

  public Guid toUpperCase() {
    char[] uppercaseGuid = new char[36];
    for (short s = 0; s < 36; ++s)
      uppercaseGuid[s] = internal_guid[s] >= 97 && internal_guid[s] <= 122 ? (char) (internal_guid[s] - 32) : internal_guid[s];

    return new Guid(uppercaseGuid);
  }

  public Guid toLowerCase() {
    char[] lowerGuid = new char[36];
    for (short s = 0; s < 36; ++s)
      lowerGuid[s] = internal_guid[s] >= 65 && internal_guid[s] <= 90 ? (char) (internal_guid[s] + 32) : internal_guid[s];

    return new Guid(lowerGuid);
  }

  public boolean equals(Guid guid) {
    Guid test = guid.toLowerCase();
    Guid that = new Guid(internal_guid).toLowerCase();

    for (int i = 0; i < 36; ++i)
      if (test.charAt(i) != that.charAt(i))
        return false;
    return true;
  }

  private static int positive(int i) {
    return i < 0 ? -i : i;
  }
}

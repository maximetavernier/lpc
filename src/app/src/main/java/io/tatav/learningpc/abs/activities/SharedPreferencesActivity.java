package io.tatav.learningpc.abs.activities;

/**
 * Created by Tatav on 15/02/2017.
 */
public interface SharedPreferencesActivity {
  boolean setPreferences(String key, String value);

  String getPreferences(String key);
}

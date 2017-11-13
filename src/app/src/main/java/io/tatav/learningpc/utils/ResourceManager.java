package io.tatav.learningpc.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.InputStream;
import java.util.Locale;

import io.tatav.learningpc.R;

/**
 * Created by Tatav on 07/03/2017.
 */

public final class ResourceManager {
  private static final String[] ALLOWED_LANGUAGES = {
          "fr",
          "en"
  };
  private static final String DEFAULT_LANGUAGE = "en";

  public static final String getLangage() {
    String lang = Locale.getDefault().getLanguage().toLowerCase();

    for (String l : ALLOWED_LANGUAGES)
      if (l.equals(lang))
        return lang;
    return DEFAULT_LANGUAGE;
  }

  private static final int getRawIdByName(Context context, String name) {
    int rawId = 0;
    if (!isNullOrEmpty(name))
      rawId = context.getResources().getIdentifier(name, "raw", context.getPackageName());
    return rawId;
  }

  public static final InputStream getRawByName(Context context, String name) {
    int rawId = getRawIdByName(context, name);

    return rawId != 0 ? context.getResources().openRawResource(rawId) : null;
  }

  private static final int getDrawableIdByName(Context context, String name, String defaultName) {
    int drawableId = 0;
    if (!isNullOrEmpty(name))
      drawableId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    return drawableId != 0 ? drawableId : context.getResources().getIdentifier(!isNullOrEmpty(defaultName) ? defaultName : "", "drawable", context.getPackageName());
  }

  public static final Drawable getDrawableByName(Context context, String name, String defaultName) {
    int drawableId = getDrawableIdByName(context, name, defaultName);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      return drawableId != 0 ? context.getDrawable(drawableId) : null;
    else
      return drawableId != 0 ? context.getResources().getDrawable(drawableId) : null;
  }

  private static final boolean isNullOrEmpty(String name) {
    return name == null || name.length() == 0;
  }
}

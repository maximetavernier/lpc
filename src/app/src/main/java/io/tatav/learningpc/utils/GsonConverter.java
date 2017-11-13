package io.tatav.learningpc.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.tatav.learningpc.abs.entities.LearningModel;

/**
 * Created by Tatav on 05/03/2017.
 */
public final class GsonConverter {
  private static final Gson getGson() {
    return new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .registerTypeAdapter(Guid.class, getJsonGuidConverter())
            .registerTypeAdapter(LearningModelStatus.class, getJsonLearningModelStatusConverter())
            .registerTypeAdapter(KeyValuePair.class, getKeyValuePairJsonConverter())
            .registerTypeAdapter(KeyValuePair.class, getJsonKeyValuePairConverter())
            .registerTypeAdapter(PairMap.class, getJsonPairMapConverter())
            .create();
  }

  private static final JsonDeserializer<Guid> getJsonGuidConverter() {
    return new JsonDeserializer<Guid>() {
      @Override
      public Guid deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
          return Guid.TryParse(json.getAsJsonPrimitive().getAsString());
        } catch (GuidException e) {
          Log.e(Tags.APPLICATION_TAG, e.getMessage());
        }
        return Guid.Empty;
      }
    };
  }

  private static final JsonDeserializer<LearningModelStatus> getJsonLearningModelStatusConverter() {
    return new JsonDeserializer<LearningModelStatus>() {
      @Override
      public LearningModelStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
          return LearningModelStatus.getStatus(json.getAsJsonPrimitive().getAsInt());
        } catch (LearningModelStatusException e) {
          Log.e(Tags.APPLICATION_TAG, e.getMessage());
        }
        return null;
      }
    };
  }

  private static final JsonSerializer<KeyValuePair<Guid, LearningModelStatus>> getKeyValuePairJsonConverter() {
    return new JsonSerializer<KeyValuePair<Guid, LearningModelStatus>>() {
      @Override
      public JsonElement serialize(KeyValuePair<Guid, LearningModelStatus> src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("key", src.getKey().toString());
        obj.addProperty("value", src.getValue().toString());
        return obj;
      }
    };
  }

  private static final JsonDeserializer<KeyValuePair<Guid, LearningModelStatus>> getJsonKeyValuePairConverter() {
    return new JsonDeserializer<KeyValuePair<Guid, LearningModelStatus>>() {
      @Override
      public KeyValuePair<Guid, LearningModelStatus> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
          JsonObject obj = json.getAsJsonObject();
          return new KeyValuePair(new Guid(obj.get("key").getAsString()), LearningModelStatus.valueOf(obj.get("value").getAsString()));
        } catch (Exception e) {
          Log.e(Tags.APPLICATION_TAG, e.getMessage());
        }
        return null;
      }
    };
  }

  private static final JsonDeserializer<PairMap> getJsonPairMapConverter() {
    return new JsonDeserializer<PairMap>() {
      @Override
      public PairMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
          JsonArray array = json.getAsJsonArray();
          PairMap map = new PairMap(array.size());

          for (JsonElement elem : array) {
            JsonObject obj = elem.getAsJsonObject();
            KeyValuePair pair = new KeyValuePair(obj.get("key").toString(), obj.get("value"));
            map.put(pair);
          }
          return map;
        } catch (Exception e) {
          Log.e(Tags.APPLICATION_TAG, e.getMessage());
        }
        return null;
      }
    };
  }

  public static final<T extends LearningModel> List<T> getListFromJsonArrayCallback(final String json, final Class<T> c) {
    List l = null;
    try {
      JSONArray array = new JSONArray(json);
      l = new ArrayList<>(array.length());
      for (int i = 0; i < array.length(); ++i) {
        String item = array.getJSONObject(i).toString();
        l.add(getGson().fromJson(item, c));
      }
      return l;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return l;
  }

  public static final PairMap getPairMapFromJsonArrayCallback(final String json) {
    PairMap m = null;
    if (json != null && json.length() > 2)
      try {
        JSONArray array = new JSONArray(json);

        m = new PairMap(array.length());
        for (int i = 0; i < array.length(); ++i)
          m.add(getGson().fromJson(array.getJSONObject(i).toString(), KeyValuePair.class));
        return m;
      } catch (JSONException e) {
        e.printStackTrace();
      }
    return m;
  }

  public static final String getJsonArrayFromPairMapCallback(final PairMap map) {
    return getGson().toJson(map, PairMap.class);
  }
}

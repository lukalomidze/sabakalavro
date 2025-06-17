package ge.edu.cu.l_lomidze2.sabakalavro.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
        .create();
    }

    public static <T> List<T> toList(String json, Class<T> listTypeClass) throws JsonSyntaxException {
        Type typeOfT = TypeToken.getParameterized(List.class, listTypeClass).getType();

        List<T> list = gson.fromJson(
            json,
            typeOfT
        );

        if(list == null){
            list = new ArrayList<T>();
        }

        return list;
    }

    public static <T> List<T> toList(Path json, Class<T> listTypeClass) throws JsonSyntaxException, IOException {
        Type typeOfT = TypeToken.getParameterized(List.class, listTypeClass).getType();

        List<T> list = gson.fromJson(
            Files.readString(json),
            typeOfT
        );

        if(list == null){
            list = new ArrayList<T>();
        }

        return list;
    }

    public static <T> String toJson(List<T> list) {
        return gson.toJson(list);
    }

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public static <T> void toJsonFile(List<T> list, Path path) throws IOException{
        Files.writeString(
            path,
            toJson(list)
        );
    }

    public static <T> void toJsonFile(T object, Path path) throws IOException{
        Files.writeString(
            path,
            toJson(object)
        );
    }
}

class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(date.format(formatter));
    }

    @Override
    public LocalDate deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return LocalDate.parse(json.getAsString(), formatter);
    }
}

package com.palmithor.veggies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmithor.veggies.util.Rfc339DateJsonAdapter;

import java.util.Date;

/**
 * @author palmithor
 * @since 8.2.2017.
 */
public class TestUtils {

    public static Gson getDefaultGson() {
        return new GsonBuilder().registerTypeAdapter(Date.class, new Rfc339DateJsonAdapter()).create();
    }
}

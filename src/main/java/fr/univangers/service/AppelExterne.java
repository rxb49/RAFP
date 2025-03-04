package fr.univangers.service;

import com.google.gson.Gson;
import fr.univangers.classes.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AppelExterne {

    @Value("${url.api.appscho}")
    String url ;


    /**
     * Fonction qui récupère des informations d'une API
     * @param login : Login passé en paramètre
     * @return : Un User contenant les infos de l'API
     * @throws IOException : IOException
     */
    public User info(String login) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url+login)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        Gson gson = new Gson();
        return gson.fromJson(responseBody.string(), fr.univangers.classes.User.class);
    }
}

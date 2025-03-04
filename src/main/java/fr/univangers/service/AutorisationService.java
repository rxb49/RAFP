package fr.univangers.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.univangers.classes.AccesGestionnaire;
import fr.univangers.exceptions.NonAutorisationException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutorisationService {
    @Value("${url.api}")
    String url ;

    @Value("${api.apiKeyAccess}")
    private String apiKeyAccess;

    private final Logger logger = LogManager.getLogger(AutorisationService.class);

    /**
     * Vérifie si l'utilisateur connecté a bien accès à l'application
     * @param idEncrypt : Id encrypt de l'utilisateur
     * @throws Exception : Exception
     */
    public void verifAutorisation(String idEncrypt) throws Exception {

        logger.info("Début de l'appel à l'API qui se vérifie si l'utilisateur a bien accès à l'application.");

        boolean aAccess = false;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url + idEncrypt)
                .header("X-Token", generateTokenAPIUA(apiKeyAccess))
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        Response responseHTTP = client.newCall(request).execute();
        ResponseBody responseBody = responseHTTP.body();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<AccesGestionnaire>>(){}.getType();
        List<AccesGestionnaire> access = gson.fromJson(responseBody.string(), listType);

        if (access.size() > 0) {
            aAccess = true;
        }

        if (!aAccess){
            logger.warn("Un utilisateur essaye de se connecter alors qu'il n'a pas accès à l'application - idEncrypt : "+idEncrypt+".");
            throw new NonAutorisationException();
        }

        logger.info("Fin de l'appel à l'API qui se vérifie si l'utilisateur a bien accès à l'application.");
    }

    private String generateTokenAPIUA(String key){
        long timestamp = System.currentTimeMillis() / 1000;
        String token = "key="+encoding(key+"-"+timestamp)+",timestamp="+timestamp;
        return token;
    }
    private String encoding(String chaine){
        String encoding = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(chaine.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encoding = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return encoding;
    }


}

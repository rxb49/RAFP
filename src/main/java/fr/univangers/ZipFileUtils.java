package fr.univangers;

import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ZipFileUtils {

    /**
     * Télécharge un fichier ZIP et l'envoie dans la réponse HTTP.
     *
     * @param zipFile  Le fichier ZIP à télécharger.
     * @param response L'objet HttpServletResponse pour envoyer le fichier.
     * @throws IOException En cas d'erreur de lecture/écriture.
     */
    public static void sendZipFile(File zipFile, HttpServletResponse response) throws IOException {
        if (zipFile == null || !zipFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Aucun fichier ZIP trouvé.");
            return;
        }

        // Configurer la réponse HTTP
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipFile.getName());
        response.setContentLength((int) zipFile.length());

        // Envoyer le fichier ZIP en réponse
        try (FileInputStream fis = new FileInputStream(zipFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}

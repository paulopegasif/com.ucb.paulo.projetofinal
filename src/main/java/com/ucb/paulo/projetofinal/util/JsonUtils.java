package com.ucb.paulo.projetofinal.util;

// Bibliotecas
import com.ucb.paulo.projetofinal.model.Article;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

///  Ler JSON, transformar cada Objeto em uma instância de Article

public class JsonUtils {
    public static List<Article> carregarArtigos(String caminhoArquivo) {
        List<Article> artigos = new ArrayList<>();

        try(InputStream is = new FileInputStream(caminhoArquivo)) {
            JSONArray jsonArray = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.optString("title", "Sem titulo");
                String abstractText = jsonObject.optString("abstract", "Sem texto");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return artigos;
    }

}

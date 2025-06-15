package com.ucb.paulo.projetofinal.servers;

import com.ucb.paulo.projetofinal.model.Article;
import com.ucb.paulo.projetofinal.util.JsonUtils;
import com.ucb.paulo.projetofinal.util.KMP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerB {
    private static final int PORT = 5001; // Porta dedicada do servidor B
    private static final String JSON_PATH = "src/main/resources/dados_servidor_b.json";

    public static void main(String[] args) {
        System.out.println("Servidor B aguardando conexões na porta " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                String termoBusca = reader.readLine();
                System.out.println("[Server B] Termo recebido: " + termoBusca);


                List<Article> artigos = JsonUtils.carregarArtigos("dados_servidor_b.json");
                List<Article> encontrados = new ArrayList<>();
                System.out.println("[Server B] Artigos Encontrados: " + artigos.size()); //debugando





                for (Article artigo : artigos) {
                    if (artigo.getTitle().toLowerCase().contains(termoBusca.toLowerCase()) ||
                            artigo.getAbstractText().toLowerCase().contains(termoBusca.toLowerCase())) {
                        encontrados.add(artigo);
                    }
                }

                // retornando resultados
                for (Article artigo : encontrados) {
                    writer.println(artigo.getTitle() + " | " + artigo.getAbstractText());
                }

                writer.println("FIM"); // marca final da lista de resultados
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
                System.out.println("\n[Server B] Termo recebido: " + termoBusca);


                List<Article> artigos = JsonUtils.carregarArtigos("dados_servidor_b.json");
                List<Article> encontrados = new ArrayList<>();

                int ocorrencias = 0;

                //System.out.println("[Server B] Total de  artigos: " + artigos.size()); //debugando





                for (Article artigo : artigos) {
                    String titulo = artigo.getTitle().toLowerCase();
                    String resumo = artigo.getAbstractText().toLowerCase();
                    String termo = termoBusca.toLowerCase();

                    boolean contem = titulo.contains(termo) || resumo.contains(termo);

                    if (contem) {
                        encontrados.add(artigo);
                    }

                    // conta quantas vezes o termo aparece
                    ocorrencias += contarOcorrencias(titulo, termo);
                    ocorrencias += contarOcorrencias(resumo, termo);
                }

                // retornando resultados
                for (Article artigo : encontrados) {
                    writer.println(artigo.getTitle() + " | " + artigo.getAbstractText());
                }

                writer.println("[OCORRENCIAS] " + ocorrencias);
                writer.println("FIM"); // marca final da lista de resultados

                //System.out.println("[Server B] Artigos que contêm o termo: " + encontrados.size());
                System.out.println("[Server B] Ocorrências do termo \"" + termoBusca + "\": " + ocorrencias);

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int contarOcorrencias(String texto, String termo) {
        int count = 0;
        int index = 0;
        while ((index = texto.indexOf(termo, index)) != -1) {
            count++;
            index += termo.length();
        }
        return count;
    }
}
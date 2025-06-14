package com.ucb.paulo.projetofinal.servers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerA {
    private static final int PORTA_CLIENTE = 5050; //5000 ocupada
    private static final String HOST = "localhost";
    private static final int PORTA_B = 5001;
    private static final int PORTA_C = 5002;

    public static void main(String[] args) {
        System.out.println("Servidor A aguardando conexões do cliente na porta " + PORTA_CLIENTE + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORTA_CLIENTE)) {
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                BufferedReader readerCliente = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                PrintWriter writerCliente = new PrintWriter(clienteSocket.getOutputStream(), true);

                String termoBusca = readerCliente.readLine();
                System.out.println("Cliente enviou: " + termoBusca);

                // envia termo para B e C e coleta resultados
                List<String> resultados = new ArrayList<>();
                resultados.addAll(buscarNoServidor(PORTA_B, termoBusca));
                resultados.addAll(buscarNoServidor(PORTA_C, termoBusca));

                // envia os resultados de volta ao client
                for (String linha : resultados) {
                    writerCliente.println(linha);
                }
                writerCliente.println("FIM");

                clienteSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> buscarNoServidor(int porta, String termo) {
        List<String> resposta = new ArrayList<>();
        try (Socket socket = new Socket(HOST, porta)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(termo);

            String linha;
            while (!(linha = reader.readLine()).equals("FIM")) {
                resposta.add(linha);
            }

        } catch (IOException e) {
            System.err.println("Erro ao conectar no servidor na porta " + porta);
            e.printStackTrace();
        }
        return resposta;
    }
}
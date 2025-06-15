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
                System.out.println("[Server A] Cliente enviou: " + termoBusca);

                // envia termo para B e C e coleta resultados
                ResultadoServidor resultadoB = buscarNoServidor(PORTA_B, termoBusca);
                ResultadoServidor resultadoC = buscarNoServidor(PORTA_C, termoBusca);

                List<String> resultados = new ArrayList<>();
                resultados.addAll(resultadoB.linhas);
                resultados.addAll(resultadoC.linhas);

                int totalOcorrencias = resultadoB.ocorrencias + resultadoC.ocorrencias;
                System.out.println("\n[Server A] Ocorrências totais do termo \"" + termoBusca + "\": " + totalOcorrencias + "\n");

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

    private static ResultadoServidor buscarNoServidor(int porta, String termo) {
        List<String> resposta = new ArrayList<>();
        int ocorrencias = 0;

        try (Socket socket = new Socket("localhost", porta)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(termo);

            String linha;
            while (!(linha = reader.readLine()).equals("FIM")) {
                if (linha.startsWith("[OCORRENCIAS]")) {
                    ocorrencias = Integer.parseInt(linha.split(" ")[1]);
                } else {
                    resposta.add(linha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao conectar no servidor na porta " + porta);
            e.printStackTrace();
        }

        return new ResultadoServidor(resposta, ocorrencias);
    }
    static class ResultadoServidor {
        List<String> linhas;
        int ocorrencias;

        ResultadoServidor(List<String> linhas, int ocorrencias) {
            this.linhas = linhas;
            this.ocorrencias = ocorrencias;
        }
    }
}
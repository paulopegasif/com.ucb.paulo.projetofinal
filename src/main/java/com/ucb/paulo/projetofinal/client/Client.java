package com.ucb.paulo.projetofinal.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORTA = 5050; //porta serverA

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o termo de busca: ");
        String termo = scanner.nextLine();

        try (Socket socket = new Socket(HOST, PORTA)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // envia o termo
            writer.println(termo);

            // recebe os resultados
            System.out.println("\nResultados encontrados:\n");
            String linha;
            while (!(linha = reader.readLine()).equals("FIM")) {
                System.out.println("• " + linha);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.ucb.paulo.projetofinal.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORTA = 5050; //porta serverA

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        try (Socket socket = new Socket(HOST, PORTA)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter fileWriter = new PrintWriter(new FileWriter("resultados.txt"));

            System.out.print("Digite o termo de busca: ");
            String termo = scanner.nextLine();
            // envia o termo
            writer.println(termo);

            int count = 0;

            // recebe os resultados
            String linha;
            while (!(linha = reader.readLine()).equals("FIM")) {
                fileWriter.println(linha);
                count++;
            }

            fileWriter.close();

            System.out.println("\nTotal encontrados: "+ count);
            System.out.println("Resultados salvos no arquivo resultados.txt ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
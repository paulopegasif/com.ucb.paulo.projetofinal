package com.ucb.paulo.projetofinal;

import com.ucb.paulo.projetofinal.servers.ServerA;
import com.ucb.paulo.projetofinal.servers.ServerB;
import com.ucb.paulo.projetofinal.servers.ServerC;
import com.ucb.paulo.projetofinal.client.Client;

public class Main {
    public static void main(String[] args) {

        System.out.println("Iniciando servidores...");
        // inicia ServerB
        new Thread(() -> ServerB.main(null)).start();
        System.out.println("Server B: Iniciado!");

        // inicia ServerC
        new Thread(() -> ServerC.main(null)).start();
        System.out.println("Server C: Iniciado!");

        // aguarda
        sleep(1000);

        // inicia ServerA
        new Thread(() -> ServerA.main(null)).start();
        System.out.println("Server A: Iniciado!");

        // aguarda
        sleep(1000);

        System.out.println("Iniciando client...");

        // inicia o client com termo de busca teste
        new Thread(() -> Client.main(null)).start();
        System.out.println("Client: Iniciado!");
        System.out.println("\n---------------------------\n");

    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
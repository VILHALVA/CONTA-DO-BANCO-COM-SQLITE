package com.mycompany.contabanco;

import java.util.Scanner;

public class ClienteBanco {
    public static void main(String[] args) {
        Database.createTable();
        
        Scanner input = new Scanner(System.in);

        ContaBanco p1 = new ContaBanco();
        System.out.println("Digite os dados da primeira conta:");
        
        System.out.print("Tipo de Conta (CC ou CP):\n>>> ");
        String tipoConta1 = input.nextLine();
        p1.setTipo(tipoConta1); 
        
        System.out.print("Número da Conta:\n>>> ");
        int numConta1 = input.nextInt();
        p1.setNumConta(numConta1);
        input.nextLine(); 
        
        System.out.print("Nome do Dono:\n>>> ");
        String dono1 = input.nextLine();
        p1.setDono(dono1); 
        System.out.print("Depósito inicial:\n>>> ");
        float depositoInicial1 = input.nextFloat();
        p1.Depositar(depositoInicial1); 
        
        p1.AbrirConta(tipoConta1); 
        p1.estadoAtual(); 
        p1.FecharConta(); 
        
        ContaBanco p2 = new ContaBanco();
        input.nextLine(); 
        
        System.out.println("\nDigite os dados da segunda conta:");
        
        System.out.print("Tipo de Conta (CC ou CP):\n>>> ");
        String tipoConta2 = input.nextLine();
        p2.setTipo(tipoConta2); 

        System.out.print("Número da Conta:\n>>> ");
        int numConta2 = input.nextInt();
        p2.setNumConta(numConta2);
        input.nextLine(); 

        System.out.print("Nome do Dono:\n>>> ");
        String dono2 = input.nextLine();
        p2.setDono(dono2); 

        System.out.print("Depósito inicial:\n>>> ");
        float depositoInicial2 = input.nextFloat();
        p2.Depositar(depositoInicial2); 
        
        p2.AbrirConta(tipoConta2); 
        
        System.out.print("\nValor para sacar da segunda conta:\n>>> ");
        float valorSaque = input.nextFloat();
        p2.Sacar(valorSaque); 
        
        p2.estadoAtual(); 
        input.close(); 
    }
}

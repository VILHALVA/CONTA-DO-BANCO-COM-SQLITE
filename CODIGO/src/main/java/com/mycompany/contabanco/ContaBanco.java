package com.mycompany.contabanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Statement;

public class ContaBanco {
    public int numConta; 
    protected String tipo;
    private String dono;
    private float saldo;
    private boolean Status;

    public ContaBanco() {  
        this.saldo = 0;
        this.Status = false;
    }

    public int getNumConta() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }
    
    public void carregarConta(int numConta) {
        String sql = "SELECT * FROM contas WHERE numConta = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setInt(1, numConta);
            ResultSet rs = pstmt.executeQuery(); 
            
            if (rs.next()) {
                this.setNumConta(rs.getInt("numConta"));
                this.setDono(rs.getString("dono"));
                this.setTipo(rs.getString("tipo"));
                this.setSaldo(rs.getFloat("saldo"));
                this.setStatus(rs.getBoolean("status"));
                System.out.println("Conta carregada com sucesso.");
            } 
            else {
                System.out.println("Conta não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void AbrirConta(String t) {
        this.setTipo(t);
        this.setStatus(true);
    
        if (t.equals("CC")) {
            this.setSaldo(50);
        } 
        else if (t.equals("CP")) {
            this.setSaldo(150);
        }
        
        String sql = "INSERT INTO contas(numConta, dono, tipo, saldo, status) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.getNumConta());
            pstmt.setString(2, this.getDono());  
            pstmt.setString(3, this.getTipo());
            pstmt.setFloat(4, this.getSaldo());
            pstmt.setBoolean(5, this.getStatus());
            pstmt.executeUpdate();
            System.out.println("Conta aberta e salva no banco de dados.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void FecharConta() {
        if (this.getSaldo() > 0) {
            System.out.println("ERRO: ESSA CONTA AINDA TEM DINHEIRO!");
        } else if (this.getSaldo() < 0) {
            System.out.println("ERRO: VOCÊ ESTÁ DEVENDO DINHEIRO!"); 
        } else {
            System.out.println("FECHAMENTO EFETUADO COM SUCESSO!");
            this.setStatus(false);
        }
    }

    public void Sacar(float v) {
        if (this.getStatus() && this.getSaldo() >= v) {
            this.saldo -= v;
            
            String sql = "UPDATE contas SET saldo = ? WHERE numConta = ?";
            try (Connection conn = Database.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setFloat(1, this.getSaldo());
                pstmt.setInt(2, this.getNumConta());
                pstmt.executeUpdate();
                System.out.println("Saque realizado com sucesso e atualizado no banco.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } 
        else if (this.getSaldo() < v) {
            System.out.println("ERRO: SALDO INSUFICIENTE!");
        } 
        else if (!this.getStatus()) {
            System.out.println("ERRO: IMPOSSÍVEL SACAR!");
        }
    }    

    public void Depositar(float v) {
        if (this.getStatus()) {
            this.saldo += v;
            
            String sql = "UPDATE contas SET saldo = ? WHERE numConta = ?";
            try (Connection conn = Database.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setFloat(1, this.getSaldo());
                pstmt.setInt(2, this.getNumConta());
                pstmt.executeUpdate();
                System.out.println("Depósito realizado com sucesso e atualizado no banco.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } 
        else {
            System.out.println("ERRO: IMPOSSÍVEL DEPOSITAR");
        }
    }    

    public void PagarMensal() {
        int v = 0;
        if (this.getTipo().equals("CC")) {
            v = 12;
        } else if (this.getTipo().equals("CP")) {
            v = 20;
        }

        if (this.getStatus()) {
            if (this.getSaldo() > v) {
                this.setSaldo(this.getSaldo() - v);
                System.out.println("MENSALIDADE PAGA COM SUCESSO!");
            } 
            else {
                System.out.println("ERRO: SALDO INSUFICIENTE!");
            }
        } 
        else {
            System.out.println("ERRO: IMPOSSÍVEL PAGAR!");
        }
    }
    
    public void estadoAtual() {
        System.out.println("========================================");
        System.out.println("DONO: " + this.getDono());
        System.out.println("CONTA: " + this.getNumConta());
        System.out.println("TIPO: " + this.getTipo());
        System.out.println("SALDO: " + this.getSaldo());
        System.out.println("STATUS: " + this.getStatus());
        System.out.println("========================================");
    }
}

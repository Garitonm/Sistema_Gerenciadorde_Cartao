package model.entity;

import java.util.Random;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.dao.ConectaBD;
import model.dao.Transacao;

public class CartaoCredito {
	private int idtitular;
    private double limiteCredito = 2000.0;
    private String nomeTitular;
    private String cpf;
    private long numero;
    private String dataValidade;
    private double limiteDisponivel;
    private double fatura;
    private List<Transacao> transacoes;

    public CartaoCredito(String nomeTitular, String cpf) {
        this.nomeTitular = nomeTitular;
        this.cpf = cpf;
        this.numero = gerarNumeroCartao();
        this.dataValidade = gerarDataValidade();
        this.limiteDisponivel = limiteCredito;
        this.transacoes = new ArrayList<>();
        this.fatura = 0.0;
        this.idtitular = idtitular;
    }

    public CartaoCredito() {
    	this.numero = gerarNumeroCartao();
        this.dataValidade = gerarDataValidade();
        this.limiteDisponivel = limiteCredito;
    }
    public CartaoCredito(int idtitular) {
    	this.numero = gerarNumeroCartao();
        this.dataValidade = gerarDataValidade();
        this.limiteDisponivel = limiteCredito;
        this.idtitular = idtitular;
    }
    
    public CartaoCredito(int idtitular, long numero, String dataValidade, double limiteDisponivel) {
    	this.numero = gerarNumeroCartao();
        this.dataValidade = gerarDataValidade();
        this.limiteDisponivel = limiteCredito;
        this.idtitular = idtitular;
    }
    private long gerarNumeroCartao() {
    	Random random = new Random();
        return 1000000000000L + random.nextInt(900000000);
    }

	private String gerarDataValidade() {
    	  Random random = new Random();
          int mes = random.nextInt(12) + 1;
          int ano = 2024 + random.nextInt(5);
          return String.format("%02d/%d", mes, ano);
    }

    public long getNumero() {
        return numero;
    }
    public double getLimiteCredito() {
        return limiteCredito;
    }
    public String getDataValidade() {
        return dataValidade;
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }
    
    public int getIdtitular() {
		return idtitular;
	}

	public void setIdtitular(int idtitular) {
		this.idtitular = idtitular;
	}

    public boolean realizarTransacao(int cartao, double valor, String estabelecimento) {
        if (valor <= limiteDisponivel) {
            limiteDisponivel -= valor;
            fatura += valor;
            transacoes.add(new Transacao(cartao, valor, estabelecimento));
            return true;
        } else {
            System.out.println("Limite de crédito excedido.");
            return false;
        }
    }

    public void efetuarPagamentoFatura(double valor) {
        if (valor <= getFatura()) {
            limiteDisponivel += valor;
            System.out.println("Pagamento realizado com sucesso.");
        } else {
            System.out.println("Valor de pagamento incorreto.");
        }
    }

    public double getFatura() {
        return fatura;
    }/*
    public void consultarFatura1() {
    	ConectaBD con = new ConectaBD();
    	
    	String sql = "SELECT valor FROM transacao";
        try{
            PreparedStatement pst = con.getConexao().prepareStatement(sql);

            pst.execute();
            System.out.println("foi ");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    	
    	
        System.out.println("____________ Fatura______________");
        System.out.println("Valor total da fatura: R$" + fatura);
        System.out.println("Transações realizadas:");

        if (transacoes == null) {
            System.out.println("Nenhuma transação encontrada.");
        } else {
            for (Transacao transacao : transacoes) {
                System.out.println("Estabelecimento: " + transacao.getEstabelecimento());
                System.out.println("Valor: R$" + transacao.getValor());
                System.out.println("_______________________");
            }
        }
    }*/
    public List<Transacao> consultarFatura1(){
        ConectaBD con = new ConectaBD();
        String sql = "SELECT estabelecimento, valor FROM transacao";
        
       
        List<Transacao> lista = new LinkedList<Transacao>();
        try {
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
            	Transacao transacao = new Transacao(); 
            	String estabelecimento = rs.getString("estabelecimento");
                double valor = rs.getDouble("valor");
                
                transacao.setValor(valor);
                transacao.setEstabelecimento(estabelecimento);
                lista.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    public List<Transacao> consultarFatura(){
        ConectaBD con = new ConectaBD();
        String sql = "SELECT SUM(valor)  FROM transacao";
        
       
        List<Transacao> lista = new LinkedList<Transacao>();
        try {
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
            	Transacao transacao = new Transacao(); 
                double valor = rs.getDouble("SUM(valor)");
                
                transacao.setValor(valor);
                lista.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    public List<Transacao> consultarSaldo(){
        ConectaBD con = new ConectaBD();
        String sql = "SELECT limite FROM cartaocredito INNER JOIN transacao ON valor ";
        List<Transacao> lista = new LinkedList<Transacao>();
        try {
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
            	Transacao transacao = new Transacao(); 
                double valor = rs.getDouble("valor");
                
                transacao.setValor(valor);
                lista.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
}

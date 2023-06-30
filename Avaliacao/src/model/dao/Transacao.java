package model.dao;

import model.entity.CartaoAdicional;
import model.entity.CartaoCredito;

public class Transacao {
	private int cartao;
    private double valor;
    private String estabelecimento;

    public Transacao(int cartao, double valor, String estabelecimento) {
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.cartao = cartao;
    }
    public Transacao() {
    	
    	
    }
    
public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
public void setCartao(int cartao) {
		this.cartao = cartao;
	}
public int getCartao(){
	return cartao;
}
    
    public void setValor(double valor) {
	this.valor = valor;
}
	public double getValor() {
        return valor;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }
}
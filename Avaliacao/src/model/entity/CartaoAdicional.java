package model.entity;

import model.entity.*;

public class CartaoAdicional extends CartaoCredito {
    private CartaoCredito cartaoPrincipal;

    public CartaoAdicional(String nomeTitular, String cpf, CartaoCredito cartaoPrincipal) {
        super(nomeTitular, cpf);
        this.cartaoPrincipal = cartaoPrincipal;
    }

    public CartaoAdicional(int idtitular, long numero,String dataValidade, double limite) {
        this.cartaoPrincipal = cartaoPrincipal;
    }

    @Override
    public boolean realizarTransacao(int cartao, double valor, String estabelecimento) {
        boolean transacaoRealizada = super.realizarTransacao(cartao, valor, estabelecimento);
        if (transacaoRealizada) {
            cartaoPrincipal.realizarTransacao(cartao, valor, estabelecimento);
        }
        return transacaoRealizada;
    }
}

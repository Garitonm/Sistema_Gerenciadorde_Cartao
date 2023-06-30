package model;


class CartaoCreditoAdicional extends CartaoCredito {
    private CartaoCredito cartaoPrincipal;

    public CartaoCreditoAdicional(String nomeTitular, String documentoTitular, CartaoCredito cartaoPrincipal) {
        super(nomeTitular, documentoTitular);
        this.cartaoPrincipal = cartaoPrincipal;
    }

    @Override
    public boolean realizarTransacao(double valor, String estabelecimento) {
        boolean transacaoRealizada = super.realizarTransacao(valor, estabelecimento);
        if (transacaoRealizada) {
            cartaoPrincipal.realizarTransacao(valor, estabelecimento);
        }
        return transacaoRealizada;
    }
}
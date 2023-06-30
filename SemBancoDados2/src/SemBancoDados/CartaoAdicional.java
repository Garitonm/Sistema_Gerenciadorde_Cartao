package SemBancoDados;


public class CartaoAdicional extends CartaoCredito {
    private CartaoCredito cartaoPrincipal;
    private String nomeDependente;
    private String nomeTitular;
    private String cpf;
    
    public CartaoAdicional(String nomeDependente, String nomeTitular, String cpf, CartaoCredito cartaoPrincipal) {
    	super(nomeTitular, cpf);
        this.nomeDependente = nomeDependente;
        this.nomeTitular = nomeTitular;
        this.cpf = cpf;
        this.cartaoPrincipal = cartaoPrincipal;
    }
    public CartaoAdicional(int idTitular) {
    	super(idTitular);
    	this.cartaoPrincipal = cartaoPrincipal;
    }
    public String getNomeDependente(){
    	return nomeDependente;
    }
    /*public CartaoAdicional(String nomeTitular, String cpf, CartaoCredito cartaoPrincipal) {
        super(nomeTitular, cpf);
        this.cartaoPrincipal = cartaoPrincipal;
    }*/

    @Override
    public boolean realizarTransacao(double valor, String estabelecimento) {
        boolean transacaoRealizada = super.realizarTransacao(valor, estabelecimento);
        if (transacaoRealizada) {
            cartaoPrincipal.realizarTransacao(valor, estabelecimento);
        }
        return transacaoRealizada;
    }
}

package SemBancoDados;


class Titular {
    private static int idCounter = 1;
    private int id;
    private String nomeTitular;
    private String cpf;
    private CartaoCredito cartaoPrincipal;
    private CartaoAdicional cartaoAdicional;

    public Titular(String nomeTitular, String cpf) {
        this.id = idCounter++;
        this.nomeTitular = nomeTitular;
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getCpf() {
        return cpf;
    }

    public CartaoCredito getCartaoPrincipal() {
        return cartaoPrincipal;
    }


    public void emitirCartaoPrincipal() {
        cartaoPrincipal = new CartaoCredito(nomeTitular, cpf);
    }

    public CartaoAdicional getCartaoAdicional() {
        return cartaoAdicional;
    }

    public void emitirCartaoAdicional(String nomeDependente) {
        cartaoAdicional = new CartaoAdicional(nomeDependente, nomeTitular, cpf, cartaoPrincipal);
    }
}
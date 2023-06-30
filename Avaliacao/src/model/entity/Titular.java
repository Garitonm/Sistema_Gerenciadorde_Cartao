package model.entity;

public class Titular {
    private static int idCounter = 1;
    private int id;
    private String nomeTitular;
    private String cpf;
    private CartaoCredito cartaoPrincipal;
    private CartaoAdicional cartaoAdicional;

    public Titular(int id, String nomeTitular, String cpf) {
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

    public CartaoAdicional getCartaoAdicional() {
        return cartaoAdicional;
    }

    public void emitirCartaoPrincipal() {
        cartaoPrincipal = new CartaoCredito(nomeTitular, cpf);
    }

    public void emitirCartaoAdicional() {
        cartaoAdicional = new CartaoAdicional(nomeTitular, cpf, cartaoPrincipal);
    }
}

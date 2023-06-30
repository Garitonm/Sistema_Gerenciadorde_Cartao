package model;


import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class CartaoCredito {
    private double limiteCredito = 2000.0;
    private String nomeTitular;
    private String documentoTitular;
    private long numero;
    private String dataValidade;
    private double limiteDisponivel;
    private List<Transacao> transacoes;
    private double fatura;

    public CartaoCredito(String nomeTitular, String documentoTitular) {
        this.nomeTitular = nomeTitular;
        this.documentoTitular = documentoTitular;
        this.numero = gerarNumeroCartao();
        this.dataValidade = gerarDataValidade();
        this.limiteDisponivel = limiteCredito;
        this.transacoes = new ArrayList<>();
        fatura = 0;
        
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

    public String getDataValidade() {
        return dataValidade;
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public boolean realizarTransacao(double valor, String estabelecimento) {
        if (valor <= limiteDisponivel) {
            limiteDisponivel -= valor;
            transacoes.add(new Transacao(valor, estabelecimento));
            return true;
        } else {
            System.out.println("Limite de crédito excedido.");
            return false;
        }
    }
    public double getFatura() {
        return fatura;
    }
    public void efetuarPagamentoFatura(double valor) {
        if (valor <= getFatura()) {
            limiteDisponivel += valor;
            System.out.println("Pagamento realizado com sucesso.");
        } else {
            System.out.println("Valor de pagamento incorreto.");
        }
    }
    public void consultarFatura() {
        System.out.println("----- Fatura -----");
        System.out.println("Valor total da fatura: R$" + fatura);
        System.out.println("Transações realizadas:");

        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
        } else {
            for (Transacao transacao : transacoes) {
                System.out.println("Estabelecimento: " + transacao.getEstabelecimento());
                System.out.println("Valor: R$" + transacao.getValor());
                System.out.println("-----");
            }
        }
    }

}

package SemBancoDados;

class Transacao {
    private double valor;
    private String estabelecimento;

    public Transacao(double valor, String estabelecimento) {
        this.valor = valor;
        this.estabelecimento = estabelecimento;
    }

    public double getValor() {
        return valor;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }
}
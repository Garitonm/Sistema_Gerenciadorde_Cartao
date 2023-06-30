package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    private static List<Titular> titulares;

    public static void main(String[] args) {
        titulares = new ArrayList<>();

        exibirMenu();
    }

    private static void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- Menu -----");
            System.out.println("1. Cadastrar Titular");
            System.out.println("2. Emitir Cartão");
            System.out.println("3. Emitir Cartão Adicional");
            System.out.println("4. Realizar Transação");
            System.out.println("5. Efetuar Pagamento da Fatura do Cartão");
            System.out.println("6. Consultar Saldo Disponível");
            System.out.println("7. Consultar Fatura");
            System.out.println("8. Consultar Titulares e Cartões Cadastrados por ID");
            System.out.println("0. Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarTitular();
                    break;
                case 2:
                    emitirCartao();
                    break;
                case 3:
                    emitirCartaoAdicional();
                    break;
                case 4:
                    realizarTransacao();
                    break;
                case 5:
                    efetuarPagamentoFatura();
                    break;
                case 6:
                    consultarSaldoDisponivel();
                    break;
                case 7:
                    consultarFatura();
                    break;
                case 8:
                    consultarTitularesECartoes();
                    break;
                case 0:
                    System.out.println("Programa encerrado.");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void cadastrarTitular() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome do Titular: ");
        String nome = scanner.nextLine();

        System.out.print("Documento do Titular: ");
        String documento = scanner.nextLine();

        Titular titular = new Titular(nome, documento);
        titulares.add(titular);

        System.out.println("Titular cadastrado com sucesso. ID: " + titular.getId());
    }

    private static void emitirCartao() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Titular: ");
        int idTitular = scanner.nextInt();

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            titular.emitirCartaoPrincipal();
            System.out.println("Cartão emitido com sucesso para o Titular " + titular.getNomeTitular());
            System.out.println("Número do Cartão: " + titular.getCartaoPrincipal().getNumero());
            System.out.println("Data de Validade: " + titular.getCartaoPrincipal().getDataValidade());
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }

    private static void emitirCartaoAdicional() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Titular: ");
        int idTitular = scanner.nextInt();

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            titular.emitirCartaoAdicional();
            System.out.println("Cartão Adicional emitido com sucesso para o Titular " + titular.getNomeTitular());
            System.out.println("Número do Cartão Adicional: " + titular.getCartaoAdicional().getNumero());
            System.out.println("Data de Validade: " + titular.getCartaoAdicional().getDataValidade());
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }

    private static void realizarTransacao() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Titular: ");
        int idTitular = scanner.nextInt();

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            System.out.print("Valor da Transação: ");
            double valor = scanner.nextDouble();

            System.out.print("Estabelecimento: ");
            scanner.nextLine();
            String estabelecimento = scanner.nextLine();

            System.out.print("Tipo de Cartão (Principal / Adicional): ");
            String tipoCartao = scanner.nextLine();

            CartaoCredito cartao;
            if (tipoCartao.equalsIgnoreCase("principal")) {
                cartao = titular.getCartaoPrincipal();
            } else if (tipoCartao.equalsIgnoreCase("adicional")) {
                cartao = titular.getCartaoAdicional();
            } else {
                System.out.println("Tipo de Cartão inválido.");
                return;
            }

            boolean transacaoRealizada = cartao.realizarTransacao(valor, estabelecimento);
            if (transacaoRealizada) {
                System.out.println("Transação realizada com sucesso.");
            } else {
                System.out.println("Transação não realizada. Verifique o limite de crédito.");
            }
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }

    private static void efetuarPagamentoFatura() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Titular: ");
        int idTitular = scanner.nextInt();

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            double fatura = titular.getCartaoPrincipal().getFatura();
            System.out.println("Valor da Fatura: R$" + fatura);

            System.out.print("Valor do Pagamento: ");
            double valorPagamento = scanner.nextDouble();

            titular.getCartaoPrincipal().efetuarPagamentoFatura(valorPagamento);
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }

    private static void consultarSaldoDisponivel() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Titular: ");
        int idTitular = scanner.nextInt();

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            System.out.println("Saldo Disponível do Cartão Principal: R$" + titular.getCartaoPrincipal().getLimiteDisponivel());
            System.out.println("Saldo Disponível do Cartão Adicional: R$" + titular.getCartaoAdicional().getLimiteDisponivel());
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }

    private static void consultarFatura() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Titular: ");
        int idTitular = scanner.nextInt();

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            titular.getCartaoPrincipal().consultarFatura();
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }

    private static void consultarTitularesECartoes() {
        for (Titular titular : titulares) {
            System.out.println("\nID: " + titular.getId());
            System.out.println("Nome: " + titular.getNomeTitular());
            System.out.println("Documento: " + titular.getCpf());

            CartaoCredito cartaoPrincipal = titular.getCartaoPrincipal();
            if (cartaoPrincipal != null) {
                System.out.println("Cartão Principal:");
                System.out.println("Número: " + cartaoPrincipal.getNumero());
                System.out.println("Data de Validade: " + cartaoPrincipal.getDataValidade());
                System.out.println("Limite Disponível: R$" + cartaoPrincipal.getLimiteDisponivel());
            }

            CartaoCreditoAdicional cartaoAdicional = titular.getCartaoAdicional();
            if (cartaoAdicional != null) {
                System.out.println("Cartão Adicional:");
                System.out.println("Número: " + cartaoAdicional.getNumero());
                System.out.println("Data de Validade: " + cartaoAdicional.getDataValidade());
                System.out.println("Limite Disponível: R$" + cartaoAdicional.getLimiteDisponivel());
            }
        }
    }

    private static Titular buscarTitularPorId(int id) {
        for (Titular titular : titulares) {
            if (titular.getId() == id) {
                return titular;
            }
        }
        return null;
    }
}

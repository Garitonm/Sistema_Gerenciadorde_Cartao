package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.dao.CartaoDAO;
import model.dao.ConectaBD;
import model.dao.TitularDAO;
import model.dao.Transacao;
import model.entity.CartaoAdicional;
import model.entity.CartaoCredito;
import model.entity.Titular;

public class app {
	public static String leString(String msg) {
        String valor = JOptionPane.showInputDialog(null, msg);
        return valor;
    }

	public static int leInt(String msg) {
	    String valor = JOptionPane.showInputDialog(null, msg);
	    int numero = Integer.parseInt(valor);
	    return numero;
	}
	
	public static long leLong(String msg) {
	    String valor = JOptionPane.showInputDialog(null, msg);
	    long numero = Long.parseLong(valor);
	    return numero;
	}
	
	public static double leDouble(String msg) {
	    String valor = JOptionPane.showInputDialog(null, msg);
	    double numero = Double.parseDouble(valor);
	    return numero;
	}
	
	
	private static List<Titular> titulares;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        titulares = new ArrayList<>();
        
        do{
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar titular");
            System.out.println("2 - Gerar cartão de crédito");
            System.out.println("3 - Gerar cartão adicional");
            System.out.println("4 - Realizar transação");
            System.out.println("5 - Consultar Fatura");
            System.out.println("6 - Consultar Saldo Disponível");
            System.out.println("7 - Efetuar Pagamento da Fatura");
            System.out.println("8 - Sair");
            opcao = scanner.nextInt();
            
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
                	consultarFatura();
                    break;
                
                case 6:
                	consultarSaldoDisponivel();
                
                    break;
                case 7:
                	efetuarPagamentoFatura();
                
                    break;
            
		        case 8:
		            System.out.println("Você saiu do sistema!");
		            break;
		   
		     }
            
            System.out.println("");
        } while(opcao!=8);
        
        
        
    }
 
    private static void cadastrarTitular() {
    	int id = leInt("Digite o ID do Titular:");
    	String nome = leString("Digite nome do titular:");
    	String cpf = leString("Digite o cpf do titular: ");
    	Titular titular = new Titular(id,nome,cpf);
    	TitularDAO titularDAO = new TitularDAO();
    	titularDAO.inserir(titular);
    
        System.out.println("Titular "+titular.getNomeTitular()+" cadastrado com sucesso. ");
    }
    
    private static void emitirCartao() {
    	
    	int idTitular = leInt("Digite o ID do Titular que queira fazer um catão Principal");

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            titular.emitirCartaoPrincipal();
            
            long numero = titular.getCartaoPrincipal().getNumero();
            String validade = titular.getCartaoPrincipal().getDataValidade();
            double limitecredito = leDouble("Digite um limite para seu cartão de até 5 mil reais");
            
            
            if (limitecredito <= 5000.0) {
                CartaoCredito cartaocredito = new CartaoCredito(idTitular, numero, validade, limitecredito);
                CartaoDAO cartaoDAO = new CartaoDAO();
                cartaoDAO.inserir(cartaocredito);
                System.out.println("Cartão emitido com sucesso para o Titular " + titular.getNomeTitular());
                System.out.println("Número do Cartão: " + titular.getCartaoPrincipal().getNumero());
                System.out.println("Data de Validade: " + titular.getCartaoPrincipal().getDataValidade());
                
                System.out.println("Cartão de crédito cadastrado com sucesso.");
            } else {
                System.out.println("Limite de crédito inválido. O limite deve ser de até 5 mil reais.");
            }
            
            
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }


    private static void emitirCartaoAdicional() {
    	int idTitular = leInt("Digite o ID do Titular que queira fazer um cartão adicional");
    	
        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            titular.emitirCartaoAdicional();
            long num = titular.getCartaoAdicional().getNumero();
            String val = titular.getCartaoAdicional().getDataValidade();
            double limite = leDouble("Digite um limite para o cartao adicional: ");
            		
            CartaoAdicional cartaoadicional = new CartaoAdicional(idTitular,num,val, limite);
            CartaoDAO cartaoDAO = new CartaoDAO();
            cartaoDAO.inserircartaoadicional(cartaoadicional);
            
            
            System.out.println("Cartão Adicional emitido com sucesso para o Titular " + titular.getNomeTitular());
            System.out.println("Número do Cartão Adicional: " + titular.getCartaoAdicional().getNumero());
            System.out.println("Data de Validade: " + titular.getCartaoAdicional().getDataValidade());
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }
   
    private static Titular buscarTitularPorId(int id) {
    	ConectaBD con = new ConectaBD();
    	
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try {
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
            	int ID = rs.getInt("id");
                String nomeTitular = rs.getString("nome");
                String cpf = rs.getString("cpf");

                return new Titular(ID,nomeTitular, cpf);
            } else {
                return null; 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        /*for (Titular titular : titulares) {
            if (titular.getId() == id) {
                return titular;
            }
        }
        return null;*/
    }
    private static void realizarTransacao() {
        Scanner scanner = new Scanner(System.in);
        int idTitular = leInt("ID do Titular: ");

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            System.out.println("Escolha o cartão para a transação:");
            System.out.println("1. Cartão Principal");
            System.out.println("2. Cartão Adicional");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();

            if (opcao == 1) {
                realizarTransacaoCartaoPrincipal(titular);
            } else if (opcao == 2) {
                realizarTransacaoCartaoAdicional(titular);
            } else {
                System.out.println("Opção inválida. Transação cancelada.");
            }
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }
    private static void realizarTransacaoCartaoPrincipal(Titular titular) {
    	int idTitular = leInt ("Qual o id do principal");
    	Titular titularbusc = buscarTitularPorId(idTitular);

    	CartaoCredito cartaoPrincipal = titular.getCartaoPrincipal();
        if (titularbusc != null) {
           
        	int cartao = leInt("qual o id do cartao: ");
            double valor = leDouble("Valor da Transação: ");
            String estabelecimento = leString("estabelecimento da Compra: ");
            
            Transacao transacao = new Transacao(cartao, valor, estabelecimento);
            CartaoDAO cartaoDAO = new CartaoDAO();            
            if (cartaoDAO.inserecompra(transacao)) {
                System.out.println("Transação realizada com sucesso no Cartão Principal do titular " + titular.getNomeTitular());
            } else {
                System.out.println("Não foi possível realizar a transação no Cartão Principal.");
            }
        } else {
            System.out.println("O titular não possui um Cartão Principal.");
        }
    }
    private static void realizarTransacaoCartaoAdicional(Titular titular) {
    	int idTitular = leInt ("Qual o id do principal");
    	Titular titularbusc = buscarTitularPorId(idTitular);
    	
        CartaoAdicional cartaoAdicional = titular.getCartaoAdicional();
        if (titularbusc != null) {
        	int cartao = leInt("qual o id do cartao: ");
            double valor = leDouble("Valor da Transação: ");
            String estabelecimento = leString("estabelecimento da Compra: ");
            
            Transacao transacao = new Transacao(cartao, valor, estabelecimento);
            CartaoDAO cartaoDAO = new CartaoDAO();            
            if (cartaoDAO.inserecompra(transacao)) {
                System.out.println("Transação realizada com sucesso no Cartão Principal do titular " + titular.getNomeTitular());
            } else {
                System.out.println("Não foi possível realizar a transação no Cartão Principal.");
            }
        } else {
            System.out.println("O titular não possui um Cartão Principal.");
        }
    }
    private static void consultarFatura() {

        int idTitular = leInt("ID do Titular: ");

        //int id = Integer.parseInt(idTitular);
       // CartaoCredito card = new CartaoCredito();
       // Transacao t = card.consultar(idTitular);

        
        List<Transacao> registros = new CartaoCredito().consultarFatura();
        if (!registros.isEmpty()){
            String saida = "";
            saida += "fatura: ";
            for (int i = 0; i < registros.size(); i++) {
                Transacao t = registros.get(i);
                saida += t.getValor()+"\t";
                               
            }
            JOptionPane.showMessageDialog(null, new JTextArea(saida));
        }else{
            System.out.println("Nao tem registros");
        }
    
        List<Transacao> registros1 = new CartaoCredito().consultarFatura1();
        if (!registros1.isEmpty()){
            String saida = "";
            saida += "valores: ";
            for (int i = 0; i < registros1.size(); i++) {
                Transacao t1 = registros1.get(i);
                saida+= t1.getEstabelecimento()+"\t";
                saida += t1.getValor()+"\t";
                               
            }
            JOptionPane.showMessageDialog(null, new JTextArea(saida));
        }else{
            System.out.println("Nao tem registros");
        }
    
        
        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            System.out.println("___________Fatura___________");
            System.out.println("Titular: " + titular.getNomeTitular()+'\n');
            
            CartaoCredito cartaocredito = new CartaoCredito();     
            if (titular != null) {
                System.out.println("Cartão Principal");
                cartaocredito.consultarFatura();
                System.out.println("");
            }

           /* CartaoAdicional cartaoAdicional = titular.getCartaoAdicional();
            if (titular != null) {
                System.out.println("Cartão Adicional");
                cartaoAdicional.consultarFatura1();
                System.out.println("");
            }
        } */ else {
        		System.out.println("Titular não encontrado. Verifique o ID informado.");
        
    }
        }
    }
    

    private static void consultarSaldoDisponivel() {
        int idTitular = leInt("ID do Titular: ");
        CartaoCredito cartao = new CartaoCredito(idTitular);
        

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            System.out.println("___________Saldo Disponível___________"+'\n');
            System.out.println("Titular: " + titular.getNomeTitular()+'\n');
            System.out.println("");
            
            List<Transacao> registros = new CartaoCredito().consultarSaldo();
            if (!registros.isEmpty()){
                String saida = "";
                saida += "valores";
                for (int i = 0; i < registros.size(); i++) {
                    Transacao t = registros.get(i);
                    saida += t.getValor()+"\t";
                                   
                }
                JOptionPane.showMessageDialog(null, new JTextArea(saida));
            }else{
                System.out.println("Nao tem registros");
            }
        

            CartaoCredito cartaoPrincipal = titular.getCartaoPrincipal();
            if (cartaoPrincipal != null) {
                System.out.println("Cartão Principal");
                System.out.println("Limite de Crédito: R$" + cartao.getLimiteCredito()+'\n');
                System.out.println("Limite Disponível: R$" + cartao.getLimiteDisponivel()+'\n');
                System.out.println("");
            }

            CartaoAdicional cartaoAdicional = titular.getCartaoAdicional();
            if (cartaoAdicional != null) {
                System.out.println("Cartão Adicional");
                System.out.println("Limite de Crédito: R$" + cartaoAdicional.getLimiteCredito()+'\n');
                System.out.println("Limite Disponível: R$" + cartaoAdicional.getLimiteDisponivel()+'\n');
                System.out.println("");
            }
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }
    private static void efetuarPagamentoFatura() {
        int idTitular = leInt("ID do Titular: ");

        Titular titular = buscarTitularPorId(idTitular);
        if (titular != null) {
            

            System.out.println("Valor total da fatura: R$" );
            List<Transacao> registros = new CartaoCredito().consultarFatura();
            
            String saida = "";
            saida += "fatura: ";
            for (int i = 0; i < registros.size(); i++) {
                Transacao t = registros.get(i);
                saida += t.getValor()+"\t";
                               
            
            System.out.println("fatura: "+ saida);;
        }

            double valorPagamento = leDouble("Valor do Pagamento: ");
            
    
            if (valorPagamento >= 0 ) {
                
                if (titular.getCartaoAdicional() != null) {
                    titular.getCartaoAdicional().efetuarPagamentoFatura(valorPagamento);
                }
                System.out.println("Pagamento efetuado com sucesso.");
            } else {
                System.out.println("Valor de pagamento incorreto. Verifique o valor da fatura.");
            }
        } else {
            System.out.println("Titular não encontrado. Verifique o ID informado.");
        }
    }
/*
    public static void calcularFaturaTotal() {
    	int idTitular = leInt("ID do Titular: ");

        
}*/
}
  


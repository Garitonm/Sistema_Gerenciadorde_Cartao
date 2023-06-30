package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.entity.CartaoAdicional;
import model.entity.CartaoCredito;

public class CartaoDAO {
	public void inserir(CartaoCredito cartaocredito){
        ConectaBD con = new ConectaBD();
        String sql = "INSERT INTO cartaocredito (idpessoa, numero, dataValidade, limite) VALUES (?,?,?,?)";
        try{
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            pst.setInt(1, cartaocredito.getIdtitular());
            pst.setLong(2, cartaocredito.getNumero());
            pst.setString(3, cartaocredito.getDataValidade());
            pst.setDouble(4, cartaocredito.getLimiteCredito());
            pst.execute();
            System.out.println("Cartão de credito do "+ cartaocredito.getIdtitular() +" inserido");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
	
	public void inserircartaoadicional(CartaoAdicional cartaoadicional){
        ConectaBD con = new ConectaBD();
        String sql = "INSERT INTO cartaoadicional (idpessoa, numero, dataValidadead,limiteprin) VALUES (?,?,?,?)";
        try{
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            pst.setInt(1, cartaoadicional.getIdtitular());
            pst.setLong(2, cartaoadicional.getNumero());
            pst.setString(3, cartaoadicional.getDataValidade());
            pst.setDouble(4, cartaoadicional.getLimiteCredito());
            pst.execute();
            System.out.println("Cartão de credito do "+ cartaoadicional.getIdtitular() +" inserido");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
	public boolean inserecompra(Transacao transacao){
        ConectaBD con = new ConectaBD();
        String sql = "INSERT INTO transacao (cartao, valor, estabelecimento) VALUES (?,?,?)";
        try{
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            pst.setInt(1, transacao.getCartao());
            pst.setDouble(2, transacao.getValor());
            pst.setString(3, transacao.getEstabelecimento());
            pst.execute();
            return true;

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
		return false;
    }
}


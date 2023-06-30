package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.entity.Titular;

public class TitularDAO {
	public void inserir(Titular titular){
        ConectaBD con = new ConectaBD();
        String sql = "INSERT INTO pessoa (id,nome, cpf) VALUES (?,?,?)";
        try{
            PreparedStatement pst = con.getConexao().prepareStatement(sql);
            pst.setInt(1, titular.getId());
            pst.setString(2, titular.getNomeTitular());
            pst.setString(3, titular.getCpf());
            pst.execute();
            System.out.println(titular.getNomeTitular() + " inserido");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

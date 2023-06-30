package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBD {
    private Connection conexao;

    // construtor
    public ConectaBD(){
        String url = "jdbc:mariadb://localhost:3307/teste";
        String user = "root";
        String pwd = "root";
        try{
            conexao = DriverManager.getConnection(url, user, pwd);
            System.out.println("---------");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que retorna a conexao com o BD
     * @return um objeto do tipo Connection
     */
    public Connection getConexao(){
        return conexao;
    }
}

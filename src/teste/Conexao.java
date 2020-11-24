
package teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","");
        }catch(SQLException e){
            System.out.println("Erro");
        } 
        return conn;
    }
}

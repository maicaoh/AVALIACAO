/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael
 */
public class ValidarAeronaves {
    PreparedStatement conn = null;
    
    public boolean validaVoo(String aviao,String origem,String destino,String duracao,int dia,int mes,int ano,int hora) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH");
        Date inicio = sdf.parse(dia+"/"+mes+"/"+ano+" "+hora);
        int horaAcrecimo = hora+Integer.parseInt(duracao);
        Date fim = sdf.parse(dia+"/"+mes+"/"+ano+" "+horaAcrecimo);
        
        try {
            conn = Conexao.getConexao().prepareStatement("select id from aviao where nome = ?");
            conn.setString(1, aviao); 
            ResultSet rs = conn.executeQuery();
            int idAviao = 0;
            while(rs.next()){
                idAviao =  rs.getInt("id");
            }
            conn = null;
            rs = null;
            conn = Conexao.getConexao().prepareStatement("select id from cidade where nome = ?");
            conn.setString(1,origem);
            rs = conn.executeQuery();
            int idCidadeDeOrigem = 0;
            while(rs.next())
                idCidadeDeOrigem =  rs.getInt("id");
            conn = null;
            rs = null;
            conn = Conexao.getConexao().prepareStatement("select id from cidade where nome = ?");
            conn.setString(1,destino);
            rs = conn.executeQuery();
            int idCidadeDeDestino = 0;
            while(rs.next())
                idCidadeDeDestino =  rs.getInt("id");

            
            conn = null;
            rs=null;

            PreparedStatement stmt = Conexao.getConexao().prepareStatement("select * from voo where idaviao = ? and dia = ? and mes = ? and ano= ? and horario= ? and idCidadedeOrigem = ?" );
            stmt.setInt(1, idAviao);    
            stmt.setInt(2, dia);    
            stmt.setInt(3, mes);
            stmt.setInt(4, ano);
            stmt.setInt(5, hora);
            stmt.setInt(6, idCidadeDeOrigem);
            stmt.execute();
                           
            rs = stmt.executeQuery();
          

            if(!rs.next()){
                conn = null;
                rs=null;  
                conn = Conexao.getConexao().prepareStatement("select ano,mes,dia,duracao,horario from voo where idaviao = " +idAviao);
                rs = conn.executeQuery(); 
                Date inicialx,finalx;
                int horaAux;
                while(rs.next()){
                    inicialx =   sdf.parse(rs.getInt("dia")+"/"+rs.getInt("mes")+"/"+rs.getInt("ano")+" "+rs.getInt("horario"));
                    horaAux = Integer.parseInt(rs.getString("duracao"))+(rs.getInt("horario"));
                    finalx = sdf.parse((rs.getInt("dia"))+"/"+rs.getInt("mes")+"/"+rs.getInt("ano")+" "+(rs.getInt("horario")+horaAux));
                    if(!(finalx.before(inicio) ||inicialx.after(fim)) ){
                        return false;
                    }
                }
              
                try{  
                    stmt = Conexao.getConexao().prepareStatement("insert into voo(idAviao,idCidadedeOrigem,idCidadedeDestino,ano,mes,dia,horario,duracao) value (?,?,?,?,?,?,?,?)");
                    stmt.setInt(1, idAviao);
                    stmt.setInt(2, idCidadeDeOrigem);
                    stmt.setInt(3, idCidadeDeDestino);
                    stmt.setInt(4, ano);
                    stmt.setInt(5, mes);
                    stmt.setInt(6, dia);
                    stmt.setInt(7, hora);
                    stmt.setInt(8, Integer.parseInt(duracao));
                    stmt.execute();
                    return true;
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, e);
                }             
            }else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidarAeronaves.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}

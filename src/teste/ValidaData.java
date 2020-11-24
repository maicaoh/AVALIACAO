/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import sun.util.calendar.Gregorian;

public class ValidaData {
   
    
    public boolean data(int dia,int mes,int ano){
        if(dia>0 && dia<32 && mes>0 && mes<13 && ano>0 && ((mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12) || ((mes==4 || mes==6 || mes==9 || mes==11) && dia<=30) || (mes==2 &&(dia<=29 && ano%4==0 && (ano%100!=0 || ano%400==0))|| dia<=28))){
            return true;
        }else{
           return false;
        }
    }
    public boolean dataAdiantada(int dia,int mes,int ano,int hora) throws ParseException{
        GregorianCalendar calendar = new GregorianCalendar();
        int diaHoje = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mesHoje = calendar.get(GregorianCalendar.MONTH);
        int anoHoje = calendar.get(GregorianCalendar.YEAR);
        int horaHoje = calendar.get(GregorianCalendar.HOUR_OF_DAY);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDeEntrada = sdf.parse(dia+"/"+mes+"/"+ano);
        Date dataHoje = sdf.parse(diaHoje+"/"+(mesHoje+1)+"/"+anoHoje);
        
        if(dataHoje.compareTo(dataDeEntrada)== 1){
                return false;
        }else if(dataHoje.compareTo(dataDeEntrada)<0){
            return true;
        }else{
            if(horaHoje<hora)
                return true;
            else 
                return false;
        }
    }
}

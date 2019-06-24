/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.conversores;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Manoel Souza 
 */
@FacesConverter(value = "converterCalendar")
public class ConverteCalendar implements Serializable, Converter{

    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    
    //converte a data da visão para Calendar
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String stringData) {
        if(stringData!=null){
            try {
                Calendar dt = Calendar.getInstance();
                dt.setTime(formatoData.parse(stringData));
                return dt;
            } catch (ParseException ex) {
                return null;
            }
        } else{
            return null;
        }
    }

    //converte Calendar para String
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Calendar dt = (Calendar) o;
        return formatoData.format(dt.getTime());
    }
    
}

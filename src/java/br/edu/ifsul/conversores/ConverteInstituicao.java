/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.conversores;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Instituicao;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import javax.faces.convert.FacesConverter;

/**
 *
 * @author Manoel Souza 
 */
@FacesConverter(value = "converterInstituicao")
public class ConverteInstituicao implements Serializable, Converter{

    //converter a informação da disciplina (tela) para o controle
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.equalsIgnoreCase("Selecione um registro")){
            return null;
        } else{
            return EntityManagerUtil.getEntityManager().find(Instituicao.class, 
                    Integer.parseInt(string));
        }
    }

    //Busca as informações pelo controle e converte para mostrar na tela
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null)
            return  null;
        Instituicao c = (Instituicao) o;
        return c.getId().toString();
    }
    
}

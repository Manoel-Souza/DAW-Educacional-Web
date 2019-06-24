/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Especialidades;
import java.io.Serializable;

/**
 *
 * @author Manoel Souza 
 */
public class EspecialidadesDAO<TIPO> extends DAOGenerico<Especialidades> implements Serializable{

    public EspecialidadesDAO() {
        super();
        classePersistente = Especialidades.class;
    }
    
}

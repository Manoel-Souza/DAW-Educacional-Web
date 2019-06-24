/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Disciplina;
import java.io.Serializable;

/**
 *
 * @author Manoel Souza 
 */
public class DisciplinaDAO<TIPO>  extends DAOGenerico<Disciplina> implements Serializable{

    public DisciplinaDAO() {
        super();
        classePersistente = Disciplina.class;
    }
    
}

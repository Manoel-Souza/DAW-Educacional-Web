/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ProfessorDAO;
import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.modelo.Aluno;
import br.edu.ifsul.modelo.Professor;
import br.edu.ifsul.dao.EspecialidadesDAO;
import br.edu.ifsul.modelo.Especialidades;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Manoel Souza 
 */
@ManagedBean(name = "controleProfessor")
@SessionScoped
public class ControleProfessor implements Serializable{
    private ProfessorDAO<Professor> dao;
    private Professor obj;
    private AlunoDAO daoAluno;
    private EspecialidadesDAO daoEspecialidades;

    public ControleProfessor() {
        dao = new ProfessorDAO<>();
	daoAluno = new AlunoDAO();
	daoEspecialidades = new EspecialidadesDAO();
    }

    public ProfessorDAO<Professor> getDao() {
        return dao;
    }

    public void setDao(ProfessorDAO<Professor> dao) {
        this.dao = dao;
    }

    public Professor getObj() {
        return obj;
    }

    public void setObj(Professor obj) {
        this.obj = obj;
    }
    
    public AlunoDAO getDaoAluno() {
        return daoAluno;
    }

    public void setDaoAluno(AlunoDAO daoAluno) {
        this.daoAluno = daoAluno;
    }
    
    public EspecialidadesDAO getDaoEspecialidades() {
	return daoEspecialidades;
    }

    public void setDaoEspecialidades(EspecialidadesDAO daoEspecialidades) {
	this.daoEspecialidades = daoEspecialidades;
    }

    public String listar(){
        return "/privado/professor/listar?faces-redirect=true";
    }
    
    public String novo(){
        obj = new Professor();
        return "formulario?faces-redirect=true";
    }
    
    public String salvar(){
        boolean persistiu;
        if(obj.getId() == null){
            persistiu = dao.persist(obj);
        } else{
            persistiu = dao.merge(obj);
        }
        if(persistiu){
            Util.mensagemInformacao(dao.getMensagem());
            return "listar?faces-redirect=true";
        }else{
            Util.mensagemErro(dao.getMensagem());
            return "formulario?faces-redirect=true";
        }
    }
    public String cancelar(){
        return "listar?faces-redirect=true";
    }
    
    public String editar(Integer id){
        try {
            obj = dao.localizar(id);
            return "formulario?faces-redirect=true";
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));
            return "listar?faces-redirect=true";
        }
    }
    
    public void remover(Integer id){
        obj = dao.localizar(id);
        if(dao.remove(obj))
            Util.mensagemInformacao(dao.getMensagem());
        else
            Util.mensagemErro(dao.getMensagem());
    }
    
}

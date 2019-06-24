/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.NotaDAO;
import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.modelo.Aluno;
import br.edu.ifsul.modelo.Nota;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
  * @author Manoel Souza 
 */
@ManagedBean(name = "controleNota")
@SessionScoped
public class ControleNota implements Serializable{
    private NotaDAO<Nota> dao;
    private Nota obj;
    private AlunoDAO daoAluno;
    private DisciplinaDAO daoDisciplina;

    public ControleNota() {
        dao = new NotaDAO<>();
	daoAluno = new AlunoDAO();
	daoDisciplina = new DisciplinaDAO();
    }

    public NotaDAO<Nota> getDao() {
        return dao;
    }

    public void setDao(NotaDAO<Nota> dao) {
        this.dao = dao;
    }

    public Nota getObj() {
        return obj;
    }

    public void setObj(Nota obj) {
        this.obj = obj;
    }
    
    public AlunoDAO getDaoAluno() {
        return daoAluno;
    }

    public void setDaoAluno(AlunoDAO daoAluno) {
        this.daoAluno = daoAluno;
    }
    
    public DisciplinaDAO getDaoDisciplina() {
        return daoDisciplina;
    }

    public void setDaoDisciplina(DisciplinaDAO daoDisciplina) {
        this.daoDisciplina = daoDisciplina;
    }

    public String listar(){
        return "/privado/nota/listar?faces-redirect=true";
    }
    
    public String novo(){
        obj = new Nota();
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

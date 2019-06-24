/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.modelo.Aluno;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Manoel Souza 
 */
@ManagedBean(name = "controleMatricula")
//@SessionScoped
@ViewScoped
public class ControleMatricula implements Serializable{
    private DisciplinaDAO<Disciplina> dao;
    private Disciplina obj;
    private AlunoDAO<Aluno> daoAluno;
    private Aluno aluno;

    public ControleMatricula() {
        dao = new DisciplinaDAO<>();
	daoAluno = new AlunoDAO<>();
    }

    public DisciplinaDAO<Disciplina> getDao() {
        return dao;
    }

    public void setDao(DisciplinaDAO<Disciplina> dao) {
        this.dao = dao;
    }

    public Disciplina getObj() {
        return obj;
    }

    public void setObj(Disciplina obj) {
        this.obj = obj;
    }
    
     public Aluno getAluno() {
	return aluno;
    }

    public void setAluno(Aluno aluno) {
	this.aluno = aluno;
    }

    public AlunoDAO<Aluno> getDaoAluno() {
	return daoAluno;
    }

    public void setDaoAluno(AlunoDAO<Aluno> daoAluno) {
	this.daoAluno = daoAluno;
    }

    public String listar(){
        return "/privado/disciplina/listar?faces-redirect=true";
    }
    
    public String novo(){
        obj = new Disciplina();
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

    
//     public void adicionarMatricula(){
//        if(aluno != null){
//            if(!obj.getListaMatricula().contains(aluno)){
//                obj.adicionarAlunoDesejo(aluno);
//                Util.mensagemInformacao("Desejo adicionado com sucesso");
//            } else{
//                Util.mensagemErro("Aluno já existe na lista de Matricula");
//            }
//        }
//    }
//    
//    public void removerMatricula(int indice){
//         Object[] lista = obj.getListaMatricula().toArray();
//         Aluno p = (Aluno) lista[indice];
//         obj.getListaMatricula().remove(p);
//         Util.mensagemInformacao("Matricula removido com sucesso");
//    }
}

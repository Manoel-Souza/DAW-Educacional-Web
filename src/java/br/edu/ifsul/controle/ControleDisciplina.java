/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.dao.CursoDAO;
import br.edu.ifsul.modelo.Curso;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.modelo.Aluno;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Manoel Souza 
 */
@ManagedBean(name = "controleDisciplina")
//@SessionScoped
@ViewScoped
public class ControleDisciplina implements Serializable {

    private DisciplinaDAO<Disciplina> dao;
    private Disciplina obj;
    private CursoDAO daoCurso;
    private AlunoDAO<Aluno> daoAluno;
    private Aluno aluno;

    public ControleDisciplina() {
	dao = new DisciplinaDAO<>();
	daoCurso = new CursoDAO();
	daoAluno = new AlunoDAO<>();
    }

    public Disciplina getObj() {
	return obj;
    }

    public void setObj(Disciplina obj) {
	this.obj = obj;
    }

    public DisciplinaDAO<Disciplina> getDao() {
	return dao;
    }

    public void setDao(DisciplinaDAO<Disciplina> dao) {
	this.dao = dao;
    }

    public String listar() {
	return "/privado/disciplina/listarDis?faces-redirect=true";
    }

    public void novo() {
	obj = new Disciplina();
    }

    public void salvar() {
	boolean persistiu;
	if (obj.getId() == null) {
	    persistiu = dao.persist(obj);
	} else {
	    persistiu = dao.merge(obj);
	}
	if (persistiu) {
	    Util.mensagemInformacao(dao.getMensagem());

	} else {
	    Util.mensagemErro(dao.getMensagem());

	}
    }

    public String cancelar() {
	return "listar?faces-redirect=true";
    }

    public void editar(Integer id) {
	try {
	    obj = dao.localizar(id);

	} catch (Exception e) {
	    Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));

	}
    }

    public void remover(Integer id) {
	obj = dao.localizar(id);
	if (dao.remove(obj)) {
	    Util.mensagemInformacao(dao.getMensagem());
	} else {
	    Util.mensagemErro(dao.getMensagem());
	}
    }

    public CursoDAO<Curso> getDaoCurso() {
	return daoCurso;
    }

    public void setDaoCurso(CursoDAO<Curso> daoCurso) {
	this.daoCurso = daoCurso;
    }

    public AlunoDAO<Aluno> getDaoAluno() {
	return daoAluno;
    }

    public void setDaoAluno(AlunoDAO<Aluno> daoAluno) {
	this.daoAluno = daoAluno;
    }

    public Aluno getAluno() {
	return aluno;
    }

    public void setAluno(Aluno aluno) {
	this.aluno = aluno;
    }

    public void adicionarMatricula() {
	
	if (aluno != null) {
	    if (!obj.getListaMatriculas().contains(aluno)) {
		obj.adicionaAlunoMatricula(aluno);
		Util.mensagemInformacao("Desejo adicionado com sucesso");
	    } else {
		Util.mensagemErro("Aluno já existe na lista de Matricula");
	    }
	}
    }

    public void removerMatricula(int indice) {
	Object[] lista = obj.getListaMatriculas().toArray();
	Aluno p = (Aluno) lista[indice];
	obj.getListaMatriculas().remove(p);
	Util.mensagemInformacao("Matricula removido com sucesso");
    }
}

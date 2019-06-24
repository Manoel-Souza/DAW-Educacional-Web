/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.util.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * 
 * @author Manoel Souza 
 */
public class DAOGenerico<TIPO> {
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    protected EntityManager em;
    protected Class classePersistente;
    private String mensagem;
    protected String ordem = "id";
    protected String filtro = "";
    protected Integer maximoObjetos = 2;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos = 0;

    public DAOGenerico() {
	em = EntityManagerUtil.getEntityManager();
    }

    public List<TIPO> getListaObjetos() {
	String sql = "from "+classePersistente.getSimpleName();
	String where = "";
	//proteger contra SQL Injection
	filtro = filtro.replaceAll("[';-]", "");
	if (filtro.length()>0) {
	    if(ordem.equalsIgnoreCase("ID")) 
		try {
		    Integer.parseInt(filtro);
		    where += " where "+ordem+" = '"+filtro+"' ";
		} catch (Exception e) {
		}		
	    else{
		where += " where upper("+ordem+") like '"+filtro.toUpperCase()+"%' ";
	    }
	}
	sql += where;
	sql += " order by "+ordem;
	
	totalObjetos = em.createQuery(sql).getResultList().size();
	
	return em.createQuery(sql).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }

    public List<TIPO> getListaTodos() {
	String sql = "from "+classePersistente.getSimpleName();
	return em.createQuery(sql).getResultList();
    }

    public String getMensagem() {
	return mensagem;
    }

    public void setMensagem(String mensagem) {
	this.mensagem = mensagem;
    }
    
    public boolean persist(TIPO obj){
	try {
	    em.getTransaction().begin();
	    em.persist(obj);
	    em.getTransaction().commit();
	    mensagem = "Objeto persistido com sucesso";
	    return true;
	} catch (Exception e) {
	    rollback();
	    mensagem = "Erro ao persistir: "+Util.getMensagemErro(e);
	    return false;
	}
    }
    
    public void rollback(){
	if(!em.getTransaction().isActive())
	    em.getTransaction().begin();
	em.getTransaction().rollback();
    }
    
    public boolean merge(TIPO obj) {
	try {
	    em.getTransaction().begin();
	    em.merge(obj);//edita e atualiza
	    em.getTransaction().commit();
	    mensagem = "Objeto atualizar com sucesso";
	    return true;
	} catch (Exception e) {
	    rollback();
	    mensagem = "Erro ao atualizar: "+Util.getMensagemErro(e);
	    return false;
	}
    }
    
    public boolean remove(TIPO obj) {
	try {
	    em.getTransaction().begin();
	    em.remove(obj);
	    em.getTransaction().commit();
	    mensagem = "Objeto removido com sucesso";
	    return true;
	} catch (Exception e) {
	    rollback();
	    mensagem = "Erro ao remover: "+Util.getMensagemErro(e);
	    return false;
	}
    }
    
    public TIPO localizar(Integer id){
	TIPO obj = (TIPO) em.find(classePersistente, id);
	return obj;
    }

    public String getOrdem() {//sem capsular
	return ordem;
    }

    public void setOrdem(String ordem) {//sem capsular
	this.ordem = ordem;
    }

    public String getFiltro() {//sem capsular
	return filtro;
    }

    public void setFiltro(String filtro) {//sem capsular
	this.filtro = filtro;
    }

    public Integer getMaximoObjetos() {//sem capsular
	return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {//sem capsular
	this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {//sem capsular
	return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {//sem capsular
	this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {//sem capsular
	return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {//sem capsular
	this.totalObjetos = totalObjetos;
    }
    
    //metodos de navegação da paginação
    public void primeiro(){
	posicaoAtual = 0;
    }
    
    public void anterior(){
	posicaoAtual -= maximoObjetos;
	if (posicaoAtual < 0) { 
	    //seestiver na primeira pagina mantem.
	    posicaoAtual = 0;
	}
    }
    
    public void proximo(){
	if(posicaoAtual + maximoObjetos < totalObjetos){
	    posicaoAtual += maximoObjetos;
	}
    }
    
    public void ultimo(){
	int resto = totalObjetos % maximoObjetos;
	if(resto > 0){
	    posicaoAtual = totalObjetos - resto;
	}else{
	    posicaoAtual = totalObjetos - maximoObjetos;
	}
    }
    
    public String getMensagemNavegacao(){
	int ate = posicaoAtual + maximoObjetos;
	if (ate > totalObjetos) 
	    ate = totalObjetos;
	
	return "Listando de "+(posicaoAtual+1)+" até "+ate+" de "+totalObjetos+" registros";
	
    }
    
}

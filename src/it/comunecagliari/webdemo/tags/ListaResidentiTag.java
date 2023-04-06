package it.comunecagliari.webdemo.tags;

import it.comunecagliari.webdemo.system.services.residente.ResidenteManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ListaResidentiTag extends TagSupport {
	private String nomelista;
	private String nomemessaggio;

	public int doStartTag() throws JspException {
		ResidenteManager ls = new ResidenteManager();
		StringBuilder str = new StringBuilder("");
		try {
			str.append(ls.inizializzaListaResidenti(this.pageContext));
			this.pageContext.setAttribute(this.getNomelista(), ls);
			this.pageContext.setAttribute(this.getNomemessaggio(), str);
		} catch (Exception e) {
			throw new JspException(" ListaResidentiTag: " + e.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() {
		return EVAL_PAGE;
	}
	public String getNomelista() {
		return nomelista;
	}
	public void setNomelista(String nomelista) {
		this.nomelista = nomelista;
	}
	public String getNomemessaggio() {
		return nomemessaggio;
	}
	public void setNomemessaggio(String nomemessaggio) {
		this.nomemessaggio = nomemessaggio;
	}
}

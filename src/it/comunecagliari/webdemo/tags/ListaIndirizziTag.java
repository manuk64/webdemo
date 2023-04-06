package it.comunecagliari.webdemo.tags;

import it.comunecagliari.webdemo.system.ListaIndirizzi;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ListaIndirizziTag extends TagSupport {
	private String nomelista;
	private String nomemessaggio;

	public int doStartTag() throws JspException {
		ListaIndirizzi lsInd = new ListaIndirizzi();
		StringBuilder str = new StringBuilder("");
		try {
			str.append(lsInd.inizializzaListaIndirizzi(this.pageContext));
			this.pageContext.setAttribute(this.getNomelista(), lsInd);
			this.pageContext.setAttribute(this.getNomemessaggio(), str);
		} catch (Exception e) {
			throw new JspException(" ListaIndirizziTag: " + e.getMessage());
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

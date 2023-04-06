package it.comunecagliari.webdemo.tags;

import it.comunecagliari.webdemo.system.Famiglia;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SchedaFamigliaTag extends TagSupport {
	private String nomescheda;
	private String nomemessaggio;

	public int doStartTag() throws JspException {
		Famiglia famiglia =  new Famiglia();
		StringBuilder message = new StringBuilder("");
		try {
			message.append(famiglia.inizializzaFamiglia(this.pageContext));
			this.pageContext.setAttribute(this.getNomescheda(), famiglia);
			this.pageContext.setAttribute(this.getNomemessaggio(), message);
		} catch (Exception e) {
			throw new JspException("  " + e.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	public String getNomescheda() {
		return nomescheda;
	}
	public void setNomescheda(String nomescheda) {
		this.nomescheda = nomescheda;
	}
	public String getNomemessaggio() {
		return nomemessaggio;
	}
	public void setNomemessaggio(String nomemessaggio) {
		this.nomemessaggio = nomemessaggio;
	}
}

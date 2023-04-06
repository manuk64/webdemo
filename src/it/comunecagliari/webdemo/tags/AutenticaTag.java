package it.comunecagliari.webdemo.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import it.comunecagliari.webdemo.system.Autentica;

public class AutenticaTag extends TagSupport{
	private String nomelogin;
	private String nomemessaggio;

	public int doStartTag() throws JspException {
		Autentica autorizzazione =  new Autentica();
		StringBuilder str = new StringBuilder("");
		try {
			str.append(autorizzazione.checkAutorizzazione(this.pageContext));
			this.pageContext.getSession().setAttribute(this.getNomelogin(), autorizzazione);
			this.pageContext.getSession().setAttribute(this.getNomemessaggio(), str);
		}
		catch (Exception e) {
			throw new JspException(" AutenticaTag: " + e.getMessage());
		}
		return EVAL_BODY_INCLUDE;	
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	public String getNomelogin() {
		return nomelogin;
	}
	public void setNomelogin(String nomelogin) {
		this.nomelogin = nomelogin;
	}
	public String getNomemessaggio() {
		return nomemessaggio;
	}
	public void setNomemessaggio(String nomemessaggio) {
		this.nomemessaggio = nomemessaggio;
	}
}


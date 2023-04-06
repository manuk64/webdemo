package it.comunecagliari.webdemo.tags;

import it.comunecagliari.webdemo.system.services.residente.Residente;
import it.comunecagliari.webdemo.system.services.residente.ResidenteDAO;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SchedaIndividualeTag extends TagSupport {
	private String nomescheda;

	public int doStartTag() throws JspException {
		Residente residente =  new Residente(this.pageContext.getRequest().getParameter("codice"));
		try {
			ResidenteDAO.creaScheda(residente);
			this.pageContext.setAttribute(this.getNomescheda(), residente);
		} catch (Exception e) {
			throw new JspException( " SchedaIndividualeTag: " + e.getMessage());
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
}

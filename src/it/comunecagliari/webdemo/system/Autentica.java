package it.comunecagliari.webdemo.system;

import it.comunecagliari.webdemo.system.models.LdapConfig;
import it.comunecagliari.webdemo.system.services.group.GroupLdapManager;
import it.comunecagliari.webdemo.system.services.user.LdapUser;
import it.comunecagliari.webdemo.system.services.user.UserLdapManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

public class Autentica {
	private boolean _auth;
	
	public Autentica() {
		_auth = false;
	}
	
	public StringBuilder checkAutorizzazione(PageContext ctx){
		UserLdapManager userLDAP = new UserLdapManager();
		LdapConfig ldapConfig = (LdapConfig) getJNDIResource("java:comp/env/bean/LdapConfigFactory");
		userLDAP.setLdapConfig(ldapConfig);
		String login, password;
		LdapUser utenteLDAP = null;
		login = null;
		StringBuilder str = new StringBuilder();
		ServletRequest req = ctx.getRequest();
		if (null != req.getParameter("login"))				
			if (!(req.getParameter("password") == "") ) {
				login = req.getParameter("login");
				password = req.getParameter("password");
				try {
					utenteLDAP = userLDAP.getUser(login , password);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		if (null != utenteLDAP) {
			GroupLdapManager gestore = new GroupLdapManager();
			gestore.setLdapConfig(ldapConfig);
			boolean isUser = gestore.isUsersAuth(utenteLDAP.getUserName());
			if (isUser)
				this.setAuth(true);
			else {
				this.setAuth(false);
				str.append(utenteLDAP.getUserName() + " non risulta autorizzato!");
			}
		} else 
			if (null != login) {
				this.setAuth(false);
				str.append("Utente non riconosciuto!");
			}
		return str;
}
	
	protected Object getJNDIResource(String name){
		Object resource = null;
		try {
			Context ctx = new InitialContext();
			resource = ctx.lookup(name);
			return resource;
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return resource;
	}
	
	public boolean getAuth() {
		return _auth;
	}

	public void setAuth(boolean auth) {
		this._auth = auth;
	}
}
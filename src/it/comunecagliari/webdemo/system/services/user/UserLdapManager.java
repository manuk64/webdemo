/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of JAPS software.
* JAPS and its  source-code is  licensed under the  terms of the
* GNU General Public License  as published by  the Free Software
* Foundation (http://www.fsf.org/licensing/licenses/gpl.txt).
* 
* You may copy, adapt, and redistribute this file for commercial
* or non-commercial use.
* When copying,  adapting,  or redistributing  this document you
* are required to provide proper attribution  to AgileTec, using
* the following attribution line:
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package it.comunecagliari.webdemo.system.services.user;

import it.comunecagliari.webdemo.system.models.LdapConfig;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * Servizio di gestione degli utenti, estensione del manager 
 * degli utenti per la gestione degli utenti definiti nel db ldap.
 * @version 1.0
 * @author E.Santoboni
 */ 
public class UserLdapManager {
	
	public UserLdapManager() {
		this._userLdapDao = new UserLdapDAO();
	}
    
	/**
	 * Recupera un'user caricandolo da db. 
	 * In questo caso l'autenticazione viene effettuata fornendo invece che 
	 * la userId l'indirizzo mail. Se eMail e password non
	 * corrispondono ad un utente, restituisce null.
	 * @param userName L'indirizzo mail dell'utente da restituire.
	 * @param password La password dell'utente da restituire.
	 * @return un oggetto user con corrispondenti userName e password
     * @throws ApsSystemException 
     * @throws ApsSystemException in caso di errore nell'accesso al db.
	 */
	public LdapUser getUser(String userName, String password) throws Throwable {
		LdapUser user = null;
		if (this.isAuthOnLdapServer(userName, password)) {
			user = this.getUser(userName);
		}
		return user;
	}
	
	private boolean isAuthOnLdapServer(String userName, String password) throws Throwable {
		boolean isAuth = false;
		DirContext dirCtx = null;
		try {
			Hashtable ldapParams = this.getLdapConfig().getLdapParameters();
			dirCtx = new InitialDirContext(ldapParams);
			isAuth = _userLdapDao.isAuth(userName, password, ldapParams, dirCtx);
		} catch (ApsSystemException e) {
			throw e;
		} catch (NamingException e) {
			throw new ApsSystemException(e.getMessage());
		} finally {
			if (dirCtx != null) dirCtx.close();
		} 
		return isAuth;
	}
	
	public LdapUser getUser(String userName) throws Throwable {
		LdapUser user= null;
		DirContext dirCtx = null;
		try {
			dirCtx = new InitialDirContext(this.getLdapConfig().getLdapParameters());
			user = _userLdapDao.loadUser(userName, dirCtx);
		} catch (ApsSystemException e) {
			throw e;
		} catch (NamingException e) {
			throw new ApsSystemException(e.getMessage());
		} finally {
			if (dirCtx != null) dirCtx.close();
		}
		return user;
	}
	
	private LdapConfig ldapConfig;
	
	private UserLdapDAO _userLdapDao;
	
	public LdapConfig getLdapConfig() {
		return ldapConfig;
	}
	public void setLdapConfig(LdapConfig ldapConfig) {
		this.ldapConfig = ldapConfig;
	}
	
	
}

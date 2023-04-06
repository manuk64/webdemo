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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.AbstractDAO;

/**
 * Data Access Object per gli oggetti User da Ldap. 
 * @version 1.0
 * @author E.Santoboni
 */
public class UserLdapDAO extends AbstractDAO {
	
	protected boolean isAuth(String userName, String password, 
			Hashtable ldapParams, DirContext dirCtx) throws ApsSystemException {
		boolean isAuth = false;
		try {
			NamingEnumeration answer = dirCtx.search("cn=Users", "(uid="+userName+")", null);
			if (answer.hasMoreElements()) {
				dirCtx.close();
				try {
					SearchResult searchResult = (SearchResult) answer.nextElement();
					String userdn = searchResult.getName() + ",cn=Users,dc=comune,dc=cagliari,dc=it";
					answer.close();
					
					Hashtable clonedParams = new Hashtable();
					clonedParams.putAll(ldapParams);
					clonedParams.put(Context.SECURITY_PRINCIPAL, userdn);
					clonedParams.put(Context.SECURITY_CREDENTIALS, password);
					dirCtx = new InitialDirContext(clonedParams);
					answer = dirCtx.search("cn=Users", "(uid="+userName+")", null);
					isAuth = answer.hasMoreElements();
					answer.close();
					if (isAuth) {
						//log.info("Found User '" + userName + "'");
					}
				} catch (Throwable t) {
					//log.info("Bad Login for User '" + userName + "'");
				}
			} else {
				//log.info("Not found User '" + userName + "'");
			}
			answer.close();
		} catch (Throwable t) {
			//processDaoException(t, ctx, "Errore in autenticazione utente", "isAuth");
		}
		return isAuth;
	}
	
	public LdapUser loadUser(String userName, DirContext dirCtx) throws ApsSystemException {
		LdapUser user = null;
		try {
			NamingEnumeration enumx = dirCtx.search("cn=Users", "(uid="+userName+")", null);
			if (enumx.hasMore()) {
				SearchResult sr = (SearchResult)enumx.next();
				Attributes attrs = sr.getAttributes();
				user = this.createUserFromAttributes(attrs);
				if (user != null) {
					//this.loadUserAttributes(user, groupsConfig, dirCtx, ctx);
				}
			}
		} catch (Throwable t) {
			//processDaoException(t, ctx, "Errore in caricamento utente", "loadUser");
		}
		return user;
	}
	
	/**
	 * Carica e restituisce la lista completa di utenti presenti nel db.
	 * @param dirCtx
	 * @param ctx Il contesto del sistema.
	 * @throws com.agiletec.aps.system.exception.ApsSystemException 
	 * @return La lista completa di utenti (oggetti User)
	 */
	public List loadUsers(DirContext dirCtx)
			throws ApsSystemException {
		List usersList = new ArrayList();
		try {
			NamingEnumeration answer = dirCtx.search("cn=Users", "(uid=*)", null);
			while (answer.hasMore()) {
				SearchResult res = (SearchResult) answer.next();
				Attributes attrs = res.getAttributes();
				LdapUser user = this.createUserFromAttributes(attrs);
				usersList.add(user);
			}
		} catch (Throwable e) {
			processDaoException(e,
					"Errore in caricamento lista di utenti", "loadUsersList");
		}
		return usersList;
	}
	
	private LdapUser createUserFromAttributes(Attributes attrs) throws NamingException {
		LdapUser user = new LdapUser();
		String userName = (String) attrs.get("uid").get(0);
		user.setUserName(userName);
		if (null != attrs.get("userPassword")) {
			byte[] bytesPwd = (byte[]) attrs.get("userPassword").get(0);
			String userPassword = new String(bytesPwd);
			user.setPassword(userPassword);
		}
		return user;
	}
	
}

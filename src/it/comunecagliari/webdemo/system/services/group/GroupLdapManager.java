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
package it.comunecagliari.webdemo.system.services.group;

import it.comunecagliari.webdemo.system.models.LdapConfig;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Servizio di gestione dei gruppi, estensione del manager 
 * dei gruppi per la gestione dei gruppi definiti nel db ldap.
 * @version 1.0
 * @author E.Santoboni
 */
public class GroupLdapManager {
	
	public GroupLdapManager() {
		_groupLdapDao = new GroupLdapDAO();	
	}
	
	public boolean isAuth(){
		return false;
	} 
	public LdapConfig getLdapConfig() {
		return ldapConfig;
	}
	public void setLdapConfig(LdapConfig ldapConfig) {
		this.ldapConfig = ldapConfig;
	}

	/*
	 * Non rilancia eccezioni in maniera da poter essere 
	 * utilizzabile anche in assenza di server LDAP.
	 */
	private void loadGroups()  {
		DirContext dirCtx = null;
		_groups = null;
		try {
			Hashtable ldapParams = this.getLdapConfig().getLdapParameters();
			dirCtx = new InitialDirContext(ldapParams);
			_groups = _groupLdapDao.loadGroups(dirCtx, "cn=webdemo");
		} catch (Throwable t) {			
		} finally {
			try {
				if (dirCtx != null) dirCtx.close();
			} catch (Throwable t) {}
		}
	}

	/**
	 * Restituisce la mappa dei gruppi presenti nel sistema. 
	 * La mappa è indicizzata in base al nome del gruppo.
	 * @return La mappa dei gruppi presenti nel sistema.
	 */
	public Map getGroupsMap() {
		return _groups;
	}
	
	public Map getUsersAuthMap() {
		return _usersAuth;
	}

	public boolean isUsersAuth(String utente) {
		try {
			this.loadGroups();
			this.loadUsersAuth();
			String chiave = "cn=" + utente.trim().toLowerCase();
			if (_usersAuth.containsKey(chiave))
				return true;
		} catch (Throwable t) {}
		return false;
	}

	private void loadUsersAuth() {
		DirContext dirCtx = null;
		_usersAuth = new HashMap();
		try {
			Hashtable ldapParams = this.getLdapConfig().getLdapParameters();
			dirCtx = new InitialDirContext(ldapParams);
			Iterator chiavi = _groups.keySet().iterator();
			while (chiavi.hasNext()) {
				LdapGroup gruppo = (LdapGroup) _groups.get(chiavi.next());
				if (gruppo.getDescr().compareTo("cn=users") == 0) {
					_usersAuth.put(gruppo.getName().toLowerCase(), gruppo);
				} else {
					Map utenti = _groupLdapDao.loadGroups(dirCtx, gruppo.getName());
					_usersAuth.putAll(utenti);
				}
			}
		} catch (Throwable t) {}
	}

	private GroupLdapDAO _groupLdapDao;
	private LdapConfig ldapConfig;
	private Map _groups;
	private Map _usersAuth;
	
	
}

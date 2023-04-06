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

/**
 * Rappresentazione di un'utente LDAP.
 * @version 1.0
 * @author E.Santoboni
 */
public class LdapUser {
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	public String getUserName() {
		return _userName;
	}
	public void setUserName(String userName) {
		this._userName = userName;
	}

	private String _userName;
    private String _password;
}

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

/**
 * Rappresentazione di un gruppo LDAP.
 * @version 1.0
 * @author E.Santoboni
 */
public class LdapGroup implements Comparable {
	
    /**
	 * Restituisce il nome del gruppo.
	 * @return Il nome del gruppo.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Setta il nome del gruppo.
	 * @param name Il nome del gruppo.
	 */
	public void setName(String name) {
		this._name = name;
	}
	
	/**
	 * Restituisce la descrizione del gruppo.
	 * @return La descrizione del gruppo.
	 */
	public String getDescr() {
		return _descr;
	}
	
	/**
	 * Setta la descrizione del gruppo.
	 * @param descr La descrizione del gruppo.
	 */
	public void setDescr(String descr) {
		this._descr = descr;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object group) {
		return _name.compareTo(((LdapGroup) group).getName());
	}
	
	private String _name;
	private String _descr;
	
}

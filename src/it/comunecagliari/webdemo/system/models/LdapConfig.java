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
package it.comunecagliari.webdemo.system.models;

import it.comunecagliari.webdemo.system.models.util.DesEncrypter;

import java.util.Hashtable;

import javax.naming.Context;

/**
 * @version 1.0
 * @author E.Santoboni
 */
public class LdapConfig {
	
	public LdapConfig() {
		_env = new Hashtable(11);
	}
	
	public void setCtxFactory(String ctxFactory) {
		_env.put(Context.INITIAL_CONTEXT_FACTORY, ctxFactory);
	}
	
	public void setProviderUrl(String providerUrl) {
		_env.put(Context.PROVIDER_URL, providerUrl);
	}
	
	public void setSecurityPrincipal(String securityPrincipal) {
		if (securityPrincipal != null && securityPrincipal.length()>0) {
			_env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
		}
	}
	
	public void setSecurityCredential(String securityCredential) {
		if (securityCredential != null && securityCredential.length()>0) {
			DesEncrypter encr = new DesEncrypter();
			String decryptedPassword = encr.decrypt(securityCredential);
			_env.put(Context.SECURITY_CREDENTIALS, decryptedPassword);
		}
	}
	
	public Hashtable getLdapParameters() {
		return this._env;
	}
	
	private Hashtable _env;
	
}

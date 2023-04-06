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

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

import com.agiletec.aps.system.services.AbstractDAO;


public class GroupLdapDAO extends AbstractDAO {
	
	public Map loadGroups(DirContext dirCtx, String strQuery) throws Throwable {
		Map ldapGroups = new HashMap();
		try {
			NamingEnumeration answer = dirCtx.search("cn=portal.040414.1205, cn=Groups", "(" + strQuery + ")", null);
			
				SearchResult res = (SearchResult) answer.next();
				Attributes attrs = res.getAttributes();
				Attribute attributi = attrs.get("uniquemember");
				NamingEnumeration valori = attributi.getAll();
				while (valori.hasMore()){
					LdapGroup group = new LdapGroup();
					StringTokenizer uniqueMember = new StringTokenizer((String) valori.next(), ",");
					group.setName(uniqueMember.nextToken().trim());
					group.setDescr(uniqueMember.nextToken().trim());
					ldapGroups.put(group.getName().toLowerCase(),group);
				}

		} catch (Throwable t) {
			throw t;
		} 
		return ldapGroups;
	} 
}

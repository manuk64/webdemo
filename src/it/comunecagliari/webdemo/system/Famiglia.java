package it.comunecagliari.webdemo.system;

import it.comunecagliari.webdemo.system.services.db.Database;
import it.comunecagliari.webdemo.system.services.residente.Residente;
import it.comunecagliari.webdemo.system.services.residente.ResidenteDAO;

import java.sql.*;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.*;

public class Famiglia {
	private ArrayList _famiglia;
	private ResultSet _risultato;

	public Famiglia() {
		_famiglia = new ArrayList();
	}

	public ArrayList getFamiglia() {
		return _famiglia;
	}

	public String inizializzaFamiglia(PageContext ctx)
	throws Exception {
		ServletRequest req = ctx.getRequest();
		String uscita = "";
		if (req.getParameter("famiglia") != null)
			if (! (req.getParameter("famiglia")=="")) {
				Database db = new Database();
				db.connettiDatabase();
				String sql = creaStringaSql(req);
				_risultato = db.eseguiQuery(sql);
				uscita = riempiComponenti();
				db.chiudiTutto();
		}
		return uscita;
	}

	private String riempiComponenti() 
	throws Exception {
		String uscita = "";
		int i = 0;
		if (_risultato.next()) {
			do {
				Residente familiare = new Residente(_risultato.getString("codiceMaster"));
				ResidenteDAO.creaSchedaRidotta(familiare);
				_famiglia.add(familiare);
				i++;
			} while (_risultato.next());
		} else
			uscita = " - Non ho trovato nulla! Prova con una nuova ricerca.";
		return uscita;
	}

	private String creaStringaSql(ServletRequest req) {
		String risultatoSql = " select a.m1_mast_cod codiceMaster, "
			+ " m1_mast_nomef || m1_mast_nomev nome, "
			+ " to_char(m1_mast_datnas, 'dd/mm/yyyy') datanas, "
			+ " decode(m1_mast_sex, 'F', nvl(m1_relpar_desf, ' '), nvl(m1_relpar_desm, ' ')) relpar "
			+ " from m1_master a, m1_indir b, an_resid c, m1_tab_relpar "
			+ " where a.m1_mast_cod = b.m1_mast_cod "
			+ " and a.m1_mast_cod = c.m1_mast_cod "
			+ " and nvl(an_tab_relpar, '00000') = m1_relpar_cod (+) "
			+ " and m1_ind_seq = an_res_indir "
			+ " and an_cod_fam = " + req.getParameter("famiglia");
		if (Integer.parseInt(req.getParameter("famiglia")) < 0) 
			risultatoSql += " and a.m1_mast_cod = " + req.getParameter("indi");
		risultatoSql += " and (m1_mast_sw2 is not null or m1_mast_sw3 is not null "
			+ " or m1_mast_sw4 is not null or m1_mast_sw6 is not null "
			+ " or m1_mast_sw9 is not null) "
			+ " order by nvl(m1_mast_sw2,'Z'), an_tab_relpar, to_char(m1_mast_datnas, 'yyyymmdd') ";		
		return risultatoSql;
	}
}

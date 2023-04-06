package it.comunecagliari.webdemo.system;

import it.comunecagliari.webdemo.system.services.db.Database;

import java.sql.*;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

public class ListaIndirizzi {
	private ArrayList _listaIndirizzi;
	private ResultSet _risultato;


	public ListaIndirizzi(){
		_listaIndirizzi = new ArrayList();
	}

	public ArrayList getIndirizzi() {
		return _listaIndirizzi;
	}

	public String inizializzaListaIndirizzi(PageContext ctx)
	throws Exception {
		ServletRequest req = ctx.getRequest();
		String uscita = "";
		if (req.getParameter("indirizzo") != null && req.getParameter("civico") != null)
			if (! (req.getParameter("indirizzo")=="") && ! (req.getParameter("civico")=="")) {
				try {
					String sql = creaStringaSql(req);
					Database db = new Database();
					db.connettiDatabase();
					_risultato = db.eseguiQuery(sql);
					uscita = riempiIndirizzi();
					db.chiudiTutto();
				} catch (Exception e) {
					uscita = e.getMessage();
				}
		} 
		return uscita;
	}

	private String creaStringaSql(ServletRequest req) 
	throws Exception {
		String risultatoSql = " select a.m1_via_desc via, b.m1_ind_numero civico, nvl(b.m1_ind_bis, ' ') bis, a.m1_comu_cod codiceComune, a.m1_via_cod codiceVia"
			+ " from m1_tab_vie a, m1_indir b "
			+ " where a.m1_via_desc like '" + req.getParameter("indirizzo").toUpperCase().replace("'", "''") + "%' "
			+ " and  b.m1_ind_numero = " + req.getParameter("civico") 
			+ " and a.m1_via_cod = b.m1_via_cod "
			+ " and a.m1_comu_cod = b.m1_comu_cod "
			+ " and b.m1_ind_seq = 1 "
			+ " group by a.m1_via_desc,  b.m1_ind_numero, nvl(b.m1_ind_bis, ' '), a.m1_comu_cod,  a.m1_via_cod ";
		if (! req.getParameter("civico").matches("(\\d{0,4})"))
			throw new Exception("Civico non valido");
		return risultatoSql;
	}

	private String riempiIndirizzi() throws SQLException {
		String uscita = "";
		if (_risultato.next()) {
			do {
				Indirizzo indirizzo = new Indirizzo();
				indirizzo.setDescrizioneVia(_risultato.getString("via"));
				indirizzo.setNumeroCivico(_risultato.getInt("civico"));
				indirizzo.setBis(_risultato.getString("bis"));
				indirizzo.setCodiceComune(_risultato.getInt("codiceComune"));
				indirizzo.setCodiceVia(_risultato.getInt("codiceVia"));
				_listaIndirizzi.add(indirizzo);
			} while (_risultato.next());
		} else
			uscita = "Nessun indirizzo trovato!";
		return uscita;
	}
}

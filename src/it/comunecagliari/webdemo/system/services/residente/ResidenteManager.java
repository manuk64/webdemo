package it.comunecagliari.webdemo.system.services.residente;

import it.comunecagliari.webdemo.system.services.db.Database;

import java.sql.*;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.*;

public class ResidenteManager {
	private ResultSet _risultato;
	private ArrayList _listaResidenti;
	
	public ResidenteManager(){
		 _listaResidenti = new ArrayList();
	}
	
	public StringBuilder inizializzaListaResidenti(PageContext ctx)
	throws Exception {
		ServletRequest req = ctx.getRequest();
		StringBuilder str = new StringBuilder("");
		if (null != req.getParameter("nomef") 
				|| null != req.getParameter("datnas")
				|| null != req.getParameter("codfis")) 
			if (!(req.getParameter("nomef")=="") 
					|| !(req.getParameter("datnas")=="")
					|| !(req.getParameter("codfis")=="")) {
				str.append(elabora(req, true));
			}	
		if (req.getParameter("codvia") != null) 
			str.append(elabora(req, false));
		return str;
		
	}
	
	private String riempiResidenti(boolean controllo) 
	throws SQLException {
		String uscita = "";
		if (_risultato.next()) {
			do {
				Residente residenti = new Residente();
				residenti.setCodiceMaster(_risultato.getString("codiceMaster"));
				residenti.setNome(_risultato.getString("nome")+ " " + _risultato.getString("deceduto"));
				residenti.setDataNascita( _risultato.getString("datanas"));
				residenti.setStato(_risultato.getString("residenteOra"));
				_listaResidenti.add(residenti);
			} while (_risultato.next() && (_risultato.getRow() < 100 || ! controllo));
			if (_risultato.getRow() == 100 && controllo)
				uscita = "Visualizzati i primi 100 nominativi! Riprova con una ricerca meno ampia.";
		} 
		else 
			if (_listaResidenti.size() == 0)
				uscita = "Non ho trovato nulla! Riprova ampliando la ricerca.";
		return uscita;
	}
	
	private String creaSqlResidenti(ServletRequest req) 
	throws Exception {
		String risultatoSql = "select a.m1_mast_cod codiceMaster, a.m1_mast_nomef||a.m1_mast_nomev nome "
			+ " ,to_char(a.m1_mast_datnas,'dd/mm/yyyy') datanas "
			+ " ,decode(a.m1_mast_sw9,'X', '+', '1', '+', ' ') deceduto "
			+ " , decode(nvl(a.m1_mast_sw2, ' '), 'X', 'RESIDENTE', 'NON RESIDENTE') residenteOra "
			+ " from m1_master a, m1_indir b, an_resid c, m1_comuni d, "
			+ " an_scnas f, m1_master g, m1_master h "
			+ " where a.m1_mast_cod = c.m1_mast_cod "
			+ " and c.m1_mast_cod = b.m1_mast_cod (+) "
			+ " and c.an_res_indir = b.m1_ind_seq (+) "
			+ " and nvl(a.m1_mast_comnas, 0) = d.m1_comu_cod (+) "
			+ " and a.m1_mast_cod = f.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_padre, 0) = g.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_madre, 0) = h.m1_mast_cod (+) ";
		risultatoSql = validaParametri(req, risultatoSql);
		risultatoSql += " and a.m1_mast_sw2 = 'X' "
 			+ " order by a.m1_mast_nomef, to_char(a.m1_mast_datnas, 'yyyymmdd') ";
		return risultatoSql;
	}

	private String creaSqlContaResidenti(ServletRequest req) 
	throws Exception {
		String risultatoSql = "select count(*) totale "
			+ " from m1_master a, m1_indir b, an_resid c, m1_comuni d, "
			+ " an_scnas f, m1_master g, m1_master h "
			+ " where a.m1_mast_cod = c.m1_mast_cod "
			+ " and c.m1_mast_cod = b.m1_mast_cod (+) "
			+ " and c.an_res_indir = b.m1_ind_seq (+) "
			+ " and nvl(a.m1_mast_comnas, 0) = d.m1_comu_cod (+) "
			+ " and a.m1_mast_cod = f.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_padre, 0) = g.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_madre, 0) = h.m1_mast_cod (+) ";
		risultatoSql = validaParametri(req, risultatoSql);
		risultatoSql += " and a.m1_mast_sw2 = 'X' ";
		return risultatoSql;
	}

	private String creaSqlNonResidenti(ServletRequest req) 
	throws Exception {
		String risultatoSql = "select a.m1_mast_cod codiceMaster, a.m1_mast_nomef||a.m1_mast_nomev nome "
			+ " , to_char(a.m1_mast_datnas,'dd/mm/yyyy') datanas "
			+ " , decode(a.m1_mast_sw9,'X', '+', '1', '+', ' ') deceduto "
			+ " , decode(nvl(a.m1_mast_sw2, ' '), 'X', 'RESIDENTE', 'NON RESIDENTE') residenteOra "
			+ " from m1_master a, m1_indir b, an_resid c, m1_comuni d, is_apr4b e, "
			+ " an_scnas f, m1_master g, m1_master h "
			+ " where a.m1_mast_cod = c.m1_mast_cod "
			+ " and c.m1_mast_cod = b.m1_mast_cod (+) "
			+ " and c.an_res_indir = b.m1_ind_seq (+) "
			+ " and a.m1_mast_cod = e.m1_mast_cod (+) "
			+ " and nvl(a.m1_mast_comnas, 0) = d.m1_comu_cod (+) "
			+ " and a.m1_mast_cod = f.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_padre, 0) = g.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_madre, 0) = h.m1_mast_cod (+) ";
		risultatoSql = validaParametri(req, risultatoSql);
		risultatoSql += " and (a.m1_mast_sw2 is null or a.m1_mast_sw3 is not null "
			+ " or a.m1_mast_sw4 is not null or a.m1_mast_sw6 is not null "
			+ " or a.m1_mast_sw9 is not null) "
 			+ " order by a.m1_mast_nomef, to_char(a.m1_mast_datnas, 'yyyymmdd') ";
		return risultatoSql;
	}

	private String creaSqlContaNonResidenti(ServletRequest req) 
	throws Exception {
		String risultatoSql = "select count(*) totale "
			+ " from m1_master a, m1_indir b, an_resid c, m1_comuni d, is_apr4b e, "
			+ " an_scnas f, m1_master g, m1_master h "
			+ " where a.m1_mast_cod = c.m1_mast_cod "
			+ " and c.m1_mast_cod = b.m1_mast_cod (+) "
			+ " and c.an_res_indir = b.m1_ind_seq (+) "
			+ " and a.m1_mast_cod = e.m1_mast_cod (+) "
			+ " and nvl(a.m1_mast_comnas, 0) = d.m1_comu_cod (+) "
			+ " and a.m1_mast_cod = f.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_padre, 0) = g.m1_mast_cod (+) "
			+ " and nvl(f.an_cod_madre, 0) = h.m1_mast_cod (+) ";
		risultatoSql = validaParametri(req, risultatoSql);
		risultatoSql += " and (a.m1_mast_sw2 is null or a.m1_mast_sw3 is not null "
			+ " or a.m1_mast_sw4 is not null or a.m1_mast_sw6 is not null "
			+ " or a.m1_mast_sw9 is not null) ";
		return risultatoSql;
	}

	private String elabora(ServletRequest req, boolean controllo) 
	throws Exception {
		String uscita = "";
		String sql = "";
		int totale = 0;
		Database db = new Database();
		try {
			db.connettiDatabase();
			sql = creaSqlContaResidenti(req);
			_risultato = db.eseguiQuery(sql);
			while (_risultato.next()) {
				totale = totale + _risultato.getInt("totale");
			}
			sql = creaSqlContaNonResidenti(req);
			_risultato = db.eseguiQuery(sql);
			while (_risultato.next()) {
				totale = totale + _risultato.getInt("totale");
			}
			if (totale < 100 || ! controllo) {
				sql = creaSqlResidenti(req);
				_risultato = db.eseguiQuery(sql);
				uscita = riempiResidenti(controllo);
				sql = creaSqlNonResidenti(req);
				_risultato = db.eseguiQuery(sql);
				uscita = riempiResidenti(controllo);
			} else {
				uscita = "Sono stati trovati troppi nominativi! Riprova con una ricerca meno ampia.";
			}
			db.chiudiTutto();
		} catch (Exception e) {
			uscita = e.getMessage();
		}
		return uscita;
	}
	
	private String validaParametri(ServletRequest req, String risultatoSql) 
	throws Exception {
		if ((req.getParameter("nomef") != null) 
				|| (req.getParameter("datnas") != null)
				|| (req.getParameter("codfis") != null)) {
			if (! req.getParameter("nomef").equals("")) {
				risultatoSql += " and a.m1_mast_norm like '" 
					+ req.getParameter("nomef").toUpperCase().replace("'", "").replace(" ", "") + "%' ";
			}
			risultatoSql += " and a.m1_mast_sex like '" 
				+ req.getParameter("sesso") + "' ";
			if (! req.getParameter("nomep").equals("")) {
				risultatoSql += " and nvl(g.m1_mast_norm, '') like '" 
					+ req.getParameter("nomep").toUpperCase().replace("'", "").replace(" ", "") + "%' ";
			}
			if (! req.getParameter("nomem").equals("")) {
				risultatoSql += " and nvl(h.m1_mast_norm, '') like '" 
					+ req.getParameter("nomem").toUpperCase().replace("'", "").replace(" ", "") + "%' ";
			}
			if (! req.getParameter("luonas").equals("")) {
					risultatoSql += " and m1_comu_desc like '" 
						+ req.getParameter("luonas").toUpperCase() + "%' ";
				}
			if (! req.getParameter("datnas").equals("")) 
				if (req.getParameter("datnas").matches("(\\d{2})/(\\d{2})/(\\d{4})")) {
					risultatoSql += " and a.m1_mast_datnas = to_date('" 
						+ req.getParameter("datnas") + "', 'dd/mm/yyyy') ";
				} else 
					throw new Exception("Data di nascita errata");
			if (! req.getParameter("annoda").equals("")) 
				if (req.getParameter("annoda").matches("(\\d{4})")) {
					risultatoSql += " and to_number(to_char(a.m1_mast_datnas, 'yyyy')) >=  " 
						+ req.getParameter("annoda");
				} else 
					throw new Exception("Anno di nascita non valido");
			if (! req.getParameter("annoa").equals("")) 
				if (req.getParameter("annoa").matches("(\\d{4})")) {
					risultatoSql += " and to_number(to_char(a.m1_mast_datnas, 'yyyy')) <=  " 
						+ req.getParameter("annoa");
				} else 
					throw new Exception("Anno di nascita non valido");
			if (! req.getParameter("codfis").equals("")) 
				if (req.getParameter("codfis").matches("([a-zA-Z]{6})(\\d{2})([a-zA-Z])(\\d{2})([a-zA-Z])(\\d{3})([a-zA-Z])")) {
					risultatoSql += " and a.m1_mast_codfis = '" 
						+ req.getParameter("codfis").toUpperCase() + "' ";
				} else 
					throw new Exception("Formato codice fiscale non valido");
		}
		if (req.getParameter("codvia") != null) {
			if (! req.getParameter("civ").equals("")) 
				risultatoSql += " and b.m1_comu_cod = " + req.getParameter("codcom")
				+ " and b.m1_via_cod = " + req.getParameter("codvia")
				+ " and b.m1_ind_numero = " + req.getParameter("civ")
				+ " and nvl(b.m1_ind_bis, '***') = '" 
				+ ((req.getParameter("bis")=="") ? "***" : req.getParameter("bis")) + "' ";
		}
		return risultatoSql;
	}

	public ArrayList getListaResidenti() {
		return _listaResidenti;
	}

}

package it.comunecagliari.webdemo.system.services.residente;

import it.comunecagliari.webdemo.system.services.db.Database;

import java.sql.*;

public class ResidenteDAO {

	public static void creaSchedaRidotta (Residente persona) throws Exception {
		Database database = new Database();
		database.connettiDatabase();
		if(! isDatoSensibile(persona, database)) {
			inizializzaScheda(persona, database);
			cercaGenitori(persona, database);
			cercaImmigrazioneEmigrazione(persona, database);
			cercaIndirizzo(persona, database);
			cercaDecesso(persona, database);
			cercaRelazioneParentela(persona, database);
			cercaFamiglia(persona, database);
		}
		else
			persona.setNome("Dati non visualizzabili tramite web! \nSi prega contattare personalmente l'ufficio Anagrafe");
		database.chiudiTutto();
	}
	public static void creaScheda (Residente persona) throws Exception {
		Database database = new Database();
		database.connettiDatabase();
		if(! isDatoSensibile(persona, database)) {
			inizializzaScheda(persona, database);
			cercaGenitori(persona, database);
			cercaImmigrazioneEmigrazione(persona, database);
			cercaIndirizzo(persona, database);
			cercaConiuge(persona, database);
			cercaDecesso(persona, database);
			cercaDocumentoIdentita(persona, database);
			cercaVariazioniResidenza(persona, database);
			cercaVariazioniResidenzaAppese(persona, database);
			cercaDivorzio(persona, database);
			cercaDatiElettorali(persona, database);
			cercaRelazioneParentela(persona, database);
			cercaFamiglia(persona, database);
		}
		else
			persona.setNome("Dati non visualizzabili tramite web! \nSi prega contattare personalmente l'ufficio Anagrafe");
		database.chiudiTutto();
	}
	
	private static void cercaVariazioniResidenza(Residente persona, Database database) throws Exception {
		String ResidenzaAttuale = "";
		String DataVariazione = "";
		String ResidenzaStorica = "";
		int i = 0;
		String _sql = " select to_char(an_dateeve,'dd/mm/yyyy') dataEvento, an_desvar, "
			+ " nvl(an_attuale, ' ') an_attuale, an_storico || an_storico1 ||' '|| an_indir_nuovo storico "
			+ " from an_variazstf "
			+ " where m1_mast_cod = " + persona.getCodiceMaster() 
			+ " and an_codvar in (2,14,60,61,62,63,64,81,82,83,86,95,161) "
			+ " order by an_datareg desc ";
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			do {
				DataVariazione =_result.getString("dataEvento");
				ResidenzaStorica =_result.getString("an_desvar") + " " + _result.getString("storico");
				ResidenzaAttuale=DataVariazione +" " + ResidenzaStorica + " " + _result.getString("an_attuale");
				persona.getVariazioniResidenze().add(ResidenzaAttuale.toUpperCase());
				i++;
			} while (_result.next());
		} else {
			persona.getVariazioniResidenze().add("Nessuna");
		}
	}
	
	private static void cercaVariazioniResidenzaAppese(Residente persona, Database database) throws Exception {
		String variazione;
		int i = 0;
		String _sql = " select distinct nvl(to_char(an_data_pratica,'dd/mm/yyyy'),' ') dataPratica, an_ind_dest richiesta "
			+ " from an_dichiara "
			+ " where m1_mast_cod = " + persona.getCodiceMaster()
			+ " and an_fase_pratica <> 1";			
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			do {
				variazione = _result.getString("dataPratica") + " " + _result.getString("richiesta");
				persona.getVariazioneAppesa().add(variazione.toUpperCase());
				i++;
			} while (_result.next());
		} else
			persona.getVariazioneAppesa().add("Nessuna");
	}
	
	private static void cercaImmigrazioneEmigrazione(Residente persona, Database database) throws Exception {
		int codiceComuneEmigrazione = 0;
		int codiceComuneImmigrazione = 0;
		String secondaNazionalita="";
		String _sql = " select to_char(nvl(an_dat_emigr, '01/feb/1800'), 'dd/mm/yyyy') dataEmigrazione, "
			  + " nvl(an_com_emigr, 0) codiceEmigrazione, to_char(nvl(an_dat_immigr, '01/feb/1800'), 'dd/mm/yyyy') dataImmigrazione, " 
			  + " nvl(an_com_immigr, 0) codiceImmigrazione, nvl(an_cancellato, ' ') cancellato " 
			  + " , nvl(an_iscritto, ' ') iscritto, nvl(a.m1_nazio_des, ' ') primaNazionalita, nvl(b.m1_nazio_des, ' ') secondaNazionalita, "
			  + " to_char(nvl(an_data_ricomo, '01/feb/1800'), 'dd/mm/yyyy' ) dataRicomparsa, nvl(an_com_ricomp, 0) codiceComuneRicomparsa "
			  + " from an_resid, m1_tab_nazio a, m1_tab_nazio b "
			  + " where m1_mast_cod = " + persona.getCodiceMaster() 
			  + " and an_tab_naz = a.m1_nazio_cod (+) "
			  + " and an_tab_naz1 = b.m1_nazio_cod (+) ";
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next()){
			persona.setDataEmigrazione(_result.getString("dataEmigrazione").equals("01/02/1800") ? " " : _result.getString("dataEmigrazione"));
			persona.setDataImmigrazione( _result.getString("dataImmigrazione").equals("01/02/1800") ? " " : _result.getString("dataImmigrazione"));
			codiceComuneEmigrazione = _result.getInt("codiceEmigrazione");
			codiceComuneImmigrazione = _result.getInt("codiceImmigrazione");
			if(secondaNazionalita!="")
				secondaNazionalita = " / " + _result.getString("secondaNazionalita");
			persona.setNazionalita(_result.getString("primaNazionalita") + secondaNazionalita);
			persona.setIscritto(_result.getString("iscritto"));
			persona.setCancellato(_result.getString("cancellato"));
			if(persona.getCancellato().equals("003")){
				persona.setCodiceComuneRicomparsa(_result.getInt("codiceComuneRicomparsa"));
				persona.setDataRicomparsa(_result.getString("dataRicomparsa").equals("01/02/1800") ? " " : _result.getString("dataRicomparsa"));					
				persona.setDescrizioneComuneRicomparsa(decodificaComune(persona.getCodiceComuneRicomparsa(), database));
			}
			persona.setComuneEmigrazione(decodificaComune(codiceComuneEmigrazione, database));
			persona.setComuneImmigrazione(decodificaComune(codiceComuneImmigrazione, database));

			}
	}
	
	private static void cercaPraticheImmigrazioneEmigrazione(Residente persona, Database database) throws Exception {
		int codiceComuneEmigrazione = 0;
		int codiceComuneImmigrazione = 0;
		String secondaNazionalita="";
		String _sql = " select to_char(nvl(an_dat_emigr, '01/feb/1800'), 'dd/mm/yyyy') dataEmigrazione, "
			  + " nvl(an_com_emigr, 0) codiceEmigrazione, to_char(nvl(an_dat_immigr, '01/feb/1800'), 'dd/mm/yyyy') dataImmigrazione, " 
			  + " nvl(an_com_immigr, 0) codiceImmigrazione, nvl(an_cancellato, ' ') cancellato " 
			  + " , nvl(an_iscritto, ' ') iscritto, nvl(a.m1_nazio_des, ' ') primaNazionalita, nvl(b.m1_nazio_des, ' ') secondaNazionalita, "
			  + " to_char(nvl(an_data_ricomo, '01/feb/1800'), 'dd/mm/yyyy' ) dataRicomparsa, nvl(an_com_ricomp, 0) codiceComuneRicomparsa "
			  + " from an_resid, m1_tab_nazio a, m1_tab_nazio b "
			  + " where m1_mast_cod = " + persona.getCodiceMaster() 
			  + " and an_tab_naz = a.m1_nazio_cod (+) "
			  + " and an_tab_naz1 = b.m1_nazio_cod (+) ";
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next()){
			persona.setDataEmigrazione(_result.getString("dataEmigrazione").equals("01/02/1800") ? " " : _result.getString("dataEmigrazione"));
			persona.setDataImmigrazione( _result.getString("dataImmigrazione").equals("01/02/1800") ? " " : _result.getString("dataImmigrazione"));
			codiceComuneEmigrazione = _result.getInt("codiceEmigrazione");
			codiceComuneImmigrazione = _result.getInt("codiceImmigrazione");
			if(secondaNazionalita!="")
				secondaNazionalita = " / " + _result.getString("secondaNazionalita");
			persona.setNazionalita(_result.getString("primaNazionalita") + secondaNazionalita);
			persona.setIscritto(_result.getString("iscritto"));
			persona.setCancellato(_result.getString("cancellato"));
			if(persona.getCancellato().equals("003")){
				persona.setCodiceComuneRicomparsa(_result.getInt("codiceComuneRicomparsa"));
				persona.setDataRicomparsa(_result.getString("dataRicomparsa").equals("01/02/1800") ? " " : _result.getString("dataRicomparsa"));					
				persona.setDescrizioneComuneRicomparsa(decodificaComune(persona.getCodiceComuneRicomparsa(), database));
			}
			persona.setComuneEmigrazione(decodificaComune(codiceComuneEmigrazione, database));
			persona.setComuneImmigrazione(decodificaComune(codiceComuneImmigrazione, database));

			}
	}
	
	private static void cercaGenitori(Residente persona, Database database) throws Exception	{
		String _sql = " select nvl(an_cod_madre, 0) codiceMadre, nvl(an_cod_padre, 0) codicePadre "
			  + " from an_scnas "
			  + " where m1_mast_cod = " + persona.getCodiceMaster();
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next()){
			persona.setCodiceMadre( _result.getString("codiceMadre"));
			persona.setCodicePadre( _result.getString("codicePadre"));
			persona.setMadre(decodificaParentela(persona.getCodiceMadre(), database));
			persona.setPadre(decodificaParentela(persona.getCodicePadre(), database));
			}
	}
	
	private static String decodificaParentela(String codiceMaster, Database database) throws Exception{
		String _sql = " select m1_mast_cod codice, m1_mast_nomef||m1_mast_nomev nome, nvl(m1_mast_sex, ' ') sesso, "
			+ " to_char(m1_mast_datnas,'dd/mm/yyyy') dataNascita, "
			+ " decode(m1_mast_sw9, 'X', '+', '1', '+', ' ') deceduto "
			+ " from m1_master "
			+ " where m1_mast_cod = " + codiceMaster;
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next() && _result.getString("nome") != null)
			return _result.getString("nome");
		else 
			return "";
	}
	
	private static String decodificaComune(int CodiceComune, Database database) throws Exception{
		String Provincia ="";
		String Comune ="";
		String _sql = " select m1_comu_desc comune, m1_comu_prov provincia "
			  + " from m1_comuni "
			  + " where m1_comu_cod = " + CodiceComune;
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next() && _result.getString("comune")!=null) {
				Provincia = _result.getString("provincia");
				if (Provincia != null)
					Comune = _result.getString("comune") + " (" + _result.getString("provincia") + ")";
				else 
					Comune =_result.getString("comune");
			}
		return Comune;
	}
	
	private static void inizializzaScheda(Residente persona, Database database) throws Exception {
		if( persona.getCodiceMaster() != null){
			String _sql = " select m1_mast_cod codice, m1_mast_nomef||m1_mast_nomev nome "
				+ " , nvl(m1_mast_sex, ' ') sesso "
				+ " , to_char(nvl(m1_mast_datnas, '01/feb/1800'),'dd/mm/yyyy') dataNascita"
				+ " , nvl(m1_mast_codfis, '') codiceFiscale "
				+ " , nvl(m1_mast_comnas, 0) codComuneNascita "
				+ " , decode(nvl(m1_mast_sw9, ' '), 'X', '+', '1', '+', ' ') deceduto "
				+ " , nvl(m1_mast_sw16, '0') convalidaCodice "
				+ " , nvl(m1_mast_sw3, '0') cancellato "
				+ " , nvl(m1_mast_sw6, '0') emigrato "
				+ " , nvl(m1_mast_sw4, '0') aire "
				+ " , nvl(m1_mast_sw2, ' ') residenteOra "
				+ " , nvl(m1_mast_sw24, ' ') generico "
				+ " from m1_master "
				+ " where m1_mast_cod = " + persona.getCodiceMaster();
			ResultSet _result = database.eseguiQuery(_sql);
			if (_result.next()) {
				persona.setNome( _result.getString("nome"));
				persona.setSesso(_result.getString("sesso"));
				persona.setDataNascita(_result.getString("dataNascita").equals("01/02/1800") ? " " : _result.getString("dataNascita"));
				persona.setCodiceFiscale(_result.getString("codiceFiscale"));
				if (_result.getString("convalidaCodice").equals("2"))
					persona.setCodiceFiscale(persona.getCodiceFiscale() + " (CONVALIDATO)");
				else
					persona.setCodiceFiscale(persona.getCodiceFiscale() + " (NON CONVALIDATO)");
				if (_result.getString("residenteOra").equals("X"))
					persona.setStato("RESIDENTE");
				else {
					if (_result.getString("generico").equals("X"))
						persona.setStato("SCHEDA GENERICA");
					if (_result.getString("cancellato").equals("X"))
						persona.setStato("NON RESIDENTE");
					if (_result.getString("cancellato").equals("1"))
						persona.setStato("IRREPERIBILE");
					if (_result.getString("emigrato").equals("X"))
						persona.setStato("EMIGRATO");
					if (_result.getString("aire").equals("X"))
						persona.setStato("AIRE");
					if (_result.getString("deceduto").equals("+"))
						persona.setStato("DECEDUTO");
				}
				persona.setComuneNascita(decodificaComune(_result.getInt("codComuneNascita"), database));
				if (persona.getStato().equals("")) {
					persona.setStato("PRATICA DI IMMIGRAZIONE IN CORSO");
					schedaApr4(persona, database);
				}
			}
		}
	}

	private static void schedaApr4(Residente persona, Database database) throws Exception {
		if( persona.getCodiceMaster() != null){
			String _sql = " select m1_mast_cod codice, m1_mast_nomef||m1_mast_nomev nome, nvl(m1_mast_sex, ' ') sesso "
				+ " , to_char(nvl(m1_mast_datnas, '01/feb/1800'),'dd/mm/yyyy') dataNascita, nvl(m1_mast_codfis, '') codiceFiscale "
				+ " , nvl(m1_mast_comnas, 0) codComuneNascita "
				+ " , nvl(m1_mast_sw16, '0') convalidaCodice "
				+ " from m1_master_apr4 "
				+ " where m1_mast_cod = " + persona.getCodiceMaster();
			ResultSet _result = database.eseguiQuery(_sql);
			if(_result.next()){
				persona.setNome( _result.getString("nome"));
				persona.setSesso(_result.getString("sesso"));
				persona.setDataNascita(_result.getString("dataNascita").equals("01/02/1800") ? " " : _result.getString("dataNascita"));
				persona.setCodiceFiscale(_result.getString("codiceFiscale"));
				if(_result.getString("convalidaCodice").equals("2"))
					persona.setCodiceFiscale(persona.getCodiceFiscale() + " (CONVALIDATO)");
				else
					persona.setCodiceFiscale(persona.getCodiceFiscale() + " (NON CONVALIDATO)");
				persona.setComuneNascita(decodificaComune(_result.getInt("codComuneNascita"), database));
			}
		}
	}

	private static void cercaIndirizzo(Residente persona, Database database) 
	throws Exception {
		String _sql = " select nvl(m1_comu_cod, 0) codiceComune, nvl(m1_via_cod, 0) codiceVia, "
			  + " nvl(m1_ind_numero, 0) numeroCivico, m1_ind_bis bis, nvl(m1_ind_scala, '') scala, "
			  + " nvl(m1_ind_piano, '') piano, nvl(m1_ind_interno,'') interno ";
		if (persona.getStato().equals("PRATICA DI IMMIGRAZIONE IN CORSO"))
			_sql += " from m1_indir_apr4 a, an_resid_apr4 b ";
		else
			_sql += " from m1_indir a, an_resid b ";
		_sql += " where a.m1_mast_cod = " + persona.getCodiceMaster()
				  + " and a.m1_mast_cod = b.m1_mast_cod "
				  + " and m1_ind_seq = an_res_indir ";
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next()){
			persona.getIndirizzo().setCodiceComune( _result.getInt("codiceComune"));
			persona.getIndirizzo().setCodiceVia( _result.getInt("codiceVia"));
			persona.getIndirizzo().setNumeroCivico(_result.getInt("numeroCivico"));
			if(_result.getString("bis")!=null){
				persona.getIndirizzo().setBis( _result.getString("bis"));
			}
			persona.getIndirizzo().setScala(_result.getString("scala"));
			persona.getIndirizzo().setPiano(_result.getString("piano"));
			persona.getIndirizzo().setInterno(_result.getString("interno"));
			persona.getIndirizzo().setDescrizioneComune(decodificaComune(persona.getIndirizzo().getCodiceComune(), database));		
			persona.getIndirizzo().setDescrizioneVia(decodificaIndirizzo(persona.getIndirizzo().getCodiceComune(), persona.getIndirizzo().getCodiceVia(), database));
		}	
	}
	
	private static String decodificaIndirizzo(int codiceComune, int codiceVia, Database database) 
	throws Exception {
		String _sql = " select nvl(m1_via_desc, ' ') indirizzo "
			  + " from m1_tab_vie "
			  + " where m1_comu_cod = " + codiceComune 
			  + " and m1_via_cod = " + codiceVia;
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next())
			return _result.getString("indirizzo");		
		else
			return "";
	}
	
	private static void cercaFamiglia(Residente persona, Database database) 
	throws Exception{
		String _sql = " select an_cod_fam "
			  + " from an_resid "
			  + " where m1_mast_cod = " + persona.getCodiceMaster();
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next())
			persona.setCodiceFamiglia(_result.getString("an_cod_fam"));
	}

	private static void cercaRelazioneParentela(Residente persona, Database database) 
	throws Exception {
		String _sql = " select decode(m1_mast_sex, 'F', m1_relpar_desf, m1_relpar_desm) relpar "
			  + " from an_resid a, m1_tab_relpar b, m1_master c "
			  + " where an_tab_relpar = m1_relpar_cod " 
			  + " and a.m1_mast_cod = " + persona.getCodiceMaster()
			  + " and a.m1_mast_cod = c.m1_mast_cod ";
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next())
			persona.setRelazioneParentela(_result.getString("relpar"));
	}
	
	private static void cercaConiuge(Residente persona, Database database) 
	throws Exception{
		int AttoComune = 0;
		String _sql = " select nvl(an_cod_coniuge, 0) codiceConiuge, nvl(to_char(an_mat_data,'dd/mm/yyyy'), ' ') dataMatrimonio, "
			 + " nvl(to_char(an_mat_ora, 'hh:mm'), ' ') oraMatrimonio, "
			 + " nvl(an_mat_atcomune, 0) attoComune "
			 + " from an_scmat "
			 + " where m1_mast_cod = " + persona.getCodiceMaster();
		ResultSet _result = database.eseguiQuery(_sql);
		if(_result.next()){
			persona.setCodiceConiuge(_result.getString("codiceConiuge"));		
			persona.setDataMatrimonio(_result.getString("dataMatrimonio"));
			persona.setOraMatrimonio(_result.getString("oraMatrimonio"));
			AttoComune = _result.getInt("attoComune");
			persona.setConiuge(decodificaParentela(persona.getCodiceConiuge(), database));
			persona.setComuneMatrimonio(decodificaComune(AttoComune, database));
		}		
	}
	

	private static void cercaDecesso(Residente persona, Database database) 
	throws Exception{
		int codiceComuneMorte = 0;
		String _sql = " select to_char(an_mor_data, 'dd/mm/yyyy') dataMorte, "
			 + " nvl(an_mor_atcomune, 0) codiceComuneMorte "
			 + " from an_scmor "
			 + " where m1_mast_cod = " + persona.getCodiceMaster();
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			codiceComuneMorte =  _result.getInt("codiceComuneMorte");
			persona.setDataMorte(_result.getString("dataMorte"));
			persona.setComuneMorte(decodificaComune(codiceComuneMorte, database));
		}
	}
	
	private static void cercaDocumentoIdentita(Residente persona, Database database) 
	throws Exception {
		String _sql = " select nvl(an_cid_lett, ' ') lettere, to_char(an_cid_numero, '0000000') numeri, to_char(an_cid_rilascio, 'dd/mm/yyyy') dataRilascio, "
			 + " to_char(an_cid_scadenza, 'dd/mm/yyyy') dataScadenza, "
			 + " decode(an_cid_valesp, 0, 'Valida espatrio', '1', 'Non valida espatrio', 'Non previsto') espatrio "
			 + " from an_caride "
			 + " where m1_mast_cod = " + persona.getCodiceMaster()
			 + " and an_cid_progr = (select max(an_cid_progr) from an_caride where m1_mast_cod = " 
			 + persona.getCodiceMaster() + " and an_cid_annril = 0 group by m1_mast_cod) ";
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			persona.setCartaIdentita( _result.getString("lettere") + _result.getString("numeri") + " " + _result.getString("espatrio"));
			persona.setScadenzaCartaIdentita(_result.getString("dataScadenza"));
			persona.setEmissioneCartaIdentita(_result.getString("dataRilascio"));
			}
	}
	
	private static void cercaDivorzio(Residente persona, Database database) 
	throws Exception{
		String _sql = " select nvl(to_char(an_div_datadiv, 'dd/mm/yyyy'), '') dataDivorzio, "
			+ " nvl(an_div_tribunale, '') tribunale "
			+ " from an_scdiv "
			+ " where m1_mast_cod = " + persona.getCodiceMaster();
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			persona.setTribunaleDivorzio(_result.getString("tribunale"));
			persona.setDataDivorzio(_result.getString("dataDivorzio"));
			}
	}
	
	private static void cercaDatiElettorali(Residente persona, Database database)
	throws Exception{
		String _sql = " select el_anagr.el_num_gen numeroGenerale, el_anagr.el_sez_app sezioneAppartenenza, " 
			+ " el_tab_sezsts.el_sez_luogo luogoSezione, el_collprov.el_descam descrizioneCamera, "
			+ " el_collprov.el_despro descrizioneProvincia, el_collprov.el_deseur descrizioneEuropee, "
			+ " el_collprov.el_descir descrizioneCircoscrizione "
			+ " from el_anagr, el_tab_sezsts, el_collprov "
			+ " where m1_mast_cod = " + persona.getCodiceMaster() 
			+ " and el_tab_sezsts.el_sez_num = el_anagr.el_sez_app "
			+ "and el_collprov.el_sez_app = el_anagr.el_sez_app";
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			persona.setNumeroElettorale(_result.getInt("numeroGenerale"));
			persona.setNumeroSezione(_result.getInt("sezioneAppartenenza"));
			persona.setSezioneElettorale(_result.getString("luogoSezione"));
			persona.setCamera( _result.getString("descrizioneCamera"));
			persona.setProvinciaElettorale(_result.getString("descrizioneProvincia"));
			persona.setEuropee(_result.getString("descrizioneEuropee"));
			persona.setCircoscrizione(_result.getString("descrizioneCircoscrizione"));
			}
	}
	
	private static boolean isDatoSensibile(Residente persona, Database database) 
	throws Exception{
		String msgcond="";
	    int msgbloc=0;
		String _sql = " select instr(m1_appl_cod,'an') msgcond, m1_msg_bloc msgbloc "
			+ " from m1_message " 
			+ " where m1_mast_cod = " + persona.getCodiceMaster();
		ResultSet _result = database.eseguiQuery(_sql);
		if (_result.next()) {
			msgbloc = _result.getInt("msgbloc");
			msgcond = _result.getString("msgcond");
			}
		if (msgbloc == 0 || msgcond == "0")
			return false;
		else
			return true;
	}
	
}



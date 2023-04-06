package it.comunecagliari.webdemo.system.services.residente;

import it.comunecagliari.webdemo.system.Indirizzo;

import java.util.ArrayList;

public class Residente {
	private String _codiceMaster;
	private String _codiceFiscale;
	private String _nome;
	private String _sesso;
	private String _comuneNascita;
	private String _dataNascita;
	private String _codicePadre;
	private String _codiceMadre;
	private String _padre;
	private String _madre;
	private String _scala;   
    private String _piano;   
    private String _interno;
    private String _dataImmigrazione;
    private String _comuneImmigrazione;
    private String _dataEmigrazione;
    private String _comuneEmigrazione;
    private String _codiceConiuge;
    private String _coniuge;
    private String _dataMatrimonio;
	private String _oraMatrimonio;
	private String _comuneMatrimonio;
	private String _dataMorte;
	private String _comuneMorte;
	private String _cartaIdentita;
	private String  _scadenzaCartaIdentita;
	private String _emissioneCartaIdentita;
	private Indirizzo _indirizzo;
	private ArrayList _variazioniResidenze;
	private ArrayList _variazioneAppesa;
	private String _nazionalita;
	private String _tribunaleDivorzio;
	private String _dataDivorzio;
	private int _numeroElettorale;
	private int _numeroSezione;
	private String _sezioneElettorale;
	private String _camera;
	private String _provinciaElettorale;
	private String _europee;
	private String _circoscrizione;
	private String _cancellato;
	private String _iscritto;
	private int _codiceComuneRicomparsa;
	private String _descrizioneComuneRicomparsa;
	private String _dataRicomparsa;
	private String _codiceFamiglia;
	private String _relazioneParentela;
	private String _stato;
	
	public Residente(){
		_indirizzo = new Indirizzo();
		_cancellato = "";
		_iscritto = " ";
		_codiceComuneRicomparsa = 0;
		_descrizioneComuneRicomparsa = "";
		_dataRicomparsa = "";
		_variazioniResidenze = new ArrayList();
		_variazioneAppesa = new ArrayList();
		_codiceMaster = "";
		_codiceFiscale = "";
		_nome = "";
		_sesso = "";
		_comuneNascita = "";
		_dataNascita = "";
		_codiceMadre="0";
		_codicePadre="0";
		_padre="";
		_madre="";
	    _scala = "";   
	    _piano = "";   
	    _interno = "";
	    _dataImmigrazione = "";
	    _comuneImmigrazione="";
	    _dataEmigrazione =  "";
	    _comuneEmigrazione = "";
	    _codiceConiuge = "0";
	    _coniuge = "";
	    _dataMatrimonio = "";
		_oraMatrimonio = "";
		_comuneMatrimonio = "";
		_dataMorte = "";
		_comuneMorte = "";
		_cartaIdentita = "";
		_emissioneCartaIdentita = "";
		_scadenzaCartaIdentita = "";
		_nazionalita="";
		_tribunaleDivorzio="";
		_dataDivorzio="";
		_numeroElettorale = 0;
		_sezioneElettorale = "";
		_numeroSezione = 0;
		_camera = "";
		_provinciaElettorale= "";
		_europee= "";
		_circoscrizione= "";
		_codiceFamiglia = "0";
		_relazioneParentela = "";
		_stato = "";
	}
	public Residente(String codiceMaster){
		this();
		_codiceMaster = codiceMaster;
	}
	public String getCamera() {
		return _camera;
	}
	public void setCamera(String _camera) {
		this._camera = _camera;
	}
	public String getCartaIdentita() {
		return _cartaIdentita;
	}
	public void setCartaIdentita(String identita) {
		_cartaIdentita = identita;
	}
	public String getCircoscrizione() {
		return _circoscrizione;
	}
	public void setCircoscrizione(String _circoscrizione) {
		this._circoscrizione = _circoscrizione;
	}
	public int getNumeroCivico() {
		return getIndirizzo().getNumeroCivico(); 
	}
	public String getBis() {
		return getIndirizzo().getBis();
	}
	public String getCodiceConiuge() {
		return _codiceConiuge;
	}
	public void setCodiceConiuge(String coniuge) {
		_codiceConiuge = coniuge;
	}
	public String getCodiceFiscale() {
		return _codiceFiscale;
	}
	public void setCodiceFiscale(String fiscale) {
		_codiceFiscale = fiscale;
	}
	public String getCodiceMadre() {
		return _codiceMadre;
	}
	public void setCodiceMadre(String madre) {
		_codiceMadre = madre;
	}
	public String getCodiceMaster() {
		return _codiceMaster;
	}
	public void setCodiceMaster(String master) {
		_codiceMaster = master;
	}
	public String getCodicePadre() {
		return _codicePadre;
	}
	public void setCodicePadre(String padre) {
		_codicePadre = padre;
	}
	public String getComuneEmigrazione() {
		return _comuneEmigrazione;
	}
	public void setComuneEmigrazione(String emigrazione) {
		_comuneEmigrazione = emigrazione;
	}
	public String getComuneImmigrazione() {
		return _comuneImmigrazione;
	}
	public void setComuneImmigrazione(String immigrazione) {
		_comuneImmigrazione = immigrazione;
	}
	public String getComuneMatrimonio() {
		return _comuneMatrimonio;
	}
	public void setComuneMatrimonio(String matrimonio) {
		_comuneMatrimonio = matrimonio;
	}
	public String getComuneMorte() {
		return _comuneMorte;
	}
	public void setComuneMorte(String morte) {
		_comuneMorte = morte;
	}
	public String getComuneNascita() {
		return _comuneNascita;
	}
	public void setComuneNascita(String nascita) {
		_comuneNascita = nascita;
	}
	public String getComuneResidenza() {
		return getIndirizzo().getDescrizioneComune();
	}
	public void setComuneResidenza(String residenza) {
		getIndirizzo().setDescrizioneComune(residenza);
	}
	public String getConiuge() {
		return _coniuge;
	}
	public void setConiuge(String _coniuge) {
		this._coniuge = _coniuge;
	}
	public String getDataDivorzio() {
		return _dataDivorzio;
	}
	public void setDataDivorzio(String divorzio) {
		_dataDivorzio = divorzio;
	}
	public String getDataEmigrazione() {
		return _dataEmigrazione;
	}
	public void setDataEmigrazione(String emigrazione) {
		_dataEmigrazione = emigrazione;
	}
	public String getDataImmigrazione() {
		return _dataImmigrazione;
	}
	public void setDataImmigrazione(String immigrazione) {
		_dataImmigrazione = immigrazione;
	}
	public String getDataMatrimonio() {
		return _dataMatrimonio;
	}
	public void setDataMatrimonio(String matrimonio) {
		_dataMatrimonio = matrimonio;
	}
	public String getDataMorte() {
		return _dataMorte;
	}
	public void setDataMorte(String morte) {
		_dataMorte = morte;
	}
	public String getDataNascita() {
		return _dataNascita;
	}
	public void setDataNascita(String nascita) {
		_dataNascita = nascita;
	}
	public String getEmissioneCartaIdentita() {
		return _emissioneCartaIdentita;
	}
	public void setEmissioneCartaIdentita(String cartaIdentita) {
		_emissioneCartaIdentita = cartaIdentita;
	}
	public String getEuropee() {
		return _europee;
	}
	public void setEuropee(String _europee) {
		this._europee = _europee;
	}
	public String getInterno() {
		return _interno;
	}
	public void setInterno(String _interno) {
		this._interno = _interno;
	}
	public String getMadre() {
		return _madre;
	}
	public void setMadre(String _madre) {
		this._madre = _madre;
	}
	public String getNazionalita() {
		return _nazionalita;
	}
	public void setNazionalita(String _nazionalita) {
		this._nazionalita = _nazionalita;
	}
	public String getNome() {
		return _nome;
	}
	public void setNome(String _nome) {
		this._nome = _nome;
	}
	public int getNumeroElettorale() {
		return _numeroElettorale;
	}
	public void setNumeroElettorale(int elettorale) {
		_numeroElettorale = elettorale;
	}
	public int getNumeroSezione() {
		return _numeroSezione;
	}
	public void setNumeroSezione(int sezione) {
		_numeroSezione = sezione;
	}
	public String getOraMatrimonio() {
		return _oraMatrimonio;
	}
	public void setOraMatrimonio(String matrimonio) {
		_oraMatrimonio = matrimonio;
	}
	public String getPadre() {
		return _padre;
	}
	public void setPadre(String _padre) {
		this._padre = _padre;
	}
	public String getPiano() {
		return _piano;
	}
	public void setPiano(String _piano) {
		this._piano = _piano;
	}
	public String getProvinciaElettorale() {
		return _provinciaElettorale;
	}
	public void setProvinciaElettorale(String elettorale) {
		_provinciaElettorale = elettorale;
	}
	public String getScadenzaCartaIdentita() {
		return _scadenzaCartaIdentita;
	}
	public void setScadenzaCartaIdentita(String cartaIdentita) {
		_scadenzaCartaIdentita = cartaIdentita;
	}
	public String getScala() {
		return _scala;
	}
	public void setScala(String _scala) {
		this._scala = _scala;
	}
	public String getSesso() {
		return _sesso;
	}
	public void setSesso(String _sesso) {
		this._sesso = _sesso;
	}
	public String getSezioneElettorale() {
		return _sezioneElettorale;
	}
	public void setSezioneElettorale(String elettorale) {
		_sezioneElettorale = elettorale;
	}
	public String getTribunaleDivorzio() {
		return _tribunaleDivorzio;
	}
	public void setTribunaleDivorzio(String divorzio) {
		_tribunaleDivorzio = divorzio;
	}
	public ArrayList getVariazioneAppesa() {
		return _variazioneAppesa;
	}
	public void setVariazioneAppesa(ArrayList appesa) {
		_variazioneAppesa = appesa;
	}
	public ArrayList getVariazioniResidenze() {
		return _variazioniResidenze;
	}
	public void setVariazioniResidenze(ArrayList residenze) {
		_variazioniResidenze = residenze;
	}
	public Indirizzo getIndirizzo() {
		return _indirizzo;
	}
	public void setIndirizzo(Indirizzo _indirizzo) {
		this._indirizzo = _indirizzo;
	}
	public int getCodiceComuneRicomparsa() {
		return _codiceComuneRicomparsa;
	}
	public void setCodiceComuneRicomparsa(int codiceComuneRicomparsa) {
		this._codiceComuneRicomparsa = codiceComuneRicomparsa;
	}
	public String getDataRicomparsa() {
		return _dataRicomparsa;
	}
	public void setDataRicomparsa(String dataRicomparsa) {
		this._dataRicomparsa = dataRicomparsa;
	}
	public String getDescrizioneComuneRicomparsa() {
		return _descrizioneComuneRicomparsa;
	}
	public void setDescrizioneComuneRicomparsa(String descrizioneComuneRicomparsa) {
		this._descrizioneComuneRicomparsa = descrizioneComuneRicomparsa;
	}
	public String getCancellato() {
		return _cancellato;
	}
	public void setCancellato(String cancellato) {
		this._cancellato = cancellato;
	}
	public String getCodiceFamiglia() {
		return _codiceFamiglia;
	}
	public void setCodiceFamiglia(String codiceFamiglia) {
		this._codiceFamiglia = codiceFamiglia;
	}
	public String getRelazioneParentela() {
		return _relazioneParentela;
	}
	public void setRelazioneParentela(String relazioneParentela) {
		this._relazioneParentela = relazioneParentela;
	}
	public String getIscritto() {
		return _iscritto;
	}
	public void setIscritto(String iscritto) {
		this._iscritto = iscritto;
	}
	public String getStato() {
		return _stato;
	}
	public void setStato(String stato) {
		this._stato = stato;
	}
}


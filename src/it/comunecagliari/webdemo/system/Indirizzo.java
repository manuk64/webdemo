package it.comunecagliari.webdemo.system;

public class Indirizzo {
	private int _codiceVia;
	private int _codiceComune;
	private String _descrizioneComune;
	private String _descrizioneVia;
	private int _numeroCivico;
	private String _bis;
    private String _scala;   
    private String _piano;   
    private String _interno;

	
	public Indirizzo(){
		_codiceVia=0;
		_codiceComune=0;
		_descrizioneComune = "";
		_descrizioneVia="";
		_numeroCivico=0;
		_bis="";		
		_scala="";
		_piano="";
		_interno="";
	}
	
	public String getBis() {
		return _bis;
	}
	public void setBis(String bis) {
		this._bis = bis;
	}
	public int getCodiceComune() {
		return _codiceComune;
	}
	public void setCodiceComune(int codiceComune) {
		this._codiceComune = codiceComune;
	}
	public int getCodiceVia() {
		return _codiceVia;
	}
	public void setCodiceVia(int codiceVia) {
		this._codiceVia = codiceVia;
	}
	public String getDescrizioneVia() {
		return _descrizioneVia;
	}
	public void setDescrizioneVia(String descrizioneVia) {
		this._descrizioneVia = descrizioneVia;
	}
	public int getNumeroCivico() {
		return _numeroCivico;
	}
	public void setNumeroCivico(int numeroCivico) {
		this._numeroCivico = numeroCivico;
	}

	public String getInterno() {
		return _interno;
	}

	public void setInterno(String interno) {
		this._interno = interno;
	}

	public String getPiano() {
		return _piano;
	}

	public void setPiano(String piano) {
		this._piano = piano;
	}

	public String getScala() {
		return _scala;
	}

	public void setScala(String scala) {
		this._scala = scala;
	}

	public String getDescrizioneComune() {
		return _descrizioneComune;
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this._descrizioneComune = descrizioneComune;
	}	
}

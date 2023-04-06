package it.comunecagliari.webdemo.system.services.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Database {
	private Statement _comando;
	private Connection _connessione;

	public Database() {
		super();
		this._comando = null;
		this._connessione = null;
	}

	public void connettiDatabase() throws Exception {
		// Read Resource data from Initial Context
		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/DemogDatasource");
		_connessione = ds.getConnection();
    	// Use the Connection to create a Statement object
    	_comando = _connessione.createStatement();
	  }

	public ResultSet eseguiQuery (String query) throws Exception {
		ResultSet risultato = _comando.executeQuery(query);
		return risultato;
	}

	public Statement getComando() {
		return _comando;
	}

	public Connection getConnessione() {
		return _connessione;
	}

	public void chiudiTutto() throws Exception {
		_comando.close();
		_connessione.close();
	}
}

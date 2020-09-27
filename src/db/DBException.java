package db;

public class DBException extends RuntimeException {

	/*
	 * Classe para tratamentos de exceção com o DataBase.
	 */
	
	private static final long serialVersionUID = 1L;
	
	public DBException(String msg) {
		super(msg);
	}

}

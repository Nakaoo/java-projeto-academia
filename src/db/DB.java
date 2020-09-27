package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Statement;

/*
 * Classe respons�vel pela conex�o com o banco de dados
 * Tendo em mente o modelo JDBC, isso � feito para separar bem as atividades de banco de dados.
 */
public class DB {
	
	private static Connection conn = null;
	
	//abrir conex�o
	public static Connection getConnection() {
		if (conn == null)
		{
			try {
				Properties props = loadProperties(); // pega o valor da fun��o
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		return conn;
	}
	//fechar conex�o
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	//carregar propriedades que est�o em outro arquivo
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
}
}

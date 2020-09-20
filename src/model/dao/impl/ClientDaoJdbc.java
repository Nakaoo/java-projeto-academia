

package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DBException;
import model.dao.ClientDao;
import model.entities.Client;

public class ClientDaoJdbc implements ClientDao{

	private Connection conn;
	
	public ClientDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Client obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO seller "
				+"(Name, Email) "
				+"VALUES "
				+"(?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error. No one line was affected");
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public void update(Client obj) {
				
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE seller "
				+"SET Name = ?, Email = ?"
				+"WHERE Id = ? "
			);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		
	PreparedStatement st = null;
	try {
		st = conn.prepareStatement(
			"DELETE FROM seller "
			+"WHERE Id = ? "
		);
		st.setInt(1, id);
		
		st.executeUpdate();
	}
	catch (SQLException e) {
		throw new DBException(e.getMessage());
	}
	finally {
		DB.closeStatement((Statement) st);
	}
		
	}

	@Override
	public Client findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM tbcliente ORDER BY Nome");
			rs = st.executeQuery();

			List<Client> list = new ArrayList<>();

			while (rs.next()) {
				Client obj = new Client();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement((Statement) st);
			DB.closeResultSet(rs);
		}
	}
}



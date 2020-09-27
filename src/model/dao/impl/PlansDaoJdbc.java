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
import db.DbIntegrityException;
import gui.util.Utils;
import model.dao.PlansDao;
import model.entities.Plans;

public class PlansDaoJdbc implements PlansDao{
	private Connection conn;

	public PlansDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Plans obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO tbplano " + "(Nome, Preco) " + "VALUES "

					+ "(?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setFloat(2, obj.getPrice());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Unexpected error. No one line was affected");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public void update(Plans obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE tbplano " +
				"SET NOME = ? ," +
				"PRECO = ? " +
				"WHERE ID = ?");

			st.setString(1, obj.getName());
			st.setDouble(2, obj.getPrice());
			st.setInt(3, obj.getId());

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
				"DELETE FROM tbplano WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public Plans findById(Integer id) {
		return null;
	}

	@Override
	public List<Plans> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM tbplano ORDER BY ID");
			rs = st.executeQuery();

			List<Plans> list = new ArrayList<>();

			while (rs.next()) {
				Plans obj = new Plans();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Nome"));
				obj.setPrice(Utils.tryParseToFloat(rs.getString("PRECO")));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement((Statement) st);
			DB.closeResultSet(rs);
		}
	}
}

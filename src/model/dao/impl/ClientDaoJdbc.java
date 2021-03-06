
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
import model.dao.ClientDao;
import model.entities.Client;

public class ClientDaoJdbc implements ClientDao {

	private Connection conn;

	public ClientDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Client obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO TBCLIENTE "
					+ "(NOME, ENDERECO, BAIRRO, ESTADO, DATANASCIMENTO, CPF, EMAIL, FKIDINSTRUTOR) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setString(3, obj.getBairro());
			st.setString(4, obj.getEstado());
			st.setDate(5, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(6, obj.getCpf());
			st.setString(7, obj.getEmail());
			st.setInt(8, obj.getInstructorName().getId());

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
	public void update(Client obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE TBCLIENTE " + "SET NOME = ? ," + "ENDERECO= ? ," + "BAIRRO = ? ,"
					+ "ESTADO = ? ," + "DATANASCIMENTO = ? ," + "CPF = ? ," + "EMAIL = ? ,"
					+ "FKIDINSTRUTOR = ? " + "WHERE ID = ?");

			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setString(3, obj.getBairro());
			st.setString(4, obj.getEstado());
			st.setDate(5, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(6, obj.getCpf());
			st.setString(7, obj.getEmail());
			st.setInt(8, obj.getInstructorName().getId());
			st.setInt(9, obj.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement((Statement) st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM tbcliente WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement((Statement) st);
		}

	}

	public Client findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT *  FROM TBCLIENTE INNER JOIN TBINSTRUTOR ON TBCLIENTE.FKIDINSTRUTOR= TBINSTRUTOR.ID ORDER BY TBCLIENTE.ID");
			rs = st.executeQuery();

			List<Client> list = new ArrayList<>();

			while (rs.next()) {
				Client obj = new Client();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setEndereco(rs.getString("endereco"));
				obj.setBairro(rs.getString("bairro"));
				obj.setEstado(rs.getString("estado"));
				obj.setDataNascimento(new java.util.Date(rs.getTimestamp("DataNascimento").getTime()));
				obj.setCpf(rs.getString("cpf"));
				obj.setEmail(rs.getString("email"));
				obj.setInstructor(rs.getString("TBINSTRUTOR.NOME"));
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

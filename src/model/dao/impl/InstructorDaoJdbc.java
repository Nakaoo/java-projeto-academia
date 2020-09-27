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
import model.dao.InstructorDao;
import model.entities.Instructor;
import model.entities.Plans;

public class InstructorDaoJdbc implements InstructorDao {

	private Connection conn;

	public InstructorDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Instructor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO TBINSTRUTOR "
					+ "(NOME, ENDERECO, BAIRRO, ESTADO, DATANASCIMENTO, CPF, EMAIL, SALARIO, FKIDPLANO) " + "VALUES "

					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setString(3, obj.getBairro());
			st.setString(4, obj.getEstado());
			st.setDate(5, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(6, obj.getCpf());
			st.setString(7, obj.getEmail());
			st.setDouble(8, obj.getSalario());
			st.setInt(9, obj.getPlanNome().getId());

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
	public void update(Instructor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE TBINSTRUTOR " + "SET NOME = ? ," + "ENDERECO= ? ," + "BAIRRO = ? ,"
					+ "ESTADO = ? ," + "DATANASCIMENTO = ? ," + "CPF = ? ," + "EMAIL = ? ," + "SALARIO = ? ,"
					+ "FKIDPLANO = ? " + "WHERE ID = ?");

			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setString(3, obj.getBairro());
			st.setString(4, obj.getEstado());
			st.setDate(5, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(6, obj.getCpf());
			st.setString(7, obj.getEmail());
			st.setDouble(8, obj.getSalario());
			st.setInt(9, obj.getPlanNome().getId());
			st.setInt(10, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM tbinstrutor WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement((Statement) st);
		}

	}

	@Override
	public Plans findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instructor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM TBINSTRUTOR INNER JOIN TBPLANO ON TBINSTRUTOR.FKIDPLANO = TBPLANO.ID");
			rs = st.executeQuery();

			List<Instructor> list = new ArrayList<>();

			while (rs.next()) {
				Instructor obj = new Instructor();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setEndereco(rs.getString("Endereco"));
				obj.setBairro(rs.getString("Bairro"));
				obj.setEstado(rs.getString("Estado"));
				obj.setEmail(rs.getString("Email"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setSalario(rs.getDouble("Salario"));
				obj.setPlanVin(rs.getString("TbPlano.nome"));
				obj.setDataNascimento(new java.util.Date(rs.getTimestamp("DataNascimento").getTime()));
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

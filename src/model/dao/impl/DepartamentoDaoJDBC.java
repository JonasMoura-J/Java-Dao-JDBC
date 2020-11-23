package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao{
	
	private Connection conn;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departamento obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department " + 
					"(Name) " + 
					"VALUES " + 
					"(?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			
			int linhasAlteradas = st.executeUpdate();
			
			if(linhasAlteradas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id =rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
				
			}else {
				throw new DbException("Erro inesperado, nenhuma linha afetada");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("UPDATE department " + 
					"SET Name = ?" + 
					"WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}catch(NullPointerException e) {
			throw new DbException("Id não encontrado");
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteByID(Integer id) {
		PreparedStatement st = null;
		
		try {
			if(findById(id) == null) {
				throw new DbException("Esse id de usuário não existe");
			}
			
			st = conn.prepareStatement("DELETE FROM department " + 
					"WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Departamento findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM department WHERE department.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departamento dep = InstanceDepartamento(rs);
				
				return dep;
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		return null;
		
	}

	@Override
	public List<Departamento> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM department");
					
			rs = st.executeQuery();
			
			List<Departamento> list = new ArrayList<>();
			
			while (rs.next()) {
				Departamento obj = new Departamento(rs.getInt("Id"),rs.getString("Name"));
				
				list.add(obj);
			}	
			return list;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Departamento InstanceDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNome(rs.getString("Name"));
		return dep;
	}
}

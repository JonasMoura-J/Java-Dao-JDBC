package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao{
	
	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Vendedor obj) {
		
	}

	@Override
	public void update(Vendedor obj) {
		
	}

	@Override
	public void deleteByID(Integer id) {
		
	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + 
					"FROM seller INNER JOIN department " + 
					"ON seller.DepartmentId = department.Id " + 
					"WHERE seller.Id = ?");
					
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departamento dep = InstanceDepartamento(rs);
				
				Vendedor obj = InstanceVendedor(rs, dep);
				
				return obj;
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private Vendedor InstanceVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor obj = new Vendedor();
		
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setSalario(rs.getDouble("BaseSalary"));
		obj.setNascimento(rs.getDate("BirthDate"));
		obj.setDepartamento(dep);
		
		return obj;
	}

	private Departamento InstanceDepartamento(ResultSet rs) throws SQLException {
		Departamento dep =new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> findAll() {
		return null;
	}

	@Override
	public List<Vendedor> findByDepartamento(Departamento dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + 
					"FROM seller INNER JOIN department " + 
					"ON seller.DepartmentId = department.Id " + 
					"WHERE DepartmentId = ? " + 
					"ORDER BY Name");
					
			st.setInt(1, dep.getId());
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				Departamento departamento = map.get(rs.getInt("DepartmentId"));
				
				if(departamento == null) {
					departamento = InstanceDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), departamento);
				}
				Vendedor obj = InstanceVendedor(rs, departamento);
				
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
}

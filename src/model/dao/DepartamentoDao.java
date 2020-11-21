package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {
	
	public void insert(Departamento obj);
	
	public void update(Departamento obj);
	
	public void deleteByID(Integer id);
	
	public Departamento findById(Integer id);
	
	public List<Departamento> findAll();
}

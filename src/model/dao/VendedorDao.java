package model.dao;

import java.util.List;

import model.entities.Vendedor;

public interface VendedorDao {
		
		public void insert(Vendedor obj);
		
		public void update(Vendedor obj);
		
		public void deleteByID(Integer id);
		
		public Vendedor findById(Integer id);
		
		public List<Vendedor> findAll();

}

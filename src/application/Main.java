package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Main {

	public static void main(String[] args) {
		
		VendedorDao vd = DaoFactory.createVendedorDao();
		
		System.out.println("===Test 1: Vendedor findById ===");
		
		Vendedor vend = vd.findById(3);
		
		System.out.println(vend);
		
		System.out.println("\n===Test 1: Vendedor findByDepartamento ===");
		
		Departamento dep = new Departamento(2, null);
		List<Vendedor> list = vd.findByDepartamento(dep);
		
		for(Vendedor v : list) {
			System.out.println(v);
		}
		
		System.out.println("\n===Test 3: Vendedor findAll ===");
		
		list = vd.findAll();
		
		for(Vendedor v : list) {
			System.out.println(v);
		}
	}

}

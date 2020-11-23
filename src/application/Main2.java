package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Main2 {

	public static void main(String[] args) {
		DepartamentoDao departamentoDao = DaoFactory.createDepartamentoDao();
		
		System.out.println("\n===Test 1: Departamento findAll ===");
		List<Departamento> list = departamentoDao.findAll();
		
		for(Departamento d : list) {
			System.out.println(d);
		}
		
//		System.out.println("\n===Test 2: Departamento insert ===");
//		Departamento dep = new Departamento(null, "Esporte");
//		
//		dd.insert(dep);
//		System.out.println("Inserido! novo id = " + dep.getId());
		
		System.out.println("\n===Test 3: Departamento findById ===");
		Departamento dep = departamentoDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n===Test 4: Departamento Update ===");
		dep = departamentoDao.findById(1);
		dep.setNome("Logística");
		departamentoDao.update(dep);
		System.out.println("Atualizado com sucesso!");
		
	}

}

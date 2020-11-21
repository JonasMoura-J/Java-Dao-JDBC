package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Main {

	public static void main(String[] args) {
		Departamento obj = new Departamento(1, "Livros");
		Vendedor vendedor = new Vendedor(21, "Jonas", "jonas@gmail.com", new Date(), 3000.0, obj);
		
		VendedorDao vd = DaoFactory.createVendedorDao();
		
		System.out.println(vendedor);
	}

}

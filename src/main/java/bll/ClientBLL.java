package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.ClientAgeValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

	private List<Validator<Client>> validators;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());
		validators.add(new ClientAgeValidator());
	}

	public Client findStudentById(int id) {
		Client st = ClientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The student with id =" + id + " was not found!");
		}
		return st;
	}

	public int insertStudent(Client student) {
		for (Validator<Client> v : validators) {
			v.validate(student);
		}
		return ClientDAO.insert(student);
	}
	
	public void updateClient(){
		ClientDAO.updateClient();
	}
	
	public void deleteClient(){
		ClientDAO.deleteClient();
	}
	
	public void displayClients(){
		ClientDAO.displayClients();
	}
}

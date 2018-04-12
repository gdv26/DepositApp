package bll;

import java.util.NoSuchElementException;

import dao.ProductDAO;
import model.Product;

public class ProductBLL {

	public Product findProductById(int id) {
		Product product = ProductDAO.findById(id);
		if (product == null) {
			throw new NoSuchElementException("The product with id =" + id + " was not found!");
		}
		return product;
	}
	
	public int insertProduct(Product product) {
		return ProductDAO.insert(product);
	}
	
	public void updateProduct(){
		ProductDAO.updateProduct();
	}
	
	public void deleteProduct(){
		ProductDAO.deleteProduct();
	}
}

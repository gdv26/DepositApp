package model;

public class Product {
	
	private int id;
	private String denumire;
	private int pret;
	private int cantitate;
	
	
	public Product(int id, String denumire, int pret, int cantitate){
		
		super();
		this.id = id;
		this. denumire = denumire;
		this.pret = pret;
		this.cantitate = cantitate;
	}
	
	public Product(String denumire, int pret, int cantitate){
		
		super();
		this.denumire = denumire;
		this.pret = pret;
		this.cantitate = cantitate;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getDenumire(){
		return denumire;
	}
	
	public void setDenumire(String denumire){
		this.denumire = denumire;
	}
	
	public int getPret(){
		return pret;
	}
	
	public void setPret(int pret){
		this.pret = pret;
	}
	
	public void setCantitate(int cantitate){
		this.cantitate = cantitate;
	}
	
	public int getCantitate(){
		return cantitate;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", denumire=" + denumire + ", pret=" + pret + ", cantitate=" + cantitate + "]";
	}
}

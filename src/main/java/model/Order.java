package model;

public class Order {
	
	private int id;
	private String numeClient;
	private String denumireProdus;
	private int cantitate;
	
	public Order(int id, String numeClient, String denumireProdus, int cantitate){
		
		super();
		this.id = id;
		this.numeClient = numeClient;
		this.denumireProdus = denumireProdus;
		this.cantitate = cantitate;
	}
	
	public Order(String numeClient, String denumireProdus, int cantitate){
		
		super();
		this.numeClient = numeClient;
		this.denumireProdus = denumireProdus;
		this.cantitate = cantitate;
	}
	
	public void setOrderId(int id){
		this.id = id;
	}
	
	public int getOrderId(){
		return id;
	}
	
	public void setNumeClient(String numeClient){
		this.numeClient = numeClient;
	}
	
	public String getNumeClient(){
		return numeClient;
	}
	
	public void setDenumireProdus(String denumireProdus){
		this.denumireProdus = denumireProdus;
	}
	
	public String getDenumireProdus(){
		return denumireProdus;
	}
	
	public void setCantitate(int cantitate){
		this.cantitate = cantitate;
	}
	
	public int getCantitate(){
		return cantitate;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", numeClient=" + numeClient + ", denumireProdus=" + denumireProdus + ", cantitate=" + cantitate + "]";
	}
}

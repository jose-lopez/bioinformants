package bioinformants.client.model.predictor;

//package ASIP.server;
public class Base {

	private String base;
	private String limitInf;
	private String limitSup;
	
	public void setBase(String base){
		this.base=base;
	}
	public void setLimits(String [] limits){
		this.limitInf=limits[0];
		this.limitSup=limits[1];
	}
	public String getBase(){
		return this.base;
	}
	public String getLimitInf(){
		return this.limitInf;
	}
	public String getLimitSup(){
		return this.limitSup;
	}
}

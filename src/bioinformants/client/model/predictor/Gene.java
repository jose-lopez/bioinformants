package bioinformants.client.model.predictor;

//package ASIP.server;
import java.util.*;
public class Gene {
	private String locus_tag;
	private String genes;
	private String exones;
	private Vector bases;
	private Vector exon;
	private boolean complement;
	private boolean enabled;
	
	Gene(){
		this.exones="";
		this.bases=new Vector(5,2);
		this.exon=new Vector(8,2);
	}
	public void setLocusTag(String locus_tag){
		this.locus_tag=locus_tag;
	}
	public void setGenes(String genes){
		this.genes=genes;
	}
	public void addGenes(String genes_add){
		this.genes=this.genes+genes_add;
	}
	public void setExones(String exones){
		this.exones=exones;
	}
	public String getLocusTag(){
		return this.locus_tag;
	}
	public String getGenes(){
		return this.genes;
	}
	public String getExones(){
		return this.exones;
	}
	public void addExones(String exon_add){
		this.exones=this.exones+exon_add;
	}
	// It does not have any sense, setBases by removing elements?. It looks like a resetBases.
	public void setBases(){
		this.bases.removeAllElements();
	}
	public boolean addBase(Base base){
		return this.bases.add(base);
	}
	public boolean addBase(Vector bases){
		for(int i=0;i<bases.size();i++){
			if(!this.addBase((Base)bases.elementAt(i))){
				return false;
			}
		}
		return true;
	}
	public Base getBases(int i){
		return (Base)this.bases.elementAt(i);
	}
	public int numBases(){
		return this.bases.size();
	}
	public void setExon(){
		this.exon.removeAllElements();
	}
	public boolean addExon(Base base){
		return this.exon.add(base);
	}
	public boolean addExon(Vector bases){
		for(int i=0;i<bases.size();i++){
			if(!this.addExon((Base)bases.elementAt(i))){
				return false;
			}
		}
		return true;
	}
	public Base getExon(int i){
		return (Base)this.exon.elementAt(i);
	}
	public int numExon(){
		return this.exon.size();
	}
	public boolean isComplement(){
		return this.complement;
	}
	public boolean setComplement(boolean comp){
		return this.complement=comp;
	}
	public boolean isEnabled(){
		return this.enabled;
	}
	public boolean setEnabled(boolean enb){
		return this.enabled=enb;
	}
}

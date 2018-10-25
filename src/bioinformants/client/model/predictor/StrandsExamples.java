package bioinformants.client.model.predictor;

//package ASIP.server;
import java.io.*;

//import javax.servlet.ServletException;
//import javax.servlet.http.*;
//import javax.servlet.*;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.net.URLConnection;


public class StrandsExamples {
   public void ejemplos(String GI, int cant_bases,String root_url){
	   try { 
		   	 int hebra=0;
		   	 
			 String directorio=root_url+"Data/"+GI+"/";
			 String hebras []={"Watson","Crick"};
			 char inihebra[]={'W','C'};
	   		while(hebra<2)
	   		{	
  				PrintStream NEW= new PrintStream(directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_ZE"+cant_bases+".txt");
	   			FileReader FileIn = new FileReader(directorio+hebras[hebra]+"/Ejemplos"+"/duplex"+inihebra[hebra]+"_ZE.txt"); 
	   			BufferedReader READ = new BufferedReader(FileIn);
	   			String line="";
	   			while ((line=READ.readLine()) != null) {	
	   				String almacenar ="";
	   				int index_p=line.lastIndexOf("p");
	   				if(index_p>((cant_bases*2)-2))
	   				{	almacenar=almacenar.concat("[");
	   					almacenar=almacenar.concat(line.substring(index_p-((cant_bases*2)-2)));
	   					NEW.println(almacenar);
	   				}
	   			}
	   			NEW.close();
	   			READ.close();
	   			//   			
	   			NEW= new PrintStream(directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_EZ"+cant_bases+".txt");
	   			FileIn = new FileReader(directorio+hebras[hebra]+"/Ejemplos"+"/duplex"+inihebra[hebra]+"_EZ.txt"); 
	   			READ = new BufferedReader(FileIn);
	   			line="";
	   			while ((line=READ.readLine()) != null) {	
	   				String almacenar ="";
	   				int index_p=line.lastIndexOf("p");
	   				if(line.length()>index_p+(cant_bases*2)+9)
	   				{	almacenar=almacenar.concat(line.substring(0,index_p+(cant_bases*2)+9));
	   					almacenar=almacenar.concat("]");
	   					NEW.println(almacenar);
	   				}
	   			}
	   			NEW.close();
	   			READ.close();
	   			//
	   			NEW= new PrintStream(directorio+hebras[hebra]+"/Ejemplos/tri"+inihebra[hebra]+"_IEZ"+cant_bases+".txt");
	   			FileIn = new FileReader(directorio+hebras[hebra]+"/Ejemplos/tri"+inihebra[hebra]+"_IEZ.txt"); 
	   			READ = new BufferedReader(FileIn);
	   			line="";
	   			while ((line=READ.readLine()) != null) {	
	   				String almacenar ="";
	   				int index_p=line.lastIndexOf("p");
	   				if(line.length()>index_p+(cant_bases*2)+9)
	   				{	almacenar=almacenar.concat(line.substring(0,index_p+(cant_bases*2)+9));
	   					almacenar=almacenar.concat("]");
	   					NEW.println(almacenar);
	   				}
	   			}
	   			NEW.close();
	   			READ.close();
	   			//
	   			NEW= new PrintStream(directorio+hebras[hebra]+"/Ejemplos/tri"+inihebra[hebra]+"_ZEI"+cant_bases+".txt");
	   			FileIn = new FileReader(directorio+hebras[hebra]+"/Ejemplos/tri"+inihebra[hebra]+"_ZEI.txt"); 
	   			READ = new BufferedReader(FileIn);
	   			line="";
	   			while ((line=READ.readLine()) != null) {	
	   				String almacenar ="";
	   				int index_p=line.indexOf("p");
	   				if(index_p>((cant_bases*2)-2))
	   				{	almacenar=almacenar.concat("[");
	   					almacenar=almacenar.concat(line.substring(index_p-((cant_bases*2)-2)));
	   					NEW.println(almacenar);
	   				}
	   			}
	   			NEW.close();
	   			READ.close();
	   			//
	   			hebra++;	   			
	   		}
       }
	   catch (Exception errormessage) {
			errormessage.printStackTrace();
		}	   
   }
}

package bioinformants.client.model.predictor;

//package ASIP.server;
import java.io.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.servlet.http.HttpSession;


public class DataEngineIn{
	Vector predicados=new Vector();
	public String datos_entrada(String GI,int cant_bases,String root_url,String username, String [] requestParam){
		String Experimentos_Reducido="";
			try { 
			   int futuro=0; //variable utilizada para cuando haya produccion de esas zonas
			   boolean b;
			   String cex="";
			   if((b = Boolean.parseBoolean(requestParam[6])) == true){
				   cex=cex.concat("z,");
			   }
			   if( Boolean.parseBoolean(requestParam[7])){
				   cex=cex.concat("cez,cie,cze,cei,");
			   }
			   if( Boolean.parseBoolean(requestParam[8])){
				   cex=cex.concat("l,");
			   }
			   if( Boolean.parseBoolean(requestParam[9])){
				   cex=cex.concat("r,");
			   }
			   double porcen[]= {1,0.8,0.2};
			   if(requestParam[10].compareTo(GI)==0){
				   porcen[0]= 0.5;porcen[1]=0.3;porcen[2]=0.2;
			   }
			   String data_uEIi="";
			   String data_uEId="";
			   String data_uEIs="";
			   String data_uIEi="";
			   String data_uIEd="";
			   String data_uIEp="";
			   String data_uIEr="";
			   String data_uIEs="";
			   String archivo;
			   String d_temp;
			   int numline;
			   int c;
			   int cl=0;
			   String tipo_iter []={"","test","resto"};
			   String directorio=root_url+"Data/"+GI+"/";
			   File dir= new File(root_url+username+"_temp");
		   	   dir.mkdir();
		   	   dir= new File(root_url+"knowledge_base_"+username);
		   	   dir.mkdir();
		   	   dir= new File(root_url+"knowledge_base_"+username+"/learned");
		   	   dir.mkdir();
		   	   dir= new File(root_url+username+"_temp/"+GI);
		   	   dir.mkdir();
			   String directorio_temp=root_url+username+"_temp/"+GI+"/";
			   String hebras []={"Watson","Crick"};
			   char inihebra[]={'W','C'};
			   int hebra=2;
			   int iteracion=2;
			   
			   if( Boolean.parseBoolean(requestParam[20])){
				   hebra=1;				  
			   }
			   if( Boolean.parseBoolean(requestParam[19])){
				   if(hebra==2)
					   iteracion=1;
				   hebra=0;				   
			   }				   
			   while(hebra<iteracion)
		   		{  dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]);
			   	   dir.mkdir();
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]);
			   	   dir.mkdir();
			   	   FileReader FileIn;
			   	   BufferedReader READ;
			   	   PrintStream IZQ = new PrintStream(directorio+"temp.txt");
			   	   IZQ.close();
			   	   PrintStream IZQ2=new PrintStream(directorio+"temp.txt");
			   	   IZQ2.close();			   	   
			   	   PrintStream IZQ3= new PrintStream(directorio+"temp.txt");
			   	   IZQ3.close();
			   	   PrintStream DER= new PrintStream(directorio+"temp.txt");
			   	   DER.close();
			   	   PrintStream SE;
			   	   String line;
			   	   int limite;
			   	   int i;
			   	   String cb;
//duplex_EI
			   	   if( Boolean.parseBoolean(requestParam[4])){
			   		if( Boolean.parseBoolean(requestParam[16])){
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//conservadosGT
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//senal_gt
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//Preparando ejemplos
		archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_EI.txt"; 
		FileIn = new FileReader(archivo);
		READ = new BufferedReader(FileIn);
		for(c=0;c<3;c++){	   	   	
			if(c==1 && requestParam[10].compareTo(GI)!=0){
				directorio=root_url+"Data/"+requestParam[10]+"/";
			}
				   archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_EI.txt";
				   numline=count_line(archivo);				   
			if((c==1 && requestParam[10].compareTo(GI)!=0)){	
				   FileIn = new FileReader(archivo);
				   READ = new BufferedReader(FileIn);
			}
			if( Boolean.parseBoolean(requestParam[17])){
				IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_EI_VI"+tipo_iter[c]+"c.pl");
				if(c==0)
					data_uEIi=data_uEIi.concat("duplex"+inihebra[hebra]+"_EI_VIc,");
			}
			if( Boolean.parseBoolean(requestParam[18]))	   {
				DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_EI_VD"+tipo_iter[c]+"c.pl");
				if(c==0)
					data_uEId=data_uEId.concat("duplex"+inihebra[hebra]+"_EI_VDc,");
			}
				   SE= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_EI_V"+tipo_iter[c]+"s.pl");
				   if(c==0)
						data_uEIs=data_uEIs.concat("duplex"+inihebra[hebra]+"_EI_Vs,");
		cl=0;
				   while (((line=READ.readLine()) != null)) {
					   if(c==2)
						   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
					   if( Boolean.parseBoolean(requestParam[17]))
						   IZQ.println(crear_line("cgti",line,"pgt","izq"));
					   if( Boolean.parseBoolean(requestParam[18]))
						   DER.println(crear_line("cgtd",line,"pgt","der"));
			   		SE.println(crear_line("senal_gt",line,"pgt",""));
			 cl++;
			  if(numline * porcen[c] < cl)
			   			break;
			  }
				   	if( Boolean.parseBoolean(requestParam[17]))
				   IZQ.close();
				   	if( Boolean.parseBoolean(requestParam[18]))
			   	   DER.close();			   	   
			   	   SE.close();
			if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
			   	READ.close();
		}  		   	
//Preparando contraejemplos
			   	   String cej[]= cex.split(",");//{"cez","cie","cze","l","r","z"};
			   	   limite= cej.length;
			   	   i=0;
			   	   cb="";
			   	   while(i<limite)
			   	   {	if(cej[i].compareTo("cei")!=0){
			   		   if(cej[i].compareTo("cez")==0||cej[i].compareTo("cie")==0||cej[i].compareTo("cze")==0||cej[i].compareTo("z")==0)cb="";else cb=String.valueOf(cant_bases);
			   		archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_EI/duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+".txt";
			   		FileIn = new FileReader(archivo);
					READ = new BufferedReader(FileIn);
					for(c=0;c<3;c++){	   	   	
						if(c==1 && requestParam[10].compareTo(GI)!=0){
							directorio=root_url+"Data/"+requestParam[10]+"/";
						}
						archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_EI/duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+".txt";
			   		   numline=count_line(archivo); 
			   		if(c==1 && requestParam[10].compareTo(GI)!=0){	
						   FileIn = new FileReader(archivo);
						   READ = new BufferedReader(FileIn);
					}
			   		if( Boolean.parseBoolean(requestParam[17])){
			   			IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_FI"+tipo_iter[c]+"c.pl");
			   			if(c==0)
							data_uEIi=data_uEIi.concat("duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_FIc,");
			   		}
			   		if( Boolean.parseBoolean(requestParam[18])){
			   			DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_FD"+tipo_iter[c]+"c.pl");
			   		   if(c==0)
			   			   data_uEId=data_uEId.concat("duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_FDc,");
			   		}
			   		SE= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_F"+tipo_iter[c]+"s.pl");
			   		if(c==0)
			   			   data_uEIs=data_uEIs.concat("duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_Fs,");
			   		 cl=0;
			   		   while (((line=READ.readLine()) != null)) {
			   			if(c==2)
							   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
			   			if( Boolean.parseBoolean(requestParam[17]))
			   				IZQ.println(crear_line(":-cgti",line,"pgt","izq"));
			   			if( Boolean.parseBoolean(requestParam[18]))
			   				DER.println(crear_line(":-cgtd",line,"pgt","der"));
			   			   	SE.println(crear_line(":-senal_gt",line,"pgt",""));
			   			 cl++;
			   			if(numline * porcen[c] < cl)
				   			break;
			   		   }
			   		if( Boolean.parseBoolean(requestParam[17]))
						   IZQ.close();
						   	if( Boolean.parseBoolean(requestParam[18]))
					   	   DER.close();			   	   
			   		   SE.close();
			   		if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
					   	READ.close();
			   	   }  }
			   		i++;
			   	   }}}
			   	   //
//  duplex_EZ
			   	   if(futuro==1){
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//conservadosPAR
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//Preparando ejemplos
			   	archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_EZ"+cant_bases+".txt";
			   	FileIn = new FileReader(archivo);
				READ = new BufferedReader(FileIn);
				for(c=0;c<3;c++){	   	   	
					if(c==1 && requestParam[10].compareTo(GI)!=0){
						directorio=root_url+"Data/"+requestParam[10]+"/";
					} 
			   	   archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_EZ"+cant_bases+".txt";
			   	   numline=count_line(archivo);
			   	if((c==1 && requestParam[10].compareTo(GI)!=0)){	
					   FileIn = new FileReader(archivo);
					   READ = new BufferedReader(FileIn);
				}
				   IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_EZ"+cant_bases+"_VI"+tipo_iter[c]+"c.pl");
				   DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_EZ"+cant_bases+"_VD"+tipo_iter[c]+"c.pl");
				   cl=0;
				   while ((line=READ.readLine()) != null) {
					   if(c==2)
						   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
			   		IZQ.println(crear_line("cpari",line,"p","izq"));
			   		DER.println(crear_line("cpard",line,"p","der"));
			   		cl++;
		   			if(numline * porcen[c] < cl)
			   			break;
			   	   }
			   	   IZQ.close();
			   	   DER.close();
			   	if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
				   	READ.close();
		   	   }  
//Preparando contraejemplos
			   	   String cej2 []= cex.split(",");//{"cei","cie","cze","l","r","z"};
			   	   limite= cej2.length;
			   	   i=0;
			   	   cb="";
			   	   while(i<limite)
			   	   {	if(cej2[i].compareTo("cez")!=0){
			   		   if(cej2[i].compareTo("cie")==0||cej2[i].compareTo("cei")==0)cb="";else cb=String.valueOf(cant_bases);
			   		archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_EZ/duplex"+inihebra[hebra]+cej2[i]+"_EZ"+cb+".txt";
			   		FileIn = new FileReader(archivo);
					READ = new BufferedReader(FileIn);
					for(c=0;c<3;c++){	   	   	
						if(c==1 && requestParam[10].compareTo(GI)!=0){
							directorio=root_url+"Data/"+requestParam[10]+"/";
						} 
			   		   archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_EZ/duplex"+inihebra[hebra]+cej2[i]+"_EZ"+cb+".txt";
			   		   numline=count_line(archivo); 
			   		if((c==1 && requestParam[10].compareTo(GI)!=0)){	
						   FileIn = new FileReader(archivo);
						   READ = new BufferedReader(FileIn);
					}
			   		   IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej2[i]+"_EZ"+cb+"_FI"+tipo_iter[c]+"c.pl");
			   		   DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej2[i]+"_EZ"+cb+"_FD"+tipo_iter[c]+"c.pl");
			   		cl=0;
			   		while ((line=READ.readLine()) != null) {
			   			if(c==2)
							   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
			   			IZQ.println(crear_line(":-cpari",line,"p","izq"));
			   			   	DER.println(crear_line(":-cpard",line,"p","der"));
			   			 cl++;
				   			if(numline * porcen[c] < cl)
					   			break;
			   		   }
			   		   IZQ.close();
			   		   DER.close();
			   		if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
					   	READ.close();
			   	   }  }
			   		   i++;
			   	   }}
			   	   //
 //duplex_IE
			   	   if( Boolean.parseBoolean(requestParam[5])){
			   		if( Boolean.parseBoolean(requestParam[11])||Boolean.parseBoolean(requestParam[14])|| Boolean.parseBoolean(requestParam[15])){
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
			   	   
			   	   String expaux="Experimentos_Reducido";
			   	   /*if( Boolean.parseBoolean(requestParam[11]))
			   		expaux=expaux.concat(expaux="conservadosAG,");
			   	   if( Boolean.parseBoolean(requestParam[14]))
			   		expaux=expaux.concat("pirimidinas,");
			   	   if( Boolean.parseBoolean(requestParam[15]))
			   		expaux=expaux.concat("ramificacion,");*/
			   	   String exp[]= expaux.split(",");
			   	   for(int j=0;j<exp.length;j++)
			   	   {	dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/"+exp[j]);
			   	   		dir.mkdir();
			   	   }
//senal_AG
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//Preparando ejemplos
			   	archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_IE.txt";
			   	FileIn = new FileReader(archivo);
				READ = new BufferedReader(FileIn);
				for(c=0;c<3;c++){	   	   	
					if(c==1 && requestParam[10].compareTo(GI)!=0){
						directorio=root_url+"Data/"+requestParam[10]+"/";
					} 
			   	   archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_IE.txt";
		   		   numline=count_line(archivo); 
		   		if((c==1 && requestParam[10].compareTo(GI)!=0)){	
					   FileIn = new FileReader(archivo);
					   READ = new BufferedReader(FileIn);
				}
		   		
		   		if( Boolean.parseBoolean(requestParam[12])){
				   IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_IE_VI"+tipo_iter[c]+"c.pl");
				   if(c==0)
						data_uIEi=data_uIEi.concat("duplex"+inihebra[hebra]+"_IE_VIc,");
				}
		   		if( Boolean.parseBoolean(requestParam[13])){
		   			DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_IE_VD"+tipo_iter[c]+"c.pl");
		   			if(c==0)
						data_uIEd=data_uIEd.concat("duplex"+inihebra[hebra]+"_IE_VDc,");
				}
		   		if( Boolean.parseBoolean(requestParam[14])){
		   			IZQ2= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_IE_V"+tipo_iter[c]+"p.pl");
		   			if(c==0)
		   				data_uIEp=data_uIEp.concat("duplex"+inihebra[hebra]+"_IE_Vp,");
		   		}
		   		if( Boolean.parseBoolean(requestParam[15])) {  
				   IZQ3= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_IE_V"+tipo_iter[c]+"r.pl");
				   if(c==0)
		   				data_uIEr=data_uIEr.concat("duplex"+inihebra[hebra]+"_IE_Vr,");
		   		}
				   SE= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_IE_V"+tipo_iter[c]+"s.pl");
				   if(c==0)
						data_uIEs=data_uIEs.concat("duplex"+inihebra[hebra]+"_IE_Vs,");
				   cl=0;
				   while ((line=READ.readLine()) != null) {
					   if(c==2)
						   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
					   if( Boolean.parseBoolean(requestParam[12]))
					   IZQ.println(crear_line("cagi",line,"pag","izq"));
					   if( Boolean.parseBoolean(requestParam[13]))
					   DER.println(crear_line("cagd",line,"pag","der"));
					   if( Boolean.parseBoolean(requestParam[14]))
					   IZQ2.println(crear_line("zona_rica_pirimidinas",line,"pag","izq"));
					   if( Boolean.parseBoolean(requestParam[15]))
					   IZQ3.println(crear_line("zona_ramifi",line,"pag","izq"));
			   		SE.println(crear_line("senal_ag",line,"pag",""));
			   		cl++;
		   			if(numline * porcen[c] < cl)
			   			break;
			   	   }
				   if( Boolean.parseBoolean(requestParam[12]))
				   IZQ.close();
				   if( Boolean.parseBoolean(requestParam[13]))
				   DER.close();
				   if( Boolean.parseBoolean(requestParam[14]))
			   	   IZQ2.close();
				   if( Boolean.parseBoolean(requestParam[15]))
			   	   IZQ3.close();
			   	   SE.close();
			   	if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
				   	READ.close();
		   	   }  
//Preparando contraejemplos
			   	   String cej3 []= cex.split(",");//{"cei","cez","cze","l","r","z"};
			   	    limite= cej3.length;
			   	    i=0;
			   	    cb="";
			   	   while(i<limite)
			   	   {	if(cej3[i].compareTo("cie")!=0){
			   		   if(cej3[i].compareTo("cez")==0||cej3[i].compareTo("cei")==0||cej3[i].compareTo("cze")==0||cej3[i].compareTo("z")==0)cb="";else cb=String.valueOf(cant_bases);
			   		archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_IE/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+".txt";
			   		FileIn = new FileReader(archivo);
					READ = new BufferedReader(FileIn);
					for(c=0;c<3;c++){	   	   	
						if(c==1 && requestParam[10].compareTo(GI)!=0){
							directorio=root_url+"Data/"+requestParam[10]+"/";
						} 
			   		   archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_IE/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+".txt";
			   		   numline=count_line(archivo); 
			   		if((c==1 && requestParam[10].compareTo(GI)!=0)){	
						   FileIn = new FileReader(archivo);
						   READ = new BufferedReader(FileIn);
					}
			   		if( Boolean.parseBoolean(requestParam[12])){
			   			IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_FI"+tipo_iter[c]+"c.pl");
			   			if(c==0)
							data_uIEi=data_uIEi.concat("duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_FIc,");
			   		}
			   		if( Boolean.parseBoolean(requestParam[13])){
			   			DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_FD"+tipo_iter[c]+"c.pl");
			   			if(c==0)
							data_uIEd=data_uIEd.concat("duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_FDc,");
			   		}
			   		if( Boolean.parseBoolean(requestParam[14])){
			   			IZQ2= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_F"+tipo_iter[c]+"p.pl");
			   			if(c==0)
							data_uIEp=data_uIEp.concat("duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_Fp,");
			   		}
			   		if( Boolean.parseBoolean(requestParam[15])){
			   			IZQ3= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_F"+tipo_iter[c]+"r.pl");
			   			if(c==0)
							data_uIEr=data_uIEr.concat("duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_Fr,");
			   		}
			   		   SE= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_F"+tipo_iter[c]+"s.pl");
			   		if(c==0)
						data_uIEs=data_uIEs.concat("duplex"+inihebra[hebra]+cej3[i]+"_IE"+cb+"_Fs,");
			   		cl=0;
			   		while ((line=READ.readLine()) != null) {
			   			if(c==2)
							   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
			   			if( Boolean.parseBoolean(requestParam[12]))
			   				IZQ.println(crear_line(":-cagi",line,"pag","izq"));
			   			if( Boolean.parseBoolean(requestParam[13]))
			   				DER.println(crear_line(":-cagd",line,"pag","der"));
			   			if( Boolean.parseBoolean(requestParam[14]))
			   				IZQ2.println(crear_line(":-zona_rica_pirimidinas",line,"pag","izq"));
			   			if( Boolean.parseBoolean(requestParam[15]))
			   				IZQ3.println(crear_line(":-zona_ramifi",line,"pag","izq"));
				   		SE.println(crear_line(":-senal_ag",line,"pag",""));
				   		cl++;
			   			if(numline * porcen[c] < cl)
				   			break;
			   		   }
			   		if( Boolean.parseBoolean(requestParam[12]))
						   IZQ.close();
						   if( Boolean.parseBoolean(requestParam[13]))
						   DER.close();
						   if( Boolean.parseBoolean(requestParam[14]))
					   	   IZQ2.close();
						   if( Boolean.parseBoolean(requestParam[15]))
					   	   IZQ3.close();
				   	   SE.close();
					if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
					   	READ.close();
			   	   }  }
			   		   i++;
			   	   }}}
			   	   //
//duplex_EZ
			   	   if( futuro==1){
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//conservadosATG
			   	   dir= new File(root_url+username+"_temp/"+GI+"/"+hebras[hebra]+"/Experimentos_Reducido");
			   	   dir.mkdir();
//Preparando ejemplos
			   	archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_ZE"+cant_bases+".txt";
			   	FileIn = new FileReader(archivo);
				READ = new BufferedReader(FileIn);
				for(c=0;c<3;c++){	   	   	
					if(c==1 && requestParam[10].compareTo(GI)!=0){
						directorio=root_url+"Data/"+requestParam[10]+"/";
					} 
					archivo=directorio+hebras[hebra]+"/Ejemplos/duplex"+inihebra[hebra]+"_ZE"+cant_bases+".txt";
		   		   numline=count_line(archivo); 
		   		if((c==1 && requestParam[10].compareTo(GI)!=0)){	
					   FileIn = new FileReader(archivo);
					   READ = new BufferedReader(FileIn);
				}
				   IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_ZE"+cant_bases+"_VI"+tipo_iter[c]+"c.pl");
				   DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+"_ZE"+cant_bases+"_VD"+tipo_iter[c]+"c.pl");
				   cl=0;
				   while ((line=READ.readLine()) != null) {
					   if(c==2)
						   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
			   		IZQ.println(crear_line("catgi",line,"patg","izq"));
			   		DER.println(crear_line("catgd",line,"patg","der"));
			   		cl++;
		   			if(numline * porcen[c] < cl)
			   			break;
			   	   }
			   	   IZQ.close();
			   	   DER.close();
			   	if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
				   	READ.close();
		   	   } 
//Preparando contraejemplos
			   	   String cej4 []= cex.split(",");//{"cei","cie","cez","l","r","z"};
			   	   limite= cej4.length;
			   	   i=0;
			   	   cb="";
			   	   while(i<limite)
			   	   {	if(cej4[i].compareTo("cze")!=0){
			   		   if(cej4[i].compareTo("cie")==0||cej4[i].compareTo("cei")==0)cb="";else cb=String.valueOf(cant_bases);
			   		archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_ZE/duplex"+inihebra[hebra]+cej4[i]+"_ZE"+cb+".txt";
			   		FileIn = new FileReader(archivo);
					READ = new BufferedReader(FileIn);
					for(c=0;c<3;c++){	   	   	
						if(c==1 && requestParam[10].compareTo(GI)!=0){
							directorio=root_url+"Data/"+requestParam[10]+"/";
						} 
						archivo=directorio+hebras[hebra]+"/Contraejemplos/duplex"+inihebra[hebra]+"_ZE/duplex"+inihebra[hebra]+cej4[i]+"_ZE"+cb+".txt";
			   		   numline=count_line(archivo); 
			   		if((c==1 && requestParam[10].compareTo(GI)!=0)){	
						   FileIn = new FileReader(archivo);
						   READ = new BufferedReader(FileIn);
					}
			   		   IZQ= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej4[i]+"_ZE"+cb+"_FI"+tipo_iter[c]+"c.pl");
			   		   DER= new PrintStream(directorio_temp+hebras[hebra]+"/Experimentos_Reducido/duplex"+inihebra[hebra]+cej4[i]+"_ZE"+cb+"_FD"+tipo_iter[c]+"c.pl");
			   		cl=0;
			   		while ((line=READ.readLine()) != null) {
			   			if(c==2)
							   line=line.substring(0,line.indexOf("p"))+line.substring(line.indexOf("p")+2,line.length());
			   			   	IZQ.println(crear_line(":-catgi",line,"patg","izq"));
			   			   	DER.println(crear_line(":-catgd",line,"patg","der"));
			   			 cl++;
				   			if(numline * porcen[c] < cl)
					   			break;
			   		   }
			   		   IZQ.close();
			   		   DER.close();
			   		if(c==2||(c==0 && requestParam[10].compareTo(GI)!=0))
					   	READ.close();
			   	   } }
			   		   i++;
			   	   }}
			   	   /*//Scriptssssss
			   	   //directorio_temp+hebras[hebra]+"/duplex"+inihebra[hebra]+"_EI/Experimentos_Reducido/duplex"+inihebra[hebra]+"_EI_VI.pl"
			   	   //directorio_temp+hebras[hebra]+"/duplex"+inihebra[hebra]+"_EI/Experimentos_Reducido/duplex"+inihebra[hebra]+cej[i]+"_EI"+cb+"_FI.pl");
			   	  d_temp=directorio_temp+hebras[hebra]+"/Experimentos_Reducido/";
			   	  session.setAttribute("ASIP.directorio",d_temp);
		   	  
			   	if( Boolean.parseBoolean(requestParam[12])){
			   		 
			   		session.setAttribute("ASIP.cagi",scripts_mineria(d_temp,"cagi.pl",root_url,data_uIEi,requestParam[21],requestParam[22]));
			   		  data_uIEi="";
			   		Experimentos_Reducido=Experimentos_Reducido.concat("cagi,");
				   }
			   	if( Boolean.parseBoolean(requestParam[13])){
			   		 
			   		  session.setAttribute("ASIP.cagd",scripts_mineria(d_temp,"cagd.pl",root_url,data_uIEd,requestParam[21],requestParam[22]));
			   		  data_uIEd="";
			   		Experimentos_Reducido=Experimentos_Reducido.concat("cagd,");
				   }
			   	if( Boolean.parseBoolean(requestParam[14])){
			   		 
			   		session.setAttribute("ASIP.pirimidinas",scripts_mineria(d_temp,"pirimidinas.pl",root_url,data_uIEp,requestParam[21],requestParam[22]));
			   		  data_uIEp="";
			   		Experimentos_Reducido=Experimentos_Reducido.concat("pirimidinas,");
				   }
			   	if( Boolean.parseBoolean(requestParam[15])){
			   		 
			   		session.setAttribute("ASIP.ramificacion",scripts_mineria(d_temp,"ramificacion.pl",root_url,data_uIEr,requestParam[21],requestParam[22]));
			   		  data_uIEr="";
			   		Experimentos_Reducido=Experimentos_Reducido.concat("ramificacion,");
				   }
			    if( Boolean.parseBoolean(requestParam[17])){
			   		
			   		  session.setAttribute("ASIP.cgti",scripts_mineria(d_temp,"cgti.pl",root_url,data_uEIi,requestParam[21],requestParam[22]));
			   		  data_uEIi="";
			   		  Experimentos_Reducido=Experimentos_Reducido.concat("cgti,");
				   }
			   	if( Boolean.parseBoolean(requestParam[18])){
			   		  
			   		  session.setAttribute("ASIP.cgtd",scripts_mineria(d_temp,"cgtd.pl",root_url,data_uEId,requestParam[21],requestParam[22]));
			   			data_uEId="";
			   			Experimentos_Reducido=Experimentos_Reducido.concat("cgtd,");
				   }
			   	//fase 2
			    if( Boolean.parseBoolean(requestParam[5])){
			    	session.setAttribute("ASIP.agn",scripts_mineria(d_temp,"agnlisto.pl",root_url,data_uIEs,requestParam[21],requestParam[22]));
		   			data_uIEs="";
		   			//Experimentos_Reducido=Experimentos_Reducido.concat("agn,");
				   }
				   if( Boolean.parseBoolean(requestParam[4])){
					   session.setAttribute("ASIP.gtn",scripts_mineria(d_temp,"gtnlisto.pl",root_url,data_uEIs,requestParam[21],requestParam[22]));
			   			data_uEIs="";
			   			//Experimentos_Reducido=Experimentos_Reducido.concat("gtn,");
				   }
			   	  //*/
				   hebra++;
		   		}
			   
		   }
		   catch (Exception errormessage) {
				errormessage.printStackTrace();
			}	   
		   return Experimentos_Reducido;
	   }
	public int count_line(String archivo){
		int numline=0;
		try{	   
	   RandomAccessFile randFile = new RandomAccessFile(archivo,"r");
	   long lastRec=randFile.length();
	   randFile.close();
	   FileReader FileIn = new FileReader(archivo); 
	   LineNumberReader num= new LineNumberReader(FileIn);
	   num.skip(lastRec);
	   System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+num.getLineNumber());
	   System.out.println(archivo);
	   numline=num.getLineNumber();
	   num.close();
	   }
	   catch (Exception errormessage) {
			errormessage.printStackTrace();
		}	
	   return numline;
	}
	public String crear_line(String experimento, String data, String senal, String direccion){
		 String line;
		 if (direccion.compareTo("")==0)
			 line= experimento+"("+data+","+senal+").";			 
		 else
			 line= experimento+"("+data+","+senal+","+direccion+").";
		 return line;
   }	
	public String scripts_mineria(String d_temp, String script, String root_url, String arch_lec,String r21, String r22){
		String nombres="";
		try{
			Pattern pattern; 
			Matcher matcher;
			String resultado;
			String abrir_script=root_url+"Scripts mineria/"+script;
			FileReader FileIn = new FileReader(abrir_script);
			BufferedReader READ = new BufferedReader(FileIn);
			String line="";
			PrintStream NEW= new PrintStream(d_temp+script);
			int test=0;
			int hasta=0;
			String new_arch []= arch_lec.split(",");
			hasta=new_arch.length;
			if(Boolean.parseBoolean(r22)&& !Boolean.parseBoolean(r21))
				test=1;
			if(!Boolean.parseBoolean(r22)&& Boolean.parseBoolean(r21))
				hasta=1;
			while ((line=READ.readLine()) != null) {
				NEW.println(line);
				if(script.compareTo("gtnlisto.pl")!=0&&script.compareTo("agnlisto.pl")!=0)
					if(line.contains(":-generalise")){
						line=line.replace(":-generalise(","");
						line=line.replace(")?","");					
						predicados.add(line);					
					}
				if(line.contains(":-modeh")||line.contains(":-modeb")){
					pattern = Pattern.compile("( ?[+-][a-zA-Z]+[,)])+");
					matcher = pattern.matcher(line);					
					if (matcher.find()) {			
						resultado=matcher.group();
						String campos[] = resultado.split(",");
						for(int h=0; h<campos.length;h++){							
							campos[h]=campos[h].replaceAll("[ +)-]","");
							if(!nombres.contains(campos[h]))
								nombres=nombres.concat(campos[h]+",");
						}
					}					
				}
				if(line.compareTo("%NUEVOS ARCHIVOS")==0){
					for(int i=0;i<new_arch.length;i++){
						NEW.println(":-consult("+new_arch[i]+")?");
					}					
				}				
				if(line.compareTo("%TEST ARCHIVOS")==0){
					for(int i=test;i<hasta;i++){
						NEW.println("%:-test("+new_arch[i].substring(0,new_arch[i].length()-1)+"test"+new_arch[i].substring(new_arch[i].length()-1)+")?");
					}					
				}
			}
			READ.close();
			NEW.close();
			if(script.compareTo("gtnlisto.pl")==0||script.compareTo("agnlisto.pl")==0){
				if(script.compareTo("gtnlisto.pl")==0)
					script="soportes_gt.pl";
				else
					script="soportes_ag.pl";
				abrir_script=root_url+"Scripts mineria/"+script;
				 FileIn = new FileReader(abrir_script);
				 READ = new BufferedReader(FileIn);
				 line="";
				 NEW= new PrintStream(d_temp+script);
				 while ((line=READ.readLine()) != null) {
					 NEW.println(line);
				 }
				 READ.close();
				 NEW.close();
			}
				
			
		}
		catch (Exception errormessage) {
			errormessage.printStackTrace();
		}	  
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+nombres);
		return nombres;
	}
}
//Accion,GI,if download,bases amount,mining EI, mining IE, 
//contraejem Z, change signal, mov left, mov rigth, GI Validate,
//Conserved AG, Wayup AG, Waydown AG, Pirimidinas AG, Ramification AG
//Conserved GT, Wayup GT, Waydown GT

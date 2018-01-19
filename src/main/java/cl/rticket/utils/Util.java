package cl.rticket.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import cl.rticket.model.RUT;

public class Util {
	
	
	public static boolean verificaRUT(String rut) {
		 	String dv = "";
			
		 	if(rut==null || rut.isEmpty()){
				return false;
			}else{
				if(rut.substring(0, 1).equalsIgnoreCase("P")){
					//pasaporte
					return true;
				}
				if (rut.matches("^([0-9]{1,8}-[0-9kK])")) {
					StringTokenizer str = new StringTokenizer(rut,"-");
					rut =  str.nextToken();
					dv =  str.nextToken();
				}else{
					return false;
				}
			}
		
		  int largo=rut.length()-1;
		
		  if(largo == 6){ 
		  	 rut = "0"+rut;
		  	 largo++;
		  }
		  if (largo < 6) {
		  	for(int i=largo;i<9;i++) {
		  		rut = rut+"0";
		  	}
		  }		  
		  int c=2; 
		  int s=0;
		  int resto; 
		  String tmp; 
		    for (int i=largo; i>-1; i--){
				 tmp=rut.charAt(i) +"";
				 s=s+Integer.parseInt(tmp)*c; 
				 c++; 
				  if (c>7)c=2; 
		    }			    
		    resto=s%11;
		    int ver=11-resto;		   
		    if(dv.equalsIgnoreCase("k") && ver == 10) return true;
		    if(dv.equalsIgnoreCase("k") && ver !=10) return false;
		    			    	
		    if(Integer.parseInt(dv)==0 && ver == 11) return true;
		    if(Integer.parseInt(dv)== ver) return true;
		    
		    return false;   
		} 
	


	public static String random() {
	    SecureRandom random = new SecureRandom();	 
	    return new BigInteger(130, random).toString(32);
	  
	}
	
	public static String randomNumber() {
		 Random randomGenerator = new Random();
		    int a = randomGenerator.nextInt(9);
		    int b = randomGenerator.nextInt(9);
		    int c = randomGenerator.nextInt(9);
		    int d = randomGenerator.nextInt(9);
		    //int e = randomGenerator.nextInt(9);
		    
		 return a+""+b+""+c+""+d;
	}
	
	public static RUT obtieneRUT(String rutIngresado) {
		RUT rut = new RUT();	
		
			if(rutIngresado.contains("-")) {
				//es cedula nueva
				String [] tmp = rutIngresado.trim().split("-");
				rut.setNumero(new Integer(tmp[0]));
				rut.setDv(tmp[1]);
			} else {
				//es cedula antigua
				rut.setNumero(new Integer(rutIngresado.trim().substring(0, rutIngresado.trim().length() - 1)));
				rut.setDv(rutIngresado.substring(rutIngresado.trim().length() - 1, rutIngresado.trim().length()).toUpperCase());
		    }
		
				
		return rut;
	}
	
	
	public static Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}
	
	public  static boolean validaFecha(String dateToValidate, String dateFromat) {		
			if(dateToValidate == null){
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
			sdf.setLenient(false);

			try {
				//if not valid, it will throw ParseException
				Date date = sdf.parse(dateToValidate);
				System.out.println(date);

			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
			return true;		
	}
	
	public static void main(String args[]) {
		//for(int i = 0 ; i < 10000 ; i++)
		  // System.out.println("---->13859176|"+ random());
		//Map<String, String> map = getQueryMap("https://portal.sidiv.registrocivil.cl/docstatus?RUN=23235193-4&type=CEDULA&serial=110719966&mrz=110719966810012882201281");
		//map.get("RUN");
		//System.out.println("--->"+map.get("RUN"));
		System.out.println("--->"+obtieneRUT("13859176-K    ").rutCompleto());
		System.out.println("--->"+obtieneRUT("13859176K    ").rutCompleto());
		System.out.println("--->"+obtieneRUT("13859176    ").rutCompleto());
		
		//System.out.println("--->"+obtieneRUT("AQWWWWQW").rutCompleto());
		//System.out.println("--->"+obtieneRUT("13.859.176-K    ").rutCompleto());
			
	}
	
	
}

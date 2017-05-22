package cl.rticket.utils;

import java.util.StringTokenizer;

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
				if (rut.matches("^([0-9]{7,8}-[0-9kK])")) {
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
}

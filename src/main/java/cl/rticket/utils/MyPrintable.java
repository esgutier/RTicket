package cl.rticket.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MyPrintable {
	
	public static class Impresora implements Printable {		 
		@Override
		  public int print(Graphics graphics, PageFormat pageFormat, 
			                int pageIndex) throws PrinterException {    
			                int result = NO_SUCH_PAGE;    
			                if (pageIndex == 0) {                    
			                Graphics2D g2d = (Graphics2D) graphics;                    
			                             
			                 
			                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
			                Font font = new Font("Arial",Font.BOLD,12);       
			                Font fontVS = new Font("Arial",Font.BOLD,10); 
			                Font fontFecha = new Font("Monospaced",Font.BOLD,10); 
			                Font fontSector = new Font("Arial",Font.BOLD,16); 
			                Font fontSocio = new Font("Monospaced",Font.BOLD,12); 
			                g2d.setFont(font);
			                Rectangle rec1 = new Rectangle(5,5,200,300);
			                g2d.draw(rec1);		                       			               
			                try {			        	
		                         int x=80 ;                                       
		                         int y=5;                                        
		                         int imagewidth=50;
		                         int imageheight=50;
		                         //BufferedImage read = ImageIO.read(getClass().getResource("/image/logo.gif")); para web
		                         BufferedImage read = ImageIO.read(new File("C:\\desarrollo\\logo_png.png"));
		                         g2d.drawImage(read,x,y,imagewidth,imageheight,null); 
		                         drawCenteredString(g2d,"V/S",rec1,70,fontVS);
		                         drawCenteredString(g2d,"BARCELONA",rec1,85,font);
		                         drawCenteredString(g2d,"Domingo 15 de Septiembre 2017",rec1,98,fontFecha);
		                         drawCenteredString(g2d,"15:30 Hrs.",rec1,107,fontFecha);
		                         drawCenteredString(g2d,"Estadio B. Nelson Oyarzún A.",rec1,115,fontFecha);
		                         g2d.drawLine(20, 120, 185, 120);   
		                         drawCenteredString(g2d,"PACIFICO",rec1,148,fontSector);
		                         drawCenteredString(g2d,"Socio",rec1,158,fontSocio);
		                         g2d.drawLine(20, 170, 185, 170); 
		                         
		                         
		                 		 try {
		                 			Hashtable hintMap = new Hashtable();
			                 		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
									BitMatrix byteMatrix = qrCodeWriter.encode("demo",BarcodeFormat.QR_CODE, 100, 100, hintMap);
									int matrixWidth = byteMatrix.getWidth();
									BufferedImage qr = new BufferedImage(matrixWidth, matrixWidth,BufferedImage.TYPE_INT_RGB);
									qr.createGraphics();
									Graphics2D grap = (Graphics2D) qr.getGraphics();
									grap.setColor(Color.WHITE);
									grap.fillRect(0, 0, matrixWidth, matrixWidth);
									// Paint and save the image using the ByteMatrix
									grap.setColor(Color.BLACK);

									for (int i = 0; i < matrixWidth; i++) {
										for (int j = 0; j < matrixWidth; j++) {
											if (byteMatrix.get(i, j)) {
												grap.fillRect(i, j, 1, 1);
											}
										}
									}
									
									
									g2d.drawImage(qr,50,175,100,100,null); 
									
									
								} catch (WriterException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                         
		                         
		                         //g2d.drawLine(10, y+60, 180, y+60);                          
		                                 } catch (IOException e) {
			        			e.printStackTrace();
			        		}
			               // Rectangle rec2 = new Rectangle(5,105,200,100);
			               // g2d.draw(rec2);	
		      		try{
			        /*Draw Header*/
		                    int y=80;
			             // g2d.drawString("ABC Shopping Complex", 30,y);  
			             // g2d.drawString("CopyWrite 2009-2014", 50,y+10);                 //shift a line by adding 10 to y value
			             // g2d.drawString(now(), 10, y+20);                                //print date
			            //  g2d.drawString("Cashier : admin", 10, y+30);  
			        		
			           
		            }
		            catch(Exception r){
		              r.printStackTrace();
		            }
		  
			                result = PAGE_EXISTS;    
			            }    
			            return result;    
		      } //fin print
		   }
	
	
	public static void drawCenteredString(Graphics2D g, String text, Rectangle rect, int y, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)	    
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	
	
	protected static double convert_CM_To_PPI(double cm) {            
        return toPPI(cm * 0.393600787);            
    }

    protected static double toPPI(double inch) {            
        return inch * 72d;            
    }
	
	public static PageFormat getPageFormat(PrinterJob pj){
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();    
             
           //     double middleHeight =total_item_count*1.0;  //dynamic----->change with the row count of jtable
            double headerHeight = 5.0;                  //fixed----->but can be mod
        	double footerHeight = 5.0;                  //fixed----->but can be mod
                
            double width = convert_CM_To_PPI(9);      //printer know only point per inch.default value is 72ppi
        	double height = convert_CM_To_PPI(headerHeight+4+footerHeight); 
            paper.setSize(width, height);
            paper.setImageableArea(
                            convert_CM_To_PPI(0.25), 
                            convert_CM_To_PPI(0.5), 
                            width - convert_CM_To_PPI(0.35), 
                            height - convert_CM_To_PPI(1));   //define boarder size    after that print area width is about 180 points*/
            
            pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
            pf.setPaper(paper);    
            
            return pf;
}
	
	public static void main(String args[]){
		MyPrintable ps=new MyPrintable();
		 
		       
		 PrinterJob pj = PrinterJob.getPrinterJob();
		 pj.setPrintable(new Impresora(),getPageFormat(pj));
		       try {
		    	  // for(int i = 0; i < 6 ; i++) {
		            pj.print();
		    	  // }
		            }
		        catch (PrinterException ex) {
		                ex.printStackTrace();
		            }
	}
}

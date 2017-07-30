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
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import cl.rticket.exception.ImpresoraNoDisponibleException;
import cl.rticket.model.Ticket;

public class ImpresionMasiva {
	
	private static final String PRINTER_NAME = "ticket";
	
	
	
	public static class MyPrintable implements Printable {	
		
		private Ticket ticket;
		
		private SimpleDateFormat formateador = new SimpleDateFormat("EEEEEEEEE dd 'de' MMMMM 'de' yyyy", new Locale("es","ES"));
		
		@Override
		  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {    
			                int result = NO_SUCH_PAGE;    
			                if (pageIndex == 0) {                    
			                Graphics2D g2d = (Graphics2D) graphics;                    
			                             
			                //ticket.print(); 
			                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
			                Font font = new Font("Arial",Font.BOLD,12);       
			                Font fontVS = new Font("Arial",Font.BOLD,10); 
			                Font fontFecha = new Font("Monospaced",Font.BOLD,9); 
			                Font fontSector = new Font("Arial",Font.BOLD,14); 
			                Font fontSocio = new Font("Monospaced",Font.BOLD,12); 
			                g2d.setFont(font);
			                Rectangle rec1 = new Rectangle(5,5,200,300);
			                g2d.draw(rec1);		                       			               
			                try {			        	
		                         int x=80 ;                                       
		                         int y=5;                                        
		                         int imagewidth=50;
		                         int imageheight=50;
		                        
		                         //BufferedImage read = ImageIO.read(getClass().getResource("../../../logo_png.png")); 
		                         BufferedImage read = ImageIO.read(new File("C:\\logo_png.png"));
		                         //BufferedImage read = ImageIO.read(new File("C:\\apache-tomcat-7.0.78\\logo_png.png"));
		                         /*System.out.println(read);
		                         System.out.println("---->"+read.getHeight());
		                         System.out.println("---->"+read.getWidth());*/
		                         //BufferedImage read = ImageIO.read(new File("C:\\desarrollo\\logo_png.png"));
		                         g2d.drawImage(read,x,y,imagewidth,imageheight,null); 		                         
		                         drawCenteredString(g2d,"V/S",rec1,70,fontVS);
		                         drawCenteredString(g2d,ticket.getRival(),rec1,85,font);
		                         drawCenteredString(g2d,(formateador.format(ticket.getFecha())).toUpperCase(),rec1,98,fontFecha);		                        
		                         drawCenteredString(g2d,ticket.getHora(),rec1,107,fontFecha);
		                         drawCenteredString(g2d,"Estadio B. Nelson Oyarzún A.",rec1,115,fontFecha);
		                         g2d.drawLine(20, 120, 185, 120);   
		                         drawCenteredString(g2d,ticket.getSector()+" "+ticket.getComentario(),rec1,148,fontSector);
		                         drawCenteredString(g2d,"Normal - $"+String.format("%,d", Integer.parseInt(ticket.getPrecio())),rec1,158,fontSocio);		                        
		                         g2d.drawLine(20, 170, 185, 170); 
		                         
		                         
		                 		 try {
		                 			Hashtable hintMap = new Hashtable();
			                 		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
									BitMatrix byteMatrix = qrCodeWriter.encode(""+ticket.getToken(),BarcodeFormat.QR_CODE, 100, 100, hintMap);
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
									drawCenteredString(g2d,""+ticket.getToken(),rec1,270,fontSocio);
									drawCenteredString(g2d,""+ticket.getSecuencia(),rec1,285,fontFecha);
		
								} catch (WriterException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                       
		                      } catch (IOException e) {
			        			e.printStackTrace();
			        		}
			             		      
			                result = PAGE_EXISTS;    
			            }    
			            return result;    
		      } //fin print
		public Ticket getTicket() {
			return ticket;
		}
		public void setTicket(Ticket ticket) {
			this.ticket = ticket;
		}
    }
	
	
	public static void drawCenteredString(Graphics2D g, String text, Rectangle rect, int y, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);	  
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;	  
	    g.setFont(font);
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
	private static PrintService findPrintService(String printerName,
			PrintService[] services) {
		for (PrintService service : services) {
			if (service.getName().equalsIgnoreCase(printerName)) {
				return service;
			}
		}
		return null;
	}
	
	public PrintService obtenerImpresoraService() {
		//verificar si esta disponible la impresora
				DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
				PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
				PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
				PrintService service = findPrintService(PRINTER_NAME, printService);
				return service;
	}
	
	public  void imprimirTicket(Ticket ticket, PrintService service) throws ImpresoraNoDisponibleException {
		
		//imprimir
		PrinterJob pj = PrinterJob.getPrinterJob();
		MyPrintable myPrintable = new MyPrintable();
		myPrintable.setTicket(ticket);
		try {
			pj.setPrintService(service);
			pj.setPrintable(myPrintable,getPageFormat(pj));
			pj.print();
		} catch (PrinterException e) {			
			throw new ImpresoraNoDisponibleException();
		}
		
		
	}
	
	public static void main(String args[]){
		ImpresionNominativa ps=new ImpresionNominativa();
		 
		/*       
		 PrinterJob pj = PrinterJob.getPrinterJob();
		 
		 pj.setPrintable(new MyPrintable(),getPageFormat(pj));
		       try {
		    	  // for(int i = 0; i < 6 ; i++) {
		            pj.print();
		    	  // }
		            }
		        catch (PrinterException ex) {
		                ex.printStackTrace();
		            }
		       
		   */
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
		PrintService service = findPrintService("Zewwbra", printService);
		System.out.println(service);
		PrinterJob pj = PrinterJob.getPrinterJob();
		try {
			pj.setPrintService(service);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
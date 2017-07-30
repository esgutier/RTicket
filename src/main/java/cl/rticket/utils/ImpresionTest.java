package cl.rticket.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import cl.rticket.exception.ImpresoraNoDisponibleException;

public class ImpresionTest {

private static final String PRINTER_NAME = "ticket";
	
	public static class MyPrintable implements Printable {	

		@Override
		 public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {    
            int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    
            Graphics2D g2d = (Graphics2D) graphics;                    
                         
            //ticket.print(); 
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
            Font font = new Font("Arial",Font.BOLD,14);       
            
            g2d.setFont(font);
            Rectangle rec1 = new Rectangle(5,5,200,100);
            g2d.draw(rec1);		                       			                                          
            drawCenteredString(g2d,"IMPRESORA OK!",rec1,70,font);      
            result = PAGE_EXISTS;    
        }    
        return result;    
        } //fin print

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
            double headerHeight = 1.0;                  //fixed----->but can be mod
        	double footerHeight = 5.0;                  //fixed----->but can be mod
                
            double width = convert_CM_To_PPI(9);      //printer know only point per inch.default value is 72ppi
        	double height = convert_CM_To_PPI(headerHeight+4+footerHeight); 
            paper.setSize(width, height);
            /*paper.setImageableArea(
                            convert_CM_To_PPI(0.25), 
                            convert_CM_To_PPI(0.5), 
                            width - convert_CM_To_PPI(0.35), 
                            height - convert_CM_To_PPI(1));  */
            paper.setImageableArea(
                    convert_CM_To_PPI(0.0), 
                    convert_CM_To_PPI(0.0), 
                    width - convert_CM_To_PPI(0.35), 
                    height - convert_CM_To_PPI(1));  
            
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
	
	public  void imprimirTest() throws ImpresoraNoDisponibleException {
		
		//imprimir
		PrintService service = this.obtenerImpresoraService();
		PrinterJob pj = PrinterJob.getPrinterJob();
		MyPrintable myPrintable = new MyPrintable();
		
		try {
			pj.setPrintService(service);
			pj.setPrintable(myPrintable,getPageFormat(pj));
			pj.print();
		} catch (PrinterException e) {			
			throw new ImpresoraNoDisponibleException();
		}
		
		
	}
	
}

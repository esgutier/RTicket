package cl.rticket.utils;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
//https://github.com/picharras/print-with-Java/blob/master/Ticket.java
public class PrinterService implements Printable {
	
	private static final String PRINTER_NAME = "zebra";
	private static final byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33};
	private static final byte[] INITIALIZE_PRINTER = {0x1B,0x40};
	private static final byte[] CTL_LF          = {0x0a};   
	private static final byte[] HW_INIT         = {0x1b,0x40};    
	
	private static final byte[] LINE_SPACE_24   = {0x1b,0x33,24}; // Set the line spacing at 24
    private static final byte[] LINE_SPACE_30   = {0x1b,0x33,30}; // Set the line spacing at 30
	
	private static final byte[] TXT_BOLD_ON     = {0x1b,0x45,0x01};
	private static final byte[] TXT_BOLD_OFF    = {0x1b,0x45,0x00};
	private static final byte[] TXT_NORMAL      = {0x1b,0x21,0x00}; // Normal text
	private static final byte[] TXT_2HEIGHT     = {0x1b,0x21,0x10}; // Double height text
	private static final byte[] TXT_ALIGN_CT    = {0x1b,0x61,0x01}; // Centering
	private static final byte[] TXT_ALIGN_LT    = {0x1b,0x61,0x00}; // Left justification
	private static final byte[] TXT_ALIGN_RT    = {0x1b,0x61,0x02}; // Right justification
	private static final byte[] TXT_4SQUARE     = {0x1b,0x21,0x30}; // Quad area text
	
	private static final byte[] BARCODE_TXT_OFF = {0x1d,0x48,0x00}; // HRI printBarcode chars OFF
	private static final byte[] BARCODE_TXT_ABV = {0x1d,0x48,0x01}; // HRI printBarcode chars above
	private static final byte[] BARCODE_TXT_BLW = {0x1d,0x48,0x02}; // HRI printBarcode chars below
	private static final byte[] BARCODE_TXT_BTH = {0x1d,0x48,0x03}; // HRI printBarcode chars both above and below
	private static final byte[] BARCODE_FONT_A  = {0x1d,0x66,0x00}; // Font type A for HRI printBarcode chars
	private static final byte[] BARCODE_FONT_B  = {0x1d,0x66,0x01}; // Font type B for HRI printBarcode chars
	private static final byte[] BARCODE_HEIGHT  = {0x1d,0x68,0x64}; // Barcode Height [1-255]
	private static final byte[] BARCODE_WIDTH   = {0x1d,0x77,0x03}; // Barcode Width  [2-6]
	private static final byte[] BARCODE_UPC_A   = {0x1d,0x6b,0x00}; // Barcode type UPC-A
	private static final byte[] BARCODE_UPC_E   = {0x1d,0x6b,0x01}; // Barcode type UPC-E
	private static final byte[] BARCODE_EAN13   = {0x1d,0x6b,0x02}; // Barcode type EAN13
	private static final byte[] BARCODE_EAN8    = {0x1d,0x6b,0x03}; // Barcode type EAN8
	private static final byte[] BARCODE_CODE39  = {0x1d,0x6b,0x04}; // Barcode type CODE39
	private static final byte[] BARCODE_ITF     = {0x1d,0x6b,0x05}; // Barcode type ITF
	private static final byte[] BARCODE_NW7     = {0x1d,0x6b,0x06}; // Barcode type NW7
	
	public List<String> getPrinters(){
		
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		
		PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		
		List<String> printerList = new ArrayList<String>();
		for(PrintService printerService: printServices){
			printerList.add( printerService.getName());
		}
		
		return printerList;
	}

	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		if (page > 0) { // We have only one page, and 'page' is zero-based 
			return NO_SUCH_PAGE;
		}
 System.out.println("acacaca");
	
		 // User (0,0) is typically outside the imageable area, so we must
		 // translate by the X and Y values in the PageFormat to avoid clipping
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		 //Now we perform our rendering 

		g.setFont(new Font("Roman", 0, 8));
		g.drawString("Hello world !", 0, 10);

		return PAGE_EXISTS;
	}

	public void printString(String text) {
		
		// find the printService of name printerName
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		PrintService service = findPrintService(PRINTER_NAME, printService);

		DocPrintJob job = service.createPrintJob();

		try {

			byte[] bytes;

			// important for umlaut chars
			bytes = text.getBytes("CP437");

			Doc doc = new SimpleDoc(bytes, flavor, null);
			

			
			job.print(doc, null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void printBytes(byte[] bytes) {
		
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		PrintService service = findPrintService(PRINTER_NAME, printService);

		DocPrintJob job = service.createPrintJob();

		try {

			Doc doc = new SimpleDoc(bytes, flavor, null);

			job.print(doc, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private PrintService findPrintService(String printerName,
			PrintService[] services) {
		for (PrintService service : services) {
			if (service.getName().equalsIgnoreCase(printerName)) {
				return service;
			}
		}

		return null;
	}
	



	public static void main(String[] args) {

		PrinterService printerService = new PrinterService();
		
		System.out.println(printerService.getPrinters());
				
		//print some stuff
		printerService.printBytes(HW_INIT);
		printerService.printBytes(INITIALIZE_PRINTER);
		printerService.printBytes(TXT_ALIGN_CT);
		printerService.printBytes(TXT_2HEIGHT);
		printerService.printBytes(TXT_BOLD_ON);
		printerService.printString("ÑUBLENSE\n");
		printerService.printBytes(TXT_BOLD_OFF);
		printerService.printString("V/S\n");
		printerService.printString("BARCELONA F.C.\n\n\n\n\n\n\n\n\n");
		/*
		try {
			BufferedImage image = ImageIO.read(new File("C:\\desarrollo\\logo.bmp"));
			System.out.println("---------->"+image.getHeight());
			printImage(image,printerService);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}*/
		
		
		
		/*printerService.printBytes(TXT_BOLD_OFF);
		printerService.printBytes(TXT_2HEIGHT);
		printerService.printBytes(TXT_ALIGN_LT);		
		printerService.printString("\n\n Testing ÑUBLENSE EPSON POS \n\n");*/
		
		printerService.printBytes(LINE_SPACE_24);
		printerService.printBytes(CTL_LF);
		
		//printerService.printBytes(TXT_4SQUARE);
		//printerService.printString("\n\n Testing ÑUBLENSE EPSON POS \n\n\n\n\n\n");
		
		//test barcode printerService.printBytes();
		/*printerService.printBytes(BARCODE_HEIGHT);
		printerService.printBytes(BARCODE_WIDTH);
		printerService.printBytes(BARCODE_TXT_OFF);
		printerService.printBytes(BARCODE_EAN13);
		printerService.printString("123456");*/
		//printerService.printBytes();
		//printerService.printBytes();
		//printerService.printBytes(HW_INIT);
		
		
		

		// cut that paper!
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };

		printerService.printBytes(cutP);
		
	
	}
	
	private static void printImage(BufferedImage image, PrinterService printerService) {
        Image img = new Image();
        int[][] pixels = img.getPixelsSlow(image);
        for (int y = 0; y < pixels.length; y += 24) {
        	printerService.printBytes(LINE_SPACE_24);
        	printerService.printBytes(SELECT_BIT_IMAGE_MODE);
        	printerService.printBytes(new byte[]{(byte)(0x00ff & pixels[y].length), (byte)((0xff00 & pixels[y].length) >> 8)});
            for (int x = 0; x < pixels[y].length; x++) {
            	printerService.printBytes(img.recollectSlice(y, x, pixels));
            }

            printerService.printBytes(CTL_LF);
        }
        printerService.printBytes(CTL_LF);
        printerService.printBytes(LINE_SPACE_30);
    }
	
	
	
	



}


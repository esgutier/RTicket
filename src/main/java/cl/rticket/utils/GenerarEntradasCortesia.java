package cl.rticket.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import cl.rticket.model.Ticket;
import cl.rticket.model.Usuario;

public class GenerarEntradasCortesia {
	
	private static Font NORMAL = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL);
	private static Font PARTIDO = new Font(Font.FontFamily.HELVETICA, 15,Font.BOLD);
	private static Font FECHA = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL);
	private static Font SECTOR = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD);
	private static Font FOOTER = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
	
	public static ByteArrayOutputStream generaPDF(String file, ArrayList<Ticket> tickets, Usuario usuario) {
		 
		Document document = null; 
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		try {
			float left = 5;
	        float right = 5;
	        float top = 20;
	        float bottom = 0;
	        document = new Document(PageSize.A4, left, right, top, bottom);
			PdfWriter.getInstance(document, baosPDF);
			document.open();
			for(Ticket ticket: tickets) {
				tablaTicketBase(document, ticket, usuario);
				document.newPage();
			}
			document.close();
 
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return baosPDF;
 
	}
	
	private static void tablaTicketBase(Document document, Ticket ticket, Usuario usuario) throws DocumentException {
		 
		 PdfPTable tableSuperior = new PdfPTable(1);
		 float[] columnWidths = {7,3};
		 PdfPTable table = new PdfPTable(columnWidths);
		 
		 
		 PdfPCell cellTitulo = new PdfPCell( new Phrase("Ticket de Cortesía - "+ticket.getNombres().toUpperCase(),NORMAL));
		 cellTitulo.setColspan(2);	
		 cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cellTitulo.setBorder(0);
		 
		 
		 PdfPCell cellPartido= new PdfPCell( new Phrase(usuario.getNombreEquipo()+" V/S " + ticket.getRival(), PARTIDO));
		 cellPartido.setColspan(2);	
		 cellPartido.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cellPartido.setBorder(0);
		 
		 PdfPCell cellLugar= new PdfPCell( new Phrase(usuario.getNombreEstadio() != null ? usuario.getNombreEstadio(): "", FECHA));
		 cellLugar.setColspan(2);
		 cellLugar.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cellLugar.setBorder(0);
		 
		 SimpleDateFormat formateador = new SimpleDateFormat("EEEEEEEEE dd 'de' MMMMM 'de' yyyy", new Locale("es","ES"));
		 PdfPCell cellFecha= new PdfPCell( new Phrase(formateador.format(ticket.getFecha()).toUpperCase() +"    Hora: " +ticket.getHora(), FECHA));
		 cellFecha.setColspan(2);
		 cellFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cellFecha.setBorder(0);
		 
		 
		 BarcodeQRCode barcodeQRCode = new BarcodeQRCode(ticket.getToken(), 500, 500, null);
		 
		 Image codeQrImage = barcodeQRCode.getImage();
		 //Chunk chunk = new Chunk(codeQrImage, 20, -50);
		 Chunk chunk = new Chunk(codeQrImage, 30, -40);
		 PdfPCell cellQR     = new PdfPCell();
		 cellQR.setBorder(0);
		 cellQR.setHorizontalAlignment(Element.ALIGN_CENTER);
		 //cellQR.setImage(codeQrImage);
		 cellQR.addElement(chunk);
		 
		 PdfPCell cellSector = new PdfPCell();
		 cellSector.setBorder(0);
		 cellSector.addElement(tablaSector(document, ticket.getSector()));
		 
		 PdfPCell cellFooter = new PdfPCell(new Phrase("Ticket al portador. Se requerirá cédula de identidad en el acceso al estadio\n", FOOTER));
		 cellFooter.setColspan(2);
		 cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cellFooter.setBorder(0);
		 
		 LineSeparator line = new LineSeparator();
	     line.setOffset(-2);
	   
	     PdfPCell cellSeparador = new PdfPCell();
	     cellSeparador.setColspan(2);
	     cellSeparador.setBorder(0);
	     cellSeparador.addElement(line);
	     
		 
		 table.addCell(cellTitulo);
		 table.addCell(cellPartido);
		 table.addCell(cellLugar);
		 table.addCell(cellFecha);
		 
		 table.addCell(cellBlank(2));
		 table.addCell(cellSeparador);
		 table.addCell(cellBlank(2));
		
		 table.addCell(cellSector);
		 table.addCell(cellQR);
		 table.addCell(cellBlank(2));
		
		 table.addCell(cellSeparador);
		 table.addCell(cellBlank(2));
		 
		 table.addCell(cellFooter);
		 tableSuperior.addCell(table);
		 tableSuperior.addCell(cellBlank(1));
		 document.add(tableSuperior);
	}
	
	private static PdfPTable tablaSector(Document document, String sector) {
		 PdfPTable table = new PdfPTable(1);
		 PdfPCell cellLabelSector = new PdfPCell(new Phrase("SECTOR", NORMAL));
		 cellLabelSector.setBorder(0);
		 cellLabelSector.setHorizontalAlignment(Element.ALIGN_CENTER);
		 PdfPCell cellSector =  new PdfPCell(new Phrase(sector, SECTOR));
		 cellSector.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cellSector.setBorder(0);
		 table.addCell(cellLabelSector);
		 table.addCell(cellSector);
		 return table;
		 
	}
	
	private static PdfPCell cellBlank(int colspan) {
		PdfPCell cellBlank = new PdfPCell(new Phrase(""));
		cellBlank.setBorder(0);
		cellBlank.setColspan(colspan);
		cellBlank.setFixedHeight(22f);
		return cellBlank;
		
	}

}

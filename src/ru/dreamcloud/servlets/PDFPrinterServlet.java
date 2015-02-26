package ru.dreamcloud.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.GreekList;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PDFPrinterServlet
 */
/*@WebServlet("/services/print")*/
public class PDFPrinterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String tempContentDir;
	private List<Object> listToPrint;
	private String contextpath;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PDFPrinterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		contextpath = context.getRealPath("/");
	    
		
		readProperties(request);
		if (initAccess(session)) {
			if(session.getAttribute("listToPrint") != null){
				listToPrint = (List<Object>)session.getAttribute("listToPrint");
			}
			printToPDF(tempContentDir, "print.pdf", response);
			showPDF(new File(tempContentDir + "print.pdf"), response);
		} else {
			response.sendRedirect("/profile/login");
		}
	}

	private void printToPDF(String filePath, String fileName,
			HttpServletResponse response) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath+fileName));

			document.open();
			//document.add(new Paragraph("Привет Мир! Hello World!",getFont()));
			document.add(createTable());
			document.close();

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "inline;filename=" + fileName);
			response.setHeader("Accept-Ranges", "bytes");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Font getFont() throws DocumentException, IOException{
		BaseFont opensans = BaseFont.createFont(contextpath+"bootstrap/v3/template/fonts/open-sans/OpenSans-Regular-webfont.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(opensans, 12);
		return font;
	}
	
	private PdfPTable createTable() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell;       
        if(!listToPrint.isEmpty()){
	        for (Object listItem : listToPrint) {
	        	/*Class c = listItem.getClass(); 
	        	Method[] methods = c.getMethods(); 
	        	for (Method method : methods) {
	        	    System.out.println("Имя: " + method.getName()); 
	        	    System.out.println("Возвращаемый тип: " + method.getReturnType().getName()); 
	        	 
	        	    Class[] paramTypes = method.getParameterTypes(); 
	        	    System.out.print("Типы параметров: "); 
	        	    for (Class paramType : paramTypes) { 
	        	        System.out.print(" " + paramType.getName()); 
	        	    } 
	        	    System.out.println(); 
	        	}*/ 
	        	PatientHistory phItem = (PatientHistory)listItem;
	            cell = new PdfPCell(new Phrase(phItem.getPatient().getFullname(),getFont()));
	            table.addCell(cell);
			}
        }
        return table;
    }
	
	private void showPDF(File pdfFile, HttpServletResponse response)
			throws IOException {
		FileInputStream fis = new FileInputStream(pdfFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ServletOutputStream sos = response.getOutputStream();
		byte[] buffer = new byte[2048];
		while (true) {
			int bytesRead = bis.read(buffer, 0, buffer.length);
			if (bytesRead < 0) {
				break;
			}
			sos.write(buffer, 0, bytesRead);
			sos.flush();
		}
		sos.flush();
		bis.close();
	}
	
	private void readProperties(HttpServletRequest request){
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String filename = contextpath+"/WEB-INF/labels/general.properties";
			input = new FileInputStream(filename);
			prop.load(input);			
			tempContentDir = prop.getProperty("tempContentDir");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Boolean initAccess(HttpSession session) {
		Boolean result = false;
		String jsessionid = session.getId();
		CommonUserInfo userInfo = (CommonUserInfo)session.getAttribute("userInfo");
		if (userInfo == null) {
			result = false;
		} else {
			if (userInfo.getSessionid().equalsIgnoreCase(jsessionid)) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
}

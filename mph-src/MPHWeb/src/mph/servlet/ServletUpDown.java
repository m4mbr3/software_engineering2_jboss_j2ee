package mph.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import java.io.*;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.DiskFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUpload;

import mph.session.GetBytesFromFile;
import mph.entity.Deliverable;
import mph.entity.Group;
import mph.remote.SessionProjectRemote;
import mph.remote.SessionUpDownRemote;


/**
 * Servlet implementation class ServletUpDown
 */
public class ServletUpDown extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletUpDown() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String to = request.getParameter("to");

		if (to.equals("upload"))
			this.upload(request, response);
		if (to.equals("downloadSingle"))
			this.downloadSingle(request, response);
		if (to.equals("getZip"))
			this.getZip(request, response);
	}
	
	public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
}

	
	private void getZip(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		long idDel;
		long idGroup;
		try{
		if(request.getParameter("idDel")!= null)
			{ idDel = Long.parseLong(request.getParameter("idDel"));}
		else
			{idDel= 0;}
		}catch(Exception e)
		{
			idDel = 0;
		}
		try{
		if(request.getParameter("idGroup")!= null)
			{ idGroup = Long.parseLong(request.getParameter("idGroup"));}
		else
			{ idGroup=0;}
		}
		catch(Exception e)
		{
			idGroup=0;
		}
		try{
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();
			
			Object ref2 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionPro = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref2, SessionProjectRemote.class);
			
			if((idDel!=0) && (idGroup!=0)){
				List<mph.entity.File> listFil = sessionPro.getGroupFile(idGroup);
				Deliverable deliverable = sessionPro.getDeliv(idDel);
				
				
				 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			     ZipOutputStream zos = new ZipOutputStream(baos);
			     
			     for(int i=0; i < listFil.size(); i++)
	             {
					long id = listFil.get(i).getDeliverable().getIdDeliverable();
	                if(id == idDel){   
	                	ZipEntry entry = new ZipEntry((listFil.get(i).getIdFile())+listFil.get(i).getFileName()); 
	                	entry.setSize(listFil.get(i).getCod().length);
	     		        try{
	     		        	zos.putNextEntry(entry);
	     		        	zos.write(listFil.get(i).getCod());
	         		        zos.closeEntry();
	     		        }
	     		        catch(ZipException e)
	     		        {
	     		        	
	     		        }
	     		        
	     		        
	                	}
	             }
				zos.close();
				//session.setAttribute("zip", baos.toByteArray());
				response.setContentType("application/zip");
				response.setContentLength((int) baos.toByteArray().length);
				response.setHeader("Content-Disposition",
						"inline; filename="+deliverable.getDeliverableName()+".zip");
				ServletOutputStream out = response.getOutputStream();
				out.write(baos.toByteArray());
				out.flush();
				out.close();
				//redirect("/groupPageProf.jsp", request, response);
			}
			else if(idGroup == 0)
			{
				Deliverable deliverable = sessionPro.getDeliv(idDel);
				List<mph.entity.File> listFil = sessionPro.getFileDel(idDel);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			     ZipOutputStream zos = new ZipOutputStream(baos);
			     
			     for(int i=0; i < listFil.size(); i++)
	             {
					
	                   
	                	ZipEntry entry = new ZipEntry((listFil.get(i).getIdFile())+listFil.get(i).getFileName()); 
	                	entry.setSize(listFil.get(i).getCod().length);
	     		        try{
	     		        	zos.putNextEntry(entry);
	     		        	zos.write(listFil.get(i).getCod());
	         		        zos.closeEntry();
	     		        }
	     		        catch(ZipException e)
	     		        {
	     		        	
	     		        }
	     		        
	     		        
	                	
	             }
				zos.close();
				//session.setAttribute("zip", baos.toByteArray());
				response.setContentType("application/zip");
				response.setContentLength((int) baos.toByteArray().length);
				response.setHeader("Content-Disposition",
						"inline; filename="+deliverable.getDeliverableName()+".zip");
				ServletOutputStream out = response.getOutputStream();
				out.write(baos.toByteArray());
				out.flush();
				out.close();
				//redirect("/groupPageProf.jsp", request, response);
				
			}
			else
			{
				List<mph.entity.File> listFil = sessionPro.getGroupFile(idGroup);
				
				 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			     ZipOutputStream zos = new ZipOutputStream(baos);
			     
			     for(int i=0; i < listFil.size(); i++)
	             {
					long id = listFil.get(i).getDeliverable().getIdDeliverable();
	                  
	                	ZipEntry entry = new ZipEntry((listFil.get(i).getIdFile())+listFil.get(i).getFileName()); 
	                	entry.setSize(listFil.get(i).getCod().length);
	     		        try{
	     		        	zos.putNextEntry(entry);
	     		        	zos.write(listFil.get(i).getCod());
	         		        zos.closeEntry();
	     		        }
	     		        catch(ZipException e)
	     		        {
	     		        	
	     		        }
	     		        
	     		        
	                	
	             }
				zos.close();
				//session.setAttribute("zip", baos.toByteArray());
				response.setContentType("application/zip");
				response.setContentLength((int) baos.toByteArray().length);
				response.setHeader("Content-Disposition",
						"inline; filename=group"+idGroup+".zip");
				ServletOutputStream out = response.getOutputStream();
				out.write(baos.toByteArray());
				out.flush();
				out.close();
				//redirect("/groupPageProf.jsp", request, response);
			}
		}
			
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void upload(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		Group gr = (Group) session.getAttribute("GROUP");
		long idGroup = gr.getIdGroup();
		long idDel = Long.parseLong(request.getParameter("idDel"));
		String tipo = request.getParameter("type");

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionUpDownRemote");
			SessionUpDownRemote ud = (SessionUpDownRemote) PortableRemoteObject
					.narrow(ref1, SessionUpDownRemote.class);

			boolean isMultipart = FileUpload.isMultipartContent(request);
			File savedFile = null;
			if (isMultipart) {
				DiskFileUpload upload = new DiskFileUpload();

				@SuppressWarnings("rawtypes")
				List items = upload.parseRequest(request);
				@SuppressWarnings("rawtypes")
				Iterator itr = items.iterator();

				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (!item.isFormField()) {
						File fullFile = new File(item.getName());
						savedFile = new File(getServletContext().getRealPath(
								"/"), fullFile.getName());
						item.write(savedFile);
					} else {
						InputStream input = item.getInputStream();
						BufferedReader bin = new BufferedReader(
								new InputStreamReader(input));
					}

				}
			}
			if (savedFile.length() > 100000000) {
				session.setAttribute("dimFile", "DimensioneFileEccessiva");
				return;
			}
			String name = savedFile.getName();
			byte[] fileByte = GetBytesFromFile.getBytesFromFile(savedFile);
			ud.upload(name, fileByte, tipo, idGroup, idDel);
			redirect("ServletGroup?to=createGroupPage&idGroup=" + idGroup,
					request, response);
			return;
		} catch (Exception e) {
			redirect("ServletGroup?to=createGroupPage&idGroup=" + idGroup,
					request, response);
			e.printStackTrace();
		}
		redirect("ServletGroup?to=createGroupPage&idGroup=" + idGroup, request,
				response);
		return;
	}

	protected void downloadSingle(HttpServletRequest request,
			HttpServletResponse response) {
		long idFile = Long.parseLong(request.getParameter("idFile"));
		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionUpDownRemote");
			SessionUpDownRemote ud = (SessionUpDownRemote) PortableRemoteObject
					.narrow(ref1, SessionUpDownRemote.class);
			byte[] cod = ud.downloadSingleFile(idFile);
			String type = ud.getExtension(idFile);
			if (type.equals("pdf")) {
				response.setContentType("application/pdf");
			}
			if (type.equals("rar")) {
				response.setContentType("application/zip");
			}
			if (type.equals("jpg")) {
				response.setContentType("application/jpg");
			}
			response.setContentLength((int) cod.length);
			response.setHeader("Content-Disposition", "inline; filename=test");
			ServletOutputStream out = response.getOutputStream();
			out.write(cod);
			out.flush();
			out.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void removeFile(HttpServletRequest request,
			HttpServletResponse response) {
		long idFile = Long.parseLong(request.getParameter("idFile"));
		long idGroup = Long.parseLong(request.getParameter("idGroup"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionUpDownRemote");
			SessionUpDownRemote ud = (SessionUpDownRemote) PortableRemoteObject
					.narrow(ref1, SessionUpDownRemote.class);
			ud.removeFile(idFile);
			redirect("ServletGroup?to=createGroupPage&idGroup=" + idGroup,
					request, response);
		} catch (Exception e) {
		}
	}
}

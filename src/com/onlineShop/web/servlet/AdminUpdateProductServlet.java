package com.onlineShop.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.onlineShop.domain.Category;
import com.onlineShop.domain.Product;
import com.onlineShop.service.AdminService;
import com.onlineShop.utils.CommonUtils;

public class AdminUpdateProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.将上传的文件存到服务器磁盘上。
		//2.收集表单数据，并封装成一个Product实体
				
			
		Product product = new Product();
		Map<String,Object>map = new HashMap<String,Object>();	//用map便于接收表单信息，并封装到实体中
		
		try {
			//上传文件
			//1.创建磁盘文件
			DiskFileItemFactory factory = new DiskFileItemFactory();
			String path_temp = this.getServletContext().getRealPath("/products/temp");
			factory.setRepository(new File(path_temp));
			factory.setSizeThreshold(1024*1024);
			//2.创建核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			//判断是否是文件上传
			//if(upload.isMultipartContent(request)){
			//是文件上传项
			//3.得到文件项集合并遍历
			List<FileItem> fileItemList = upload.parseRequest(request);
			for(FileItem item:fileItemList){
				boolean isFormField = item.isFormField();
				if(isFormField){
					//是普通的表单项
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					//封装数据
					map.put(fieldName,fieldValue);
				}else{
					//是文件上传项
					//上传文件
					String path_upload = this.getServletContext().getRealPath("/products/upload");
					String fileName = item.getName();
					//如果上传文件的情况
					if(fileName != ""){
						String pimage = "products/upload/" + fileName;
						InputStream in = item.getInputStream();
						OutputStream out = new FileOutputStream(path_upload + "/" + fileName);//输入流是文件，不是文件夹，所以要加上文件名称
						IOUtils.copy(in, out);
						in.close();
						out.close();
						//item.delete();
						//封装数据
						map.put("pimage",pimage);	//上传文件会覆盖原有的pimage；同样，如果不添加文件，就用原来的pimage
					}
					//不上传文件也可以
				}
			}
			
			//封装数据到实体中
			BeanUtils.populate(product, map);
			
			//封装剩下的数据
			AdminService service = new AdminService();
			Category category = new Category();
			category.setCid((String)map.get("cid"));
			product.setCategory(category);
			product.setPdate(new Date());	//更新时间
			
			
			//封装完成，传入数据到service，更新product
			boolean isSuccess = service.updateProduct(product);
			//System.out.println("isSuccess = " + isSuccess);
			
			response.sendRedirect(request.getContextPath() + "/admin?method=findProductList");
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
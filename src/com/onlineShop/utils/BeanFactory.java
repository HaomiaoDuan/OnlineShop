package com.onlineShop.utils;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class BeanFactory {

	//解耦的含义： 某些层的实现方法可以进行随意的更换。
		//（举例：三层架构中，dao层的实现根据数据库的类型mysql/oracle，实现的方法就要有多套）
	//面向接口：
		//实现可以变，但是接口不变，一个接口对应多个实现。
		//在不修改java源码的情况下，写好的多个实现可以通过修改配置文件的形式，进行切换。
	//面向接口编程的过程：
		//传入id的是接口的名称，然后根据从bean.xml配置文件中读取对应于该接口不同的实现类
		//然后通过解析出的类名，用反射的方法 创建其对象（接口中有方法定义），进而调用相关方法，来实现层之间的解耦。
	public static Object getBean(String id){
		
		//生产对象----根据清单生产---即配置文件
		//配置文件----含有每个对象的一些生产的细节
		try {
			//解析配置文件
			//1.创建解析器
			SAXReader saxReader = new SAXReader();
			//2.获得文档对象--bean.xml在src下
			//通过class文件的类加载器去获取相对位置的xml文件的路径
			String path = BeanFactory.class.getClassLoader().getResource("bean.xml").getPath();
			Document document = saxReader.read(path);
			//3.获得元素---参数是xpath规则
			//node分为elemet，text，attribute。可以把node强转为element
			Element element = (Element)document.selectSingleNode("//bean[@id='" + id + "']");	//Xpath的函数和规则
				//<bean id="adminService" class="com.onlineShop.service.impl.AdminServiceImpl"></bean>
			String className = element.attributeValue("class");		//className="com.onlineShop.service.impl.AdminServiceImpl"
			//使用反射创建对象
			Class clazz = Class.forName(className);
			Object object = clazz.newInstance();
			return object;
			
			/*//3.获得根元素
			Element rootElement = document.getRootElement();
			//4.获得子元素
			List<Element> elements = rootElement.elements();
			//5.遍历子元素，获取想要的元素
			for (Element element : elements) {
				if("bean".equals(element.getName())){
					Element beanElement = element.element("bean");
					String beanId = beanElement.attributeValue("id");
					String beanClass = beanElement.attributeValue("class");
					System.out.println(beanId);
					System.out.println(beanClass);
				}
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

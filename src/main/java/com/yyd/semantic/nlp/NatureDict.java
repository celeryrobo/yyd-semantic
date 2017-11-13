package com.yyd.semantic.nlp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yyd.semantic.common.FileUtils;

public class NatureDict {
	private static Map<String,ArrayList<String>> natures = new HashMap<String,ArrayList<String>>();

	static {
		load();
//		for(Map.Entry<String, ArrayList<String>> entry:natures.entrySet()) {
//			 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
//		}
	}
	
	public static List<String> getServices(String nature){
		return natures.get(nature);
	}
		
	@SuppressWarnings("unchecked")
	private static void load() {
		String filePath = null;		
		String classRootPath = FileUtils.getResourcePath();
		filePath = classRootPath +"nlp/natures.xml";
			
		Element root = null;
		//加载文件
		try {
			root = LoadFile(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Iterator<Element> it = root.elementIterator("nature"); it.hasNext();) {
		        Element elem = it.next();		        
		        ParseNature(elem);	
		    }
		
	}
	
	 private static Element LoadFile(String filePath) throws Exception{  
        //创建SAXReader对象  
        SAXReader reader = new SAXReader();  
        //读取文件 转换成Document  
        Document document = reader.read(new File(filePath));  
        //获取根节点元素对象  
        Element root = document.getRootElement();  
        //遍历  
        //listNodes(root); 
        return root;
	 }
	 
	 @SuppressWarnings("unchecked")
	private static void ParseNature(Element node){
		if(null == node){
			return;
		}
		String natureName = null;
		ArrayList<String> services = new ArrayList<String>();
		
		for (Iterator<Element> it = node.elementIterator(); it.hasNext();) {
	        Element element = it.next();
	        // do something
	        if(element.getName().equals("name")){   
	        	natureName = element.getText();
	        }
	        else if(element.getName().equals("service")){ 
	        	services.add(element.getText());
	        }		       
	    }		
		
		if(null != natureName) {
			natures.put(natureName, services);
		}
			
	}
		 

	 
	 
}

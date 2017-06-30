package com.lenovo.weixin.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lenovo.weixin.beans.ItemBean;
import com.lenovo.weixin.beans.NewsMessageBean;
import com.lenovo.weixin.beans.TextMessageBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	//解析xml
	/* xml文件格式
			 <xml>
			 <ToUserName><![CDATA[toUser]]></ToUserName>
			 <FromUserName><![CDATA[fromUser]]></FromUserName> 
			 <CreateTime>1348831860</CreateTime>
			 <MsgType><![CDATA[text]]></MsgType>
			 <Content><![CDATA[this is a test]]></Content>
			 <MsgId>1234567890123456</MsgId>
			 </xml>
		 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> resolverXML(String xmlStr) throws IOException, DocumentException{
		//存放解析结果的Map
		Map<String,String> messageMap = new HashMap<>();
		
		//获取输入流
		//InputStream reqIn = request.getInputStream();
		
		//获取Document对象
		SAXReader saxR = new SAXReader();
		Document doc = saxR.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
	
		
		//获取xml根节点
		Element root = doc.getRootElement();
		
		//获取所有子节点
		List<Element> elList = root.elements();
		
		//遍历所有节点
		for (Element element : elList) {
			messageMap.put(element.getName(), element.getText());
		}
		
//		//释放资源
//		if(reqIn != null){
//			reqIn.close();
//		}
		
		return messageMap;
	}
	
	 private static XStream xstream = new XStream(new XppDriver() {  
	        public HierarchicalStreamWriter createWriter(Writer out) {  
	            return new PrettyPrintWriter(out) {  
	                // 对所有xml节点的转换都增加CDATA标记  
	                boolean cdata = true;  
	  
	                @SuppressWarnings("rawtypes")
					public void startNode(String name, Class clazz) {  
	                    super.startNode(name, clazz);  
	                }  
	  
	                protected void writeText(QuickWriter writer, String text) {  
	                    if (cdata) {  
	                        writer.write("<![CDATA[");  
	                        writer.write(text);  
	                        writer.write("]]>");  
	                    } else {  
	                        writer.write(text);  
	                    }  
	                }  
	            };  
	        }  
	    });  
	 
	//将TextMessageBean转换为XML
	public static String textMeassageToXML(TextMessageBean textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	public static String newsMessageToXML(NewsMessageBean newsMessageBean){
		xstream.processAnnotations(new Class[]{NewsMessageBean.class,ItemBean.class}); 
		return xstream.toXML(newsMessageBean);
	}
}

package fr.umlv.parsor;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Parsor {
	
	public static void main(String[] args) throws JDOMException, IOException {
		System.out.println(parserXML("stage1.xml","planet"));
	}
	
	public static int parserXML(String fileName,String typeObject) throws JDOMException, IOException{
		SAXBuilder sxb = new SAXBuilder();
		Document document;
		document = sxb.build(new File(fileName));
		Element racine = document.getRootElement();
		Iterator<Element> object = racine.getChildren(typeObject).iterator();
		Element courant;
		int density = 0;
		while(object.hasNext()){
			courant=object.next();
			density = (int) (Float.parseFloat(courant.getAttributeValue("total"))*Float.parseFloat(courant.getAttributeValue("density")));			
		}
		return density;
	}
}

package fr.umlv.parsor;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Parsor {
	
	public static void main(String[] args) throws JDOMException, IOException {
		parserXML("stage1.xml");
	}
	
	static void parserXML(String fileName) throws JDOMException, IOException{

		SAXBuilder sxb = new SAXBuilder();
		Document document;
		document = sxb.build(new File(fileName));
		Element racine = document.getRootElement();
		Iterator<Element> planet = racine.getChildren("planet").iterator();
		Element courant;
		while(planet.hasNext()){
			courant=planet.next();
			System.out.println("Total : "+courant.getAttributeValue("total"));
			System.out.println("Density : "+courant.getAttributeValue("density"));
			System.out.println("Radius : "+courant.getChildText("radius"));
			System.out.println("X : "+courant.getChildText("x"));
			System.out.println("Y : "+courant.getChildText("y"));
		}
		
//		Iterator<Element> bomb = racine.getChildren("bomb").iterator();
//		while(bomb.hasNext()){
//			courant=bomb.next();
//			System.out.println("Total : "+courant.getAttributeValue("total"));
//			System.out.println("Density : "+courant.getAttributeValue("density"));
//		}
		
	}
}

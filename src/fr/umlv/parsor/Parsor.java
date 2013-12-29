package fr.umlv.parsor;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Parsor {
	
	/**
	 * The class is used to parse Object to create a level.
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	
	public static void main(String[] args) throws JDOMException, IOException {
		System.out.println(parserXML("stage1.xml","ennemi"));
		int wave = parserWave("stage1.xml");
		for(int i=0; i<wave;i++){
			StringBuilder b = new StringBuilder("ennemi");
			b.append(i+1);
			System.out.println(parserXML("stage1.xml",b.toString()));
		}
	}
	/**
	 * Parse the custom XML file.
	 * @param fileName the name of the file
	 * @param typeObject the type of object to parse
	 * @return the density of an object
	 * @throws JDOMException
	 * @throws IOException
	 */
	
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
	
	public static int parserWave(String fileName) throws JDOMException, IOException{
		SAXBuilder sxb = new SAXBuilder();
		Document document;
		document = sxb.build(new File(fileName));
		Element racine = document.getRootElement();
		int size = Integer.parseInt(racine.getChildText("wave"));
		return size;
	}
}

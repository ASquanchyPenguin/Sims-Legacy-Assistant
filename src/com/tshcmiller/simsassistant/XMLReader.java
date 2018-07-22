package com.tshcmiller.simsassistant;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

	private Document xmlFile;
	
	public XMLReader(String path) {
		this.xmlFile = getXMLDocument(path);
	}
	
	/**
	 * <p>Finds the attribute associated with a certain node.</p>
	 * @param node the node to search
	 * @param attribute the desired attribute
	 * @return the value of the attribute
	 */
	public String getAttribute(String node, String attribute) {
		Node currentNode = getTopNode(node);
		Element element = (Element) currentNode;
		
		return element.getAttribute(attribute);
	}
	
	/**
	 * <p>Returns a list of child nodes of the specified parent node.</p>
	 * @param rootElement the parent node
	 * @return the child nodes
	 */
	public ArrayList<String> getNodeList(String rootElement) {
		ArrayList<String> elements = new ArrayList<String>();
		
		Node root = getTopNode(rootElement);
		NodeList commands = root.getChildNodes();
		
		Node currentNode;
		Element currentElement;
		for (int i = 0; i < commands.getLength(); i++) {
			currentNode = commands.item(i);
			
			if (currentNode.getNodeName().contains("#text")) {
				continue;
			}
			
			currentElement = (Element) currentNode;
			elements.add(currentElement.getNodeName());
		}
		
		return elements;
	}
	
	/**
	 * <p>Returns the specified node</p>
	 * @param node the name of the node
	 * @return the node itself
	 */
	private Node getTopNode(String node) {
		NodeList nodes = xmlFile.getElementsByTagName(node);
		
		return nodes.item(0);
	}
	
	/**
	 * <p>Loads and creates an Document to read an XML file.</p>
	 * @param path the location of the XML file
	 * @return the document created to read the file
	 */
	private Document getXMLDocument(String path) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLReader.class.getResourceAsStream(path));
			document.normalize();
			
			return document;
		} catch (Exception e) {
			System.err.println("Failed to load XML at " + path);
			e.printStackTrace();
		}
		
		return null;
	}
}

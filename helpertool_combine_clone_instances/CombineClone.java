import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class CombineClone {

  public static void main(String argv[]) {

  	System.out.println(argv[0]);

    try {


		File fXmlFile = new File(argv[0]);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList duplicationlist = doc.getElementsByTagName("duplication");
		Node root = doc.getElementsByTagName("pmd-cpd").item(0);

		System.out.println("----------------------------");

		for(int i=0; i < duplicationlist.getLength(); i++) {
			Node duplication = duplicationlist.item(i);
			Element element = (Element) duplication;

			String codefragment = element.getElementsByTagName("codefragment").item(0).getTextContent();
			System.out.print(i+" ");

			for(int j=i+1; j < duplicationlist.getLength(); j++) {
				Node currentNode = duplicationlist.item(j);
				Element el = (Element) currentNode;

				if(codefragment.equals(el.getElementsByTagName("codefragment").item(0).getTextContent())) {

					NodeList files = el.getElementsByTagName("file");
					moveFiles(files, duplication);

					root.removeChild(currentNode);
					j--;
					System.out.println("List length: "+duplicationlist.getLength());
				}
				
			}

 		System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

		}
		writeXMLFile(doc, "generatedoutput.xml");
	//}
    } catch (Exception e) {
	e.printStackTrace();
    }




  }

  // Moves files from one node to a node duplication (if they do not yet exist)
  public static void moveFiles(NodeList files, Node duplication) { 

  	for(int x=files.getLength()-1; x>=0; x--) { // Because appendChild MOVES the node, so getLength() is -1
		if(!nodeContainsFile(files.item(x), duplication)) {
			duplication.appendChild(files.item(x));
		}
		
	}
  }

  // Check if a node (duplication) already has a specific file
  public static Boolean nodeContainsFile(Node fileNode, Node duplicationNode) { 
  	Element fileEl = (Element) fileNode;
  	String line = fileEl.getAttribute("line");
  	String path = fileEl.getAttribute("path");

  	Element duplicationEl = (Element) duplicationNode;
  	NodeList duplicationFilesList = duplicationEl.getElementsByTagName("file");
  	
  	for(int x=0; x<duplicationFilesList.getLength(); x++) { // Because appendChild MOVES the node, so getLength() is -1
		Element dupEl = (Element) duplicationFilesList.item(x);
		String dupLine = dupEl.getAttribute("line");
  		String dupPath = dupEl.getAttribute("path");

		if(dupLine.equals(line) && dupPath.equals(path)) {
			return true;
		}
	}
	return false;
  }




  // Write DOM to XML-file
  public static void writeXMLFile(Document document, String filepath) {
  	
  	try {
	  	// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(filepath));

		transformer.transform(source, result);
	} catch (TransformerException tfe) {
		tfe.printStackTrace();
	}
  }










}
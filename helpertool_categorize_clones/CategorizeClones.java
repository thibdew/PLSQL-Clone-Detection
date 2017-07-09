import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import java.io.File;

public class CategorizeClones {

	public static void main(String argv[]) {

		System.out.println(argv[0]);

		try {

			File clonesXMLFile = new File(argv[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(clonesXMLFile);
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// QUERY ELIMINATION #1 -> SUBQUERYS
			Document docSubquerys = dBuilder.newDocument();
			Element rootSubquerys = docSubquerys.createElement("pmd-cpd");
			docSubquerys.appendChild(rootSubquerys);

			// QUERY ELIMINATION #2 -> STRING LITERALS
			Document docLiterals = dBuilder.newDocument();
			Element rootLiterals = docLiterals.createElement("pmd-cpd");
			docLiterals.appendChild(rootLiterals);

			// QUERY ELIMINATION #3 -> EFECTIVE DATES
			Document docEffDate = dBuilder.newDocument();
			Element rootEffDate = docEffDate.createElement("pmd-cpd");
			docEffDate.appendChild(rootEffDate);

			NodeList duplicationlist = doc.getElementsByTagName("duplication");



			for(int i=0; i < duplicationlist.getLength(); i++) {
				Node duplication = duplicationlist.item(i);
				Element element = (Element) duplication;

				String codefragment = element.getElementsByTagName("codefragment").item(0).getTextContent();
				System.out.print(i+" ");

				// QUERY ELIMINATION #1 -> SUBQUERYS
				if(containsSubquery(codefragment)) {
					Node importedNode = docSubquerys.importNode(duplication, true);
					rootSubquerys.appendChild(importedNode);
					System.out.print("- subquery ");
				}

				// QUERY ELIMINATION #2 -> STRING LITERALS
				if(containsLiteral(codefragment)) {
					Node importedNode = docLiterals.importNode(duplication, true);
					rootLiterals.appendChild(importedNode);
					System.out.print("- literal ");
				}

				// QUERY ELIMINATION #3 -> EFECTIVE DATES
				if(containsLiteral(codefragment)) {
					Node importedNode = docEffDate.importNode(duplication, true);
					rootEffDate.appendChild(importedNode);
					System.out.print("- eff date");
				}

				System.out.println();

			}

			writeXMLFile(docSubquerys, "categorySubquery.xml");
			writeXMLFile(docLiterals, "categoryLiterals.xml");
			writeXMLFile(docEffDate, "categoryEffDate.xml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static Boolean containsSubquery(String clone) {
		// All subquery SELECT statements whom does not contain 'EFF' between SELECT and WHERE
		Pattern pattern = Pattern.compile("(?s)\\(SELECT((?!EFF).)*?WHERE");
		Matcher matcher = pattern.matcher(clone);
		return matcher.find();
	}

	public static Boolean containsLiteral(String clone) {
		Pattern pattern = Pattern.compile("'.*'");
		Matcher matcher = pattern.matcher(clone);
		return matcher.find();
	}

	public static Boolean containsEffDate(String clone) {
		Pattern pattern = Pattern.compile("\\(SELECT.*EFF");
		Matcher matcher = pattern.matcher(clone);
		return matcher.find();
	}


	public static Boolean contains(String clone, String pattern) {
	 return clone.toLowerCase().contains(pattern.toLowerCase());
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
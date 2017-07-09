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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.*;
import java.nio.charset.Charset;

public class CreateQueryFiles {

	public static void main(String argv[]) {

		System.out.println(argv[0]);

		try {

			File queryXMLFile = new File(argv[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(queryXMLFile);
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList queryList = doc.getElementsByTagName("query");

			for(int i=0; i < queryList.getLength(); i++) {
				Node query = queryList.item(i);
				Element element = (Element) query;

				String queryName = element.getElementsByTagName("queryname").item(0).getTextContent();
				String queryTxt = element.getElementsByTagName("sql").item(0).getTextContent();
				System.out.print(i+" "+queryName);

				writeXMLFile(queryTxt, argv[1]+queryName+".sql");

				System.out.println();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
 


	// Write String to XML-file
	public static void writeXMLFile(String text, String filepath) {
		try {
			Path file = Paths.get(filepath);
			Files.write(file, text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}










}
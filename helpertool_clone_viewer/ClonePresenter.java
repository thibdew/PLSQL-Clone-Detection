
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ClonePresenter {


	public static void main(String argv[]) {
		System.out.println("Selected file: "+argv[0]);

		List<Clone> clonesList = new ArrayList<Clone>();

		try {
			File fXmlFile = new File(argv[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList cloneList = doc.getElementsByTagName("duplication");
			for(int i=0; i<cloneList.getLength(); i++) {
				System.out.println(i);
				Node cloneNode = cloneList.item(i);
				Element clone = (Element) cloneNode;

				String lines = clone.getAttribute("lines");
				String tokens = clone.getAttribute("tokens");
				Clone cloneObject = new Clone(lines, tokens);

				// Add all files (all occurences) to cloneObject
				NodeList fileList = clone.getElementsByTagName("file");
				for(int j=0; j<fileList.getLength(); j++) {
					Node fileNode = fileList.item(j);
					Element file = (Element) fileNode;
					String start = file.getAttribute("line");
					String name = file.getAttribute("path");
					String[] parts = name.split("/");
					name = parts[parts.length-1];
					System.out.println(argv[1]+name);
					String fragment = getCloneFromFile(Integer.parseInt(start), cloneObject.lengthInstanceLines, argv[1]+name);
					cloneObject.addFile(start, name, fragment);
				}

				// Add clone to list of clones
				clonesList.add(cloneObject);
			}
			
			String html = new ClonePresenter().createHTML(clonesList);
			writeFile(html, "output.html");


		} catch (Exception e) {
			e.printStackTrace();
	    }

	}

	public String createHTML(List<Clone> clonesList) {

		try {
			// InputStream in = new Object() { }.getClass().getResourceAsStream("head.html"); 
			// BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			// String head = org.apache.commons.io.IOUtils.toString(reader);
			// System.out.println(resource);
			BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("head.html")));
			StringBuilder textBuilder = new StringBuilder();
			String head = null;
			while((head = in.readLine()) != null) {
				textBuilder.append(head+"\n");
			}
			
			//System.out.println(textBuilder);
			
			
			
			String html = textBuilder.toString();
			
			html = html+"var clones = [\n";
			for(Clone cloneObject : clonesList){

				String array = "[";
				for (int i=0; i<cloneObject.filesStart.size(); i++) {
					array = array + "["+cloneObject.filesStart.get(i)+",'"+cloneObject.filesName.get(i)+"',  \""+cloneObject.filesFragment.get(i)+"\"]";
					if(i!=cloneObject.filesStart.size()-1) {
						array = array + ",";
					}
				}
				array = array+ "],\n";
				html = html+ array;
			}
			html = html+ "\n];\n		 		</script> \n	</head>\n<body>\n<div style='height:350px;overflow:scroll;'>\n<table id='clonetable' class='tablesorter'>\n<caption></caption>\n<thead>\n<tr>\n<th>Clone #</th>\n<th>Occurences</th>\n<th>Length total lines</th>\n<th>Length instance lines</th>\n<th>Length instance tokens</th>\n</tr>\n</thead>\n<tbody>";

			int i = 1;
			for(Clone cloneObject : clonesList){
				String tr = "<tr><td>"+i+"</td><td>"+cloneObject.occurences()+"</td><td>"+cloneObject.length()+"</td><td>"+cloneObject.lengthInstanceLines+"</td><td>"+cloneObject.lengthInstanceTokens+"</td></tr>\n";
				i++;
				html = html+tr;
			}
			
			
			BufferedReader inFoot = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("foot.html")));
			StringBuilder textBuilderFoot = new StringBuilder();
			String foot = null;
			while((foot = inFoot.readLine()) != null) {
				textBuilderFoot.append(foot+"\n");
			}
			
			
			html = html + textBuilderFoot.toString();
			return html;
			//System.out.println(html);

		} catch (Exception e) {
			e.printStackTrace();
	    }
	    return "";
	}

	public static void writeFile(String content, String path) {
		// Path file = Paths.get(path);
		// Files.write(file, content, Charset.forName("UTF-8"));
		try{
		    PrintWriter writer = new PrintWriter(path, "UTF-8");
		    writer.print(content);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}

	public static String readFile(String path) {
		try{
			String content = new String(Files.readAllBytes(Paths.get(path)));
			return content;
		} catch (IOException e) {
		   // do something
			return "";
		}
	}

	public static String getCloneFromFile(int start, int numberOfLines, String queryName) {
		try{
			List<String> fileList = Files.readAllLines(Paths.get(queryName), Charset.forName("UTF-8"));
			fileList = fileList.subList(start-1, start-1+numberOfLines);
			//CharSequence[] cs = fileList.toArray(new CharSequence[list.size()]);
			String clone = String.join("\\n", fileList);
			// System.out.println(clone);
			// System.out.println();
			// System.out.println();
			// System.out.println();
			// System.out.println();
			return clone;
		} catch (IOException e) {
		   // do something
			return "";
		}
		
	}
}

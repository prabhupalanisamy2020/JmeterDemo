package performance.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runners.MethodSorters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.security.PublicKey;
import java.util.stream.Collectors;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import java.io.FileInputStream;

@FixMethodOrder(MethodSorters.DEFAULT)
public class JMXUtil {

    public static String jmxData = "";
    public static String threadsCount = "3";
    public static String jmxFilePath="C:\\Users\\prabhu.palanisamy\\Desktop\\SiebelPOC.jmx";
    public static String xmlFilePath="C:\\Users\\prabhu.palanisamy\\Desktop\\SiebelPOC.xml";
    public static String newJMXFilePath="C:\\Users\\prabhu.palanisamy\\Desktop\\SiebelPOC.jmx";

    @Test
    public void A_readJMXFile() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(jmxFilePath)));
        jmxData = reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    @Test
    public void B_writeXMLFileWithJMXData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(xmlFilePath)));
        writer.write(jmxData);
        writer.close();
    }

    @Test
    public void C_yupdateThreadsCount() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //an instance of builder to parse the specified xml file
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFilePath);
        doc.getDocumentElement().normalize();
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//stringProp[@name=\"ThreadGroup.num_threads\"]");
        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        if(nodeList != null && nodeList.getLength() > 0){
            for (int j = 0; j < nodeList.getLength(); j++) {
                Element el = (org.w3c.dom.Element) nodeList.item(j);
                if (el.hasAttribute("name") && el.getAttribute("name").equalsIgnoreCase("ThreadGroup.num_threads")) {
                    el.setTextContent(threadsCount);
                }
            }
        }
        //write the updated document to file or console
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(newJMXFilePath));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("JMX file updated successfully");

    }

    @Test
    public void D_zrunJmeter() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "JmeterRunner.bat");
        File dir = new File("C:\\Users\\prabhu.palanisamy\\Desktop");
        pb.directory(dir);
        Process p = pb.start();
    }


}

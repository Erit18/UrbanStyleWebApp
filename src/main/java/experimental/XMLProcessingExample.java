package experimental;

import java.util.*;

public class XMLProcessingExample {
    private List<String> xmlDocuments;
    
    public XMLProcessingExample() {
        xmlDocuments = new ArrayList<>();
        generateDummyXML();
    }
    
    private void generateDummyXML() {
        for (int i = 0; i < 100; i++) {
            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<root>\n");
            
            for (int j = 0; j < 50; j++) {
                xml.append("  <element id=\"").append(j).append("\">\n");
                xml.append("    <name>Item ").append(j).append("</name>\n");
                xml.append("    <value>").append(Math.random() * 1000).append("</value>\n");
                xml.append("  </element>\n");
            }
            
            xml.append("</root>");
            xmlDocuments.add(xml.toString());
        }
    }
    
    public void processXML() {
        for (String xml : xmlDocuments) {
            // Simular procesamiento
            String[] lines = xml.split("\n");
            for (String line : lines) {
                if (line.contains("element")) {
                    processElement(line);
                }
            }
        }
    }
    
    private void processElement(String element) {
        StringBuilder processed = new StringBuilder();
        for (int i = 0; i < element.length(); i++) {
            processed.append(Character.toUpperCase(element.charAt(i)));
        }
    }
}

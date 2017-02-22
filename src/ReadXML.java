import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXML {

	/*
	 * 将nmap扫描结果xml解析成element形式
	 */
	public static Element readXML(String path) {
		Element element = null;
		File f = new File(path);
		if (f.exists()) {
			DocumentBuilder db = null;
			DocumentBuilderFactory dbf = null;
			try {
				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				Document dt = db.parse(f);
				element = dt.getDocumentElement();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return element;
	}

	/*
	 * 将*.xml的每一个结点解析存到list中
	 */
	public static List getContent(String path) {

		List<String> valueList = new ArrayList();
		Element element = readXML(path);
		NodeList list = element.getChildNodes();
		NodeList childlist = null;

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			StringBuffer str = new StringBuffer();
			if ("host".equals(node.getNodeName())) {
				childlist = node.getChildNodes();

				for (int j = 0; j < childlist.getLength(); j++) {

					StringBuffer tmp = new StringBuffer();
					NodeList minodelist = null;
					Node childnode = childlist.item(j);
					if ("address".equals(childnode.getNodeName())) {
						String addr = childnode.getAttributes().getNamedItem("addr").getNodeValue();
						str.append(addr+"'");
					}
					if ("ports".equals(childnode.getNodeName())) {
						StringBuffer str2 = new StringBuffer();
						minodelist = childnode.getChildNodes();
						for (int k = 0; k < minodelist.getLength(); k++) {
							Node minode = minodelist.item(k);
							NodeList statelist = null;
							if ("port".equals(minode == null ? null : minode.getNodeName())) {
								str2.append(minode.getAttributes().getNamedItem("portid") == null ? " "
										: minode.getAttributes().getNamedItem("portid").getNodeValue());
								str2.append("'");
								str2.append(minode.getAttributes().getNamedItem("protocol") == null ? " "
										: minode.getAttributes().getNamedItem("protocol").getNodeValue());
								str2.append("'");
								statelist = minode.getChildNodes();
								for (int l = 0; l < statelist.getLength(); l++) {
									Node statenode = statelist.item(l);
									if ("state".equals(statenode.getNodeName())) {
										str2.append(
												statenode.getAttributes().getNamedItem("state").getNodeValue() == null
														? ""
														: statenode.getAttributes().getNamedItem("state")
																.getNodeValue());
										str2.append("'");
										str2.append(statenode.getAttributes().getNamedItem("reason") == null ? " "
												: statenode.getAttributes().getNamedItem("reason").getNodeValue());
										str2.append("'");
									}
									if ("service".equals(statenode == null ? null : statenode.getNodeName())) {
										str2.append(statenode.getAttributes().getNamedItem("name") == null ? " "
												: statenode.getAttributes().getNamedItem("name").getNodeValue());
										str2.append("'");
										str2.append(statenode.getAttributes().getNamedItem("product") == null ? " "
												: statenode.getAttributes().getNamedItem("product").getNodeValue());
										str2.append("'");
										str2.append(statenode.getAttributes().getNamedItem("version") == null ? " "
												: statenode.getAttributes().getNamedItem("version").getNodeValue());
										str2.append("'");
										str2.append(statenode.getAttributes().getNamedItem("extrainfo") == null ? " "
												: statenode.getAttributes().getNamedItem("extrainfo").getNodeValue());
										str2.append("'");
									}
								}

								tmp.append(str.toString());
								tmp.append(str2);
								str2.delete(0, str2.length());
								//System.out.println(tmp.toString());
								if (tmp != null) {
									valueList.add(tmp.toString());
									tmp.delete(0, tmp.length());
								}
							}

						}

					}

				}

			}
		}

		return valueList;

	}
}

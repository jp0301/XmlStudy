/* ===========================================
 * XmlDomTest04.java
 * - 콘솔 기반 자바 프로그램
 * - XML DOM 활용 → 로컬(Local) XML 읽어내기
 *   (VEHICLES.xml)
 * ===========================================
 */


/*
-----------------------------------------------------
NO    MAKE    MODEL    YEAR    STYLE          PRICE
-----------------------------------------------------
1     Dodge   Durango  1998    Sport Utility  18000
                       :
10                       
-----------------------------------------------------
*/



/* 위에 처리가 끝나면
-----------------------------------------------------
NO    MAKE    MODEL    YEAR    STYLE          PRICE
-----------------------------------------------------
1     Dodge   Durango  1998    Sport Utility  18000
Options ---------------------------------------------
        Power_Locks : Yes
        Power_Window : Yes
        Stereo : Radio/Cassette/CD
        Air_Conditioning : Yes
        Automatic : Yes
        Four_Wheel_Drive : Full/Partial
        Note : Very clean
-----------------------------------------------------
2
                     :

*/

package com.test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlDomTest04
{

	public static void main(String[] args)
	{
		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			String url = "VEHICLES.xml";
			Document xmlObj = builder.parse(url);
			
			Element root = xmlObj.getDocumentElement();
			NodeList vehicleNodeList = root.getElementsByTagName("VEHICLE");
			
			System.out.println("==========================================================");
			System.out.println("NO    MAKE      MODEL      YEAR    STYLE           PRICE");
			System.out.println("----------------------------------------------------------");

			for (int i = 0; i < vehicleNodeList.getLength(); i++)
			{
				Node vehicleNode = vehicleNodeList.item(i);
				
				Element vehicleElement = (Element)vehicleNode;
				
				System.out.printf("%-5s %-9s %-10s %-7s %-15s %-4s%n", getText(vehicleElement, "INVENTORY_NUMBER")
														   , getText(vehicleElement, "MAKE")
														   , getText(vehicleElement, "MODEL")
														   , getText(vehicleElement, "YEAR")
														   , getText(vehicleElement, "STYLE")
														   , getText(vehicleElement, "PRICE")
								 );
				
				/* Option print */
				System.out.println("Options --------------------------------------------------");
				
				NodeList optionNodeList = vehicleElement.getElementsByTagName("OPTIONS");
				
				/*
				 * if(optionNodeList.getLength() > 0) { Node optionNode =
				 * optionNodeList.item(0); Element optionElement = (Element)optionNode;
				 * 
				 * NodeList subNodeList = optionElement.getChildNodes();
				 * 
				 * for (int j = 0; j < subNodeList.getLength(); j++) { Node subNode =
				 * subNodeList.item(j);
				 * 
				 * if (subNode.getNodeType() == 1) { Element subNodeElement = (Element)subNode;
				 * System.out.printf("         %s : %s%n", subNodeElement.getNodeName(),
				 * subNodeElement.getTextContent()); } } }
				 */
				
				NodeList options = vehicleElement.getElementsByTagName("OPTIONS");
				Node option = options.item(0);
				Element optionElement = (Element)option;
				
				NodeList childNodes = optionElement.getChildNodes();
				for (int k = 0; k < childNodes.getLength(); k++)
				{
					Node childNode = childNodes.item(k);
					if (childNode.getNodeType() == 1)
					{
						System.out.printf("             %s  : %s%n", childNode.getNodeName()
								                                    , childNode.getTextContent());
					}
				}
				System.out.println("----------------------------------------------------------");
					
			}
			System.out.println("==========================================================");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	

	private static String getText(Element parent, String tagName)
	{
		String result = "";
		// 대상 태그(tagName) 객체의 첫 번째 자식 노드 얻어오기
		Node node = parent.getElementsByTagName(tagName).item(0);
		Element element = (Element)node;
		
		// 대상 엘리먼트(element)의 자식 노드(텍스트 노드)의 값 얻어오기
		result = element.getChildNodes().item(0).getNodeValue();
		
		return result;
	}
}

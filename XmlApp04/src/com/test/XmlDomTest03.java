/* ===========================================
 * XmlDomTest03.java
 * - 콘솔 기반 자바 프로그램
 * - XML DOM 활용 → 로컬(Local) XML 읽어내기
 *   (breakfast_menu.xml)
 * ===========================================
 */


// breakfast_menu.xml 파일을 대상으로
/*
■[Belgian Waffles] $5.95 650칼로리
- Two of our famous Belgian Waffles with plenty of real maple syrup
---------------------------------------------------------------------
■[Strawberry Belgian Waffles] $7.95 900칼로리
- Light Belgian waffles covered with strawberries and whipped cream
---------------------------------------------------------------------
  :
*/
// 이와 같이 결과 출력이 이루어질 수 있도록 프로그램을 작성한다.
// xml dom 활용

package com.test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlDomTest03
{
	
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
	
	
	public static void main(String[] args)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			String url = "breakfast_menu.xml";
			Document xmlObj = builder.parse(url);
			
			Element root = xmlObj.getDocumentElement();
			
			NodeList breakfastMenuNodeList = root.getElementsByTagName("food");
			
			for (int i = 0; i < breakfastMenuNodeList.getLength(); i++)
			{
				Node breakfastMenuNode = breakfastMenuNodeList.item(i);
				
				Element breakfastMenuElement = (Element)breakfastMenuNode;
				
				System.out.printf("■[%s] %s %s칼로리%n - %s%n", getText(breakfastMenuElement, "name")
														 , getText(breakfastMenuElement, "price")
														 , getText(breakfastMenuElement, "calories")
														 , getText(breakfastMenuElement, "description")
				);
				System.out.println("-------------------------------------------------------------------");
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}
}

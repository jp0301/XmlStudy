/* =======================================================================
 * XmlDomTest06.java
 * - 콘솔 기반 자바 프로그램
 * - XML DOM 활용 → 원격(Remote) XML 읽어내기
 *   (http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108)
 * =======================================================================
 */

/*
http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108 전국
109 서울, 경기
105 강원
131 충북
133 충남
146 전북
156 전남
143 경북
159 경남
184 제주
*/


package com.test;

import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlDomTest06
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String [] stnId = {"108", "109", "105", "131", "133", "146", "156", "143", "159", "184"};
	
		do
		{
			try
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlObj = null;
				
				System.out.println("");
				System.out.println("");
				System.out.println("-----------------------------------");
				System.out.println("■■■ 기상청 육상 중기 예보 ■■■");
				System.out.println("-----------------------------------");
				System.out.println("1.	전국 ");
				System.out.println("2.	서울, 경기 ");
				System.out.println("3.	강원 ");
				System.out.println("4.	충북 ");
				System.out.println("5.	충남 ");
				System.out.println("6.	전북 ");
				System.out.println("7.	전남 ");
				System.out.println("8.	경북 ");
				System.out.println("9.	경남 ");
				System.out.println("10.	제주특별자치도");
				System.out.printf(">> 지역 선택(종료 0) : ");
				
				String m = sc.next();
				
				if(m.equals("0"))
					break;
				
				String str = String.format("https://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=%s"
										  , stnId[Integer.parseInt(m)-1]);
				
				// URL 객체 구성 → 절대 경로 지정 방식
				URL url = new URL(str);
				
				// URL 로 접근하여 데이터 내용 읽어오기(스트림)
				InputSource is = new InputSource(url.openStream());
				xmlObj = builder.parse(is);
				
				// 루트 엘리먼트 접근
				Element root = xmlObj.getDocumentElement();
				
				// 타이틀 추출하기
				Node itemNode = root.getElementsByTagName("item").item(0);
				Element itemElement = (Element)itemNode;
				
				System.out.printf("%n%s%n%n", XMLDOM.getText(itemElement, "title"));
				
				// 기상 전망 추출하기
				Node wfNode = root.getElementsByTagName("wf").item(0);
				Element wfElement =(Element)wfNode;
				
				System.out.println("[기상전망] -----------------------------------");
				System.out.printf("%s%n%n", wfElement.getTextContent().replaceAll("<br />", "\n"));
				
				// 도시 별 기상 예보 추출하기
				System.out.println("[육상날씨] ------------------------------------");
				NodeList locationNodeList = root.getElementsByTagName("location");
				
				for (int i = 0; i < locationNodeList.getLength(); i++)
				{
					Node locationNode = locationNodeList.item(i);
					Element locationElement = (Element)locationNode;
					
					System.out.printf("도시 : %s%n"
							, XMLDOM.getText(locationElement, "city"));
					
					System.out.println("-----------------------------------------------");
					NodeList dataNodeList = locationElement.getElementsByTagName("data");
					
					for (int j = 0; j < dataNodeList.getLength(); j++)
					{
						Node dataNode = dataNodeList.item(j);
						Element dataElement = (Element)dataNode;
						
						System.out.printf("   %s / %s / %s ~ %s / %s%n"
								, XMLDOM.getText(dataElement, "tmEf")	// 날짜 시간
								, XMLDOM.getText(dataElement, "wf")		// 날씨 예보
								, XMLDOM.getText(dataElement, "tmn")	// 최저 온도
								, XMLDOM.getText(dataElement, "tmx")	// 최고 온도
								, XMLDOM.getText(dataElement, "rnSt")	// 강수 확률
						);
					}
					
					
				}
				
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
			
			
		} while (true);
	
	
	
	}
	
	
	
}

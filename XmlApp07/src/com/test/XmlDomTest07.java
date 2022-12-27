/*===================================================================================
	XmlDomTest07.java
	- 콘솔 기반 자바 프로그램
	- XML DOM 활용 → 원격(remote) XML 읽어내기
	  (https://fs.jtbc.joins.com/RSS/newsflash.xml)
===================================================================================*/

/*
	title> JTBC News
	link> https://fs.jtbc.joins.com/RSS/newsflash.xml
	description> 속보 RSS
	copyright> Copyright(C) JTBC All rights reserved.
	
	주요 기사 --------------------------------------------------------
	title > 12월 27일 (화) 상암동클라스 다시보기
	description > 12월 27일 화요일, 당신의 클라스를 높여드리는 아침 '상암동 클라스' 어제(26일) 생방송 시청 인증샷 많은 분들이 올려주셨는데요. 저희는 이 두 분을 뽑았습니다. 바로 크게 띄워주세요. 먼저 인자한 미소로 셀카
	link > https://news.jtbc.co.kr/article/article.aspx?news_id=NB12108656
	pubDate > 2022.12.27
	

*/

package com.test;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class XmlDomTest07
{
	public static void main(String[] args)
	{
		/*
		 1. 원격 XML 정보를 요청하고, 그 결과를 메모리에 로드
		    → XML DOM 구성
		    - DBF 활용
		    - DB 활용
		    - XML 로딩
		    
		 2. 루트 엘리먼트 접근
		 3. 특정 하위 엘리먼트 접근
		    → 위치, 이름 등
		 4. 텍스트 노드 접근
		    → 필요한 데이터 획득
		 5. 결과 출력
		 */
		try
		{
			// ※ DOM(Document Object Model)
			//    - XML 이나 HTML Document(문서)를 응용프로그램에서 사용하기 위한 API 규격
			//    - DOM 은 Document(문서)의 각 부분들을 객체(Object)로 표현한 API
			
			// ※ DOM(Document Object Model) 파서(Parser)
			//    - XML 문서를 읽고, 해석한 후,
			//      해석한 결과르 메모리에 DOM 객체 트리 구조로 생성시키는 파서(Parser)
			//    - 원하는 데이터에 접근할 수 있도록 해 주어
			//      검색, 수정, 삭제 등의 처리를 할 수 있도록 지원
			
			// ※ DOM 을 컨트롤 하는 과정에서 주로 사용되는 DOM(Document Object Model) 인터페이스
			//    - Node
			//      : 모든 객체의 부모 인터페이스로서 공통적으로 기능하는 함수를 가진다.
			//    - NodeList (NODESET)
			//      : 노드들을 리스트로 받아 처리하기 쉽도록 한 것.
			//    - Document
			//      : DOM(Document Object Model) 트리 구조의 최상위 노드로
			//        XML 문서 자체를 의미한다.
			//    - Element
			//      : XML 의 엘리먼트에 해당하는 객체 유형.
			//    - Attr
			//      : XML 의 Attribute 에 해당하는 객체 유형.
			//    - CharacterData
			//      : XML 의 데이터에 해당하는 객체 유형.
			//    - Text
			//      : 문자 데이터(내용)에 해당하는 객체 유형.
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//-- 『DocumentBuilderFactory』
			//   DocumentBuilder (DOM Parser)를 생성할 수 있도록 지원하는 Factory 클래스
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			//-- 『DocumentBuilder』
			//   DOM Parser 객체의 클래스.
			
			Document xmlObj = null;
			//-- 『Document』
			//   XML 문서 자체를 의미하는 객체
			
			String str = "https://fs.jtbc.joins.com/RSS/newsflash.xml";
			
			// URL 객체 구성
			URL url = new URL(str);
			//-- 요청하게 될 문서의 URL 구성.
			
			// 데이터 내용 읽어오기
			InputSource is  = new InputSource(url.openStream());
			//-- 해당 URL 의 스트림을 읽어들여 메모리에 로드할 수 있도록 준비
			
			xmlObj = builder.parse(is);
			//-- 문서를 읽고, 메모리에 DOM 트리 구조의 객체를 구성
			
			// 루트 엘리먼트 접근
			Element root = xmlObj.getDocumentElement();
			
			// 확인(테스트)
			// System.out.println(root.getNodeName());
			//--==>> rss
			
			
			Element channelElement = (Element)root.getElementsByTagName("channel").item(0);
			
			
			System.out.printf("%ntitle > %s", XMLDOM.getText(channelElement, "title"));
			System.out.printf("%nlink > %s", XMLDOM.getText(channelElement, "link"));
			System.out.printf("%ndescription > %s", XMLDOM.getText(channelElement, "description"));
			System.out.printf("%ncopyright > %s", XMLDOM.getText(channelElement, "copyright"));
						
			System.out.println();
			System.out.println("주요 기사 ------------------------------------------------");
			
			NodeList itemNodeList = root.getElementsByTagName("item");

			for (int i = 0; i < itemNodeList.getLength(); i++)
			{ 
				Node itemNode = itemNodeList.item(i);
				Element itemElement = (Element)itemNode;
				
				System.out.printf("title> %s%n", XMLDOM.getText(itemElement, "title"));
				System.out.printf("link> %s%n", XMLDOM.getText(itemElement, "link"));
				System.out.printf("description> %s%n", XMLDOM.getText(itemElement, "description"));
				System.out.printf("pubDate> %s%n", XMLDOM.getText(itemElement, "pubDate"));
				System.out.println("");
			}
			
		} catch (Exception e)               
		{
			System.out.println(e.toString());
		}
		
		
	}
}

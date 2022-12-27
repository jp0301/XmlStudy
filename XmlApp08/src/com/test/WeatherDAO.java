package com.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WeatherDAO
{
	// 공통 멤버 구성 > 멤버 변수 > 초기화 > 생성자
	private Document xmlObj;
	private XPath xPath;
	private HashMap<String, String> map;
	
	// 생성자 정의 > 기본 생성자
	public WeatherDAO() throws ParserConfigurationException, IOException, SAXException
	{
		this("108"); 	// 전국 기준
		/*
		stnId=108	전국
		stnId=109	서울, 경기 
		stnId=105	강원 
		stnId=131	충북
		stnId=133	충남 
		stnId=146	전북 
		stnId=156	전남 
		stnId=143	경북 
		stnId=159	경남 
		stnId=184	제주특별자치도
		*/
	}
	
	
	// 생성자 정의 > 매개변수 있는 생성자
	public WeatherDAO(String stnId) throws ParserConfigurationException, IOException, SAXException
	{
		map = new HashMap<String, String>();
		/*
		맑음 구름조금 구름많음, 구름많고 비, 구름많고 비/눈, 구름많고 눈/비, 구름많고 눈
		흐림, 흐리고 비, 흐리고 비/눈, 흐리고 눈/비, 흐리고 눈 
		*/
		map.put("맑음", "W_DB01.png");
		map.put("흐림", "W_DB04.png");
		map.put("비", "W_DB05.png");
		map.put("구름조금", "W_NB02.png");
		map.put("구름많음", "W_NB03.png");
		map.put("흐리고 비", "W_NB08.png");
		map.put("구름많고 비", "W_NB20.png");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		String str = String.format("http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=%s", stnId);
		
		URL url = new URL(str);
		InputSource is = new InputSource(url.openStream());
		
		xmlObj = builder.parse(is);
		xPath = XPathFactory.newInstance().newXPath();				//-- check~!!!
		//-- XPathFactory xFactory = XPathFactory.newInstance();
		//   xPath - xFactory.newPath();
		//   와 동일한 구문
		
		
		/*
		○ XPath 생성
		   - XPathFactory 의 정적 메소드(static) 『newInstance()』 호출을 통해 
		     XPath 를 생성해 주는 XPathFactory 를 생성하고
		   - 이 XPathFactory 의 정적 메소드(static) 『newXPath()』 호출을 통해
		     XPath 객체를 생성한다.
		
		○ 노드 선택(Selecting Nodes)
		   - 브라우저마다 XPath 를 처리하는 방법에서 차이를 보인다.
		   - Chrome, Firefox, Edge, Opera, Safari 등은
		     『evaluation()』 메소드를 사용하여 노드를 처리한다.
		     → xmlDoc.evaluation(xpath, xmlDoc, null, XPathResult.ANY_TYPE, null)
		     - IE 는 『selectNodes()』 메소드를 사용하여 노드를 선택한다.
		     
		○ XPath 의 『compile(XPath 경로 표현식)』
		   - XML 데이터 Parsing
		     1. XML 이 제공되는 URL 로 접속하여 데이터를 수신한다.
		     2. DocumentBuilderFactory ... newInstance() 로 factory 를 생성한다.
		     3. DocumentBuilder ... newDocumentBuilder() 로 builder 를 생성한다.
		     4. InputSource is ... new InputSource() 로 InputSource 를 생성한다.
		        이때, 파일로 수신한 경우라면 File 객체를 넘겨준다.
		     5. Document xmlObj = builder.parse(is) 로 XMl 을 파싱(Parshing)한다.
		     6. XPath xPath = XPathFactory.newInstance().newXPath() 로
		        XPath 객체를 생성하고
		     7. XPathExpression expr = XPath.compile( XPath 경로 표현식 ) 으로
		        가져올 Element 를 선택하게 된다.
		     8. 해당 노드(Element)에 접근하여 필요한 데이터를 추출한다.
		*/

	}
	
	
	// <title>서울,경기도 육상 중기예보 - 2022년 06월 16일 (목)요일 06:00 발표</title>
	public String weatherTitle() throws XPathExpressionException
	{
		String result = "";
		
		xPath.compile("/rss/channel/item/title").evaluate(xmlObj);
		
		return result;
	}
	
	/*
	<wf>
		<![CDATA[ ○ (하늘상태) 이번 예보기간은 대체로 흐리겠습니다.<br />○ (기온) 이번 예보기간 아침 기온은 18~22도, 낮 기온은 25~31도로 어제(15일, 아침최저기온 14~15도, 낮최고기온 17~25도)보다 높겠습니다.<br />○ (해상) 서해중부해상의 물결은 21일(화)과 22일(수)는 1.0~2.5m로 일겠고, 그 밖의 날은 0.5~2.0m로 일겠습니다.<br />○ (주말전망) 18일(토)~19일(일)은 대체로 흐리겠고, 아침 기온은 19~21도, 낮 기온은 26~31도가 되겠습니다.<br /><br />* 20일(월) 이후 우리나라 주변의 기압계 변화에 따라 강수 시점과 지역의 변동성이 매우 크겠으니, 앞으로 발표되는 예보와 기상정보를 참고하기 바랍니다. ]]>
	</wf> 
	*/
	
	public String weatherInfo() throws XPathExpressionException
	{
		String result = "";
		
		result = xPath.compile("/rss/channel/item/description/header/wf").evaluate(xmlObj);
		
		return result;
	}
	
	/*
	○ XPath 의 『evaluate()』 메소드 두 번째 파라미터 
	   - XPathConstants.NODESET
	   - XPathCounstants.NODE
	   - XPathCounstants.BOOLEAN
	   - XPathCounstants.NUMBER
	   - XPathCounstants.STRING
	*/
	
	
	
	// check~!!!
	// 도시 이름 배열 구성
	public ArrayList<String> weatherCityList() throws XPathExpressionException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		NodeList cityNodeList = (NodeList)xPath
				.compile("/rss/channel/item/description/body/location/city")
				.evaluate(xmlObj, XPathConstants.NODESET);	
		
		for (int i = 0; i < cityNodeList.getLength(); i++)
		{
			Node cityNode = cityNodeList.item(i);
			result.add(cityNode.getTextContent());
		}
		return result;
	}
	
	
	
	// check~!!!
	// 날씨 정보 리스트
	public ArrayList<WeatherDTO> weatherList(String idx) throws XPathExpressionException
	{
		ArrayList<WeatherDTO> result = new ArrayList<WeatherDTO>();
		
		NodeList dataNodeList = 
				(NodeList)xPath
				.compile(String.format("/rss/channel/item/description/body/location[%s]/data", idx))
				.evaluate(xmlObj, XPathConstants.NODESET);
		
		// check~!!! → 『i=1』
		for (int i = 1; i <= dataNodeList.getLength(); i++)
		{
			// tmEf
			String tmEf = xPath
					.compile(String.format("/rss/channel/item/description/body/location[%s]/data[%s]/tmEf"
					, idx, i)).evaluate(xmlObj);
			
			// wf
			String wf = xPath
					.compile(String.format("/rss/channel/item/description/body/location[%s]/data[%s]/wf"
					, idx, i)).evaluate(xmlObj);
			

			// tmn
			String tmn = xPath
					.compile(String.format("/rss/channel/item/description/body/location[%s]/data[%s]/tmn"
					, idx, i)).evaluate(xmlObj);
			
			// tmx
			String tmx = xPath
					.compile(String.format("/rss/channel/item/description/body/location[%s]/data[%s]/tmx"
					, idx, i)).evaluate(xmlObj);
		
			// rnSt
			String rnSt = xPath
					.compile(String.format("/rss/channel/item/description/body/location[%s]/data[%s]/rnSt"
					, idx, i)).evaluate(xmlObj);
			
			WeatherDTO w = new WeatherDTO();
			w.setTmEf(tmEf);
			w.setWf(wf);
			w.setTmn(tmn);
			w.setTmx(tmx);
			w.setRnSt(rnSt);
			w.setImg(map.get(wf));
			
			result.add(w);
		
		}
		return result;
	}
	
}

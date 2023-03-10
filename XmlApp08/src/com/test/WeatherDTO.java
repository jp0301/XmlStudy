package com.test;

public class WeatherDTO
{
	private String tmEf, wf, tmn, tmx, rnSt, img;
	
	//-- tmEf	→ 날짜와 시간 태그
	//			   2022-06-19 00:00
	//-- wf     → 날씨 예보 태그
	//-- tmn    → 최저 온도 태그
	//             (21 → 21℃)	→ 『ㄹ』
	//-- tmx	→ 최고 온도 태그
	//             (30 → 30℃)	→ 『ㄹ』
	//-- rnSt   → 강수 확률 태그
	//             (40 → 40%)
	//-- img    → 날씨 예보에 따른 이미지
	
	
	
	public String getTmEf()
	{
		return tmEf;
	}

	public void setTmEf(String tmEf)
	{
		this.tmEf = tmEf;
	}

	public String getWf()
	{
		return wf;
	}

	public void setWf(String wf)
	{
		this.wf = wf;
	}

	public String getTmn()
	{
		return tmn;
	}

	public void setTmn(String tmn)
	{
		this.tmn = tmn;
	}

	public String getTmx()
	{
		return tmx;
	}

	public void setTmx(String tmx)
	{
		this.tmx = tmx;
	}

	public String getRnSt()
	{
		return rnSt;
	}

	public void setRnSt(String rnSt)
	{
		this.rnSt = rnSt;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}
	
	
}

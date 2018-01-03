package com.yyd.semantic.services.impl.areacode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.db.bean.region.AreaCode;
import com.yyd.semantic.db.bean.region.City;
import com.yyd.semantic.db.bean.region.District;
import com.yyd.semantic.db.bean.region.Province;
import com.yyd.semantic.db.bean.region.RegionLevel;
import com.yyd.semantic.db.service.region.AreaCodeService;
import com.yyd.semantic.db.service.region.CityService;
import com.yyd.semantic.db.service.region.DistrictService;
import com.yyd.semantic.db.service.region.ProvinceService;



@Component
public class AreaCodeSemantic implements Semantic<AreaCodeBean>{	
	@Autowired
	private AreaCodeService areaCodeService;
	
	@Autowired
	private ProvinceService provService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private DistrictService districtService;
	
	@Override
	public AreaCodeBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		AreaCodeBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case AreaCodeIntent.QUERY_AREACODE: {
				result = queryCode(objects, semanticContext);
				break;
			}	
			case AreaCodeIntent.QUERY_REGION: {
				result = queryRegion(objects, semanticContext);
				break;
			}	
			default: {
				result = new AreaCodeBean("这句话太复杂了，我还不能理解");
				break;
			}
		}
		return result;
	}
	
	
	private AreaCodeBean queryCode(Map<String, String> slots, SemanticContext semanticContext) {
		String result ="我听不懂你在说什么";		
		AreaCodeBean resultBean = null;
		List<AreaCode> listAreaCode = new ArrayList<AreaCode>();
		
		String provShort =slots.get(AreaCodeSlot.REGION_PROV_SHORT);
		String provFull = slots.get(AreaCodeSlot.REGION_PROV_FULL);
		String cityShort = slots.get(AreaCodeSlot.REGION_CITY_SHORT);
		String cityFull = slots.get(AreaCodeSlot.REGION_CITY_FULL);			
		String districtShort = slots.get(AreaCodeSlot.REGION_DISTRICT_SHORT);
		String districtFull =slots.get(AreaCodeSlot.REGION_DISTRICT_FULL);	
		
		List<Province> provs = null;
		List<City> citys = null;
		List<District> districts  = null;
		City targetCity = null;
		Province targetProv = null;
		String targetDistrict = null;
		
		if(null != districtShort || null != districtFull) {
			if(null != districtShort) {
				districts = districtService.getByShortName(districtShort);
			}
			else if(null != districtFull) {
				districts = districtService.getByFullName(districtFull);
			}
		}		
		if(null !=cityShort || null != cityFull) {				
			if(null != cityShort) {
				citys = cityService.getByShortName(cityShort);
			}
			else if(null != cityFull) {
				citys = cityService.getByFullName(cityFull);
			}
		 }
		if(null != provShort || null != provFull) {		
			if(null != provShort) {
				provs = provService.getByShortName(provShort);				
			}
			else if(null != provFull) {
				provs = provService.getByFullName(provFull);				
			}	
		}
		
		//省名和地级市名不能相同，因此只取第一个即可
		if(null != provs && provs.size() > 0) {
			targetProv = provs.get(0);
		}
		if(null != citys && citys.size() > 0) {
			targetCity = citys.get(0);
		}
		
		
		if(null != districts) {
			//对于县级区域一律根据它的上一级查找区号，即根据地市级区域和省级区域查找区号
			//1.地名校验
			boolean verifyCity = true;
			if(null != targetProv && null != targetCity) {
				if(targetProv.getAreaId() != targetCity.getUpper()) {
					verifyCity = false;
				}
			}
			
			List<District> districtList = new ArrayList<District>();
			if(verifyCity) {					
				for(District district:districts) {					
					boolean verifyDistrict = true;
					int upperLevel = district.getUpperLevel();
					if(upperLevel == RegionLevel.LEVEL_CITY) {
						if(null != targetCity && targetCity.getAreaId() != district.getUpper()) {
							verifyDistrict = false;							
						}
						
						//省+县级区域情况
						if(null == targetCity && null != targetProv) {
							List<City> targetCitys = cityService.getByAreaId(district.getUpper());
							if(null != targetCitys && targetCitys.size() > 0) {
								City tmpCity = null;
								//地级市不会重名，因此只取第一个
								tmpCity = targetCitys.get(0);
								if(tmpCity.getUpper() != targetProv.getAreaId()) {
									verifyDistrict = false;								
								}
							}
							
						}
					}
					else if(upperLevel == RegionLevel.LEVEL_PROVINCE)
					{
						//省直辖的县级区域，中间不能再出现地级区域
						if(null != targetProv && targetProv.getAreaId() != district.getUpper()) {
							verifyDistrict = false;							
						}
					}
					
					if(verifyDistrict) {
						districtList.add(district);
					}
				}
				
			}
			
			//县级区域返回时要加上它的上一级区域
			if(districtList.isEmpty()) {
				result = "我还不知道该地区号";
			}
			else
			{
				//2.根据地级或省级区域查找区号
				List<AreaCode> tmpAreaCode = new ArrayList<AreaCode>();
				for(District area:districtList) {
					List<AreaCode> list = areaCodeService.getByAreaIdAndLevel(area.getUpper(), area.getUpperLevel());
					tmpAreaCode.addAll(list);
				}	
				
				
				if(null != districtList.get(0).getUnit()) {
					targetDistrict = districtList.get(0).getName() + districtList.get(0).getUnit();
				}
				else
				{
					targetDistrict = districtList.get(0).getName();
				}
				
				if(tmpAreaCode.size() > 0) {
					for(int i =0;i < tmpAreaCode.size();i++) {	
						AreaCode area = tmpAreaCode.get(i);
						//县级区域，要加上上一级区域名字
						area.setName(area.getName()+targetDistrict);						
						listAreaCode.add(area);
					}
					
				}
				
				
			}
		}
		else if(null !=citys) {			
			//地名校验
			List<City> cityList = new ArrayList<City>();
			if(null != targetProv) {
				for(City city:citys) {
					if(targetProv.getAreaId() == city.getUpper()) {
						cityList.add(city);
					}
				}
			}
			else
			{
				cityList.addAll(citys);
			}
			
			//根据地级区域查找
			if(cityList.isEmpty()) {
				result = "我还不知道该地区号";
			}
			else
			{
				for(City city:cityList) {
					List<AreaCode> list = areaCodeService.getByAreaIdAndLevel(city.getAreaId(),RegionLevel.LEVEL_CITY);
					listAreaCode.addAll(list);
				}				
			}
			
		}
		else if(null != provs) {
			//根据省级区域查找						
			if(provs.isEmpty()) {
				result = "我还不知道该地区号";
			}
			else
			{
				//先根据省级信息获取区号
				if(null != provs) {
					for(Province prov:provs) {
						List<AreaCode> list = areaCodeService.getByAreaIdAndLevel(prov.getAreaId(), RegionLevel.LEVEL_PROVINCE);
						listAreaCode.addAll(list);
					}					
					
				}				
				
				//再根据省级下面的地级区域获取信息
				if(listAreaCode.isEmpty()){
					//省名不应该相同，因此只取第一个省
					citys = cityService.getByUpper(provs.get(0).getAreaId());	
					
					if(null != citys) {
						for(City city:citys) {
							List<AreaCode> list = areaCodeService.getByAreaIdAndLevel(city.getAreaId(), RegionLevel.LEVEL_CITY);
							listAreaCode.addAll(list);
						}	
					}								
					
				}
							
			}
			
		}
		
		StringBuilder builder = new StringBuilder();
		for(int i =0; i < listAreaCode.size();i++) {			
			builder.append(listAreaCode.get(i).getName()+" "); //显示上一级区域名字			
			builder.append(listAreaCode.get(i).getCode());
			
			if(i != listAreaCode.size()-1) {
				builder.append(",");
			}
		}
		
		
		if(!listAreaCode.isEmpty()) {
			result = builder.toString();
		}
				
		resultBean = new AreaCodeBean(result);		
		
		return resultBean;
	}
	
	/**
	 * 根据区号查询区域
	 * @param slots
	 * @param semanticContext
	 * @return
	 */
	private AreaCodeBean queryRegion(Map<String, String> slots, SemanticContext semanticContext) {
		String result ="我听不懂你在说什么";		
		String areaCode =slots.get(AreaCodeSlot.REGION_AREACODE);
		AreaCodeBean resultBean = null;
		
		if(null == areaCode) {
			resultBean = new AreaCodeBean(result);
			return resultBean;
		}
		
		List<AreaCode> areaCodeList = areaCodeService.getByCode(areaCode);
		if(null == areaCodeList || areaCodeList.isEmpty()) {
			result = "我还不知道"+areaCode+"是哪里的邮编";
			resultBean = new AreaCodeBean(result);
			return resultBean;
		}
			
		List<AreaCode> provPostCode = new ArrayList<AreaCode>();
		List<AreaCode> cityPostCode = new ArrayList<AreaCode>();
		List<AreaCode> districtPostCode = new ArrayList<AreaCode>();	
		
		for(AreaCode code:areaCodeList) {
			int level = code.getLevel();
			if(level == RegionLevel.LEVEL_PROVINCE) {
				provPostCode.add(code);
			}
			else if(level == RegionLevel.LEVEL_CITY) {
				cityPostCode.add(code);
			}
			else if(level == RegionLevel.LEVEL_DISTRICT) {
				districtPostCode.add(code);
			}
		}
		
		//有多个区域邮编重复时，取其行政级别最高的区域
		List<String> areas = new ArrayList<String>();
		if(!provPostCode.isEmpty()) {
			for(AreaCode code:provPostCode) {
				areas.add(code.getName());
			}
		}
		else if(!cityPostCode.isEmpty()) {
			for(AreaCode code:cityPostCode) {
				areas.add(code.getName());
			}
		}
		else if(!districtPostCode.isEmpty()) {
			for(AreaCode code:districtPostCode) {				
				String upperName = null;
				Province tmpProv = null;
				City tmpCity = null;	
				if(code.getUpperLevel() == RegionLevel.LEVEL_PROVINCE) {
					List<Province> provList = provService.getByAreaId(code.getUpper());
					if(null != provList && provList.size() >0) {
						tmpProv = provList.get(0);
						
						if(null != tmpProv.getUnit()) {
							upperName = tmpProv.getName() + tmpProv.getUnit();
						}
						else
						{
							upperName = tmpProv.getName();
						}
					}							
					
				}
				else if(code.getUpperLevel() == RegionLevel.LEVEL_CITY)
				{
					List<City> cityList = cityService.getByAreaId(code.getUpper());
					if(null != cityList && cityList.size() >0) {
						tmpCity = cityList.get(0);
						if(null != tmpCity.getUnit()) {
							upperName = tmpCity.getName() + tmpCity.getUnit();
						}
						else
						{
							upperName = tmpCity.getName();
						}
					}								
					
				}
				
				if(null != upperName) {
					areas.add(upperName+code.getName());
				}
				else
				{
					areas.add(code.getName());
				}
				
			}
		}
		
		StringBuilder builder = new StringBuilder();
		for(int i =0; i < areas.size();i++) {
			builder.append(areas.get(i));
			if(i != areas.size()-1) {
				builder.append(",");
			}
		}		
		result = builder.toString();
		
		
		resultBean = new AreaCodeBean(result);
		
		return resultBean;
	}
}

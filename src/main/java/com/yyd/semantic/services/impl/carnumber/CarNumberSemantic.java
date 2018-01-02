package com.yyd.semantic.services.impl.carnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.db.bean.region.CarNumber;
import com.yyd.semantic.db.bean.region.City;
import com.yyd.semantic.db.bean.region.District;
import com.yyd.semantic.db.bean.region.Province;
import com.yyd.semantic.db.bean.region.RegionLevel;
import com.yyd.semantic.db.service.region.CarNumberService;
import com.yyd.semantic.db.service.region.CityService;
import com.yyd.semantic.db.service.region.DistrictService;
import com.yyd.semantic.db.service.region.ProvinceService;


@Component
public class CarNumberSemantic implements Semantic<CarNumberBean>{	
	@Autowired
	private CarNumberService carNumberService;
	
	@Autowired
	private ProvinceService provService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private DistrictService districtService;
	
	@Override
	public CarNumberBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CarNumberBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case CarNumberIntent.QUERY_CARNUMBER: {
				result = queryNumber(objects, semanticContext);
				break;
			}	
			case CarNumberIntent.QUERY_REGION: {
				result = queryRegion(objects, semanticContext);
				break;
			}	
			default: {
				result = new CarNumberBean("这句话太复杂了，我还不能理解");
				break;
			}
		}
		return result;
	}
	
	
	private CarNumberBean queryNumber(Map<String, String> slots, SemanticContext semanticContext) {
		String result ="我听不懂你在说什么";		
		CarNumberBean resultBean = null;
		List<CarNumber> listCarNumber = new ArrayList<CarNumber>();
		
		String provShort =slots.get(CarNumberSlot.REGION_PROV_SHORT);
		String provFull = slots.get(CarNumberSlot.REGION_PROV_FULL);
		String cityShort = slots.get(CarNumberSlot.REGION_CITY_SHORT);
		String cityFull = slots.get(CarNumberSlot.REGION_CITY_FULL);			
		String districtShort = slots.get(CarNumberSlot.REGION_DISTRICT_SHORT);
		String districtFull =slots.get(CarNumberSlot.REGION_DISTRICT_FULL);		
		
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
			//地名校验
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
						//TODO:省+县级区域情况暂不考虑，语法上也没有这中语法
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
			
						
			//先根据县一级区域查找车牌，如果找不到再根据上一级区域查找
			if(districtList.isEmpty()) {
				result = "我还不知道该地车牌号";
			}
			else
			{
				//2.先根据级级区域查找车牌
				List<CarNumber> tmpCarNumber = new ArrayList<CarNumber>();
				for(District area:districtList) {
					List<CarNumber> list = carNumberService.getByAreaIdAndLevel(area.getAreaId(), RegionLevel.LEVEL_DISTRICT);
					tmpCarNumber.addAll(list);
				}	
					
				//3.再根据地级区域查找车牌
				if(tmpCarNumber.isEmpty()) {
					for(District area:districtList) {
						List<CarNumber> list = carNumberService.getByAreaIdAndLevel(area.getUpper(), area.getUpperLevel());
						tmpCarNumber.addAll(list);
					}	
				}				
				
				//TODO:县级区域重名的，只返回第一个区域 
				if(null != districtList.get(0).getUnit()) {
					targetDistrict = districtList.get(0).getName() + districtList.get(0).getUnit();
				}
				else
				{
					targetDistrict = districtList.get(0).getName();
				}
				
				if(tmpCarNumber.size() > 0) {
					listCarNumber.add(tmpCarNumber.get(0));
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
				result = "我还不知道该地区车牌";
			}
			else
			{
				for(City city:cityList) {
					List<CarNumber> list = carNumberService.getByAreaIdAndLevel(city.getAreaId(),RegionLevel.LEVEL_CITY);
					listCarNumber.addAll(list);
				}				
			}
			
		}
		else if(null != provs) {
			//根据省级区域查找			
			if(provs.isEmpty()) {
				result = "我还不知道该地车牌";
			}
			else
			{	
				//获取该省下面的地级区域及直接管辖的县级区域
				Province  prov = provs.get(0);
				citys = cityService.getByUpper(prov.getAreaId());	
				districts = districtService.getByUpperAndLevel(prov.getAreaId(), RegionLevel.LEVEL_PROVINCE);;
				
				if(null != citys) {
					for(City city:citys) {
						List<CarNumber> list = carNumberService.getByAreaIdAndLevel(city.getAreaId(), RegionLevel.LEVEL_CITY);
						listCarNumber.addAll(list);
					}	
				}
				
				if(null != districts) {
					for(District district:districts) {
						List<CarNumber> list = carNumberService.getByAreaIdAndLevel(district.getAreaId(), RegionLevel.LEVEL_DISTRICT);
						listCarNumber.addAll(list);
					}	
				}
							
			}
			
		}
		
		StringBuilder builder = new StringBuilder();
		for(int i =0; i < listCarNumber.size();i++) {
			if(targetDistrict != null) {
				builder.append(targetDistrict+" ");
			}
			else
			{
				builder.append(listCarNumber.get(i).getName()+" ");
			}
			
			builder.append(listCarNumber.get(i).getCode());
			
			if(i != listCarNumber.size()-1) {
				builder.append(",");
			}
		}	
		if(!listCarNumber.isEmpty()) {
			result = builder.toString();
		}
				
		resultBean = new CarNumberBean(result);			
		
		
		return resultBean;
	}
	
	
	private CarNumberBean queryRegion(Map<String, String> slots, SemanticContext semanticContext) {
		String result ="我听不懂你在说什么";		
		String carNumber =slots.get(CarNumberSlot.REGION_CARNUMBER);
		CarNumberBean resultBean = null;
		
		if(null == carNumber) {
			resultBean = new CarNumberBean(result);
			return resultBean;
		}
		
		List<CarNumber> carNumberList = carNumberService.getByNumber(carNumber);
		if(null == carNumberList || carNumberList.isEmpty()) {
			result = "我还不知道"+carNumber+"是哪里的车牌";
			resultBean = new CarNumberBean(result);
			return resultBean;
		}
			
		List<CarNumber> provPostCode = new ArrayList<CarNumber>();
		List<CarNumber> cityPostCode = new ArrayList<CarNumber>();
		List<CarNumber> districtPostCode = new ArrayList<CarNumber>();	
		
		for(CarNumber code:carNumberList) {
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
			for(CarNumber code:provPostCode) {
				areas.add(code.getName());
			}
		}
		else if(!cityPostCode.isEmpty()) {
			for(CarNumber code:cityPostCode) {
				areas.add(code.getName());
			}
		}
		
		//特殊处理：海南省有些县级区域与地级区域车牌号相同，但这些区域没有隶属关系
		if(!districtPostCode.isEmpty()) {
			for(CarNumber code:districtPostCode) {
				areas.add(code.getName());
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
		
		
		resultBean = new CarNumberBean(result);
		
		return resultBean;
	}
}

package com.yyd.semantic.services.impl.carnumber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
				String msg = CarNumberError.getMsg(CarNumberError.ERROR_UNKNOW_INTENT);
				result = new CarNumberBean(CarNumberError.ERROR_UNKNOW_INTENT,msg);
				break;
			}
		}
		return result;
	}
	
	
	private CarNumberBean queryNumber(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = CarNumberError.ERROR_NO_RESOURCE;
		
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
				if(!targetProv.getAreaId().equals(targetCity.getUpper())) {
					verifyCity = false;
				}
			}
			
			List<District> districtList = new ArrayList<District>();
			if(verifyCity) {					
				for(District district:districts) {					
					boolean verifyDistrict = true;
					Integer upperLevel = district.getUpperLevel();
					if(upperLevel.equals(RegionLevel.LEVEL_CITY)) {
						if(null != targetCity && !targetCity.getAreaId().equals(district.getUpper())) {
							verifyDistrict = false;
						}
						
						//省+县级区域情况
						if(null == targetCity && null != targetProv) {
							List<City> targetCitys = cityService.getByAreaId(district.getUpper());
							if(null != targetCitys && targetCitys.size() > 0) {
								City tmpCity = null;
								//地级市不会重名，因此只取第一个
								tmpCity = targetCitys.get(0);
								if(!tmpCity.getUpper().equals(targetProv.getAreaId())) {
									verifyDistrict = false;									
								}
							}
							
						}
					}
					else if(upperLevel.equals(RegionLevel.LEVEL_PROVINCE))
					{
						//省直辖的县级区域，中间不能再出现地级区域
						if(null != targetProv && !targetProv.getAreaId().equals(district.getUpper())) {
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
				//此处表明地址校验失败,即没有地址通过校验
				if(districts.size() > 0) {
					errorCode = CarNumberError.ERROR_REGION_NAME_ERROR;
				}
			}
			else
			{
				//2.先根据县级区域查找车牌
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
				
				//县级区域要加上它的上一级区域名字
				if(tmpCarNumber.size() > 0) {
					for(int i=0; i < tmpCarNumber.size();i++) {
						CarNumber area = tmpCarNumber.get(i);
						if(area.getLevel() == RegionLevel.LEVEL_PROVINCE || area.getLevel() == RegionLevel.LEVEL_CITY) {
							area.setName(area.getName()+targetDistrict);
						}
						else if(area.getLevel() ==RegionLevel.LEVEL_DISTRICT) {
							Province tmpProv = targetProv;
							City tmpCity = targetCity;							
							String upperName = null;
							
							if(area.getUpperLevel() == RegionLevel.LEVEL_PROVINCE) {
								List<Province> provList = provService.getByAreaId(area.getUpper());
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
							else if(area.getUpperLevel() == RegionLevel.LEVEL_CITY)
							{
								List<City> cityList = cityService.getByAreaId(area.getUpper());
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
								area.setName(upperName+area.getName());
							}
						}
						
						listCarNumber.add(area);
					}
					
				}
			}
		}
		else if(null !=citys) {
			//地名校验
			List<City> cityList = new ArrayList<City>();
			if(null != targetProv) {
				for(City city:citys) {
					if(targetProv.getAreaId().equals(city.getUpper())) {
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
				//此处表明地址校验失败,即没有地址通过校验
				if(citys.size() > 0) {
					errorCode = CarNumberError.ERROR_REGION_NAME_ERROR;
				}
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
				errorCode = CarNumberError.ERROR_NO_RESOURCE;
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
		
		
		//检查地名是否一样，因为有些地方下面有多个车牌号，如省级单位或直辖市
		boolean same = false;
		Set<String> names = new HashSet<String>();
		for(int i=0; i<listCarNumber.size();i++) {
			names.add(listCarNumber.get(i).getName());
		}
		if(listCarNumber.size() > 1 && names.size() == 1) {
			same = true;
		}
		
		String result = null;
		StringBuilder builder = new StringBuilder();
		for(int i =0; i < listCarNumber.size();i++) {	
			if(!same) {
				builder.append(listCarNumber.get(i).getName()+" ");	
			}
			else
			{
				if(0 == i) {
					builder.append(listCarNumber.get(i).getName()+" ");	
				}
			}
			
			builder.append(listCarNumber.get(i).getCode());
			
			if(i != listCarNumber.size()-1) {
				builder.append(",");
			}
		}	
		if(!listCarNumber.isEmpty()) {
			result = builder.toString();
		}
		if(null != result) {
			errorCode = CarNumberError.ERROR_SUCCESS;
		}
		
		
		if(errorCode.equals(CarNumberError.ERROR_SUCCESS)) {
			resultBean = new CarNumberBean(result);		
		}
		else
		{
			String msg = CarNumberError.getMsg(errorCode);			
			if(errorCode.equals(CarNumberError.ERROR_REGION_NAME_ERROR)) {
				//地名校验失败的错误单独处理，因为其他家的服务对此问题一般会回答错误
				resultBean = new CarNumberBean(msg);
			}
			else
			{
				resultBean = new CarNumberBean(errorCode,msg);
			}
					
		}
			
		
		
		return resultBean;
	}
	
	
	private CarNumberBean queryRegion(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = CarNumberError.ERROR_NO_RESOURCE;
		
		String carNumber =slots.get(CarNumberSlot.REGION_CARNUMBER);
		CarNumberBean resultBean = null;
		
		if(null == carNumber) {
			errorCode = CarNumberError.ERROR_NO_SLOG_DATA;
			String msg = CarNumberError.getMsg(errorCode);
			resultBean = new CarNumberBean(errorCode,msg);
			return resultBean;
		}
		
		List<CarNumber> carNumberList = carNumberService.getByNumber(carNumber);
		if(null == carNumberList || carNumberList.isEmpty()) {
			errorCode = CarNumberError.ERROR_NO_RESOURCE;
			String msg = CarNumberError.getMsg(errorCode);
			resultBean = new CarNumberBean(errorCode,msg);
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
			//县级区域加上上一级区域名
			for(CarNumber code:districtPostCode) {				
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
		
		String result = null;
		StringBuilder builder = new StringBuilder();
		for(int i =0; i < areas.size();i++) {
			builder.append(areas.get(i));
			if(i != areas.size()-1) {
				builder.append(",");
			}
		}		
		result = builder.toString();
		if(null != result) {
			errorCode = CarNumberError.ERROR_SUCCESS;
		}
		
		
		if(errorCode.equals(CarNumberError.ERROR_SUCCESS)) {
			resultBean = new CarNumberBean(result);
		}
		else
		{
			String msg = CarNumberError.getMsg(errorCode);
			resultBean = new CarNumberBean(errorCode,msg);
		}
		
		
		return resultBean;
	}
}

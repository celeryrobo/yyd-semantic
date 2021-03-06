package com.yyd.semantic.services.impl.postcode;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.db.bean.region.City;
import com.yyd.semantic.db.bean.region.District;
import com.yyd.semantic.db.bean.region.PostCode;
import com.yyd.semantic.db.bean.region.Province;
import com.yyd.semantic.db.bean.region.RegionLevel;
import com.yyd.semantic.db.service.region.CityService;
import com.yyd.semantic.db.service.region.DistrictService;
import com.yyd.semantic.db.service.region.PostCodeService;
import com.yyd.semantic.db.service.region.ProvinceService;

@Component
public class PostCodeSemantic implements Semantic<PostCodeBean>{	
	@Autowired
	private PostCodeService postService;
	
	@Autowired
	private ProvinceService provService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private DistrictService districtService;

	
	@Override
	public PostCodeBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PostCodeBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
			case PostCodeIntent.QUERY_POSTCODE: {
				result = queryCode(objects, semanticContext);
				break;
			}	
			case PostCodeIntent.QUERY_REGION: {
				result = queryRegion(objects, semanticContext);
				break;
			}	
			default: {
				String msg = PostCodeError.getMsg(PostCodeError.ERROR_UNKNOW_INTENT);
				result = new PostCodeBean(PostCodeError.ERROR_UNKNOW_INTENT,msg);
				break;
			}
		}
		return result;
	}
	
	
	private PostCodeBean queryCode(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = PostCodeError.ERROR_NO_RESOURCE;
		
		List<PostCode> listPostCode = new ArrayList<PostCode>();
		
		String provShort =slots.get(PostCodeSlot.REGION_PROV_SHORT);
		String provFull = slots.get(PostCodeSlot.REGION_PROV_FULL);
		String cityShort = slots.get(PostCodeSlot.REGION_CITY_SHORT);
		String cityFull = slots.get(PostCodeSlot.REGION_CITY_FULL);			
		String districtShort = slots.get(PostCodeSlot.REGION_DISTRICT_SHORT);
		String districtFull =slots.get(PostCodeSlot.REGION_DISTRICT_FULL);	
		
		List<Province> provs = null;
		List<City> citys = null;
		List<District> districts  = null;
		City targetCity = null;
		Province targetProv = null;
		
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
						
			//根据县级区域查找				
			if(districtList.isEmpty()) {
				//此处表明地址校验失败,即没有地址通过校验
				if(districts.size() > 0) {
					errorCode = PostCodeError.ERROR_REGION_NAME_ERROR;
				}
			}
			else
			{
				List<PostCode> tmpPostCode = new ArrayList<PostCode>();
				for(District area:districtList) {
					List<PostCode> list = postService.getByAreaIdAndLevel(area.getAreaId(), RegionLevel.LEVEL_DISTRICT);
					tmpPostCode.addAll(list);
				}	
				
				//县级区域要加上它的上一级区域名字
				if(tmpPostCode.size() > 0) {
					for(int i =0;i < tmpPostCode.size();i++) {
						Province tmpProv = targetProv;
						City tmpCity = targetCity;
						PostCode area = tmpPostCode.get(i);
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
												
						listPostCode.add(area);
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
					errorCode = PostCodeError.ERROR_REGION_NAME_ERROR;
				}
				
			}
			else
			{
				for(City city:cityList) {
					List<PostCode> list = postService.getByAreaIdAndLevel(city.getAreaId(),RegionLevel.LEVEL_CITY);
					listPostCode.addAll(list);
				}				
			}
			
		}
		else if(null != provs) {
			//根据省级区域查找							
			if(provs.isEmpty()) {
				errorCode = PostCodeError.ERROR_NO_RESOURCE;
			}
			else
			{
				//暂时只列出省下面的地级区域的邮编，不列省直辖的县级区域的邮编
				citys = cityService.getByUpper(provs.get(0).getAreaId());				
				if(null != citys) {
					for(City city:citys) {						
						List<PostCode> list = postService.getByAreaIdAndLevel(city.getAreaId(), RegionLevel.LEVEL_CITY);
						listPostCode.addAll(list);
					}	
				}
							
			}
			
		}
		
		String result = null;
		StringBuilder builder = new StringBuilder();		
		for(int i =0; i < listPostCode.size();i++) {
			builder.append(listPostCode.get(i).getName()+" ");
			builder.append(listPostCode.get(i).getCode());
			
			if(i != listPostCode.size()-1) {
				builder.append(",");
			}
		}				
		if(!listPostCode.isEmpty()) {
			result = builder.toString();
		}
		if(null != result) {
			errorCode = PostCodeError.ERROR_SUCCESS;
		}
		
		
		PostCodeBean resultBean = null;
		if(errorCode.equals(PostCodeError.ERROR_SUCCESS)) {
			resultBean = new PostCodeBean(result);
		}
		else
		{
			String msg = PostCodeError.getMsg(errorCode);
			if(errorCode.equals(PostCodeError.ERROR_REGION_NAME_ERROR)) {
				//地名校验失败的错误单独处理，因为其他家的服务对此问题一般会回答错误
				resultBean = new PostCodeBean(msg);
			}
			else
			{
				resultBean = new PostCodeBean(errorCode,msg);
			}
			
		}
				
		return resultBean;
	}
	
	/**
	 * 根据邮编号码查询区域
	 * @param slots
	 * @param semanticContext
	 * @return
	 */
	private PostCodeBean queryRegion(Map<String, String> slots, SemanticContext semanticContext) {
		Integer errorCode = PostCodeError.ERROR_NO_RESOURCE;
		
		String postCode =slots.get(PostCodeSlot.REGION_POSTCODE);
		PostCodeBean resultBean = null;
		
		if(null == postCode) {
			errorCode = PostCodeError.ERROR_NO_SLOG_DATA;
			String msg = PostCodeError.getMsg(errorCode);
			resultBean = new PostCodeBean(errorCode,msg);
			return resultBean;
		}
		
		List<PostCode> postCodeList = postService.getByCode(postCode);
		if(null == postCodeList || postCodeList.isEmpty()) {
			errorCode = PostCodeError.ERROR_NO_RESOURCE;
			String msg = PostCodeError.getMsg(errorCode);
			resultBean = new PostCodeBean(errorCode,msg);
			return resultBean;
		}
			
		List<PostCode> provPostCode = new ArrayList<PostCode>();
		List<PostCode> cityPostCode = new ArrayList<PostCode>();
		List<PostCode> districtPostCode = new ArrayList<PostCode>();	
		
		for(PostCode code:postCodeList) {
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
			for(PostCode code:provPostCode) {
				areas.add(code.getName());
			}
		}
		else if(!cityPostCode.isEmpty()) {
			for(PostCode code:cityPostCode) {
				areas.add(code.getName());
			}
		}
		else if(!districtPostCode.isEmpty()) {
			//县级区域加上上一级区域名
			for(PostCode code:districtPostCode) {
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
				
				
				if(null !=upperName) {
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
			errorCode = PostCodeError.ERROR_SUCCESS;
		}
		
		
		if(errorCode.equals(PostCodeError.ERROR_SUCCESS)) {
			resultBean = new PostCodeBean(result);
		}
		else
		{
			String msg = PostCodeError.getMsg(errorCode);
			resultBean = new PostCodeBean(errorCode,msg);
		}		
		
		return resultBean;
	}
}

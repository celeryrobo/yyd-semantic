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
				result = new PostCodeBean("这句话太复杂了，我还不能理解");
				break;
			}
		}
		return result;
	}
	
	
	private PostCodeBean queryCode(Map<String, String> slots, SemanticContext semanticContext) {
		String result ="我听不懂你在说什么";		
		PostCodeBean resultBean = null;
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
						//TODO:省+县级区域情况暂不考虑，语法上也没有这种语法
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
						
			//根据县级区域查找				
			if(districtList.isEmpty()) {
				result = "我还不知道该地邮编";
			}
			else
			{
				//只返回第一个县级区域邮编
				List<PostCode> tmpPostCode = new ArrayList<PostCode>();
				for(District area:districtList) {
					List<PostCode> list = postService.getByAreaIdAndLevel(area.getAreaId(), RegionLevel.LEVEL_DISTRICT);
					tmpPostCode.addAll(list);
				}	
				
				//TODO:县级区域重名的，只返回第一个区域 
				if(tmpPostCode.size() > 0) {
					listPostCode.add(tmpPostCode.get(0));
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
				result = "我还不知道该地邮编";
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
				result = "我还不知道该地邮编";
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
		
		
		resultBean = new PostCodeBean(result);
		
		return resultBean;
	}
	
	/**
	 * 根据邮编号码查询区域
	 * @param slots
	 * @param semanticContext
	 * @return
	 */
	private PostCodeBean queryRegion(Map<String, String> slots, SemanticContext semanticContext) {
		String result ="我听不懂你在说什么";		
		String postCode =slots.get(PostCodeSlot.REGION_POSTCODE);
		PostCodeBean resultBean = null;
		
		if(null == postCode) {
			resultBean = new PostCodeBean(result);
			return resultBean;
		}
		
		List<PostCode> postCodeList = postService.getByCode(postCode);
		if(null == postCodeList || postCodeList.isEmpty()) {
			result = "我还不知道"+postCode+"是哪里的邮编";
			resultBean = new PostCodeBean(result);
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
			for(PostCode code:districtPostCode) {
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
		resultBean = new PostCodeBean(result);
		
		return resultBean;
	}
}

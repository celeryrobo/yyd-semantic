package com.yyd.semantic.services.impl.calc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumToChn {
	static String CHN_NUMBER[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};  
    static String CHN_UNIT[] = {"", "十", "百", "千"};          //权位  
    static String CHN_UNIT_SECTION[] = {"", "万", "亿", "万亿"}; //节权位  
    static Set<String> allSymbols = new HashSet<String>();
    
    static{
    	for(int i =0; i < CHN_NUMBER.length;i++){
    		if(CHN_NUMBER[i] != null && !CHN_NUMBER[i].isEmpty()){
    			allSymbols.add(CHN_NUMBER[i]);
    		}
    	}
    	
    	for(int i =0; i < CHN_UNIT.length;i++){
    		if(CHN_UNIT[i] != null && !CHN_UNIT[i].isEmpty()){
    			allSymbols.add(CHN_UNIT[i]);
    		}
    	}
    	
    	for(int i =0; i < CHN_UNIT_SECTION.length;i++){
    		if(CHN_UNIT_SECTION[i] != null && !CHN_UNIT_SECTION[i].isEmpty()){
    			allSymbols.add(CHN_UNIT_SECTION[i]);
    		}
    	}
    }
  
    
    
    /** 
     * 阿拉伯数字转换为中文数字的核心算法实现。 
     * @param num为需要转换为中文数字的阿拉伯数字，是无符号的整形数 
     * @return 
     */  
    public static String NumberToChn(int num) {  
        StringBuffer returnStr = new StringBuffer();  
        Boolean needZero = false;  
        int pos=0;           //节权位的位置  
        if(num==0){  
            //如果num为0，进行特殊处理。  
            returnStr.insert(0,CHN_NUMBER[0]);  
        }  
        while (num > 0) {  
            int section = num % 10000;  
            if (needZero) {  
                returnStr.insert(0, CHN_NUMBER[0]);  
            }  
            String sectionToChn = SectionNumToChn(section);  
            //判断是否需要节权位  
            sectionToChn += (section != 0) ? CHN_UNIT_SECTION[pos] : CHN_UNIT_SECTION[0];  
            returnStr.insert(0, sectionToChn);  
            needZero = ((section < 1000 && section > 0) ? true : false); //判断section中的千位上是不是为零，若为零应该添加一个零。  
            pos++;  
            num = num / 10000;  
        }  
        return returnStr.toString();  
    }  
  
    /** 
     * 将四位的section转换为中文数字 
     * @param section 
     * @return 
     */  
    public static String SectionNumToChn(int section) {  
        StringBuffer returnStr = new StringBuffer();  
        int unitPos = 0;       //节权位的位置编号，0-3依次为个十百千;  
  
        Boolean zero = true;  
        while (section > 0) {  
  
            int v = (section % 10);  
            if (v == 0) {  
                if ((section == 0) || !zero) {  
                    zero = true; /*需要补0，zero的作用是确保对连续的多个0，只补一个中文零*/  
                    //chnStr.insert(0, chnNumChar[v]);  
                    returnStr.insert(0, CHN_NUMBER[v]);  
                }  
            } else {  
                zero = false; //至少有一个数字不是0  
                StringBuffer tempStr = new StringBuffer(CHN_NUMBER[v]);//数字v所对应的中文数字  
                tempStr.append(CHN_UNIT[unitPos]);  //数字v所对应的中文权位  
                returnStr.insert(0, tempStr);  
            }  
            unitPos++; //移位  
            section = section / 10;  
        }  
        return returnStr.toString();  
    }  
    
    
    /** 
     * 中文转换成阿拉伯数字，中文字符串除了包括0-9的中文汉字，还包括十，百，千，万等权位。 
     * 此处是完成对这些权位的类型定义。 
     * name是指这些权位的汉字字符串。 
     * value是指权位多对应的数值的大小。诸如：十对应的值的大小为10，百对应为100等 
     * secUnit若为true，代表该权位为节权位，即万，亿，万亿等 
     */  
    public static class Chn_Name_value{  
        String name;  
        int value;  
        Boolean secUnit;  
        public Chn_Name_value(String name,int value,Boolean secUnit){  
            this.name=name;  
            this.value=value;  
            this.secUnit=secUnit;  
        }  
    }  
    static Chn_Name_value chnNameValue[]={  
        new Chn_Name_value("十",10,false),  
        new Chn_Name_value("百",100,false),  
        new Chn_Name_value("千",1000,false),  
        new Chn_Name_value("万",10000,true),  
        new Chn_Name_value("亿",100000000,true)  
};  
	
	/** 
     * 返回中文数字汉字所对应的阿拉伯数字，若str不为中文数字，则返回-1 
     * @param str 
     * @return 
     */  
    public static int ChnNumToValue(String str){  
        for(int i=0;i<CHN_NUMBER.length;i++){  
            if(str.equals(CHN_NUMBER[i])){  
                return i;  
            }  
        }  
        return -1;  
    }  
  
    /** 
     * 返回中文汉字权位在chnNameValue数组中所对应的索引号，若不为中文汉字权位，则返回-1 
     * @param str 
     * @return 
     */  
    public static int ChnUnitToValue(String str){  
        for(int i=0;i<chnNameValue.length;i++){  
            if(str.equals(chnNameValue[i].name)){  
                return i;  
            }  
        }  
        return -1;  
    }  
    
    /**
     * 检测是否为汉字数字内容
     * @param c
     * @return
     */
    public static boolean isHanZiNum(char c) {
    	String str = String.valueOf(c);
    	if(allSymbols.contains(str)) {
    		return true;
    	}
    	
    	return false;
    }
    
      
    /**
     * 将包含中文数字的字符串替换为英文数字
     * @param text
     * @return
     */
    public static String replaceHanzi(String text) {
    	String result = null;
    	if(null == text || text.isEmpty()) {
    		return null;
    	}
    	
    	String tmpText = text;
    	tmpText = tmpText.replace("两", "二");
    	List<IndexRange> ranges = new ArrayList<IndexRange>();
    	
    	char[] array = tmpText.toCharArray();
    	int start = -1;
    	IndexRange range = null;
    	for(int i =0; i < array.length;i++) {
    		String str = String.valueOf(array[i]);
    		if(allSymbols.contains(str)) {
    			if(start < 0) {
    				start = i;
    			}    			
    		}
    		else
    		{
    			if(start >= 0) {
    				range = new IndexRange(start,i-1);
    				ranges.add(range);
    				range = null;
    				start = -1;
    			}
    		}
    	}
    	//加载最后一段内容
    	if(start >= 0){
    		range = new IndexRange(start,tmpText.length()-1);
			ranges.add(range);
			start = -1;
    	}
    	
    	
    	StringBuilder build = new StringBuilder();
    	for(int i=0; i < ranges.size();i++) {
    		String tmp = tmpText.substring(ranges.get(i).letf, ranges.get(i).right+1);
    		int p1 = 0;
    		int p2 = 0;
    		
    		if(i <=0) {
    			p1 = 0;
    		}
    		else
    		{
    			p1 = ranges.get(i-1).right+1;
    		}	    		
    		
    		p2 = ranges.get(i).letf;
    	
    		
    		if(p2 > p1){
    			String pre = tmpText.substring(p1,p2);
	    		build.append(pre);  
    		}	    		  	
    		Integer numStr = ChnStringToNumber(tmp);
    		build.append(numStr.toString());   		
    	}
    	
    	//加载最后一段内容
    	if(ranges.size() > 0 && ranges.get(ranges.size()-1).right+1 <= tmpText.length()-1){
    		build.append(tmpText.substring(ranges.get(ranges.size()-1).right+1));
    	}
    	else	    		
    	{
    		//没有中文数字
    		build.append(tmpText);
    	}
    	result = build.toString();
    	return result;
    	
    }
    
    
    
    /**
     * 检查是否为合法的中文数字,并将不规范的汉字数字转化为规范化的数字
     * @param text
     * @return
     */
    public static String verifyAndFilter(String text){
    	return null;
    }
    
    public static int ChnStringToNumber(String str){  
	        int returnNumber=0;  
	        int section=0;  
	        int pos=0;  
	        int number=0;  
	        while (pos<str.length()){  
	            int num=ChnNumToValue(str.substring(pos,pos+1));  
	            //若num>=0，代表该位置（pos），所对应的是数字不是权位。若小于0，则表示为权位  
	            if(num>=0){  
	                number=num;  
	                pos++;  
	                //pos是最好一位，直接将number加入到section中。  
	                if(pos>=str.length()){  
	                    section+=number;  
	                    returnNumber+=section;  
	                    break;  
	                }  
	            }else{  
	                int chnNameValueIndex=ChnUnitToValue(str.substring(pos,pos+1));  
	                //chnNameValue[chnNameValueIndex].secUnit==true，表示该位置所对应的权位是节权位，  
	                if(chnNameValue[chnNameValueIndex].secUnit){  
	                    section=(section+number)*chnNameValue[chnNameValueIndex].value;  
	                    returnNumber+=section;  
	                    section=0;  
	                }else{  
	                    section+=number*chnNameValue[chnNameValueIndex].value;  
	                }  
	                pos++;  
	                number=0;  
	                if(pos>=str.length()){  
	                    returnNumber+=section;  
	                    break;  
	                }  
	            }  
	        }  
	        return returnNumber;  
	    } 
    
    
    
}

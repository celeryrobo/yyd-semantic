package com.yyd.semantic.services.impl.calc;

import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;



@Component
public class CalcSemantic implements Semantic<CalcBean>{
	@Override
	public CalcBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CalcBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		String text = ybnfCompileResult.getText();		
		switch (action) {
			case CalcIntent.CALC_SIMPLE_ADD: {
				result = calcSimpleAdd(text);
				break;
			}	
			case CalcIntent.CALC_SIMPLE_SUB: {
				result = calcSimpleSub(text);
				break;
			}
			case CalcIntent.CALC_SIMPLE_MUL: {
				result = calcSimpleMul(text);
				break;
			}	
			case CalcIntent.CALC_SIMPLE_DIV: {				
				result = calcSimpleDiv(text);
				break;
			}	
			case CalcIntent.CALC_MULTI: {
				result = calcSimpleMulti(text);
				break;
			}	
			default: {
				String msg = CalcError.getMsg(CalcError.ERROR_UNKNOW_INTENT);
				result = new CalcBean(CalcError.ERROR_UNKNOW_INTENT,msg);
				break;
			}
		}
	
		return result;
	}
		
	/**
	 * 将加号中文替换为符号
	 * @param text
	 * @return
	 */
	private String replaceAdd(String text) {
		String tmpText = text;
		tmpText = tmpText.replace("然后加上", "+");
		tmpText = tmpText.replace("之后加上", "+");
		tmpText = tmpText.replace("最后加上", "+");
		tmpText = tmpText.replace("然后加", "+");
		tmpText = tmpText.replace("之后加", "+");
		tmpText = tmpText.replace("最后加", "+");
		tmpText = tmpText.replace("然后再加上", "+");
		tmpText = tmpText.replace("之后再加上", "+");
		tmpText = tmpText.replace("最后再加上", "+");
		tmpText = tmpText.replace("然后再加", "+");
		tmpText = tmpText.replace("之后再加", "+");
		tmpText = tmpText.replace("最后再加", "+");
		tmpText = tmpText.replace("再加上", "+");
		tmpText = tmpText.replace("再加", "+");
		tmpText = tmpText.replace("加上", "+");
		tmpText = tmpText.replace("加", "+");
		return tmpText;
	}
	/**
	 * 将减号中文替换为符号
	 * @param text
	 * @return
	 */
	private String replaceSub(String text) {
		String tmpText = text;
		tmpText = tmpText.replace("然后减去", "-");
		tmpText = tmpText.replace("之后减去", "-");
		tmpText = tmpText.replace("最后减去", "-");
		tmpText = tmpText.replace("然后减", "-");
		tmpText = tmpText.replace("之后减", "-");
		tmpText = tmpText.replace("最后减", "-");
		tmpText = tmpText.replace("然后再减去", "-");
		tmpText = tmpText.replace("之后再减去", "-");
		tmpText = tmpText.replace("最后再减去", "-");
		tmpText = tmpText.replace("然后再减", "-");
		tmpText = tmpText.replace("之后再减", "-");
		tmpText = tmpText.replace("最后再减", "-");
		tmpText = tmpText.replace("再减去", "-");
		tmpText = tmpText.replace("再减", "-");
		tmpText = tmpText.replace("减去", "-");
		tmpText = tmpText.replace("减", "-");
		return tmpText;
	}
	/**
	 * 将乘号中文替换为符号
	 * @param text
	 * @return
	 */
	private String replaceMul(String text) {
		String tmpText = text;
		tmpText = tmpText.replace("然后乘以", "*");
		tmpText = tmpText.replace("之后乘以", "*");
		tmpText = tmpText.replace("最后乘以", "*");
		tmpText = tmpText.replace("然后乘上", "*");
		tmpText = tmpText.replace("之后乘上", "*");
		tmpText = tmpText.replace("最后乘上", "*");
		tmpText = tmpText.replace("然后乘", "*");
		tmpText = tmpText.replace("之后乘", "*");
		tmpText = tmpText.replace("最后乘", "*");	
		tmpText = tmpText.replace("然后再乘以", "*");
		tmpText = tmpText.replace("之后再乘以", "*");
		tmpText = tmpText.replace("最后再乘以", "*");
		tmpText = tmpText.replace("然后再乘上", "*");
		tmpText = tmpText.replace("之后再乘上", "*");
		tmpText = tmpText.replace("最后再乘上", "*");
		tmpText = tmpText.replace("然后再乘", "*");
		tmpText = tmpText.replace("之后再乘", "*");
		tmpText = tmpText.replace("最后再乘", "*");
		tmpText = tmpText.replace("再乘以", "*");
		tmpText = tmpText.replace("乘以", "*");
		tmpText = tmpText.replace("乘上", "*");
		tmpText = tmpText.replace("再乘", "*");
		tmpText = tmpText.replace("乘", "*");
		return tmpText;
	}
	/**
	 * 将除号中文替换为符号
	 * @param text
	 * @return
	 */
	private String replaceDiv(String text) {
		String tmpText = text;
		tmpText = tmpText.replace("然后除以", "/");
		tmpText = tmpText.replace("之后除以", "/");
		tmpText = tmpText.replace("最后除以", "/");
		tmpText = tmpText.replace("然后除", "/");
		tmpText = tmpText.replace("之后除", "/");
		tmpText = tmpText.replace("最后除", "/");		
		tmpText = tmpText.replace("然后再除以", "/");
		tmpText = tmpText.replace("之后再除以", "/");
		tmpText = tmpText.replace("最后再除以", "/");
		tmpText = tmpText.replace("然后再除", "/");
		tmpText = tmpText.replace("之后再除", "/");
		tmpText = tmpText.replace("最后再除", "/");
		tmpText = tmpText.replace("再除以", "/");
		tmpText = tmpText.replace("除以", "/");
		tmpText = tmpText.replace("再除", "/");
		tmpText = tmpText.replace("除", "/");
		return tmpText;
	}
	
	/**
	 * 运算符替换
	 * @param text
	 * @return
	 */
	private String replaceOperator(String text) {
		if(null == text || text.isEmpty()) {
			return null;
		}
		
		String tmpText = text;
		tmpText = tmpText.replace("×", "*");
		tmpText = tmpText.replace("÷", "/");
		tmpText = tmpText.replace("（", "(");
		tmpText = tmpText.replace("）", ")");
		return tmpText;
	}
	
	/**
	 * 将汉字数字替换为阿拉伯数字
	 * @param text
	 * @return
	 */
	private String replaceHanzi(String text) {
		String tmpText = NumToChn.replaceHanzi(text);		
		return tmpText;
	}
	
	private boolean isNum(char c) {
		if(c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}
	
	private boolean isOperator(char c) {
		if(c=='=' || c=='*'||c=='+'||c=='-'||c=='/') {
			return true;
		}
		return false;
	}
	
	/**
	 * 提取问句中的数学符表达式
	 * @param text
	 * @return
	 */
	private String getExpression(String text) {
		if(null==text || text.isEmpty()) {
			return text;
		}
		
		String tmpText = text.replace(" ", "");
		char[] array = tmpText.toCharArray();
		int start = -1;
		int end = -1;
		for(int i=0; i < array.length;i++) {
			if(array[i] == '(' || isNum(array[i])) {
				start = i;
				break;
			}
		}
		
		for(int i=array.length-1; i >= 0;i--) {
			if(array[i] =='=' || array[i] == ')' ||isNum(array[i])) {
				end = i;
				break;
			}
		}
		
		if(start <0 || end <0) {
			return null;
		}
		
		String result = text.substring(start,end+1);
		//数学表达式不要有"="
		result = result.replace("=", "");
		result = result.replaceAll(" ", "");
		
		return result;
	}
	
	/**
	 * 验证数学表达式
	 * @param text
	 * @return
	 */
	private boolean verifyExpresision(String text) {
		if(null == text || text.isEmpty()) {
			return false;
		}
		
		char[] array = text.toCharArray();
		for(int i=0; i < array.length;i++) {
			if(isNum(array[i]) || isOperator(array[i]) || array[i] == '.'||array[i] == '('||array[i] == ')'||array[i] == '=') {
				continue;
			}
			else
			{
				return false;
			}
		}
		
		
		return true;
	}
	
	private String calc(String expression) {		
		ScriptEngineManager manager = new ScriptEngineManager();  
        ScriptEngine engine = manager.getEngineByExtension("js");  
        Object result = null;
		try {
			result = engine.eval(expression);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  
       	String ret = result.toString();
		return ret;
	}
	
	private CalcBean calcSimpleAdd(String text) {
		Integer errorCode = CalcError.ERROR_SUCCESS;
		String result = null;
		String tmpText = text;
		tmpText = replaceAdd(tmpText);
		tmpText = replaceOperator(tmpText);
		tmpText = replaceHanzi(tmpText);
		String expression = getExpression(tmpText);
		if(verifyExpresision(expression)) {
			result = calc(expression);
			if(null == result || result.isEmpty()) {
				errorCode = CalcError.ERROR_CALC_FAIL;
			}
		}
		else
		{
			errorCode = CalcError.ERROR_EXPRESSION;
		}
		
		CalcBean  resultBean = null;
		if(errorCode.equals(CalcError.ERROR_SUCCESS)) {
			resultBean = new CalcBean(expression+"="+result);
		}
		else
		{
			String msg = CalcError.getMsg(errorCode);
			resultBean = new CalcBean(errorCode,msg);
		}
		
		return resultBean;
	}
	private CalcBean calcSimpleSub(String text) {
		Integer errorCode = CalcError.ERROR_SUCCESS;
		String result = null;
		String tmpText = text;
		tmpText = replaceSub(tmpText);
		tmpText = replaceOperator(tmpText);
		tmpText = replaceHanzi(tmpText);
		String expression = getExpression(tmpText);
		if(verifyExpresision(expression)) {
			result = calc(expression);
			if(null == result || result.isEmpty()) {
				errorCode = CalcError.ERROR_CALC_FAIL;
			}			
		}
		else
		{
			errorCode = CalcError.ERROR_EXPRESSION;
		}
		
		CalcBean  resultBean = null;
		if(errorCode.equals(CalcError.ERROR_SUCCESS)) {
			resultBean = new CalcBean(expression+"="+result);
		}
		else
		{
			String msg = CalcError.getMsg(errorCode);
			resultBean = new CalcBean(errorCode,msg);
		}
		
		return resultBean;
	}
	private CalcBean calcSimpleMul(String text) {
		Integer errorCode = CalcError.ERROR_SUCCESS;
		String result = null;
		String tmpText = text;
		tmpText = replaceMul(tmpText);
		tmpText = replaceOperator(tmpText);
		tmpText = replaceHanzi(tmpText);
		String expression = getExpression(tmpText);
		if(verifyExpresision(expression)) {
			result = calc(expression);
			if(null == result || result.isEmpty()) {
				errorCode = CalcError.ERROR_CALC_FAIL;
			}
		}
		else
		{
			errorCode = CalcError.ERROR_EXPRESSION;
		}
		
		CalcBean  resultBean = null;
		if(errorCode.equals(CalcError.ERROR_SUCCESS)) {
			resultBean = new CalcBean(expression+"="+result);
		}
		else
		{
			String msg = CalcError.getMsg(errorCode);
			resultBean = new CalcBean(errorCode,msg);
		}
		
		return resultBean;
	}
	private CalcBean calcSimpleDiv(String text) {
		Integer errorCode = CalcError.ERROR_SUCCESS;
		String result = null;
		String tmpText = text;
		tmpText = replaceDiv(tmpText);
		tmpText = replaceOperator(tmpText);
		tmpText = replaceHanzi(tmpText);
		String expression = getExpression(tmpText);
		if(verifyExpresision(expression)) {
			result = calc(expression);
			if(null == result || result.isEmpty()) {
				errorCode = CalcError.ERROR_CALC_FAIL;
			}
			else if(result.equalsIgnoreCase("Infinity")) {
				errorCode = CalcError.ERROR_DIVISOR_ZERO;
			}
		}
		else
		{
			errorCode = CalcError.ERROR_EXPRESSION;
		}
		
		CalcBean  resultBean = null;
		if(errorCode.equals(CalcError.ERROR_SUCCESS)) {
			resultBean = new CalcBean(expression+"="+result);
		}
		else
		{
			String msg = CalcError.getMsg(errorCode);
			resultBean = new CalcBean(errorCode,msg);
		}
		
		return resultBean;
	}
	private CalcBean calcSimpleMulti(String text) {
		Integer errorCode = CalcError.ERROR_SUCCESS;
		String result = null;
		String tmpText = text;
		tmpText = replaceAdd(tmpText);
		tmpText = replaceSub(tmpText);
		tmpText = replaceMul(tmpText);
		tmpText = replaceDiv(tmpText);
		tmpText = replaceOperator(tmpText);
		tmpText = replaceHanzi(tmpText);
		String expression = getExpression(tmpText);
		if(verifyExpresision(expression)) {
			result = calc(expression);
			if(null == result || result.isEmpty()) {
				errorCode = CalcError.ERROR_CALC_FAIL;
			}
			else if(result.equalsIgnoreCase("Infinity")) {
				errorCode = CalcError.ERROR_DIVISOR_ZERO;
			}
		}
		else
		{
			errorCode = CalcError.ERROR_EXPRESSION;
		}
		
		CalcBean  resultBean = null;
		if(errorCode.equals(CalcError.ERROR_SUCCESS)) {
			resultBean = new CalcBean(expression+"="+result);
		}
		else
		{
			String msg = CalcError.getMsg(errorCode);
			resultBean = new CalcBean(errorCode,msg);
		}
		
		return resultBean;
	}
	
}

package com.yyd.semantic.services.impl.geling;

import java.util.HashMap;
import java.util.Map;

public class GeLingSlot {
	/**
	 * 语义槽参数,汉语拼音-->汉字
	 */
	static Map<String,String> params =new  HashMap<String,String>();
	
	static {
		params.put("quweituya","趣味涂鸦");
		params.put("qiqiaoban","七巧板");
		params.put("renshizhiwu","趣味涂鸦");
		params.put("renshidongwu","认识动物");
		params.put("zhiwushijie","植物世界");
		params.put("renshishucai","认识蔬菜");
		params.put("renshi1to5","认识1-5");
		params.put("renshituxing","认识图形");
		params.put("yingwenerge","英文儿歌");
		params.put("zhongwenerge","中文儿歌");
		params.put("yinyuebaoweizhan","音乐保卫战");
		params.put("xiaoxiaojiazimu","小小架子鼓");
		params.put("yiqianlingyiye","一千零一夜");
		params.put("chengyugushi","成语故事");
		params.put("renwugushi","人物故事");
		params.put("yanyugushi","谚语故事");
		params.put("tonghuagushi","童话故事");
		params.put("yisuoyuyan","伊索寓言");
		params.put("sanzijing","三字经");
		params.put("dizigui","弟子规");
		params.put("baijiaxing","百家姓");
		params.put("qianziwen","千字文");
		params.put("lunyu","论语");
		params.put("zengguangxianwen","增广贤文");
		params.put("pinyinrenzhi","拼音认知");
		params.put("kuaileshizi","快乐识字");
		params.put("MYABC","MY ABC");
		params.put("dongmanbeidanci","动漫背单词");
		params.put("shouhuiertongyingyu","手绘儿童英语");
		params.put("ertonghudongkouyu","儿童互动口语");
		params.put("zaojiaorenzhi","早教认知");
		params.put("mofayuedu","魔法阅读");		
		params.put("xiaoxuediandu","小学点读");
		params.put("mingshijingjiang","名师精讲");
		params.put("xiaoxuejiaofu","小学教辅");
		params.put("xiaoxueaoshu","小学奥数");
		params.put("chuzhongtongbumingshi","初中同步名师");
		params.put("chuzhongtongbujiaofu","初中同步教辅");
		params.put("chuzhongtongbutiku","初中同步题库");
		params.put("zhongkaojingjiang","中考精讲");
		params.put("chengyudiangu","成语典故");
		params.put("dongwushijie","动物世界");
		params.put("shangxiawuqiannian","上下五千年");
		params.put("shijielishi","世界历史");
		params.put("kewaiyuedu","课外阅读");
		params.put("dilizhishi","地理知识");
		params.put("zhengzhizhishi","政治知识");
		params.put("shengwuzhishi","生物常识");
		params.put("huaxuegongshi","化学公式");
		params.put("wuligongshi","物理公式");
		params.put("yuansuzhouqibiao","元素周期表");
		params.put("xiandaihanyucidian","现代汉语词典");
		params.put("hanyingdacidian","汉英大词典");
		params.put("yinghandacidian","英汉大词典");
		params.put("shiyongyinghancidian","实用英汉词典");
		params.put("shiyonghanyingcidian","实用汉英词典");
		params.put("tongjinfanyicidian","同近反义词典");
		params.put("zhonghuachengyu","中华成语");
		params.put("guhanyucidian","古汉语词典");
		params.put("quangongnengcidian","全功能词典");
		params.put("gaozhongtongbumingshi","高中同步名师");
		params.put("gaozhongtongbujiaofu","高中同步教辅");
		params.put("gaozhongtongbutiku","高中同步题库");
		params.put("gaokaojingjiang","高考精讲");		
	}

	public static String get(String pinyin) {
		String hanzi = params.get(pinyin);
		return hanzi;
	}
}

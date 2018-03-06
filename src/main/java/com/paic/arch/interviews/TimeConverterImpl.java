package com.paic.arch.interviews;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author xiaoluhuan
 *
 */
public class TimeConverterImpl implements TimeConverter {

	private static final Logger LOG = LoggerFactory.getLogger(TimeConverterImpl.class);

	@Override
	public String convertTime(String aTime) {
		
		String result = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			dateFormat.parse(aTime);
		} catch (ParseException e) {
			LOG.error("aTime[{}] parameter is error", aTime);
			throw new RuntimeException(aTime + "parameter is error");
		}

		String[] timeAry = aTime.split(":");
		String[] dateAry = new String[5];

		// 1.处理第一排秒
		dateAry[0] = Integer.parseInt(timeAry[2]) % 2 == 0 ? TimeContants.LampColor.Y.name()
				: TimeContants.LampColor.O.name();

		// 2.处理第二排小时 (每个灯代表5个小时)
		int hours = Integer.parseInt(timeAry[0]);
		int totalRedNum = hours / 5;
		dateAry[1] = getParseResult(TimeContants.BASE_SHOW_FOUR, 0, totalRedNum, TimeContants.LampColor.R.name());

		// 3.处理第三排小时 (每个灯代表一个小时)
		totalRedNum = hours % 5;
		dateAry[2] = getParseResult(TimeContants.BASE_SHOW_FOUR, 0, totalRedNum, TimeContants.LampColor.R.name());

		int minute = Integer.parseInt(timeAry[1]);
		// 4.处理第四排(每个灯代表五分钟)
		totalRedNum = minute / 5;
		dateAry[3] = getParseResult(TimeContants.BASE_SHOW_TWELVE, totalRedNum, TimeContants.BASE_SHOW_TWELVE.length(),
				TimeContants.LampColor.O.name());

		// 5.处理第五排(每个灯代表1分钟)
		totalRedNum = minute % 5;
		dateAry[4] = getParseResult(TimeContants.BASE_SHOW_FOUR, 0, totalRedNum, TimeContants.LampColor.Y.name());
		
		return String.join(System.getProperty("line.separator"),dateAry);
	}
    
	/**
	 * 替换字符串
	 * @param source 
	 * @param start 
	 * @param end 
	 * @param replaceChar 
	 * @return
	 */
	public String getParseResult(String source, int start, int end, String replaceChar) {
		String replaceStr = "";
		for (int i = 0; i < end - start; i++) {
			replaceStr += replaceChar;
		}
		return new StringBuffer(source).replace(start, end, replaceStr).toString();
	}

	public static void main(String args[]) {
		TimeConverter converter = new TimeConverterImpl();
		converter.convertTime("23:00:00:00");
	}

}

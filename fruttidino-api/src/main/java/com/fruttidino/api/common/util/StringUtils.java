package com.fruttidino.api.common.util;

import java.math.BigDecimal;
import java.math.MathContext;

import static com.fruttidino.api.common.util.Calculator.divide;
import static com.fruttidino.api.common.util.Calculator.multiply;

public class StringUtils {
    public static String padLeftZero20(String txt) {
        return String.format("%022.2f", Double.parseDouble(txt));
    }

    public static String padLeftZero20(long idx) {
        return String.format("%020d", idx);
    }

    public static String formatValue(String val, int scale) {
        String[] arr = val.split("\\.");
        if (arr.length==2 && arr[1].length() > scale) {
            arr[1] = arr[1].substring(0, scale);
            val = arr[0]+"."+arr[1];
        }
        return val;
    }
    
    public static String padRightZero(String val) {
        String[] arr = val.split("\\.");
        if (arr.length==2) {
        	char[] chars = arr[1].toCharArray();
        	for(int i = chars.length - 1; i >= 0; i--) {
        		if(chars[i] != '0') {
        			arr[1] = arr[1].substring(0, i+1);
                    val = arr[0]+"."+arr[1];
                    break;
        		}   
        		
        		val = arr[0];
        	}
        }
        return val;
    }

    public static String formatValue(String val) {
        return formatValue(val, 20);
    }

    public static String setScale(String val) {
        return formatValue(val, 20);
    }

    public static boolean isZero(String vol, int scale) {
        String v = formatValue(vol, scale);
        if(0 >= Double.parseDouble(v)) {
            return true;
        }
        return false;
    }

    public static boolean isZero(String vol) {
        return isZero(vol, 8);
    }
    
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}
	
	/**
	 * text 에 repl 값을 with 로 max 만큼 교체 한다. <br>
	 * @param text
	 * @param repl
	 * @param with
	 * @param max
	 * @return
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (isEmpty(text) || isEmpty(repl) || with == null || max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(repl, start);
		if (end == -1) {
			return text;
		}
		int replLength = repl.length();
		int increase = with.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(repl, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * null 이거나 공백만으로 이루어졌을 경우 true 반환 <br>
	 * 내부적으로 trim() method 로 공백 제거
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
    /**
     * 수수료금액 계산
     *
     * @param volumn
     * @param feeRate
     * @return
     */
    public static String getFee(String volumn, String feeRate, int scale) {
        return multiply(volumn, divide(feeRate, "100"));
    }

    public static String getFee(String volumn, String feeRate) {
        return getFee(volumn, feeRate, 10);
    }

    /**
     * 비교연산 v1이 크면 1, 작으면 -1, 같으면 0
     * @param v1
     * @param v2
     * @return
     */
    public static int compare(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1, MathContext.UNLIMITED);
        BigDecimal b2 = new BigDecimal(v2, MathContext.UNLIMITED);
        if(b1.compareTo(b2)==1) {
            return 1;
        } else if (b1.compareTo(b2)==-1) {
            return -1;
        } else {
            return 0;
        }
    }
    
    
    public static void main(String[] args) {
    	String value = "1.0000";
    	
    	System.out.println(StringUtils.padRightZero(value));
    	

    }
}
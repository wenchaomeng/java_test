package javabasic.idgenerator;

/**
 * @author wenchao.meng
 * <p>
 * Dec 26, 2017
 */

import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * 身份证号码,可以解析身份证号码的各个字段，以及验证身份证号码是否有效<br>
 * 身份证号码构成：6位地址编码+8位生日+3位顺序码+1位校验码
 *
 */
public class ValidateIDCard {

    public static final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};// 18位身份证中，前17位数字各个数字的生成校验码时的权值
    public static final String[] LASTCODE = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};// 18位身份证中最后一位校验码


    /**
     * 身份证号验证(18位新身份证号)
     * @param validateStr 身份证号码
     * @return true:合法 | false:非法
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidIDCardNum(String validateStr) {
        if (validateStr.length() != 18) {
            return false;
        }
        String regex = "[0-9]{17}[0-9|xX]$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(validateStr).matches()) {//前17位数字，第18位数字或字母X
            return false;
        }
        Hashtable areacodeHashtable = new Hashtable();
        areacodeHashtable.put("11", "北京");
        areacodeHashtable.put("12", "天津");
        areacodeHashtable.put("13", "河北");
        areacodeHashtable.put("14", "山西");
        areacodeHashtable.put("15", "内蒙古");
        areacodeHashtable.put("21", "辽宁");
        areacodeHashtable.put("22", "吉林");
        areacodeHashtable.put("23", "黑龙江");
        areacodeHashtable.put("31", "上海");
        areacodeHashtable.put("32", "江苏");
        areacodeHashtable.put("33", "浙江");
        areacodeHashtable.put("34", "安徽");
        areacodeHashtable.put("35", "福建");
        areacodeHashtable.put("36", "江西");
        areacodeHashtable.put("37", "山东");
        areacodeHashtable.put("41", "河南");
        areacodeHashtable.put("42", "湖北");
        areacodeHashtable.put("43", "湖南");
        areacodeHashtable.put("44", "广东");
        areacodeHashtable.put("45", "广西");
        areacodeHashtable.put("46", "海南");
        areacodeHashtable.put("50", "重庆");
        areacodeHashtable.put("51", "四川");
        areacodeHashtable.put("52", "贵州");
        areacodeHashtable.put("53", "云南");
        areacodeHashtable.put("54", "西藏");
        areacodeHashtable.put("61", "陕西");
        areacodeHashtable.put("62", "甘肃");
        areacodeHashtable.put("63", "青海");
        areacodeHashtable.put("64", "宁夏");
        areacodeHashtable.put("65", "新疆");
        areacodeHashtable.put("71", "台湾");
        areacodeHashtable.put("81", "香港");
        areacodeHashtable.put("82", "澳门");
        areacodeHashtable.put("91", "国外");
        if (areacodeHashtable.get(validateStr.substring(0, 2)) == null) {
            return false;
        }
        String birthdate = validateStr.substring(6, 14);//获取生日
        int birthyear = Integer.parseInt(birthdate.substring(0, 4));//出生年份
        int curyear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);//当前时间年份
        if (birthyear >= curyear || birthyear < 1900) {//不在1900年与当前时间之间
            return false;
        }
        String dateRegex = "^((\\d{2}(([02468][048])|([13579][26]))[\\/\\/\\s]?((((0 ?[13578])|(1[02]))[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\/\\/\\s]?((((0?[13578])|(1[02]))[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if (!Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matches(dateRegex, birthdate)) {//出生年月日不合法
            return false;
        }
        String tempLastCode = generateVerifyCode(validateStr.substring(0, 17));//临时记录身份证号码最后一位
        if (validateStr.substring(17).equals(tempLastCode)) {//最后一位符合
            return true;
        } else {
            return false;
        }
    }

    public static String generateVerifyCode(String id17){

        if(id17 == null){
            throw new IllegalArgumentException("null id");
        }

        if(id17.length() != 17){
            throw new IllegalArgumentException("id expected length 17");
        }

        int sum = 0;//前17位号码与对应权重值相乘总和
        for (int i = 0; i < 17; i++) {
            sum += ((int) (id17.charAt(i) - '0')) * WEIGHT[i];
        }
        return LASTCODE[sum % 11];//实际最后一位号码

    }

    public static void main(String[] argc) {

        System.out.println(ValidateIDCard.isValidIDCardNum("370883198411200416"));
        System.out.println(ValidateIDCard.isValidIDCardNum("370883198411200417"));
    }
}
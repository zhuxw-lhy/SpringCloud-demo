/*
package com.cludove.dirigible.instrument.random;

import java.security.Provider;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import com.cludove.dirigible.gear.security.SecurityRandom;

import com.cludove.dirigible.instrument.random.Generator;

*/
/**
 * 随机数对象，用于生成随机数
 *
 * @author leon
 *
 *//*

public class Generator {

    private String randomValue;

    private boolean isAllChars = Boolean.FALSE.booleanValue();

    private Integer length = new Integer(8);

    private List<Character> lower = null;

    private List<Character> upper = null;

    private char[] single = null;

    private int singleCount = 0;

    private boolean isSingle = Boolean.FALSE.booleanValue();

    private String algorithm = null;

    private int digit = 0;

    private Provider provider = null;

    private boolean isSecure = Boolean.FALSE.booleanValue();

    private static Generator generator = null;

    private static Random random = null;

    private static SecureRandom secrandom = null;

    private static Map<String, Object> randomExistValues;

    private Generator() {
        randomExistValues = new HashMap<String, Object>();
    }

    public static Generator getInstance() {
        if (generator == null)
            generator = new Generator();

        return generator;
    }

    */
/**
     * 产生随机数对象
     *
     * @throws Exception
     *//*

    public final void generateRandomObject() throws Exception {
        if (isSecure) {//默认为false
            //获取同一个随机数
            if (secrandom == null)
                secrandom = SecurityRandom.getSecureRandom(algorithm, digit,
                        provider);
        } else {
            if (random == null)
                random = new Random();
        }
    }


    private final void generateRandom() {
        if (isAllChars)
            for (int i = 0; i < length.intValue(); i++)
                randomValue = randomValue
                        + new Character(
                        (char) ((int) 34 + ((int) (getFloat() * 93))))
                        .toString();
        else if (isSingle) {
            if (upper.size() == 3) {
                for (int i = 0; i < length.intValue(); i++) {
                    if (((int) (getFloat() * 100)) % 2 == 0) {
                        if (((int) (getFloat() * 100)) % 2 == 0)
                            randomValue = randomValue
                                    + randomSingle().toString();
                        else
                            randomValue = randomValue
                                    + randomChar((Character) lower.get(2),
                                    (Character) upper.get(2))
                                    .toString();
                    } else {
                        if (((int) (getFloat() * 100)) % 2 == 0)
                            randomValue = randomValue
                                    + randomChar((Character) lower.get(1),
                                    (Character) upper.get(1))
                                    .toString();
                        else
                            randomValue = randomValue
                                    + randomChar((Character) lower.get(0),
                                    (Character) upper.get(0))
                                    .toString();
                    }
                }
            } else if (upper.size() == 2) {
                for (int i = 0; i < length.intValue(); i++) {
                    if (((int) (getFloat() * 100)) % 2 == 0) {
                        randomValue = randomValue + randomSingle().toString();
                    } else if (((int) (getFloat() * 100)) % 2 == 0) {
                        randomValue = randomValue
                                + randomChar((Character) lower.get(1),
                                (Character) upper.get(1)).toString();
                    } else {
                        randomValue = randomValue
                                + randomChar((Character) lower.get(0),
                                (Character) upper.get(0)).toString();
                    }
                }
            } else if (upper.size() == 1) {
                for (int i = 0; i < length.intValue(); i++) {
                    if (((int) getFloat() * 100) % 2 == 0)
                        randomValue = randomValue + randomSingle().toString();
                    else
                        randomValue = randomValue
                                + randomChar((Character) lower.get(0),
                                (Character) upper.get(0)).toString();
                }
            } else {
                for (int i = 0; i < length.intValue(); i++)
                    randomValue = randomValue + randomSingle().toString();
            }
        } else {
            if (upper.size() == 3) {
                for (int i = 0; i < length.intValue(); i++) {
                    if (((int) (getFloat() * 100)) % 2 == 0) {
                        randomValue = randomValue
                                + randomChar((Character) lower.get(2),
                                (Character) upper.get(2)).toString();
                    } else if (((int) (getFloat() * 100)) % 2 == 0) {
                        randomValue = randomValue
                                + randomChar((Character) lower.get(1),
                                (Character) upper.get(1)).toString();
                    } else {
                        randomValue = randomValue
                                + randomChar((Character) lower.get(0),
                                (Character) upper.get(0)).toString();
                    }
                }
            } else if (upper.size() == 2) {
                for (int i = 0; i < length.intValue(); i++) {
                    if (((int) (getFloat() * 100)) % 2 == 0)
                        randomValue = randomValue
                                + randomChar((Character) lower.get(1),
                                (Character) upper.get(1)).toString();
                    else
                        randomValue = randomValue
                                + randomChar((Character) lower.get(0),
                                (Character) upper.get(0)).toString();
                }
            } else
                for (int i = 0; i < length.intValue(); i++)
                    randomValue = randomValue
                            + randomChar((Character) lower.get(0),
                            (Character) upper.get(0)).toString();
        }
    }

    private final Character randomSingle() {

        return (new Character(single[(int) ((getFloat() * singleCount) - 1)]));
    }

    private final Character randomChar(Character lower, Character upper) {
        int tempval;
        char low = lower.charValue();
        char up = upper.charValue();
        tempval = (int) ((int) low + (getFloat() * ((int) (up - low))));
        return (new Character((char) tempval));
    }

    private final float getFloat() {
        if (isSecure)
            return secrandom.nextFloat();
        else
            return random.nextFloat();
    }

    */
/**
     * 取得随机数
     *
     * @return 随机数
     *//*

    public final String getRandom() {
        randomValue = new String();
        generateRandom();
        if (randomExistValues != null) {
            while (randomExistValues.containsKey(randomValue))
                generateRandom();

            randomExistValues.put(randomValue, null);
        }

        return randomValue;
    }

    */
/**
     * 设置范围
     *
     * @param low
     *            下限
     * @param up
     *            上限
     *//*

    public final void setRanges(ArrayList<Character> low, ArrayList<Character> up) {
        lower = low;
        upper = up;
    }

    */
/**
     * 设置长度
     *
     * @param value
     *            长度值
     *//*

    public final void setLength(String value) {
        length = new Integer(value);
    }

    */
/**
     * 设置算法
     *
     * @param value
     *            算法名字
     *//*

    public final void setAlgorithm(String value) {
        algorithm = value;
        isSecure = Boolean.TRUE.booleanValue();
    }

    */
/**
     * 设置加密长度
     *
     * @param value
     *            长度
     *//*

    public final void setDigit(int value) {
        digit = value;
    }

    */
/**
     * 设置算法提供者
     *
     * @param value
     *            算法提供者
     *//*

    public final void setProvider(Provider value) {
        provider = value;
    }

    */
/**
     * 设置是否使用全字符
     *
     * @param value
     *            是否使用
     *//*

    public final void setIsAllchars(boolean value) {
        isAllChars = value;
    }

    */
/**
     * 设置是否使用指定字符
     *
     * @param chars
     *            指定字符
     * @param value
     *            是否使用
     *//*

    public final void setIsSingle(char[] chars, int value) {
        single = chars;
        singleCount = value;
        isSingle = Boolean.TRUE.booleanValue();
    }

    */
/**
     * 添加随机数字符
     *
     * @param value
     *            随机数字符
     *//*

    public final void setCharset(String value) {
        boolean more = Boolean.TRUE.booleanValue();
        lower = new ArrayList<Character>(3);
        upper = new ArrayList<Character>(3);
        if (value.compareTo("all") == 0) {
            isAllChars = Boolean.TRUE.booleanValue();
            more = Boolean.FALSE.booleanValue();
        } else if ((value.charAt(1) == '-') && (value.charAt(0) != '\\')) {
            while (more && (value.charAt(1) == '-')) {
                if (value.charAt(0) == '\\')
                    break;
                else {
                    lower.add(new Character(value.charAt(0)));
                    upper.add(new Character(value.charAt(2)));
                }
                if (value.length() <= 3)
                    more = Boolean.FALSE.booleanValue();
                else
                    value = value.substring(3);
            }
        }

        if (more) {
            single = new char[30];
            StringTokenizer tokens = new StringTokenizer(value);
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if (token.length() > 1)
                    single[singleCount++] = '-';
                single[singleCount++] = token.charAt(0);
            }
        }
        if ((lower == null) && (single == null))
            setCharset("a-zA-Z0-9");
    }

    public boolean isSecure() {
        return isSecure;
    }

    public void setSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

}*/

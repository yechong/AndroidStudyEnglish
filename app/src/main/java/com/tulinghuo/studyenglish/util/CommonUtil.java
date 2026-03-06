package com.tulinghuo.studyenglish.util;

import android.content.Context;

import com.tulinghuo.studyenglish.MyApp;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    private static volatile boolean isStartCountDown = false;

    public static synchronized void startCountDown() {
        if (isStartCountDown) return;
        isStartCountDown = true;
        new Thread(() -> {
            while (true) {
                if (Constant.sendSecond < 1) {
                    isStartCountDown = false;
                    Constant.sendSecond = 60;
                    break;
                }
                Constant.sendSecond--;
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
     * 将dp值转换为px值
     *
     * @param context 上下文
     * @param dp      dp值
     * @return px值
     */
    public static int dpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int spToPx(Context context, float sp) {
        return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static boolean isLogin(Context context) {
        String token = MyApp.getTokenManager().getToken();
        return !isBlank(token);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否为整数
     */
    public static boolean isInteger(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return str.matches("-?\\d+");
    }

    /**
     * 验证邮箱地址是否合法
     *
     * @param email 待验证的邮箱地址
     * @return true-合法邮箱，false-非法邮箱
     */
    public static boolean isValidEmail(String email) {
        // 1. 基础空值检查
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // 2. 去除前后空格
        email = email.trim();

        // 3. 检查长度（一般邮箱地址长度有限制）
        if (email.length() > 254) {
            return false;
        }

        // 4. 检查是否包含@符号，且只能有一个
        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');
        if (atIndex <= 0 || atIndex != lastAtIndex) {
            return false;
        }

        // 5. 分离本地部分和域名部分
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        // 6. 检查本地部分和域名部分是否为空
        if (localPart.isEmpty() || domainPart.isEmpty()) {
            return false;
        }

        // 7. 检查本地部分长度（一般不超过64个字符）
        if (localPart.length() > 64) {
            return false;
        }

        // 8. 检查域名部分是否包含点，且不能以点开始或结束
        if (!domainPart.contains(".") ||
                domainPart.startsWith(".") ||
                domainPart.endsWith(".")) {
            return false;
        }

        // 9. 检查域名部分是否有连续的点
        if (domainPart.contains("..")) {
            return false;
        }

        // 10. 使用正则表达式进行详细验证
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * 根据每天背单词数，计算所需天数，结果是5的倍数
     *
     * @param totalWords
     * @param wordsPerDay
     * @return
     */
    public static int calculateDays(int totalWords, int wordsPerDay) {
        if (totalWords <= 0 || wordsPerDay <= 0) {
            return 0;
        }
        int daysNeeded = (totalWords + wordsPerDay - 1) / wordsPerDay;
        return ((daysNeeded + 4) / 5) * 5;
    }

    /**
     * 根据总体时间，计算每天所需背的单词数，结果是5的倍数
     *
     * @param totalWords
     * @param days
     * @return
     */
    public static int calculatePerDayWords(int totalWords, int days) {
        if (totalWords <= 0 || days <= 0) {
            return 0;
        }
        if (days % 5 != 0) {
            return 0;
        }
        // 计算每天需要的单词数（向上取整）
        int wordsPerDay = (totalWords + days - 1) / days;
        // 调整为5的倍数
        return ((wordsPerDay + 4) / 5) * 5;
    }
}

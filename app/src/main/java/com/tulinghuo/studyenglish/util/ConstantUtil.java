package com.tulinghuo.studyenglish.util;

public class ConstantUtil {

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

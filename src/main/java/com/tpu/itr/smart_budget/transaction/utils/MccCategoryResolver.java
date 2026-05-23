package com.tpu.itr.smart_budget.transaction.utils;

import java.util.Map;

public final class MccCategoryResolver {


    private MccCategoryResolver() {
    }

    // захардкоженные mcc
    private static final Map<Integer, String> MCC_TO_CATEGORY = Map.of(
            5814, "Фастфуд",
            5812, "Рестораны и кафе",
            5411, "Продуктовые магазины",
            5399, "Интернет-магазины",
            4121, "Транспорт и такси",
            5732, "Электроника и техника",
            4899, "Онлайн-сервисы и подписки",
            5651, "Одежда и обувь"
    );

    /**
     * @param mcc MCC-код
     * @return категория на русском языке
     */
    public static String resolveCategory(int mcc) {
        return MCC_TO_CATEGORY.getOrDefault(mcc, "Прочее");
    }
}
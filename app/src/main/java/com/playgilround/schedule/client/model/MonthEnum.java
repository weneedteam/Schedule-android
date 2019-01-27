package com.playgilround.schedule.client.model;

/**
 * 19-01-27
 * Month English -> Number
 */
public enum MonthEnum {
    
    MONTH_ENUM_1("Jan", "01"), MONTH_ENUM_2("Feb", "02"),
    MONTH_ENUM_3("Mar", "03"), MONTH_ENUM_4("Apr", "04"),
    MONTH_ENUM_5("May", "05"), MONTH_ENUM_6("Jun", "06"),
    MONTH_ENUM_7("Jul", "07"), MONTH_ENUM_8("Aug", "08"),
    MONTH_ENUM_9("Sep", "09"), MONTH_ENUM_10("Oct", "10"),
    MONTH_ENUM_11("Nov", "11"), MONTH_ENUM_12("Dec", "12");


    private String monthEng;
    private String monthNum;

    MonthEnum(String monthEng, String monthNum) {
        this.monthEng = monthEng;
        this.monthNum = monthNum;
    }

    public static String getMonthNum(String month) {
        for (int i = 0; i < MonthEnum.values().length; i++) {
            MonthEnum currentMonthEnum = MonthEnum.values()[i];
            if (month.equals(currentMonthEnum.monthEng)) {
                return currentMonthEnum.monthNum;
            }
        }
        return "-01";
    }
}

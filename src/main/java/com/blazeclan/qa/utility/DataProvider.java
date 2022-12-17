package com.blazeclan.qa.utility;

import com.blazeclan.qa.constants.IConstants;

import java.lang.reflect.Method;

public class DataProvider {

    /**
     * getDataFromProvider method is used to read data from different providers
     * @param method test method name
     * @return data in double array
     */
    @org.testng.annotations.DataProvider(name = "DataProvider")
    public static Object[][] getDataFromProvider(Method method) {
        ExcelUtil excel = new ExcelUtil(IConstants.DATA_PROVIDER_FILE_PATH);
//        excel.readExcelData("",1,3,4,6);
        switch (method.getName()) {
            case "" -> {
                return excel.readExcelData("", 1, 4, 5, 6);
            }
            case "  " -> {
                return excel.readExcelData("", 1, 4, 5, 7);
            }
            default -> {
            }
        }
        return null;
    }
}

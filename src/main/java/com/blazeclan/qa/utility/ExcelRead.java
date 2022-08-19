package com.blazeclan.qa.utility;


import com.blazeclan.qa.logging.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelRead {

    private FileInputStream fis;
    private FileOutputStream fos;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFCell cell;
    private CellStyle style;
    private final String path;
    private final File file;
    private XSSFRow row;
    private String srcSheetNo;
    private String fileFullPath;
    private String resultPath;
    private String resultSheetName;

    public ExcelRead(String path) {
        this.path = path;
        file = new File(path);
        try {
            if (!file.exists()) {
                throw new FileNotFoundException("File doesn't Exist");
            }
            fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        } else {
            sheet = workbook.getSheetAt(index);
            System.out.println(sheet.getPhysicalNumberOfRows());
            return sheet.getPhysicalNumberOfRows();
        }
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);

            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(colNum);
            if (cell == null)
                return "";

            if (cell.getCellType().name().equals("STRING")) {
                return cell.getStringCellValue();

            } else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY

                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
                }
                return cellText;

            } else {
                return "";
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.error("row " + rowNum + " or column " + colNum + " does not exist  in sheet");
            return "row " + rowNum + " or column " + colNum + " does not exist  in sheet";
        }
    }

    public boolean isSheetExist(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            Log.error(sheetName + " doesn't exist");
            return index != -1;
        } else {
            return true;
        }
    }

    private int getColumnCount(String sheetName) {
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null)
            return -1;

        return row.getLastCellNum();
    }

    public Object[][] readExcelData(String sheetName, int startRow, int endRow, int startColumn, int endColumn) {
        int noOfRows = endRow - startRow + 1;
        int noOfColumns = endColumn - startColumn + 1;

        String[][] data = new String[noOfRows][noOfColumns];

        int fColumn = startColumn - 1;
        int count;
        count = 0;
        if (isSheetExist(sheetName)) {
            for (int i = startRow; i <= endRow; i++) {
                for (int j = fColumn; j < endColumn; j++) {
                    data[i - startRow][j - fColumn] = getCellData(sheetName, i, j);
                }
                count++;

            }
            System.out.println(count);
        } else {
            data = null;
        }

        return data;
    }

    public void WordReader(String fileName, String sheetno, String ResultPath, String ResultName) {
        try {
            fis = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetno);
            srcSheetNo = sheetno;
            fileFullPath = fileName;
            resultPath = ResultPath;
            resultSheetName = ResultName;
        } catch (FileNotFoundException fnfEx) {
            System.out.println(fileName + " is not Found. please check the file name.");
            System.exit(0);
        } catch (IOException ioEx) {
            System.out.println(fis + " is not Found. please check the path.");
        } catch (Exception ex) {
            System.out.println("There is error reading/loading xls file, due to " + ex);
        }
    }

    public int searchField(String sheetName, int colNum, String value) throws Exception {
        try {
            int rowCount = sheet.getLastRowNum();
            System.out.println("rowCount " + rowCount);

            for (int i = 0; i <= rowCount; i++) {
                if (getCellData(i, colNum).equalsIgnoreCase(value)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw (e);
        }
    }

    public String[] getRowData(int rowNum) throws Exception {
        String[] temp = new String[sheet.getRow(rowNum).getLastCellNum()];

        for (int i = 0; i < temp.length; i++)
            temp[i] = getCellData(rowNum, i);
        return temp;
    }

    public String getCellData(int rowNum, int colNum) throws Exception {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            return cell.getStringCellValue();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCellIntData(int rowNum, int colNum) throws Exception {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            String CellData = String.valueOf(cell.getNumericCellValue());
            CellData = CellData.substring(0, CellData.indexOf("."));
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }


    public ArrayList<HashMap<String, String>> getExcelData() {
        int lastRow = sheet.getLastRowNum();
        System.out.println(lastRow);

        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>(lastRow);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            HashMap<String, String> testdata = new HashMap<String, String>();

            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                try {
                    System.out.println("i:" + i + " " + "j:" + j);
                    testdata.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getStringCellValue());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            result.add(testdata);
        }

        return result;
    }

    public HashMap<String, String> getExcelRowData(int rowNum) {
        HashMap<String, String> map = new HashMap<>();

        for (int j = 0; j < sheet.getRow(rowNum).getLastCellNum(); j++)
            map.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(rowNum).getCell(j).getStringCellValue());
        System.out.println(map);
        return map;
    }

    public int getFirstRowNum() {
        return sheet.getFirstRowNum();
    }

    public int getLastRowNum() {
        return sheet.getLastRowNum();
    }

    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {

            if (rowNum <= 0) return false;
            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;

            if (index == -1) return false;
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);

            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }

            if (colNum == -1) return false;
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);

            if (row == null)
                row = sheet.createRow(rowNum - 1);
            cell = row.getCell(colNum);

            if (cell == null)
                cell = row.createCell(colNum);
            cell.setCellValue(data);
            fos = new FileOutputStream(fileFullPath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addSheet(String filePath, String sheetName) {
        try {
            workbook.createSheet(sheetName);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean removeSheet(String filePath, String sheetName) {
        int index = workbook.getSheetIndex(sheetName);

        if (index == -1) return false;
        try {
            workbook.removeSheetAt(index);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String XSSFCellToString(XSSFCell cell) {
        String cellValue = null;

        if (cell != null) {
            cellValue = cell.toString();
            cellValue = cellValue.trim();
        } else {
            cellValue = "";
        }

        return cellValue;
    }

    public HashMap<String, HashMap<String, String>> getExcelDataAll() {
        int lastRow = sheet.getLastRowNum();
        System.out.println(lastRow);

        HashMap<String, HashMap<String, String>> result = new HashMap<>(lastRow);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            HashMap<String, String> testdata = new HashMap<String, String>();

            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                try {
                    System.out.println("i:" + i + " " + "j:" + j);
                    testdata.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getStringCellValue());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            result.put(sheet.getRow(i).getCell(0).getStringCellValue(), testdata);
        }

        return result;
    }

    public Map<String, String> getRowByColumnVal(String columnValue, int columnNum, int headerRowNum) {
        try {
            Map<String, String> rowMap;

            for (Row tempRow : sheet) {
                String tempContent = tempRow.getCell(columnNum).getRichStringCellValue().toString();
                if (columnValue.compareTo(tempContent) == 0) {
                    int rowNumber = tempRow.getRowNum();
                    rowMap = new HashMap<>();
                    DataFormatter dataFormatter = new DataFormatter();
                    Row headerRow = sheet.getRow(headerRowNum);
                    Row row = sheet.getRow(rowNumber);
                    int totalCells = headerRow.getLastCellNum();
                    for (int i = 0; i < totalCells; i++) {
                        String headerCell = dataFormatter.formatCellValue(headerRow.getCell(i));
                        String rowCell = dataFormatter.formatCellValue(row.getCell(i));
                        rowMap.put(headerCell, rowCell);
                    }
                    return rowMap;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, String> fetchDataFromDataReader(String testCaseId) {

        return getRowByColumnVal(testCaseId, 0, 0);

    }

    /**
     * Remove a row by its index
     *
     * @param rowIndex a 0 based index of removing row
     */
    public void removeRow(int rowIndex) throws IOException {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        fos = new FileOutputStream(fileFullPath);
        workbook.write(fos);
        fos.close();
    }


    public void updateRowValue(String columnValue, String updatedValue) {

        try {

            for (Row tempRow : sheet) {
                String tempContent = tempRow.getCell(0).getRichStringCellValue().toString();
                if (columnValue.compareTo(tempContent) == 0) {
                    int rowNumber = tempRow.getRowNum();
                    Row row = sheet.getRow(rowNumber);
                    Cell cel = sheet.getRow(rowNumber).getCell(0);
                    cel.setCellValue(updatedValue);
                    break;
                }
            }

//	workbook.write(new FileOutputStream(System.getProperty("user.dir") + "\\" + getBundle().getString("TestFolderPath") + "\\" + getBundle().getString("TestdataFileName")));
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();


        }
    }
}
package com.drinks.milk.utils.excel;


import com.drinks.milk.annotation.Excel;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcelUtils <T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";

    private Class<T> clazz;

    public ExcelUtils(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ExcelUtils() {

    }

    /**
     * 下载文件
     * @param response
     * @param fileName
     * @param data
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
//        response.setContentType("application/octet-stream");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")+".xls");
        exportExcel(data, response.getOutputStream());
    }

    /**
     * 创建 表格
     * @param data
     * @param out
     * @throws Exception
     */
    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);
            wb.write(out);
        } finally {
            wb.close();
            out.close();
        }
    }

    /**
     * 将数据写入表格
     * @param wb
     * @param sheet
     * @param data
     */
    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {
        int rowIndex = 0;
        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(sheet, data.getColNames(),data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);
    }

    /**
     * 写入表头
     * @param wb
     * @param sheet
     * @param titles
     * @return
     */
    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;
        Font titleFont = wb.createFont();//获取字体
        titleFont.setFontName("simsun");//设置字体名称（宋体）
        titleFont.setBold(true);//设置字体加粗
        XSSFCellStyle titleStyle = wb.createCellStyle();//获取单元格样式
        titleStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格的水平对齐类型
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格的垂直对齐类型（这里是居中）
        Row titleRow = sheet.createRow(rowIndex);//在该工作簿中创建第一行.
        colIndex = 0;
        for (String field : titles) {//循环创建列
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            colIndex++;
        }
        rowIndex++;//将行数++ 返回用于下面添加数据
        return rowIndex;
    }

    /**
     * 将数据写入
     * @param sheet
     * @param rows
     * @param rowIndex
     * @return
     */
    private static int writeRowsToExcel(Sheet sheet, List<String> colNames, List<Object> rows, int rowIndex) {
        int colIndex = 0;
        for (Object rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            colIndex = 0;
            JSONObject jsonObject = JSONObject.fromObject(rowData);
            for(String name:colNames){
                    Cell cell = dataRow.createCell(colIndex);
                    cell.setCellValue(jsonObject.get(name)+"");
                    colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    /**
     * 自动调整大小
     * @param sheet
     * @param columnNumber
     */
    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 1000);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }


    public List<T> readExcel(Class<T> cls, MultipartFile file){

        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            //logger.error("上传文件格式不正确");
        }
        List<T> dataList = new ArrayList<>();
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if (fileName.endsWith(EXCEL2007)) {
                workbook = new XSSFWorkbook(is);
            }
            if (fileName.endsWith(EXCEL2003)) {
                workbook = new HSSFWorkbook(is);
            }
            if (workbook != null) {
                //类映射  注解 value-->bean columns
                Map<String, List<Field>> classMap = new HashMap<>();
                List<Field> fields = Stream.of(cls.getDeclaredFields()).collect(Collectors.toList());
                fields.forEach(
                        field -> {
                            Excel annotation = field.getAnnotation(Excel.class);
                            if (annotation != null) {
                                String value = annotation.name();
                                if (StringUtils.isBlank(value)) {
                                    return;
                                }
                                if (!classMap.containsKey(value)) {
                                    classMap.put(value, new ArrayList<>());
                                }
                                field.setAccessible(true);
                                classMap.get(value).add(field);
                            }
                        }
                );
                //索引-->columns
                Map<Integer, List<Field>> reflectionMap = new HashMap<>(16);
                //默认读取第一个sheet
                Sheet sheet = workbook.getSheetAt(0);

                boolean firstRow = true;
                for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    //首行  提取注解
                    if (firstRow) {
                        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            if (classMap.containsKey(cellValue)) {
                                reflectionMap.put(j, classMap.get(cellValue));
                            }
                        }
                        firstRow = false;
                    } else {
                        //忽略空白行
                        if (row == null) {
                            continue;
                        }
                        try {
                            T t = cls.newInstance();
                            //判断是否为空白行
                            boolean allBlank = true;
                            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                                if (reflectionMap.containsKey(j)) {
                                    Cell cell = row.getCell(j);
                                    String cellValue = getCellValue(cell);
                                    if (StringUtils.isNotBlank(cellValue)) {
                                        allBlank = false;
                                    }
                                    List<Field> fieldList = reflectionMap.get(j);
                                    fieldList.forEach(
                                            x -> {
                                                try {
                                                    handleField(t, cellValue, x);
                                                } catch (Exception e) {
                                                    //log.error(String.format("reflect field:%s value:%s exception!", x.getName(), cellValue), e);
                                                }
                                            }
                                    );
                                }
                            }
                            if (!allBlank) {
                                dataList.add(t);
                            } else {
                                //log.warn(String.format("row:%s is blank ignore!", i));
                            }
                        } catch (Exception e) {
                            //log.error(String.format("parse row:%s exception!", i), e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            //log.error(String.format("parse excel exception!"), e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    //log.error(String.format("parse excel exception!"), e);
                }
            }
        }
        return dataList;
    }


    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType().getCode() == 0) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                return HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
            } else {
                return new BigDecimal(cell.getNumericCellValue()).toString();
            }
        } else if (cell.getCellType().getCode() == 1) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cell.getCellType().getCode() == 2) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cell.getCellType().getCode() == 3) {
            return "";
        } else if (cell.getCellType().getCode() == 4) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType().getCode() == 5) {
            return "ERROR";
        } else {
            return cell.toString().trim();
        }

    }


    private static <T> void handleField(T t, String value, Field field) throws Exception {
        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }
        if (type == Object.class) {
            field.set(t, value);
            //数字类型
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            //
            field.set(t, value);
        } else if (type == String.class) {
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }

    /**
     * 将数据写入到EXCEL文档
     *
     * @param dataOrQuery
     *            数据集合
     * @return
     * @throws Exception
     */
    public HttpServletResponse writeExcel(Object dataOrQuery, String fileName,
                                          HttpServletResponse response) throws Exception {
        List<T> list = (List) dataOrQuery;
        // 创建并获取工作簿对象
        Workbook wb = getWorkBook(clazz, list);
        // 写入到文件
        OutputStream os = response.getOutputStream();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx","utf-8") );
        wb.write(os);
        os.close();
        return response;
    }

    /**
     * 获得Workbook对象
     *
     * @param list
     *            数据集合
     * @return Workbook
     * @throws Exception
     */
    public Workbook getWorkBook(Class clazz, List<T> list) throws Exception {
        // 创建工作簿
        Workbook wb = new SXSSFWorkbook();
        ExcelDataFormatter edf = new ExcelDataFormatter();
        if (list == null || list.size() == 0){
            return wb;
        }
        // 创建一个工作表sheet
        Sheet sheet = wb.createSheet();
        // 申明行
        Row row = sheet.createRow(0);
        // 申明单元格
        Cell cell = null;

        CreationHelper createHelper = wb.getCreationHelper();

        Field[] fields = clazz.getDeclaredFields();

        XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置前景色
        titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(159, 213, 183)));
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = wb.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        font.setBold(true);
        // 设置字体
        titleStyle.setFont(font);

        int columnIndex = 0;
        Excel excel = null;
        for (Field field : fields) {
            field.setAccessible(true);
            excel = field.getAnnotation(Excel.class);
            //判断字段是否加上注解
            if (excel!=null){
                // 列宽注意乘256
                sheet.setColumnWidth(columnIndex, excel.width() * 256);
                // 写入标题
                cell = row.createCell(columnIndex);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(excel.name());

                columnIndex++;
            }
        }

        int rowIndex = 1;

        CellStyle cs = wb.createCellStyle();

        for (T t : list) {
            row = sheet.createRow(rowIndex);
            columnIndex = 0;
            Object o = null;
            for (Field field : fields) {

                field.setAccessible(true);

                // 忽略标记skip的字段
                excel = field.getAnnotation(Excel.class);
                if (excel == null || excel.skip() == true) {
                    continue;
                }
                // 数据
                cell = row.createCell(columnIndex);

                o = field.get(t);
                // 如果数据为空，设置为空
                if (o == null) {
                    cell.setCellValue("");
                }
                // 处理日期类型
                else if (o instanceof Date) {
                    cs.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                    cell.setCellStyle(cs);
                    cell.setCellValue((Date) field.get(t));
                } else if (o instanceof Double || o instanceof Float) {
                    cell.setCellValue((Double) field.get(t));
                } else if (o instanceof Boolean) {
                    Boolean bool = (Boolean) field.get(t);
                    if (edf == null) {
                        cell.setCellValue(bool);
                    } else {
                        Map<String, String> map = edf.get(field.getName());
                        if (map == null) {
                            cell.setCellValue(bool);
                        } else {
                            cell.setCellValue(map.get(bool.toString().toLowerCase()));
                        }
                    }

                } else if (o instanceof Integer) {

                    Integer intValue = (Integer) field.get(t);

                    if (edf == null) {
                        cell.setCellValue(intValue);
                    } else {
                        Map<String, String> map = edf.get(field.getName());
                        if (map == null) {
                            cell.setCellValue(intValue);
                        } else {
                            cell.setCellValue(map.get(intValue.toString()));
                        }
                    }
                } else if (o instanceof String) {

                    String stringValue = (String) field.get(t);

                    if (edf == null) {
                        cell.setCellValue(stringValue);
                    } else {
                        Map<String, String> map = edf.get(field.getName());
                        if (map == null) {
                            cell.setCellValue(stringValue);
                        } else {
                            cell.setCellValue(map.get(stringValue.toString()));
                        }
                    }
                } else {
                    cell.setCellValue(field.get(t).toString());
                }

                columnIndex++;
            }

            rowIndex++;
        }

        return wb;
    }
}
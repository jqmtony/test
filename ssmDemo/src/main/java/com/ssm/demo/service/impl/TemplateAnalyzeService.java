package com.ssm.demo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.demo.service.ITemplateAnalyzeSerivce;


@Service
public class TemplateAnalyzeService implements ITemplateAnalyzeSerivce {

  /**
   * 应用日志器
   */
  protected Log logger = LogFactory.getLog(getClass());

  /**
   * excel格式验证 2003 xls 2007 xlsx
   */
  @Override
  public Boolean validationEXl(MultipartFile file) {
    // TODO Auto-generated method stub
    // 获取file的格式
    if (null == file) {
      // 导入的文件出错！
      logger.info("Import File Error！");
      return false;
    }
    try {
      String fileName = file.getOriginalFilename();
      String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.lastIndexOf(".") + 4);
      // 兼容 2007
      if (fileName.endsWith("xlsx")) {
        return true;
      }
      if (!"xls".equals(fileType)) {
        // 导入的文件格式不正确，该文件格式为
        logger.info("The imported file format is not correct,the right is " + fileType);
        return false;
      }
      return true;
    } catch (Exception e) {
      if (e instanceof IOException) {
        // 文件名获取出错
        logger.info("ERROR--------------the name of the file is error");
      } else {
        logger.info("ERROR--------------" + e.toString());
      }
    }
    return false;
  }

  @Override
  public List<Object[]> converExlToList(InputStream inputStream) {
    try {
      List<Object[]> list = getRowsByFirstRow(inputStream, 0);
      if (null == list || list.size() == 0)
        return list;
      return list;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  // 2007 xlsx 转换成数组
  @Override
  public List<Object[]> converXlsxToList(InputStream inputStream) {
    // TODO Auto-generated method stub
    try {
      List<Object[]> list = getRowsByFirstRowFromXlsx(inputStream, 0);
      if (null == list || list.size() == 0)
        return list;
      return list;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;

  }

  public static List<Object[]> getRowsByFirstRow(InputStream is, int sheetIndex) throws IOException {
    List<Object[]> result = new ArrayList<Object[]>();
    try {
      POIFSFileSystem fs = new POIFSFileSystem(is);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(sheetIndex);

      int rowNum = sheet.getPhysicalNumberOfRows();
      int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
      HSSFRow row = null;
      HSSFCell cell = null;
      for (int i = 1; i < rowNum; i++) {
        row = sheet.getRow(i);
        if (isEmptyRow(row)) {
          continue;
        }
        // cellNum = row.getPhysicalNumberOfCells();

        Object[] objs = new Object[cellNum];
        for (int j = 0; j < cellNum; j++) {
          cell = row.getCell(j);
          objs[j] = getCellValue(cell);
        }
        result.add(objs);
      }

    } catch (Exception e) {
      throw new IOException(e);

    } finally {
      is.close();
    }
    return result;
  }

  /**
   * @param is
   * @param sheetIndex
   * @return
   * @throws IOException
   */
  public static List<Object[]> getRowsByFirstRowFromXlsx(InputStream is, int sheetIndex) throws IOException {
    // 结果集列表
    List<Object[]> result = new ArrayList<Object[]>();
    try {
      XSSFWorkbook book = new XSSFWorkbook(is);
      XSSFSheet sheet = book.getSheetAt(sheetIndex);
      // 获取行数rowNum 和每行的栏数cellNum
      int rowNum = sheet.getPhysicalNumberOfRows();
      int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
      XSSFRow row = null;
      XSSFCell cell = null;
      // 获取每一行
      for (int i = 0; i < rowNum; i++) {
        row = sheet.getRow(i);
        if (row == null) {
          continue;
        }
        // if (isEmptyRow(row)) {
        // continue;
        // }
        Object[] objs = new Object[cellNum];
        for (int j = 0; j < cellNum; j++) {
          cell = row.getCell(j);
          if (cell == null) {
            continue;
          }
          objs[j] = getCellValue(cell);
        }
        result.add(objs);
      }
    } catch (Exception e) {
      throw new IOException(e);

    } finally {
      is.close();
    }
    return result;

  }

 
  private static Object getCellValue(HSSFCell cell) {
    Object value = null;
    if (cell != null) {
      // get the type of the cell
      int cellType = cell.getCellType();
      switch (cellType) {
      // ""
      case HSSFCell.CELL_TYPE_BLANK:
        value = "";
        break;
      // Boolean
      case HSSFCell.CELL_TYPE_BOOLEAN:
        value = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
        break;
      // Error
      case HSSFCell.CELL_TYPE_ERROR:
        value = "ERR-" + cell.getErrorCellValue();
        break;
      // Formula
      case HSSFCell.CELL_TYPE_FORMULA:
        value = cell.getCellFormula();
        break;
      // Numeric
      case HSSFCell.CELL_TYPE_NUMERIC:
        // Date
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue();
        }
        // Number
        else {
          value = cell.getNumericCellValue() + "";
        }
        break;
      // String
      case HSSFCell.CELL_TYPE_STRING:
        value = cell.getStringCellValue();
        break;
      // Other
      default:
        value = "Unknown Cell Type:" + cell.getCellType();
      }
    }
    return value;

  }

  private static Object getCellValue(XSSFCell cell) {
    Object value = null;
    if (cell != null) {
      // get the type of the cell
      int cellType = cell.getCellType();
      switch (cellType) {
      // ""
      case XSSFCell.CELL_TYPE_BLANK:
        value = "";
        break;
      // Boolean
      case XSSFCell.CELL_TYPE_BOOLEAN:
        value = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
        break;
      // Error
      case XSSFCell.CELL_TYPE_ERROR:
        value = "ERR-" + cell.getErrorCellValue();
        break;
      // Formula
      case XSSFCell.CELL_TYPE_FORMULA:
        value = cell.getCellFormula();
        break;
      // Numeric
      case XSSFCell.CELL_TYPE_NUMERIC:
        // Date
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue();
        }
        // Number
        else {
          value = cell.getNumericCellValue() + "";
        }
        break;
      // String
      case XSSFCell.CELL_TYPE_STRING:
        value = cell.getStringCellValue();
        break;
      // Other
      default:
        value = "Unknown Cell Type:" + cell.getCellType();
      }
    }
    return value;

  }

  public static boolean isEmptyRow(XSSFRow row) {

    if (row == null) {
      return true;
    }

    XSSFCell cell = null;
    int cellNum = row.getPhysicalNumberOfCells();
    int blankNum = 0; // 为空的数量

    // Object[] objs = new Object[cellNum];

    for (int j = 0; j < cellNum; j++) {
      cell = row.getCell(j);
      Object val = getCellValue(cell);
      if (val == null || (val instanceof String && StringUtils.isBlank(val.toString()))) {
        blankNum++;
      }
    }

    if (cellNum != blankNum) {
      return false;
    }

    return true;
  }

  public static boolean isEmptyRow(HSSFRow row) {

    if (row == null) {
      return true;
    }

    HSSFCell cell = null;
    int cellNum = row.getPhysicalNumberOfCells();
    int blankNum = 0; // 为空的数量

    // Object[] objs = new Object[cellNum];

    for (int j = 0; j < cellNum; j++) {
      cell = row.getCell(j);
      Object val = getCellValue(cell);
      if (val == null || (val instanceof String && StringUtils.isBlank(val.toString()))) {
        blankNum++;
      }
    }

    if (cellNum != blankNum) {
      return false;
    }

    return true;
  }
 
}
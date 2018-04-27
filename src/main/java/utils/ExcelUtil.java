package utils;

import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel 工具类
 */
public class ExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 根据execl数据类型返回数据
     *
     * @param cell
     * @param formula
     * @return
     */
    public static Object getCellData(Cell cell, FormulaEvaluator formula) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                //return cell.getRichStringCellValue().getString();
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    return df.format(cell.getNumericCellValue());
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return formula.evaluate(cell).getNumberValue();
            default:
                return "";
        }
    }

    /**
     * 读取excel时检查版本，是否能创建对象，工作簿
     *
     * @param in
     * @return
     */
    public static Workbook create(InputStream in) {
        try {
            if (in == null) {
                return null;
            }
            return WorkbookFactory.create(in);
        } catch (Exception e) {
            log.error("创建 excel解析对象异常：" + e.getMessage());
            return null;
        }
    }


}

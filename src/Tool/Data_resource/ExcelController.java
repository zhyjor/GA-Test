package Tool.Data_resource;

import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author 武浩
 */
public class ExcelController {

    public java.util.List data;
    private String workbookname;
    private String sheetname;
    private int num;
    
    private Workbook wb;
    public Sheet sheet;
    public jxl.Cell cell;
    private WritableWorkbook wwb;
    
    public String in = "C:\\Users\\WWW\\Desktop\\";
    public String out = "C:\\Users\\WWW\\Desktop\\";

    public static void main(String[] args) throws IOException, BiffException, WriteException {
        ExcelController e = new ExcelController();
        e.getWorkbook("www");
        e.getSheet(0);
        String[] c = e.getColumn(0);
        System.out.println(c.length);
        int hang[] = {1, 3, 5};
        int lie[] = {2, 4, 6};
        String[][] con = new String[3][3];
        int x = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                con[i][j] = "" + x++;
            }
        }

        e.Reviser(hang, lie, con);

        e.End();
    }

    public void getWorkbook(String name) throws IOException, BiffException {
        workbookname = name;
        wb = Workbook.getWorkbook(new File(in + name + ".xls"));
    }

    public void getSheet(String name) {
        sheetname = name;
        sheet = wb.getSheet(name);
    }

    public void getSheet(int i) {
        num = i;
        sheet = wb.getSheet(i);
    }

    public void outWorkbook(String name) throws IOException, BiffException {
        workbookname = name;
        wb = Workbook.getWorkbook(new File(in + name + ".xls"));
    }

    public void outSheet(String name) {
        sheetname = name;
        sheet = wb.getSheet(name);
    }

    public void outSheet(int i) {
        num = i;
        sheet = wb.getSheet(i);
    }

    /**
     * ********必须先用上面的方法，才能用读取方法***********
     */
    /*
     *获取一行内容转化为String数组
     */
    public String[] getRow(int i) {
        jxl.Cell[] cell = sheet.getRow(i);
        int length = cell.length;
        String[] content = new String[length];
        for (int j = 0; j < length; j++) {
            content[i] = cell[i].getContents();
        }
        return content;
    }

    /**
     * 获取一列内容转化为String数组
     */
    public String[] getColumn(int i) {
        jxl.Cell[] cell = sheet.getColumn(i);
        int length = cell.length;
        String[] content = new String[length];
        for (int j = 0; j < length; j++) {
            content[i] = cell[i].getContents();
        }
        return content;
    }

    /**
     * ***********上面的就是读取方法*************
     */
    /**
     * 本方法是将原表复制过来后再修改的方法，名称中会加（改)字 用法：（列号，行号，内容）
     */
    public void Reviser(int[] lie, int[] hang, String[][] content) throws IOException, BiffException, WriteException {
        wwb = Workbook.createWorkbook(new File(out + workbookname + "(改).xls "), wb);
        WritableSheet wsheet = wwb.getSheet(num);
        int hangshu = lie.length;
        int lieshu = hang.length;
        for (int i = 0; i < hangshu; i++) {
            for (int j = 0; j < lieshu; j++) {
                wsheet.addCell(new jxl.write.Label(lie[j], hang[i], content[j][i]));
            }
        }

        wwb.write();
    }

    /**
     * 关闭修改，写入方法
     */
    public void End() throws IOException, WriteException {
        wwb.close();
    }

}

package com.devsejong.util;

import com.devsejong.util.ExcelIOUtil;
import org.junit.Test;

import java.io.*;


public class ExcelIOUtilTest {

    @Test
    public void testReadXLSFile() throws Exception {
        InputStream inputStream = new FileInputStream(getTestFolderPath() + "sample.xls");
        ExcelIOUtil.readXLSInputStream(inputStream);
    }

    @Test
    public void testReadXLSXFile() throws Exception {
        InputStream inputStream = new FileInputStream(getTestFolderPath() + "sample.xlsx");
        ExcelIOUtil.readXLSXFile(inputStream);
    }

    @Test
    public void testWriteXLSFile() throws Exception {
        File file = new File(getTestFolderPath() + "sampleUpload.xls");
        if(!file.exists()) {
            file.createNewFile();
        }

        System.out.println(file.getAbsolutePath());
        OutputStream outputStream = new FileOutputStream(file);
        ExcelIOUtil.writeXLSFile(outputStream);
    }

    @Test
    public void testWriteXLSXFile() throws Exception {
        File file = new File(getTestFolderPath() + "sampleUpload.xlsx");
        if(!file.exists()) {
            file.createNewFile();
        }

        OutputStream outputStream = new FileOutputStream(file);
        ExcelIOUtil.writeXLSFile(outputStream);
    }


    private String getTestFolderPath(){
        return this.getClass().getResource("/excelIOUtil").getPath() + File.separator;
    }

}

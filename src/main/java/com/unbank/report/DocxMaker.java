package com.unbank.report;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocxMaker {

	public static void main(String[] args) {
		// 获取到信息后写到word里
		File outputFile = new File("E:/test.docx");
		if(outputFile.exists()){
			outputFile.mkdirs();
		}

		XWPFDocument document = new XWPFDocument();

		XWPFTable tableOne = document.createTable();

		XWPFTableRow tableOneRowOne = tableOne.getRow(0);
		tableOneRowOne.getCell(0).setText("11");
		XWPFTableCell cell12 = tableOneRowOne.createCell();
		cell12.setText("12");
		// tableOneRowOne.addNewTableCell().setText("第1行第2列");
		// tableOneRowOne.addNewTableCell().setText("第1行第3列");
		// tableOneRowOne.addNewTableCell().setText("第1行第4列");

		XWPFTableRow tableOneRowTwo = tableOne.createRow();
		tableOneRowTwo.getCell(0).setText("21");
		tableOneRowTwo.getCell(1).setText("22");
		// tableOneRowTwo.getCell(2).setText("第2行第3列");

		XWPFTableRow tableOneRow3 = tableOne.createRow();
		tableOneRow3.addNewTableCell().setText("31");
		tableOneRow3.addNewTableCell().setText("32");

		FileOutputStream fOut = null;

		try {
			fOut = new FileOutputStream(outputFile);

			document.write(fOut);
			fOut.flush();
			// 操作结束，关闭文件
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

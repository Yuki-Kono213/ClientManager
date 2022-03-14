package application;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class ExcelWriter {
	public void excelWrite(InputManager im) {
		Workbook workbook;
	    FileInputStream in = null;
		try {
			Row row0;
			in = new FileInputStream("雛形.xlsx");
			workbook = WorkbookFactory.create(in);
			Sheet sheet = workbook.getSheet("sheet1");
			for(int i =0; i < im.priceList.size(); i++) 
			{

				row0 = sheet.getRow(19 + i);

				Cell nameCell = row0.getCell(1);
		 
				Cell amountCell = row0.getCell(4);
		 
				Cell priceCell = row0.getCell(5);
				
				
				nameCell.setCellValue(im.nameList.get(i));
				amountCell.setCellValue(im.amountList.get(i));
				priceCell.setCellValue(im.priceList.get(i));
			}
			
			row0 = sheet.getRow(0);

			Cell codeCell = row0.getCell(6);
			
			codeCell.setCellValue(im.Code);
			
			row0 = sheet.getRow(8);

			Cell nameCell = row0.getCell(1);
			
			nameCell.setCellValue(im.name);
			
			row0 = sheet.getRow(13);

			Cell arrivalCell = row0.getCell(1);
			
			arrivalCell.setCellValue(im.arrivalDateTime);
			

			Cell periodCell = row0.getCell(2);
			
			periodCell.setCellValue(im.period);
			
			Cell departureCell = row0.getCell(3);
			
			departureCell.setCellValue(im.departureDateTime);
			

			Cell personCountCell = row0.getCell(4);
			
			personCountCell.setCellValue(im.personCount);
			
			
			Cell issueCell = row0.getCell(5);
			
			issueCell.setCellValue(im.issueDateTime);
			
			Cell memoCell = row0.getCell(6);
			
			memoCell.setCellValue(im.memo);
	 
			sheet.setForceFormulaRecalculation(true);
		    FileOutputStream output = null;
		    try{
		    output = new FileOutputStream(im.Directory + "/"+im.CodeName+".xlsx");
		      workbook.write(output);
		      System.out.println("完了。。");
		    }catch(IOException e){
		      System.out.println(e.toString());
		    }finally{
		      try {
		    	  if (output != null) {
		    		  	output.close();
		    	      }
		        if (workbook != null) {
		        	workbook.close();
		          }
		      }catch(IOException e){
		        System.out.println(e.toString());
		      }
		    }
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}
}

package com.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;

import com.ruleengine.pojo.ExcelOutputData;
import com.ruleengine.pojo.Stopwatch;
import com.ruleengineservlet.CrRuleConstValue;

public class Test {

	public static void main(String args[]) {
		Test ts = new Test();
		String fieldJson = "tj";
		String str = "\r\n" + "{\r\n" + "     \r\n" + "        \"agent_id__c\" : \"1015\",\r\n"
				+ "        \"month\" : \"2\",\r\n" + "        \"registration_id__c\" : \"77482\",\r\n"
				+ "        \"deposit_received__c\" : \"Y\",\r\n" + "        \"doc_ok__c\" : \"Y\",\r\n"
				+ "        \"agent_name__c\" : \"TBUL\",\r\n" + "        \"unit_name__c\" : \"NVTE/4/407\",\r\n"
				+ "        \"title\" : \"MRS.\",\r\n" + "        \"customername\" : \"HUDA AL ZUFAIRI\",\r\n"
				+ "        \"nationality__c\" : \"KUWAITI\"\r\n" + "     \r\n" + "}\r\n" + "";
		String tr = "{\"Raw_Data\":{},\"Transform\":{\"Sum\":\"SUM(B2,B2)\",\"Sum1\":\"SUM(B2,B2)\"}}";
		try {
			JSONObject js = new JSONObject(str);
			JSONObject jst = new JSONObject(tr);
			ts.writeToFile(js, jst);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String sg[] = new String[3];
		for (int j = 0; j < 3; j++) {

			// fieldarray.put(fieldJson);
			sg[j] = fieldJson;
			System.out.println("fieldJson:: string" + fieldJson);
		}
		System.out.println("Arrays:: string" + Arrays.toString(sg));

	}

	public String writeToFile(JSONObject excelOutputData, JSONObject mainobject) {//change for transform
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		Font headerFont = null;
		HSSFCellStyle headerCellStyle = null;
		HSSFRow headerRow = null;
		FileOutputStream fileOut = null;
		String filePath = null;
		Stopwatch task = null;
		String transformkeys = null;
		String rawkeys = null;
		String rawvalues = null;
		String transformvalues = null;
		try {

			System.out.println(" Creating WorkBook for Excel File");
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet("OutputData");
			headerFont = workbook.createFont();
			headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerFont.setFontHeightInPoints((short) 10);
			headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			headerRow = sheet.createRow(0);
			task = new Stopwatch();
			HSSFCell cell = null;
			Iterator keys = excelOutputData.keys();
			JSONArray keysjsarr = new JSONArray();

			while (keys.hasNext()) {
				String str5 = (String) keys.next();
				System.out.println("key= " + str5);
				keysjsarr.put(str5);

			}
			for (int i = 0; i < keysjsarr.length(); i++) {
				cell = headerRow.createCell(i);
				System.out.println("keysjsarr.getString(i)= " + keysjsarr.getString(i));
				cell.setCellValue(keysjsarr.getString(i));

				cell.setCellStyle(headerCellStyle);

			}

			System.out
					.println("Excel File creating at " + new Date() + " and the elapsed time is " + task.elapsedTime());
			HSSFRow row = null;
			int columnCount = 0;
			for (int rowNum = 1; rowNum < 2; rowNum++) {
				row = sheet.createRow(rowNum);
				for (int i = 0; i < keysjsarr.length(); i++) {

					System.out.println("keysjsarr.length()= " + keysjsarr.length());

					String key = keysjsarr.getString(i);
					System.out.println("key.getString(key)= " + key);
					Cell cell1 = row.createCell(columnCount++);
					if (excelOutputData.has(key)) {

						try {
							cell1.setCellValue(excelOutputData.getDouble(key));
							cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
							System.out.println(key + " Double Data");
						} catch (Exception e) {
							try {
								cell1.setCellValue(excelOutputData.getString(key));
								cell1.setCellType(Cell.CELL_TYPE_STRING);
								System.out.println(key + " String Data");
								// out.println(key + " String Data");
							} catch (Exception e1) {
							}
						}
						if (i == keysjsarr.length() - 1) {
							System.out.println("i= " + i);
							if (mainobject.has("Transform")) {
								JSONObject transformdata = mainobject.getJSONObject("Transform");

								Iterator<?> transformjsonitr = transformdata.keys();
								while (transformjsonitr.hasNext()) {
									transformkeys = (String) transformjsonitr.next();
									// create transformheader
									System.out.println("transformkeys= " + transformkeys);
									cell = headerRow.createCell(columnCount++);
									System.out.println("keysjsarr.getString(i)= " + columnCount);
									System.out.println("keysjsarr.getString(i)= " + keysjsarr.getString(i));
									cell.setCellValue(transformkeys);

									cell.setCellStyle(headerCellStyle);

									transformvalues = transformdata.getString(transformkeys);
									Cell cell2 = row.createCell(columnCount - 1);
									cell2.setCellFormula(transformvalues);
									FormulaEvaluator objFormulaEvaluator1 = new HSSFFormulaEvaluator(
											(HSSFWorkbook) workbook);
									CellValue cellValue = objFormulaEvaluator1.evaluate(cell2);
									Object formulaValue = null;
									switch (cellValue.getCellType()) {
									case Cell.CELL_TYPE_NUMERIC:
										formulaValue = cellValue.getNumberValue();
										break;
									case Cell.CELL_TYPE_STRING:
										formulaValue = cellValue.getStringValue();
										break;
									case Cell.CELL_TYPE_ERROR:
										formulaValue = "";
										break;
									}
									System.out.println("form= " + formulaValue);
									// String cellStringValue =
									// objFormulaEvaluator1.evaluate(cell1).getStringValue();

									// out.println("formula Value: " + formulaValue.toString());
									if (formulaValue.toString() != null && !formulaValue.toString().equals("")
											&& formulaValue.toString().length() > 0) {
										excelOutputData
												.put(transformkeys
														.replaceFirst(transformkeys.substring(0, 1),
																transformkeys.substring(0, 1).toLowerCase())
														.replace(" ", "_"), formulaValue);
										System.out.println("excelOutputData= " + excelOutputData);
									}
								

								}

							}
						}

				
						System.out.println("excelOutputData.getString(key)= " + excelOutputData.getString(key));

					}
				}

			}
			int noOfColumns = headerRow.getPhysicalNumberOfCells();
			System.out.println("in excel num= "+noOfColumns);
			System.out.println(
					"Excel File finally created at " + new Date() + " and the elapsed time is " + task.elapsedTime());
			// Resize all columns to fit the content size
			for (int i = 0; i < keysjsarr.length(); i++) {
				sheet.autoSizeColumn(i);
			}
			// Write the output to a file
			//// out.println("Generating Excel File");
			filePath = "commission-generated-file" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())
					+ ".xls";
			String fp = "D:\\CARROTRULEWork\\finalopExcel\\abc.xls";
			fileOut = new FileOutputStream(fp);
//			fileOut = new FileOutputStream(CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
//					+ "Output_Management_Consolidator/" + filePath);
			workbook.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			//// out.println("Error caught while generating excel file "+e.getMessage());
			return "{\"status\":\"E\",\"message\":\"" + e.getMessage() + "\"}";
		} finally {
			if (null != fileOut) {
				try {
					fileOut.flush();
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					fileOut = null;
				}

			}

			if (null != workbook) {
				workbook = null;
			}
		}
		return "{\"status\":\"S\",\"message\":\"http://35.186.166.22:8082/carrotruleexcelfiles/Output_Management_Consolidator/"
				+ filePath + "\"}";

	}

}

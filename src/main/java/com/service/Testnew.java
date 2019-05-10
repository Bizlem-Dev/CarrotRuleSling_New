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

public class Testnew {

	public static void main(String args[]) {
		Testnew ts = new Testnew();
		String fieldJson = "tj";
		String str = "\r\n" + "{\r\n" + "     \r\n" + "        \"agent_id__c\" : \"1015\",\r\n"
				+ "        \"month\" : \"2\",\r\n" + "        \"registration_id__c\" : \"77482\",\r\n"
				+ "        \"deposit_received__c\" : \"Y\",\r\n" + "        \"doc_ok__c\" : \"Y\",\r\n"
				+ "        \"agent_name__c\" : \"TBUL\",\r\n" + "        \"unit_name__c\" : \"NVTE/4/407\",\r\n"
				+ "        \"title\" : \"MRS.\",\r\n" + "        \"customername\" : \"HUDA AL ZUFAIRI\",\r\n"
				+ "        \"nationality__c\" : \"KUWAITI\"\r\n" + "     \r\n" + "}\r\n" + "";
//		String tr = "{\"Raw_Data\":{},\"Transform\":{\"Sum\":\"SUM(B2,B2)\",\"Sum1\":\"SUM(B2,B2)\"}}";
		String tr="{\"Transform\":[{\"addition\":\"SUM(F2:G2)\"},{\"Sum\":\"COUNT(F2,G2)\"}],\"Raw_Data\":{\"sr No\":\"sr no\",\"id\":\"id\",\"marks\":\"marks\",\"discount\":\"discount\"}}";
		String mn="{\"Transform\":[{\"addition\":\"SUM(F2:G2)\"},{\"Sum\":\"COUNT(F2,G2)\"}],\"Raw_Data\":{\"sr No\":\"sr no\",\"id\":\"id\",\"marks\":\"marks\",\"discount\":\"discount\"},\r\n" + 
				"\"Rule_Engine\":{\"sdf\":{\"OutputField\":[\"Addedop1\",\"Addedop2\"]},\"rlg2\":{\"OutputField\":[\"Added New34\",\"Added4\",\"Added New34\"]}}\r\n" + 
				"\r\n" + 
				"}";
		try {
		
			JSONObject jst = new JSONObject(mn);
			System.out.println(tr);
//			ts.writeToFile(js, jst);
		

		
String rt="{\"Rule_Engine\":{\"sdf\":{\"OutputField\":[\"Addedop1\",\"Addedop2\"]},\"rlg2\":{\"OutputField\":[\"Added New33\",\"Added4\",\"Added New34\"]}}}";
JSONObject mainobject=new JSONObject(rt);
System.out.println(mainobject);
String tsj="{ \"eligiblectcases\": \" \",\r\n" + 
		"    \"recovery\": \" \",\r\n" + 
		"    \"processinitiator\": \"\",\r\n" + 
		"    \"sfobject\": \"Sales_Data__c\",\r\n" + 
		"    \"primarykey\": \"agent_id__c\",\r\n" + 
		"    \"output\": \"\",\r\n" + 
		"    \"field\": \"Clubbing_flag\",\r\n" + 
		"    \"value\": \"\",\r\n" + 
		"    \"clubbing_flag\": \"\",\r\n" + 
		"    \"Addedop1\": \"2\"}";
String sd="{\"searchedMongodata\":[\r\n" + 
		"{\r\n" + 
		"     \r\n" + 
		"        \"agent_id__c\" : \"1015\",\r\n" + 
		"        \"month\" : \"\",\r\n" + 
		"        \"registration_id__c\" : \"77482\",\r\n" + 
		"        \"deposit_received__c\" : \"Y\",\r\n" + 
		"        \"doc_ok__c\" : \"Y\",\r\n" + 
		"        \"agent_name__c\" : \"TBUL\",\r\n" + 
		"        \"unit_name__c\" : \"NVTE/4/407\",\r\n" + 
		"        \"title\" : \"MRS.\",\r\n" + 
		"        \"customername\" : \"HUDA AL ZUFAIRI\",\r\n" + 
		"        \"nationality__c\" : \"KUWAITI\"\r\n" + 
		"     \r\n" + 
		"},\r\n" + 
		"{\r\n" + 
		"     \r\n" + 
		"        \"agent_id__c\" : \"1016\",\r\n" + 
		"        \"month\" : \"\",\r\n" + 
		"        \"registration_id__c\" : \"77482\",\r\n" + 
		"        \"deposit_received__c\" : \"Y\",\r\n" + 
		"        \"doc_ok__c\" : \"Y\",\r\n" + 
		"        \"agent_name__c\" : \"TBUL\",\r\n" + 
		"        \"unit_name__c\" : \"NVTE/4/407\",\r\n" + 
		"        \"title\" : \"MRS.\",\r\n" + 
		"        \"customername\" : \"HUDA AL ZUFAIRI\",\r\n" + 
		"        \"nationality__c\" : \"KUWAITI\"\r\n" + 
		"     \r\n" + 
		"}\r\n" + 
		"]}\r\n" + 
		"";
JSONObject js = new JSONObject(sd);
JSONObject jsresp=new JSONObject(tsj);

JSONObject rlgobj=mainobject.getJSONObject("Rule_Engine");
Iterator keys = rlgobj.keys();

JSONObject js2=new JSONObject();
while (keys.hasNext()) {
	String rlname = (String) keys.next();
	System.out.println("key= " + rlname);
	
	String op=rlgobj.getString(rlname);
	System.out.println("opfld= "+op);
	JSONObject jsop=new JSONObject(op);
	JSONArray opfld=jsop.getJSONArray("OutputField");
	System.out.println("opfld= "+opfld.length());
	for(int k=0;k<opfld.length();k++) {
		String flname=opfld.getString(k);
		System.out.println("flname= "+flname);
	if(jsresp.has(flname)) {
		System.out.println("js21= " +jsresp);
		String val=jsresp.getString(flname);
		
		js2.put(flname, val);
		System.out.println("js2= " + js2);
		
		
	}else {
//		System.out.println("js2= " +jsresp);
	}
	}
}
ts.writeToFile(js, jst,"","");
//ts.writeToFile(js, jst,js2);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}


//		String sg[] = new String[3];
//		for (int j = 0; j < 3; j++) {
//
//			// fieldarray.put(fieldJson);
//			sg[j] = fieldJson;
//			System.out.println("fieldJson:: string" + fieldJson);
//		}
//		System.out.println("Arrays:: string" + Arrays.toString(sg));

	}
//JSONObject excelOutputData
	public String writeToFile(JSONObject exceldata, JSONObject mainobject,String username,String project) {//change for transform
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
		JSONObject rulEngobj=null;
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
			JSONObject excelOutputData=null;
JSONArray excldataarr=exceldata.getJSONArray("searchedMongodata");
	int rnc=excldataarr.length();
	System.out.println("rnc= "+rnc);
			for(int z=0;z<excldataarr.length();z++) {
				excelOutputData=excldataarr.getJSONObject(z);
				System.out.println("exceldat= "+excelOutputData);
			Iterator keys = excelOutputData.keys();
			JSONArray keysjsarr = new JSONArray();
			
			while (keys.hasNext()) {
				String str5 = (String) keys.next();
				System.out.println("key= " + str5);
				keysjsarr.put(str5);

			}
			if(z == 0) {
			for (int i = 0; i < keysjsarr.length(); i++) {
				cell = headerRow.createCell(i);
				System.out.println("keysjsarr.getString(i)= " + keysjsarr.getString(i));
				cell.setCellValue(keysjsarr.getString(i));

				cell.setCellStyle(headerCellStyle);

			}
			}

			System.out
					.println("Excel File creating at " + new Date() + " and the elapsed time is " + task.elapsedTime());
			HSSFRow row = null;
			
//			for (int rowNum = 1; rowNum < rnc; rowNum++) {
				int columnCount = 0;
				row = sheet.createRow(z+1);
				for (int i = 0; i < keysjsarr.length(); i++) {
					System.out.println("keysjsarr.length()= iii =" + i);
					System.out.println("keysjsarr.length()= " + keysjsarr.length());

					String key = keysjsarr.getString(i);
					System.out.println("key.getString(key)= " + key);
					Cell cell1 = row.createCell(columnCount++);
					if (excelOutputData.has(key)) {

						try {
							cell1.setCellValue(excelOutputData.getDouble(key));
							cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
							System.out.println(excelOutputData.getDouble(key) + " firstcell Double Data");
						} catch (Exception e) {
							try {
								cell1.setCellValue(excelOutputData.getString(key));
								cell1.setCellType(Cell.CELL_TYPE_STRING);
								System.out.println(key + " String Data");
								// out.println(key + " String Data");
							} catch (Exception e1) {
							}
						}
						if (i == keysjsarr.length() -1 ) {
							System.out.println("i=in transform " + i);
							if (mainobject.has("Transform")) {
//								JSONObject transformdata = mainobject.getJSONObject("Transform");
								JSONArray transar=mainobject.getJSONArray("Transform");
								for(int j=0;j<transar.length();j++) {
									JSONObject transjs=transar.getJSONObject(j);
									JSONArray jsar=transjs.names();
									for (int k = 0; k < jsar.length(); ++k) {
										transformkeys= jsar.getString(k); // Here's your key
										System.out.println("key1= " + transformkeys);
										cell = headerRow.createCell(columnCount++);
										System.out.println("keysjsarr.getString(i)= " + columnCount);
										
										cell.setCellValue(transformkeys);

										cell.setCellStyle(headerCellStyle);

										transformvalues = transjs.getString(transformkeys);
										System.out.println("keysjsarr.transformvaluestransformvalues(i)= " + transformvalues);
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
										
										}
									
									
									
								}
								}
								
								
							

							}
						// for rule output write	    {"oput1": "45",  "Addedop1": "2"}
//						    "Addedop1": "2"}
							rulEngobj=	mainobject.getJSONObject("Rule_Engine");
							Iterator keysrul = rulEngobj.keys();
					
							JSONObject Singleruleop=null;;
							while (keysrul.hasNext()) {
								Singleruleop=new JSONObject();
								String rlname = (String) keysrul.next();
								System.out.println("key= " + rlname);
								
								String op=rulEngobj.getString(rlname);
								JSONObject fireresp=null;
								String json=null;
								String d=" {\"oput1\": \"45\",  \"Addedop1\": \"2\",\"Added4\":\"99\",\"Addedop2\":\"88\"}";
								try {
									System.out.println("Json Data Rule Input: " + excelOutputData);
//									json = uploadToServer("http://35.186.166.22:8082/drools/callrules/"
//											+ username + "_" + project + "_"
//											+ rlname + "/fire", excelOutputData);
									fireresp=new  JSONObject(d);
									System.out.println("Method Return : " + json);
									
								
//									if() {
//										
//									}
									
								} catch (Exception e) {
									System.out.println("exc in rulfire= " + e);
								}
								System.out.println("opfld= "+op);
								JSONObject jsop=new JSONObject(op);
								JSONArray opfld=jsop.getJSONArray("OutputField");
								System.out.println("opfld= "+opfld.length());
								for(int k=0;k<opfld.length();k++) {
									String flname=opfld.getString(k);
									System.out.println("flname= "+flname);
								if(fireresp.has(flname)) {
									System.out.println("js21= " +fireresp);
									String val=fireresp.getString(flname);
									
									Singleruleop.put(flname, val);
									System.out.println("js2= " + Singleruleop);
									
									
								}else {
//									System.out.println("js2= " +jsresp);
								}
								}
//							}
							
							
//							JSONObject hj=new JSONObject(d);
							if (Singleruleop.length()>0) {
//							
//								for(int j=0;j<transar.length();j++) {
//									JSONObject transjs=transar.getJSONObject(j);
									JSONArray jsar=Singleruleop.names();
									for (int k = 0; k < jsar.length(); ++k) {
										transformkeys= jsar.getString(k); // Here's your key
										System.out.println("key1= " + transformkeys);
										cell = headerRow.createCell(columnCount++);
										System.out.println("keysjsarr.getString(i)= " + columnCount);
										
										cell.setCellValue(transformkeys);

										cell.setCellStyle(headerCellStyle);
										
										transformvalues = Singleruleop.getString(transformkeys);
										System.out.println("keysjsarr.transformvaluestransformvalues(i)= " + transformvalues);
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
										
										}
									
									
									
								}
								
								
								
							

							}
							}
						}

				
						System.out.println("excelOutputData= " + excelOutputData);

					}
				}
	

//			}
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
			}
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
	

	public String writeRulengOPToFile(JSONObject rulFireoutput) {//change for transform
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
			
			JSONArray keysjsarr = new JSONArray();

	

			System.out
					.println("Excel File creating at " + new Date() + " and the elapsed time is " + task.elapsedTime());
			HSSFRow row = null;
			int columnCount = 0;
			for (int rowNum = 1; rowNum < 2; rowNum++) {
				row = sheet.createRow(rowNum);
				for (int i = 0; i < keysjsarr.length(); i++) {
					
						// for rule output write	    {"oput1": "45",  "Addedop1": "2"}
//						    "Addedop1": "2"}
							String d=" {\"oput1\": \"45\",  \"Addedop1\": \"2\"}";
//							JSONObject hj=new JSONObject(d);
							if (rulFireoutput.length()>0) {
//								
//								for(int j=0;j<transar.length();j++) {
//									JSONObject transjs=transar.getJSONObject(j);
									JSONArray jsar=rulFireoutput.names();
									for (int k = 0; k < jsar.length(); ++k) {
										transformkeys= jsar.getString(k); // Here's your key
										System.out.println("key1= " + transformkeys);
										cell = headerRow.createCell(columnCount++);
										System.out.println("keysjsarr.getString(i)= " + columnCount);
										
										cell.setCellValue(transformkeys);

										cell.setCellStyle(headerCellStyle);
										
										transformvalues = rulFireoutput.getString(transformkeys);
										System.out.println("keysjsarr.transformvaluestransformvalues(i)= " + transformvalues);
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
//										if (formulaValue.toString() != null && !formulaValue.toString().equals("")
//												&& formulaValue.toString().length() > 0) {
//											excelOutputData
//													.put(transformkeys
//															.replaceFirst(transformkeys.substring(0, 1),
//																	transformkeys.substring(0, 1).toLowerCase())
//															.replace(" ", "_"), formulaValue);
//										
//										}
									
									
									
								}
								
								
								
							

							}
						}

				
//						System.out.println("excelOutputData= " + excelOutputData);

					}
//				}

//			}
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

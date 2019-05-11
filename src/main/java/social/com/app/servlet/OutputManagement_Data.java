package social.com.app.servlet;

import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import java.lang.Boolean;
import java.lang.reflect.Array;
import java.util.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.*;

import jxl.*;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.jsoup.Jsoup;
import org.osgi.service.http.HttpService;

import com.sun.jersey.core.util.Base64;
import com.sun.jndi.toolkit.url.UrlUtil;

import javafx.css.SimpleStyleableIntegerProperty;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

import com.ruleengineservlet.CrRuleConstValue;
import com.service.FreeTrial12;

// TODO: Auto-generated Javadoc
/**
 * The Class OutputManagement_Data.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/OutputManagement" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class OutputManagement_Data extends SlingAllMethodsServlet {

	/** The repo. */
	@Reference
	private SlingRepository repo;
	
	/** The fr. */
	FreeTrial12 fr = new FreeTrial12();
	
	/** The session. */
	Session session = null;
	// @Reference
	// private SchedulerService product;

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("hello");

	}

/* (non-Javadoc)
 * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
 */
// OutputManagement.outputfile
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("outputfile")) {
			int rownum = 1;

			// request.getRequestDispatcher("/content/static/.brokerage").forward(request,
			// response);
			Session session = null;
//			Node carrotrule = null;
//			Node usernamenode = null;
			Node projectnamenode = null;
			Node outputmanagementnode = null;
			Node outputfilenode = null;
			String filename = null;
			String filedata = null;
			ArrayList<String> keys = new ArrayList();
			ArrayList<String> values = new ArrayList();
			ArrayList<String> outputkeys = new ArrayList();

			JSONArray dataarra = new JSONArray();
			JSONObject object = new JSONObject();
			JSONObject fullvaluesjson = new JSONObject();
			JSONObject mainoutputjson = new JSONObject();

			String valuedata = null;
			String objectdata = null;
			String primarykeydata = null;
			ArrayList<String> keysjson = new ArrayList();
			JSONObject valuesjson = new JSONObject();
			JSONObject transformjson = null;
			String mainobjectkeys = null;
			String mainobjectvalues = null;
			String transformkeys = null;
			String transformvalues = null;
			Value[] datavalues = null;
			String outputfieldvalue = null;
			Node carrotmainNode = null;
			try {
				session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node downloadnode = null;
//				Node content = session.getRootNode().getNode("content");

				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String line;
				while ((line = bufferedReaderCampaign.readLine()) != null) {
					builder.append(line + "\n");
				}
				JSONObject fulljsonobject = new JSONObject(builder.toString());

				String username = fulljsonobject.getString("user_name").replace("@", "_");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}

				String project = fulljsonobject.getString("project_name").replace(" ", "_");
				String ruleengine = fulljsonobject.getString("Rule_Engine").replace(" ", "_");
				JSONObject transformfilejson = fulljsonobject.getJSONObject("OutPut_Management_Data");
				// out.println("Transform Json:" + transformfilejson);
				if (carrotmainNode != null) {
					if (!carrotmainNode.hasNode(project)) {
						projectnamenode = carrotmainNode.addNode(project);

					} else {
						projectnamenode = carrotmainNode.getNode(project);

					}

					Node externaldatanode = null;
					if (!projectnamenode.hasNode("EXTERNAL_DATA")) {
						externaldatanode = projectnamenode.addNode("EXTERNAL_DATA");
						// out.println(" Transform if ...");
					} else {
						externaldatanode = projectnamenode.getNode("EXTERNAL_DATA");
						// out.println(" Transform eles...");
					}

					if (!projectnamenode.hasNode("Output_Management")) {
						outputmanagementnode = projectnamenode.addNode("Output_Management");
						// out.println(" Transform if ...");
					} else {
						outputmanagementnode = projectnamenode.getNode("Output_Management");
						// out.println(" Transform eles...");
					}

					if (!outputmanagementnode.hasNode("Output_File")) {
						outputfilenode = outputmanagementnode.addNode("Output_File");
					} else {
						outputfilenode = outputmanagementnode.getNode("Output_File");
						outputfilenode.remove();
						outputfilenode = outputmanagementnode.addNode("Output_File");

					}
					try {
						if (fulljsonobject.has("OutPut_Management_Data")) {

							// out.println("Yes");
							filename = transformfilejson.getString("filename");
							filedata = transformfilejson.getString("filedata");

							/*
							 * outputfilenode.setProperty("Url",
							 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
							 * project + "/Output_Management/Output_File/" + filename);
							 */

//						outputfilenode.setProperty("Url",
//								request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//										+ request.getContextPath() + "/bin/cpm/nodes/property.bin/content/CARROT_RULE/"
//										+ username + "/" + project + "/Output_Management/Output_File/" + filename
//										+ "/_jcr_content?name=jcr%3Adata");

							String url1 = request.getScheme() + "://" + request.getServerName() + ":"
									+ request.getServerPort() + request.getContextPath()
									+ "/content/services/freetrial/users/" + username + "/" + "CarrotruleMainNode/"
									+ project + "/Output_Management/Output_File/" + filename;

							outputfilenode.setProperty("Url", url1);
							byte[] bytes = Base64.decode(filedata);
							InputStream myInputStream = new ByteArrayInputStream(bytes);

							Node subfileNode = outputfilenode.addNode(filename, "nt:file");
							Node jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");
							jcrNode1.setProperty("jcr:data", myInputStream);
							jcrNode1.setProperty("jcr:mimeType", "attach");

							session.save();
							String excelurl = outputfilenode.getProperty("Url").getString();
							URL url = new URL(excelurl);
							URLConnection conn = url.openConnection();

							String contentType = conn.getContentType();
							int contentLength = conn.getContentLength();
							if (contentType.startsWith("text/") || contentLength == -1) {
								System.out.println("This is not a binary file.");
							}
							out.println(" file."+url1);

							// Node externalnode =
							// projectnamenode.getNode("EXTERNAL_DATA").getNode("Download_Excel");
							try {
								InputStream raw = conn.getInputStream();
								InputStream in = new BufferedInputStream(raw);
								byte[] data = new byte[contentLength];
								int bytesRead = 0;
								int offset = 0;
								while (offset < contentLength) {
									bytesRead = in.read(data, offset, data.length - offset);
									if (bytesRead == -1)
										break;
									offset += bytesRead;
								}
								in.close();

								if (offset != contentLength) {
									// System.out.println("Only read " + offset + " bytes; Expected " +
									// contentLength + " bytes");
								}
								out.println(" file.12 "+filename);
								FileOutputStream streamout = new FileOutputStream(
										CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
												+ "Output_Management/" + filename);
								streamout.write(data);
								streamout.close();
								raw.close();
								in.close();
								out.println(" file.13 "+filename);
								InputStream ExcelFileToRead = new FileInputStream(
										CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
												+ "Output_Management/" + filename);
								HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
								HSSFSheet sheet = wb.getSheetAt(0);
								DataFormatter objDefaultFormat = new DataFormatter();

								FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);

								HSSFRow row;

								HSSFCell cell;
								Iterator<Row> rows = sheet.rowIterator();
								JSONArray dataarray = new JSONArray();
								JSONObject mainobject = new JSONObject();
								out.println(" file.14 "+filename);
								while (rows.hasNext()) {
									JSONObject insidejson = new JSONObject();
									row = (HSSFRow) rows.next();
									if (row.getRowNum() == 0) {
										continue;
									}
									if (row.getRowNum() == 1) {
										continue;
									}
									Iterator cells = row.cellIterator();

									while (cells.hasNext()) {
										
										cell = (HSSFCell) cells.next();
										out.println("cell.cell.getColumnIndex()306  = "+cell.getColumnIndex() );
										if (cell.getColumnIndex() == 1) {

											objFormulaEvaluator.evaluate(cell);
											objectdata = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
											out.println(" file.15 "+objectdata);
											out.println("cell.cell.getColumnIndex()306  = "+cell.getColumnIndex() );
										}
										
										if (cell.getColumnIndex() == 2) {

											objFormulaEvaluator.evaluate(cell);
											valuedata = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
											out.println("cell.cell.getColumnIndex()306  = "+cell.getColumnIndex() +"= valuedata- "+valuedata);
										}
										out.println("cell.cell.getColumnIndex()  = "+cell.getColumnIndex() );
										if (cell.getColumnIndex() == 3) {
											
											objFormulaEvaluator.evaluate(cell);
											primarykeydata = objDefaultFormat.formatCellValue(cell,
													objFormulaEvaluator);
											out.println("cell objformulaevaluator  = "+objDefaultFormat );
										}
										out.println(" cell.getColumnIndex() else :: "+cell.getColumnIndex());
										// valueobject.put("Value", valuedata);
										// dataarray.put(valueobject);
									}
									
									// output excel data
									String o = null;

									// External Data excel reading
									NodeIterator externalnodeitr = externaldatanode.getNode("File").getNodes();
									out.println(" file.16 in externaldatanode337 "+externaldatanode);
									Node excelname = null;
									while (externalnodeitr.hasNext()) {
										excelname = externalnodeitr.nextNode();
										out.println("excelname.getName() : " + excelname.getName());
										InputStream externalexcelread = new FileInputStream(
												CrRuleConstValue.StringConstant.FILE_PATH.value()
														+ excelname.getName());
										// out.println("Path : "+externalexcelread);
										HSSFWorkbook externalwb = new HSSFWorkbook(externalexcelread);
										HSSFSheet externalsheet = externalwb.getSheetAt(0);
										DataFormatter externalobjDefaultFormat = new DataFormatter();

										FormulaEvaluator externalobjFormulaEvaluator = new HSSFFormulaEvaluator(
												(HSSFWorkbook) externalwb);
										
										HSSFRow externalrow;

										HSSFCell externalcell;
										HSSFCell externalcellFirst;
										Iterator<Row> externalrows = externalsheet.rowIterator();
										JSONArray externaldataarray = new JSONArray();
										JSONObject externalmainobject = new JSONObject();
										Row firstRow = null;
										while (externalrows.hasNext()) {
											externalrow = (HSSFRow) externalrows.next();
											if (externalrow.getRowNum() == 0) {
												firstRow = externalrow;
												continue;
											}
											if (externalrow.getRowNum() == 1) {
												continue;
											}
											Iterator firstRowIt = firstRow.cellIterator();
											Iterator externalcells = externalrow.cellIterator();
											int i = externalrow.getFirstCellNum();
											int j = externalrow.getLastCellNum();
											//
											// out.println("First Cell : "+i);
											// out.println("Last Cell : "+j);

											String val = "";
											String rowdata = "";
											int iCount = 0;
											while (externalcells.hasNext()) {
												// out.println("Primary in while"+primarykeydata);
												externalcell = (HSSFCell) externalcells.next();
												externalcellFirst = (HSSFCell) firstRowIt.next();
												// out.println("Column_Index : " + externalrow.getLastCellNum());
												// if (externalrow.getRowNum() == 0) {
												// // keysvalue = objDefaultFormat.formatCellValue(cell,
												// objFormulaEvaluator);
												// externalobjFormulaEvaluator.evaluate(externalcell); // This will
												// evaluate the
												// cell, And any type of
												// rowdata =
												// externalobjDefaultFormat.formatCellValue(externalcell,externalobjFormulaEvaluator);
												// // externalcell.getStringCellValue()
												// // out.println("Row Heading : "+rowdata);
												// keysjson.add(rowdata);
												// }

												if (externalcell.getColumnIndex() == 0) {
													externalobjFormulaEvaluator.evaluate(externalcell); // This will
																										// evaluate
																										// the
																										// cell, And any
																										// type of

													val = externalobjDefaultFormat.formatCellValue(externalcell,
															externalobjFormulaEvaluator);
													// externalcell.getStringCellValue()
													 out.println("Data Formatter Values : "+val);
												}else {
													
												}

												if (val.equals(primarykeydata)) {

													iCount = 1;
													if (externalcell.getColumnIndex() < externalrow.getLastCellNum()) {

														externalobjFormulaEvaluator.evaluate(externalcell);
														o = externalobjDefaultFormat.formatCellValue(externalcell,
																externalobjFormulaEvaluator);
														externalobjFormulaEvaluator.evaluate(externalcellFirst);
														rowdata = externalobjDefaultFormat.formatCellValue(
																externalcellFirst, externalobjFormulaEvaluator);
														String str = rowdata;
														str = str.replaceFirst(str.substring(0, 1),
																str.substring(0, 1).toLowerCase());

														insidejson.put(str.replace(" ", ""), o);
														 out.println("insidejson 428: "+insidejson);
													}

													else {
														break;
													}
													 out.println("mainobject 434- "+mainobject);
												}

											}
											if (iCount == 1) {
												break;
											}
										}
										ExcelFileToRead.close();
										externalexcelread.close();
									}
									mainobject.put("Raw_Data", insidejson);
									 out.println("mainobject 446: "+mainobject);
									// This is where we will get the formula
									Node transformnode = carrotmainNode.getNode(project).getNode("TRANSFORM_DATA")
											.getNode("Transform");
									
									datavalues = transformnode.getProperty("Transform").getValues();
									 out.println("mainobject 446: lengt= "+datavalues.length+"datavalues- -"+datavalues);
									for (int i = 0; i < datavalues.length; i++) {
										String transformnodevalues = datavalues[i].getString();
										JSONObject json = new JSONObject(transformnodevalues);
										mainobject.put("Transform", json);
										 out.println("transform 456: "+json);
										Iterator<?> transformjsonitr = json.keys();
										while (transformjsonitr.hasNext()) {
											transformkeys = (String) transformjsonitr.next();
											// out.println("Keys: "+transformkeys);
											transformvalues = json.getString(transformkeys);
											// out.println("Values : "+transformvalues);
										}

									}
									 out.println("Mainobject 467 : "+mainobject);

									JSONObject exceldata = mainobject.getJSONObject("Raw_Data");
									 out.println("exceldata 470: "+exceldata+"=  len= "+exceldata.length());
									if (exceldata.length() > 0) {

										// HARI START
										HSSFWorkbook workbook = new HSSFWorkbook();
										HSSFSheet excelsheet = workbook.createSheet("sample");
										workbook.setForceFormulaRecalculation(true);
										Row row3 = excelsheet.createRow(2); // Create 2rd Row

										int columnCount = 0;
										int rowCount = 1;

										Value[] transformRawValue = carrotmainNode.getNode(project)
												.getNode("TRANSFORM_DATA").getNode("Raw_Data").getProperty("Raw_Data")
												.getValues();
										out.println("transformRawValue 485: "+transformRawValue+"=  len= "+transformRawValue.length);
										for (int i = 0; i < transformRawValue.length; i++) {
											JSONObject jsonObject = new JSONObject(transformRawValue[i].getString());
											Iterator<?> jsonIterator = jsonObject.keys();

											while (jsonIterator.hasNext()) {
												String key = (String) jsonIterator.next();
												Cell cell1 = row3.createCell(columnCount++);
												 out.println("key :"+key);
												 out.println("transformvalues :"+transformvalues);

												if (exceldata.has(key)) {

													try {
														cell1.setCellValue(exceldata.getDouble(key));
														cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
														// out.println(key + " Double Data");
													} catch (Exception e) {
														try {
															cell1.setCellValue(exceldata.getString(key));
															cell1.setCellType(Cell.CELL_TYPE_STRING);
															// out.println(key + " String Data");
														} catch (Exception e1) {
														}
													}

													// out.println(exceldata.getString(key));
												} else
													cell1.setCellValue("");
											}
										}

										JSONObject transformdata = mainobject.getJSONObject("Transform");
										 out.println("transformdata :518 "+ transformdata);

										Iterator<?> transformjsonitr = transformdata.keys();
										while (transformjsonitr.hasNext()) {
											transformkeys = (String) transformjsonitr.next();
											transformvalues = transformdata.getString(transformkeys);

											// out.println("formula : "+ transformvalues);

											Cell cell1 = row3.createCell(columnCount++);
											cell1.setCellFormula(transformvalues);

											// String cellValue = cell1.getStringCellValue();
											// FormulaEvaluator evaluator =
											// wb.getCreationHelper().createFormulaEvaluator();
											FormulaEvaluator objFormulaEvaluator1 = new HSSFFormulaEvaluator(
													(HSSFWorkbook) workbook);
											CellValue cellValue = objFormulaEvaluator1.evaluate(cell1);
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

											// String cellStringValue =
											// objFormulaEvaluator1.evaluate(cell1).getStringValue();

											 out.println("formula Value:556 " + formulaValue);

											exceldata.put(transformkeys.replaceFirst(transformkeys.substring(0, 1),
													transformkeys.substring(0, 1).toLowerCase()), formulaValue);
											 out.println("exceldata Value:556 " + exceldata);
										}

											//added code for level 1Transform_Generic_Node/Level1
		
//											Node =null;
											
										
										/*
										 * Iterator<?> mainobjectjsonitr = exceldata.keys();
										 * 
										 * 
										 * 
										 * 
										 * 
										 * 
										 *//*
											 * int sfColumnCount = 0; NodeIterator nodeIterator = sfdcNode.getNodes();
											 * 
											 * while(nodeIterator.hasNext()){ Node sfdcChileNode =
											 * nodeIterator.nextNode(); String nodeName = sfdcChileNode.getName();
											 * Value[] sfValues = sfdcChileNode.getProperty(nodeName).getValues();
											 * 
											 * sfColumnCount += sfValues.length; }
											 * 
											 * Node transformRawData =
											 * session.getRootNode().getNode("content").getNode("CARROT_RULE")
											 * .getNode(username).getNode(project).getNode("SFDC_SELECTDATA");
											 *//*
												 * 
												 * while (mainobjectjsonitr.hasNext()) { mainobjectkeys = (String)
												 * mainobjectjsonitr.next(); mainobjectvalues =
												 * exceldata.getString(mainobjectkeys);
												 * 
												 * 
												 * Iterator<?> transformjsonitr = transformdata.keys(); while
												 * (transformjsonitr.hasNext()) { transformkeys = (String)
												 * transformjsonitr.next(); transformvalues =
												 * transformdata.getString(transformkeys);
												 * 
												 *//*
													 * Cell cell1 = row1.createCell(++columnCount);
													 * cell1.setCellFormula(transformvalues);
													 *//*
														 * 
														 * //if (mainobjectkeys.contains(transformvalues)) {
														 * mainobjectvalues = exceldata.getString(transformvalues);
														 * valuesjson.put(transformkeys, mainobjectvalues);
														 * 
														 * keys.add(transformkeys); values.add(mainobjectvalues);//
														 * out.println("Inside : "+valuesjson); //} //
														 * out.println("Outside : "+valuesjson);
														 * 
														 * }
														 * 
														 * }
														 */

										FileOutputStream outstream = new FileOutputStream(
												CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
														+ "Output_Management/sample.xls"); //
										workbook.write(outstream);
										outstream.close();

										/*
										 * for (int i = 0; i < keys.size(); i++) {
										 * exceldata.put(keys.get(i).replaceFirst(keys.get(i).substring(0, 1),
										 * keys.get(i).substring(0, 1).toLowerCase()), values.get(i)); }
										 */

										/* START */
										// out.println(""+exceldata);

										// FileInputStream createXlsx = new FileInputStream(
										// "/home/vil/sling
										// tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/Output_Management/sample.xls");
										// //

										// File xlsFile = new File("/home/vil/sling
										// tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/Output_Management/sample.xls");
										// xlsFile

										// XSSFWorkbook workbook = new XSSFWorkbook(/*createXlsx*/);
										// XSSFSheet excelsheet = workbook.createSheet("sample");

										// mainobjectjsonitr --> RAW Data

										/*
										 * while (mainobjectjsonitr.hasNext()) { mainobjectkeys = (String)
										 * mainobjectjsonitr.next(); mainobjectvalues =
										 * exceldata.getString(mainobjectkeys);
										 * 
										 * Row row1 = excelsheet.createRow(++rowCount); int columnCount = 0;
										 * 
										 * Iterator<?> transformjsonitr = transformdata.keys(); while
										 * (transformjsonitr.hasNext()) { transformkeys = (String)
										 * transformjsonitr.next(); transformvalues =
										 * transformdata.getString(transformkeys);
										 * 
										 * if (mainobjectkeys.contains(transformvalues)) { Cell cell1 =
										 * row1.createCell(++columnCount); mainobjectvalues =
										 * exceldata.getString(transformvalues); cell1.setCellFormula(transformvalues);
										 * } } }
										 */

										// createXlsx.close();

										/* END */

										// HARI END
										// out.println("Url :
										// "+"http://35.236.213.87:8080/drools/callrules/"+username+"_"+project+"_"+ruleengine+"/fire");
										// http://35.236.213.87:8080/drools/callrules/carrotrule@xyz.com_R_Rule/fire
//										 "http://35.186.166.22:8082/drools/generaterules";
//										http://35.186.166.22:8082/drools/callrules/carrotrule444@gmail.com_carrotproj45_Rule2/fire
										String json = uploadToServer("http://35.186.166.22:8082/drools/callrules/"
												+ username.replace("_", "@") + "_" + project + "_" + ruleengine
												+ "/fire", exceldata);
										// out.println("Json Data : "+exceldata);
										 out.println("Method Return : " + json);
										JSONObject fullrulejson = new JSONObject(json);
										JSONObject outputdatajson = null;
										JSONObject outputjson = null;

										String fieldvalueoutput = null;
										Node rulename = null;
										NodeIterator ruleengineitr = carrotmainNode.getNode(project)
												.getNode("Rule_Engine").getNode(ruleengine).getNodes();
										Value[] ruledata = null;
										outputjson = new JSONObject();

										while (ruleengineitr.hasNext()) {
											rulename = ruleengineitr.nextNode();
											ruledata = rulename.getProperty("OutputField").getValues();
											for (int i = 0; i < ruledata.length; i++) {
												String outputvalues = ruledata[i].getString();

												outputdatajson = new JSONObject(outputvalues);
												fieldvalueoutput = outputdatajson.getString("field");
												outputjson.put("Field", fieldvalueoutput);
												outputkeys.add(fieldvalueoutput);
											}

										}
										// String outputfieldslingvalue=null;
										String finaloutput = "";
										rownum++;
										for (int i = 0; i < outputkeys.size(); i++) {

											if (fullrulejson.has(outputkeys.get(i))) {

												outputfieldvalue = fullrulejson.getString(outputkeys.get(i));
												finaloutput = finaloutput + outputfieldvalue + ",";
												row = sheet.getRow(rownum);//
												if (row == null) {

													row = sheet.createRow(rownum);
													// out.println("row : "+row);
												}
												Cell c = row.getCell(4);

												if (c == null) {
													c = row.createCell(4);
												}
												c.setCellValue(finaloutput.substring(0, finaloutput.length() - 1));
												// out.println("finaloutput :" + finaloutput);

												if (!finaloutput.contains("null,")) {
													FileOutputStream fileOut = new FileOutputStream(
															CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
																	+ "Output_Management/" + filename); //
													wb.write(fileOut);
													fileOut.close();
												}

											}

										}
										outputkeys.clear();

										InputStream is = new FileInputStream(
												CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
														+ "Output_Management/" + filename); // );

										Node jcrNode = null;

										if (!outputmanagementnode.hasNode("Output_Download_Excel")) {

											downloadnode = outputmanagementnode.addNode("Output_Download_Excel");
										} else {
											downloadnode = outputmanagementnode.getNode("Output_Download_Excel");
											downloadnode.remove();
											downloadnode = outputmanagementnode.addNode("Output_Download_Excel");
										}
										Node outputfiledownloadnode = null;
										if (!downloadnode.hasNode("Output_Download_Excel")) {
											outputfiledownloadnode = downloadnode.addNode("Download.xls", "nt:file");
										}

										/*
										 * downloadnode.setProperty("Path",
										 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
										 * project + "/Output_Management/Output_Download_Excel/" + "Download.xls");
										 */
//								downloadnode.setProperty("Path",
//										request.getScheme() + "://" + request.getServerName() + ":"
//												+ request.getServerPort() + request.getContextPath()
//												+ "/bin/cpm/nodes/property.bin/content/CARROT_RULE/" + username + "/"
//												+ project + "/Output_Management/Output_Download_Excel/" + "Download.xls"
//												+ "/_jcr_content?name=jcr%3Adata");
										String url2 = request.getScheme() + "://" + request.getServerName() + ":"
												+ request.getServerPort() + request.getContextPath()
												+ "/content/services/freetrial/users/" + username + "/"
												+ "CarrotruleMainNode/" + project
												+ "/Output_Management/Output_Download_Excel/" + "Download.xls";
										downloadnode.setProperty("Path", url2);
										downloadnode.setProperty("Last_Column", row.getPhysicalNumberOfCells());

										jcrNode = outputfiledownloadnode.addNode("jcr:content", "nt:resource");

										jcrNode.setProperty("jcr:data", is);
										// jcrNode.setProperty("jcr:data", stream);
										// jcrNode.setProp("");
										jcrNode.setProperty("jcr:mimeType", "attach");
									}

									// System.out.println();

								}
								out.println(downloadnode.getProperty("Path").getString());
							} catch (Exception e) {
								// TODO: handle exception
							}

						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					out.println(downloadnode.getProperty("Path").getString());
				} else {
					out.println("No user Exist");
					// TODO: handle exception
				}
				session.save();
			} catch (Exception ex) {
				out.println(ex.getStackTrace().toString());
				ex.printStackTrace(out);
				out.println("Exception ex : " + ex.getMessage());

			}
		}

	}

	/**
	 * Upload to server.
	 *
	 * @param urlstr the urlstr
	 * @param json the json
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JSONException the JSON exception
	 */
	// Rule Engine Call
	private static String uploadToServer(String urlstr, JSONObject json) throws IOException, JSONException {
		JSONObject jsonObject = null;
		StringBuffer response = null;

		try {

			int responseCode = 0;
			String urlParameters = "";

			URL url = new URL(urlstr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json");

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(json.toString());
			wr.flush();
			wr.close();

			responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();

	}

}
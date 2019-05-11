package social.com.app.servlet;

import java.util.*;
import java.net.*;

import ruleengine.db.ConnectionFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.servlet.ServletException;
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
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import com.sun.jersey.core.util.Base64;
import java.io.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.ruleengineservlet.CrRuleConstValue;
import com.service.FreeTrial12;

// TODO: Auto-generated Javadoc
/**
 * The Class Add_External_Parameter.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/Add_External_Parameter" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class Add_External_Parameter extends SlingAllMethodsServlet {

/** The repo. */
//http://35.236.154.164:8082/portal/servlet/service/Add_External_Parameter.External
	@Reference
	private SlingRepository repo;
	
	/** The fr. */
	FreeTrial12 fr = new FreeTrial12();
	
	/** The session. */
	Session session = null;

	// @Reference
	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	// private SchedulerService product;
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("in demo");
		try {

			if (request.getRequestPathInfo().getExtension().equals("Demopage")) {

				request.getRequestDispatcher("/content/static/.brokerage").forward(request, response);

			}

		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
//		Node brokerage = null;
		Node project_name = null;
		Node primarykey = null;
		Node sf_object = null;
		Session session = null;
//		Node ruls_node = null;
		Node PrimaryNode = null;
		Node werbservicesnode = null;
		Node urlnode = null;
		Node outputnode = null;
		Node extNode = null;
		Node carrotmainNode = null; // /content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode
		JSONObject test = new JSONObject();
		try {

			System.out.println("Test");
			// out.println("Remote User : : :" + request.getRemoteUser());

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

			String username = null;
			String project = null;
			String primary = null;

			JSONObject result = new JSONObject();
			if (request.getRequestPathInfo().getExtension().equals("External")) {

				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				// out.println(builder.toString());
				JSONObject jsonObject = new JSONObject(builder.toString());
				// out.println(jsonObject);
				username = jsonObject.getString("user_name").replace("@", "_");

				try {
					String freetrialstatus = fr.checkfreetrial(username);
//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				if (carrotmainNode != null) {
					project = jsonObject.getString("project_name").replace(" ", "_");
					// String rule=jsonObject.getString("rule_Engne");
//					primary = jsonObject.getString("primary_key").replace(":", "_");
					result.put("Status", "Saved Successfully");
					result.put("user_name", username);
					result.put("project_name", project);
//					result.put("primary_key", primary);
//				out.println("respky :: "+result);
					JSONObject object2 = null;
					JSONObject webservicesjson = new JSONObject();
				
//					if (!brokerage.hasNode(username.replace("@", "_"))) {
//						ruls_node = brokerage.addNode(username.replace("@", "_"));
//					} else {
//						ruls_node = brokerage.getNode(username.replace("@", "_"));
//					}

						if (!carrotmainNode.hasNode(project)) {
							project_name = carrotmainNode.addNode(project);
						} else {
							project_name = carrotmainNode.getNode(project);
						}
						try {
						if (!project_name.hasNode("EXTERNAL_DATA")) {
							sf_object = project_name.addNode("EXTERNAL_DATA");
//							
							// out.println(" Add_External_Parameter if ...");
						} else {
							sf_object = project_name.getNode("EXTERNAL_DATA");
							
							// out.println(" Add_External_Parameter eles...");
						}
						}catch (Exception e) {
							// TODO: handle exception
						}
						if (jsonObject.has("WebServices")) {
							webservicesjson = jsonObject.getJSONObject("WebServices");
						if (!sf_object.hasNode("WebServices")) {
							werbservicesnode = sf_object.addNode("WebServices");
							// out.println(" primarykey if ...");
						} else {
							werbservicesnode = sf_object.getNode("WebServices");

						}
						object2 = webservicesjson.getJSONObject("EXTERNAL_DATA");
						// {"user_name":"carrotrule@xyz.com","project_name":"sdf","primary_key":"id","WebServices":{"EXTERNAL_DATA":{"URL":[{"url":"sdf","token":"fgdfgfd","username":"fdh","password":"fhd"}],"output_field":[{"output_name":"of1","output_type":"1","output_length":"109"}]}},"File_Data":[]}
						JSONArray urlarray = object2.getJSONArray("URL");
						int urllength = object2.getJSONArray("URL").length();
						String sg[] = new String[urllength];
						String urldata = null;
						for (int i = 0; i < urlarray.length(); i++) {

							urldata = object2.getJSONArray("URL").getString(i);
							// fieldarray.put(fieldJson);
							sg[i] = urldata;
						}
						if (!werbservicesnode.hasNode("URL")) {
							urlnode = werbservicesnode.addNode("URL");
							urlnode.setProperty("URL", sg);
						} else {
							urlnode = werbservicesnode.getNode("URL");
							urlnode.setProperty("URL", sg);
						}

					} // http://35.236.154.164:8082/portal/content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode/Bizlem_project/EXTERNAL_DATA/File/abc.xls
					else {}
					JSONObject excelKeys = null;
					JSONArray excelKeysArr = null;
					if (jsonObject.has("File_Data")) {
						excelKeysArr = new JSONArray();
						JSONArray filename = jsonObject.getJSONArray("File_Data");
						// out.println("File_Data : "+filename);
						for (int i = 0; i < filename.length(); i++) {
							try {
								JSONObject filejsonobject = filename.getJSONObject(i);
								String data = filejsonobject.getString("filedata").replace(" ", "_");
								String name = filejsonobject.getString("filename");
								name=	name.substring(name.lastIndexOf("\\")+1, name.length()).replace(" ", "_");
								// out.println("File_Name : "+name);

								byte[] bytes = Base64.decode(data);
								//
								Node jcrNode1 = null;
								Node fileName = null;
								InputStream myInputStream = new ByteArrayInputStream(bytes);

								/*
								 * String url = "http://35.186.166.22:8082/portal/content/CARROT_RULE/" +
								 * username + "/" + project + "/EXTERNAL_DATA/File/" + name;
								 */
//						String url = request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+request.getContextPath()
//						+ "/bin/cpm/nodes/property.bin" + carrotmainNode + "/" + project
//						+ "/EXTERNAL_DATA/File/" + name+"/_jcr_content?name=jcr%3Adata";
//						filectnode.setProperty("filepath", request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+request.getContextPath()+dtaNode.getPath()+"/Excel/"+lsct+"/"+ subfileNode.getName() + "/" + filename);
								String url = request.getScheme() + "://" + request.getServerName() + ":"
										+ request.getServerPort() + request.getContextPath()
										+ "/content/services/freetrial/users/" + username + "/" + "CarrotruleMainNode/"
										+ project + "/" + "EXTERNAL_DATA/File/" + name;

								Node subfileNode = null;

								if (!sf_object.hasNode("File")) {
									fileName = sf_object.addNode("File");
									fileName.setProperty("file_url", url);

								} else {
									fileName = sf_object.getNode("File");
									fileName.setProperty("file_url", url);
									fileName.remove();
									fileName = sf_object.addNode("File");
									fileName.setProperty("file_url", url);
								}
								// if (!fileName.hasNode(name)) {
								subfileNode = fileName.addNode(name, "nt:file");

								jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");

								jcrNode1.setProperty("jcr:data", myInputStream);

								jcrNode1.setProperty("jcr:mimeType", "attach");
								session.save();
								try {
									String excelurl = fileName.getProperty("file_url").getString();
									// out.println("Excel Url : " + excelurl);
									URL urlconnection = new URL(excelurl);
									URLConnection conn = urlconnection.openConnection();

									String contentType = conn.getContentType();
									int contentLength = conn.getContentLength();
									if (contentType.startsWith("text/") || contentLength == -1) {
										System.out.println("This is not a binary file.");
									}

									InputStream raw = conn.getInputStream();
									InputStream in = new BufferedInputStream(raw);
									byte[] databyte = new byte[contentLength];
									int bytesRead = 0;
									int offset = 0;
									while (offset < contentLength) {
										bytesRead = in.read(databyte, offset, databyte.length - offset);
										if (bytesRead == -1)
											break;
										offset += bytesRead;
									}
									in.close();

									if (offset != contentLength) {
										// System.out.println("Only read " + offset + " bytes; Expected " +
										// contentLength + " bytes");
									}
									FileOutputStream streamout = new FileOutputStream(
											CrRuleConstValue.StringConstant.FILE_PATH.value() + name);
									streamout.write(databyte);
									streamout.close();
									raw.close();
									in.close();

									InputStream ExcelFileToRead = new FileInputStream(
											CrRuleConstValue.StringConstant.FILE_PATH.value() + name);
									HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
									HSSFSheet sheet = wb.getSheetAt(0);
									DataFormatter objDefaultFormat = new DataFormatter();

									FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);

									HSSFRow row;
									HSSFCell cell;
									Iterator<Row> rows = sheet.rowIterator();
									JSONArray dataarray = new JSONArray();
									JSONObject mainobject = new JSONObject();

									while (rows.hasNext()) {
										row = (HSSFRow) rows.next();
										// if (row.getRowNum() == 0) {
										// continue;
										// }
										Iterator cells = row.cellIterator();
										while (cells.hasNext()) {
											cell = (HSSFCell) cells.next();

											// out.println("Column_Index : " + cell.getColumnIndex());
											excelKeys = new JSONObject();
											String keysvalue = null;
											String valuedata = null;

											if (row.getRowNum() == 0) {
												JSONObject keyobject = new JSONObject();
												objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And
																					// any type of
												keysvalue = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
												// out.println(keysvalue);
												excelKeys.put("field", keysvalue);
											
												excelKeysArr.put(excelKeys);
												
											}

											// mainobject.put("Data", dataarray);

										}

									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

						}
					}
				
					JSONObject resultjson =new  JSONObject();
				
					
					try {
						extNode = project_name.getNode(CrRuleConstValue.StringConstant.EXTERNAL_DATA.value());
						if (extNode.hasNode("File")) {
							checkFordbCollection(username.replaceAll("\\.", "_")   );
							retrieveDatafromExcel(extNode, username.replaceAll("\\.", "_")  , out);
//							 out.println("data saved successfully");
							createIndex(username.replaceAll("\\.", "_")  ,out);
							// out.println("Index successfully");
						}

//				out.println("excelKeysArr = "+excelKeysArr);
						try {
						 resultjson = getJsonAlldata(object2, response, session, username, project);
						
						}catch (Exception e) {
							// TODO: handle exception
						}
//				out.println("resultjson  ==== "+resultjson);
						
//				out.println("rs1 = "+result);
					} catch (Exception e) {
						// TODO: handle exception
//						out.println(e);
					}
					if (excelKeysArr!=null) {
						resultjson.put("EXCEL_DATA", excelKeysArr);
					
					}
					result.put("Message", resultjson);
					// out.println(result);
					try {

						JSONObject messagejson = result.getJSONObject("Message");
						
						String field = null;
						String type = null;
						HSSFWorkbook workbook = new HSSFWorkbook();
						HSSFSheet sheet = workbook.createSheet("FirstSheet");
						int num = 0;

						HSSFRow rowhead = sheet.createRow((short) 0);
						rowhead.createCell(0).setCellValue("Raw Data");

						HSSFRow row1 = sheet.createRow((short) 1);
						HSSFRow row2 = sheet.createRow((short) 2);
						int rowCount = 0;
						if(messagejson.has("SFDC_SelectData")) {

						JSONObject sfdcjson = messagejson.getJSONObject("SFDC_SelectData");
						Iterator keys = sfdcjson.keys();

						while (keys.hasNext()) {
							JSONArray insidejsonarray = sfdcjson.getJSONArray((String) keys.next());
							// sfdata
							for (int i = 0; i < insidejsonarray.length(); i++) {

								JSONObject object = insidejsonarray.getJSONObject(i);
								field = object.getString("field");
								type = object.getString("type");
								row1.createCell(num++).setCellValue(
										field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toLowerCase()));
								row2.createCell(rowCount++).setCellValue(type.toLowerCase());
								// str = str.replaceFirst(str.substring(0, 1),
								// str.substring(0, 1).toLowerCase());

								//
							}
						}
					}
						
						if(messagejson.has("EXTERNAL_DATA")) {

						JSONArray externaldataarray = messagejson.getJSONArray("EXTERNAL_DATA");
						String outputfielddata = null;
						String outputtypedata = null;
						if (externaldataarray.length() > 1) {
							for (int i = 0; i < externaldataarray.length(); i++) {
								JSONObject outputfieldjson = externaldataarray.getJSONObject(i);

								outputfielddata = outputfieldjson.getString("output_name");
								outputtypedata = outputfieldjson.getString("output_type");

								row1.createCell(num++)
										.setCellValue(outputfielddata.replaceFirst(outputfielddata.substring(0, 1),
												outputfielddata.substring(0, 1).toLowerCase()));
								row2.createCell(rowCount++).setCellValue(outputtypedata.toLowerCase());

							}
						}
					}
						
						if(messagejson.has("EXCEL_DATA")) {
						
						JSONArray exceldatarray = messagejson.getJSONArray("EXCEL_DATA");
						String excelfield = null;
						for (int i = 0; i < exceldatarray.length(); i++) {
							JSONObject excelfieldjson = exceldatarray.getJSONObject(i);

							excelfield = excelfieldjson.getString("field");
							// out.println(excelfield);

							row1.createCell(num++).setCellValue(excelfield.replaceFirst(excelfield.substring(0, 1),
									excelfield.substring(0, 1).toLowerCase()));
							row2.createCell(rowCount++).setCellValue(excelfield.toLowerCase());

						}}
						int noOfColumns = row1.getPhysicalNumberOfCells();
						
						// Header Column Merge
						sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, num - 1));
						rowhead.createCell(num).setCellValue("Transform Data");

						FileOutputStream fileOut = new FileOutputStream(
								CrRuleConstValue.StringConstant.DOWNLOAD_FILE_PATH.value());
						workbook.write(fileOut);
						fileOut.close();
						// out.println("Your excel file has been generated!");
						InputStream is = new FileInputStream(
								CrRuleConstValue.StringConstant.DOWNLOAD_FILE_PATH.value());

						Node jcrNode = null;
						Node downloadnode = null;
//http://35.236.154.164:8082/portal/content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode/Bizlem_project/EXTERNAL_DATA/Download_Excel/Download.xls
	//http://35.236.154.164:8082/portal/content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode/Bizlem_project/EXTERNAL_DATA/Download_Excel/Download.xls
						if (!sf_object.hasNode("Download_Excel")) {

							downloadnode = sf_object.addNode("Download_Excel");
						} else {
							downloadnode = sf_object.getNode("Download_Excel");
							downloadnode.remove();
							downloadnode = sf_object.addNode("Download_Excel");

						}
						Node subfileNode = null;
						if (!downloadnode.hasNode("Download_Excel")) {

							subfileNode = downloadnode.addNode("Download.xls", "nt:file");
						}
						// else {
						// subfileNode = downloadnode.getNode("Download.xls",
						// "nt:file");
						// }
						// out.println(subfileNode);

						/*
						 * downloadnode.setProperty("Path",
						 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
						 * project + "/EXTERNAL_DATA/Download_Excel/" + "Download.xls");
						 */
//					downloadnode.setProperty("Path", request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+request.getContextPath()
//					+ "/bin/cpm/nodes/property.bin" + carrotmainNode
//												+ "/" + project + "/EXTERNAL_DATA/Download_Excel/" + "Download.xls"+"/_jcr_content?name=jcr%3Adata");
						downloadnode.setProperty("Path",
								request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
										+ request.getContextPath() + "/content/services/freetrial/users/" + username
										+ "/" + "CarrotruleMainNode/" + project + "/EXTERNAL_DATA/Download_Excel/"
										+ "Download.xls");

						// http://35.236.154.164:8082/portal/content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode/Bizlem_project/EXTERNAL_DATA/File/abc.xls
						downloadnode.setProperty("Last_Column", noOfColumns);

						jcrNode = subfileNode.addNode("jcr:content", "nt:resource");

						jcrNode.setProperty("jcr:data", is);
						// jcrNode.setProperty("jcr:data", stream);

						jcrNode.setProperty("jcr:mimeType", "attach");

					} catch (Exception ex) {
						
					}
					try {
						if (carrotmainNode.getNode(project).getNode("EXTERNAL_DATA").hasNode("Download_Excel")) {
							Node excelurl = carrotmainNode.getNode(project).getNode("EXTERNAL_DATA")
									.getNode("Download_Excel");
							String excelurlpath = excelurl.getProperty("Path").getString();
//				out.println("if dwld "+result);
							result.put("Download_Excel", excelurlpath);
						} else {
//	out.println("no dwld "+result);
						}
					} catch (Exception e) {
						// TODO: handle exception
//						out.println("exc::: " + e);
					}
					out.println(result);
				} else {
					out.println("No user Exist");
				}
			}
			session.save();
		} catch (Exception e) {
			// e.printStackTrace();
			out.println("Exception =" + e);
		}

	}

	/**
	 * Retrieve data from excel files saved in sling repository and save data as JSONObject in mongoDb.
	 *
	 * @param extNode the Node where file is saved
	 * @param username the username
	 * @param out the PrintWriter object
	 * @return the JSON object
	 */
	private JSONObject retrieveDatafromExcel(Node extNode, String username, PrintWriter out) {
		NodeIterator externalnodeitr = null;
		Node excelname = null;
		InputStream externalexcelread = null;
//		out.println("username= "+username);
		String o = "";
		// String ruleUrl = null;
		// String response = null;
		// Stopwatch timer1 = null;
		JSONObject primarykeydata = null;
		// int count = 0;
		
		try {
			externalnodeitr = extNode.getNode("File").getNodes();

			while (externalnodeitr.hasNext()) {
				excelname = externalnodeitr.nextNode();
				// out.println("ExcelFile name "+excelname);

				externalexcelread = new FileInputStream(CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
						+ "Externaldata/" + excelname.getName());
			
				HSSFWorkbook externalwb = new HSSFWorkbook(externalexcelread);
				HSSFSheet externalsheet = externalwb.getSheetAt(0);
				DataFormatter externalobjDefaultFormat = new DataFormatter();

				FormulaEvaluator externalobjFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) externalwb);

				HSSFRow externalrow = null;

				HSSFCell externalcell = null;
				HSSFCell externalcellFirst = null;
				Iterator<Row> externalrows = externalsheet.rowIterator();

				Row firstRow = null;
				Iterator firstRowIt = null;
				Iterator externalcells = null;
				
				while (externalrows.hasNext()) {
					// timer1 = new Stopwatch();
					externalrow = (HSSFRow) externalrows.next();
					if (externalrow.getRowNum() == 0) {
						firstRow = externalrow;
						continue;
					}
					// System.out.println(" At Row Entry Level:: searching for the passed agentId ::
					// " + new Date()+" and the elapsed time is "+timer1.elapsedTime());

					firstRowIt = firstRow.cellIterator();
					externalcells = externalrow.cellIterator();

					String val = "";
					String rowdata = "";
					primarykeydata = new JSONObject();
					while (externalcells.hasNext()) {
						try {
						externalcell = (HSSFCell) externalcells.next();
						externalcellFirst = (HSSFCell) firstRowIt.next();
					
						if (externalcell.getColumnIndex() == 0) {
							externalobjFormulaEvaluator.evaluate(externalcell);
							val = externalobjDefaultFormat.formatCellValue(externalcell, externalobjFormulaEvaluator);
							
						}
						// System.out.println(" At Row Column Level:: Passed agentId to the Excel File "
						// + new Date()+" and the elapsed time is "+timer1.elapsedTime());

						/*
						 * if(!(val.equals(primarykeydata.getString("value")))){ break; }
						 */

						if (externalcell.getColumnIndex() < firstRow.getLastCellNum()) {
							try {
							if (externalcell.getCellType() == Cell.CELL_TYPE_FORMULA) {
								Object formulaValue = null;
								switch (externalcell.getCachedFormulaResultType()) {
								case Cell.CELL_TYPE_NUMERIC:
									formulaValue = externalcell.getNumericCellValue();
									o = formulaValue.toString();
									
									break;
								case Cell.CELL_TYPE_STRING:
									formulaValue = externalcell.getRichStringCellValue();
									o = formulaValue.toString();

									break;
								case Cell.CELL_TYPE_ERROR:
									formulaValue = "";
									o = formulaValue.toString();
									break;

								}
							
							} else {
								externalobjFormulaEvaluator.evaluate(externalcell);
								o = externalobjDefaultFormat.formatCellValue(externalcell, externalobjFormulaEvaluator);

							}
//							out.println("in mongo val oooo = "+o);
							externalobjFormulaEvaluator.evaluate(externalcellFirst);
							rowdata = externalobjDefaultFormat.formatCellValue(externalcellFirst,
									externalobjFormulaEvaluator);
						
							String str = rowdata;
//							out.println("in mongo val str = "+str);
							primarykeydata.put(str, o);
//							out.println("in mongo primarykeydataprimarykeydataprimarykeydata = "+primarykeydata);
//							primarykeydata.put(str.replaceAll("[\\s.╔,û#á]", "").toLowerCase(),
//									o.replaceAll("[╔,û#á]", ""));
						}catch (Exception e) {
							// TODO: handle exception
						}
						}

						else {
							break;
						}
						}catch (Exception e) {
							// TODO: handle exception
						}
					}
					// Make database call here
//					out.println("in mongo"+primarykeydata);
					makedbConnection(primarykeydata, username,out);
					// count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != externalexcelread) {
				try {
					externalexcelread.close();
					externalexcelread = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return primarykeydata;
	}

	/**
	 * Check fordb collection.
	 *
	 * @param username the username
	 */
	private void checkFordbCollection(String username) {

		Mongo mongoClient = null;
		DB db = null;
		try {
			mongoClient = ConnectionFactory.CONNECTION.getClient();
			db = mongoClient.getDB("carrotruleSlingdb");
			db.getCollection(username).drop();
			System.out.println("Collection dropped Successfully");

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ":" + e.getMessage());

		}

	}

	/**
	 * Creates the index of database collection.
	 *
	 * @param username the username
	 */
	
// db = mongoClient.getDB("carrotruledb");
	private void createIndex(String username, PrintWriter out) {
		Mongo mongoClient = null;
		DB db = null;
		DBCollection linked = null;
		try {
			mongoClient = ConnectionFactory.CONNECTION.getClient();
//			db = mongoClient.getDB("carrotruleSlingdb");
			db = mongoClient.getDB("carrotruleSlingdb");
			linked = db.getCollection(username);
			linked.createIndex(new BasicDBObject("agent_id__c", 1));
//			out.println("linked index");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.CONNECTION.closeConnection();
		}
	}

	/**
	 * Make database connection with mongoDb.
	 *
	 * @param json the json
	 * @param username the username
	 * @param out the out
	 */
	private void makedbConnection(JSONObject json, String username,PrintWriter out) {
		Mongo mongoClient = null;
		DB db = null;
		DBObject dbObject = null;
		DBCollection linked = null;
		try {
			mongoClient = ConnectionFactory.CONNECTION.getClient();
//			db = mongoClient.getDB("carrotruleSlingdb");
			db = mongoClient.getDB("carrotruleSlingdb");
			if (db.collectionExists(username)) {
				db.getCollection(username).drop();
			}
			linked = db.getCollection(username);
			// linked.createIndex("agent_id__c");
//			out.println("json 770= "+json.toString());
			dbObject = (DBObject) (JSON.parse(json.toString()));
			
			linked.insert(dbObject);
//			out.println("json 770=inserted ");
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ":" + e.getMessage());

		}

	}

	/**
	 * Get SFDC selct data from sling and Create the JSONObject with all data.
	 *
	 * @param object2 the JSONObject
	 * @param response the SlingHttpServletResponse
	 * @param session the session
	 * @param username the username
	 * @param project the project name
	 * @return the JSONObject with all data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JSONObject getJsonAlldata(JSONObject object2, SlingHttpServletResponse response, Session session,
			String username, String project) throws IOException {
		PrintWriter out = response.getWriter();
		JSONObject allvaluejson = new JSONObject();
		try {

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			JSONObject subobject = null;
			JSONArray mainarray = null;

			Node pg2nodes = null;
			Node subnodes = null;
			String nodevalue = null;

			JSONArray array = object2.getJSONArray("output_field");
//			out.println("array - " + array);
			for (int i = 0; i < array.length(); i++) {

				JSONObject object = array.getJSONObject(i);
				jsonObject = new JSONObject();
				String output_name = object.getString("output_name");
				String output_type = object.getString("output_type");
				String output_length = object.getString("output_length");

				jsonObject.put("output_name", output_name);
				jsonObject.put("output_type", output_type);
				jsonObject.put("output_length", output_length);
				jsonArray.put(jsonObject);

			}
/// content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode
			Node ip = session.getRootNode().getNode("content").getNode("services").getNode("freetrial").getNode("users")
					.getNode(username).getNode("CarrotruleMainNode");
//			out.println("ip : " + ip);
			Value[] datavalues = null;
			NodeIterator iterator = ip.getNode(project).getNodes();
			while (iterator.hasNext()) {

				subnodes = iterator.nextNode();// sfobject and 2 nd node
				nodevalue = subnodes.getName();// sfobject

				if (nodevalue.equals("SFDC_SELECTDATA")) {
//					out.println("nodevalue :" + nodevalue);
					NodeIterator page2itr = ip.getNode(project).getNode(nodevalue).getNodes();
					mainarray = new JSONArray();
					subobject = new JSONObject();

					while (page2itr.hasNext()) {

						pg2nodes = page2itr.nextNode();
						// out.println(pg2nodes);
						String pg2nodevalue = pg2nodes.getName();
						// out.println("pg2nodevalue :"+pg2nodevalue);
						datavalues = pg2nodes.getProperty(pg2nodevalue).getValues();
						for (int i = 0; i < datavalues.length; i++) {
							String valuestring = datavalues[i].getString();
							if (i == 0) {
							} else {
								mainarray.put(new JSONObject(valuestring));
								subobject.put(pg2nodevalue, mainarray);
							}
						}

					}
					allvaluejson.put("SFDC_SelectData", subobject);

					// if(jsonArray.length()>1) {
					allvaluejson.put("EXTERNAL_DATA", jsonArray);
					// JSONObject object3 = new JSONObject();
					// object3.put("field", "fieldvalue");
					// }
					// array12.put(excelKeys);
//					allvaluejson.put("EXCEL_DATA", excelKeysArr);
				}

			}

		} catch (Exception e) {

//			out.println("Exception ex : " + e.getMessage());
		}
		return allvaluejson;
	}

}
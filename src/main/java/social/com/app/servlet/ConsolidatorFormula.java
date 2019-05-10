package social.com.app.servlet;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
import org.apache.poi.hwpf.sprm.SprmUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;

import com.sun.jersey.core.util.Base64;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;


import jxl.CellType;
import java.text.SimpleDateFormat;

import com.ruleengineservlet.CrRuleConstValue;
import com.service.FreeTrial12;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/ConsolidatorFormula" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

// http://35.236.154.164:8082/portal/servlet/service/ConsolidatorFormula.formula
//hello fr.getCarrotruleNode = node /content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode
public class ConsolidatorFormula extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	FreeTrial12 fr = new FreeTrial12();
	Session session = null;
	// @Reference
	// private SchedulerService product;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("hello");
		try {
			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
			String freetrialstatus = fr.checkfreetrial("carrotrule444@gmail.com");
			out.println("hello fr.checkfreetrial = " + freetrialstatus);
			out.println("hello fr.getCarrotruleNode = "
					+ fr.getCarrotruleNode(freetrialstatus, "carrotrule444@gmail.com", session, response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Session session = null;
//		Node usernamenode = null;
		Node projectnamenode = null;
		Node jcrNode1=null;
		Node subfileNode=null;
		Value[] outputdata = null;
		Value[] Fielddata = null;
		int count = 0;
		int outputcount = 0;
		int ruleenginecount = 0;
		Node consolidatorgenericnode = null;
		Node transformfilenode = null;
		
		JSONArray arr = new JSONArray();
		String username = null;
		String projectname = null;
		String ruleenginename = null;
		String filename=null;
		String filedata=null;
		ArrayList formulavalues = new ArrayList();
		JSONObject forobj = null;
		JSONArray formulaarray = new JSONArray();
		byte[] bytes=null;
		InputStream myInputStream = null;
		String excelurl = null;
		URL url = null;
		URLConnection conn = null;
		int contentLength = 0;
		InputStream raw = null;
		InputStream in = null;
		byte[] data = null;
		FileOutputStream streamout = null;
		Node carrotmainNode=null;
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> keysarray = new ArrayList<String>();
		ArrayList<String> valuesarray = new ArrayList<String>();
		String valued = null;
		long lastcolumn=0;
		String keysv=null;
		try {

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

			if (request.getRequestPathInfo().getExtension().equals("formula")) {

				StringBuilder builder = new StringBuilder();
			//	String excelPath = CrRuleConstValue.StringConstant.FILE_PATH.value() + "newfile.xls";
				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				JSONObject jsonObject = new JSONObject(builder.toString());
				username = jsonObject.getString("user_name").replace("@", "_");
				projectname = jsonObject.getString("projectname").replace("@", "_");
				JSONObject consolidatorjson=jsonObject.getJSONObject("Consolidator");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//				out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//									out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
//				Node content = session.getRootNode().getNode("content").getNode("CARROT_RULE");
				if (carrotmainNode !=null) {
					
//					usernamenode = content.getNode(username);
					if (carrotmainNode.hasNode(projectname)) {
						projectnamenode = carrotmainNode.getNode(projectname);
						if (!projectnamenode.hasNode("Consolidator_Generic_Node")) {
							consolidatorgenericnode = projectnamenode.addNode("Consolidator_Generic_Node");
						} else {

							consolidatorgenericnode = projectnamenode.getNode("Consolidator_Generic_Node");

						}
						lastcolumn=consolidatorgenericnode.getProperty("lastcolnumber").getLong();
						if (jsonObject.has("Consolidator")) {
							filename = consolidatorjson.getString("filename").replaceAll("\\(", "").replaceAll("\\)","");
							filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
							filedata = consolidatorjson.getString("filedata");
							bytes = Base64.decode(filedata);
							myInputStream = new ByteArrayInputStream(bytes);
/*							transformdownloadnode.setProperty("Url",
									CrRuleConstValue.StringConstant.PORTAL_URL.value() + "content/CARROT_RULE/" + username + "/"
											+ projectname + "/Transform_Generic_Node/Output_Consolidator_File/" + filename);
*/	
							String url1 = request.getScheme() + "://" + request.getServerName() + ":"
									+ request.getServerPort() + request.getContextPath()
									+ "/content/services/freetrial/users/" + username + "/" + "CarrotruleMainNode/"
									+ projectname + "/Consolidator_Generic_Node/" + filename;
							consolidatorgenericnode.setProperty("Url",url1);
//							consolidatorgenericnode.setProperty("Url",
//									request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//											+ request.getContextPath() + "/bin/cpm/nodes/property.bin/content/CARROT_RULE/"
//											+ username + "/" + projectname
//											+ "/Consolidator_Generic_Node/" + filename
//											+ "/_jcr_content?name=jcr%3Adata");
							if(consolidatorgenericnode.hasNode(filename)) {
								 consolidatorgenericnode.getNode(filename).remove();
								
							}
							subfileNode = consolidatorgenericnode.addNode(filename, "nt:file");
							jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");
							jcrNode1.setProperty("jcr:data", myInputStream);
							jcrNode1.setProperty("jcr:mimeType", "attach");
							session.save();
							excelurl = consolidatorgenericnode.getProperty("Url").getString();
							url = new URL(excelurl);
							conn = url.openConnection();

							// contentType = conn.getContentType();
							contentLength = conn.getContentLength();

							raw = conn.getInputStream();
							in = new BufferedInputStream(raw);
							data = new byte[contentLength];
							int bytesRead = 0;
							int offset = 0;
							while (offset < contentLength) {
								bytesRead = in.read(data, offset, data.length - offset);
								if (bytesRead == -1)
									break;
								offset += bytesRead;
							}

							streamout = new FileOutputStream(CrRuleConstValue.StringConstant.Generic_PATH.value() + username+"/"+username+"transformformula.xls");

							streamout.write(data);
							streamout.flush();
							streamout.close();

							raw.close();
							in.close();


					out.println("INSIDE");
					InputStream ExcelFileToRead = new FileInputStream(CrRuleConstValue.StringConstant.Generic_PATH.value() + username+"/"+username+"transformformula.xls");
					HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
					HSSFSheet sheetread = wb.getSheetAt(0);
					HSSFRow row;
					HSSFCell cell;
					Iterator<Row> rows = sheetread.rowIterator();
					DataFormatter objDefaultFormat = new DataFormatter();

					FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
					while (rows.hasNext()) {
						row = (HSSFRow) rows.next();
						// out.println("Row Next");
						Iterator cells = row.cellIterator();

						while (cells.hasNext()) {
							cell = (HSSFCell) cells.next();
							 out.println("Cell Next");
							 out.println("Cell cell.getColumnIndex()  = "+cell.getColumnIndex() );
								if (cell.getColumnIndex() > lastcolumn) {
									out.print("lastcolumn = "+lastcolumn);
//									out.print("cell.getColumnIndex() = "+cell.getColumnIndex());
									if (row.getRowNum() == 0) {
										objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
										// And any
										// type of
										keysv = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
										keysarray.add(keysv);
										out.println("keysv row=0= "+keysv);
										out.println("keysarray = "+keysarray);
										
									}
									if (row.getRowNum() == 1) {
										
										if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
											//
//																			formulavalues.add(cell.getCellFormula());
																			valued = cell.getCellFormula();
																			valuesarray.add(valued);
																			out.println("keysv row1= "+valued);
																			out.println("keysarray = "+valuesarray);
																			 out.println("formulavalues= "+formulavalues);
																		}else {
										objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																			// And any
																			// type of
										valued = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
										valuesarray.add(valued);
										out.println("keysv row1= "+valued);
										out.println("keysarray = "+valuesarray);
																		}
										// dataarray.put(keyobject);
									}
									if (row.getRowNum() == 2) {
//										out.print("lastcolumn 2= "+lastcolumn);
										objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																			// And any
																			// type of
																			// cell will return string value
										valued = objDefaultFormat.formatCellValue(cell);
										out.println("valued = 2row"+valued);
										 out.println("Formula : "+valued);
										valuesarray.add(valued);

									}

									// mainobject.put("Data", dataarray);

								}
//							if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
//
//								formulavalues.add(cell.getCellFormula());
//							}else {
//								out.println("Celse formula ");
//							}
						
					}
						JSONObject rawjsontrnsform=new JSONObject();
						JSONArray array=new JSONArray();
						try {
							JSONArray trnformarray = new JSONArray();

							JSONObject rawjsontrn = null;
							for (int k = 0; k < keysarray.size(); k++) {
								rawjsontrn = new JSONObject();

								rawjsontrnsform.put(keysarray.get(k), valuesarray.get(k));

								rawjsontrn.put(keysarray.get(k), valuesarray.get(k));
								trnformarray.put(rawjsontrn);
								out.println("rawjsontrn= 2"+rawjsontrn);

							}

							JSONObject colmedata = new JSONObject();

							array.put(rawjsontrnsform);
							colmedata.put("Transform", trnformarray);
//							dataarra.put(colmedata);
							out.println("trnformarray= 2"+trnformarray);
							out.println("trnformarray= 2"+trnformarray.length());
							String forarray[] = new String[trnformarray.length()];
							for (int j = 0; j < trnformarray.length(); j++) {
								out.println("trnformarray.getString(j) ="+trnformarray.getString(j));
								forarray[j] = trnformarray.getString(j);
							//	out.println(forarray);
								
							}
							out.println("forarr"+Arrays.toString(forarray));
						
							consolidatorgenericnode.setProperty("Formula", forarray);
							
							session.save();
						} catch (Exception e) {
							// TODO: handle exception
						}
				

				for (int i = 0; i < formulavalues.size(); i++) {
					out.println(formulavalues.get(i));
					forobj = new JSONObject();
					forobj.put("Formula", formulavalues.get(i));
					formulaarray.put(forobj);

				}
				//out.println("Jsonarray : : : :" + formulaarray);
				String forarray[] = new String[formulaarray.length()];
				for (int j = 0; j < formulaarray.length(); j++) {

					forarray[j] = formulaarray.getString(j);
				//	out.println(forarray);

				}
				
				
				
				/*String agentdata = Getagentdata(session, username, projectname);
				out.println("Agent Data Created : " + agentdata);
			*/
				session.save();
				out.println("formula set");
			}
		} 
				
					}
					}
			}
				}catch (Exception e) {
			out.println("Exception ex :" + e.getMessage());
		}
	}

	public JSONObject GetData(String path, Session session, String username, String projectname, Node carrotnode) throws IOException {

//		Node usernamenode = null;
		Node projectnamenode = null;
		String fielddatapropertyvalue = null;
		JSONObject objectvalues = null;
		JSONArray rawobjectarray = null;

		Node ruleenginenode = null;
		Node ruleenginenamenode = null;
		Node ruleenginerulesnode = null;
		String ruleenginevalue = null;
		String ruleenginenamevalue = null;
		String ruleenginerulename = null;
		Value[] fielddatavalues = null;
		try {

//			Node content = session.getRootNode().getNode("content").getNode("CARROT_RULE");
			if (carrotnode !=null) {
//				usernamenode = content.getNode(username);

				if (carrotnode.hasNode(projectname)) {
					projectnamenode = carrotnode.getNode(projectname);
					NodeIterator ruleengineitr = carrotnode.getNode(projectname).getNode("Rule_Engine")
							.getNodes();
					objectvalues = new JSONObject();
					rawobjectarray = new JSONArray();

					if (ruleengineitr.hasNext()) {
						ruleenginenode = ruleengineitr.nextNode();// RuleEngineName
						ruleenginevalue = ruleenginenode.getName();
						NodeIterator ruleenginerulesitr = carrotnode.getNode(projectname)
								.getNode("Rule_Engine").getNode(ruleenginevalue).getNodes();

						if (ruleenginerulesitr.hasNext()) {
							ruleenginerulesnode = ruleenginerulesitr.nextNode();// Rule name
							ruleenginerulename = ruleenginerulesnode.getName();

							// System.out.println("Transform Node Value : "+transformnodevalue);
							if (ruleenginerulesnode.hasProperty("FieldData")) {
								// System.out.println("In");

								fielddatavalues = ruleenginerulesnode.getProperty("FieldData").getValues();
								for (int i = 0; i < fielddatavalues.length; i++) {

									fielddatapropertyvalue = fielddatavalues[i].getString();
									rawobjectarray.put(new JSONObject(fielddatapropertyvalue));
								}
								objectvalues.put("RawData", rawobjectarray);

							}

						}

					}
				}
			}

		} catch (Exception e) {

		}
		return objectvalues;
	}

	public String Getagentdata(Session session, String username, String projectname, Node carrotnode) throws IOException {
//		Node usernamenode = null;
		Node projectnamenode = null;
		Value[] outputdata = null;
		FileOutputStream fileOut = null;
		String key = null;
		String values = null;
		JSONObject objectjson = null;
		String valuedata = null;
		JSONArray mainarray = new JSONArray();
		JSONObject mainjson = new JSONObject();
		String clubbingflagvalue = null;
		JSONArray newJsonArr = new JSONArray();

		try {
//			Node content = session.getRootNode().getNode("content").getNode("CARROT_RULE");
			// HSSFWorkbook workbook1 = new HSSFWorkbook();
			HSSFSheet sheet1 = null;
			DataFormatter objDefaultFormat = new DataFormatter();
			HSSFWorkbook workbook1 = null;
			if (carrotnode !=null) {
//				usernamenode = content.getNode(username);
				if (carrotnode.hasNode(projectname)) {
					projectnamenode = carrotnode.getNode(projectname);
					NodeIterator itr = carrotnode.getNode(projectname).getNode("Rule_Engine").getNodes();
					if (itr.hasNext()) {
						Node itrnode = itr.nextNode();
						String ruleenginename = itrnode.getName();// ruleengine name
						if (ruleenginename.equals("ConsolidatorNode")) {
						} else {
							// out.println("ruleenginename : "+ruleenginename);
							NodeIterator ruleengineitr = carrotnode.getNode(projectname)
									.getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT").getNodes();
							while (ruleengineitr.hasNext()) {
								Node yearnode = ruleengineitr.nextNode();
								String yearnodename = yearnode.getName();// year
								NodeIterator yearitr =carrotnode.getNode(projectname)
										.getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT")
										.getNode(yearnodename).getNodes();
								while (yearitr.hasNext()) {
									Node monthnode = yearitr.nextNode();
									String monthname = monthnode.getName();// Month
									NodeIterator agentitr = carrotnode.getNode(projectname)
											.getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT")
											.getNode(yearnodename).getNode(monthname).getNodes();

									while (agentitr.hasNext()) {
										JSONArray objectarray = null;
										objectarray = new JSONArray();
										Node agentid = agentitr.nextNode();
										String agentname = agentid.getName();// agentid
										NodeIterator agentcountitr = carrotnode.getNode(projectname)
												.getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT")
												.getNode(yearnodename).getNode(monthname).getNode(agentname).getNodes();
										System.out.println("agentname :" + agentname);
										workbook1 = new HSSFWorkbook();
										sheet1 = workbook1.createSheet("Agent File");
										int agentcountdata = 1;
										HSSFRow rowhead1 = null;
										HSSFRow rowhead = sheet1.createRow((short) 0);
										JSONObject objYJson = new JSONObject();
										while (agentcountitr.hasNext()) {
											Node countnode = agentcountitr.nextNode();// agentnodecount
											rowhead1 = sheet1.createRow((short) agentcountdata++);
											if (countnode.hasProperty("Output")) {
												int count = 0;
												int count1 = 0;
												outputdata = countnode.getProperty("Output").getValues();
												for (int i = 0; i < outputdata.length; i++) {
													values = outputdata[i].getString();
													// System.out.println("Json Data : " + new JSONObject(values));
													// JSONObject objectjson = new JSONObject(values);

													objectjson = new JSONObject(values);
													// HSSFRow rowhead = sheet1.createRow((short) 0);
													// rowhead1 = sheet1.createRow((short) agentcountdata++);

													Iterator<String> keys = objectjson.keys();
													while (keys.hasNext()) {
														key = keys.next();
														String jsonvalues = objectjson.getString(key);
														// System.out.println("jsonvalues : :: :"+jsonvalues);
														clubbingflagvalue = objectjson.getString("clubbing_flag");
														rowhead.createCell(count++).setCellValue(key);
														Cell cellvalue = rowhead1.createCell(count1++);
														if (jsonvalues.matches(".*\\d+.*") == false
																&& jsonvalues.length() > 0) {
															// System.out.println("false : " + jsonvalues);
															cellvalue.setCellType(Cell.CELL_TYPE_STRING);
															cellvalue.setCellValue(jsonvalues);
															// cellvalue.setCellStyle(style);
														}
														if (jsonvalues.matches("[-+]?[0-9]*\\.?[0-9]*") == true
																&& jsonvalues.length() > 0) {
															// System.out.println("true number : " + jsonvalues);
															cellvalue.setCellType((Cell.CELL_TYPE_NUMERIC));
															cellvalue.setCellValue((Double.parseDouble(jsonvalues)));
														}
														if (jsonvalues.matches(
																"((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
																		+ "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
																		+ "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
																		+ "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$"
																		+ "|^([0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9])$"
																		+ "((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))/02/29)$"
																		+ "|^(((19|2[0-9])[0-9]{2})/02/(0[1-9]|1[0-9]|2[0-8]))$"
																		+ "|^(((19|2[0-9])[0-9]{2})/(0[13578]|10|12)/(0[1-9]|[12][0-9]|3[01]))$"
																		+ "|^(([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9])$"
																		+ "|^(([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9])$") == true
																|| jsonvalues.contains("/")) {
															// System.out.println("true Date ");
															cellvalue.setCellValue(jsonvalues);

															// workbook1.removeSheetAt(0);
														}
													}

												}

												/*
												 * System.out.println(workbook1.getSheetAt(0) .getRow(2).getCell(120));
												 */
												// objectjson.put("clubbingsales", valuedata);
												/*
												 * String valuedata1 = workbook1.getSheetAt(0)
												 * .getRow(Integer.parseInt(countnode.getName()))
												 * .getCell(rowhead1.getLastCellNum()).toString();
												 * System.out.println("Valuedata: : : " + valuedata1);
												 */

												// System.out.println("Agent Count : "+agentcountdata);
												objectarray.put(objectjson);
											}
											// System.out.println("ValueData : : : "+valuedata);
											// System.out.println("The final clubsales value to be substituted is ::
											// "+objectarray.getJSONObject(objectarray.length()-1).getString("clubbingsales"));
											// String clubsumValue =
											// objectarray.getJSONObject(objectarray.length()-1).getString("clubbingsales");
											// for(int j = 0 ; j< objectarray.length()-1 ;j++) {
											// JSONObject nsum =
											// objectarray.getJSONObject(j).put("clubbingsales",clubsumValue);
											// System.out.println("The manipulated jsonObject is
											// =="+nsum.getString("clubbingsales"));
											// }
											// objectarray.put(objectjson.put("Clubbingsales", valuedata));
											// Sheet sheet = workbook1.getSheetAt(0);
											/*
											 * for (Row row : sheet) { sheet.removeRow(row); }
											 */

										}
										NodeIterator transformformula = projectnamenode
												.getNode("Transform_Generic_Node").getNodes();
										while (transformformula.hasNext()) {
											Node transform = transformformula.nextNode();
											if (transform.hasProperty("Formula")) {
												Value[] userformula = transform.getProperty("Formula").getValues();
												for (int i = 0; i < userformula.length; i++) {
													String formulavalues = userformula[i].getString();
													System.out.println("Formula : : : " + formulavalues);
													Cell cell1 = rowhead1.createCell(rowhead1.getLastCellNum());
													cell1.setCellFormula(
															new JSONObject(formulavalues).getString("Formula"));
													FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator(
															(HSSFWorkbook) workbook1);
													objFormulaEvaluator.evaluate(cell1); // This will evaluate
																							// the cell, And any
													valuedata = objDefaultFormat
															.formatCellValue(cell1, objFormulaEvaluator).toString();

												}
											}
										}

										// objectjson.put("clubbingsales", valuedata);
										// objectarray.put(objectjson.put("Clubbingsales", valuedata));
										for (int i = 0; i < objectarray.length(); i++) {
											newJsonArr
													.put(objectarray.getJSONObject(i).put("clubbingsales", valuedata));
										}
										fileOut = new FileOutputStream(
												CrRuleConstValue.StringConstant.Generic_PATH.value()
														+ "agentdatanew.xls");
										workbook1.write(fileOut);
										// System.out.println(mainarray);
										workbook1 = null;
										sheet1 = null;
										fileOut.close();

									}

									// clubbingsales
									//

									/*
									 * objectarray.put(mainjson);
									 * 
									 * InputStream ExcelFileToRead = new
									 * FileInputStream(CrRuleConstValue.StringConstant.Generic_PATH.value()+
									 * "agentdata.xls");
									 * 
									 * HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead); HSSFSheet sheetread =
									 * wb.getSheetAt(0); DataFormatter objDefaultFormatread = new DataFormatter();
									 * 
									 * FormulaEvaluator objFormulaEvaluator = new
									 * HSSFFormulaEvaluator((HSSFWorkbook) wb);
									 * 
									 * HSSFRow row; HSSFCell cell; Iterator<Row> rows = sheetread.rowIterator();
									 * JSONObject mainobject = new JSONObject(); JSONObject rawjsontrnsform = new
									 * JSONObject(); DataFormatter dataFormatter = new DataFormatter();
									 * 
									 */

									/*
									 * File xx = new File(CrRuleConstValue.StringConstant.Generic_PATH.value()
									 * +"agentdata.xls"); if (xx.exists()) { xx.delete(); }
									 */

								}
							}
						}

					}

				}
			}

		} catch (Exception ex) {
			System.out.println("Exception ex : " + ex.getMessage());
		}
		return "Created";
	}

	public static String uploadToServer(String urlstr, JSONArray json) throws IOException, JSONException {
		StringBuilder response = null;
		URL url = null;
		HttpURLConnection con = null;
		DataOutputStream wr = null;
		BufferedReader in = null;
		// .replaceAll("[╔,û#á]", "")
		// BufferedWriter writer = null;
		try {
			// out.println("Json passed is "+json);
			url = new URL(urlstr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			con.setDoOutput(true);
			wr = new DataOutputStream(con.getOutputStream());
			// writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
			// writer.write(json.toString());
			wr.writeBytes(json.toString());
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine = null;
			response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// System.out.println(response.toString());

		} catch (Exception e) {
			response = null;

		} finally {

			if (null != con) {
				con.disconnect();
				con = null;
			}
			if (null != wr) {
				wr.flush();
				wr.close();
				wr = null;
			}
			/*
			 * if(null != writer){ writer.flush(); writer.close(); writer = null; }
			 */
			if (null != in) {
				in.close();
				in = null;
			}
		}
		return (response == null ? "" : response.toString());

	}

}
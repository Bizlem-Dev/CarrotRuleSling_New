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
import ruleengine.db.ConnectionFactory;

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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.ruleengine.pojo.ExcelOutputData;
import com.ruleengine.pojo.Stopwatch;
import com.ruleengineservlet.CrRuleConstValue;

//import com.ruleengineservlet.RuleEngineMaster;
import com.service.FreeTrial12;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/OutputManagementExcel" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class OutputMgmtNew_DataTest extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	FreeTrial12 fr = new FreeTrial12();
	Session session = null;
	// @Reference
	// private SchedulerService product;
	OutputMgmtNew_DataTest opdata=new OutputMgmtNew_DataTest();
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("hello");

	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("outputfile")) {
			int rownum = 1;
			FileOutputStream fileOut = null;	
			HSSFWorkbook workbook = null;
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
			String primarykeydata = "";
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

			JSONObject ruleflowjsonobject = null;
			ArrayList<String> objectlist = null;
			ArrayList<String> primarykeylist = null;
			JSONObject searcheddata = null;
			int rowstoaddfinalExcel = 0;
			Value[] level1values = null;
			Value[] level2values = null;
			Value[] consovalues = null;
			try {
				session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node downloadnode = null;
//				Node content = session.getRootNode().getNode("content");

				StringBuilder builder = new StringBuilder();
				primarykeylist = new ArrayList<String>();
				BufferedReader bufferedReaderCampaign = request.getReader();
				String line;
				while ((line = bufferedReaderCampaign.readLine()) != null) {
					builder.append(line + "\n");
				}
				JSONObject fulljsonobject = new JSONObject(builder.toString());

				String username = fulljsonobject.getString("user_name").replace("@", "_");
				out.println("carrotmainNode username:" + username);
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
			if(carrotmainNode!=null) {

				String project = fulljsonobject.getString("project_name").replace(" ", "_");
				String ruleengine = fulljsonobject.getString("Rule_Engine").replace(" ", "_");
				JSONObject transformfilejson = fulljsonobject.getJSONObject("OutPut_Management_Data");
				out.println("carrotmainNode project:" + project);
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
//					out.println("outputfilenode= " + outputfilenode);
					try {

						if (fulljsonobject.has("OutPut_Management_Data")) {

//							out.println("Yes");
							filename = transformfilejson.getString("filename");
							filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length())
									.replace(" ", "_");
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
									+ project + "/Output_Management/Output_File/File/" + filename;
//							  http://35.236.154.164:8082/portal/content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode/prjdisc18/Output_Management/Output_File/25data.xls
							// http://35.236.154.164:8082/portal/content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode/prjdisc18/Output_Management/Output_File/File/25data.xls
							out.println("filename= " + filename);

							byte[] bytes = Base64.decode(filedata);
							InputStream myInputStream = new ByteArrayInputStream(bytes);
//							outputfilenode.setProperty("Url", url1);
//							Node subfileNode = outputfilenode.addNode(filename, "nt:file");
//							Node jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");
//							jcrNode1.setProperty("jcr:data", myInputStream);
//							jcrNode1.setProperty("jcr:mimeType", "attach");

							out.println("url1= " + url1);
							Node subfileNode = null;
							Node fileName = null;
							Node jcrNode1 = null;
							if (!outputfilenode.hasNode("File")) {
								fileName = outputfilenode.addNode("File");
								fileName.setProperty("file_url", url1);

							} else {
								fileName = outputfilenode.getNode("File");
//								fileName.setProperty("file_url", url1);
								fileName.remove();
								fileName = outputfilenode.addNode("File");
								fileName.setProperty("file_url", url1);

							}
//							out.println("filename= " + filename);
							// if (!fileName.hasNode(name)) {
							subfileNode = fileName.addNode(filename, "nt:file");

							jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");

							jcrNode1.setProperty("jcr:data", myInputStream);

							jcrNode1.setProperty("jcr:mimeType", "attach");

							session.save();
							String excelurl = fileName.getProperty("file_url").getString();
//							out.println(" file is = " + excelurl);
							URL url = new URL(excelurl);
							URLConnection conn = url.openConnection();

							String contentType = conn.getContentType();
							int contentLength = conn.getContentLength();
							if (contentType.startsWith("text/") || contentLength == -1) {
								System.out.println("This is not a binary file.");
							}

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
								out.println(" file.12 " + filename);
								FileOutputStream streamout = new FileOutputStream(
										CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
												+ "Output_Management/" + filename);
								streamout.write(data);
								streamout.close();
								raw.close();
								in.close();
//								out.println(" file.13 " + filename);
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
//								out.println(" file.14 " + filename);
								while (rows.hasNext()) {
									boolean searchDataStatus = false;
									row = (HSSFRow) rows.next();
									out.println("rows while =" + row.getRowNum());
									if (row.getRowNum() == 0) {
										continue;
									}
									if (row.getRowNum() == 1) {
										continue;
									}
									Iterator cells = row.cellIterator();
									ruleflowjsonobject = new JSONObject();
									ruleflowjsonobject.put("processInitiator", username.replaceAll("\\.", "_"));

//									out.println("ruleflowjsonobject 322= "+ruleflowjsonobject);
									while (cells.hasNext()) {
										// create json of agent data
										cell = (HSSFCell) cells.next();
										if (cell.getColumnIndex() == 1) {

											objFormulaEvaluator.evaluate(cell);
											objectdata = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);

//											objectlist.add(objectdata);
											ruleflowjsonobject.put("sfobject", objectdata);

										}
										if (cell.getColumnIndex() == 2) {
											objFormulaEvaluator.evaluate(cell);
											primarykeydata = objDefaultFormat.formatCellValue(cell,
													objFormulaEvaluator);
//											out.println("primarykeydata 344= "+primarykeydata);
//											primarykeylist.add(primarykeydata);
											ruleflowjsonobject.put("primarykey", primarykeydata);
//											out.println("primarykey 322= "+ruleflowjsonobject);
										}
										if (cell.getColumnIndex() == 3) {
											objFormulaEvaluator.evaluate(cell);
											valuedata = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
//											out.println("valuedata 344= "+valuedata);
											ruleflowjsonobject.put("value", valuedata.replace(" ", "%20"));
											out.println("ruleflowjsonobject 344= " + ruleflowjsonobject);
											if (carrotmainNode.getNode(project).getNode("EXTERNAL_DATA")
													.hasNode("File")) {
												out.println("searcheddata -----------------------------= ");
												try {
													searcheddata = new JSONObject();
													searcheddata = selectDataByAgentId(ruleflowjsonobject,
															username.replaceAll("\\.", "_"), out);
													if (searcheddata.length() > 0) {
														searchDataStatus = true;
														rowstoaddfinalExcel = rowstoaddfinalExcel + 1;
													}
													out.println("searcheddata 350= " + searcheddata);
												} catch (Exception e) {
													// TODO: handle exception
												}
												// retrieveDatafromExcel(extNode,ruleflowjsonobject,out);
											}
										}

									}

									// output excel data
									if (searchDataStatus == true) {
										out.println("mainobject 446 repeat: " + mainobject);
										// This is where we will get the formula
										try {
											JSONArray tranarr=new JSONArray();
											Node transformnode = carrotmainNode.getNode(project)
													.getNode("TRANSFORM_DATA").getNode("Transform");
//										out.println("transfornnnode---------------- " + transformnode);
											datavalues = transformnode.getProperty("Transform").getValues();
//											out.println("mainobject 446: lengt= " + datavalues.length);
											for (int i = 0; i < datavalues.length; i++) {
//												String transformnodevalues = datavalues[i].getString();
//												JSONObject json = new JSONObject(transformnodevalues);
//												mainobject.put("Transform", json);
												
												String transformnodevalues = datavalues[i].getString();
												JSONObject json = new JSONObject(transformnodevalues);
												tranarr.put(json);
//											out.println("json 456: " + json);
//											out.println("transform 456: " + mainobject);

											}
											mainobject.put("Transform", tranarr);
										} catch (Exception e) {
											out.println("exc=in trans= " + e.getMessage());
										}
										try {
//										{"Transform":{"Sum":"SUM(C3:D3)"},"Raw_Data":{"sr No":"sr no","id":"id","marks":"marks","discount":"discount"}}
											Node rawnode = carrotmainNode.getNode(project).getNode("TRANSFORM_DATA")
													.getNode("Raw_Data");
//										out.println("transfornnnode---------------- " + rawnode);
											datavalues = rawnode.getProperty("Raw_Data").getValues();
//										out.println("mainobject 446: lengt= " + datavalues.length );
											for (int i = 0; i < datavalues.length; i++) {
												String transformnodevalues = datavalues[i].getString();
												JSONObject json = new JSONObject(transformnodevalues);
//												out.println("exc=in json= " + json.toString());
												mainobject.put("Raw_Data", json);
//											out.println("json 456: " + json);
//											out.println("transform 456: " + mainobject);

											}
										} catch (Exception e) {
											out.println("exc=in trans= " + e.getMessage());
										}

//										out.println("Mainobject 467 final: " + mainobject);

										JSONObject exceldata = mainobject.getJSONObject("Raw_Data");
										out.println("exceldata 470: " + exceldata + "=  len= " + exceldata.length());
										if (exceldata.length() > 0) {

											JSONArray rulelev1 = new JSONArray();
											JSONArray rulelev2 = new JSONArray();

											Node currentruleengine = carrotmainNode.getNode(project)
													.getNode("Rule_Engine");
											Value[] va = null;
											Value[] va1 = null;
											try {
												if (currentruleengine.hasProperty("Current_Rule_Engine_Level1")) {

													va = currentruleengine.getProperty("Current_Rule_Engine_Level1")
															.getValues();
												}
												if (currentruleengine.hasProperty("Current_Rule_Engine_Level2")) {

													va1 = currentruleengine.getProperty("Current_Rule_Engine_Level2")
															.getValues();
												}

												for (int k = 0; k < va.length; k++) {
													String s = va[k].getString();
													String currentruleenginename = new JSONObject(s)
															.getString("RuleEngine");
													if(currentruleenginename!="") {
													rulelev1.put(currentruleenginename);
													}

												}
												for (int k = 0; k < va1.length; k++) {
													String s = va1[k].getString();
													String currentruleenginename = new JSONObject(s)
															.getString("RuleEngine");
													if(currentruleenginename!="") {
													rulelev2.put(currentruleenginename);
													}

												}
											} catch (Exception e) {
												// TODO: handle exception
											}
											out.println("rulelev1= " + rulelev1);
											out.println("rulelev2= " + rulelev2);

											JSONArray ruleenginearray = null;
										
											Value[] ruledata = null;
											JSONArray rulenar=null;
											try {
												if (carrotmainNode.hasNode(project)) {
													ruleenginearray = new JSONArray();
//													out.println("carrotmainNode= " + carrotmainNode);
													NodeIterator itr = carrotmainNode.getNode(project)
															.getNode("Rule_Engine").getNodes();
													JSONObject js = new JSONObject();
													while (itr.hasNext()) {
														rulenar=new JSONArray();
													
														JSONObject opjs = new JSONObject();
														Node itrnode = itr.nextNode();
														String name = itrnode.getName();
														out.println("name=----------- "+name);
														Node rlg= carrotmainNode.getNode(project)
																.getNode("Rule_Engine").getNode(name);
//														out.println("rlg=----------- "+rlg);
														NodeIterator itr1 = rlg.getNodes();
												
														
														while (itr1.hasNext()) {
															Node itrnode1 = itr1.nextNode();
															String rlname = itrnode1.getName();
														
														if (rlg.hasNode(rlname)) {
															Node rulnode = rlg.getNode(rlname);
//															out.println("rulnode= "+rulnode);
															ruledata = rulnode.getProperty("OutputField").getValues();
															
//															out.println("op lengt= "+ruledata.length);
															for (int i = 0; i < ruledata.length; i++) {
																String outputvalues = ruledata[i].getString();

																String fieldvalueoutput = new JSONObject(outputvalues)
																		.getString("field");
//																out.println("op fieldvalueoutput= "+fieldvalueoutput);
																rulenar.put(fieldvalueoutput);
																
//																out.println("op ruljs= "+ruljs);
															}
//															out.println("ruljs:: " + rulenar);
															

														}
													}
														opjs.put("OutputField", rulenar);
//														out.println("opjs:: " + opjs);
														// {"Rule_Engine":[{"rule1":{"OutputField":{"Field":"Output"}}},{"rul33":{"OutputField":{"Field":"newOp"}}},{"rul44":{"OutputField":{"Field":"Added
														// New
														// "}}}],"Current_Rule_Engine_Level1":["rule1","rul33"],"Current_Rule_Engine_Level2":["rule1","rul33","rul44"]}
														js.put(name, opjs);
														

								            	out.println("ruleenginearray:: ==================="+ruleenginearray);
													}
//													ruleenginearray.put(js);
													
													mainobject.put("Rule_Engine", js);
													mainobject.put("Current_Rule_Engine_Level1", rulelev1);
													mainobject.put("Current_Rule_Engine_Level2", rulelev2);
//													out.println(ruleenginejson);
//													mainobject.put(key, ruleenginejson)
												} else {
//											out.println(projectname);	
												}

											} catch (Exception e) {
												// TODO: handle exception
												out.println("exc in ruleeng" + e);
											}
//get levels formulas

											try {
												Node translevl1 = null;
												JSONArray translvl1arr = new JSONArray();

												if (projectnamenode.hasNode("Transform_Generic_Node")) {

													Node trans1 = projectnamenode.getNode("Transform_Generic_Node");

													if (trans1.hasNode("Level1")) {
														translevl1 = trans1.getNode("Level1");
														if (translevl1.hasProperty("Formula")) {

															level1values = translevl1.getProperty("Formula")
																	.getValues();
															out.println("exc=in level2values.length= "
																	+ level1values.length);
															for (int i = 0; i < level1values.length; i++) {
																String transformnodevalues = level1values[i]
																		.getString();
																JSONObject json = new JSONObject(transformnodevalues);
																translvl1arr.put(json);
															}
														}
														out.println("Level1Formula= " + translvl1arr);
														mainobject.put("Level1Formula", translvl1arr);

													}

												
												
												Node translevl2 = null;
												JSONArray translvl2arr = new JSONArray();

										
													if (trans1.hasNode("Level2")) {
														translevl2 = trans1.getNode("Level2");
														if (translevl2.hasProperty("Formula")) {

															level2values = translevl2.getProperty("Formula")
																	.getValues();
															out.println("exc=in level2values.length= "
																	+ level2values.length);
															for (int i = 0; i < level2values.length; i++) {
																String transformnodevalues = level2values[i]
																		.getString();
																JSONObject json = new JSONObject(transformnodevalues);
																translvl2arr.put(json);
															}
														}
														out.println("Level2Formula= " + translvl2arr);
														mainobject.put("Level2Formula", translvl2arr);

													}

												}
												Node consolidatorgenericnode = null;
												JSONArray consoformula = new JSONArray();

												if (projectnamenode.hasNode("Consolidator_Generic_Node")) {
													consolidatorgenericnode = projectnamenode
															.getNode("Consolidator_Generic_Node");
													if (consolidatorgenericnode.hasProperty("Formula")) {
														consovalues = consolidatorgenericnode.getProperty("Formula")
																.getValues();
														out.println(
																"exc=in level2values.length= " + consovalues.length);
														for (int i = 0; i < consovalues.length; i++) {
															String transformnodevalues = consovalues[i].getString();
															JSONObject json = new JSONObject(transformnodevalues);
															consoformula.put(json);

														}
														mainobject.put("Consolidator_Generic", consoformula);

													}

												}
// {"Transform":[{"Sum":"SUM(C3:D3)"}],"Raw_Data":{"sr No":"sr no","id":"id","marks":"marks","discount":"discount"},"Rule_Engine":[{"rule1":{"OutputField":{"Field":"Output"}}},{"rul33":{"OutputField":{"Field":"newOp"}}},{"rul44":{"OutputField":{"Field":"Added New "}}}],"Current_Rule_Engine_Level1":["rule1","rul33"],"Current_Rule_Engine_Level2":["rule1","rul33","rul44"],"Level1Formula":[{"Sum3":"SUM(F2,G2,H2)"}],"Level2Formula":[{"Formula":"SUM(C3:D3)"}],"Consolidator_Generic":[{"addition":"SUM(F2:G2)"},{"Sum":"COUNT(F2,G2)"}]}
												out.println("Mainobject all final: " + mainobject);

											} catch (Exception e) {
												out.println("exc=in trans= " + e.getMessage());
											}
											//write op ecel
											
											
											
											
											
											//end
											
											// HARI ENDRule_Engine
//											"Rule_Engine":[{"sdf":{"OutputField":["Addedop1 ","Addedop2"]},"rlg2":{"OutputField":["Added New34 ","Added4","Added New34 "]}}
											
										
											
											out.println("url--- " + "http://35.186.166.22:8082/drools/callrules/"
													+ username + "_" + project + "_" + ruleengine
													+ "/fire");
											// http://35.186.166.22:8082/drools/callrules/carrotrule444@gmail.com_carrotproj45_Rule2/fire
//										http://35.186.166.22:8082/drools/callrules/carrotrule@xyz.com_LeadAutoConvertRules_RulesLAC/fire
											try {
												out.println("in write--- ");
//												opdata.writeToFile(searcheddata, mainobject,username,project,workbook,out);
												
									
												
												//end
											} catch (Exception e) {
												// TODO: handle exception
												out.println("Exception in write excel ="+e);
											}
//											InputStream is = new FileInputStream(
//													CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
//															+ "Output_Management/" + filename); // );
//
//											Node jcrNode = null;
//
//											if (!outputmanagementnode.hasNode("Output_Download_Excel")) {
//
//												downloadnode = outputmanagementnode.addNode("Output_Download_Excel");
//											} else {
//												downloadnode = outputmanagementnode.getNode("Output_Download_Excel");
//												downloadnode.remove();
//												downloadnode = outputmanagementnode.addNode("Output_Download_Excel");
//											}
//											Node outputfiledownloadnode = null;
//											if (!downloadnode.hasNode("Output_Download_Excel")) {
//												outputfiledownloadnode = downloadnode.addNode("Download.xls",
//														"nt:file");
//											} else {
//
//											}
											/*
											 * downloadnode.setProperty("Path",
											 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/"
											 * + project + "/Output_Management/Output_Download_Excel/" +
											 * "Download.xls");
											 */
//								downloadnode.setProperty("Path",
//										request.getScheme() + "://" + request.getServerName() + ":"
//												+ request.getServerPort() + request.getContextPath()
//												+ "/bin/cpm/nodes/property.bin/content/CARROT_RULE/" + username + "/"
//												+ project + "/Output_Management/Output_Download_Excel/" + "Download.xls"
//												+ "/_jcr_content?name=jcr%3Adata");
										
//											jcrNode = outputfiledownloadnode.addNode("jcr:content", "nt:resource");
//
//											jcrNode.setProperty("jcr:data", is);
//											// jcrNode.setProperty("jcr:data", stream);
//											// jcrNode.setProp("");
//											jcrNode.setProperty("jcr:mimeType", "attach");
										}

										// System.out.println();
									}

								}
								
//								String url2 = request.getScheme() + "://" + request.getServerName() + ":"
//										+ request.getServerPort() + request.getContextPath()
//										+ "/content/services/freetrial/users/" + username + "/"
//										+ "CarrotruleMainNode/" + project
//										+ "/Output_Management/Output_Download_Excel/" + "Download.xls";
//								downloadnode.setProperty("Path", url2);
////								downloadnode.setProperty("Last_Column", row.getPhysicalNumberOfCells());

							String	filePath = "commission-generated-file" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())
										+ ".xls";
//								String fp = "D:\\CARROTRULEWork\\finalopExcel\\abc.xls";
//								fileOut = new FileOutputStream(fp);
//								fileOut = new FileOutputStream(CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value()
//										+ "Output_Management_Consolidator/" + filePath);
//								workbook.write(fileOut);
//								out.println(downloadnode.getProperty("Path").getString());
							} catch (Exception e) {
								// TODO: handle exception
							}

						}
					} catch (Exception e) {
						// TODO: handle exception
					}
//					out.println(downloadnode.getProperty("Path").getString());
				} else {
					out.println("No user Exist");
					// TODO: handle exception
				}
				session.save();
			}
			} catch (Exception ex) {
				out.println(ex.getStackTrace().toString());
				ex.printStackTrace(out);
				out.println("Exception ex : " + ex.getMessage());

			}
		}

	}

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

	public JSONObject selectDataByAgentId(JSONObject agentId, String username, PrintWriter out) {
		Mongo mongoClient = null;
		DB db = null;
		DBCollection linked = null;
		BasicDBObject searchQuery = null;
		DBCursor cursor = null;
		JSONObject primarykeydata = null;
		try {
			mongoClient = ConnectionFactory.CONNECTION.getClient();
			db = mongoClient.getDB("carrotruleSlingdb");

			linked = db.getCollection(username);

			searchQuery = new BasicDBObject();
			searchQuery.put("agent_id__c", agentId.getString("value"));

			cursor = linked.find(searchQuery);

			while (cursor.hasNext()) {

				try {

					primarykeydata = new JSONObject(JSON.serialize(cursor.next().toMap()));
//				out.println("Data fetched is "+primarykeydata.toString());
					primarykeydata.put("processInitiator", agentId.getString("processInitiator"));
					primarykeydata.put("sfobject", agentId.getString("sfobject"));
					primarykeydata.put("primarykey", agentId.getString("primarykey"));
					primarykeydata.remove("_id");
//				out.println("primarykeydata1 searched from mongo = "+primarykeydata);
					// System.out.println(primarykeydata);
				} catch (Exception e) {
//					e.printStackTrace();
				}

			}
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			ConnectionFactory.CONNECTION.closeConnection();
		}
		return primarykeydata;

	}
	

	public String writeToFile(JSONObject excelOutputData, JSONObject mainobject,String username,String project,HSSFWorkbook workbook,PrintWriter out) {//change for transform
//		HSSFWorkbook workbook = null;
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
		String transformvaluesexcel = null;
		JSONObject rulEngobj=null;
		try {

			out.println(" Creating WorkBook for Excel File");
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
					System.out.println("keysjsarr.length()= iii =" + i);
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
								 out.println(key + " String Data");
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

										transformvaluesexcel = transjs.getString(transformkeys);
										System.out.println("keysjsarr.transformvaluesexceltransformvaluesexcel(i)= " + transformvaluesexcel);
										Cell cell2 = row.createCell(columnCount - 1);
										cell2.setCellFormula(transformvaluesexcel);
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
								out.println("key rul name = " + rlname);
								
								String op=rulEngobj.getString(rlname);
								JSONObject fireresp=null;
								String json=null;
								String d=" {\"oput1\": \"45\",  \"Addedop1\": \"2\",\"Added4\":\"99\",\"Addedop2\":\"88\"}";
								try {
									System.out.println("Json Data Rule Input: " + excelOutputData);
									json = uploadToServer("http://35.186.166.22:8082/drools/callrules/"
											+ username + "_" + project + "_"
											+ rlname + "/fire", excelOutputData);
									fireresp=new  JSONObject(json);
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
										
										transformvaluesexcel = Singleruleop.getString(transformkeys);
										System.out.println("keysjsarr.transformvaluesexceltransformvaluesexcel(i)= " + transformvaluesexcel);
										Cell cell2 = row.createCell(columnCount - 1);
										cell2.setCellFormula(transformvaluesexcel);
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
		} 
		finally {
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
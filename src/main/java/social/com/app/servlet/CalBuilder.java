package social.com.app.servlet;

import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

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

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/CalBuilder" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class CalBuilder extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repos;
	FreeTrial12 fr = new FreeTrial12();
	Session session = null;
	ResourceBundle bundleRes = ResourceBundle.getBundle("config");

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("hello");
		try {
			if (request.getRequestPathInfo().getExtension().equals("rule")) {

			}

		} catch (Exception ex) {

			out.println("Exception ex : " + ex.getMessage());
		}
	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Session session = null;

		try {
			if (request.getRequestPathInfo().getExtension().equals("postaddCal")) {
				// out.println("In post method");
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));

				JSONObject calculationjsonobject = new JSONObject();

				ArrayList<String> keysarray = new ArrayList();
				ArrayList<String> valuesarray = new ArrayList();
				JSONArray calcularray = new JSONArray();
				JSONArray calculfullarray = new JSONArray();
				JSONObject calculfileobject = new JSONObject();
				ArrayList<String> keyarraylist = new ArrayList();
				ArrayList<String> valuearraylist = new ArrayList();
				boolean hashnode = false;
//				Node userNode = null;
				boolean hashnode2 = false;
				Node projectnode = null;
				Node projectnode1 = null;
				boolean hashnode3 = false;
				Node ruleenginenode = null;
				Node rulenamenode = null;
				Node rulenamenode1 = null;
				JSONArray calcuarray = new JSONArray();

				boolean hashnode4 = false;
				boolean rootrule = false;
				String values = null;
				String valuesoutput = null;
				String valuescaloutput = null;
				JSONArray error = new JSONArray();
				JSONArray errorsfdcarray = new JSONArray();
				JSONArray erroroutputarray = new JSONArray();
				JSONArray errorcalarray = new JSONArray();
				JSONObject suceess = new JSONObject();
				JSONObject innererrorsfdcobject = new JSONObject();
				JSONObject innererroroutputobject = new JSONObject();
				JSONObject innererrorcalobject = new JSONObject();
				JSONArray finaljsonarray = new JSONArray();

				String count1 = null;
				Node rulename = null;
				JSONArray fieldarray = new JSONArray();
				JSONArray outputfield = new JSONArray();
				String fieldJson = null;
				String CalculationBuilderjson = null;
				Node carrotmainNode = null;
				//
				StringBuilder builder = new StringBuilder();
				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}

				// out.println(g);
				// String ruledata=request.getParameter("json");
				JSONObject ruledataobj = new JSONObject(builder.toString());
				// JSONArray ruledataobj=new JSONArray(g);
				// out.println("ruledataobj"+ruledataobj);
				String projectname = ruledataobj.getString("project_name").replace(" ", "_");
				// out.println("projectname"+projectname);
				String username = ruledataobj.getString("user_name").replace("@", "_");

				try {
					String freetrialstatus = fr.checkfreetrial(username);
//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}

				// out.println("username"+username);
				String ruleengine_name = ruledataobj.getString("ruleengine_name").replace(" ", "");
				// out.println("ruleengine_name"+ruleengine_name);
				if (carrotmainNode != null) {
					hashnode2 = carrotmainNode.hasNode(projectname);
					if (hashnode2 == false) {
						// out.println("In false get a projectnode");
						// projectnode=userNode.addNode(projectname);
					} else {
						// out.println("In true get a projectnode");
						projectnode = carrotmainNode.getNode(projectname);
					}

					projectnode1 = projectnode.getNode("Rule_Engine");
					hashnode3 = projectnode1.hasNode(ruleengine_name);
					if (hashnode3 == false) {
						// out.println("In false get a ruleenginenode");
						// ruleenginenode=projectnode1.addNode(ruleengine_name);
						// ruleenginenode.setProperty("jcr:count", String.valueOf(0));
					} else {
						// out.println("In true get a ruleenginenode");
						ruleenginenode = projectnode1.getNode(ruleengine_name);
					}
					// count1=ruleenginenode.getProperty("jcr:count").getString();
					// out.println("count1"+count1);
					if (ruledataobj.has("data")) {
						JSONArray dataarray = ruledataobj.getJSONArray("data");
						for (int i = 0; i < dataarray.length(); i++) {

							JSONObject m = dataarray.getJSONObject(i);
							// System.out.println("rulename::"+m.getString("rulename"));
							String rule_from_json = m.getString("rulename");
							if ((rule_from_json != null)) {
								// out.println("rule_from_json"+rule_from_json);
								ArrayList sfdckey = new ArrayList();
								ArrayList outputkey = new ArrayList();
								ArrayList calkey = new ArrayList();
								JSONObject data = dataarray.getJSONObject(i);
								String Rule_name = data.getString("rulename");
								JSONArray sfdata = data.getJSONArray("SFdata");
								if (sfdata.length() > 0) {
									for (int j = 0; j < sfdata.length(); j++) {

									}

									JSONArray outputdata = data.getJSONArray("Outputdata");
									for (int p = 0; p < outputdata.length(); p++) {

									}

									JSONArray CalBuidler = data.getJSONArray("CalculationBuilder");
									for (int e = 0; e < CalBuidler.length(); e++) {

									}
									if (errorsfdcarray.length() > 0) {
										innererrorsfdcobject.put("SFdata", errorsfdcarray);
									}
									if (erroroutputarray.length() > 0) {
										innererroroutputobject.put("Outputdata", erroroutputarray);
									}
									if (errorcalarray.length() > 0) {
										innererrorcalobject.put("CalculationBuilder", errorcalarray);
									}
//					if (Rule_name.equals("")) {
//						suceess.put("Status", "Not Store");
//						suceess.put("message", "rule name blank at" + i);
//					}

									if (errorsfdcarray.length() == 0 && erroroutputarray.length() == 0
											&& errorcalarray.length() == 0) {
										rootrule = ruleenginenode.hasNode(rule_from_json);
										if (rootrule == true) {
											rulenamenode1 = ruleenginenode.getNode(rule_from_json);
											//
											int CalculationBuilder = m.getJSONArray("CalculationBuilder").length();
											String sh[] = new String[CalculationBuilder];
											for (int k = 0; k < CalculationBuilder; k++) {
												CalculationBuilderjson = m.getJSONArray("CalculationBuilder")
														.getString(k);
												// outputfield.put(OutputdatalengthJson);
												sh[k] = CalculationBuilderjson;

											}
											rulenamenode1.setProperty("CalculationBuilder", sh);
											// ruleenginenode.setProperty("jcr:count",String.valueOf(Integer.parseInt(count1)+1));
											// out.println(ruledataobj);
											session.save();
											suceess.put("Status", "Saved Successfully");
											// error.put(data);
										}
									}
									

								}

							}
							out.println(suceess);
						}
					}
					Node calculationbuildernode = null;
					Node calculationbuilderuploadnode = null;
					String filename = null;
					try {
						if (ruledataobj.has("Calculation_File_Data")) {
							// out.println("Has Calculation_File_Data");
							JSONObject Calculation_File_Data = ruledataobj.getJSONObject("Calculation_File_Data");
							if (Calculation_File_Data.has("filename") && Calculation_File_Data.has("filedata")) {

								if (!projectnode.hasNode("Calculation_Excel_File")) {
									calculationbuildernode = projectnode.addNode("Calculation_Excel_File");
								} else {
									calculationbuildernode = projectnode.getNode("Calculation_Excel_File");
								}
								if (!calculationbuildernode.hasNode("Calculation_Upload_File")) {
									calculationbuilderuploadnode = calculationbuildernode
											.addNode("Calculation_Upload_File");
								} else {
									calculationbuilderuploadnode = calculationbuildernode
											.getNode("Calculation_Upload_File");
								}
								String rulenamecalculation = "";
								if (Calculation_File_Data.has("rulename")) {
									rulenamecalculation = Calculation_File_Data.getString("rulename");
								}
								filename = Calculation_File_Data.getString("filename");
								filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
								/*
								 * calculationbuilderuploadnode.setProperty("Url",
								 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
								 * projectname + "/Calculation_Excel_File/Calculation_Upload_File/" +
								 * Calculation_File_Data.getString("filename"));
								 */
//					calculationbuilderuploadnode.setProperty("Url",request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+request.getContextPath()
//					+ "/bin/cpm/nodes/property.bin/content/CARROT_RULE/" + username + "/" + projectname
//														+ "/Calculation_Excel_File/Calculation_Upload_File/"
//														+ Calculation_File_Data.getString("filename")+"/_jcr_content?name=jcr%3Adata");

								String url1 = request.getScheme() + "://" + request.getServerName() + ":"
										+ request.getServerPort() + request.getContextPath()
										+ "/content/services/freetrial/users/" + username + "/" + "CarrotruleMainNode/"
										+ projectname + "/Calculation_Excel_File/Calculation_Upload_File/" + filename;
								calculationbuilderuploadnode.setProperty("Url", url1);

								byte[] bytes = Base64.decode(Calculation_File_Data.getString("filedata"));
								InputStream myInputStream = new ByteArrayInputStream(bytes);

								Node subfileNode = calculationbuilderuploadnode.addNode(filename, "nt:file");
								Node jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");
								jcrNode1.setProperty("jcr:data", myInputStream);
								jcrNode1.setProperty("jcr:mimeType", "attach");

								String calculurl = calculationbuilderuploadnode.getProperty("Url").getString();
								URL url = new URL(calculurl);
								URLConnection conn = url.openConnection();

								String contentType = conn.getContentType();
								int contentLength = conn.getContentLength();
								if (contentType.startsWith("text/") || contentLength == -1) {
									// out.println("This is not a binary file.");
								}

								Node calculationnode = projectnode.getNode("Calculation_Excel_File")
										.getNode("Calculation_Download_File");

								Long lastcolumn = calculationnode.getProperty("Last_Column").getLong();

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
									System.out.println(
											"Only read " + offset + " bytes; Expected " + contentLength + " bytes");
								}
								FileOutputStream streamout = new FileOutputStream(
										CrRuleConstValue.StringConstant.CAL_BUILDER_PATH.value() + filename);
								streamout.write(data);
								streamout.close();
								raw.close();
								in.close();

								InputStream ExcelFileToRead = new FileInputStream(
										CrRuleConstValue.StringConstant.CAL_BUILDER_PATH.value() + filename);
								HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
								HSSFSheet sheet = wb.getSheetAt(0);
								DataFormatter objDefaultFormat = new DataFormatter();

								FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);

								HSSFRow row;
								HSSFCell cell;
								Iterator<Row> rows = sheet.rowIterator();
								JSONObject mainobject = new JSONObject();
								while (rows.hasNext()) {
									try {
										row = (HSSFRow) rows.next();
										if (row.getRowNum() == 0) {
											continue;
										}
										Iterator cells = row.cellIterator();

										while (cells.hasNext()) {
											cell = (HSSFCell) cells.next();

//										 out.println("Column_Index : " + cell.getColumnIndex());

											String keysvalue = null;
											String valuedformula = null;

											if (cell.getColumnIndex() >= lastcolumn) {
//											 out.println("lastcolumn :"+lastcolumn);
												if (row.getRowNum() == 1) {
													objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																						// And
																						// any
																						// type of
													keysvalue = objDefaultFormat.formatCellValue(cell,
															objFormulaEvaluator);
													keyarraylist.add(keysvalue);
//												 out.println("keyarraylist :" + keyarraylist);

												}

												if (row.getRowNum() == 2) {

//									objFormulaEvaluator.evaluate(cell);
//									valuedata1 = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
//									// out.println(valuedata1);
//									valuearraylist.add(valuedata1);

													if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
//														out.println("Formula is " + cell.getCellFormula());
														valuedformula = cell.getCellFormula();
														valuearraylist.add(valuedformula);
													} else {
//													objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
//																						// And
//																						// any type of
//													valuedformula = objDefaultFormat.formatCellValue(cell,
//															objFormulaEvaluator);

														objFormulaEvaluator.evaluate(cell); // This will evaluate the
																							// cell,
																							// And any
																							// type of
																							// cell will return string
																							// value
														valuedformula = objDefaultFormat.formatCellValue(cell);
//													 out.println("Formula : "+valuedformula);
//													valuesarray.add(valued);
														valuearraylist.add(valuedformula);
													}

//												 out.println("Valuearraylist : " + valuearraylist);
												}
											} else {
//											out.println("in 12(cell.getColumnIndex() :: "+cell.getColumnIndex());
//											out.println("in lastcolumn) :: "+lastcolumn);
											}

										}
										JSONObject calcuobject = null;

										for (int k = 0; k < valuearraylist.size(); k++) {
											calcuobject = new JSONObject();
											// out.println("value : " + valuearraylist.get(k));
											calcuobject.put(keyarraylist.get(k), valuearraylist.get(k));
											calcuarray.put(calcuobject);
										}

										String calarr[] = new String[calcuarray.length()];
										for (int k = 0; k < calcuarray.length(); k++) {

											// outputfield.put(OutputdatalengthJson);
											calarr[k] = calcuarray.getString(k);
											

										}
										Node caldatanode = null;
										if (!calculationbuildernode.hasNode("Calculation_Data")) {
											caldatanode = calculationbuildernode.addNode("Calculation_Data");
										} else {
											caldatanode = calculationbuildernode.getNode("Calculation_Data");
										}
										caldatanode.setProperty("Calculation_Data", calarr);

									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}
								JSONArray outputjsonarray = new JSONArray();
								JSONArray valuejsonarray = new JSONArray();
								try {

									Node rulenameitr = carrotmainNode.getNode(projectname).getNode("Rule_Engine")
											.getNode(ruleengine_name).getNode(rulenamecalculation);
									Value[] datavalues = rulenameitr.getProperty("FieldData").getValues();
									Value[] datavaluesout = rulenameitr.getProperty("OutputField").getValues();
//								out.println("SF Value : " + datavalues);

									// Sfdata
									for (int i = 0; i < datavalues.length; i++) {
										String valuesdata = datavalues[i].getString();
										// out.println("Field : " + new JSONObject(valuesdata));
										valuejsonarray.put(new JSONObject(valuesdata));

										// out.println("SF Data : " + valuejsonarray);
									}

									for (int i = 0; i < datavaluesout.length; i++) {
										String outputvalues = datavaluesout[i].getString();
										outputjsonarray.put(new JSONObject(outputvalues));

									}

								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								calculationjsonobject.put("SFdata", valuejsonarray);
								calculationjsonobject.put("Outputdata", outputjsonarray);
								calculationjsonobject.put("CalculationBuilder", calcuarray);
								calculationjsonobject.put("rulename", rulenamecalculation);
								// out.println("calculationjsonobject"+calculationjsonobject);
								calculfullarray.put(calculationjsonobject);
//					out.println("calculfullarray"+calculfullarray);
								calculfileobject.put("user_name", username);
								calculfileobject.put("project_name", projectname);
								calculfileobject.put("ruleengine_name", ruleengine_name);
								calculfileobject.put("data", calculfullarray);

								out.println(calculfileobject.toString());
								// http://35.236.154.164:8082/portal/servlet/service/CalBuilder.postaddCal io
								// http://35.186.166.22:8082/portal/servlet/service/CalBuilder.postaddCal
								String apiurl = request.getScheme() + "://" + request.getServerName() + ":"
										+ request.getServerPort() + request.getContextPath()
										+ "/servlet/service/CalBuilder.postaddCal";
//									 String calculationdata = uploadToServer(bundleRes.getString(
//										CrRuleConstValue.StringConstant.URL_GET.value() + "CalBuilder.postaddCal"),
//										calculfileobject);
//										 String calculationdata = uploadToServer(apiurl,
//													calculfileobject);
//								out.println(calculationdata);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else {
					out.println("No user Exist");
				}

			}
			session.save();
		}

		catch (Exception e) {
			out.println("Exception : : :" + e.getMessage());
			e.printStackTrace();
		}
	}

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
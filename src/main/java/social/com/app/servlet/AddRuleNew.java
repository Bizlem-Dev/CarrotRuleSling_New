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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.jsoup.Jsoup;
import org.osgi.service.http.HttpService;

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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/AddRule" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

// http://35.236.154.164:8082/portal/servlet/service/RuleengineList.ruleenginelist
public class AddRuleNew extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repos;
	FreeTrial12 fr = new FreeTrial12();
	Session session = null;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
	static ResourceBundle bundleststic = ResourceBundle.getBundle("config");
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
PrintWriter out = response.getWriter();
		
		out.println("hello");
		try {
			
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

    	}
@Override
protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
		throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	Session session = null;

	try {
		if (request.getRequestPathInfo().getExtension().equals("postaddRule")) {
//			 out.println("In post method");
			session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));
			// out.println("session"+session);
			// Node content
			// =session.getRootNode().getNode("content").getNode("CARROT_RULE");
			// out.println("content"+content);
			// String data="";
			boolean hashnode = false;
//			Node userNode = null;
			boolean hashnode2 = false;
			Node projectnode = null;
			boolean hashnode3 = false;
			Node ruleenginenode = null;
			Node rulenamenode = null;
			Node rulenamenode1 = null;
			boolean hashnode4 = false;
			boolean rootrule = false;
			JSONObject rules = new JSONObject();
			JSONArray rulearry = null;
			JSONArray arrayrule = new JSONArray();
			JSONArray outputdat = new JSONArray();
			JSONObject rullss = new JSONObject();
			JSONObject json123 = null;
			JSONObject jsonobject = null;

			JSONObject suceess = new JSONObject();
			String count1 = null;
			// JSONArray errorsfdcarray = new JSONArray();
			JSONObject errorsfdcobject = new JSONObject();
			JSONObject innererrorsfdcobject = new JSONObject();
			JSONArray erroroutputarray = new JSONArray();
			JSONObject erroroutputobject = new JSONObject();
			JSONObject innererroroutputobject = new JSONObject();
			JSONArray finaljsonarray = new JSONArray();
			Node rulename = null;
			JSONArray fieldarray = new JSONArray();
			JSONArray outputfield = new JSONArray();
			String fieldJson = null;
			JSONArray sucessarray = new JSONArray();
			String OutputdatalengthJson = null;
			boolean hashnoderuleengine = false;
			Node ruleengine = null;
			String values = null;
			String valuesoutput = null;
			Node subnodes = null;
			JSONObject outobject = null;
			Node carrotmainNode = null;
			StringBuilder builder = new StringBuilder();
			BufferedReader bufferedReaderCampaign = request.getReader();
			String brokerageline;
			while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
				builder.append(brokerageline + "\n");
			}

			// out.println(g);
			// String ruledata=request.getParameter("json");
			JSONObject ruledataobj = new JSONObject(builder.toString());

			String username = ruledataobj.getString("user_name").replace("@", "_");
			try {
				String freetrialstatus = fr.checkfreetrial(username);
//				out.println("hello fr.checkfreetrial = " + freetrialstatus);
				carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//									out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

			} catch (Exception e) {
				// TODO: handle exception

			}
			if (carrotmainNode != null) {
				rules.put("current_sfdc_login_user_email", ruledataobj.getString("user_name"));
				rules.put("current_sfdc_login_user_name", ruledataobj.getString("user_name"));
				String projectname = ruledataobj.getString("project_name").replace(" ", "_");
				rules.put("project_name", ruledataobj.getString("project_name"));

				String ruleengine_name = ruledataobj.getString("ruleengine_name").replace(" ", "_");
				rules.put("rule_engine_name", ruledataobj.getString("ruleengine_name"));

				hashnode2 = carrotmainNode.hasNode(projectname);
				if (hashnode2 == false) {
//					 out.println("In false get a projectnode");
					projectnode = carrotmainNode.addNode(projectname);
				} else {
//					 out.println("In true get a projectnode");
					projectnode = carrotmainNode.getNode(projectname);
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");// dd/MM/yyyy
				Date date1 = new Date();
				String date = dateFormat.format(date1);
				if(!projectnode.hasProperty("created_date")) {
				projectnode.setProperty("created_date", date);
				}
				hashnoderuleengine = projectnode.hasNode("Rule_Engine");
				if (hashnoderuleengine == false) {
					// out.println("In false get a ruleenginenode");
					ruleengine = projectnode.addNode("Rule_Engine");
					// ruleengine.setProperty("jcr:count", String.valueOf(0));
				} else {
					// out.println("In true get a ruleenginenode");
					ruleengine = projectnode.getNode("Rule_Engine");
				}
				// hashnode3 = ruleengine.hasNode(ruleengine_name);
				if (!ruleengine.hasNode(ruleengine_name)) {
//					 out.println("In false get a ruleenginenode");
					ruleenginenode = ruleengine.addNode(ruleengine_name);
					// ruleenginenode.setProperty("jcr:count", String.valueOf(0));
				} else {
					// out.println("In true get a ruleenginenode");
					// ruleenginenode = ruleengine.getNode(ruleengine_name);
					ruleengine.getNode(ruleengine_name).remove();
					ruleenginenode = ruleengine.addNode(ruleengine_name);
				}
			
				JSONArray dataarray = ruledataobj.getJSONArray("data");
//				 out.print("dataarray::"+dataarray);
				int count = 0;
				JSONObject jsonRules = null;
				for (int i = 0; i < dataarray.length(); i++) {
					JSONArray arrayRules = new JSONArray();

					jsonRules = new JSONObject();
					// JSONArray error = new JSONArray();
					JSONObject m = dataarray.getJSONObject(i);
					String rule_from_json = m.getString("rulename");
					json123 = new JSONObject();
					// out.println("rule_from_json"+rule_from_json);
					ArrayList sfdckey = new ArrayList();
					ArrayList outputkey = new ArrayList();
					JSONObject data = dataarray.getJSONObject(i);
					String Rule_name = data.getString("rulename");
					json123.put("sr_no", count);
					json123.put("rule_name", Rule_name.replace(" ", ""));
					// System.out.println(Rule_name);
					JSONArray sfdata = data.getJSONArray("SFdata");

					rulearry = new JSONArray();
					for (int j = 0; j < sfdata.length(); j++) {
						jsonobject = new JSONObject();
						JSONObject innersfdc = sfdata.getJSONObject(j);
						String field = innersfdc.getString("field");
						String type = innersfdc.getString("type");
						String symbol_category = innersfdc.getString("symbol_category");
						String value = innersfdc.getString("value");

						if (!value.equals("")) {
							if (symbol_category.equals("=")) {

								jsonobject.put(field.replace(" ", "_"), value);

							} else {
								String addvalue = symbol_category + value;
								jsonobject.put(field.replace(" ", "_"), addvalue);

							}
							jsonobject.put("type",
									type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase());

							rulearry.put(jsonobject);
						}
					}

					json123.put("input_fields", rulearry);
					// out.println("rulearry :"+rulearry);

					JSONArray outputdata = data.getJSONArray("Outputdata");
					outputdat = new JSONArray();
					for (int p = 0; p < outputdata.length(); p++) {
						outobject = new JSONObject();
						JSONObject inneroutput = outputdata.getJSONObject(p);

						String Field12 = inneroutput.getString("field");
						String values12 = inneroutput.getString("value");
						outobject.put(Field12, values12);
						outobject.put("type", "String");

						// Iterator<String> keysoutput = inneroutput.keys();
						// while (keysoutput.hasNext()) {
						// // System.out.println("Key"+j+keys.next());
						// String blankkeycheackoutput = keysoutput.next();
						// // System.out.println("keys are" +blankkeycheackoutput);
						// valuesoutput = inneroutput.getString(blankkeycheackoutput);
						// if (valuesoutput.equals("")) {
						// // erroroutputobject.put(blankkeycheackoutput, valuesoutput);
						// // erroroutputobject.put("no",p);
						// inneroutput.put("no", p);
						// erroroutputarray.put(inneroutput);
						//
						// }
						// // outputkey.add(valuesoutput);
						// }

						outputdat.put(outobject);
						// countvalue++;

					}

					json123.put("output_fields", outputdat);

					rootrule = ruleenginenode.hasNode(rule_from_json.replace(" ", ""));
					if (rootrule == false) {
						rulenamenode1 = ruleenginenode.addNode(rule_from_json.replace(" ", ""));

						// out.println(rules);

					} else {
						// ruleenginenode.getNode(rule_from_json.replace(" ", ""));
						ruleenginenode.getNode(rule_from_json.replace(" ", "")).remove();
						rulenamenode1 = ruleenginenode.addNode(rule_from_json.replace(" ", ""));
					}
					// out.println(rulenamenode1);
					int SFlength = m.getJSONArray("SFdata").length();
					String sg[] = new String[SFlength];
					for (int j = 0; j < SFlength; j++) {
					
						fieldJson = m.getJSONArray("SFdata").getString(j);
						// fieldarray.put(fieldJson);
						sg[j] = fieldJson;
//						out.println("fieldJson:: string = "+fieldJson);
					}
//					 out.print("arraay fieldJson:: "+Arrays.toString(sg));

					int Outputdatalength = m.getJSONArray("Outputdata").length();
					String sh[] = new String[Outputdatalength];
					for (int k = 0; k < Outputdatalength; k++) {
						OutputdatalengthJson = m.getJSONArray("Outputdata").getString(k);
						// outputfield.put(OutputdatalengthJson);
						sh[k] = OutputdatalengthJson;
						
					}
//					out.print("arraay fieldJson  output:: "+Arrays.toString(sh));
					// out.print("OutputdatalengthJson::"+OutputdatalengthJson);
					// rulenamenode1.setProperty("id", String.valueOf(Integer.parseInt(count1)+1));
					rulenamenode1.setProperty("rulename", rule_from_json);

					rulenamenode1.setProperty("FieldData", sg);
					rulenamenode1.setProperty("OutputField", sh);
					// ruleenginenode.setProperty("jcr:count",String.valueOf(Integer.parseInt(count1)+1));
					// out.println("Sucessfully");
					session.save();
					// ruledataobj.put("Status","Saved Successfully");

					// }
					// out.println(ruledataobj);

					// out.println("suceess : "+suceess);
					sucessarray.put(data);
//Test Comment added for github
					// out.println("suceess : "+suceess);
					arrayrule.put(json123);
					rules.put("rules", arrayrule);
					// }
				}
				suceess.put("Status", "Sucessfully store");
				suceess.put("storedata", sucessarray);
//				out.println("sucessarray-- "+suceess);
				try {
					String urlParameters = rules.toString();
//					 out.println("Rules : "+urlParameters);
//34.74.125.253:8082
					String urlstring = "http://"+bundleststic.getString("carrotRuleDroolIP")+":8082/drools/generaterules";
				
//					 out.println("urlstring : "+urlstring);
					URL url = new URL(urlstring);
					// out.println("URL :"+url);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");

					conn.setDoOutput(true);

					OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

					writer.write(urlParameters);
					writer.flush();

					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//					 out.println("response code :: "+conn.getResponseCode());
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}
					writer.close();
					reader.close();

					// count++;

					// Excel Download
					int num = 0;
					int valuenum = 0;
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet sheet = workbook.createSheet("FirstSheet");
					HSSFRow rowvalue = sheet.createRow((short) 0);
					rowvalue.createCell(0).setCellValue("Raw Data");

					HSSFRow rowhead = sheet.createRow((short) 1);
					HSSFRow rowheadvalue = sheet.createRow((short) 2);

					JSONArray datajsonarray = suceess.getJSONArray("storedata");
					for (int i = 0; i < 1; i++) { //datajsonarray.length()

						JSONObject sfdcjson = datajsonarray.getJSONObject(i);
						JSONArray sfdcjsonarray = sfdcjson.getJSONArray("SFdata");
						JSONArray outputdataarray = sfdcjson.getJSONArray("Outputdata");

						for (int j = 0; j < sfdcjsonarray.length(); j++) {

							JSONObject sfdcjsondata = sfdcjsonarray.getJSONObject(j);
							String field = sfdcjsondata.getString("field");
							String value = sfdcjsondata.getString("value");
							rowhead.createCell(num++).setCellValue(field.toLowerCase());
							// if(!value.isEmpty()) {
							rowheadvalue.createCell(valuenum++).setCellValue(field.toLowerCase());
							// }
						}
						for (int k = 0; k < outputdataarray.length(); k++) {

							JSONObject outputjsondata = outputdataarray.getJSONObject(k);
							String field = outputjsondata.getString("field");
							String value = outputjsondata.getString("value");

							rowhead.createCell(num++).setCellValue(field.toLowerCase());

							rowheadvalue.createCell(valuenum++).setCellValue(field.toLowerCase());

						}
					}  //closed formula
						int noOfColumns = rowhead.getPhysicalNumberOfCells();
//						out.println("lastcol :: "+noOfColumns);
						sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, num - 1));
						rowvalue.createCell(num).setCellValue("Calculation Data");
//CrRuleConstValue.StringConstant.CALCULATION_FILE_DOWNLOAD.value() CALCULATION_FILE_DOWNLOAD
						FileOutputStream fileOut = new FileOutputStream(
								CrRuleConstValue.StringConstant.CALCULATION_FILE_DOWNLOAD.value());
						workbook.write(fileOut);
						fileOut.close();
						// out.println("Your excel file has been generated!");
						InputStream is = new FileInputStream(
								CrRuleConstValue.StringConstant.CALCULATION_FILE_DOWNLOAD.value());

						Node downloadnode = null;
						Node calcnode = null;
						Node jcrNode = null;
						if (!projectnode.hasNode("Calculation_Excel_File")) {

							downloadnode = projectnode.addNode("Calculation_Excel_File");
						} else {
							downloadnode = projectnode.getNode("Calculation_Excel_File");
							downloadnode.remove();

							downloadnode = projectnode.addNode("Calculation_Excel_File");

						}
						if (!downloadnode.hasNode("Calculation_Download_File")) {

							calcnode = downloadnode.addNode("Calculation_Download_File");
						} else {
							calcnode = downloadnode.getNode("Calculation_Download_File");
							calcnode.remove();
							calcnode = downloadnode.addNode("Calculation_Download_File");

						}
						Node subfileNode = null;
						if (!calcnode.hasNode("Calculation_Download_File")) {

							subfileNode = calcnode.addNode("CalculationExcelDownload.xls", "nt:file");
						}
						
						String url1 = request.getScheme() + "://" + request.getServerName() + ":"
								+ request.getServerPort() + request.getContextPath()
								+ "/content/services/freetrial/users/" + username + "/"
								+ "CarrotruleMainNode/" + projectname +"/Calculation_Excel_File/Calculation_Download_File/"
										+ "CalculationExcelDownload.xls";
						calcnode.setProperty("Path",url1);
//						calcnode.setProperty("Path",
//								CrRuleConstValue.StringConstant.PORTAL_URL.value() + "content/CARROT_RULE/"
//										+ username + "/" + projectname
//										+ "/Calculation_Excel_File/Calculation_Download_File/"
//										+ "CalculationExcelDownload.xls");
						
						calcnode.setProperty("Last_Column", noOfColumns);

						jcrNode = subfileNode.addNode("jcr:content", "nt:resource");

						jcrNode.setProperty("jcr:data", is);

						jcrNode.setProperty("jcr:mimeType", "attach");

						// out.println(jcrNode);
						
						suceess.put("Download_url", calcnode.getProperty("Path").getString());
//					}//for loop

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
//			String json = uploadToServer(
//					"http://35.236.213.87:8080/drools/callrules/carrotrule@xyz.com_Test2_Rule2/fire");
				out.println(suceess);
			} else {
				out.println("No user Exist");
				// TODO: handle exception
			}
			session.save();
		}
	} catch (Exception e) {
		out.println("Exception : : :" + e.getMessage());
	}
	
}

}
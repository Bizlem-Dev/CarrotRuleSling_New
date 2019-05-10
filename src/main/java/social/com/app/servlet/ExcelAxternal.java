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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/ExcelExternal" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class ExcelAxternal extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	Session session = null;
	// @Reference
	// private SchedulerService product;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("hello");
		if (request.getRequestPathInfo().getExtension().equals("excelread")) {
			out.println("Get");
			try {

				InputStream ExcelFileToRead = new FileInputStream(
						CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + "sample.xls");

				HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

				// Get first/desired sheet from the workbook
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFRow row;
				HSSFCell cell;
				Iterator<Row> rows = sheet.rowIterator();

				while (rows.hasNext()) {
					row = (HSSFRow) rows.next();
					// out.println ("Row No.: " + row.getRowNum());

					if (row.getRowNum() == 0) {
						continue; // just skip the rows if row number is 0 or 1

					}
					Iterator cells = row.cellIterator();

					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();

						// out.println("Column_Index : "+cell.getColumnIndex());
						if (cell.getColumnIndex() > 4) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								out.print(cell.getStringCellValue() + " ");
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

							} else {
								// U Can Handel Boolean, Formula, Errors
							}
						}
					}
					System.out.println();
				}

				ExcelFileToRead.close();

			} catch (Exception e) {
				out.println("Excepation =" + e.getMessage());
			}

		} else if (request.getRequestPathInfo().getExtension().equals("sfdc")) {
			JSONArray jsonarray = null;
			JSONObject jsonObject12 = new JSONObject();
			JSONObject jsonObjectadd = null;
			// PrintWriter out = response.getWriter();
			int count = 0;
			jsonarray = new JSONArray();
			JSONObject subobject = null;
			String username = "vik_bizlem.com";
			String project = "Bizlem_project";
			Node pg2nodes = null;
			Value[] data = null;
			out.println("SFDC");

			try {
				Session session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node ip = session.getRootNode().getNode("content").getNode("CARROT_RULE");

				NodeIterator iterator = ip.getNode(username).getNode(project).getNodes();
				subobject = new JSONObject();
				Node subnodes = null;
				String nodevalue = null;
				JSONArray mainarray = null;
				Node sfsubnodes = null;
				Node subnode = null;
				JSONArray subarray = null;
				JSONObject multiobject = null;
				JSONArray multiarray = null;
				JSONArray singlearray = null;
				JSONObject singleobject = null;
				Node sfdcnodename = null;
				Node transformdatanodename = null;
				Node ruleenginenode = null;
				while (iterator.hasNext()) {

					subnodes = iterator.nextNode();// sfobject and 2 nd node
					nodevalue = subnodes.getName();// sfobject

					if (nodevalue.equals("SFDC_SELECTDATA")) {
						NodeIterator sfdcitr = ip.getNode(username).getNode(project).getNode("SFDC_SELECTDATA")
								.getNodes();
						mainarray = new JSONArray();
						// mainarray.put("Hello");
						JSONObject SFDC_SELECTDATA_Json = new JSONObject();
						JSONArray SFDC_SELECTDATA_JsonArray = new JSONArray();
						while (sfdcitr.hasNext()) {

							singlearray = new JSONArray();
							sfdcnodename = sfdcitr.nextNode();// case
							// singleobject = new JSONObject();
							data = sfdcnodename.getProperty(sfdcnodename.getName()).getValues();
							// JSONArray rules_values_arr = new JSONArray();

							for (int i = 0; i < data.length; i++) {
								String values = data[i].getString();
								String da = (String) new JSONObject(values).get("field");
								out.println("Field Vales : " + da);
							}
						}
					}

				}
			} catch (Exception e) {
				e.getMessage();

			}
		}

	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Node brokerage = null;
		Node project_name = null;
		Node primarykey = null;
		Node sf_object = null;
		Session session = null;
		Node case1 = null;
		Node ruls_node = null;
		Node PrimaryNode = null;

		JSONObject test = new JSONObject();
		try {

			// out.println("Remote User : : :" + request.getRemoteUser());

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
			Node content = session.getRootNode().getNode("content");
			String username = null;
			String project = null;
			String primary = null;

			JSONObject result = new JSONObject();
			if (request.getRequestPathInfo().getExtension().equals("excel")) {
				if (!content.hasNode("CARROT_RULE")) {
					brokerage = content.addNode("CARROT_RULE");
				} else {
					brokerage = content.getNode("CARROT_RULE");
				}

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
				project = jsonObject.getString("project_name").replace(" ", "_");
				// String rule=jsonObject.getString("rule_Engne");
				primary = jsonObject.getString("primary_key").replace(":", "_");
				result.put("Status", "Saved Successfully");
				result.put("user_name", username);
				result.put("project_name", project);
				result.put("primary_key", primary);

				JSONObject webservicesjson = new JSONObject();
				if (jsonObject.has("WebServices")) {
					// out.println("Yes");
					webservicesjson = jsonObject.getJSONObject("WebServices");
					// out.println(webservicesjson);

					if (!brokerage.hasNode(username.replace("@", "_"))) {
						ruls_node = brokerage.addNode(username.replace("@", "_"));
						// ruls_node.setProperty("Rule_name", rule);
					} else {
						ruls_node = brokerage.getNode(username.replace("@", "_"));
						// ruls_node.setProperty("Rule_name", rule);
					}
					if (!ruls_node.hasNode(project)) {
						project_name = ruls_node.addNode(project);
					} else {
						project_name = ruls_node.getNode(project);

						// out.println(" poject eles...");
					}

					if (!project_name.hasNode("EXTERNAL_DATA")) {
						sf_object = project_name.addNode("EXTERNAL_DATA");
						sf_object.setProperty("primary_name", primary);
						// out.println(" Add_External_Parameter if ...");
					} else {
						sf_object = project_name.getNode("EXTERNAL_DATA");
						sf_object.setProperty("primary_name", primary);
						// out.println(" Add_External_Parameter eles...");
					}
					/*
					 * if (!sf_object.hasNode(primary)) { PrimaryNode = sf_object.addNode(primary);
					 * } else { PrimaryNode = sf_object.getNode(primary);
					 * 
					 * }
					 */

					if (!sf_object.hasNode("WebServices")) {
						primarykey = sf_object.addNode("WebServices");
						// out.println(" primarykey if ...");
					} else {
						primarykey = sf_object.getNode("WebServices");

					}

					JSONObject object2 = webservicesjson.getJSONObject("EXTERNAL_DATA");
					// out.println("object2 :"+object2);

					JSONObject resuletjson = getJsonAlldata(object2, response, session, username, project, primary);

					// out.println("Message:"+resuletjson);

					result.put("Message", resuletjson);
					// out.println("resuletjson :" + resuletjson);
					String key;
					Iterator<String> keys = object2.keys();
					while (keys.hasNext()) {
						key = (String) keys.next();
						JSONArray jsonArray = (JSONArray) object2.get(key);
						String sg[] = new String[jsonArray.length()];

						for (int i = 0; i < jsonArray.length(); i++) {
							// values=jsonArray.getString(i);
							// list.add(values);
							sg[i] = jsonArray.getString(i);

						}

						// out.println("jsonobjectcampen :"+sg.length);
						if (!primarykey.hasNode(key)) {
							case1 = primarykey.addNode(key);
							// out.println(sf_object);
							// sf_object.setProperty(key, (Value) list);
							case1.setProperty(key, sg);
							// out.println("key_name If : : : " );
						} else {
							case1 = primarykey.getNode(key);
							case1.setProperty(key, sg);
							// out.println("key_name else : : : " );
						}
					}

				}
				if (jsonObject.has("File_Data")) {

					JSONArray filename = jsonObject.getJSONArray("File_Data");
					// out.println("File_Data : "+filename);
					for (int i = 0; i < filename.length(); i++) {

						JSONObject filejsonobject = filename.getJSONObject(i);
						String data = filejsonobject.getString("filedata").replace(" ", "_");
						String name = filejsonobject.getString("filename").replace(" ", "_");
						// out.println("File_Name : "+name);

						byte[] bytes = Base64.decode(data);
						//
						Node fileNode = null;
						Node jcrNode1 = null;
						Node filefileNode = null;
						Node fileExtrnal = null;
						Node fileName = null;
						Node content_node = session.getRootNode().getNode("content").getNode("CARROT_RULE");
						InputStream myInputStream = new ByteArrayInputStream(bytes);

//						String url = "http://35.221.253.98:8082/portal/content/CARROT_RULE/vik_bizlem.com/Bizlem_project/EXTERNAL_DATA/File/"
//								+ name;
						if (!sf_object.hasNode("File")) {
							fileName = sf_object.addNode("File");

						} else {
							fileName = sf_object.getNode("File");
							// fileName.setProperty("URL", url);
						}

						Node subfileNode = subfileNode = fileName.addNode(name, "nt:file");

						jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");

						jcrNode1.setProperty("jcr:data", myInputStream);

						jcrNode1.setProperty("jcr:mimeType", "attach");

					}

				}

			}

			session.save();

			try {

				JSONObject messagejson = result.getJSONObject("Message");
				JSONObject sfdcjson = messagejson.getJSONObject("SFDC_SelectData");
				Iterator keys = sfdcjson.keys();

				String field = null;
				String type = null;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("FirstSheet");
				int num = 0;
				HSSFRow rowhead = sheet.createRow((short) 0);
				while (keys.hasNext()) {
					JSONArray insidejsonarray = sfdcjson.getJSONArray((String) keys.next());
					for (int i = 0; i < insidejsonarray.length(); i++) {

						JSONObject object = insidejsonarray.getJSONObject(i);
						field = object.getString("field");
						rowhead.createCell(num++).setCellValue(field);

						//
					}
				}
				JSONArray externaldataarray = messagejson.getJSONArray("EXTERNAL_DATA");
				String outputfielddata = null;
				for (int i = 0; i < externaldataarray.length(); i++) {
					JSONObject outputfieldjson = externaldataarray.getJSONObject(i);

					outputfielddata = outputfieldjson.getString("output_name");

					rowhead.createCell(num++).setCellValue(outputfielddata);

				}

				JSONArray exceldatarray = messagejson.getJSONArray("EXCEL_DATA");
				String excelfield = null;
				for (int i = 0; i < exceldatarray.length(); i++) {
					JSONObject excelfieldjson = exceldatarray.getJSONObject(i);

					excelfield = excelfieldjson.getString("field");

					rowhead.createCell(num++).setCellValue(excelfield);

				}
				int noOfColumns = rowhead.getPhysicalNumberOfCells();

				FileOutputStream fileOut = new FileOutputStream(
						CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + "NewFiles.xls");
				workbook.write(fileOut);
				fileOut.close();
				// out.println("Your excel file has been generated!");
				InputStream is = new FileInputStream(
						CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + "NewFiles.xls");

				Node jcrNode = null;
				Node downloadnode = null;

				if (!sf_object.hasNode("Download_Excel")) {

					downloadnode = sf_object.addNode("Download_Excel");
				} else {
					downloadnode = sf_object.getNode("Download_Excel");

				}
				Node subfileNode = null;
				if (!downloadnode.hasNode("Download.xls")) {
					subfileNode = downloadnode.addNode("Download.xls", "nt:file");
				} else {
					subfileNode = downloadnode.getNode("Download.xls");
				}
				// out.println(subfileNode);
				/*
				 * downloadnode.setProperty("Path",
				 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
				 * project + "/EXTERNAL_DATA/Download_Excel/" + "Download.xls");
				 */
				downloadnode.setProperty("Path",
						request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/bin/cpm/nodes/property.bin/content/CARROT_RULE/"
								+ username + "/" + project + "/EXTERNAL_DATA/Download_Excel/" + "Download.xls"
								+ "/_jcr_content?name=jcr%3Adata");

				downloadnode.setProperty("Last_Column", noOfColumns);

				jcrNode = subfileNode.addNode("jcr:content", "nt:resource");

				jcrNode.setProperty("jcr:data", is);
				// jcrNode.setProperty("jcr:data", stream);

				jcrNode.setProperty("jcr:mimeType", "attach");

				// out.println(jcrNode);
				session.save();

				File file = new File(CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + "NewFiles.xls");

				if (file.delete()) {
					// out.println("File deleted successfully");
				} else {
					// out.println("Failed to delete the file");
				}

			} catch (Exception ex) {
				out.println(ex);
			}
			Node excelurl = brokerage.getNode(username).getNode(project).getNode("EXTERNAL_DATA")
					.getNode("Download_Excel");
			String excelurlpath = excelurl.getProperty("Path").getString();
			result.put("Download_Excel", excelurlpath);

			out.println(result);

		} catch (Exception e) {
			out.println("Excepation =" + e.getMessage());
		}

	}

	public JSONObject getJsonAlldata(JSONObject object2, SlingHttpServletResponse response, Session session,
			String username, String project, String primary) throws IOException {
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

			int count = 0;
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

			// out.println("output value:"+jsonArray);

			Node ip = session.getRootNode().getNode("content").getNode("CARROT_RULE");
			// out.println("ip : "+ip);
			Value[] data = null;
			NodeIterator iterator = ip.getNode(username).getNode(project).getNodes();
			subobject = new JSONObject();
			while (iterator.hasNext()) {

				subnodes = iterator.nextNode();// sfobject and 2 nd node
				nodevalue = subnodes.getName();// sfobject

				if (nodevalue.equals("SFDC_SELECTDATA")) {
					// out.println("nodevalue :"+nodevalue);
					NodeIterator page2itr = ip.getNode(username).getNode(project).getNode(nodevalue).getNodes();

					while (page2itr.hasNext()) {
						pg2nodes = page2itr.nextNode();
						// out.println(pg2nodes);
						String pg2nodevalue = pg2nodes.getName();
						// out.println("pg2nodevalue :"+pg2nodevalue);
						data = pg2nodes.getProperty(pg2nodevalue).getValues();
						for (int i = 0; i < data.length; i++) {
							mainarray = new JSONArray();
							String valuestring = data[i].getString();
							// subobject.put(pg2nodevalue, valuestring);
							// out.println("valuestring :"+new JSONObject(valuestring));
							mainarray.put(new JSONObject(valuestring));
							subobject.put(pg2nodevalue, mainarray);

						}

						allvaluejson.put("SFDC_SelectData", subobject);
						allvaluejson.put("EXTERNAL_DATA", jsonArray);
						JSONArray array12 = new JSONArray();
						JSONObject object1 = new JSONObject();
						object1.put("field", "name");
						JSONObject object12 = new JSONObject();
						object12.put("field", "city");
						JSONObject object3 = new JSONObject();
						object3.put("field", "fieldvalue");
						array12.put(object1);
						array12.put(object12);
						array12.put(object3);
						allvaluejson.put("EXCEL_DATA", array12);
						// out.println(allvaluejson);
					}
				}

			}

		} catch (Exception e) {

		}

		return allvaluejson;
	}

}
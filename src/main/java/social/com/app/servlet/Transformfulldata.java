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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/transformrawdata1" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class Transformfulldata extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;

	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
//		out.println("hello");
		try {

			if (request.getRequestPathInfo().getExtension().equals("Demopage")) {

				// request.getRequestDispatcher("/content/static/.brokerage").forward(request,
				// response);

				request.getRequestDispatcher("/content/static/.fistpage").forward(request, response);

			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Session session = null;
		Node rulena = null;
		Node brokerage = null;
		Node ruls_node = null;
		Node project_name = null;
		Node transform = null;
		Node keyandvalues = null;

		// out.println("Remote User : : :" + request.getRemoteUser());
		if (request.getRequestPathInfo().getExtension().equals("form")) {
			try {
				session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node content = session.getRootNode().getNode("content");

				JSONArray jsonarray = null;
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
				JSONObject jsonObject = new JSONObject(builder.toString());
				String username = jsonObject.getString("user_name").replace("@", "_");
				String project = jsonObject.getString("project_name").replace(" ", "_");

				// String rule=jsonObject.getString("rule_Engine");

				if (!brokerage.hasNode(username.replace("@", "_"))) {
					ruls_node = brokerage.addNode(username);
					// out.println(" username if ..." +username);
				} else {
					ruls_node = brokerage.getNode(username);
					// out.println(" username else ..."+ username);
				}
				if (!ruls_node.hasNode(project)) {
					project_name = ruls_node.addNode(project);
					// project_name.setProperty("Rule_name", rule);
					// out.println(" Project if ...");
				} else {
					project_name = ruls_node.getNode(project);
					// project_name.setProperty("Rule_name", rule);
					// out.println(" project eles...");
				} /*
					 * if(!project_name.hasNode(rule)) { rulena=project_name.addNode(rule); }else {
					 * rulena=project_name.getNode(rule); }
					 */

				if (!project_name.hasNode("TRANSFORM_DATA")) {
					transform = project_name.addNode("TRANSFORM_DATA");
					// out.println(" Transform if ...");
				} else {
					transform = project_name.getNode("TRANSFORM_DATA");
					// out.println(" Transform eles...");
				}
				JSONObject object2 = jsonObject.getJSONObject("TRANSFORM");

				JSONArray transformjson = object2.getJSONArray("Transform");
				JSONObject boolean1 = getkeyvalue(transformjson, response, username, project);// call mathod
				JSONObject jsonvalueobject = getkeyjson(transformjson, jsonObject, response, username, project);// call
																												// mathod
				// out.println("list of data :"+jsonvalueobject);
				int jsonvalue = 0;
				String key;
				Iterator<String> keys = object2.keys();
				while (keys.hasNext()) {
					key = (String) keys.next();
					JSONArray objectjson = boolean1.getJSONArray("Error_Data");
					jsonvalue = objectjson.length();
					jsonarray = new JSONArray();
					if (jsonvalue == 0) {
						JSONArray jsonArray = (JSONArray) object2.get(key);
						String sg[] = new String[jsonArray.length()];
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonarray = new JSONArray();
							sg[i] = jsonArray.getString(i);
						}
						if (!transform.hasNode(key)) {
							keyandvalues = transform.addNode(key);
							keyandvalues.setProperty(key, sg);
						} else {
							keyandvalues = transform.getNode(key);
							keyandvalues.setProperty(key, sg);
						}
						session.save();
					}
				}
				if (jsonvalue == 0) {
					out.println(jsonvalueobject);
					// out.println("Massage := Save Successfull");
				} else {
					out.println(boolean1);
					// out.println("Massage := Error not Successfull ");
				}

			} catch (Exception e) {
				out.print(e.getMessage());
			}

		}

		else if (request.getRequestPathInfo().getExtension().equals("ExcelUpload")) {

			// request.getRequestDispatcher("/content/static/.brokerage").forward(request,
			// response);
			Node carrotrule = null;
			Node usernamenode = null;
			Node projectnamenode = null;
			Node transformnode = null;
			Node transformfilenode = null;
			try {
				session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node content = session.getRootNode().getNode("content");

				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String line;
				while ((line = bufferedReaderCampaign.readLine()) != null) {
					builder.append(line + "\n");
				}
				JSONObject fulljsonobject = new JSONObject(builder.toString());

				if (!content.hasNode("CARROT_RULE")) {
					carrotrule = content.addNode("CARROT_RULE");
				} else {
					carrotrule = content.getNode("CARROT_RULE");
				}
				String username = fulljsonobject.getString("user_name").replace("@", "_");
				String project = fulljsonobject.getString("project_name").replace(" ", "_");

				// String rule=jsonObject.getString("rule_Engine");

				if (!carrotrule.hasNode(username)) {
					usernamenode = carrotrule.addNode(username);
				} else {
					usernamenode = carrotrule.getNode(username);
					// out.println(" username else ..."+ username);
				}
				if (!usernamenode.hasNode(project)) {
					projectnamenode = usernamenode.addNode(project);

				} else {
					projectnamenode = usernamenode.getNode(project);

				}
				if (!projectnamenode.hasNode("TRANSFORM_DATA")) {
					transformnode = projectnamenode.addNode("TRANSFORM_DATA");
					// out.println(" Transform if ...");
				} else {
					transformnode = projectnamenode.getNode("TRANSFORM_DATA");
					// out.println(" Transform eles...");
				}
				if (!transformnode.hasNode("File")) {
					transformfilenode = transformnode.addNode("Uploaded_File");
					// out.println(" Transform if ...");
				} else {
					transformfilenode = transformnode.getNode("Uploaded_File");
					// out.println(" Transform eles...");
				}

				JSONObject filejsonobject = fulljsonobject.getJSONObject("File_Data");
				String filename = filejsonobject.getString("filename");
				transformfilenode.setProperty("Url",
						request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/bin/cpm/nodes/property.bin/content/CARROT_RULE/"
								+ username + "/" + project + "/TRANSFORM_DATA/Uploaded_File/" + filename
								+ "/_jcr_content?name=jcr%3Adata");

				/*
				 * transformfilenode.setProperty("Url",
				 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
				 * project + "/TRANSFORM_DATA/Uploaded_File/" + filename);
				 */
				String filedata = filejsonobject.getString("filedata");
				// out.println("File_Name : " + filename + " File_Data : " + filedata);
				byte[] bytes = Base64.decode(filedata);
				InputStream myInputStream = new ByteArrayInputStream(bytes);

				Node subfileNode = transformfilenode.addNode(filename, "nt:file");
				Node jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");
				jcrNode1.setProperty("jcr:data", myInputStream);
				jcrNode1.setProperty("jcr:mimeType", "attach");

				session.save();
				String excelurl = transformfilenode.getProperty("Url").getString();
				out.println("Excel Url : " + excelurl);
				URL url = new URL(excelurl);
				URLConnection conn = url.openConnection();

				String contentType = conn.getContentType();
				int contentLength = conn.getContentLength();
				if (contentType.startsWith("text/") || contentLength == -1) {
					// System.out.println("This is not a binary file.");
				}

				Node externalnode = projectnamenode.getNode("EXTERNAL_DATA").getNode("Download_Excel");
				Long lastcolumn = externalnode.getProperty("Last_Column").getLong();
				out.println(lastcolumn + (long) 1);
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
					System.out.println("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
				}
				FileOutputStream streamout = new FileOutputStream(
						CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + filename);
				streamout.write(data);
				streamout.close();
				raw.close();
				in.close();

				InputStream ExcelFileToRead = new FileInputStream(
						CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + filename);
				HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

				// Get first/desired sheet from the workbook
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFRow row;
				HSSFCell cell;
				Iterator<Row> rows = sheet.rowIterator();

				while (rows.hasNext()) {
					row = (HSSFRow) rows.next();
					out.println("Row No.: " + row.getRowNum());

					if (row.getRowNum() == lastcolumn) {
						continue;

					}
					Iterator cells = row.cellIterator();

					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();

						out.println("Column_Index : " + cell.getColumnIndex());
						if (cell.getColumnIndex() > lastcolumn) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								out.print(cell.getStringCellValue() + " ");
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								Double d = new Double(cell.getNumericCellValue());
								int i = d.intValue();
								out.println("Cell Numeric : " + i);
							} else {
								// U Can Handel Boolean, Formula, Errors
							}
						}
					}
					System.out.println();
				}

				ExcelFileToRead.close();

			} catch (Exception ex) {

				out.println("Exception ex : " + ex.getMessage());
			}
		}
	}

	public JSONObject getkeyvalue(JSONArray trformjson, SlingHttpServletResponse response, String username,
			String project) {
		JSONArray jsonarray = null;
		JSONObject json = null;
		JSONObject jsonObject2 = new JSONObject();
		try {
			int count = 0;
			jsonarray = new JSONArray();
			PrintWriter out = response.getWriter();
			for (int i = 0; i <= trformjson.length(); i++) {

				JSONObject object = trformjson.getJSONObject(i);
				Iterator<String> ke = object.keys();
				while (ke.hasNext()) {

					String[] regexvalue = { "+", "=", "-", "=>", "=<", "!=" };
					String keyname = (String) ke.next();
					String values = object.getString(keyname);

					String[] cherString = values.split("");

					if (keyname.equals("") || values.equals("")) {
						json = new JSONObject();
						// out.println("LineNo. "+ count+", key:"+keyname +", valu:"+values);
						json.put("no", count);
						json.put("fieldname", keyname);
						json.put("value", values);
						jsonarray.put(json);
					}
					count++;
				}
				jsonObject2.put("status", "Error");
				jsonObject2.put("Message", "NOT Successfull");
				jsonObject2.put("Error_Data", jsonarray);
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return jsonObject2;
	}

	public JSONObject getkeyjson(JSONArray trformjson, JSONObject responsejson, SlingHttpServletResponse response,
			String username, String project) throws IOException {
		JSONArray jsonarray = null;
		JSONObject jsonObject12 = new JSONObject();
		JSONObject jsonObjectadd = null;
		int count = 0;
		jsonarray = new JSONArray();

		for (int j = 0; j <= trformjson.length(); j++) {

			String sv = null;
			JSONObject object;
			try {
				Session session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node ip = session.getRootNode().getNode("content").getNode("CARROT_RULE");
				object = trformjson.getJSONObject(j);
				Iterator<String> ke = object.keys();
				while (ke.hasNext()) {
					jsonObjectadd = new JSONObject();
					sv = "1" + count;
					String keyname = (String) ke.next();
					String values = object.getString(keyname);
					jsonObjectadd.put("no", count);
					jsonObjectadd.put("fieldname", keyname);
					jsonObjectadd.put("value", values);
					jsonarray.put(jsonObjectadd);
					count++;

				}
				JSONObject transform = responsejson.getJSONObject("TRANSFORM");
				JSONArray rawdataarray = transform.getJSONArray("Raw_Data");
				for (int i = 0; i < rawdataarray.length(); i++) {
					JSONObject jsondata = new JSONObject();

					// out.println("Data 1 "+datajson);
					JSONObject rawdatajson = rawdataarray.getJSONObject(i);
					String inputname = rawdatajson.getString("input_name");
					// datajson.put(key, value)
					jsondata.put("no", count++);
					jsondata.put("fieldname", inputname);
					jsondata.put("value", inputname);
					jsonarray.put(jsondata);
				}
				jsonObject12.put("status", "save");
				jsonObject12.put("Message", "Save Successfull ");
				jsonObject12.put("Save_Data", jsonarray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

		}
		return jsonObject12;
	}

}
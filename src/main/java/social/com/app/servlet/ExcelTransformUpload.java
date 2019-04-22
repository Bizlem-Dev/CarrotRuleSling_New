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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/ExcelTransform1" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class ExcelTransformUpload extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	// @Reference
	// private SchedulerService product;

	final int NUMBEROFRESULTSPERPAGE = 10;
	private static final long serialVersionUID = 1L;
	Boolean bool = true;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("hello");
	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("ExcelUploadDemo")) {

			// request.getRequestDispatcher("/content/static/.brokerage").forward(request,
			// response);
			Session session = null;
			Node carrotrule = null;
			Node usernamenode = null;
			Node projectnamenode = null;
			Node transformnode = null;
			Node transformfilenode = null;
			String filename = null;
			String filedata = null;
			ArrayList<String> keys = new ArrayList();
			ArrayList<String> values = new ArrayList();
			JSONArray dataarra = new JSONArray();
			JSONObject object = new JSONObject();

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

				// out.println("Transform Json:" + transformfilejson);
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
				///////////////////////////////////////////////////////////////////////////////////
				Node transformsubnode = null;
				if (!transformnode.hasNode("Transform")) {
					transformsubnode = transformnode.addNode("Transform");
					// out.println(" Transform if ...");
				} else {
					transformsubnode = transformnode.getNode("Transform");
					// out.println(" Transform eles...");
				}
				if (!transformnode.hasNode("File")) {
					transformfilenode = transformnode.addNode("Uploaded_File");
					// out.println(" Transform if ...");
				} else {
					transformfilenode = transformnode.getNode("Uploaded_File");
					// out.println(" Transform eles...");
				}

				if (fulljsonobject.has("Transform_File_Data")) {
					JSONObject transformfilejson = fulljsonobject.getJSONObject("Transform_File_Data");

					out.println("Yes");
					filename = transformfilejson.getString("filename");
					filedata = transformfilejson.getString("filedata");

					/*
					 * transformfilenode.setProperty("Url",
					 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
					 * project + "/TRANSFORM_DATA/Uploaded_File/" + filename);
					 */
					transformfilenode.setProperty("Url",
							request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
									+ request.getContextPath() + "/bin/cpm/nodes/property.bin/content/CARROT_RULE/"
									+ username + "/" + project + "/TRANSFORM_DATA/Uploaded_File/" + filename
									+ "/_jcr_content?name=jcr%3Adata");

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
						System.out.println("This is not a binary file.");
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
						if (row.getRowNum() == 0) {
							continue;
						}
						Iterator cells = row.cellIterator();

						while (cells.hasNext()) {
							cell = (HSSFCell) cells.next();

							// out.println("Column_Index : " + cell.getColumnIndex());

							String keysvalue = null;
							String valuedata = null;
							if (cell.getColumnIndex() > lastcolumn) {
								if (row.getRowNum() == 1) {
									JSONObject keyobject = new JSONObject();
									objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of
									keysvalue = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
									keys.add(keysvalue);

									// dataarray.put(keyobject);
								}
								if (row.getRowNum() == 2) {
									JSONObject valueobject = new JSONObject();

									objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of
																		// cell will return string value
									valuedata = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
									values.add(valuedata);
									// valueobject.put("Value", valuedata);
									// dataarray.put(valueobject);
								}

								// mainobject.put("Data", dataarray);

							}

						}
					}
					//

					for (int i = 0; i < keys.size(); i++) {
						mainobject.put(keys.get(i), values.get(i));

					}
					JSONObject transformjson = new JSONObject();
					// out.println("Object : " + object);
					dataarra.put(mainobject);
					transformjson.put("TRANSFORM", dataarra);
					object.put("user_name", username);
					object.put("project_name", project);
					object.put("Transform", transformjson);
					out.println(object);

					session.save();
					ExcelFileToRead.close();
				}
			} catch (Exception ex) {

				out.println("Exception ex : " + ex.getMessage());
			}
		}

	}

	public JSONObject getkeyvalue(JSONArray datajson, SlingHttpServletResponse response, String username,
			String project) {
		JSONArray jsonarray = null;
		JSONObject json = null;
		JSONObject jsonObject2 = new JSONObject();
		try {
			int count = 0;
			jsonarray = new JSONArray();
			PrintWriter out = response.getWriter();
			for (int i = 0; i <= datajson.length(); i++) {

				JSONObject object = datajson.getJSONObject(i);
				Iterator<String> ke = object.keys();
				while (ke.hasNext()) {

					String[] regexvalue = { "+", "=", "-", "=>", "=<", "!=" };
					String keyname = (String) ke.next();
					// out.println("keyname :"+keyname);
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
					// jsonObject2.put("Error_Data ", jsonObject);
				}
				// count++;
				jsonObject2.put("status", "Error");
				jsonObject2.put("Message", "NOT Successfull");
				jsonObject2.put("Error_Data", jsonarray);
				// out.println("Json_Object : "+jsonObject);
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return jsonObject2;
	}

	public JSONObject getkeyjson(JSONArray datajson, SlingHttpServletResponse response, String username, String project)
			throws IOException {
		JSONArray jsonarray = null;
		JSONObject jsonObject12 = new JSONObject();
		JSONObject jsonObjectadd = null;
		// PrintWriter out = response.getWriter();
		int count = 0;
		jsonarray = new JSONArray();
		for (int j = 0; j <= datajson.length(); j++) {

			String sv = null;
			JSONObject object;
			try {

				object = datajson.getJSONObject(j);

				// out.println("Transform Object :"+object);
				Iterator<String> ke = object.keys();
				while (ke.hasNext()) {
					jsonObjectadd = new JSONObject();
					sv = "1" + count;
					String keyname = (String) ke.next();
					// out.println("keyname"+keyname);
					String values = object.getString(keyname);

					jsonObjectadd.put("no", count);
					jsonObjectadd.put("fieldname", keyname);
					jsonObjectadd.put("value", values);

					jsonarray.put(jsonObjectadd);
					count++;
				}
				jsonObject12.put("status", "save");
				jsonObject12.put("Message", "Save Successfull ");
				jsonObject12.put("Save_Data", jsonarray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return jsonObject12;
	}

}
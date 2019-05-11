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
import com.service.FreeTrial12;

// TODO: Auto-generated Javadoc
/**
 * The Class AdvancedPage is used to create multiple rule engines.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/AdvancedPage" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")
// http://35.236.154.164:8082/portal/servlet/service/AdvancedPage.advanced

public class AdvancedPage extends SlingAllMethodsServlet {

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
		out.println("in serv");
		try {

			if (request.getRequestPathInfo().getExtension().equals("Demopage")) {

				out.println("Hello Do get");
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

		try {

			Node carrotrulenode = null;
			Node project_name = null;
			Node ruleenginenode = null;
			Session session = null;
			Node case1 = null;
//			Node usernamenode = null;
			Node ruleNode = null;
			// out.println("Remote User : : :" + request.getRemoteUser());

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
//			Node content = session.getRootNode().getNode("content");
		
			if (request.getRequestPathInfo().getExtension().equals("advanced")) {
				Node carrotmainNode=null;
//				if (!content.hasNode("CARROT_RULE")) {
//					carrotrulenode = content.addNode("CARROT_RULE");
//				} else {
//					carrotrulenode = content.getNode("CARROT_RULE");
//				}
				
				
				
				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				// out.print(builder.toString());
				JSONObject jsonObject = new JSONObject(builder.toString());

				// out.println(builder.toString());
				String username = jsonObject.getString("user_name").replace("@", "_");
				String project = jsonObject.getString("project_name").replace(" ", "_");
				// String date=jsonObject.getString("created_date");
				// String Project_Description=jsonObject.getString("Project_Description");
				String rule = jsonObject.getString("Rule_Engine").replace(" ", "_");
				// String rule_Description=jsonObject.getString("Rule_Engine_Description");

				// out.println("username :"+username+" project :"+project+" rule :"+rule);
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				
		
				if (!carrotmainNode.hasNode(project)) {
					project_name = carrotmainNode.addNode(project);
					// project_name.setProperty("project_Description", Project_Description);
					// project_name.setProperty("created_date", date);
					// out.println(" Project if ...");
				} else {
					project_name = carrotmainNode.getNode(project);

				}
				if (!project_name.hasNode("Rule_Engine")) {
					ruleenginenode = project_name.addNode("Rule_Engine");
					ruleenginenode.setProperty(rule, rule);
				} else {
					ruleenginenode = project_name.getNode("Rule_Engine");
				}
				if (!ruleenginenode.hasNode(rule)) {
					ruleNode = ruleenginenode.addNode(rule);
				} else {

					ruleNode = ruleenginenode.getNode(rule);
					ruleNode.setProperty("Rule_Engine_Name", rule);
				}

				session.save();
				out.println(ruleNode.getName());
			} else if (request.getRequestPathInfo().getExtension().equals("fulldetails")) {
				Node carrotmainNode=null;
				Node sfdcnodename = null;
				Node transformdatanodename = null;
//				Node ip = session.getRootNode().getNode("content").getNode("CARROT_RULE");
				JSONArray singlearray = null;

				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				JSONObject jsonObject = new JSONObject(builder.toString());
				String username = jsonObject.getString("user_name").replace("@", "_");
				
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				
				String projectname = jsonObject.getString("project_name").replace(" ", "_");
				String rule = jsonObject.getString("Rule_Engine").replace(" ", "_");

				NodeIterator iterator = carrotmainNode.getNode(projectname).getNodes();
				Node subnodes = null;
				Value[] data = null;
				JSONObject mainobject = new JSONObject();

				while (iterator.hasNext()) {

					subnodes = iterator.nextNode();

					if (subnodes.getName().equals("TRANSFORM_DATA")) {

						NodeIterator transformitr = carrotmainNode.getNode(projectname).getNode("TRANSFORM_DATA")
								.getNodes();

						JSONObject TRANSFORM_DATA_Json = new JSONObject();
						while (transformitr.hasNext()) {

							singlearray = new JSONArray();
							transformdatanodename = transformitr.nextNode();// case
							if (transformdatanodename.getName().equals("Transform")
									|| transformdatanodename.getName().equals("Raw_Data")) {
//								out.println("in transform "+transformdatanodename);
								data = transformdatanodename.getProperty(transformdatanodename.getName()).getValues();
								// JSONArray rules_values_arr = new JSONArray();
							
								for (int i = 0; i < data.length; i++) {
									String values = data[i].getString();
									singlearray.put(new JSONObject(values));
								}
//								out.println("in singlearray "+singlearray);
								TRANSFORM_DATA_Json.put(transformdatanodename.getName(), singlearray);
							}
							// TRANSFORM_DATA_JsonArray.put(TRANSFORM_DATA_Json);
							
						}
						mainobject.put("TRANSFORM_DATA", TRANSFORM_DATA_Json);
//						out.println("in mainobject "+mainobject);
					}

					mainobject.put("Rule_Engine", rule);

				}

				// mainobject.put("Rule_Engine", );
				// out.println(mainobject);
				// out.println(mainobject);
				
//			
				JSONArray jsonarray = null;
				JSONObject jsonObject12 = new JSONObject();
				try {
				JSONObject jsonObjectadd = null;
				int count = 0;
				jsonarray = new JSONArray();
				JSONObject jsondata = null;

				JSONObject transformobject = mainobject.getJSONObject("TRANSFORM_DATA");
				JSONArray transformarray = transformobject.getJSONArray("Transform");

				for (int i = 0; i < transformarray.length(); i++) {
					String sv = null;

					JSONObject object = transformarray.getJSONObject(i);
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

					JSONObject transform = mainobject.getJSONObject("TRANSFORM_DATA");
					JSONArray rawdataarray = transform.getJSONArray("Raw_Data");
					for (int k = 0; k < rawdataarray.length(); k++) {

						JSONObject rawdatajson = rawdataarray.getJSONObject(k);
						Iterator<String> ke1 = rawdatajson.keys();
						String inputname = null;
						while (ke1.hasNext()) {
							String keyname = (String) ke1.next();
							String values = rawdatajson.getString(keyname);
							jsondata = new JSONObject();
							inputname = (String) ke1.next();
							jsondata.put("no", count++);
							jsondata.put("fieldname", keyname);
							jsondata.put("value", values);
							jsonarray.put(jsondata);

						}

					}
					
				

				}
//				out.println("jsonarray: "+jsonarray);
				}catch (Exception e) {
//					 out.println("Exception 2 " + e.getMessage());
				}
				jsonObject12.put("status", "save");
				jsonObject12.put("Message", "Save Successful ");
				jsonObject12.put("Save_Data", jsonarray);
//				out.println("jsonObject12"+jsonObject12);
				out.println(mainobject);

			}

		} catch (Exception e) {
			out.println("Exception " + e.getMessage());
		}
	}

}
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
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/carrotrule1" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")


public class CarrotRuleSample extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repos;
	
	Session session = null;
	// @Reference
	// private SchedulerService product;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("carrotrulefulldetails")) {

			try {
				String username = request.getParameter("username").replace("@", "_");
				String projectname = request.getParameter("projectname").replace(" ","_");
				Session session = null;
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node ip = session.getRootNode().getNode("content").getNode("CARROT_RULE");
				NodeIterator iterator = ip.getNode(username).getNode(projectname).getNodes();
				// out.println(iterator);
				Node subnodes = null;
				Value[] data = null;
				JSONObject mainobject = new JSONObject();
				;
				JSONObject subobject = null;

				JSONArray mainarray = null;
				Node sfsubnodes = null;
				Node pg2nodes = null;
				Node pg3nodes = null;
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
					subnodes=iterator.nextNode();
					//out.println("subnodes :"+subnodes.getName());
					if(subnodes.getName().equals("SFDC_SELECTDATA")){
					NodeIterator sfdcitr = ip.getNode(username).getNode(projectname).getNode("SFDC_SELECTDATA")
							.getNodes();
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
							singlearray.put(new JSONObject(values));
						}
						SFDC_SELECTDATA_Json.put(sfdcnodename.getName(), singlearray);
					}
					SFDC_SELECTDATA_JsonArray.put(SFDC_SELECTDATA_Json);
					mainobject.put("SFDC_SELECTDATA", SFDC_SELECTDATA_JsonArray);
					// out.println(mainobject);
					}
					// External_Data
					if(subnodes.getName().equals("EXTERNAL_DATA")){

					NodeIterator externalitr = ip.getNode(username).getNode(projectname).getNode("EXTERNAL_DATA")
							.getNodes();
					JSONObject EXTERNAL_DATA_Json = new JSONObject();
					JSONArray EXTERNAL_DATA_JsonArray = new JSONArray();
					if (externalitr.hasNext()) {

						singlearray = new JSONArray();
						sfdcnodename = externalitr.nextNode();// webservices
						NodeIterator webservicesitr = ip.getNode(username).getNode(projectname).getNode("EXTERNAL_DATA")
								.getNode("WebServices").getNodes();
						if (webservicesitr.hasNext()) {

							Node webservicenodename = webservicesitr.nextNode();
							data = webservicenodename.getProperty(webservicenodename.getName()).getValues();

							for (int i = 0; i < data.length; i++) {
								String values = data[i].getString();
								singlearray.put(new JSONObject(values));
							}
							EXTERNAL_DATA_Json.put(webservicenodename.getName(), singlearray);
						}
						EXTERNAL_DATA_JsonArray.put(EXTERNAL_DATA_Json);
						mainobject.put("External_Data", EXTERNAL_DATA_JsonArray);
					}
					}
					// Transform_Data
					if(subnodes.getName().equals("TRANSFORM_DATA")){

					NodeIterator transformitr = ip.getNode(username).getNode(projectname).getNode("TRANSFORM_DATA")
							.getNodes();
					JSONObject TRANSFORM_DATA_Json = new JSONObject();
					JSONArray TRANSFORM_DATA_JsonArray = new JSONArray();
					while (transformitr.hasNext()) {

						singlearray = new JSONArray();
						transformdatanodename = transformitr.nextNode();// case
						data = transformdatanodename.getProperty(transformdatanodename.getName()).getValues();
						// JSONArray rules_values_arr = new JSONArray();

						for (int i = 0; i < data.length; i++) {
							String values = data[i].getString();
							singlearray.put(new JSONObject(values));
						}
						TRANSFORM_DATA_Json.put(transformdatanodename.getName(), singlearray);
					}
					TRANSFORM_DATA_JsonArray.put(TRANSFORM_DATA_Json);
					mainobject.put("TRANSFORM_DATA", TRANSFORM_DATA_JsonArray);
					}
					if(subnodes.getName().equals("Rule_Engine")){

					NodeIterator ruleengineitr = ip.getNode(username).getNode(projectname).getNode("Rule_Engine")
							.getNodes();
					JSONArray Rule_Engine_JsonArray = new JSONArray();
					JSONObject Rule_Engine_Json = new JSONObject();
					if (ruleengineitr.hasNext()) {
						// JSONObject Rule_Engine_Json = new JSONObject();
						// JSONArray Rule_Engine_JsonArray = new JSONArray();
						ruleenginenode = ruleengineitr.nextNode();// rulename
						NodeIterator rulenameitr = ip.getNode(username).getNode(projectname).getNode("Rule_Engine")
								.getNode(ruleenginenode.getName()).getNodes();

						while (rulenameitr.hasNext()) {

							singlearray = new JSONArray();
							JSONArray output = new JSONArray();
							Node rulenamenode = rulenameitr.nextNode();
							data = rulenamenode.getProperty("FieldData").getValues();
							for (int i = 0; i < data.length; i++) {
								String values = data[i].getString();
								// out.println("Field : "+new JSONObject(values));
								singlearray.put(new JSONObject(values));
							}
							Rule_Engine_Json.put("FieldData", singlearray);
							data = rulenamenode.getProperty("OutputField").getValues();
							for (int i = 0; i < data.length; i++) {
								String values = data[i].getString();
								// out.println("OutpUt : "+new JSONObject(values));
								output.put(new JSONObject(values));
							}
							Rule_Engine_Json.put("OutputField", output);

						}
						Rule_Engine_JsonArray.put(Rule_Engine_Json);
						mainobject.put("Rule_Engine", Rule_Engine_JsonArray);
					}
					}
				}

				out.println(mainobject);
			}

			catch (Exception ex) {

				out.println("Exception ex : " + ex.getMessage() + ex.getCause());
			}
		}
		
		else if (request.getRequestPathInfo().getExtension().equals("editing")) {
			try {
				String username = request.getParameter("username").replace("@", "_");
				// out.println(username);
				Session session = null;
				Node projectname = null;
				Node ruleenginenode = null;
				JSONObject subobject = null;
				JSONArray subarray = null;
				JSONObject mainobject = new JSONObject();
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));
				NodeIterator ip = session.getRootNode().getNode("content").getNode("CARROT_RULE").getNode(username)
						.getNodes();
				subarray = new JSONArray();

				while (ip.hasNext()) {
					subobject = new JSONObject();
					projectname = ip.nextNode();
					Node dateproperty = session.getRootNode().getNode("content").getNode("CARROT_RULE")
							.getNode(username).getNode(projectname.getName());
					subobject.put("Project Name", projectname.getName());
					if(dateproperty.hasProperty("created_date")) {
						//out.println("If");
					String date = dateproperty.getProperty("created_date").getString();
					subobject.put("Created Date", date);
					}
					NodeIterator ruleengineitr = session.getRootNode().getNode("content").getNode("CARROT_RULE")
							.getNode(username).getNode(projectname.getName()).getNode("Rule_Engine").getNodes();
					while (ruleengineitr.hasNext()) {
						ruleenginenode = ruleengineitr.nextNode();
						subobject.put("Rule Engine", ruleenginenode.getName());
						subarray.put(subobject);
					}
					mainobject.put("Data", subarray);

				}
				out.println(mainobject);
			} catch (Exception ex) {

				out.println("Exception ex: " + ex.getMessage());
			}
		}
		
	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("email")) {

			try {
				Session session = null;
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));

				Node content = session.getRootNode().getNode("content");
			}

			catch (Exception e) {
				out.println("Exception : : :" + e.getMessage());
			}
		} else if (request.getRequestPathInfo().getExtension().equals("binaryread")) {

			PrintWriter o = response.getWriter();
			o.println("Post : ");
			String line = null;
			String fileName = request.getParameter("fileupload");
			try {
				// FileReader reads text files in the default encoding.
				FileReader fileReader = new FileReader(fileName);

				// Always wrap FileReader in BufferedReader.
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				while ((line = bufferedReader.readLine()) != null) {
					o.println(line);
				}

				// Always close files.
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				o.println("Unable to open file '" + fileName + "'");
			}

		} 
	}

	

}
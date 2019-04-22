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

import com.service.FreeTrial12;
import com.sun.jndi.toolkit.url.UrlUtil;

import javafx.css.SimpleStyleableIntegerProperty;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/Create_New_page" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class Create_New_page extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;

	FreeTrial12 fr = new FreeTrial12();
//http://35.236.154.164:8082/portal/servlet/service/Create_New_page.createNode
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("hello");
		try {

			if (request.getRequestPathInfo().getExtension().equals("Demopage")) {

				out.println("Hello Do get");
				request.getRequestDispatcher("/content/static/.brokerage").forward(request, response);

			}

		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
//		http://35.236.154.164:8082/portal/servlet/service/Create_New_page.createNode
		PrintWriter out = response.getWriter();
//				Node brokerage=null;
		Node project_name = null;
		Node sf_object = null;
		Session session = null;
		Node case1 = null;
//				Node ruls_node=null;
		Node ruleNode = null;
		Node carrotmainNode = null;
		try {

			// out.println("Remote User : : :" + request.getRemoteUser());

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

//					 Node content = session.getRootNode().getNode("content");

			if (request.getRequestPathInfo().getExtension().equals("createNode")) {
//						 if (!content.hasNode("CARROT_RULE")) {
//								brokerage = content.addNode("CARROT_RULE");
//							} else {
//								brokerage = content.getNode("CARROT_RULE");
//							}

				StringBuilder builder = new StringBuilder();

				/*
				 * // String stringurl=
				 * "http://35.201.178.201:8082/portal/servlet/service/Create_newTest_json.test_new";
				 * 
				 * String stringurl=
				 * "http://35.186.166.22:8082/portal/servlet/service/Create_newTest_json.test_new";
				 * URL url=new URL(stringurl);
				 * 
				 * out.println(url); HttpURLConnection connection = (HttpURLConnection)
				 * url.openConnection(); connection.setDoOutput(true);
				 * connection.setRequestMethod("POST"); connection.connect(); BufferedReader
				 * bufferedReaderCampaign = new BufferedReader(new
				 * InputStreamReader(connection.getInputStream()));
				 */
				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
//					       out.print(builder.toString());
				JSONObject jsonObject = new JSONObject(builder.toString());

				// out.println(builder.toString());
				String username = jsonObject.getString("user_name").replace("@", "_");
				// Get service node
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//						out.println("hello fr.checkfreetrial = "+freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//						out.println("hello fr.getCarrotruleNode = "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");// dd/MM/yyyy
				Date date1 = new Date();
				System.out.println(dateFormat.format(date1)); 
				if (carrotmainNode != null) {
					String project = jsonObject.getString("project_name").replace(" ", "_");
					
					String date = dateFormat.format(date1);
					String Project_Description = jsonObject.getString("Project_Description");
					String rule = jsonObject.getString("Rule_Engine").replace(" ", "_");
					String rule_Description = jsonObject.getString("Rule_Engine_Description");

//					       out.println("username  :"+username+"  project  :"+project+"  rule :"+rule);

//					       if(!brokerage.hasNode(username)){
//					    	   ruls_node =brokerage.addNode(username);
////					    	   ruls_node.setProperty("Rule_name", rule);
//					       }else {
//							ruls_node=brokerage.getNode(username);
////					       ruls_node.setProperty("Rule_name", rule);
//					       }
					if (!carrotmainNode.hasNode(project)) {
						project_name = carrotmainNode.addNode(project);

						project_name.setProperty("project_Description", Project_Description);
						project_name.setProperty("created_date", date);
//					    	   out.println(" Project if ...");
					} else {
						project_name = carrotmainNode.getNode(project);
						project_name.setProperty("project_Description", Project_Description);
						project_name.setProperty("created_date", date);
//					    	   out.println(" poject  eles...");
					}
					if (!project_name.hasNode("Rule_Engine")) {
						sf_object = project_name.addNode("Rule_Engine");
						sf_object.setProperty(rule, rule);
						sf_object.setProperty("Rule_Description", rule_Description);
////					    	   out.println(" Add_External_Parameter  if ...");
					} else {
						sf_object = project_name.getNode("Rule_Engine");
						sf_object.setProperty(rule, rule);
						sf_object.setProperty("Rule_Description", rule_Description);
////					    	   out.println(" Add_External_Parameter  eles...");
					}
					if (!sf_object.hasNode(rule)) {
						ruleNode = sf_object.addNode(rule);
//					    	   ruleNode.setProperty("Rule_Engine_Name", rule);
						sf_object.setProperty("Rule_Description", rule_Description);
					} else {

						ruleNode = sf_object.getNode(rule);
						ruleNode.setProperty("Rule_Engine_Name", rule);
//					    	   sf_object.setProperty("Rule_Description", rule_Description);

					}
					session.save();
					out.println("SUCCESSFULL");
				} else {
					out.println("No user Exist");
				}
			}

		} catch (Exception e) {
			out.println("Excepation Create New =" + e.getMessage());
		}
	}
}
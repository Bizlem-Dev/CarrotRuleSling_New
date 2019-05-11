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
 * The Class SFDC_Select_Data.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/SFDC_Select_Data" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class SFDC_Select_Data extends SlingAllMethodsServlet {

	/** The repo. */
	@Reference
	private SlingRepository repo;
	
	/** The fr. */
	FreeTrial12 fr = new FreeTrial12();
	
	/** The session. */
	Session session = null;

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override

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

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
//					Node brokerage=null;
		Node project_name = null;
		Node sf_object = null;
		Session session = null;
		Node case1 = null;
//					Node ruls_node=null;
		Node primary = null;
		Node ulenod = null;
		Node carrotmainNode = null;
		JSONArray array = null;
		JSONObject jsonall = null;
		JSONObject objectjson = new JSONObject();
		JSONObject allobject = new JSONObject();

		try {

			// out.println("Remote User : : :" + request.getRemoteUser());

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

			StringBuilder builder = new StringBuilder();

			BufferedReader bufferedReaderCampaign = request.getReader();
			String brokerageline;
			while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
				builder.append(brokerageline + "\n");
			}
//						       out.println(builder.toString());
			JSONObject jsonObject = new JSONObject(builder.toString());

			String username = jsonObject.getString("user_name").replace("@", "_");
			try {
				String freetrialstatus = fr.checkfreetrial(username);
//				out.println("hello fr.checkfreetrial = " + freetrialstatus);
				carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//									out.println("hello fr.getCarrotruleNode = "+carrotmainNode);

			} catch (Exception e) {
				// TODO: handle exception

			}

			if (carrotmainNode != null) {

				String project = jsonObject.getString("project_name").replace(" ", "_");
				String primarykey = jsonObject.getString("primary_key").replace(":", "_");

				objectjson.put("user_name", username);
				objectjson.put("project_name", project);
				objectjson.put("primary_key", primary);

				if (carrotmainNode.hasNode(project)) {
					project_name = carrotmainNode.getNode(project);
					out.println("if crt = " + carrotmainNode);
				} else {
					project_name = carrotmainNode.addNode(project);
					out.println("else crt = " + carrotmainNode);
				}

				if (!project_name.hasNode("SFDC_SELECTDATA")) {
					sf_object = project_name.addNode("SFDC_SELECTDATA");
					sf_object.setProperty("primary_name", primarykey);
				} else {
					sf_object = project_name.getNode("SFDC_SELECTDATA");
					sf_object.setProperty("primary_name", primarykey);
				}

				JSONObject object2 = jsonObject.getJSONObject("SFDC_SelectData");

//						       JSONObject jsonObject2=getjsonmathodes(object2,response);
				jsonall = new JSONObject();
				String key;
				Iterator<String> keys = object2.keys();
				while (keys.hasNext()) {

					key = (String) keys.next();
//		                           out.println("key   :"+key);	
					JSONArray jsonArray = (JSONArray) object2.get(key);
//		                           out.println("jsonArray   :"  + jsonArray);
					String sg[] = new String[jsonArray.length()];

					for (int i = 0; i < jsonArray.length(); i++) {
						array = new JSONArray();

						String values = jsonArray.getString(i);
//	                                    out.println("value  :"+values);
						array.put(values);
						jsonall.put(key, array);
						sg[i] = jsonArray.getString(i);

					}
					if (!sf_object.hasNode(key)) {
						case1 = sf_object.addNode(key);
						case1.setProperty(key, sg);
					} else {
						case1 = sf_object.getNode(key);
						case1.setProperty(key, sg);
					}
				}

				objectjson.put("SFDC_SELECTDATA", jsonall);
				allobject.put("Status", "Saved Successfully");
				allobject.put("Message", objectjson);

				session.save();

//									 out.println(allobject);
				out.println("Successfull");
			} else {
				out.println("No User exist");
			}

		} catch (Exception e) {
			out.println("Excepation SFDC Select data =" + e.getMessage());
		}
	}

}

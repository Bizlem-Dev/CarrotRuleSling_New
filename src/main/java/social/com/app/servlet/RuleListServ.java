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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/RuleengineList" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

// http://35.236.154.164:8082/portal/servlet/service/testserv
public class RuleListServ extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	FreeTrial12 fr = new FreeTrial12();
	Session session = null;
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
//		Node usernamenode = null;
		Node projectnamenode = null;
		Node carrotmainNode=null;
		try {

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
//  ruleenginelist.RuleEngineList?user_name=carrotrule444@gmail.com&projectname=
			if (request.getRequestPathInfo().getExtension().equals("ruleenginelist")) {

				//StringBuilder builder = new StringBuilder();
				JSONObject ruleenginejson = new JSONObject();
				JSONArray ruleenginearray = null;
				StringBuilder builder = new StringBuilder();
				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}

				JSONObject jsonObject = new JSONObject(builder.toString());
				String username = jsonObject.getString("user_name").replace("@", "_");
				String projectname = jsonObject.getString("projectname").replace("@", "_");
//				Node content = session.getRootNode().getNode("content").getNode("CARROT_RULE");
				
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//				out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//									out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				
				if (carrotmainNode !=null) {
//					out.println("carrotmainNode= "+carrotmainNode);
//					usernamenode = content.getNode(username);

					if (carrotmainNode.hasNode(projectname)) {
						ruleenginearray = new JSONArray();
						projectnamenode = carrotmainNode.getNode(projectname);
						NodeIterator itr = carrotmainNode.getNode(projectname).getNode("Rule_Engine")
								.getNodes();
						while (itr.hasNext()) {
							Node itrnode = itr.nextNode();
							String name = itrnode.getName();
							ruleenginearray.put(name);
			                ruleenginejson.put("Rule_Engine", ruleenginearray);
//			            	out.println(ruleenginejson);
						}
						out.println(ruleenginejson);
					}else {
//						out.println(projectname);	
					}
				}

			} else if (request.getRequestPathInfo().getExtension().equals("selectedruleengine")) {
				StringBuilder builder = new StringBuilder();
				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				
				// out.print(builder.toString());
				JSONObject jsonObject = new JSONObject(builder.toString());
				String username = jsonObject.getString("user_name").replace("@", "_");
				String projectname = jsonObject.getString("projectname").replace("@", "_");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//				out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//									out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				if (carrotmainNode !=null) {
				Node content = carrotmainNode
						.getNode(projectname).getNode("Rule_Engine");
				Value[] va = content.getProperty("Current_Rule_Engine").getValues();
				for (int i = 0; i < va.length; i++) {

					String s = va[i].getString();
					out.println("Rule Engine Values : " + new JSONObject(s).getString("RuleEngine"));
				}
				}
			}
			// session.save();

		} catch (Exception e) {
			out.println("Exception ex :" + e.getMessage());
		}
		
		
		
		
	}
	

}
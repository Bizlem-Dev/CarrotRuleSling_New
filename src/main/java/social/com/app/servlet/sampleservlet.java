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

// TODO: Auto-generated Javadoc
/**
 * The Class sampleservlet.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/sample_123" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class sampleservlet extends SlingAllMethodsServlet {

	/** The repos. */
	@Reference
	private SlingRepository repos;

	// @Reference
	// private SchedulerService product;

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("carrotrulefulldetails")) {

		}

	}

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		if (request.getRequestPathInfo().getExtension().equals("firstservlet")) {

			try {
				Session session = null;
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));
				Node usernamenode = null;
				Node carrotsecondnode = null;
				Node carrotthirdnode = null;
				Node carrotfourthnode = null;
				String username = "Myfirstproject";
				Node carrotfirstnode = null;
				// Node content = session.getRootNode().getNode("content");

				Node ip = session.getRootNode().getNode("content");
				if (!ip.hasNode("Carrot_rule")) {
					usernamenode = ip.addNode("Carrot_rule");
				} else {
					usernamenode = ip.getNode("Carrot_rule");
					out.println(" username else ..." + usernamenode);
				}
				usernamenode.setProperty("Student", "Amey");
				usernamenode.setProperty("Developer", "Ajay");
				if (!usernamenode.hasNode("Carrot_rulefirst")) {
					carrotfirstnode = usernamenode.addNode("Carrot_rulefirst");
				} else {
					carrotfirstnode = usernamenode.getNode("Carrot_rulefirst");
					out.println(" username else ..." + carrotfirstnode);
				}
				carrotfirstnode.setProperty("Engineer", "Ravi");
				carrotfirstnode.setProperty("Extc", "Apoorva");

				if (!carrotfirstnode.hasNode("Carrot_rulesecond")) {
					carrotsecondnode = carrotfirstnode.addNode("Carrot_rulesecond");
					out.println("CarrotRule Second Node If :");
				} else {
					carrotsecondnode = carrotfirstnode.getNode("Carrot_rulesecond");
					out.println("Else : ");
				}
				carrotsecondnode.setProperty("IT", "Raghu");
				carrotsecondnode.setProperty("Civil", "Shivam");

				if (!carrotsecondnode.hasNode("Carrot_rulethird")) {
					carrotthirdnode = carrotsecondnode.addNode("Carrot_rulethird");
					out.println("Third node");
				} else {
					carrotthirdnode = carrotsecondnode.getNode("Carrot_rulethird");
				}
				carrotthirdnode.setProperty("BE", "Ajeet");

				if (!carrotthirdnode.hasNode("Carrot_rulefour")) {
					carrotfourthnode = carrotthirdnode.addNode("Carrot_rulefour");
				} else {
					carrotfourthnode = carrotthirdnode.getNode("Carrot_rulefour");
					out.println(" username else ..." + carrotfourthnode);
				}
				carrotfourthnode.setProperty("BCom", "Raju");
				carrotfourthnode.setProperty("BMM", "Wadekar");
				out.println("Node Added");

				session.save();

			}

			catch (Exception e) {
				out.println("Exception : : :" + e.getMessage());
			}
		}
	}
}
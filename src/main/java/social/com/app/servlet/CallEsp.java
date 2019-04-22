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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/CallCarrotRule" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

// http://35.236.154.164:8082/portal/servlet/service/CallCarrotRule.CarrotRule
// CallEsptj.setUp
public class CallEsp extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repos;

	Session session = null;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println("hello");
		try {
			if (request.getRequestPathInfo().getExtension().equals("index")) {

				try {
					RequestDispatcher dis = request.getRequestDispatcher("/content/static/.index");
					dis.forward(request, response);

				} catch (Exception e) {

					out.println(e.getMessage().toString());
				}
			}
			if (request.getRequestPathInfo().getExtension().equals("CarrotRule")) {

				try {
					RequestDispatcher dis = request.getRequestDispatcher("/content/static/.CarrotRule");
					dis.forward(request, response);

				} catch (Exception e) {

					out.println(e.getMessage().toString());
				}
			}
			if (request.getRequestPathInfo().getExtension().equals("Setup")) {

				try {
					RequestDispatcher dis = request.getRequestDispatcher("/content/static/.setUp");
					dis.forward(request, response);

				} catch (Exception e) {

					out.println(e.getMessage().toString());
				}
			}
//			CallCarrotRule.SetUp2
			if (request.getRequestPathInfo().getExtension().equals("SetUp2")) {

				try {
					RequestDispatcher dis = request.getRequestDispatcher("/content/static/.CarrotSetup");
					dis.forward(request, response);

				} catch (Exception e) {

					out.println(e.getMessage().toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
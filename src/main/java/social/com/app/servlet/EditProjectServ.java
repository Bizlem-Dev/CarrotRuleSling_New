package social.com.app.servlet;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import java.io.*;
import com.service.FreeTrial12;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
	@Property(name = "service.vendor", value = "VISL Company"),
	@Property(name = "sling.servlet.paths", value = { "/servlet/service/EditProject_new" }),
	@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
	@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
			"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
	"productEdit" }) })
@SuppressWarnings("serial")

public class EditProjectServ extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	FreeTrial12 fr = new FreeTrial12();
	Session session = null;

	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		Node carrotmainNode = null;
		try {
			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

			Node ruleenginenode = null;
			Node projectnameNode = null;

			JSONObject mainobject = new JSONObject();

			NodeIterator ruleengineitr = null;

			String username = request.getParameter("username").replace("@", "_");
			String freetrialstatus = fr.checkfreetrial(username);
			String projectName= request.getParameter("prjName");
			
			//out.println("username: "+username+" freetrialStatus: "+freetrialstatus+" projectName: "+projectName);
			carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);

			String project_Desc="";
			String ruleEnginName= "";
			String rule_Desc="";

			if (carrotmainNode != null) {
				Node usernamecheck = session.getRootNode().getNode("content").getNode("services").getNode("freetrial").getNode("users");
				if (usernamecheck.hasNode(username)) {
					projectnameNode = usernamecheck.getNode(username).getNode("CarrotruleMainNode").getNode(projectName);

					project_Desc= projectnameNode.getProperty("project_Description").getString();

					if(projectnameNode.hasNode("Rule_Engine")) {
						ruleengineitr = projectnameNode.getNode("Rule_Engine").getNodes();
						while (ruleengineitr.hasNext()) {
							ruleenginenode = ruleengineitr.nextNode();
							ruleEnginName= ruleenginenode.getName();

							if(ruleenginenode.hasProperty("Rule_Description")) {
								rule_Desc= ruleenginenode.getProperty("Rule_Description").getString();
							}else {
								rule_Desc="";
							}							
						}
					}else {

					}

					mainobject.put("prjName", projectName);
					mainobject.put("prj_Desc", project_Desc);
					mainobject.put("ruleName", ruleEnginName);
					mainobject.put("rule_Desc", rule_Desc);
				}
				out.println(mainobject);
			} else {
				out.println("No user exist");
			}

		} catch (Exception e) {
			out.println("Exception ex :" + e.getMessage());
		}
	}

}
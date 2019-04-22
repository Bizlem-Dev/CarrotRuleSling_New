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

import com.ruleengineservlet.CrRuleConstValue;
import com.service.FreeTrial12;

@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/carrotrule" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

public class Showhomepagedetails extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repos;
	FreeTrial12 fr = new FreeTrial12();
//  carrotrule.carrotrulefulldetails
	// @Reference
	// private SchedulerService product;
//http://35.236.154.164:8082/portal/servlet/service/carrotrule.carrotrulefulldetails?username=carrotrule444_gmail.com&projectname=Bizlem_project &ruleenginename=Bizlem_rule
	// http://35.236.154.164:8082/portal/servlet/service/carrotrule.editing?username=carrotrule444_gmail.com
	@Override
	
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//		out.println("hello");
		Node carrotmainNode = null;
		Session session = null;
		if (request.getRequestPathInfo().getExtension().equals("carrotrulefulldetails")) {
			try {
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));
				String username = request.getParameter("username").replace("@", "_");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
					//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
					//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);
				} catch (Exception e) {
				}
				if (carrotmainNode != null) {
					String projectname = request.getParameter("projectname").replace(" ", "_");
					String rulename = request.getParameter("ruleenginename").replace(" ", "_");
					//				Node ip = session.getRootNode().getNode("content").getNode("CARROT_RULE");
					NodeIterator iterator = carrotmainNode.getNode(projectname).getNodes();
					// out.println(iterator);
					Node subnodes = null;
					Value[] data = null;
					JSONObject mainobject = new JSONObject();

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
						try {
							subnodes = iterator.nextNode();
							// out.println(subnodes);
							if (subnodes.getName().equals("SFDC_SELECTDATA")) {
								NodeIterator sfdcitr = carrotmainNode.getNode(projectname).getNode("SFDC_SELECTDATA")
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
							if (subnodes.getName().equals("EXTERNAL_DATA")) {

								NodeIterator externalitr = carrotmainNode.getNode(projectname).getNode("EXTERNAL_DATA")
										.getNodes();
								JSONObject EXTERNAL_DATA_Json = new JSONObject();
								JSONArray EXTERNAL_DATA_JsonArray = new JSONArray();
								if (externalitr.hasNext()) {

									singlearray = new JSONArray();
									sfdcnodename = externalitr.nextNode();// webservices
									NodeIterator webservicesitr = carrotmainNode.getNode(projectname)
											.getNode("EXTERNAL_DATA").getNode("WebServices").getNodes();
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
							if (subnodes.getName().equals("TRANSFORM_DATA")) {

								Node transformitr = carrotmainNode.getNode(projectname).getNode("TRANSFORM_DATA")
										.getNode("Transform");
								JSONObject TRANSFORM_DATA_Json = new JSONObject();
								JSONArray TRANSFORM_DATA_JsonArray = new JSONArray();
								// while (transformitr.hasNext()) {

								singlearray = new JSONArray();
								data = transformitr.getProperty("Transform").getValues();
								// JSONArray rules_values_arr = new JSONArray();

								for (int i = 0; i < data.length; i++) {
									String values = data[i].getString();
									singlearray.put(new JSONObject(values));
								}
								TRANSFORM_DATA_Json.put("Transform", singlearray);
								// }
								TRANSFORM_DATA_JsonArray.put(TRANSFORM_DATA_Json);
								mainobject.put("TRANSFORM_DATA", TRANSFORM_DATA_JsonArray);
							}
							if (subnodes.getName().equals("Rule_Engine")) {

								NodeIterator ruleengineitr = carrotmainNode.getNode(projectname).getNode("Rule_Engine")
										.getNodes();
								JSONArray Rule_Engine_JsonArray = new JSONArray();
								JSONObject Rule_Engine_Json = null;
								if (ruleengineitr.hasNext()) {
									// JSONObject Rule_Engine_Json = new JSONObject();
									// JSONArray Rule_Engine_JsonArray = new JSONArray();
									ruleenginenode = ruleengineitr.nextNode();// rulename
									NodeIterator rulenameitr = carrotmainNode.getNode(projectname)
											.getNode("Rule_Engine").getNode(rulename).getNodes();

									while (rulenameitr.hasNext()) {
										Rule_Engine_Json = new JSONObject();
										singlearray = new JSONArray();
										JSONArray output = new JSONArray();
										JSONArray calculationbuilderarray = new JSONArray();
										Node rulenamenode = rulenameitr.nextNode();// rule
										Rule_Engine_Json.put("Rule_Name", rulenamenode.getName());
										if (rulenamenode.hasProperty("FieldData")
												&& rulenamenode.hasProperty("OutputField")) {
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

											if (rulenamenode.hasProperty("CalculationBuilder")) {

												data = rulenamenode.getProperty("CalculationBuilder").getValues();

												for (int i = 0; i < data.length; i++) {
													String values = data[i].getString();
													// out.println("OutpUt : "+new JSONObject(values));
													calculationbuilderarray.put(new JSONObject(values));
												}
												Rule_Engine_Json.put("CalculationBuilder", calculationbuilderarray);

											}
											Rule_Engine_JsonArray.put(Rule_Engine_Json);

										}
									}
									mainobject.put("Rule_Engine", Rule_Engine_JsonArray);
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					out.println(mainobject);
				} else {
					out.println("no user Exist");
				}
			}

			catch (Exception ex) {

				out.println("Exception ex : " + ex.getMessage());
			}
		}

		else if (request.getRequestPathInfo().getExtension().equals("editing")) {
			try {

				Node ruleenginenode = null;
				JSONObject subobject = null;
				JSONArray subarray = null;
				JSONObject mainobject = new JSONObject();
				//					Session session = null;
				NodeIterator ip = null;
				Node dateproperty = null;
				NodeIterator ruleengineitr = null;
				JSONObject ruleEngenJson = null;
				JSONArray ruleEnginaArray = new JSONArray();
				Node projectname = null;
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));
				// out.println("Remote User : : : :"+request.getRemoteUser());
				String username = request.getParameter("username").replace("@", "_");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
					//						out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
					//											out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);
					//node /content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode
				} catch (Exception e) {
					// TODO: handle exception

				}
				if (carrotmainNode != null) {
					//					Node usernamecheck=session.getRootNode().getNode("content").getNode("CARROT_RULE");
					Node usernamecheck = session.getRootNode().getNode("content").getNode("services")
							.getNode("freetrial").getNode("users");
					if (usernamecheck.hasNode(username)) {
						ip = usernamecheck.getNode(username).getNode("CarrotruleMainNode").getNodes();
						subarray = new JSONArray();

						String date = null;

						while (ip.hasNext()) {
							projectname = ip.nextNode();

							subobject = new JSONObject();
							dateproperty = usernamecheck.getNode(username).getNode("CarrotruleMainNode")
									.getNode(projectname.getName());
							if (!dateproperty.hasProperty("Status")) {
								subobject.put("Project Name", projectname.getName());

								//					out.println("projectname.getName()   :"+projectname.getName());

								if(carrotmainNode.getNode(projectname.getName()).hasNode("Rule_Engine")) {
								ruleengineitr = carrotmainNode.getNode(projectname.getName()).getNode("Rule_Engine")
										.getNodes();
								while (ruleengineitr.hasNext()) {
									ruleEngenJson = new JSONObject();
									ruleenginenode = ruleengineitr.nextNode();

									String projectnameengine = subobject.getString("Project Name");
									if (dateproperty.hasProperty("created_date")) {
										// out.println("Yes");
										date = dateproperty.getProperty("created_date").getString();
										subobject.put("Created Date", date);
										String dateengine = subobject.getString("Created Date");
										ruleEngenJson.put("Created_Date", dateengine);

										// out.println(date);
									}

									ruleEngenJson.put("Project_Name", projectnameengine);

									ruleEngenJson.put("Rule_Engine", ruleenginenode.getName());
									ruleEnginaArray.put(ruleEngenJson);

								}
								}else {
											ruleengineitr= null;
										}
								
								mainobject.put("Data", ruleEnginaArray);
							}

						}
					}
					out.println(mainobject);
				} else {
					out.println("No user exist");
				}
			} catch (Exception ex) {

				out.println("Exception ex: " + ex.getMessage());
			}
		} else if (request.getRequestPathInfo().getExtension().equals("doctiger")) {
			try {
				String username = request.getParameter("username").replace("@", "_");
				session = repos.login(new SimpleCredentials("admin", "admin".toCharArray()));

				try {
					String freetrialstatus = fr.checkfreetrial(username);
					//					out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
					//										out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);
					//node /content/services/freetrial/users/carrotrule444_gmail.com/CarrotruleMainNode
				} catch (Exception e) {
					// TODO: handle exception

				}
				// out.println(username);

				Node projectname = null;
				Node ruleenginenode = null;
				JSONObject subobject = null;
				JSONArray subarray = null;
				Node ruleNamenode = null;
				String url = null;
				JSONArray jsonArray = new JSONArray();
				JSONObject mainobject = new JSONObject();
				JSONArray arrayoutput = null;
				JSONArray pro_ruleE_date = new JSONArray();

				JSONObject datajson = new JSONObject();
				JSONArray singlearray = null;
				JSONObject doctiger = new JSONObject();

				JSONArray arrayinput_out = null;
				JSONObject jsonUrl = null;
				JSONArray output = null;
				JSONArray inputjson = null;
				NodeIterator ip = carrotmainNode.getNodes();
				JSONObject subvaluejson = null;
				subarray = new JSONArray();
				arrayoutput = new JSONArray();
				try {
					while (ip.hasNext()) {
						try {
							subobject = new JSONObject();
							subvaluejson = new JSONObject();
							// datajson=new JSONObject();
							projectname = ip.nextNode();
							Node dateproperty = carrotmainNode.getNode(projectname.getName());

							subvaluejson.put("Project Name", projectname.getName());
							String proj_name = projectname.getName();
							if (dateproperty.hasProperty("created_date")) {
								// out.println("If");
								String date = dateproperty.getProperty("created_date").getString();
								subvaluejson.put("Created_Date", date);
							}

							NodeIterator ruleengineitr = carrotmainNode.getNode(projectname.getName())
									.getNode("Rule_Engine").getNodes();
							while (ruleengineitr.hasNext()) {

								arrayinput_out = new JSONArray();
								ruleenginenode = ruleengineitr.nextNode();

								String rulenameget = ruleenginenode.getName();
								subvaluejson.put("Rule Engine", ruleenginenode.getName());
								// pro_ruleE_date.put(subvaluejson);
								NodeIterator iteratorrules = carrotmainNode.getNode(projectname.getName())
										.getNode("Rule_Engine").getNode(rulenameget).getNodes();

								Value[] data = null;
								Value[] datafield = null;
								JSONArray Rule_Engine_JsonArray = new JSONArray();
								JSONObject Rule_Engine_Json = new JSONObject();
								while (iteratorrules.hasNext()) {

									singlearray = new JSONArray();
									output = new JSONArray();
									jsonUrl = new JSONObject();
									inputjson = new JSONArray();
									ruleNamenode = iteratorrules.nextNode();
									url = CrRuleConstValue.StringConstant.DRL_URL.value() + username.replace("_", "@")
									+ "_" + proj_name + "_" + rulenameget + "/fire";

									Rule_Engine_Json.put("URL", url);
									if (ruleNamenode.hasProperty("FieldData")) {
										datafield = ruleNamenode.getProperty("FieldData").getValues();
										JSONObject jsongetvalue = null;
										for (int i = 0; i < datafield.length; i++) {
											jsongetvalue = new JSONObject();
											String values = datafield[i].getString();
											// out.println("Field : "+new JSONObject(values));
											singlearray.put(new JSONObject(values));

											JSONObject jsonField = new JSONObject(values);

											String field = jsonField.getString("field");
											if (field.equals("id")) {
											} else {
												String type = jsonField.getString("type");

												jsongetvalue.put("field", field);
												jsongetvalue.put("type", type);

												inputjson.put(jsongetvalue);
											}
										}
										Rule_Engine_Json.put("Input", inputjson);
									}
									// Rule_Engine_Json.put("Input", singlearray);

									// Rule_Engine_Json.put("Input", inputjson);

									// Rule_Engine_JsonArray.put(Rule_Engine_Json);
									if (ruleNamenode.hasProperty("FieldData")) {
										data = ruleNamenode.getProperty("OutputField").getValues();
										JSONObject outputjson = null;
										for (int i = 0; i < data.length; i++) {

											outputjson = new JSONObject();
											String values = data[i].getString();
											JSONObject jsonoutput = new JSONObject(values);

											String field = jsonoutput.getString("field");

											String value = jsonoutput.getString("value");
											outputjson.put("field", field);

											output.put(outputjson);
										}
										Rule_Engine_Json.put("Output", output);
										// Rule_Engine_JsonArray.put(Rule_Engine_Json);

									}

								}

								subvaluejson.put("DocTiger", Rule_Engine_Json);
							}
							pro_ruleE_date.put(subvaluejson);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				mainobject.put("Data", pro_ruleE_date);
				out.println(mainobject);

			} catch (Exception ex) {

				out.println("Exception ex: " + ex.getMessage());
			}

		} else {
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
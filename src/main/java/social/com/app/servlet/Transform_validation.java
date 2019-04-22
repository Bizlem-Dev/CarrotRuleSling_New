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
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/Transform_validation" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

//          http://35.236.154.164:8082/portal/servlet/service/Transform_validation.form
public class Transform_validation extends SlingAllMethodsServlet {

	@Reference
	private SlingRepository repo;
	FreeTrial12 fr = new FreeTrial12();

	@Override
//
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("hello");
		try {
			if (request.getRequestPathInfo().getExtension().equals("Demopage")) {
				request.getRequestDispatcher("/content/static/.fistpage").forward(request, response);
			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}
	}

	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int jsonvalue = 0;
		Session session = null;
		Node rulena = null;
//		Node brokerage = null;
//		Node ruls_node = null;
		Node project_name = null;
		Node transform = null;
		Node keyandvalues = null;
		Node transformfilenode = null;
		String filename = null;
		String filedata = null;
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> keysarray = new ArrayList<String>();
		ArrayList<String> valuesarray = new ArrayList<String>();
		JSONArray dataarra = new JSONArray();
		String username = null;
		String project = null;
		JSONObject jsonvalueobject = null;
		JSONObject boolean1 = null;
		JSONArray array = new JSONArray();
		JSONObject sfdcfile = new JSONObject();
		JSONArray array2 = new JSONArray();
		JSONArray sava_data = new JSONArray();
		// out.println("Column_Index : " + cell.getColumnIndex());
		String keysv = null;
		String valued = null;
		String keysvalue = null;
		String valuedata = null;
		try {

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
//			Node content = session.getRootNode().getNode("content");
			// out.println("Remote User : : :" + request.getRemoteUser());
			Node carrotmainNode = null;
			if (request.getRequestPathInfo().getExtension().equals("form")) {
				JSONArray jsonarray = null;

				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				JSONObject jsonObject = new JSONObject(builder.toString());

				username = jsonObject.getString("user_name").replace("@", "_");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//						out.println("hello fr.checkfreetrial = "+freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//						out.println("hello fr.getCarrotruleNode = "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				if (carrotmainNode != null) {
					project = jsonObject.getString("project_name").replace(" ", "_");

					if (!carrotmainNode.hasNode(project)) {
						project_name = carrotmainNode.addNode(project);
						// project_name.setProperty("Rule_name", rule);
						// out.println(" Project if ...");
					} else {
						project_name = carrotmainNode.getNode(project);
						// project_name.setProperty("Rule_name", rule);
						// out.println(" project eles...");
					}

					if (!project_name.hasNode("TRANSFORM_DATA")) {
						transform = project_name.addNode("TRANSFORM_DATA");
					} else {
						// transform = project_name.getNode("TRANSFORM_DATA");
						project_name.getNode("TRANSFORM_DATA").remove();
						transform = project_name.addNode("TRANSFORM_DATA");
					}
					JSONObject object2 = null;
					if (jsonObject.has("TRANSFORM")) {
					
						object2 = jsonObject.getJSONObject("TRANSFORM");
					} else {
				
					}
//				if (!jsonObject.getString("Transform_File_Data").equals("Transform_File_Data")
//						&& jsonObject.getString("Transform_File_Data").length() == 2
//						&& object2.getJSONArray("Transform").length() > 0) {

					try {

						if (!jsonObject.has("Transform_File_Data") && object2.getJSONArray("Transform").length() > 0) {

							JSONArray transformjson = object2.getJSONArray("Transform");
//					 out.println("transformjson= "+transformjson);
							// out.println("list of data :"+jsonvalueobject);
							try {
								boolean1 = getkeyvalue(transformjson, response);// call mathod

//					out.println("list of data in serv boolean1 :"+boolean1);
							} catch (Exception e) {
								// TODO: handle exception
							}
							try {
								jsonvalueobject = getkeyjson(transformjson, jsonObject, response, username, project);// call
																														// mathod
//					 out.println("list of data in serv :"+jsonvalueobject);
							} catch (Exception e) {
								// TODO: handle exception
							}
							// jsonObject, response, username, project)

							String key;
							Iterator<String> keys1 = object2.keys();
							while (keys1.hasNext()) {
								key = (String) keys1.next();

								// out.println("key url :" + key);
								JSONArray objectjson = boolean1.getJSONArray("Error_Data");
								jsonvalue = objectjson.length();
								jsonarray = new JSONArray();
								if (jsonvalue == 0) {
									JSONArray jsonArray = (JSONArray) object2.get(key);
									String sg[] = new String[jsonArray.length()];
									for (int i = 0; i < jsonArray.length(); i++) {
										jsonarray = new JSONArray();
										sg[i] = jsonArray.getString(i);
										jsonarray.put(jsonArray.getString(i));
									}
									if (!transform.hasNode(key)) {
										keyandvalues = transform.addNode(key);
										keyandvalues.setProperty(key, sg);
									} else {
										keyandvalues = transform.getNode(key);
										keyandvalues.setProperty(key, sg);
									}

								}
								session.save();
							}
						} else {

							if (!transform.hasNode("Uploaded_File")) {
								transformfilenode = transform.addNode("Uploaded_File");
								// out.println(" Transform if ...");
							} else {
								transformfilenode = transform.getNode("Uploaded_File");
								// out.println(" Transform eles...");
								// transformfilenode.remove();
								transformfilenode = transform.addNode("Uploaded_File");

							}
							// out.println("Uploaded_File");

							if (jsonObject.has("Transform_File_Data")) {
//								 out.println("Uploaded_File");
								JSONObject transformfilejson = jsonObject.getJSONObject("Transform_File_Data");
								
								if (transformfilejson.has("filename") && transformfilejson.has("filename")) {
									// out.println("Yes");
									filename = transformfilejson.getString("filename");
									filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
									filedata = transformfilejson.getString("filedata");
									// out.println("filename :"+filename+" filedata :"+filedata);
									/*
									 * transformfilenode.setProperty("Url",
									 * "http://35.186.166.22:8082/portal/content/CARROT_RULE/" + username + "/" +
									 * project + "/TRANSFORM_DATA/Uploaded_File/" + filename);
									 */
//							transformfilenode.setProperty("Url", request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+request.getContextPath()
//							+ "/bin/cpm/nodes/property.bin/content/CARROT_RULE/" + username + "/" + project + "/TRANSFORM_DATA/Uploaded_File/" + filename+"/_jcr_content?name=jcr%3Adata");
									String url1 = request.getScheme() + "://" + request.getServerName() + ":"
											+ request.getServerPort() + request.getContextPath()
											+ "/content/services/freetrial/users/" + username + "/"
											+ "CarrotruleMainNode/" + project + "/" + "/TRANSFORM_DATA/Uploaded_File/"
											+ filename;
									transformfilenode.setProperty("Url", url1);
									byte[] bytes = Base64.decode(filedata);
									InputStream myInputStream = new ByteArrayInputStream(bytes);

									Node subfileNode = transformfilenode.addNode(filename, "nt:file");
									Node jcrNode1 = subfileNode.addNode("jcr:content", "nt:resource");
									jcrNode1.setProperty("jcr:data", myInputStream);
									jcrNode1.setProperty("jcr:mimeType", "attach");

									session.save();
//							try {
									String excelurl = transformfilenode.getProperty("Url").getString();
									// out.println("Excel Url : " + excelurl);
									URL url = new URL(excelurl);
									URLConnection conn = url.openConnection();

									String contentType = conn.getContentType();
									int contentLength = conn.getContentLength();
									if (contentType.startsWith("text/") || contentLength == -1) {
										// out.println("This is not a binary file.");
									}

									Node externalnode = project_name.getNode("EXTERNAL_DATA").getNode("Download_Excel");
									Long lastcolumn = externalnode.getProperty("Last_Column").getLong();
//									out.println("This is not a binary file."+externalnode);
									// out.println(lastcolumn + (long) 1);
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
										System.out.println(
												"Only read " + offset + " bytes; Expected " + contentLength + " bytes");
									}
									FileOutputStream streamout = new FileOutputStream(
											CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + "Transform/"
													+ filename);

									streamout.write(data);
									streamout.close();
									raw.close();
									in.close();

									InputStream ExcelFileToRead = new FileInputStream(
											CrRuleConstValue.StringConstant.DEFAULT_CARROT_PATH.value() + "Transform/"
													+ filename);

									HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
									HSSFSheet sheet = wb.getSheetAt(0);
									DataFormatter objDefaultFormat = new DataFormatter();

									FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);

									HSSFRow row;
									HSSFCell cell;
									Iterator<Row> rows = sheet.rowIterator();
									JSONObject mainobject = new JSONObject();
									JSONObject rawjsontrnsform = new JSONObject();
									while (rows.hasNext()) {
										row = (HSSFRow) rows.next();
										if (row.getRowNum() == 0) {
											continue;
										}
										Iterator cells = row.cellIterator();

										while (cells.hasNext()) {
											cell = (HSSFCell) cells.next();

											if (cell.getColumnIndex() < lastcolumn) {
												if (row.getRowNum() == 1) {
													objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																						// And any
																						// type of
													keysvalue = objDefaultFormat.formatCellValue(cell,
															objFormulaEvaluator);
													keys.add(keysvalue);
//													out.print("keys = "+keys);
													// dataarray.put(keyobject);
												}
												if (row.getRowNum() == 2) {

													objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																						// And any
																						// type of
																						// cell will return string value
													valuedata = objDefaultFormat.formatCellValue(cell);
													// out.println("Formula 1: "+valuedata);

													values.add(valuedata);

												}

												// mainobject.put("Data", dataarray);

											}
											//transform
											if (cell.getColumnIndex() >= lastcolumn) {
//												out.print("lastcolumn = "+lastcolumn);
//												out.print("cell.getColumnIndex() = "+cell.getColumnIndex());
												if (row.getRowNum() == 1) {
													objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																						// And any
																						// type of
													keysv = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
													keysarray.add(keysv);
//													out.print("keysarray = "+keysarray);
													// dataarray.put(keyobject);
												}
												if (row.getRowNum() == 2) {
//													out.print("lastcolumn 2= "+lastcolumn);
													objFormulaEvaluator.evaluate(cell); // This will evaluate the cell,
																						// And any
																						// type of
																						// cell will return string value
													valued = objDefaultFormat.formatCellValue(cell);
													// out.println("Formula : "+valued);
													valuesarray.add(valued);

												}

												// mainobject.put("Data", dataarray);

											}

										}

									}
//									out.println(" keys.size()."+keys.size());
									for (int i = 0; i < keys.size(); i++) {

										mainobject.put(keys.get(i), values.get(i));
//										out.println(" mainobject."+mainobject);
										// out.println("raw :" + mainobject);

									}

									JSONObject json = new JSONObject();
									array2.put(mainobject);
									json.put("Raw_Data", array2);
								
									dataarra.put(json);
//									out.println("dataarra= 1"+dataarra);
									try {
										JSONArray trnformarray = new JSONArray();

										JSONObject rawjsontrn = null;
										for (int k = 0; k < keysarray.size(); k++) {
											rawjsontrn = new JSONObject();

											rawjsontrnsform.put(keysarray.get(k), valuesarray.get(k));

											rawjsontrn.put(keysarray.get(k), valuesarray.get(k));
											trnformarray.put(rawjsontrn);

										}

										JSONObject colmedata = new JSONObject();

										array.put(rawjsontrnsform);
										colmedata.put("Transform", trnformarray);
										dataarra.put(colmedata);
//										out.println("dataarra= 2"+dataarra);
									} catch (Exception e) {
										// TODO: handle exception
									}
								}
							}
							JSONObject transformjson12 = new JSONObject();
							transformjson12.put("data", dataarra);

							sfdcfile.put("status", "save");
							sfdcfile.put("Message", "Save Successfull");
							int counttrnsform = 0;
							String key = "";
							Iterator<String> keys1 = transformjson12.keys();
							while (keys1.hasNext()) {
								key = (String) keys1.next();

								JSONArray jsonkeyvalue = (JSONArray) transformjson12.get(key);

								String string = null;
								for (int k = 0; k < jsonkeyvalue.length(); k++) {
									JSONObject bhop = jsonkeyvalue.getJSONObject(k);

									Iterator<String> keys1a = bhop.keys();

									while (keys1a.hasNext()) {

										string = (String) keys1a.next();
										// String val=bhop.getString(string);

										JSONArray array4 = (JSONArray) bhop.get(string);
										String sg[] = new String[array4.length()];
										// out.println("array4 :"+array4);
										for (int a = 0; a < array4.length(); a++) {

											JSONObject bhop4 = array4.getJSONObject(a);
											// out.println("bhop4 :"+bhop4);

											Iterator<String> keysvalueitr = bhop4.keys();
											while (keysvalueitr.hasNext()) {

												JSONObject subobject1 = new JSONObject();
												String stringvalue = (String) keysvalueitr.next();
												// out.println("stringvalue :"+stringvalue);

												String valstringvalue = bhop4.getString(stringvalue);
												// out.println("stringvalue :"+stringvalue+" valstringvalue
												// :"+valstringvalue);

												subobject1.put("no", counttrnsform);
												subobject1.put("fieldname", stringvalue);
												subobject1.put("value", valstringvalue);
												sava_data.put(subobject1);
												counttrnsform++;
												sg[a] = array4.getString(a);

											}
										}
										String fo[] = { valued };

										if (transform.hasNode(string)) {

											rulena = transform.getNode(string);
											if (string.equals("Transform")) {
												rulena.setProperty(string, fo);

											}
											rulena.setProperty(string, sg);
										} else {

											rulena = transform.addNode(string);
											if (string.equals("Transform")) {
												rulena.setProperty(string, fo);

											}
											rulena.setProperty(string, sg);
											// out.println("key :"+string);

										}
										session.save();

									}

								}
								if (sava_data.length() > 0) {
									sfdcfile.put("Save_Data", sava_data);
								}
								// out.println("Save_Data :"+sava_data);
								jsonvalueobject = sfdcfile;

							}
//				}catch(Exception e) {}

							session.save();

						}
					} catch (Exception e) {
					}

				}
			} else {
				out.println("No user Exist");
			}

			session.save();
		} catch (Exception e) {
			out.println(e);
		}
		if (jsonvalue == 0) {
			out.println(jsonvalueobject);
			// out.println("Massage := Save Successfull");
		} else {
			out.println(boolean1);
			// out.println("Massage := Error not Successfull ");
		}

	}

	public JSONObject getkeyvalue(JSONArray trformjson, SlingHttpServletResponse response) {
		JSONArray jsonarray = null;
		JSONObject json = null;
		JSONObject jsonObject2 = new JSONObject();
		try {
			int count = 0;
			jsonarray = new JSONArray();
			// PrintWriter out = response.getWriter();
			for (int i = 0; i <= trformjson.length(); i++) {

				JSONObject object = trformjson.getJSONObject(i);
				Iterator<String> ke = object.keys();
				while (ke.hasNext()) {

					// String[] regexvalue = { "+", "=", "-", "=>", "=<", "!=" };
					String keyname = (String) ke.next();
					// out.println("keyname :"+keyname);
					String values = object.getString(keyname);

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
			e.printStackTrace();
		}
		return jsonObject2;
	}

	public JSONObject getkeyjson(JSONArray trformjson, JSONObject responsejson, SlingHttpServletResponse response,
			String username, String project) throws IOException {
		PrintWriter out = response.getWriter();
		JSONArray jsonarray = null;
		JSONObject jsonObject12 = new JSONObject();
		JSONObject jsonObjectadd = null;
		int count = 0;
		jsonarray = new JSONArray();

		for (int j = 0; j <= trformjson.length(); j++) {

			JSONObject object;
			try {
				/*
				 * Session session = repo.login(new SimpleCredentials("admin",
				 * "admin".toCharArray())); Node ip =
				 * session.getRootNode().getNode("content").getNode("CARROT_RULE");
				 */
				object = trformjson.getJSONObject(j);
				Iterator<String> ke = object.keys();
				while (ke.hasNext()) {
					jsonObjectadd = new JSONObject();

					String keyname = (String) ke.next();
					String values = object.getString(keyname);
					jsonObjectadd.put("no", count);
					jsonObjectadd.put("fieldname", keyname);
					jsonObjectadd.put("value", values);
					jsonarray.put(jsonObjectadd);

					// out.println("jsonarray :"+jsonarray);
					count++;

				}
//				out.println("jsonarray1 :"+jsonarray);
				JSONObject transform = responsejson.getJSONObject("TRANSFORM");
				JSONArray rawdataarray = transform.getJSONArray("Raw_Data");
				for (int i = 0; i < rawdataarray.length(); i++) {
					JSONObject jsondata = new JSONObject();

					// out.println("Data 1 "+datajson);
					JSONObject rawdatajson = rawdataarray.getJSONObject(i);
					String inputname = rawdatajson.getString("input_name");
					// datajson.put(key, value)
					jsondata.put("no", count++);
					jsondata.put("fieldname", inputname);
					jsondata.put("value", inputname);
					jsonarray.put(jsondata);
//					out.println("jsonarray2 :"+jsonarray);
				}
				jsonObject12.put("status", "save");
				jsonObject12.put("Message", "Save Successfull ");
				jsonObject12.put("Save_Data", jsonarray);
			} catch (Exception e) {
//				out.println("exc: "+e);
//				e.getMessage();
			}
		}
		return jsonObject12;
	}

	public JSONObject getJsonAlldata(JSONObject object, Session session, String project, String username,
			SlingHttpServletResponse response) throws IOException {
		// PrintWriter out = response.getWriter();
		JSONObject rawdatajsonobject = new JSONObject();
		try {

			JSONArray jsonArray = new JSONArray();
			String key;
			Iterator<String> keys1 = object.keys();
			while (keys1.hasNext()) {
				key = (String) keys1.next();

				JSONArray jsonkeyvalue = (JSONArray) object.get(key);

				for (int k = 0; k < jsonkeyvalue.length(); k++) {
					JSONObject bhop = jsonkeyvalue.getJSONObject(k);

					Iterator<String> keys1a = bhop.keys();
					while (keys1a.hasNext()) {
						JSONObject subobject = new JSONObject();
						String string = (String) keys1a.next();
						String val = bhop.getString(string);

						// out.println("string :"+string+" val :"+val);
						subobject.put("fieldname", string);
						subobject.put("value", val);
						jsonArray.put(subobject);

					}
				}
				rawdatajsonobject.put("sfdcjson", jsonArray);
			}

		} catch (Exception e) {

		}
		return rawdatajsonobject;
	}

}
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
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
// TODO: Auto-generated Javadoc

/**
 * The Class GenericConsolidatorServ is used to generate excel with raw data, transform data and rule engine output data.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/GenericConsolidator" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })
@SuppressWarnings("serial")

// http://35.236.154.164:8082/portal/servlet/service/testserv
public class GenericConsolidatorServ extends SlingAllMethodsServlet {

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
			if (request.getRequestPathInfo().getExtension().equals("index")) {

				try {
					RequestDispatcher dis= request.getRequestDispatcher("/content/static/.index");
					   dis.forward(request, response);
					
				}catch (Exception e) {
					
					out.println(e.getMessage().toString());
				}
						}} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

    	}
	
	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		PrintWriter out = response.getWriter();
		Session session = null;
//		Node usernamenode = null;
		Node projectnamenode = null;
		Value[] outputdata = null;
		int count = 0;
		int outputcount = 0;
		int ruleenginecount = 0;
		Node consolidatorgenericnode = null;
		Node consolidatorfilenode = null;
		JSONArray arr = new JSONArray();
		String username = null;
		String projectname = null;
		String ruleenginename = null;
		ArrayList formulavalues = new ArrayList();
		JSONObject forobj = null;
		JSONObject fileobject = new JSONObject();
		JSONArray formulaarray = new JSONArray();
		Node carrotmainNode=null;
		try {

			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

			if (request.getRequestPathInfo().getExtension().equals("GenericConsolidator")) {

				StringBuilder builder = new StringBuilder();
				BufferedReader bufferedReaderCampaign = request.getReader();
				String brokerageline;
				while ((brokerageline = bufferedReaderCampaign.readLine()) != null) {
					builder.append(brokerageline + "\n");
				}
				HSSFWorkbook workbook = new HSSFWorkbook();
				// Create a new Worksheet
				HSSFSheet sheet = workbook.createSheet("Json File");

				HSSFRow rowhead0 = sheet.createRow((short) 0);
				HSSFRow rowhead1 = sheet.createRow((short) 1);
				int ruleoutputcount = 0;

				JSONObject jsonObject = new JSONObject(builder.toString());
				username = jsonObject.getString("user_name").replace("@", "_");
				projectname = jsonObject.getString("projectname").replace("@", "_");
				try {
					String freetrialstatus = fr.checkfreetrial(username);
//				out.println("hello fr.checkfreetrial = " + freetrialstatus);
					carrotmainNode = fr.getCarrotruleNode(freetrialstatus, username, session, response);
//									out.println("hello fr.getCarrotruleNode = carrotmainNode:: "+carrotmainNode);

				} catch (Exception e) {
					// TODO: handle exception

				}
				
//				Node content = session.getRootNode().getNode("content").getNode("CARROT_RULE");
//				if (content.hasNode(username)) {
				if (carrotmainNode !=null) {
					
//					usernamenode = content.getNode(username);
					// rowhead0.createCell(count).setCellValue("Input");
					JSONObject valuecheck = GetData(session, username, projectname,carrotmainNode);
					// out.println("ValueCheck : "+valuecheck);
					// System.out.println(valuecheck);
					// Set<String> uniquecode=new HashSet<String>();
					JSONArray inputjson = valuecheck.getJSONArray("RawData");
					for (int i = 0; i < inputjson.length(); i++) {

						// out.println("count : " + count);
						int columnindex = count;
						rowhead0.createCell(count).setCellValue("Input");

						// while (jsonitr.hasNext()) {
						String keys = inputjson.getString(i);

						rowhead1.createCell(count++).setCellValue((String) keys);
						// out.println("Count : : : "+count);
						// }
						// }
						sheet.addMergedRegion(new CellRangeAddress(0, 0, columnindex, count));

					}
					ruleenginecount = ruleenginecount + 1 + count;
					ruleoutputcount = ruleoutputcount + 1 + count;
					// out.println("Count : "+count);
					// out.println("ruleenginecount out : "+ruleenginecount);
					if (carrotmainNode.hasNode(projectname)) {
						projectnamenode = carrotmainNode.getNode(projectname);

						if (!projectnamenode.hasNode("Consolidator_Generic_Node")) {
							consolidatorgenericnode = projectnamenode.addNode("Consolidator_Generic_Node");
						} else {

							consolidatorgenericnode = projectnamenode.getNode("Consolidator_Generic_Node");

						}
						NodeIterator itr = carrotmainNode.getNode(projectname).getNode("Rule_Engine")
								.getNodes();
						while (itr.hasNext()) {
							Node itrnode = itr.nextNode();
							ruleenginename = itrnode.getName();// ruleengine name
							// out.println("ruleenginename : " + ruleenginename);
							rowhead0.createCell(ruleenginecount++).setCellValue(ruleenginename);
							// out.println("ruleenginecount In: "+ruleenginecount);
							NodeIterator ruleengineitr =carrotmainNode.getNode(projectname)
									.getNode("Rule_Engine").getNode(ruleenginename).getNodes();
							if (ruleengineitr.hasNext()) {

								Node rulename = ruleengineitr.nextNode();// first rule node

								if (rulename.hasProperty("OutputField")) {

									outputdata = rulename.getProperty("OutputField").getValues();
									for (int i = 0; i < outputdata.length; i++) {
										String rulvalue = outputdata[i].getString();
										// out.println("Rule Engine Count : "+ruleoutputcount);
										// rowhead0.createCell(ruleenginecount++).setCellValue(ruleenginename);

										rowhead1.createCell(ruleoutputcount++)
												.setCellValue(new JSONObject(rulvalue).getString("field"));
										// out.println("Field : : :" + new JSONObject(rulvalue).getString("field"));
										sheet.addMergedRegion(
												new CellRangeAddress(0, 0, ruleoutputcount, ruleoutputcount));

										// ruleenginecount
									}
									// out.println("RuleOutptut Count : "+ruleoutputcount);
									ruleenginecount = ruleoutputcount;
								}
							}
						}
						int noOfColumns = rowhead0.getPhysicalNumberOfCells();
//						out.println("number of colin exel= "+noOfColumns);
						
//						transformgenericnode.g("Level1")
//						Node lvlnode=transformgenericnode.getNode("Level1");//("Level1")
						if(consolidatorgenericnode.hasProperty("lastcolnumber")) {
							consolidatorgenericnode.getProperty("lastcolnumber").remove();
						
						}
						consolidatorgenericnode.setProperty("lastcolnumber", noOfColumns);

					}
					File file = new File(CrRuleConstValue.StringConstant.Generic_PATH.value() + username);
					// file.mkdirs();
					if (!file.exists()) {
						file.mkdir();

					}
					FileOutputStream fileOut = new FileOutputStream(CrRuleConstValue.StringConstant.Generic_PATH.value()
							+ username + "/" + username + "consolidator.xls");
					workbook.write(fileOut);
					fileOut.close();
					fileobject.put("fileurl",
							request.getServerName() + ":" + request.getServerPort()
									+ "/carrotruleexcelfiles/Generic/DownloadFile/" + username + "/" + username
									+ "consolidator.xls");

				}
				out.println(fileobject);
				// out.println("getLastCellNum : : :" + rowhead0.getLastCellNum());
				/*
				 * File file = new File(CrRuleConstValue.StringConstant.Generic_PATH.value() +
				 * "consolidatorread.xls"); if (file.exists()) { out.println("INSIDE");
				 * InputStream ExcelFileToRead = new FileInputStream(
				 * CrRuleConstValue.StringConstant.Generic_PATH.value() +
				 * "consolidatorread.xls"); HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
				 * HSSFSheet sheetread = wb.getSheetAt(0); DataFormatter objDefaultFormat = new
				 * DataFormatter(); FormulaEvaluator objFormulaEvaluator = new
				 * HSSFFormulaEvaluator((HSSFWorkbook) wb); HSSFRow row; HSSFCell cell;
				 * Iterator<Row> rows = sheetread.rowIterator(); while (rows.hasNext()) { row =
				 * (HSSFRow) rows.next(); // out.println("Row Next"); Iterator cells =
				 * row.cellIterator();
				 * 
				 * while (cells.hasNext()) { cell = (HSSFCell) cells.next(); //
				 * out.println("Cell Next");
				 * 
				 * if (cell.getCellNum() == rowhead1.getLastCellNum()) {
				 * 
				 * if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				 * 
				 * formulavalues.add(cell.getCellFormula()); } } }
				 * 
				 * }
				 * 
				 * for (int i = 0; i < formulavalues.size(); i++) {
				 * out.println(formulavalues.get(i)); forobj = new JSONObject();
				 * forobj.put("Formula", formulavalues.get(i)); formulaarray.put(forobj);
				 * 
				 * } out.println("Jsonarray : : : :" + formulaarray); String forarray[] = new
				 * String[formulaarray.length()]; for (int j = 0; j < formulaarray.length();
				 * j++) {
				 * 
				 * forarray[j] = formulaarray.getString(j); out.println(forarray);
				 * 
				 * } consolidatorgenericnode.setProperty("Formula", forarray);
				 */
				session.save();

				/*
				 * String agentdata = Getagentdata(session, username, projectname);
				 * out.println("Agent Data Created : " + agentdata);
				 */ }
		} catch (Exception e) {
			out.println("Exception ex :" + e.getMessage());
		}
	}

	/**
	 * Gets the data.
	 *
	 * @param session the session
	 * @param username the username
	 * @param projectname the projectname
	 * @param carrotnode the carrotnode
	 * @return the JSON object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JSONObject GetData(Session session, String username, String projectname,Node carrotnode) throws IOException {

//		Node usernamenode = null;
		Node projectnamenode = null;
		String fielddatapropertyvalue = null;
		JSONObject objectvalues = null;
		JSONArray rawobjectarray = null;

		Node ruleenginenode = null;
		Node ruleenginenamenode = null;
		Node ruleenginerulesnode = null;
		String ruleenginevalue = null;
		String ruleenginenamevalue = null;
		String ruleenginerulename = null;
		Value[] fielddatavalues = null;
		try {

//			Node content = session.getRootNode().getNode("content").getNode("CARROT_RULE");
			if (carrotnode !=null) {
//				usernamenode = content.getNode(username);

				if (carrotnode.hasNode(projectname)) {
					projectnamenode = carrotnode.getNode(projectname);
					objectvalues = new JSONObject();
					rawobjectarray = new JSONArray();
					// if (ruleengineitr.hasNext()) {

					NodeIterator ruleenginerulesitr = carrotnode.getNode(projectname)
							.getNode("EXTERNAL_DATA").getNode("File").getNodes();

					if (ruleenginerulesitr.hasNext()) {
						ruleenginerulesnode = ruleenginerulesitr.nextNode();// File name
						ruleenginerulename = ruleenginerulesnode.getName();
						System.out.println("FileName : " + ruleenginerulename);
						// File file = new File(CrRuleConstValue.StringConstant.Generic_PATH.value() +
						// username);
						System.out.println(CrRuleConstValue.StringConstant.FILE_PATH.value() + ruleenginerulename);

						InputStream ExcelFileToRead = new FileInputStream(
								CrRuleConstValue.StringConstant.FILE_PATH.value() + ruleenginerulename);
						HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
						HSSFSheet sheet1 = wb.getSheetAt(0);

						HSSFRow row;
						HSSFCell cell;
						DataFormatter objDefaultFormat1 = new DataFormatter();

						FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);

						Iterator<Row> rows = sheet1.rowIterator();

						while (rows.hasNext()) {
							row = (HSSFRow) rows.next();

							Iterator cells = row.cellIterator();
							// System.out.println(row.getRowNum());
							String keysvalue = null;

							while (cells.hasNext()) {
								cell = (HSSFCell) cells.next();
								if (row.getRowNum() == 0) {

									// System.out.println("Transform Node Value : "+transformnodevalue);
									System.out.println("In");
									objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any
									// type of
									keysvalue = objDefaultFormat1.formatCellValue(cell, objFormulaEvaluator);
									System.out.println(keysvalue);

									rawobjectarray.put(keysvalue);
								}
								objectvalues.put("RawData", rawobjectarray);

							}

						}

					}
				}
			}

		} catch (Exception e) {

		}
		return objectvalues;
	}

	/*
	 * public String Getagentdata(Session session, String username, String
	 * projectname) throws IOException { Node usernamenode = null; Node
	 * projectnamenode = null; Value[] outputdata = null; FileOutputStream fileOut =
	 * null; String key = null; String values = null; JSONObject objectjson = null;
	 * String valuedata = null; JSONArray mainarray = new JSONArray(); JSONObject
	 * mainjson = new JSONObject(); String clubbingflagvalue = null; JSONArray
	 * newJsonArr = new JSONArray();
	 * 
	 * try {
	 * 
	 * Node content =
	 * session.getRootNode().getNode("content").getNode("CARROT_RULE"); //
	 * HSSFWorkbook workbook1 = new HSSFWorkbook(); HSSFSheet sheet1 = null;
	 * DataFormatter objDefaultFormat = new DataFormatter(); HSSFWorkbook workbook1
	 * = null; if (content.hasNode(username)) { usernamenode =
	 * content.getNode(username); if (usernamenode.hasNode(projectname)) {
	 * projectnamenode = usernamenode.getNode(projectname); NodeIterator itr =
	 * content.getNode(username).getNode(projectname).getNode("Rule_Engine").
	 * getNodes(); if (itr.hasNext()) { Node itrnode = itr.nextNode(); String
	 * ruleenginename = itrnode.getName();// ruleengine name if
	 * (ruleenginename.equals("ConsolidatorNode")) { } else { //
	 * out.println("ruleenginename : "+ruleenginename); NodeIterator ruleengineitr =
	 * content.getNode(username).getNode(projectname)
	 * .getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT").getNodes()
	 * ; while (ruleengineitr.hasNext()) { Node yearnode = ruleengineitr.nextNode();
	 * String yearnodename = yearnode.getName();// year NodeIterator yearitr =
	 * content.getNode(username).getNode(projectname)
	 * .getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT")
	 * .getNode(yearnodename).getNodes(); while (yearitr.hasNext()) { Node monthnode
	 * = yearitr.nextNode(); String monthname = monthnode.getName();// Month
	 * NodeIterator agentitr = content.getNode(username).getNode(projectname)
	 * .getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT")
	 * .getNode(yearnodename).getNode(monthname).getNodes();
	 * 
	 * while (agentitr.hasNext()) { JSONArray objectarray = null; objectarray = new
	 * JSONArray(); Node agentid = agentitr.nextNode(); String agentname =
	 * agentid.getName();// agentid NodeIterator agentcountitr =
	 * content.getNode(username).getNode(projectname)
	 * .getNode("Rule_Engine").getNode("Clubbing_Rule").getNode("OUTPUT")
	 * .getNode(yearnodename).getNode(monthname).getNode(agentname).getNodes();
	 * System.out.println("agentname :" + agentname); workbook1 = new
	 * HSSFWorkbook(); sheet1 = workbook1.createSheet("Agent File"); int
	 * agentcountdata = 1; HSSFRow rowhead1 = null; HSSFRow rowhead =
	 * sheet1.createRow((short) 0); JSONObject objYJson = new JSONObject(); while
	 * (agentcountitr.hasNext()) { Node countnode = agentcountitr.nextNode();//
	 * agentnodecount rowhead1 = sheet1.createRow((short) agentcountdata++); if
	 * (countnode.hasProperty("Output")) { int count = 0; int count1 = 0; outputdata
	 * = countnode.getProperty("Output").getValues(); for (int i = 0; i <
	 * outputdata.length; i++) { values = outputdata[i].getString(); //
	 * System.out.println("Json Data : " + new JSONObject(values)); // JSONObject
	 * objectjson = new JSONObject(values);
	 * 
	 * objectjson = new JSONObject(values); // HSSFRow rowhead =
	 * sheet1.createRow((short) 0); // rowhead1 = sheet1.createRow((short)
	 * agentcountdata++);
	 * 
	 * Iterator<String> keys = objectjson.keys(); while (keys.hasNext()) { key =
	 * keys.next(); String jsonvalues = objectjson.getString(key); //
	 * System.out.println("jsonvalues : :: :"+jsonvalues); clubbingflagvalue =
	 * objectjson.getString("clubbing_flag");
	 * rowhead.createCell(count++).setCellValue(key); Cell cellvalue =
	 * rowhead1.createCell(count1++); if (jsonvalues.matches(".*\\d+.*") == false &&
	 * jsonvalues.length() > 0) { // System.out.println("false : " + jsonvalues);
	 * cellvalue.setCellType(Cell.CELL_TYPE_STRING);
	 * cellvalue.setCellValue(jsonvalues); // cellvalue.setCellStyle(style); } if
	 * (jsonvalues.matches("[-+]?[0-9]*\\.?[0-9]*") == true && jsonvalues.length() >
	 * 0) { // System.out.println("true number : " + jsonvalues);
	 * cellvalue.setCellType((Cell.CELL_TYPE_NUMERIC));
	 * cellvalue.setCellValue((Double.parseDouble(jsonvalues))); } if
	 * (jsonvalues.matches(
	 * "((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$" +
	 * "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$" +
	 * "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" +
	 * "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$" +
	 * "|^([0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9])$" +
	 * "((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))/02/29)$" +
	 * "|^(((19|2[0-9])[0-9]{2})/02/(0[1-9]|1[0-9]|2[0-8]))$" +
	 * "|^(((19|2[0-9])[0-9]{2})/(0[13578]|10|12)/(0[1-9]|[12][0-9]|3[01]))$" +
	 * "|^(([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9])$" +
	 * "|^(([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9])$") == true
	 * || jsonvalues.contains("/")) { // System.out.println("true Date ");
	 * cellvalue.setCellValue(jsonvalues);
	 * 
	 * // workbook1.removeSheetAt(0); } }
	 * 
	 * }
	 * 
	 * 
	 * System.out.println(workbook1.getSheetAt(0) .getRow(2).getCell(120));
	 * 
	 * // objectjson.put("clubbingsales", valuedata);
	 * 
	 * String valuedata1 = workbook1.getSheetAt(0)
	 * .getRow(Integer.parseInt(countnode.getName()))
	 * .getCell(rowhead1.getLastCellNum()).toString();
	 * System.out.println("Valuedata: : : " + valuedata1);
	 * 
	 * 
	 * // System.out.println("Agent Count : "+agentcountdata);
	 * objectarray.put(objectjson); } //
	 * System.out.println("ValueData : : : "+valuedata); // System.out.println("The
	 * final clubsales value to be substituted is :: //
	 * "+objectarray.getJSONObject(objectarray.length()-1).getString("clubbingsales"
	 * )); // String clubsumValue = //
	 * objectarray.getJSONObject(objectarray.length()-1).getString("clubbingsales");
	 * // for(int j = 0 ; j< objectarray.length()-1 ;j++) { // JSONObject nsum = //
	 * objectarray.getJSONObject(j).put("clubbingsales",clubsumValue); //
	 * System.out.println("The manipulated jsonObject is //
	 * =="+nsum.getString("clubbingsales")); // } //
	 * objectarray.put(objectjson.put("Clubbingsales", valuedata)); // Sheet sheet =
	 * workbook1.getSheetAt(0);
	 * 
	 * for (Row row : sheet) { sheet.removeRow(row); }
	 * 
	 * 
	 * } NodeIterator consolidatorformula =
	 * projectnamenode.getNode("consolidator_Generic_Node") .getNodes(); while
	 * (consolidatorformula.hasNext()) { Node consolidator =
	 * consolidatorformula.nextNode(); if (consolidator.hasProperty("Formula")) {
	 * Value[] userformula = consolidator.getProperty("Formula").getValues(); for
	 * (int i = 0; i < userformula.length; i++) { String formulavalues =
	 * userformula[i].getString(); System.out.println("Formula : : : " +
	 * formulavalues); Cell cell1 = rowhead1.createCell(rowhead1.getLastCellNum());
	 * cell1.setCellFormula( new JSONObject(formulavalues).getString("Formula"));
	 * FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator(
	 * (HSSFWorkbook) workbook1); objFormulaEvaluator.evaluate(cell1); // This will
	 * evaluate // the cell, And any valuedata = objDefaultFormat
	 * .formatCellValue(cell1, objFormulaEvaluator).toString();
	 * 
	 * } } }
	 * 
	 * // objectjson.put("clubbingsales", valuedata); //
	 * objectarray.put(objectjson.put("Clubbingsales", valuedata)); for (int i = 0;
	 * i < objectarray.length(); i++) { newJsonArr
	 * .put(objectarray.getJSONObject(i).put("clubbingsales", valuedata)); } fileOut
	 * = new FileOutputStream( CrRuleConstValue.StringConstant.Generic_PATH.value()
	 * + "agentdatanew.xls"); workbook1.write(fileOut); //
	 * System.out.println(mainarray); workbook1 = null; sheet1 = null;
	 * fileOut.close();
	 * 
	 * }
	 * 
	 * // clubbingsales //
	 * 
	 * 
	 * objectarray.put(mainjson);
	 * 
	 * InputStream ExcelFileToRead = new
	 * FileInputStream(CrRuleConstValue.StringConstant.Generic_PATH.value()+
	 * "agentdata.xls");
	 * 
	 * HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead); HSSFSheet sheetread =
	 * wb.getSheetAt(0); DataFormatter objDefaultFormatread = new DataFormatter();
	 * 
	 * FormulaEvaluator objFormulaEvaluator = new
	 * HSSFFormulaEvaluator((HSSFWorkbook) wb);
	 * 
	 * HSSFRow row; HSSFCell cell; Iterator<Row> rows = sheetread.rowIterator();
	 * JSONObject mainobject = new JSONObject(); JSONObject rawjsontrnsform = new
	 * JSONObject(); DataFormatter dataFormatter = new DataFormatter();
	 * 
	 * 
	 * 
	 * 
	 * File xx = new File(CrRuleConstValue.StringConstant.Generic_PATH.value()
	 * +"agentdata.xls"); if (xx.exists()) { xx.delete(); }
	 * 
	 * 
	 * } } }
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * } catch (Exception ex) { System.out.println("Exception ex : " +
	 * ex.getMessage()); } return "Created"; }
	 */

	/**
	 * Upload to server.
	 *
	 * @param urlstr the urlstr
	 * @param json the json
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JSONException the JSON exception
	 */
	public static String uploadToServer(String urlstr, JSONArray json) throws IOException, JSONException {
		StringBuilder response = null;
		URL url = null;
		HttpURLConnection con = null;
		DataOutputStream wr = null;
		BufferedReader in = null;
		// .replaceAll("[╔,û#á]", "")
		// BufferedWriter writer = null;
		try {
			// out.println("Json passed is "+json);
			url = new URL(urlstr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			con.setDoOutput(true);
			wr = new DataOutputStream(con.getOutputStream());
			// writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
			// writer.write(json.toString());
			wr.writeBytes(json.toString());
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine = null;
			response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			// System.out.println(response.toString());

		} catch (Exception e) {
			response = null;

		} finally {

			if (null != con) {
				con.disconnect();
				con = null;
			}
			if (null != wr) {
				wr.flush();
				wr.close();
				wr = null;
			}
			/*
			 * if(null != writer){ writer.flush(); writer.close(); writer = null; }
			 */
			if (null != in) {
				in.close();
				in = null;
			}
		}
		return (response == null ? "" : response.toString());

	}
	

}
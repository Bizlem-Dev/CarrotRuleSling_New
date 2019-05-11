package ruleengine.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;

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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;

import com.ruleengine.pojo.Stopwatch;
import com.ruleengineservlet.CrRuleConstValue;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class DbConnectionclass.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({ @Property(name = "service.description", value = "Save product Servlet"),
		@Property(name = "service.vendor", value = "VISL Company"),
		@Property(name = "sling.servlet.paths", value = { "/servlet/service/SaveDataInMongo" }),
		@Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.extensions", value = { "hotproducts", "cat", "latestproducts", "brief",
				"prodlist", "catalog", "viewcart", "productslist", "addcart", "createproduct", "checkmodelno",
				"productEdit" }) })

public class DbConnectionclass extends SlingAllMethodsServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The repo. */
	@Reference
	private SlingRepository repo;

	/* (non-Javadoc)
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

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
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		Session session = null;
		try{
			
			session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
			if (request.getRequestPathInfo().getExtension().equals("savedatamongo")) {

				out.println("Enter data save in mongo");
				StringBuilder builder = new StringBuilder();

				BufferedReader bufferedReaderCampaign = request.getReader();
				String line;
				while ((line = bufferedReaderCampaign.readLine()) != null) {
					builder.append(line + "\n");
				}
				JSONObject fulljsonobject = new JSONObject(builder.toString());
				String username = fulljsonobject.getString("user_name").replace("@", "_");
				String agentid = fulljsonobject.getString("agent_id");
				retrieveDatafromExcel(username);
				selectDataByAgentId(agentid,username);
				//ConnectionFactory.CONNECTION.closeConnection(linked);

		}
			out.println("Main servlet exit");
			//session.save();
				
		}catch(Exception e){
			out.println("Exception Occured"+e.getMessage());
		}
		
		
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		checkFordbCollection("mohit");
		retrieveDatafromExcel("mohit");
		createIndex("mohit");
		selectDataByAgentId("1004","mohit");
		ConnectionFactory.CONNECTION.closeConnection();
	}

	/**
	 * Select data by agent id.
	 *
	 * @param agentId the agent id
	 * @param username the username
	 */
	public static void selectDataByAgentId(String agentId,String username){
		Mongo mongoClient = null;
		DB db = null;
		DBCollection linked = null;
		BasicDBObject searchQuery = null;
		DBCursor cursor = null;
		 try {
			mongoClient = ConnectionFactory.CONNECTION.getClient();
			 db = mongoClient.getDB("carrotruledb");
			linked = db.getCollection(username);
			 searchQuery = new BasicDBObject(); 
			 searchQuery.put("agent_id__c", agentId); 
			 cursor = linked.find(searchQuery);
			 while (cursor.hasNext()) {
				 JSONObject output = new JSONObject(JSON.serialize(cursor.next()));
				 output.remove("_id");
				 System.out.println(output);
				 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the index.
	 *
	 * @param username the username
	 */
	public static void createIndex(String username){
		Mongo mongoClient = null;
		DB db = null;
		DBCollection linked = null;
		try{
			mongoClient = ConnectionFactory.CONNECTION.getClient();
			 db = mongoClient.getDB("carrotruledb");
			linked = db.getCollection(username);
			 linked.createIndex(new BasicDBObject("agent_id__c",1));
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			ConnectionFactory.CONNECTION.closeConnection();
		}
	}
	
	/**
	 * Check fordb collection.
	 *
	 * @param username the username
	 */
	private static void checkFordbCollection(String username){

		Mongo mongoClient = null;
		DB db = null;
		try
		{
		mongoClient = ConnectionFactory.CONNECTION.getClient();
		db = mongoClient.getDB("carrotruledb");
		if(!db.collectionExists(username)){
			db.getCollection(username).drop();
			System.out.println("Collection dropped Successfully");
		}
		  
		}
		catch(Exception e)
		{
		System.out.println(e.getClass().getName()+":"+e.getMessage());

		}

	
	}
	
	/**
	 * Makedb connection.
	 *
	 * @param json the json
	 * @param username the username
	 */
	private static void makedbConnection(JSONObject json, String username){
		Mongo mongoClient = null;
		DB db = null;
		DBObject dbObject = null;
		DBCollection linked = null;
		try
		{
		mongoClient = ConnectionFactory.CONNECTION.getClient();
		db = mongoClient.getDB("carrotruledb");
		/*if(db.collectionExists(username)){
			db.getCollection(username).drop();
			System.out.println("Collection dropped Successfully");
		}*/
		linked=db.getCollection(username);
		 dbObject = (DBObject)(JSON.parse(json.toString()));
		 
		 linked.insert(dbObject);
		
		  
		}
		catch(Exception e)
		{
		System.out.println(e.getClass().getName()+":"+e.getMessage());

		}

	}


	
	
	/**
	 * Retrieve datafrom excel.
	 *
	 * @param username the username
	 * @return the JSON object
	 */
	private static JSONObject retrieveDatafromExcel(String username){
		InputStream externalexcelread = null;
		String o = null;
		Stopwatch timer1 = null;
		JSONObject primarykeydata = null;
		JSONObject finalOutput = null;
		File dir = null;
		File[] directoryListing = null;
		HSSFWorkbook externalwb = null;
		HSSFSheet externalsheet = null;
		DataFormatter externalobjDefaultFormat = null;
		FormulaEvaluator externalobjFormulaEvaluator = null;
		int count = 0;
		try {
			//E:\ExcelData\Externaldata
			dir = new File("E://ExcelData/Externaldata/");
			  directoryListing = dir.listFiles();
			  if (directoryListing != null) {
			    for (File child : directoryListing) {
			      // Do something with child

					System.out.println("ExcelFile name "+child.getName());
					externalexcelread = new FileInputStream(child);
					externalwb = new HSSFWorkbook(externalexcelread);
					externalsheet = externalwb.getSheetAt(0);
					externalobjDefaultFormat = new DataFormatter();

					externalobjFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) externalwb);

					HSSFRow externalrow = null;

					HSSFCell externalcell = null;
					HSSFCell externalcellFirst = null;
					Iterator<Row> externalrows = externalsheet.rowIterator();

					Row firstRow = null;
					finalOutput = new JSONObject();
					while (externalrows.hasNext()) {
						timer1 = new Stopwatch();
						externalrow = (HSSFRow) externalrows.next();
						if (externalrow.getRowNum() == 0) {
							firstRow = externalrow;
							continue;
						}
						//System.out.println(" At Row Entry Level:: searching for the passed agentId :: " + new Date()+" and the elapsed time is "+timer1.elapsedTime());
						
						Iterator firstRowIt = firstRow.cellIterator();
						Iterator externalcells = externalrow.cellIterator();

						String val = "";
						String rowdata = "";
						primarykeydata = new JSONObject();
						while (externalcells.hasNext()) {
							externalcell = (HSSFCell) externalcells.next();
							externalcellFirst = (HSSFCell) firstRowIt.next();
							
							if (externalcell.getColumnIndex() == 0) {
								externalobjFormulaEvaluator.evaluate(externalcell);
								val = externalobjDefaultFormat.formatCellValue(externalcell, externalobjFormulaEvaluator);
							}
							//System.out.println(" At Row Column Level:: Passed agentId to the Excel File   " + new Date()+" and the elapsed time is "+timer1.elapsedTime());
							
							/*if(!(val.equals(primarykeydata.getString("value")))){
								break;
							}
							*/
								
								if (externalcell.getColumnIndex() < firstRow.getLastCellNum()) {
									if (externalcell.getCellType() == Cell.CELL_TYPE_FORMULA) {
										Object formulaValue = null;
										switch (externalcell.getCachedFormulaResultType()) {
										case Cell.CELL_TYPE_NUMERIC:
											formulaValue = externalcell.getNumericCellValue();
											o = formulaValue.toString();

											break;
										case Cell.CELL_TYPE_STRING:
											formulaValue = externalcell.getRichStringCellValue();
											o = formulaValue.toString();

											break;
										case Cell.CELL_TYPE_ERROR:
											formulaValue = "";
											o = formulaValue.toString();
											break;
										
										}
									}
										else {
										externalobjFormulaEvaluator.evaluate(externalcell);
										o = externalobjDefaultFormat.formatCellValue(externalcell,
												externalobjFormulaEvaluator);

									}
									externalobjFormulaEvaluator.evaluate(externalcellFirst);
									rowdata = externalobjDefaultFormat.formatCellValue(externalcellFirst,
											externalobjFormulaEvaluator);
									String str = rowdata;
									primarykeydata.put(str.replaceAll("[\\s.╔,û#]","").toLowerCase(), o.replaceAll("[╔,û#]", ""));
									
								}

								else {
									break;
								}
								
						
					
						}
						//Make database call here
						makedbConnection(primarykeydata, username);
						count++;
					}
					System.out.println("Total no of data saved is "+count);			
				
			    }
			  } else {
			    // Handle the case where dir is not really a directory.
			    // Checking dir.isDirectory() above would not be sufficient
			    // to avoid race conditions with another process that deletes
			    // directories.
			  }
			} catch (Exception e) {
			e.printStackTrace();
		}
		return finalOutput;
	}

}

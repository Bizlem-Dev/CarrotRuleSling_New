package com.ruleengineservlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrRuleConstValue {
//CrRuleConstValue.Rule_Engine_Name.
	
	public static boolean isNullString (String p_text){
		if(p_text != null && p_text.trim().length() > 0 && !"null".equalsIgnoreCase(p_text.trim())){
			return false;
		}
		else{
			return true;
		}
	}
	
	public static boolean isBlank(final CharSequence cs) {
		       int strLen;
		      if (cs == null || (strLen = cs.length()) == 0) {
		           return true;
		       }
		       for (int i = 0; i < strLen; i++) {
	            if (!Character.isWhitespace(cs.charAt(i))) {
		              return false;
		           }
	       }
	        return true;
	    }
	
	public static  String convertStringToDate(String dateString)
	{
	    Date date = null;
	    String formatteddate = null;
	    DateFormat df = null;
	    DateFormat dfOut = new SimpleDateFormat("dd/MM/yyyy");
	    try{
	    	if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
    		return dateString;
        }
	    	else if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
	    		df = new SimpleDateFormat("dd-MM-yyyy");
	    		date = df.parse(dateString);
	    	}
	    	else if (dateString.matches("([0-9]{4})([0-9]{2})([0-9]{2})")) {
	    		df = new SimpleDateFormat("yyyyMMdd");
	    		date = df.parse(dateString);
	    	} else if (dateString.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
	    		df = new SimpleDateFormat("yyyy-MM-dd");
	    		date = df.parse(dateString);
	    	} else if (dateString.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {
	    		df = new SimpleDateFormat("yyyy/MM/dd");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{1})/([0-9]{1})/([0-9]{2})")) {
	    		df = new SimpleDateFormat("d/M/yy");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{1})-([0-9]{1})-([0-9]{2})")) {
	    		df = new SimpleDateFormat("d-M-yy");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{2})/([0-9]{1})/([0-9]{1})")) {
	    		df = new SimpleDateFormat("yy/M/d");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{2})-([0-9]{1})-([0-9]{1})")) {
	    		df = new SimpleDateFormat("yy-M-d");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{1})/([0-9]{2})/([0-9]{4})")) {
	    		df = new SimpleDateFormat("d/MM/yyyy");
	    		date = df.parse(dateString);
	    	}
	    	else if (dateString.matches("([0-9]{1})/([0-9]{1})/([0-9]{4})")) {
	    		df = new SimpleDateFormat("d/M/yyyy");
	    		date = df.parse(dateString);
	    	}
	    	else if (dateString.matches("([0-9]{2})/([0-9]{1})/([0-9]{2})")) {
	    		df = new SimpleDateFormat("dd/M/yy");
	    		date = df.parse(dateString);
	    	}
	    	else if (dateString.matches("([0-9]{2})/([0-9]{1})/([0-9]{4})")) {
	    		df = new SimpleDateFormat("dd/M/yyyy");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{2})")) {
	    		df = new SimpleDateFormat("dd/MM/yy");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{1})/([0-9]{2})/([0-9]{2})")) {
        	df = new SimpleDateFormat("d/MM/yy");
        	date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{1})-([0-9]{2})-([0-9]{4})")) {
	    		df = new SimpleDateFormat("d-MM-yyyy");
	    		date = df.parse(dateString);
	    	}
	    	else if (dateString.matches("([0-9]{1})-([0-9]{1})-([0-9]{4})")) {
	    		df = new SimpleDateFormat("d-M-yyyy");
	    		date = df.parse(dateString);
	    	}
	    	else if (dateString.matches("([0-9]{2})-([0-9]{1})-([0-9]{4})")) {
	    		df = new SimpleDateFormat("dd-M-yyyy");
	    		date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{2})")) {
        	df = new SimpleDateFormat("dd-MM-yy");
        	date = df.parse(dateString);
	    	}else if (dateString.matches("([0-9]{1})-([0-9]{2})-([0-9]{2})")) {
	    		df = new SimpleDateFormat("d-MM-yy");
	    		date = df.parse(dateString);
	    	}
      
        
        formatteddate = dfOut.format(date);
    }
	    catch ( Exception ex ){
	    	ex.getMessage();
	    }
	    return formatteddate;
	}
	
	private CrRuleConstValue(){
	    throw new AssertionError();
	}
	
	public interface ConstantType {}

	public enum Excel_Rules{
		Clubbing_Rule,
		Nationality_rule,
		Eid_Incentive_Rule,
		Commission_Rules,
		Focused_Product_Rule,
		Deal_Base_Rule
	}
	
	public enum Rule_Engine_Name {
		   Nationality_rule,
		   Eid_Incentive_Rule,
		   Commission_Rules,
		   Focused_Product_Rule,
		   Base_rate_Rule,
		   Clubbing_Rule
		   
		}
	
 public enum StringConstant implements ConstantType {
 	ADMIN("admin"),
 	CONTENT("content"),
 	CARROT_RULE("CARROT_RULE"),
 	CARROT_RULE_XYZ("carrotrule_xyz.com"),
 	CLUBBING_PROJECT_NEW("Project"),
 	RULE_ENGINE("Rule_Engine"),
 	SFOBJECT("sfobject"),
 	PRIMARYKEY("primarykey"),
 	VALUE("value"),
 	FIELD("field"),
 	FLAGDATA("flagdata"),
 	FLAGVALUE("flagvalue"),
 	OUTPUT("OUTPUT"),
 	COUNTER("Counter"),
 	OUTRES("Output"),
 	CLUBBING_FLAG("Clubbing_flag"),
 	EXTERNAL_DATA("EXTERNAL_DATA"),
 	DEFAULT(""),
 	FOCUSSED_NODE("Deal_Base_Rule"),
 	CONSOLIDATOR_NODE("ConsolidatorNode"),
 	OUTPUT_FIELD("OutputField"),
 	COMMISSION("Commission"),
 	COMMISSIONRATE("Commission_Rate"),
 	MULTIPLIER("Multiplier"),
 	INCREMENT("Increment"),
 	FLAT_RATE("Flat Rate"),
 	DEFAULT_NODE("0"),
// 	DEFAULT_CARROT_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/"),
// 	FILE_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/Externaldata/"),
// 	DOWNLOAD_FILE_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/ExternalDataDownload/Download.xls"),
//	CALCULATION_FILE_DOWNLOAD("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/calculationbuilderdownload/CalculationExcelDownload.xls"),
// 	CAL_BUILDER_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/calculationbuilder/"),
 	
// 	Generic_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/Generic/DownloadFile/"),
	Generic_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/Generic/DownloadFile/"),
 	DEFAULT_CARROT_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/"),
 	FILE_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/Externaldata/"),
 	DOWNLOAD_FILE_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/ExternalDataDownload/Download.xls"),

 	CALCULATION_FILE_DOWNLOAD("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/calculationbuilderdownload/CalculationExcelDownload.xls"),
 	CAL_BUILDER_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/calculationbuilder/"),
 	//POST_URL("http://35.221.0.187:8082/portal/servlet/service/CalBuilder.postaddCal"),
 	URL_GET("post_url"),
 	DATE_OF_REGISTRATION("date_of_registration__c"),
 	multiplier("multiplier"),
 	increment("increment"),
 	flat_rate("flat_rate"),
 	UNKNOWN("unknown"),
 	DRL_URL("http://34.74.125.253:8082/drools/callrules/"),
 	PORTAL_URL("http://35.200.169.114:8082/portal/"),
 	RULE_ENGINE_URL("http://35.186.166.22:8082/portal/servlet/service/")
 	;
	 // /home/vil/sling tomcat/apache-tomcat-6.0.35/webapps
 	private String constvalue;
     private StringConstant(String value) {
         this.constvalue = value;
     }
     public String value() {
         return constvalue;
     }
 	
 }
 
   }

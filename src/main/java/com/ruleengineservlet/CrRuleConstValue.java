package com.ruleengineservlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class CrRuleConstValue.
 */
public class CrRuleConstValue {
//CrRuleConstValue.Rule_Engine_Name.
	
	/**
 * Checks if is null string.
 *
 * @param p_text the p text
 * @return true, if is null string
 */
public static boolean isNullString (String p_text){
		if(p_text != null && p_text.trim().length() > 0 && !"null".equalsIgnoreCase(p_text.trim())){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Checks if is blank.
	 *
	 * @param cs the cs
	 * @return true, if is blank
	 */
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
	
	/**
	 * Convert string to date.
	 *
	 * @param dateString the date string
	 * @return the string
	 */
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
	
	/**
	 * Instantiates a new cr rule const value.
	 */
	private CrRuleConstValue(){
	    throw new AssertionError();
	}
	
	/**
	 * The Interface ConstantType.
	 */
	public interface ConstantType {}

	/**
	 * The Enum Excel_Rules.
	 */
	public enum Excel_Rules{
		
		/** The Clubbing rule. */
		Clubbing_Rule,
		
		/** The Nationality rule. */
		Nationality_rule,
		
		/** The Eid incentive rule. */
		Eid_Incentive_Rule,
		
		/** The Commission rules. */
		Commission_Rules,
		
		/** The Focused product rule. */
		Focused_Product_Rule,
		
		/** The Deal base rule. */
		Deal_Base_Rule
	}
	
	/**
	 * The Enum Rule_Engine_Name.
	 */
	public enum Rule_Engine_Name {
		   
   		/** The Nationality rule. */
   		Nationality_rule,
		   
   		/** The Eid incentive rule. */
   		Eid_Incentive_Rule,
		   
   		/** The Commission rules. */
   		Commission_Rules,
		   
   		/** The Focused product rule. */
   		Focused_Product_Rule,
		   
   		/** The Base rate rule. */
   		Base_rate_Rule,
		   
   		/** The Clubbing rule. */
   		Clubbing_Rule
		   
		}
	
 /**
  * The Enum StringConstant is used to access constant variables.
  */
 public enum StringConstant implements ConstantType {
 	
	 /** The admin. */
	 ADMIN("admin"),
 	
	 /** The content. */
	 CONTENT("content"),
 	
	 /** The carrot rule. */
	 CARROT_RULE("CARROT_RULE"),
 	
	 /** The carrot rule xyz. */
	 CARROT_RULE_XYZ("carrotrule_xyz.com"),
 	
	 /** The clubbing project new. */
	 CLUBBING_PROJECT_NEW("Project"),
 	
	 /** The rule engine. */
	 RULE_ENGINE("Rule_Engine"),
 	
	 /** The sfobject. */
	 SFOBJECT("sfobject"),
 	
	 /** The primarykey. */
	 PRIMARYKEY("primarykey"),
 	
	 /** The value. */
	 VALUE("value"),
 	
	 /** The field. */
	 FIELD("field"),
 	
	 /** The flagdata. */
	 FLAGDATA("flagdata"),
 	
	 /** The flagvalue. */
	 FLAGVALUE("flagvalue"),
 	
	 /** The output. */
	 OUTPUT("OUTPUT"),
 	
	 /** The counter. */
	 COUNTER("Counter"),
 	
	 /** The outres. */
	 OUTRES("Output"),
 	
	 /** The clubbing flag. */
	 CLUBBING_FLAG("Clubbing_flag"),
 	
	 /** The external data. */
	 EXTERNAL_DATA("EXTERNAL_DATA"),
 	
	 /** The default. */
	 DEFAULT(""),
 	
	 /** The focussed node. */
	 FOCUSSED_NODE("Deal_Base_Rule"),
 	
	 /** The consolidator node. */
	 CONSOLIDATOR_NODE("ConsolidatorNode"),
 	
	 /** The output field. */
	 OUTPUT_FIELD("OutputField"),
 	
	 /** The commission. */
	 COMMISSION("Commission"),
 	
	 /** The commissionrate. */
	 COMMISSIONRATE("Commission_Rate"),
 	
	 /** The multiplier. */
	 MULTIPLIER("Multiplier"),
 	
	 /** The increment. */
	 INCREMENT("Increment"),
 	
	 /** The flat rate. */
	 FLAT_RATE("Flat Rate"),
 	
	 /** The default node. */
	 DEFAULT_NODE("0"),
// 	DEFAULT_CARROT_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/"),
// 	FILE_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/Externaldata/"),
// 	DOWNLOAD_FILE_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/ExternalDataDownload/Download.xls"),
//	CALCULATION_FILE_DOWNLOAD("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/calculationbuilderdownload/CalculationExcelDownload.xls"),
// 	CAL_BUILDER_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/calculationbuilder/"),
 	
/** The Generic PATH. */
// 	Generic_PATH("/home/ubuntu/apache-tomcat-8.5.31/webapps/carrotruleexcelfiles/Generic/DownloadFile/"),
	Generic_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/Generic/DownloadFile/"),
 	
	 /** The default carrot path. */
	 DEFAULT_CARROT_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/"),
 	
	 /** The file path. */
	 FILE_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/Externaldata/"),
 	
	 /** The download file path. */
	 DOWNLOAD_FILE_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/ExternalDataDownload/Download.xls"),

 	/** The calculation file download. */
	 CALCULATION_FILE_DOWNLOAD("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/calculationbuilderdownload/CalculationExcelDownload.xls"),
 	
	 /** The cal builder path. */
	 CAL_BUILDER_PATH("/home/vil/sling tomcat/apache-tomcat-6.0.35/webapps/carrotruleexcelfiles/calculationbuilder/"),
 	
	 /** The url get. */
	 //POST_URL("http://35.221.0.187:8082/portal/servlet/service/CalBuilder.postaddCal"),
 	URL_GET("post_url"),
 	
	 /** The date of registration. */
	 DATE_OF_REGISTRATION("date_of_registration__c"),
 	
	 /** The multiplier. */
	 multiplier("multiplier"),
 	
	 /** The increment. */
	 increment("increment"),
 	
	 /** The flat rate. */
	 flat_rate("flat_rate"),
 	
	 /** The unknown. */
	 UNKNOWN("unknown"),
 	
	 /** The drl url. */
	 DRL_URL("http://34.74.125.253:8082/drools/callrules/"),
 	
	 /** The portal url. */
	 PORTAL_URL("http://35.200.169.114:8082/portal/"),
 	
	 /** The rule engine url. */
	 RULE_ENGINE_URL("http://35.186.166.22:8082/portal/servlet/service/")
 	;
	 
 	/** The constvalue. */
 	// /home/vil/sling tomcat/apache-tomcat-6.0.35/webapps
 	private String constvalue;
     
     /**
      * Instantiates a new string constant.
      *
      * @param value the value
      */
     private StringConstant(String value) {
         this.constvalue = value;
     }
     
     /**
      * Value.
      *
      * @return the string
      */
     public String value() {
         return constvalue;
     }
 	
 }
 
   }

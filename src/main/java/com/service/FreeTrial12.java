package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONObject;

public class FreeTrial12 {

	public Node getCarrotruleNode(String freetrialstatus, String email, Session session1 ,SlingHttpServletResponse response ) throws IOException, AccessDeniedException, ItemExistsException, ReferentialIntegrityException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException {


		PrintWriter out= response.getWriter();
//		out.println("in CarrotRuleFreeTrial");

		Node contentNode=null;
		Node appserviceNode =null;
		Node appfreetrialNode=null;
		Node emailNode=null;
		Node CarrotruleMainNode=null;

		Node serviceid_userNode=null;
		Node adminserviceidNode=null;
		String adminserviceid="";

		try {
		out=response.getWriter();


//		out.println("freetrialstatus "+freetrialstatus);
//		out.println("email "+email);

		//session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
		if(session1.getRootNode().hasNode("content")) {
		contentNode=session1.getRootNode().getNode("content");
		}else {
		contentNode=session1.getRootNode().addNode("content");
		}
//		out.println("contentNode "+contentNode);

		if(freetrialstatus.equalsIgnoreCase("0")) {

		if(contentNode.hasNode("services")) {
		appserviceNode=contentNode.getNode("services");

//		out.println("appserviceNode "+appserviceNode);

		if(appserviceNode.hasNode("freetrial")) {
		appfreetrialNode=appserviceNode.getNode("freetrial");

//		out.println("appfreetrialNode "+appfreetrialNode);

		if(appfreetrialNode.hasNode("users") && appfreetrialNode.getNode("users").hasNode(email.replace("@", "_"))) {
		emailNode=appfreetrialNode.getNode("users").getNode(email.replace("@", "_"));
//		out.println("emailNode "+emailNode);
		if(emailNode.hasNode("CarrotruleMainNode")) {
		CarrotruleMainNode=emailNode.getNode("CarrotruleMainNode");
		}else {
		CarrotruleMainNode=emailNode.addNode("CarrotruleMainNode");
		}
//		out.println("CarrotruleMainNode "+CarrotruleMainNode);

		}else {
		//emailNode=appfreetrialNode.getNode("users").addNode(email.replace("@", "_"));
		}	
		}else {
		//appfreetrialNode=appserviceNode.addNode("freetrial");
		}	
		}else {
		//appserviceNode=contentNode.addNode("services");
		}
		}else {

//		out.println("in else");

		if(contentNode.hasNode("user") && contentNode.getNode("user").hasNode(email.replace("@", "_"))) {
		emailNode=contentNode.getNode("user").getNode(email.replace("@", "_"));
		if(emailNode.hasNode("services") && emailNode.getNode("services").hasNode("carrotrule") &&
		emailNode.getNode("services").getNode("carrotrule").hasNodes()) {
		NodeIterator itr = emailNode.getNode("services").getNode("carrotrule").getNodes();
		while(itr.hasNext()) {
		adminserviceid=itr.nextNode().getName();
		}	
		}	
		}	
		if((adminserviceid!="") && (!adminserviceid.equals("CarrotRuleFreeTrial"))) {

		if(contentNode.hasNode("services")) {
		appserviceNode=contentNode.getNode("services");
		}else {
		appserviceNode=contentNode.addNode("services");
		}

		if(appserviceNode.hasNode(adminserviceid)) {
		appfreetrialNode=appserviceNode.getNode(adminserviceid);
		}else {
		appfreetrialNode=appserviceNode.addNode(adminserviceid);
		}
		if(appfreetrialNode.hasNode("users")) {
		serviceid_userNode=appfreetrialNode.getNode("users");
		}else {
		serviceid_userNode=appfreetrialNode.addNode("users");
		}
		if(serviceid_userNode.hasNode(email.replace("@", "_"))) {
		emailNode=serviceid_userNode.getNode(email.replace("@", "_"));
		}else {
		emailNode=serviceid_userNode.addNode(email.replace("@", "_"));
		}

		if(emailNode.hasNode("CarrotruleMainNode")) {
		CarrotruleMainNode=emailNode.getNode("CarrotruleMainNode");
		}else {
		CarrotruleMainNode=emailNode.addNode("CarrotruleMainNode");
		}
		}	
		}
		
		//session.save();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		out.println(e.getMessage());
		} 
		session1.save();
		return CarrotruleMainNode;
		}
		public String checkfreetrial(String userid) {
		int expireFlag=0;//35.236.154.164 // 35.221.160.146
		String addr = "http://35.236.154.164:8086/apirest/trialmgmt/trialuser/"+userid+"/CarrotRuleFreeTrial";
		String username = "username";
		String password = "password";
		try{
		URL url = new URL(addr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		conn.connect();
		InputStream in = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String text = reader.readLine();
		System.out.println(text);
		JSONObject obj = new JSONObject(text);

		expireFlag=obj.getInt("expireFlag");
		System.out.println(expireFlag);

		conn.disconnect();
		}catch(Exception ex)
		{
		ex.printStackTrace();
		System.out.println("made it here");
		}
		return expireFlag+"";
		}
}

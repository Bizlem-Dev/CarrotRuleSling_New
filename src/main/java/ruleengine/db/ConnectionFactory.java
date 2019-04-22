package ruleengine.db;


import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

public enum ConnectionFactory {

	CONNECTION;
    private Mongo client = null;
    MongoURI mongoClientURI = new MongoURI("mongodb://127.0.0.1:27017");
    private ConnectionFactory() {
        try {
        	if(client == null){
            client = new Mongo(mongoClientURI);
            System.out.println("Connection to database made");
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
    public Mongo getClient() {
        if (client == null){
			try {
				client = new Mongo(mongoClientURI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        }
        return client;
    }
    public void closeConnection(){
    	try {
			if(null != client){
			client.close();
			client = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
    
    public void closeConnection(DBCollection  collectionName){
    	try {
			if(null != client){
				collectionName.drop();
			client.close();
			client = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}

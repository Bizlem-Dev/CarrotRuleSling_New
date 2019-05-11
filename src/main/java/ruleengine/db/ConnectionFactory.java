package ruleengine.db;


import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

// TODO: Auto-generated Javadoc
/**
 * Class ConnectionFactory is used to make connection with mongoDb.
 */
public enum ConnectionFactory {

	/** The connection. */
	CONNECTION;
    
    /** The client. */
    private Mongo client = null;
    
    /** The mongo client URI. */
    MongoURI mongoClientURI = new MongoURI("mongodb://127.0.0.1:27017");
    
    /**
     * Instantiates a new connection factory.
     */
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



    
    /**
     * Gets the client.
     *
     * @return the client
     */
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
    
    /**
     * Close connection.
     */
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
	
    
    /**
     * Close connection.
     *
     * @param collectionName the collection name
     */
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

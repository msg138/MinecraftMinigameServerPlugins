package com.hiveofthoughts.mc.config;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael George on 3/16/2018.
 */
public class Database {

    /**
     * Associated Variables to ensure no typos are done in getting and setting from Collections in Mongo.
     * */
    public static final String Table_User = "users";
    public static final String Table_ServerConfig = "serverConfig";

    public static final String Field_Name = "name";
    public static final String Field_Value = "value";

    public static final String Field_UUID = "uuid";
    public static final String Field_Username = "username";
    public static final String Field_Permission = "permission";

    private static Database m_instance;

    private static final String m_hostname = "104.168.133.139";

    private static final String m_databaseName = "hotmc";
    private static final String m_authenticationDatabase = "admin";
    private static final String m_username = "root";
    private static final String m_password = "A^wm%f8i5B4:U9yLLX-v~R5S099*7em";
    /** Need to fix database permissions for this account.
    private static final String m_username = "minecraft";
    private static final String m_password = "p17U49_-;L*Z;kl~q6365__hV;0*4EN";
    */
    private MongoClient m_connect = null;

    private MongoDatabase m_database = null;

    // Singleton Database object used for getting and setting data in a database.
    public static Database getInstance(){
        if(m_instance == null)
            try {
                m_instance = new Database();
            }catch(Exception e){
                e.printStackTrace();
                m_instance = null;
            }
        return m_instance;
    }

    private Database(){
        MongoCredential t_cred = MongoCredential.createCredential(m_username, m_authenticationDatabase, m_password.toCharArray());
        MongoClientOptions t_options = MongoClientOptions.builder().build(); // Used if we want to specify other options.
        ServerAddress t_sa = new ServerAddress(m_hostname, 27017);
        m_connect = new MongoClient(t_sa, Arrays.asList(t_cred));
        //MongoClientURI t_uri = new MongoClientURI("mongodb://"+m_username+":"+m_password+":27017/?authSource=" + m_authenticationDatabase);
        //m_connect = new MongoClient(t_uri);
        m_database = m_connect.getDatabase(m_databaseName);
    }

    public void closeConnection(){
        m_connect.close();
        m_instance = null;
    }

    private MongoCollection<Document> getCollection(String a_tableName) throws Exception{
        if(!collectionExists(a_tableName))
            throw new Exception("Trying to access a table that does not exist: " + a_tableName);
        return m_database.getCollection(a_tableName);
    }

    public List<Document> getDocuments(String a_tableName, String a_field, String a_value) throws Exception{
        MongoCollection<Document> t_col = getCollection(a_tableName);
        ArrayList<Document> r_ret = new ArrayList<>();
        MongoIterable<Document> t_res = t_col.find(Filters.eq(a_field, a_value));
        t_res.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document){
                r_ret.add(document);
            }
        });
        return r_ret;
    }
    public Document getDocument(String a_tableName, String a_field, String a_value) throws Exception{
        MongoCollection<Document> t_col = getCollection(a_tableName);
        Document r_ret = null;
        MongoIterable<Document> t_res = t_col.find(Filters.eq(a_field, a_value)).limit(1);
        r_ret = t_res.first();

        return r_ret;
    }

    public boolean insertDocument(String a_tableName, Document a_document) throws Exception{
        MongoCollection<Document> t_col = getCollection(a_tableName);
        t_col.insertOne(a_document);
        return true;
    }

    public boolean insertDocuments(String a_tableName, Document[] a_documents) throws Exception{
        MongoCollection<Document> t_col = getCollection(a_tableName);
        t_col.insertMany(Arrays.asList(a_documents));
        return true;
    }

    public boolean updateDocument(String a_tableName, String a_field, String a_value, String a_fieldChange, String a_valueChange) throws Exception{
        MongoCollection<Document> t_col = getCollection(a_tableName);
        t_col.updateOne(Filters.eq(a_field, a_value), Updates.set(a_fieldChange, a_valueChange));
        return true;
    }
    // Same as updatedocument, though will run on all results found.
    public boolean updateDocuments(String a_tableName, String a_field, String a_value, String a_fieldChange, String a_valueChange) throws Exception{
        MongoCollection<Document> t_col = getCollection(a_tableName);
        t_col.updateMany(Filters.eq(a_field, a_value), Updates.set(a_fieldChange, a_valueChange));
        return true;
    }

    public boolean addCollection(String a_tableName){
        m_database.createCollection(a_tableName);
        return true;
    }

    public boolean collectionExists(String a_tableName){
        for(String t_name : m_database.listCollectionNames())
            if(t_name.equals(a_tableName))
                return true;
        return false;
    }

}

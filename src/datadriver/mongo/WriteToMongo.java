package datadriver.mongo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.bson.Document;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import datadriver.infer.TYPE;

public class WriteToMongo {
	MongoClient mongoClient;
	public WriteToMongo() {
		// TODO Auto-generated constructor stub
		mongoClient = new MongoClient( "localhost" , 27017 );
	}
	public WriteToMongo(String host,int port)
	{
		mongoClient=mongoClient = new MongoClient( host , port );
	}

	public void writeMongo(String database,ArrayList<String> names,ArrayList<Object> values,String collection)
	{
		MongoDatabase db=mongoClient.getDatabase(database);
		MongoCollection<Document> coll=db.getCollection(collection);
		Document document=new Document();
		for(int i=0;i<names.size();i++)
		{
			document.append(names.get(i), values.get(i));
		}
		coll.insertOne(document);

	}
	public void writeValues(ArrayList<String> names,String database,String link,String collection)
	{
		try {
			/*
			 * Reading DBF File
			 */
			InputStream in=new FileInputStream(link);
			DBFReader dr=new DBFReader(in);
			int n=dr.getRecordCount();
			ArrayList<TYPE> temptypes=new ArrayList<>();
			ArrayList<Object> values=new ArrayList<>();
			for(int i=0;i<n;i++)
			{
				Object [] row=dr.nextRecord();
				values.clear();
				for(int j=0;j<row.length;j++)
				{
					values.add(row[j]);
				}
				writeMongo(database, names, values,collection);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return null;
	}
}

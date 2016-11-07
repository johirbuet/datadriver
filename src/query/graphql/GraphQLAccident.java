package query.graphql;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;



import graphql.schema.DataFetcher;
import java.util.Map;

import graphql.GraphQL;
import static graphql.Scalars.GraphQLString;
import static graphql.Scalars.GraphQLInt;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

import static graphql.schema.GraphQLObjectType.newObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphql.schema.DataFetcher;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;

import graphql.schema.GraphQLSchema;

public class GraphQLAccident {
	public static void main(String[] args) {
		GraphQLObjectType accident=newObject().name("Accidents").
				field(newFieldDefinition().name("STATE").dataFetcher(new DataFetcher() {
			@Override
			public Object get(DataFetchingEnvironment env) {
				return ((Accident)env.getSource()).STATE;
			}})
		.type(GraphQLInt).build())
				.field(newFieldDefinition().name("VE_TOTAL").dataFetcher(new DataFetcher() {
			
			@Override
			public Object get(DataFetchingEnvironment env) {
				return ((Accident)env.getSource()).VE_TOTAL;
			}
		}).type(GraphQLInt).build()).build();
		
	  GraphQLObjectType query=newObject().name("allAccidents")
			  .field(newFieldDefinition().name("AllAccidents").dataFetcher(new DataFetcher() {
		
		@Override
		public Object get(DataFetchingEnvironment env) {
			//GraphQLList gl=new GraphQLList(accident);
		    MongoClient mongo = new MongoClient("localhost",27017);
			DB db=mongo.getDB("fars2015");
			DBCollection collection= db.getCollection("accidents");
			BasicDBObject searchkey=new BasicDBObject();
			searchkey.put("STATE", 1);
			DBCursor  cur=collection.find(searchkey);
			Accident ac;
			List<Accident> list=new ArrayList<Accident>();
			while(cur.hasNext())
			{
				ac=new Accident();
				DBObject dob=cur.next();
				ac.STATE=(int)dob.get("STATE");
				ac.VE_TOTAL=(int)dob.get("VE_TOTAL");
				list.add(ac);
			}
			return list;
		}
	}).type(new GraphQLList(accident)).build()).build();
		GraphQLSchema schema = GraphQLSchema.newSchema().query(query).build();
		Map<String, Object> result = (Map<String, Object>)new GraphQL(schema).execute("{}").getData();
		System.out.println(result);

		
	}
	private static class Accident {
		public int STATE;
		public int VE_TOTAL;
		public int getSTATE() {
			return STATE;
		}
		public void setSTATE(int sTATE) {
			STATE = sTATE;
		}
		public int getVE_TOTAL() {
			return VE_TOTAL;
		}
		public void setVE_TOTAL(int vE_TOTAL) {
			VE_TOTAL = vE_TOTAL;
		}
		
	}
}

package query.graphql;

import static graphql.Scalars.GraphQLString;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

import static graphql.schema.GraphQLObjectType.newObject;

import java.util.Map;

import graphql.GraphQL;

import graphql.schema.DataFetcher;

import graphql.schema.DataFetchingEnvironment;

import graphql.schema.GraphQLObjectType;

import graphql.schema.GraphQLSchema;

public class SampleGraphQL {

	public static void main(String[] args) {

		// sub schema to be added to parent schema


GraphQLObjectType testPojo = newObject().name("TestPojo").description("This is a test POJO")
            .field(newFieldDefinition().name("id").dataFetcher(new DataFetcher() {
              @Override
              public Object get(DataFetchingEnvironment environment) {
                return ((TestPojo)environment.getSource()).id;
              }
            }).type(GraphQLString).build())
            .field(newFieldDefinition().name("name").dataFetcher(new DataFetcher() {
              @Override
              public Object get(DataFetchingEnvironment environment) {
                return ((TestPojo)environment.getSource()).name;
              }
            }).type(GraphQLString).build()).build();

		// parent schema

		GraphQLObjectType queryType = newObject().name("helloWorldQuery")

				.field(newFieldDefinition().name(testPojo.getName()).type(testPojo).dataFetcher(new DataFetcher() {

					@Override

					public Object get(DataFetchingEnvironment arg0) {

						Object a = greeting2();
						return a;

					}

				}).build()).build();

		GraphQLSchema schema = GraphQLSchema.newSchema().query(queryType).build();

		Map<String, Object> result = (Map<String, Object>) new GraphQL(schema).execute("{TestPojo {id,name}}").getData();

		//System.out.println(String s:result.keySet());
		for(String s: result.keySet())
		{
			System.out.print(result.get(s));
		}

		// Prints: {TestPojo={id=null, name=null}}

	}

	/**
	 * 
	 * service method 2
	 * 
	 * 
	 * 
	 * @return
	 * 
	 */

	public static TestPojo greeting2() {

		return new TestPojo("1", "Jack");

	}

	// inner pojo

	static class TestPojo {

		public String id;

		public String name;

		TestPojo(String id, String name) {

			this.id = id;

			this.name = name;

		}

	}

}

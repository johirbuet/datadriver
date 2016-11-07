package query.graphql;

import graphql.schema.GraphQLSchema;

public class GraphQLSampleController {
	String s;
	//static GraphQLSchema schema = null;
	 /**
	  * POJO to be returned for service method 2
	  * @author 
	  *
	  */
	 class TestPojo{
		 public String id;
		 public String name;
		 
		 TestPojo(String id, String name){
			 this.id = id;
			 this.name = name;
			 
		 }
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		 
	 }
	
	/**
	 * service method 1
	 * @return
	 */
	public  String greeting1() {
			return "Hello123";
     }
	 
	/**
	 * service method 2
	 * @return
	 */
	public  TestPojo greeting2() {
			return new TestPojo("1","Jack");
	}
}
package datadriver.sample;

import java.util.ArrayList;

import datadriver.infer.ReadDBF;
import datadriver.mongo.WriteToMongo;
import datadriver.mongo.protoToMongooseSchema;

public class Main {
	public static void main(String[] args) {
		ReadDBF rdb=new ReadDBF();
		String link="/Users/mislam/Desktop/Traffic Shared Data/FARS/2015/FARS2015NationalDBF/vehicle.dbf";
		rdb.readFromLocal(link);
		rdb.printSchema();
		rdb.writeToProto("boa.transportation.types");
		//rdb.writeMongSchema();
		//ArrayList<String> names=rdb.getNames();
		//WriteToMongo wm=new WriteToMongo();
		//wm.writeValues(names, "fars2015", link,"accidents");
		//protoToMongooseSchema pm=new protoToMongooseSchema("Accident.proto");
		//System.out.println(pm.getMongooseSchema());
		///pm.writeMongooseSchema();
	}

}

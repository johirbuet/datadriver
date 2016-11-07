package datadriver.protos;

import java.util.ArrayList;

import datadriver.infer.TYPE;

public interface IWriteProto {
	public void write(String packg,String filename,ArrayList<String> names,ArrayList<TYPE> types);
}

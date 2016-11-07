package datadriver.infer;

public interface IDataTypeInfere {
	public TYPE type(String s);
	public boolean verifytype();
	public void loadFromLocal(String link);
	public void loadFromURL(String url);
	public void writeProtoFile(String filename);
	public void printSchema();
	public void writeToMap(String fielName,TYPE fieldType);
	public void writeDataToMap(String fieldName,TYPE fieldType,Object fieldVale);
	
	

}

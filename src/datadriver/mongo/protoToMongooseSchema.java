package datadriver.mongo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class protoToMongooseSchema {
	
	private String protofilename;
	private String mongooseSchemaFilename;
	public protoToMongooseSchema(String protofilename) {
		this.protofilename=protofilename;
	}
	public void writeMongooseSchema()
	{
		mongooseSchemaFilename=protofilename.substring(0, protofilename.indexOf('.'))+".mns";
		File f =new File(mongooseSchemaFilename);
		PrintStream out= System.out;
		try {
			PrintStream ps=new PrintStream(f);
			System.setOut(ps);
			System.out.println(getMongooseSchema());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.setOut(out);
	}
	public String getMongooseSchema()
	{
		File f=new File(protofilename);
		Scanner sc;
		StringBuilder sb=new StringBuilder();
		String schema="";
		try {
			sc = new Scanner(f);
			while(sc.hasNextLine())
			{
				String s=sc.nextLine();
				String [] strs=s.split(" ");
				if(strs.length>=4)
				{
					String type;
					if(strs[1].equals("float") || strs[1].equals("int32"))
					{
						type="Number";
					}
					else
					{
						type="String";
					}
					sb.append(strs[2]+" : { type : " +type+"},\n" );
					
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		schema="{\n"+sb.toString().substring(0, sb.toString().length()-2)+"\n}";
		return schema;
	}

}

package datadriver.protos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import datadriver.infer.TYPE;

public class WriteProto implements IWriteProto {

	File f;
	@Override
	public void write(String packg,String filename, ArrayList<String> names, ArrayList<TYPE> types) {
		File f=new File(filename);
		PrintStream ps;
		try {
			ps = new PrintStream(f);
			System.out.println(names.size());
			PrintStream out=System.out;
			System.setOut(ps);
			System.out.println(writeProtoHeader(packg,filename));
			for(int i=0;i<names.size();i++)
			{
				System.out.println("\t"+"optional "+""+protoType(types.get(i))+" "+names.get(i)+" = "+(i+1)+";");
			}
			System.out.println("}");
			
			System.setOut(out);
			System.out.println("Written to file: "+filename);
			
			return;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Writing failed");
		
	}
	
	public String writeProtoHeader(String pckg,String filename)
	{
		String messageName=filename.substring(0,filename.indexOf('.'));
		return "package"+" "+pckg+";\n message "+messageName+"\n{";
	}
	
	public String protoType(TYPE t)
	{
		String s=null;
		if(t==TYPE.FLOAT)
		{
			s="float";
		}
		if(t==TYPE.INTGER)
		{
			s="int32";
		}
		if(t==TYPE.STRING)
		{
			s="string";
		}
		return s;
	}


}

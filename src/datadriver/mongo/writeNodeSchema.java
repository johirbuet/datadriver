package datadriver.mongo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import datadriver.infer.TYPE;

public class writeNodeSchema {
	public void write(String filename, ArrayList<String> names, ArrayList<TYPE> types) {
		File f=new File(filename);
		PrintStream ps;
		try {
			ps = new PrintStream(f);
			System.out.println(names.size());
			PrintStream out=System.out;
			System.setOut(ps);
			for(int i=0;i<names.size();i++)
			{
				System.out.println(names.get(i)+" : { type : "+mongooseType(types.get(i))+" },");
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
	public String mongooseType(TYPE t)
	{
		String s="";
		if(t==TYPE.FLOAT || t==TYPE.INTGER)
		{
			s="Number";
		}
		else
		{
			s="String";
		}
		return s;
	}
}

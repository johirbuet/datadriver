package datadriver.infer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import org.apache.commons.lang3.text.WordUtils;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import datadriver.mongo.writeNodeSchema;
import datadriver.protos.WriteProto;

public class ReadDBF implements IRead{

	ArrayList<TYPE> types=new ArrayList<>();
	ArrayList<String> names=new ArrayList<>();
	ArrayList<String> Data =new ArrayList<>(); 
	IDataTypeInfere datatype=new DataTypeInfer();
	String link;
	String url;
	Random rand=new Random();
	@Override
	public void readFromUrl(String url) {
	
	}

	@Override
	public void readFromLocal(String link) {
		this.link=link;
		try {
			/*
			 * Reading DBF File
			 */
			InputStream in=new FileInputStream(link);
			DBFReader dr=new DBFReader(in);
			int count=dr.getFieldCount();
			for(int i=0;i<count;i++)
			{
				DBFField df=dr.getField(i);
				names.add(df.getName());
			}
			int n=dr.getRecordCount();
			int ns=dr.getFieldCount();
			ArrayList<TYPE> temptypes=new ArrayList<>();
			ArrayList<Object []> rows=new ArrayList<>();
			for(int i=0;n>10?i<10:i<n;i++)
			{
				Object [] row=dr.nextRecord();
				rows.add(row);
			}

			for(int i=0;i<ns;i++)
			{
				temptypes.clear();
				for(int j=0;n>10?j<10:j<n;j++)
				{
					Object rec=rows.get(j)[i];
					TYPE t=datatype.type(rec.toString());
					temptypes.add(t);
				}
				TYPE major=geType(temptypes);
				types.add(major);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Boyer–Moore majority voting algorithm to find the majority type
	public TYPE geType(ArrayList<TYPE> types)
	{
		TYPE t=null;
		int i=0;
		for(int k=0;k<types.size();k++)
		{
			if(i==0)
			{
				t=types.get(k);
				i++;
			}
			else if(t==types.get(i))
			{
				i++;
			}
			else
			{
				i--;
			}
		}
		return t;
	}
	
	public void printSchema()
	{
		for(int i=0;i<names.size();i++)
		{
			System.out.println(names.get(i)+"\t"+printType(types.get(i)));
		}
	}
	public void writeToProto(String packg)
	{
		int start=link.lastIndexOf("/");
		int end=link.lastIndexOf(".");
		String filename=link.substring(start+1,end);
		filename=filename+".proto";
		filename=WordUtils.capitalize(filename);
		System.out.println(filename);
		WriteProto wp=new WriteProto();
		wp.write(packg,filename, names, types);
	}
	
	public void writeMongSchema()
	{
		int start=link.lastIndexOf("/");
		int end=link.lastIndexOf(".");
		String filename=link.substring(start+1,end);
		filename=filename+".mns";
		filename=WordUtils.capitalize(filename);
		System.out.println(filename);
		writeNodeSchema wn=new writeNodeSchema();
		wn.write(filename, names, types);
	}
	public String printType(TYPE t)
	{
		String s="Could not find Type";
		
		if(t==TYPE.FLOAT)
		{
			s="Float";
		}
		if(t==TYPE.INTGER)
		{
			s="Integer";
		}
		if(t==TYPE.STRING)
		{
			s="String";
		}
		return s;
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
	
	public ArrayList<String> getNames()
	{
		return this.names;
	}
	public String getLink()
	{
		return this.link;
	}

}

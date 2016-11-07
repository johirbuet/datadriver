package datadriver.infer;

public class DataTypeInfer implements IDataTypeInfere{

	
	
	@Override
	public TYPE type(String s) {
		if(isInt(s))
		{
			return TYPE.INTGER;
		}
		else if(isFloat(s))
		{
			return TYPE.FLOAT;
		}
		return TYPE.STRING;
	}
	public boolean isFloat(String s)
	{
		try {
			int n=s.indexOf('.');
			if(n>=1)
			{
				String prec=s.substring(n+1);
				if(Integer.parseInt(prec)==0)
				{
					s=s.substring(0, n);
				}
			}
			Float float1=Float.parseFloat(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean isInt(String s)
	{
		try {
			int n=s.indexOf('.');
			if(n>=1)
			{
				String prec=s.substring(n+1);
				if(Integer.parseInt(prec)==0)
				{
					s=s.substring(0, n);
				}
			}
		
			int i=Integer.parseInt(s);
		} catch (Exception e) {
			return false;
			}
		return true;
	}

	@Override
	public boolean verifytype() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadFromLocal(String link) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFromURL(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeProtoFile(String filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printSchema() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToMap(String fielName, TYPE fieldType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDataToMap(String fieldName, TYPE fieldType, Object fieldVale) {
		// TODO Auto-generated method stub
		
	}
}

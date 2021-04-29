
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private static final String split = "#";
	
	Client(String ip, int port) throws IOException{
		sock = new Socket(ip,port);
		in = new BufferedReader(
		new InputStreamReader(sock.getInputStream( )));
		out = new PrintWriter(sock.getOutputStream(), true);
	}
	
	public Production productionFindById(Long id) {
		var query = "ProductionFindById"+split+id.toString();
		out.println(query);
		String response = "";
		try {
			response = in.readLine();
			return new Production(id,response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ProductionGroup productionGroupFindByName(String name) {
		var query = "ProductionGroupFindByName"+split+name;
		out.println(query);
		String response = "";
		try {
			response = in.readLine();
			String[] fields = response.split(split);
			var id = Long.parseLong(fields[0]);
			var productionid = Long.parseLong(fields[1]);
			var parameter = Long.parseLong(fields[3]);
			return new ProductionGroup(id,productionid,name,parameter);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Production productionFindByName(String name) {
		var query = "ProductionFindByName"+split+name;
		out.println(query);
		try {
			var response = Long.parseLong(in.readLine());
			return new Production(response,name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean productionGroupUpdate(ProductionGroup productionGroup) {
		var query = "ProductionGroupUpdate"+split+productionGroup.getId().toString()+
				"#"+productionGroup.getProductionid().toString()+"#"+productionGroup.getName()
				+"#"+ProductionGroup.getParameter().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean productionUpdate(Production production) {
		var query = "ProductionUpdate"+split+production.getId().toString()+
				"#"+production.getName();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean productionGroupInsert(ProductionGroup productionGroup) {
		var query = "ProductionGroupInsert"+
				"#"+productionGroup.getProductionid().toString()+"#"+productionGroup.getName()
				+"#"+productionGroup.getParameter().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean productionInsert(Production production) {
		var query = "ProductionInsert"+
				"#"+production.getName();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean productionDelete(Production production) {
		var query = "ProductionDelete"+split+production.getId().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean productionGroupDelete(Production production) {
		var query = "ProductionDelete"+split+production.getId().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Production> productionAll(){
		var query = "ProductionAll";
		out.println(query);
		var list = new ArrayList<Production>();
		try {
			var response = in.readLine();
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=2) {
				var id = Long.parseLong(fields[i]);
				var name = fields[i+1];
				list.add(new Production(id,name));
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Production> productionAll(){
		var query = "ProductionAll";
		out.println(query);
		var list = new ArrayList<Production>();
		try {
			var response = in.readLine();
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=4) {
				var id = Long.parseLong(fields[i]);
				var productionid = Long.parseLong(fields[i+1]);
				var name = fields[i+2];
				var parameter = Long.parseLong(fields[i+3]);
				list.add(new Production(id,productionid,name,parameter));
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Production> productionFindByProductionid(Long idc){
		var query = "ProductionFindByProductionid"+split+idc.toString();
		out.println(query);
		var list = new ArrayList<Production>();
		try {
			var response = in.readLine();
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=4) {
				var id = Long.parseLong(fields[i]);
				var productionid = Long.parseLong(fields[i+1]);
				var name = fields[i+2];
				var parameter = Long.parseLong(fields[i+3]);
				list.add(new Production(id,productionid,name,parameter));
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void disconnect(){
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
	}
}

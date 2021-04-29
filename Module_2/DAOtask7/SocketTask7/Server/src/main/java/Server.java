import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket server = null;
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		while (true) {
			sock = server.accept();
			in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
			out = new PrintWriter(sock.getOutputStream(), true);
			while (processQuery());
		}
	}
	
	private boolean processQuery() {
		int result = 0;
		String response = "";
		try {
			String query = in.readLine();
			if (query==null) 
				return false;
			
			String[] fields = query.split("#");
			if (fields.length == 0){
				return true;
			} else {
				var action = fields[0];
				Production production;
				ProductionGroup productionGroup;
				
				System.out.println(action);
				
				switch(action) {
				case "ProductionFindById":
					var id = Long.parseLong(fields[1]);
					production = ProductionDAO.findById(id);
					response = production.getName();
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionGroupFindByProductionid":
					id = Long.parseLong(fields[1]);
					var list = ProductionGroupDAO.findByProductionId(id);
					var str = new StringBuilder();
					for(ProductionGroup c: list) {
						str.append(c.getId());
						str.append("#");
						str.append(c.getProductionid());
						str.append("#");
						str.append(c.getName());
						str.append("#");
						str.append(c.getParameter());
						str.append("#");
					}
					response = str.toString();
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionGroupFindByName":
					var name = fields[1];
					productionGroup = ProductionGroupDAO.findByName(name);
					response = productionGroup.getId().toString()+"#"+productionGroup.getProductionid().toString()+"#"+productionGroup.getName()+"#"+productionGroup.getparameter().toString();
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionFindByName":
					name = fields[1];
					production = ProductionDAO.findByName(name);
					response = production.getId().toString();
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionGroupUpdate":
					id = Long.parseLong(fields[1]);
					var productionid = Long.parseLong(fields[2]);
					name = fields[3];
					var parameter = Long.parseLong(fields[4]);
					productionGroup = new ProductionGroup(id,productionid,name,parameter);
					if(ProductionGroupDAO.update(productionGroup))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionUpdate":
					id = Long.parseLong(fields[1]);
					name = fields[2];
					production = new Production(id,name);
					if (ProductionDAO.update(production))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionGroupInsert":
					productionid = Long.parseLong(fields[1]);
					name = fields[2];
					parameter = Long.parseLong(fields[3]);
					productionGroup = new ProductionGroup((long) 0,productionid,name,parameter);
					if(ProductionGroupDAO.insert(productionGroup))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionInsert":
					name = fields[1];
					production = new Production();
					production.setName(name);
					
					System.out.println(name);
					
					if(ProductionDAO.insert(production))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionGroupDelete":
					id = Long.parseLong(fields[1]);
					productionGroup = new ProductionGroup();
					productionGroup.setId(id);
					if(ProductionGroupDAO.delete(productionGroup))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionDelete":
					id = Long.parseLong(fields[1]);
					production = new Production();
					production.setId(id);
					if(ProductionDAO.delete(production))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionGroupAll":
					var list1 = ProductionGroupDAO.findAll();
					str = new StringBuilder();
					for(ProductionGroup c: list1) {
						str.append(c.getId());
						str.append("#");
						str.append(c.getProductionid());
						str.append("#");
						str.append(c.getName());
						str.append("#");
						str.append(c.getParameter());
						str.append("#");
					}
					response = str.toString();
					System.out.println(response);
					out.println(response);
					break;
				case "ProductionAll":
					var list2 = ProductionDAO.findAll();
					str = new StringBuilder();
					for(Production c: list2) {
						str.append(c.getId());
						str.append("#");
						str.append(c.getName());
						str.append("#");
					}
					response = str.toString();
					System.out.println(response);
					out.println(response);
					break;
				}
			}
			
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	public static void main(String[] args) {
		try {
			Server srv = new Server();
			srv.start(12345);
		} catch(IOException e) {
			System.out.println("Возникла ошибка");
		}
	}
}

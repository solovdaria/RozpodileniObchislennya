package com.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.cli.Actions;
import com.cli.CommandLineInterface;
import com.entity.Card;
import com.entity.User;
import com.entity.Phone;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/* Normally all these methods should not throw RemoteException,
 * but for the sake of less copy-pasting cli I've made one interface Actions
 * for both socket and rmi tasks, and RemoteException is needed for rmi to work*/
public class SocketClientTask7 implements Actions{
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public SocketClientTask7(String ip, int port) throws IOException{
		// establish connection
		sock = new Socket(ip,port);
		// get input/output streams
		in = new BufferedReader(
		new InputStreamReader(sock.getInputStream( )));
		out = new PrintWriter(sock.getOutputStream(), true);
	}
	
	@Override
	public void createUser(User e) throws RemoteException {
		var query = "cO#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public User readUser(long id) throws RemoteException {
		var query = "rO#"+id;
		out.println(query);
		try {
			return new Gson().fromJson(in.readLine(), User.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User e) throws RemoteException {
		var query = "uO#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public void deleteUser(long id) throws RemoteException {
		var query = "dO#"+id; 
		out.println(query);
	}

	@Override
	public void createPhone(Phone e) throws RemoteException {
		var query = "cP#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public Phone readPhone(long id) throws RemoteException {
		var query = "rP#"+id; 
		out.println(query);
		try {
			return new Gson().fromJson(in.readLine(), Phone.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePhone(Phone e) throws RemoteException {
		var query = "uP#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public void deletePhone(long id) throws RemoteException {
		var query = "dP#"+id; 
		out.println(query);
	}

	@Override
	public void createCard(Card e) throws RemoteException {
		var query = "cC#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public Card readCard(long userid, long phoneid) throws RemoteException {
		var query = "rC#"+userid+"#"+phoneid;
		out.println(query);
		try {
			return new Gson().fromJson(in.readLine(), Card.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateCard(Card e) throws RemoteException {
		var query = "uC#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public void deleteCard(long userid, long phoneid) throws RemoteException {
		var query = "dC#"+Userid+"#"+phoneid;
		out.println(query);
	}

	@Override
	public String f1(long id) throws RemoteException {
		var query = "f1#"+id; 
		out.println(query);
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Long> f2(long id) throws RemoteException {
		var query = "f2#"+id; 
		out.println(query);
		
		Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
		try {
			return new Gson().fromJson(in.readLine(), ListType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Long> f3(long id) throws RemoteException {
		var query = "f3#"+id; 
		out.println(query);
		
		Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
		try {
			return new Gson().fromJson(in.readLine(), ListType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void f4() throws RemoteException {
		var query = "f4"; 
		out.println(query);		
	}

	@Override
	public void f5(long id, long q) throws RemoteException {
		var query = "f5#"+id+"#"+q; 
		out.println(query);		
	}
	
	@Override
	public List<Long> f6(long c, long q) throws RemoteException {
		var query = "f6#"+c+"#"+q; 
		out.println(query);
		
		Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
		try {
			return new Gson().fromJson(in.readLine(), ListType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		var client = new SocketClientTask7("localhost",12345);
		var cli = new CommandLineInterface(client);
		cli.interact_blin_gejidgjididi(args);
	}
	
}

package com.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dao.CardDAO;
import com.dao.UserDAO;
import com.dao.PhoneDAO;
import com.entity.*;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

public class SocketServerTask7 {
	public static final int THREADS = 5;
	
	private ServerSocket server = null;
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		sock = server.accept();
		in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
		out = new PrintWriter(sock.getOutputStream(), true);
		ExecutorService agent = Executors.newFixedThreadPool(THREADS);
		while (true) {
			String query = in.readLine();
			agent.execute(new processQuery(out,query));
		}
	}
	
	@AllArgsConstructor
	private static class processQuery implements Runnable {
		private PrintWriter out;
		private String query;

		@Override
		public void run() {
			if (query == null) 
				return;
			String[] fields = query.split("#");
			if (fields.length == 0)
				return;
			
			var action = fields[0];
			
			String response = "";
			switch (action) {
			case "cO":
				var user = new Gson().fromJson(fields[1], User.class);
				UserDAO.insert(user);
				break;
			case "rO":
				long id = Long.parseLong(fields[1]);
				user = UserDAO.findById(id);
				response = new Gson().toJson(user);
				break;
			case "uO":
				user = new Gson().fromJson(fields[1], User.class);
				UserDAO.update(user);
				break;
			case "dO":
				id = Long.parseLong(fields[1]);
				UserDAO.delete(id);
				break;
			
			case "cP":
				var phone = new Gson().fromJson(fields[1], Phone.class);
				PhoneDAO.insert(phone);
				break;
			case "rP":
				id = Long.parseLong(fields[1]);
				phone = PhoneDAO.findById(id);
				response = new Gson().toJson(phone);
				break;
			case "uP":
				phone = new Gson().fromJson(fields[1], Phone.class);
				PhoneDAO.update(phone);
				break;
			case "dP":
				id = Long.parseLong(fields[1]);
				PhoneDAO.delete(id);
				break;
			
			case "cC":
				var cont = new Gson().fromJson(fields[1], Card.class);
				CardDAO.insert(cont);
				break;
			case "rC":
				id = Long.parseLong(fields[1]);
				var aux = Long.parseLong(fields[2]);
				cont = CardDAO.findById(id,aux);
				response = new Gson().toJson(cont);
				break;
			case "uC":
				cont = new Gson().fromJson(fields[1], Card.class);
				CardDAO.update(cont);
				break;
			case "dC":
				id = Long.parseLong(fields[1]);
				aux = Long.parseLong(fields[2]);
				CardDAO.delete(id,aux);
				break;
			
			case "f1":
				id = Long.parseLong(fields[1]);
				response = UserDAO.getPrettyInfo(id);
				break;
			case "f2":
				id = Long.parseLong(fields[1]);
				var list = UserDAO.findByPhoneid(id);
				response = new Gson().toJson(list);
				break;
			case "f3":
				id = Long.parseLong(fields[1]);
				list = UserDAO.findByNotPhoneidAndToday(id);
				response = new Gson().toJson(list);
				break;
			case "f4":
				UserDAO.placeTodayUser();
				break;
			case "f5":
				id = Long.parseLong(fields[1]);
				var q = Long.parseLong(fields[2]);
				UserDAO.deleteSpecial(id, q);
				break;
			case "f6":
				var p = Long.parseLong(fields[1]);
				q = Long.parseLong(fields[2]);
				list = UserDAO.filterByCostAndNumber(p, q);
				response = new Gson().toJson(list);
				break;
			}
			out.println(response);
		}
		
	}
	
	public static void main(String[] args) {
		try {
			SocketServerTask7 srv = new SocketServerTask7();
			srv.start(12345);
		} catch(IOException e) {
			System.out.println("Error");
		}
	}
}

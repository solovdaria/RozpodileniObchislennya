package com.rmi;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.dao.CardDAO;
import com.dao.UserDAO;
import com.dao.PhoneDAO;
import com.entity.Card;
import com.entity.User;
import com.entity.Phone;

public class RmiServerObject extends UnicastRemoteObject implements RmiServerTask7 {

	protected RmiServerObject() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createUser(User e) throws RemoteException {
		UserDAO.insert(e);
	}

	@Override
	public User readUser(long id) throws RemoteException {
		return UserDAO.findById(id);
	}

	@Override
	public void updateUser(User e) throws RemoteException {
		UserDAO.update(e);
	}

	@Override
	public void deleteUser(long id) throws RemoteException {
		UserDAO.delete(id);
	}

	@Override
	public void createPhone(Phone e) throws RemoteException {
		PhoneDAO.insert(e);
	}

	@Override
	public Phone readPhone(long id) throws RemoteException {
		return PhoneDAO.findById(id);
	}

	@Override
	public void updatePhone(Phone e) throws RemoteException {
		PhoneDAO.update(e);
	}

	@Override
	public void deletePhone(long id) throws RemoteException {
		PhoneDAO.delete(id);
	}

	@Override
	public void createCard(Card e) throws RemoteException {
		CardDAO.insert(e);
	}

	@Override
	public Card readCard(long userid, long phoneid) throws RemoteException {
		return CardDAO.findById(userid, phoneid);
	}

	@Override
	public void updateCard(Card e) throws RemoteException {
		CardDAO.update(e);
	}

	@Override
	public void deleteCard(long userid, long phoneid) throws RemoteException {
		CardDAO.delete(userid, phoneid);
	}

	@Override
	public String f1(long id) throws RemoteException {
		return UserDAO.getPrettyInfo(id);
	}

	@Override
	public List<Long> f2(long id) throws RemoteException {
		return UserDAO.findByPhoneid(id);
	}

	@Override
	public List<Long> f3(long id) throws RemoteException {
		return UserDAO.findByNotPhoneidAndToday(id);
	}

	@Override
	public void f4() throws RemoteException {
		UserDAO.placeTodayUser();
	}

	@Override
	public void f5(long id, long q) throws RemoteException {
		UserDAO.deleteSpecial(id, q);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		var bck = new RmiServerObject();
		Registry registry = LocateRegistry.createRegistry(123);
		registry.rebind("Exam", bck);
		System.out.println("Server started!");
	}

	@Override
	public List<Long> f6(long c, long q) throws RemoteException {
		return UserDAO.filterByCostAndNumber(c, q);
	}
	
}

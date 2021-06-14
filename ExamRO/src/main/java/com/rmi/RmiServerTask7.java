package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cli.Actions;
import com.entity.Card;
import com.entity.User;
import com.entity.Phone;

public interface RmiServerTask7 extends Remote, Actions{
	public void createUser(User e) throws RemoteException;
	public User readUser(long id) throws RemoteException;
	public void updateUser(User e) throws RemoteException;
	public void deleteUser(long id) throws RemoteException;
	
	public void createPhone(Phone e) throws RemoteException;
	public Phone readPhone(long id) throws RemoteException;
	public void updatePhone(Phone e) throws RemoteException;
	public void deletePhone(long id) throws RemoteException;
	
	public void createCard(Card e) throws RemoteException;
	public Card readCard(long userid, long phoneid) throws RemoteException;
	public void updateCard(Card e) throws RemoteException;
	public void deleteCard(long userid, long phoneid) throws RemoteException;
	
	public String f1(long id) throws RemoteException;
	public List<Long> f2(long id) throws RemoteException;
	public List<Long>  f3(long id) throws RemoteException;
	public void f4() throws RemoteException;
	public void f5(long id, long q) throws RemoteException;
}

package com.cli;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Card;
import com.entity.User;
import com.entity.Phone;

public interface Actions {
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
	public List<Long> f6(long c, long q) throws RemoteException;
}

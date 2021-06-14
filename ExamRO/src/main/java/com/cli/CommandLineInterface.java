package com.cli;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Scanner;

import com.entity.Card;
import com.entity.User;
import com.entity.Phone;

public class CommandLineInterface {
	Scanner in = new Scanner(System.in);
	Actions act;

	public CommandLineInterface(Actions act) {
		super();
		this.act = act;
	}

	public void interact_blin_gejidgjididi(String[] args) throws RemoteException{
		int choice = 100000;
		long aux;
		String str;
		while(choice > 0) {
			System.out.println("Choose option:\n"
					+ "1 - create User\n"
					+ "2 - read User\n"
					+ "3 - update User\n"
					+ "4 - delete User\n"
					+ "5 - create Phone\n"
					+ "6 - read Phone\n"
					+ "7 - update Phone\n"
					+ "8 - delete Phone\n"
					+ "9 - create Card\n"
					+ "10 - read Card\n"
					+ "11 - update Card\n"
					+ "12 - delete Card\n"
					+ "13 - get all information about User\n"
					+ "14 - get all Users with Phone\n"
					+ "15 - get today's Users without Phone\n"
					+ "16 - place new User with the today's phones\n"
					+ "17 - delete Users with studid conditions\n"
					+ "18 - filter Users by cost and phones number\n"
					+ "anything else - FINALLY EXIT"
					);
			choice = in.nextInt();
			switch (choice) {
			case 1:
				var millis = System.currentTimeMillis();
				var user = new User();
				user.setDate(new Date(millis));
				act.createUser(user);
				System.out.println("Ok, creating new user, its date is today!");
				break;
			case 2:
				System.out.println("Enter id:");
				aux = in.nextLong();
				user = act.readUser(aux);
				System.out.println(user);
				break;
			case 3:
				millis = System.currentTimeMillis();
				System.out.println("Enter id:");
				aux = in.nextLong();
				user = new User();
				user.setId(aux);
				user.setDate(new Date(millis));
				System.out.println("Ok, updating this user, its date is today!");
				break;
			case 4:
				System.out.println("Enter id:");
				aux = in.nextLong();
				act.deleteUser(aux);
				System.out.println("Deleting...");
				break;
			case 5:			
				var phone = new Phone();
				System.out.println("Enter name:");
				str = in.next();
				phone.setName(str);
				System.out.println("Enter desription:");
				str = in.next();
				phone.setDescription(str);
				System.out.println("Enter price:");
				aux = in.nextLong();
				phone.setPrice(aux);
				act.updatePhone(phone);
				act.createPhone(phone);
				System.out.println("Creating new phone...");
				break;
			case 6:
				System.out.println("Enter id:");
				aux = in.nextLong();
				phone = act.readPhone(aux);
				System.out.println(phone);
				break;
			case 7:
				System.out.println("Enter id:");
				phone = new Phone();
				aux = in.nextLong();
				phone.setId(aux);
				System.out.println("Enter name:");
				str = in.next();
				phone.setName(str);
				System.out.println("Enter desription:");
				str = in.next();
				phone.setDescription(str);
				System.out.println("Enter price:");
				aux = in.nextLong();
				phone.setPrice(aux);
				act.updatePhone(phone);
				System.out.println("Updating phone...");
				break;
			case 8:
				System.out.println("Enter id:");
				aux = in.nextLong();
				act.deletePhone(aux);
				System.out.println("Deleting...");
				break;
			case 9:
				System.out.println("Enter userid, phoneid, number:");
				var cont = new Card();
				aux = in.nextLong();
				cont.setUserid(aux);
				aux = in.nextLong();
				cont.setPhoneid(aux);
				aux = in.nextLong();
				cont.setNumber(aux);
				act.createCard(cont);
				System.out.println("Adding phone to user...");
				break;
			case 10:
				System.out.println("Enter userid, phoneid:");
				aux = in.nextLong();
				var aux2 = in.nextLong();
				cont = act.readCard(aux,aux2);
				System.out.println(cont);
				break;
			case 11:
				System.out.println("Enter userid, phoneid, number:");
				cont = new Card();
				aux = in.nextLong();
				cont.setuserid(aux);
				aux = in.nextLong();
				cont.setPhoneid(aux);
				aux = in.nextLong();
				cont.setNumber(aux);
				act.updateCard(cont);
				System.out.println("Updating user details...");
				break;
			case 12:
				System.out.println("Enter userid, phoneid:");
				aux = in.nextLong();
				aux2 = in.nextLong();
				act.deleteCard(aux,aux2);
				System.out.println("Deleting...");
				break;
			case 13:
				System.out.println("Enter positive number, I don't remember what for:");
				aux = in.nextLong();
				System.out.println(act.f1(aux));
				break;
			case 14:
				System.out.println("Enter positive number, I don't remember what for:");
				aux = in.nextLong();
				var list = act.f2(aux);
				System.out.println(list);
				break;
			case 15:
				System.out.println("Enter positive number, I don't remember what for:");
				aux = in.nextLong();
				list = act.f3(aux);
				System.out.println(list);
				break;
			case 16:
				System.out.println("Ok");
				act.f4();
				break;
			case 17:
				System.out.println("Enter 2 positive numbers, I don't remember what for:");
				aux = in.nextLong();
				aux2 = in.nextLong();
				act.f5(aux, aux2);
				System.out.println("Ok");
				break;
			case 18:
				System.out.println("Enter 2 positive numbers, I don't remember what for:");
				aux = in.nextLong();
				aux2 = in.nextLong();
				list = act.f6(aux,aux2);
				System.out.println(list);
				break;
			default:
				return;
			}
		}
	}

}

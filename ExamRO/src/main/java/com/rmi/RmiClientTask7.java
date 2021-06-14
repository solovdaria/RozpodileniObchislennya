package com.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.cli.CommandLineInterface;

public class RmiClientTask7 {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, NotBoundException {
		String url = "//localhost:123/Exam";
		var server = (RmiServerTask7) Naming.lookup(url);
		System.out.println("RMI object found");
		var cli = new CommandLineInterface(server);
		cli.interact_blin_gejidgjididi(args);
	}
}

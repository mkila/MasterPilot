package fr.umlv.main;

import fr.umlv.main.Menu;

public class Main {

	/**
	 * @author Baulamon Cedric - Tran Minh-Duc
	 * Start of the application 
	 *
	 **/
	public static void main(String[] args) {
		Menu m= new Menu();
		if(args.length==0)
			m.printMenu(null);
		else
			m.printMenu(args[1]);
	}

}

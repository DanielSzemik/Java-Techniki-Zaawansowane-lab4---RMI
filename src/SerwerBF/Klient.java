package SerwerBF;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Klient {
    private Klient() {}
    static List<OS> listaSerwerow;
    static I instancja = null;
    static Random rand = new Random();

    public static void main(String[] args) {
    	ISpis spis;
    	Registry registry = null;
        String host = (args.length < 1) ? null : args[0];
        try {
        	registry = LocateRegistry.getRegistry(1888);
        	spis = (ISpis) registry.lookup("Spis");
        	listaSerwerow = spis.getServers();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return;
        }
        System.out.println("Witam w programie plecakRMI 1.1, wpisz K aby wyświetlić listę komend");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String komenda = "";
        while(true) {
        	try {
        		listaSerwerow = spis.getServers();
        		komenda = reader.readLine();
        	}catch (Exception e) {
        		e.printStackTrace();
        		return;
        	}
        	if (komenda.equals("K"))
        		PrintCommands();
        	else if (komenda.equals("WS"))
        		DisplayServers();
        	else if (komenda.equals("I"))
        		GenInstance();
        	else if (komenda.startsWith("R_")) {
				try {	
					int nr = Integer.parseInt(komenda.substring(komenda.indexOf("[")+1,komenda.indexOf("]")));	
					ISerwer serv = (ISerwer) registry.lookup(listaSerwerow.get(nr).nazwaS);
					S solution = serv.solve(instancja);
					solution.wyswwynik(instancja.getlist());
				} catch(NumberFormatException e) {
					System.out.println("Zła komenda (wpisz K aby uzyskać listę komend)");
				} catch(IndexOutOfBoundsException e) {
					System.out.println("Zła komenda (wpisz K aby uzyskać listę komend)");
				} catch(NullPointerException e) {
					System.out.println("Nie istnieje instancja");
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
        	}
        	else System.out.println("Zła komenda (wpisz K aby uzyskać listę komend)");
        }
    }
    
    static void DisplayServers() {
    	for(int i=0;i<listaSerwerow.size();i++)
    		System.out.println("Serwer("+ i + "): Nazwa - " + listaSerwerow.get(i).nazwaS + " Opis "
    				+ listaSerwerow.get(i).opisAlg);
    }
    static S GetSolution() {
    	return null;
    }
    
    static void PrintCommands() {
    	System.out.print("WS - Wyświetl serwery wraz z informacjami o nich\n"
    			+ "I - Wygeneruj instancję problemu plecakowego i wyświetl ją w konsoli\n"
    			+ "R_[numerSerweru] - Rozwiąż obecnie wygenerowaną instancję używając podany numer serweru, "
    			+ "i wyświetl wynik\n"
    			+ "K - Wyświetl komendy\n");
    }
    static void GenInstance() {
		int nr_of_items = (3)+2;
		List<Item> rzeczy = new ArrayList<Item>();
		for (int i=0;i<nr_of_items;i++)
		{
			int ItWeight = rand.nextInt(10) + 1;
			int ItValue = rand.nextInt(10) + 1;
			rzeczy.add(new Item(ItValue,ItWeight,i+1));
			System.out.println("Przedmiot " + i + ": Waga - " + ItWeight + " Wartość: " + ItValue);
		}
		int max_weight = (rand.nextInt(3)+1) * nr_of_items;
		System.out.println("Pojemność plecaka: " + max_weight);
		instancja = new I(rzeczy, max_weight);
    }
    
}

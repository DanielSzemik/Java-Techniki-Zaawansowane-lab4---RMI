package SerwerBF;

//bool register(OS) rejestracja serweru //OS - Stringi Nazwa i Opis algorytmu
//S solve(I) //I - instancja, S - rozwiazanie

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;;

public class SerwerGreedy implements ISerwer{
	public static void main(String[] args) { 
		System.out.println("Serwer Greedy uruchomiony");
        try {
            SerwerGreedy obj = new SerwerGreedy();
            ISerwer stub = (ISerwer) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry;
            try {
            	registry = LocateRegistry.createRegistry(1888);
            }catch (Exception e) {
            	registry = LocateRegistry.getRegistry(1888);
            }
            
            registry.bind("Greedy", stub);
            OS os = new OS("Greedy","Algorytm zachłanny");
            ISpis spis = (ISpis) registry.lookup("Spis");
            boolean czyDodawanieDoSpisuPowiodloSie = spis.register(os);
            if (czyDodawanieDoSpisuPowiodloSie)
            	System.out.println("Dodawanie do spisu sie powiodlo, serwer gotowy");
            else
            	System.out.println("Dodawanie sie nie powiodlo");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}
	@Override
	public S solve(I inst) throws RemoteException {
		System.out.println("Rozwiązywanie Problemu...");
		int totalweight = 0;
		float totalvalue = 0;
		List<Item> posortowanalista = new ArrayList<Item>(inst.getlist());
		Collections.sort(posortowanalista, new ItemComparator());
		int bestrozw[] = new int[posortowanalista.size()];
		
		for(int i=0;i<posortowanalista.size();i++)
		{
			if (totalweight + posortowanalista.get(i).getWeight() <= inst.getpojemnosc())
			{
				totalweight += posortowanalista.get(i).getWeight();
				totalvalue += posortowanalista.get(i).getValue();
				bestrozw[posortowanalista.get(i).getNr()-1]=1;
			}
		}
		S solution = new S(bestrozw);
		System.out.println("Problem Rozwiązany");
		return solution;
	}
	public class ItemComparator implements Comparator<Item>{
		public int compare(Item item1, Item item2) {
			return (int) (item1.getValue()/item1.getWeight() - item1.getValue()/item1.getWeight());
		}
	}
}

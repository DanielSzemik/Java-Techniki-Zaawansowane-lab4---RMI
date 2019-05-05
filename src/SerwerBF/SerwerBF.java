package SerwerBF;

//bool register(OS) rejestracja serweru //OS - Stringi Nazwa i Opis algorytmu
//S solve(I) //I - instancja, S - rozwiazanie

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class SerwerBF implements ISerwer{
	public static void main(String[] args) { 
		System.out.println("Serwer BruteForce uruchomiony");
        try {
            SerwerBF obj = new SerwerBF();
            ISerwer stub = (ISerwer) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry;
            try {
            	registry = LocateRegistry.createRegistry(1888);
            }catch (Exception e) {
            	registry = LocateRegistry.getRegistry(1888);
            }
            
            registry.bind("BruteForce", stub);
            OS os = new OS("BruteForce","Algorytm Przegladu zupelnego");
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
		System.out.println("Rozwiązywanie Problemu..");
		int totalweight = 0;
		float totalvalue = 0;
		float maxvalue = 0;
		int wPlecaku[] = new int[inst.getlist().size()];
		int bestrozw[] = new int[inst.getlist().size()];
		
		for(int i=wPlecaku.length-1;i>=0;i--)
		{
			if(wPlecaku[i] == 1)
			{
				wPlecaku[i] = 0;
			}
			else
			{
				wPlecaku[i] = 1;
				for(int j = 0;j<inst.getlist().size();j++)
				{
					if (wPlecaku[j]==1)
					{
						totalweight += inst.getlist().get(j).getWeight();
						totalvalue += inst.getlist().get(j).getValue();
					}
				}
				if (totalvalue > maxvalue && totalweight <= inst.getpojemnosc())
				{
					bestrozw=Arrays.copyOf(wPlecaku,wPlecaku.length);
					maxvalue = totalvalue;
				}
				totalweight = 0;
				totalvalue = 0;
				i=wPlecaku.length;
			}
		}
		S solution = new S(bestrozw);
		System.out.println("Problem Rozwiązany");
		return solution;
	}
}

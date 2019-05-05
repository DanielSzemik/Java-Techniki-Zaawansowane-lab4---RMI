package SerwerBF;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class Spis implements ISpis {
	List<OS> listaSerwerow = new ArrayList<OS>();
	public static void main(String[] args) { 
        try {
            Spis obj = new Spis();
            ISpis stub = (ISpis) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry;
            try {
            	registry = LocateRegistry.createRegistry(1888);
            }catch (Exception e) {
            	registry = LocateRegistry.getRegistry(1888);
            }
            
            registry.bind("Spis", stub);

            System.out.println("Spis gotowy");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}
	@Override
	public List<OS> getServers(){
		System.out.println("Wysłany spis serwerów");
		return listaSerwerow;
		//return null;
	}
	
	@Override
	public boolean register(OS id) {
		try {
			listaSerwerow.add(id);
			System.out.println("Serwer zarejstrowany do wpisu");
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}

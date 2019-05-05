package SerwerBF;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISpis extends Remote{
	List<OS> getServers() throws RemoteException;
	boolean register(OS id) throws RemoteException;
}

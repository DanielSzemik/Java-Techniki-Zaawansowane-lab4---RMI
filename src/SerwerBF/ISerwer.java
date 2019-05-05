package SerwerBF;

import java.rmi.Remote;
import java.rmi.RemoteException;

//bool register(OS) rejestracja serweru //OS - Stringi Nazwa i Opis algorytmu
//S solve(I) //I - instancja, S - rozwiazanie

public interface ISerwer extends Remote{
	S solve(I inst) throws RemoteException;
}

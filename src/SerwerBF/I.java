package SerwerBF;
import java.io.Serializable;
import java.util.List;

/**
 * @author pas109
 * Klasa prechowuje list� przedmiot�w oraz pojemno�� plecaka
 */
public class I implements Serializable{
	
	/**
	 * lista wszystkich przedmiot�w instancji 
	 */
	public List<Item> listarzeczy;
	
	/**
	 * maksymalna waga jak� mo�e plecak trzyma�
	 */
	int pojemnosc;
	
	/**
	 * 
	 * @param listarzeczy - lista wszystkich przedmiot�w instancji 
	 * @param pojemnosc - maksymalna waga jak� mo�e plecak trzyma�
	 */
	public I (List<Item> listarzeczy, int pojemnosc) {
		this.listarzeczy = listarzeczy;
		this.pojemnosc = pojemnosc;
	}
	
	/**
	 * Getter zwracajacy liste przechowujaca przedmioty
	 * @return lista rzeczy
	 */
	public List<Item> getlist()
	{
		return listarzeczy;
	}
	
	/**
	 * Getter zwracajacy pojemnosc plecaka
	 * @return maksymalna pojemnosc plecaka
	 */
	public int getpojemnosc()
	{
		return pojemnosc;
	}
	
	
}

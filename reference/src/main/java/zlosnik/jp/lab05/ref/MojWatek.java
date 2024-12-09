package lab04;
abstract public class MojWatek extends Thread {
	private int produkt = 0;      
	protected Magazyn m;
	protected int czas;
	protected PokazujacyStan psi;
	static protected boolean koniec=false;


	public static void setKoniec(boolean k) {
		koniec = k;
	}	

	public static boolean getKoniec() {
		return koniec;
	}	

	public MojWatek(String nazwa, int czas, Magazyn m)  {
		super(nazwa);
		this.czas = czas;	
		this.m = m;
		produkt = 0;
	}	

	public void setPsi(PokazujacyStan psi) {
		this.psi = psi;
	}

	public void setProdukt(int produkt) {
		this.produkt = produkt;
	}
	public int getProdukt() {
		return produkt;
	}	
}

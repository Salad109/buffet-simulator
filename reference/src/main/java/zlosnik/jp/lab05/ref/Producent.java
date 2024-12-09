package lab04;
public class Producent extends MojWatek {
     		
	public Producent(String nazwa, int czasProd, Magazyn m)  {
		super(nazwa,czasProd,m);
	}	

    public void run() {
 	    while (!koniec) { 
      		try{ 
      			sleep(czas+(int)(Math.random()*100));  
      			} catch(InterruptedException e) {
      				System.err.println("Przerwano watek");
     			   }
            setProdukt( (int) ( Math.random()*9 ) + 1 );
            psi.pokazStan();
            m.dodaj(getProdukt());         
            psi.pokazStan();
   		}
   }
}
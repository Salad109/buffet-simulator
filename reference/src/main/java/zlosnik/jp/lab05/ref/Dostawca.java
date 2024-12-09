package zlosnik.jp.lab05.ref;
public class Dostawca extends MojWatek {
		
	public Dostawca(String nazwa, int czasProd, Magazyn m)  {
		super(nazwa,czasProd,m);
	}	
	
    public void run() {                  
    	while ( !koniec ) { 
            setProdukt(m.wez());  
            psi.pokazStan();       
      		try{ 
      			sleep(czas+(int)(Math.random()*100));
      			} catch(InterruptedException e) {
      				System.err.println("Przerwano watek");
      			  }
			setProdukt(0);         // przekaz dalej
            psi.pokazStan();
      		try{ 
      			sleep(czas+(int)(Math.random()*100));
      			} catch(InterruptedException e) {
      				System.err.println("Przerwano watek");
      			  }
   		}
	}
}
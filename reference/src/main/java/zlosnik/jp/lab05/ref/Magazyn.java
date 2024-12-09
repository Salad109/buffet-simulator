package lab04;
class Magazyn { 
	private int pojemnosc = 0;   
	private int[] bufor = null;   
	private int dodajW = 0, wezZ = 0;   
	private int licznik = 0;   
	private PokazujacyStan psi=null;
 
 public int [] getBufor() {
 	return bufor;
 }	
	
 public void setPsi(PokazujacyStan psi) {
 	this.psi = psi;
 }	
 public Magazyn(int pojemnosc) {      
		if (pojemnosc <= 0) throw new IllegalArgumentException("pojemnosc<=0"); 
    	this.pojemnosc = pojemnosc;     
 		bufor = new int[pojemnosc];      
		this.psi = psi;
 }  
 
 public synchronized void dodaj(int produkt) {     
	while (licznik == pojemnosc)        
 		try {            
 			wait();         
 		} catch (InterruptedException e) {  
 			System.err.println("Przerwano watek");
 		  }      
 	
 	bufor[dodajW] = produkt;      
 	dodajW = (dodajW + 1) % pojemnosc;      
 	licznik++;                   

 	if (licznik == 1) notify();  
 	
 	if(psi != null) 
 	  psi.pokazStan(); 	
 }   
 	   
public synchronized int wez() {      
	int produkt;     
 	
 	while (licznik == 0)         
 	try { 
 		wait();         
 	} catch (InterruptedException e) {            
 		System.err.println("Przerwano watek");         
 	  }     
 	produkt = bufor[wezZ];  
 	bufor[wezZ] = 0;    
 	wezZ = (wezZ + 1) % pojemnosc;      
 	licznik--;   
 	if (licznik == pojemnosc-1) notify();
 	
 	if(psi != null) 
 	  psi.pokazStan();

 	return produkt;   
 }
  
}
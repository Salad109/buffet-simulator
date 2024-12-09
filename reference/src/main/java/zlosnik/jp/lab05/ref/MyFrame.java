package zlosnik.jp.lab05.ref;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
public class MyFrame extends JFrame {
	
	private JLabel prod1, prod2, magProd1, magProd2;
	
	private JLabel dos1, magDos1;
	
	private JLabel dos2, magDos2;

	private JLabel odb1, odb2, odb3, magOdb;

	private Producent watekProd1, watekProd2;
	private Dostawca watekDos1, watekDos2;
//	private Odbiorca watekObdb1, watekOdb2, watekOdb3;
	private Magazyn magazynProd1;
	
    MyFrame getThis() {
    	return this;
    }

	public void utworzWatki(){
		magazynProd1 = new Magazyn(6);
		magazynProd1.setPsi(
			new PokazujacyStan(){
			  public void pokazStan(){
				StringBuffer s = new StringBuffer();
				int[] bufor = magazynProd1.getBufor();
				
				for(int i=0; i<bufor.length; i++){
					s.append(bufor[i]); s.append(" ");
				}
				magProd1.setText(s.toString());
			  }
			}
		);
		
		watekProd1 = new Producent("Producent1",1000,magazynProd1);
	    watekProd1.setPsi(
	    	new PokazujacyStan(){
		      public void pokazStan(){
				prod1.setText("Producent1:"+watekProd1.getProdukt());
			  }
			}
	    );
	    
	    
	    watekDos1 = new Dostawca("Dostawca1",1000,magazynProd1);
	    watekDos1.setPsi(
	    	new PokazujacyStan(){
	    	  public void pokazStan() {
	    		magDos1.setText(">>>_"+watekDos1.getProdukt()+"_>>>");
	    	  }
	    	}
	    );
	}
	    
	public void uruchomWatki(){
		MojWatek.setKoniec(false);
		watekProd1.start();
		watekDos1.start();
	}	        
	
	public void zatrzymajWatki() {
		MojWatek.setKoniec(true);
	}
    public void createComponents() {

        JButton buttonT = new JButton("Start");
        buttonT.setMnemonic(KeyEvent.VK_T);
        JButton buttonP = new JButton("Stop");
        buttonP.setMnemonic(KeyEvent.VK_P);

        prod1 = new JLabel(new String("Producent1:0"));                
        prod2 = new JLabel(new String("Producent2:0"));                
        dos1 = new JLabel(new String("Dostawca1"));
        dos2 = new JLabel(new String("Dostawca2"));
        odb1 = new JLabel(new String("Odbiorca1"));
        odb2 = new JLabel(new String("Odbiorca2"));
        odb3 = new JLabel(new String("Odbiorca3"));

		JLabel e11 = new JLabel("            ");
		JLabel e61 = new JLabel("            ");
		JLabel e12 = new JLabel("            ");
		JLabel e62 = new JLabel("            ");
		JLabel e53 = new JLabel("            ");
		JLabel e63 = new JLabel("            ");

		magProd1 = new JLabel("            ");
		magProd2 = new JLabel("            ");
        magDos1 = new JLabel(">>>_0_>>>");
		magDos2 = new JLabel(">>>_0_>>>");
		magOdb = new JLabel("            ");        
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 3, 10, 10));

		
		p.add(e11);			p.add(e12);		p.add(odb1);
		p.add(prod1);		p.add(dos1); 	p.add(odb2);
		p.add(magProd1);	p.add(magDos1);	p.add(odb3);
        p.add(prod2); 		p.add(dos2); 	p.add(magOdb);	        
        p.add(magProd2);	p.add(magDos2); p.add(e53);
        p.add(e61);			p.add(e62);		p.add(e63);	        


        ActionListener a = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(	((JButton)e.getSource()).getText().compareTo("Start")==0){
					utworzWatki();
					uruchomWatki();
            	}
            	
            	if(	((JButton)e.getSource()).getText().compareTo("Stop")==0) {
					zatrzymajWatki();
            	}
                	
            }
        };

        buttonT.addActionListener(a);
        buttonP.addActionListener(a);   
        JPanel pb = new JPanel();
        pb.add(buttonT);
        pb.add(buttonP);
        
        this.getContentPane().setLayout(new GridLayout(2, 1));
        
        
        this.getContentPane().add(p);
        this.getContentPane().add(pb);
    }
         
    public MyFrame(String arg0) {
        super(arg0);
        createComponents();        
        pack();        
    }
}

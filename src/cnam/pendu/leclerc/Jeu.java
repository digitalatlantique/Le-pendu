package cnam.pendu.leclerc;

import java.util.*;

/**
 * Permet l'initilisation de la partie
 */
public class Jeu {

    /**
     * Default constructor
     */
    public Jeu() {
    }

    /**
     * Permet de vérifier si la partie continue
     */
    private static boolean session;



    /**
     * Initialise la partie
     * @param args
     */
    public static void main(String[] args ) {
    	
    	Scanner scanner = new Scanner(System.in);
   	
    	RegleDuJeu regle = new RegleDuJeu();
    	
    	regle.initialisation();    	
    	regle.afficherAccueil();
    	regle.afficherRegle();
    	
    	do {
    		session = regle.jouer(scanner);    	
    	}
    	while(session);    	
    }      
}
package cnam.pendu.leclerc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe g�re les informations du jeu et ses m�canismes.
 */
public class RegleDuJeu {
	
	/**
	 * Constructeur par d�faut
	 */
	public RegleDuJeu() {
		
	}

    /**
     * Ce constructeur initialise la partie lorsque le mot � trouv� et le nombre d'essais sont pass� en param�tre.
     * Initialisation du mot � trouv� et sa longueur, le nombre d'essais
     * Initialisation du tableau de bool�en correspondant aux lettres d�j� trouv�
     * Initialisation du mot � trou et du dessin du pendu
     */
    public RegleDuJeu(String mot, int nombreDeCoup) {    	
    	
    	this.motSecret = mot;
    	this.longueurMot = motSecret.length();
    	this.nombreDeCoup = nombreDeCoup;
    	this.compteurEssai = 0;
    	lettreDejaTrouve = new boolean[longueurMot];
    	motATrouTab = new char[longueurMot];
    	lettreDejaSaisie = new ArrayList<Character>();
    	Arrays.fill(motATrouTab, '-');
    	
    	dessin = new Dessin();
    	dessin.definirIncrementation(nombreDeCoup);
    }
    
    /**
     * C'est le mot � trouv�
     */
    private String motSecret;
    
    /**
     * C'est le mot � trou affich� au joueur
     */
    private String motATrou;
    
    /**
     * D�termine la longueur du mot
     */
    private int longueurMot;

    /**
     * D�termine le nombre d'essais
     */
    private int nombreDeCoup;

    /**
     * Compte le nombre d'essais effectu�
     */
    private int compteurEssai;


    /**
     * Indique si la partie est gagn�e
     */
    private boolean victoire;
    
    /**
     * Repr�sente une instance du dessin du pendu
     */
    private Dessin dessin;
    
    /**
     * Stockage du mot � trou dans un tableau de caract�re pour construire le mot � trou
     */
    private char[] motATrouTab;

    /**
     * Ce tableau de bool�en indique les lettres d�j� trouv�es
     */
    private boolean[] lettreDejaTrouve;
    
    /**
     * Ce tableau contient les lettres d�j� essay�s
     */
    private ArrayList<Character> lettreDejaSaisie;
    
    /**
     * Tableau contenant l'ensemble des mots � trouv�es
     */
    private ArrayList<String> dictionnaire;

    /**
     * Cette m�thode permet l'initialisation d'une partie
     */
    public void initialisation() {    
    	
        // V�rification de l'initialisation du dictionnaire
    	if(dictionnaire == null) {
    		dictionnaire = chargerDictionnaire();
    	}
    	// Nombre al�atoire permettant d'indiquer le nombre d'essais pour le jeu et d�finir l'incr�mentation de dessin pour l'apparition du pendu
    	int aleatoire = genererNombre(1, dictionnaire.size()-1);
    	// Initialisation des propri�t�s de la clasee
    	this.motSecret = dictionnaire.get(aleatoire);
    	this.longueurMot = motSecret.length();
    	this.nombreDeCoup = genererNombreDeCoup(longueurMot);
    	this.compteurEssai = 0;
    	
    	lettreDejaTrouve = new boolean[longueurMot];
    	motATrouTab = new char[longueurMot];
    	lettreDejaSaisie = new ArrayList<Character>();
    	Arrays.fill(motATrouTab, '-'); 
    	
    	dessin = new Dessin();
    	
    	try {
    		dessin.definirIncrementation(nombreDeCoup);
    	}
    	catch(ArithmeticException e) {
    		System.err.println(e.getMessage());
    	}    	
    }    
    
    /**
     * Permet de g�n�rer un nombre compris dans un interval
     * @param min, le nombre minimum
     * @param max, le nombre maximum
     * @return un nombre al�atoire
     */
    public int genererNombre(int min, int max) {
    	int aleatoire = (int) (Math.random() * (max - min))+min;
    	return aleatoire;
    }
    
    /**
     * G�n�re le nombre d'essais en fonction de la longueur du mot
     * @param longueur, correspond � la longueur du mot � trouv�
     * @return le nombre d'essais
     */
    public int genererNombreDeCoup(int longueur) {
    	int resultat;
    	if(longueur <= 3) {
    		resultat = longueur * 3;
    	}
    	else if(longueur >= 4 && longueur <= 6) {
    		resultat = (int) Math.round(longueur * 2.5); 
    	}
    	else {
    		resultat = longueur * 2;
        	if(resultat>25) {
        		return 25;
        	}
    	}
    	return resultat;
    }
    
    /**
     * Permet de charger un dictionnaire de mot � partir d'un fichier plat
     * @return une collection de mots
     */
    public ArrayList<String> chargerDictionnaire(){
    	
    	ArrayList<String> dictionnaire = new ArrayList<String>();
    	
    	String ligne = "";
    	String path = "./assets/dictionnaire.txt";
    	File file = new File(path);
    	try {
    		FileReader fr = new FileReader(file);
    		BufferedReader br = new BufferedReader(fr);
    		
    		
    		while((ligne = br.readLine()) != null) {
    			dictionnaire.add(ligne);
    		}		
			br.close();
			fr.close();
    	}
    	catch(FileNotFoundException e) { 
    		System.err.println(" ERREUR Fichier non touv�  ");
    	}
    	catch(IOException e) {
    		System.err.println(" ERREUR acces Reader ");
    	}
    	return dictionnaire;
    }

    /**
     * Recherche si la lettre pass� en param�tre apparait une ou plusieurs fois dans le mot � trouver
     * @param saisie, la lettre � chercher
     * @return vrai si la lettre apparait une ou plusieurs fois
     */
    public boolean rechercheLettre(char saisie) {
    	
    	char testCaractere = Character.toLowerCase(saisie);
    	String testMotSecret = motSecret.toLowerCase();
    	boolean resultat = false;

		if(!lettreDejaSaisie.contains(testCaractere)) {
			lettreDejaSaisie.add(testCaractere);
		}
        for(int i=0; i<longueurMot; i++) {
        	if(testMotSecret.charAt(i) == testCaractere) {
        		lettreDejaTrouve[i] = true;
        		motATrouTab[i] = testCaractere;      		
        		resultat = true;
        	}        	
        }
        return resultat;
    }

    /**
     * Correspond � un tour de jeu, test la pr�sence du caract�re, g�re si la partie est gagn�e ou perdu ou continue
     * @param sc, une instance de scanner 
     * @return un bool�en pour savoir si la partie se poursuit
     */
    public boolean jouer(Scanner sc) {
    	
    	String saisie;
    	
    	afficherNombreEssai();
    	afficherLettreSaisie();
    	afficherMotATrou();
    	
    	// Test la saisie du joueur
    	do {
    		afficherDemandeSaisie();
    		saisie = sc.next();
    	}
    	while(!testSaisieJoueur(saisie));
    	
    	// Recherche la lettre
    	rechercheLettre(saisie.charAt(0));    	
    	// Ajoute un tour de jeu
    	compteurEssai++;
    	// Test la victoire
    	if(testVictoire()) {
    		afficherVictoire();
    		return false;
    	}
    	// Test s'il reste encore des essais
    	else if(nombreDeCoup == compteurEssai) {
    		afficherMotATrou();
    		afficherPerdu();
    		dessin.afficherPendu(Dessin.NOMBRE_MAX_ELEMENT);
    		return false;
    	}
    	// Sinon la partie se poursuit
    	else {
    		dessin.afficherPendu();
    		return true;
    	}
    }
    
    /**
     * V�rifie � l'aide d'un tableau de bool�en si toutes les lettres ont �t� trouv�es
     * @return vrai si c'est gagn�e
     */
    public boolean testVictoire() {
    	
    	victoire = true;
    	
    	for(int i=0; i<lettreDejaTrouve.length; i++) {    		
    		victoire = victoire && lettreDejaTrouve[i];    		
    	}
    	return victoire;
    }
    
    /**
     * Test la saisie du joueur, autorisant un caract�re, non sp�cial, non num�rique
     * @param saisie, la cha�ne � tester 
     * @return vrai si c'est un caract�re conforme
     */
    public boolean testSaisieJoueur(String saisie) {
    	
    	Pattern pattern = Pattern.compile("[A-Za-z������������]{1}");
    	Matcher matcher = pattern.matcher(saisie);
    	return matcher.matches();
    }
    
    /**
     * Affiche le nombre d'essais
     */
    public void afficherNombreEssai() {
    	int afficherEssai = nombreDeCoup - compteurEssai;
    	System.out.println("Vous avez " + afficherEssai + " essai(s)." );
    }
    
    /**
     * Affiche le mot � trou
     */
    public void afficherMotATrou() {
    	motATrou = new String(motATrouTab);
    	System.out.println(motATrou);    	
    }
    
    /**
     * Affiche les lettres d�j� saisie
     */
    public void afficherLettreSaisie() {
    	
    	lettreDejaSaisie.sort(null);
    	if(lettreDejaSaisie.size() >= 1) {
    		System.out.print("Lettre d�j� saisie : ");
    	}

    	for(Character lettre : lettreDejaSaisie) {
    		System.out.print(lettre +", ");
    	}
    	System.out.println();
    }
    
    /**
     * Affiche l'indication au joueur de saisir un caract�re
     */
    public void afficherDemandeSaisie() {
    	System.out.println("Veuillez saisir un caract�re : ");
    }
    
    /**
     * Affiche la victoire
     */
    public void afficherVictoire() {
    	System.out.println("##################################\n"
    					 + "#            GAGNEE !!	         #\n"
    					 + "##################################");
    	System.out.println("Vous avez trouv� : " + this.motSecret);
    }
    
    /**
     * Affiche la d�faite
     */
    public void afficherPerdu() {
    	System.out.println("##################################\n"
    					 + "#             PERDU !!	         #\n"
    					 + "##################################");
    	System.out.println("Le mot �tait : " + this.motSecret);
    	
    }
    
    /**
     * Affiche le titre du jeu
     */
    public void afficherAccueil() {
    	System.out.println("##################################\n"
    					 + "#          JEU DU PENDU	         #\n"
    					 + "##################################");
    }
    
    /**
     * Affiche les r�gles du jeu
     */
    public void afficherRegle() {
    	System.out.println();
    	System.out.println("Le but du jeu est de trouver un mot dont on conna�t initialement le nombre de lettre.\n"
    						+ "Ce mot est repr�sent� par des traits soulign�es pour chaque lettre manquante. Le joueur \n"
    						+ "dispose d'un certain nombre de tours de jeu pour trouver le mot en proposant des lettres.\n");
    	System.out.println("BONNE CHANCE !!");
    	System.out.println();
    }

}
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
 * Cette classe gère les informations du jeu et ses mécanismes.
 */
public class RegleDuJeu {
	
	/**
	 * Constructeur par défaut
	 */
	public RegleDuJeu() {
		
	}

    /**
     * Ce constructeur initialise la partie lorsque le mot à trouvé et le nombre d'essais sont passé en paramètre.
     * Initialisation du mot à trouvé et sa longueur, le nombre d'essais
     * Initialisation du tableau de booléen correspondant aux lettres déjà trouvé
     * Initialisation du mot à trou et du dessin du pendu
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
     * C'est le mot à trouvé
     */
    private String motSecret;
    
    /**
     * C'est le mot à trou affiché au joueur
     */
    private String motATrou;
    
    /**
     * Détermine la longueur du mot
     */
    private int longueurMot;

    /**
     * Détermine le nombre d'essais
     */
    private int nombreDeCoup;

    /**
     * Compte le nombre d'essais effectué
     */
    private int compteurEssai;


    /**
     * Indique si la partie est gagnée
     */
    private boolean victoire;
    
    /**
     * Représente une instance du dessin du pendu
     */
    private Dessin dessin;
    
    /**
     * Stockage du mot à trou dans un tableau de caractère pour construire le mot à trou
     */
    private char[] motATrouTab;

    /**
     * Ce tableau de booléen indique les lettres déjà trouvées
     */
    private boolean[] lettreDejaTrouve;
    
    /**
     * Ce tableau contient les lettres déjà essayés
     */
    private ArrayList<Character> lettreDejaSaisie;
    
    /**
     * Tableau contenant l'ensemble des mots à trouvées
     */
    private ArrayList<String> dictionnaire;

    /**
     * Cette méthode permet l'initialisation d'une partie
     */
    public void initialisation() {    
    	
        // Vérification de l'initialisation du dictionnaire
    	if(dictionnaire == null) {
    		dictionnaire = chargerDictionnaire();
    	}
    	// Nombre aléatoire permettant d'indiquer le nombre d'essais pour le jeu et définir l'incrémentation de dessin pour l'apparition du pendu
    	int aleatoire = genererNombre(1, dictionnaire.size()-1);
    	// Initialisation des propriétés de la clasee
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
     * Permet de générer un nombre compris dans un interval
     * @param min, le nombre minimum
     * @param max, le nombre maximum
     * @return un nombre aléatoire
     */
    public int genererNombre(int min, int max) {
    	int aleatoire = (int) (Math.random() * (max - min))+min;
    	return aleatoire;
    }
    
    /**
     * Génère le nombre d'essais en fonction de la longueur du mot
     * @param longueur, correspond à la longueur du mot à trouvé
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
     * Permet de charger un dictionnaire de mot à partir d'un fichier plat
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
    		System.err.println(" ERREUR Fichier non touvé  ");
    	}
    	catch(IOException e) {
    		System.err.println(" ERREUR acces Reader ");
    	}
    	return dictionnaire;
    }

    /**
     * Recherche si la lettre passé en paramètre apparait une ou plusieurs fois dans le mot à trouver
     * @param saisie, la lettre à chercher
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
     * Correspond à un tour de jeu, test la présence du caractère, gère si la partie est gagnée ou perdu ou continue
     * @param sc, une instance de scanner 
     * @return un booléen pour savoir si la partie se poursuit
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
     * Vérifie à l'aide d'un tableau de booléen si toutes les lettres ont été trouvées
     * @return vrai si c'est gagnée
     */
    public boolean testVictoire() {
    	
    	victoire = true;
    	
    	for(int i=0; i<lettreDejaTrouve.length; i++) {    		
    		victoire = victoire && lettreDejaTrouve[i];    		
    	}
    	return victoire;
    }
    
    /**
     * Test la saisie du joueur, autorisant un caractère, non spécial, non numérique
     * @param saisie, la chaïne à tester 
     * @return vrai si c'est un caractère conforme
     */
    public boolean testSaisieJoueur(String saisie) {
    	
    	Pattern pattern = Pattern.compile("[A-Za-zàêëéèçîïôïüù]{1}");
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
     * Affiche le mot à trou
     */
    public void afficherMotATrou() {
    	motATrou = new String(motATrouTab);
    	System.out.println(motATrou);    	
    }
    
    /**
     * Affiche les lettres déjà saisie
     */
    public void afficherLettreSaisie() {
    	
    	lettreDejaSaisie.sort(null);
    	if(lettreDejaSaisie.size() >= 1) {
    		System.out.print("Lettre déjà saisie : ");
    	}

    	for(Character lettre : lettreDejaSaisie) {
    		System.out.print(lettre +", ");
    	}
    	System.out.println();
    }
    
    /**
     * Affiche l'indication au joueur de saisir un caractère
     */
    public void afficherDemandeSaisie() {
    	System.out.println("Veuillez saisir un caractère : ");
    }
    
    /**
     * Affiche la victoire
     */
    public void afficherVictoire() {
    	System.out.println("##################################\n"
    					 + "#            GAGNEE !!	         #\n"
    					 + "##################################");
    	System.out.println("Vous avez trouvé : " + this.motSecret);
    }
    
    /**
     * Affiche la défaite
     */
    public void afficherPerdu() {
    	System.out.println("##################################\n"
    					 + "#             PERDU !!	         #\n"
    					 + "##################################");
    	System.out.println("Le mot était : " + this.motSecret);
    	
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
     * Affiche les règles du jeu
     */
    public void afficherRegle() {
    	System.out.println();
    	System.out.println("Le but du jeu est de trouver un mot dont on connaît initialement le nombre de lettre.\n"
    						+ "Ce mot est représenté par des traits soulignées pour chaque lettre manquante. Le joueur \n"
    						+ "dispose d'un certain nombre de tours de jeu pour trouver le mot en proposant des lettres.\n");
    	System.out.println("BONNE CHANCE !!");
    	System.out.println();
    }

}
package cnam.pendu.leclerc;

/**
 * Permet l'affichage du pendu soit en sélectionnant le nombre d'éléments à afficher ou en fonction du nombre d'essai
 */
public class Dessin {

    /**
     * Default constructor
     */
    public Dessin() {    

    	pendu = initialiserDessin();
    	positionElement = initialiserPositionElement();
    	etatElement = new boolean[pendu.length][pendu[0].length];
    	 	
    }

    /**
     * Contient le dessin du pendu
     */
    private char[][] pendu;

    /**
     * Contient la position des éléments du pendu dans l'ordre d'apparition.
     */
    private int[][] positionElement;
    
    /**
     * Définit le pas d'affichage
     */
    private int incrementation;
    
    /**
     * Contient le nombre d'élément(s) à afficher
     */
    private int nombreElementAfficher;
    
    /**
     * Contient le reste de la division du nombre max d'élément par le nombre d'essai
     */
    private int reste;
    
    /**
     * Tableau de booleen contenant l'état des éléments du pendu à afficher
     */
    private boolean[][] etatElement;

    /**
     * Nombre max d'élément à afficher
     */
    public static final int NOMBRE_MAX_ELEMENT = 25;


    /**
     * Permet d'afficher les éléments du pendu en fonction du nombre d'essai 
     */
    public void afficherPendu() {
    	
    	afficherPendu(nombreElementAfficher); 
    	
    	int temp = nombreElementAfficher + incrementation;
    	// Si au tour suivant l'incrémentation  est supérieur au nombre d'élément max,  ou si l'incrémentation + le reste est égale à max, on assigne à max
    	if(temp > NOMBRE_MAX_ELEMENT || temp + reste == NOMBRE_MAX_ELEMENT ) {
    		nombreElementAfficher = NOMBRE_MAX_ELEMENT;
    	}
    	else {
    		nombreElementAfficher += incrementation;
    	}
    }
    
    /**
     * Affichage du pendu en fonction du nombre d'élément à afficher
     * @param nombreElement
     */
    public void afficherPendu(int nombreElement) {
    	
    	// Initialisation des éléments à afficher à true
    	for(int i=0; i<nombreElement; i++) {    		
    		etatElement[positionElement[i][0]][positionElement[i][1]] = true;    		
    	}
    	// Boucle sur le tableau de booleen pour connaitre les éléments à afficher
    	for(int x=0; x<etatElement.length; x++) {
    		for(int y=0; y<etatElement[x].length; y++) {
    			
    			// Si true on affiche l'élément du pendu
    			if(etatElement[x][y]==true) {
    				System.out.print(pendu[x][y]);
    			}
    			// Sinon on affiche un espace vide
    			else {
    				System.out.print(" ");
    			}    				
    		}
    		System.out.println();
    	}    	
    }
    
    /**
     * Permet de définir l'incrémentation de l'affichage du pendu en fonction du nombre d'essai
     * @param nombreDeCoup
     * @throws ArithmeticException si le nombre de coup est inférieur ou égal à zéro
     */
    public void definirIncrementation(int nombreDeCoup) throws ArithmeticException {
    	
    	if(nombreDeCoup <= 0) {
    		throw new ArithmeticException("Nombre de coup insuffisant");
    	}
    	incrementation = NOMBRE_MAX_ELEMENT / nombreDeCoup;
    	reste = NOMBRE_MAX_ELEMENT % nombreDeCoup;
    	nombreElementAfficher = incrementation;
    }
    
    /**
     * Initialise le tableau à 2 dimensions contenant le dessin du pendu
     * @return le tableau à 2 dimensions
     */
    public char[][] initialiserDessin(){
    	
    	char[][] tab = {	{' ', ' ', '_', '_', '_', '_', '_', ' '},
							{' ', ' ', '|', '/', ' ', ' ', '|', ' '},
							{' ', ' ', '|', ' ', ' ', ' ', 'O', ' '},
							{' ', ' ', '|', ' ', ' ', '/', '|', '\\'},
							{' ', ' ', '|', ' ', ' ', '/', ' ', '\\'},
							{'_', '_', '|', '_', '_', '_', '_', '_'}};    	
    	return tab;
    }
    
    /**
     * Initialise le tableau contenant la position des éléments du pendu à afficher dans l'ordre d'apparition    
     * @return le tableau à 2 dimensions
     */
    public int[][] initialiserPositionElement(){
    	
    	int[][] tab = {	{5, 0},
						{5, 1},
						{5, 3},
						{5, 4},
						{5, 5},
						{5, 6},
						{5, 7},
						{5, 2},
						{4, 2},
						{3, 2},
						{2, 2},
						{1, 2},
						{0, 2},
						{0, 3},
						{0, 4},
						{0, 5},
						{0, 6},
						{1, 3},
						{1, 6},
						{2, 6},
						{3, 6},
						{3, 5},
						{3, 7},
						{4, 5},
						{4, 7}};
    	return tab;
    }
}
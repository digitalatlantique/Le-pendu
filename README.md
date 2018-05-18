#########################     Jeu le Pendu    #########################

Projet r�alis� dans le cadre de l'unit� d'enseignement NFA032 Java programmation objet avec le CNAM

Objectif : Le but de ce projet est de r�aliser un jeu simple du pendu, en mode ligne de commande et en manipulant cha�nes de caract�res et tableaux. Le dictionnaire est g�n�r� � partir d'un fichier plat.

Java Runtime Environment : JRE 9.0.1

Disponible dans ce projet :
- un executable 			=> 		jeuLePendu.jar
- un diagramme de classe 	=> 		penduDiagramClass.jpg
- une documentation 		=>		javaDoc (dossier)
- Les sources


#########################        Ennonc�       #########################

Exemple du pendu attendu en ligne de commande :
  _____
  |/  |
  |   o
  |  /|\
  |  / \
__|_____
MA_SON

Avant-propos
Veuillez lire tout une premi�re fois avant de vous lancer.
Les classes de votre programme doivent toutes appartenir au paquetage suivant : �cnam.pendu.[votre_nom]� (remplacer [votre_nom] par votre nom de famille).
Des commentaires sont indispensables dans votre programme afin d�expliquer les classes et les m�thodes implant�es. La g�n�ration d'une documentation � l'aide de l'outil javadoc sera un plus.
Ne faite jamais confiance � l�utilisateur, contr�lez les entr�es et g�rez les erreurs.
Votre code source devra �tre compress� au format zip uniquement et devra respecter le standard suivant �nom.zip�.
Votre archive compress� devra m��tre envoy� par mail pour le dimanche 4 mars 2018 en indiquant �[pooPendu]� dans le sujet du mail.
Pas de copier / coller d�un projet existant sur Internet :) Faite le pour vous, surpassez-vous !

But du jeu
Le but du jeu est de trouver un mot dont on ne conna�t initialement que le nombre de lettre, par exemple 6 lettres pour le mot �maison". Ce mot est pr�sent� par des traits soulign�s pour chaque lettre manquante, soit _ _ _ _ _ _ pour MAISON. Le joueur dispose d'un certain nombre de tours de jeu pour trouver le mot en proposant des lettres qui peuvent appartenir au mot.
A chaque tour de jeu, le joueur propose une lettre :
soit la lettre appartient au mot et elle s'affiche aux bonnes positions en compl�tant le mot � trous (si la lettre se trouve plusieurs fois dans le mot, alors elle s'affiche � chaque occurrence),
soit la lettre n'appartient pas au mot et le dessin du pendu se compl�te d�un segment, se rapprochant de plus en plus du dessin complet. Une fois le dessin termin�, cela signe la perte de la partie.

Cahier des charges
Impl�mentation du moteur du jeu
Cr�ation d�une classe RegleDuJeu afin de g�rer les informations du jeu et ses m�canismes : l'affichage du mot � trous, la recherche d'un caract�re dans le mot, la d�tection de fin de partie, etc�
Cr�ation d�une classe Dessin permettant la gestion du dessin.
La base : ________ (8 tirets du bas �_�, touche MAJ + 8)
Le m�t : | (5 b�tons �|�, touche AltGr + 6)
La poutre horizontale : _____ (5 tirets du bas �_�, touche MAJ + 8)
Le renfort : / (1 barre oblique �/�, touche MAJ + :)
La corde : | (1 b�ton �|�, touche AltGr + 6)
La t�te : o (1 lettre o)
Le corps : | (1 b�ton �|�, touche AltGr + 6)
Les bras : / \ (1 barre oblique �/�, touche MAJ + : et une barre anti oblique �\�, touche AltGr + 8)
Les jambes : / \ (1 barre oblique �/�, touche MAJ + : et une barre anti oblique �\�, touche AltGr + 8)

.._____
..|/..|
..|...o
..|../|\
..|../.\
__|_____
Chaque (.) point devra �tre remplac� par un espace.

Ordre d�apparition du pendu (sch�matiquement) :
00DEFGH
00CI00J
00B000K
00A00MLN
00900O0P
12834567
En sachant que la lettre A correspond � 10, B = 11, �, P = 25.
Les espaces (ou vides) sont associ�s � 0, tous les autres caract�res du tableau dessin sont associ�s � des entiers dans la valeur est comprise entre 1 et nombreMaxElement.

Impl�mentez la classe Dessin avec deux tableau � double dimension. Le premier contiendra le dessin de votre pendu et le second contiendra l�ordre d'apparition de chaque �l�ment de votre pendu. Et pour finir une constante nombreMaxElement = 25.
Impl�mentez la classe RegleDuJeu, avec comme attributs le mot � trouver, le nombre d'essais autoris�s, le nombre d'erreurs commises et un tableau de bool�ens permettant de savoir quelles lettres ont d�j� �t� trouv�s dans le mot. Cette classe doit avoir un constructeur unique qui prend en param�tres le mot � trouver et le nombre d'essais autoris�s.
Impl�mentez la m�thode afficher pour lui permettre de prendre en param�tre un entier repr�sentant le nombre de traits � afficher dans le dessin.
Red�finissez la m�thode afficher pour augmenter de mani�re constante le nombre de traits affich�s dans le dessin � chaque faute. Par exemple, si on autorise 2 essais, il faut afficher la moiti� des traits � la premi�re faute, et tout le reste a la deuxi�me faute.
Ajoutez une m�thode qui affiche le mot � trous (selon l'�tat d'avancement de la partie).
Ajoutez une m�thode qui prend un caract�re en param�tre et renvoie un bool�en indiquant si oui ou non le caract�re appartient au mot recherch�. Cette m�thode doit modifier l'�tat du tableau repr�sentant le mot a trous.
Ajoutez une m�thode qui teste l'�tat du jeu et d�termine si oui ou non on a trouv� toutes les lettres (donc si on a gagn�).
Ajoutez une m�thode boolean jouer(Scanner sc) qui prend en param�tre un 
flux de caract�res pour r�cup�rer les caract�res � tester. Cette m�thode effectue
un tour de jeu : elle affiche le mot a trous puis demande au joueur d'entrer un caract�re pour le compl�ter. Ensuite elle teste la pr�sence du caract�re dans le mot puis teste la fin du jeu (un message est affich� si la partie est gagn�e ou perdue). Enfin elle renvoie un bool�en qui vaut true si le jeu continu (il reste des lettres � d�couvrir) et false si le jeu s'arr�te (gagn� ou perdu).
Ajouter une m�thode initialisation, permettant de charger une liste de mots � partir d�un fichier plat. Le mot doit �tre choisi au hasard. Sans oublier le nombre d�essai possible pour chaque mot. Un mot court � moins d�essai qu�un mot long.
Impl�mentation de l�interface du joueur

Faire une classe Jeu qui contient la m�thode main dans laquelle une partie est initialis�e. Mettre en place le syst�me en utilisant les m�thodes que vous avez impl�ment�.
Ensuite, l'entr�e standard est envelopp�e dans un flux de caract�res et pass�e en argument de la m�thode jouer. Cette methode est appelee tant qu'elle renvoie true.

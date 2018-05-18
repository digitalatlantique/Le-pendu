#########################     Jeu le Pendu    #########################

Projet réalisé dans le cadre de l'unité d'enseignement NFA032 Java programmation objet avec le CNAM

Objectif : Le but de ce projet est de réaliser un jeu simple du pendu, en mode ligne de commande et en manipulant chaînes de caractères et tableaux. Le dictionnaire est généré à partir d'un fichier plat.

Java Runtime Environment : JRE 9.0.1

Disponible dans ce projet :
- un executable 			=> 		jeuLePendu.jar
- un diagramme de classe 	=> 		penduDiagramClass.jpg
- une documentation 		=>		javaDoc (dossier)
- Les sources


#########################        Ennoncé       #########################

Exemple du pendu attendu en ligne de commande :
  _____
  |/  |
  |   o
  |  /|\
  |  / \
__|_____
MA_SON

Avant-propos
Veuillez lire tout une première fois avant de vous lancer.
Les classes de votre programme doivent toutes appartenir au paquetage suivant : “cnam.pendu.[votre_nom]” (remplacer [votre_nom] par votre nom de famille).
Des commentaires sont indispensables dans votre programme afin d’expliquer les classes et les méthodes implantées. La génération d'une documentation à l'aide de l'outil javadoc sera un plus.
Ne faite jamais confiance à l’utilisateur, contrôlez les entrées et gérez les erreurs.
Votre code source devra être compressé au format zip uniquement et devra respecter le standard suivant “nom.zip”.
Votre archive compressé devra m’être envoyé par mail pour le dimanche 4 mars 2018 en indiquant “[pooPendu]” dans le sujet du mail.
Pas de copier / coller d’un projet existant sur Internet :) Faite le pour vous, surpassez-vous !

But du jeu
Le but du jeu est de trouver un mot dont on ne connaît initialement que le nombre de lettre, par exemple 6 lettres pour le mot “maison". Ce mot est présenté par des traits soulignés pour chaque lettre manquante, soit _ _ _ _ _ _ pour MAISON. Le joueur dispose d'un certain nombre de tours de jeu pour trouver le mot en proposant des lettres qui peuvent appartenir au mot.
A chaque tour de jeu, le joueur propose une lettre :
soit la lettre appartient au mot et elle s'affiche aux bonnes positions en complétant le mot à trous (si la lettre se trouve plusieurs fois dans le mot, alors elle s'affiche à chaque occurrence),
soit la lettre n'appartient pas au mot et le dessin du pendu se complète d’un segment, se rapprochant de plus en plus du dessin complet. Une fois le dessin terminé, cela signe la perte de la partie.

Cahier des charges
Implémentation du moteur du jeu
Création d’une classe RegleDuJeu afin de gérer les informations du jeu et ses mécanismes : l'affichage du mot à trous, la recherche d'un caractère dans le mot, la détection de fin de partie, etc…
Création d’une classe Dessin permettant la gestion du dessin.
La base : ________ (8 tirets du bas “_”, touche MAJ + 8)
Le mât : | (5 bâtons “|”, touche AltGr + 6)
La poutre horizontale : _____ (5 tirets du bas “_”, touche MAJ + 8)
Le renfort : / (1 barre oblique “/”, touche MAJ + :)
La corde : | (1 bâton “|”, touche AltGr + 6)
La tête : o (1 lettre o)
Le corps : | (1 bâton “|”, touche AltGr + 6)
Les bras : / \ (1 barre oblique “/”, touche MAJ + : et une barre anti oblique “\”, touche AltGr + 8)
Les jambes : / \ (1 barre oblique “/”, touche MAJ + : et une barre anti oblique “\”, touche AltGr + 8)

.._____
..|/..|
..|...o
..|../|\
..|../.\
__|_____
Chaque (.) point devra être remplacé par un espace.

Ordre d’apparition du pendu (schématiquement) :
00DEFGH
00CI00J
00B000K
00A00MLN
00900O0P
12834567
En sachant que la lettre A correspond à 10, B = 11, …, P = 25.
Les espaces (ou vides) sont associés à 0, tous les autres caractères du tableau dessin sont associés à des entiers dans la valeur est comprise entre 1 et nombreMaxElement.

Implémentez la classe Dessin avec deux tableau à double dimension. Le premier contiendra le dessin de votre pendu et le second contiendra l’ordre d'apparition de chaque élément de votre pendu. Et pour finir une constante nombreMaxElement = 25.
Implémentez la classe RegleDuJeu, avec comme attributs le mot à trouver, le nombre d'essais autorisés, le nombre d'erreurs commises et un tableau de booléens permettant de savoir quelles lettres ont déjà été trouvés dans le mot. Cette classe doit avoir un constructeur unique qui prend en paramètres le mot à trouver et le nombre d'essais autorisés.
Implémentez la méthode afficher pour lui permettre de prendre en paramètre un entier représentant le nombre de traits à afficher dans le dessin.
Redéfinissez la méthode afficher pour augmenter de manière constante le nombre de traits affichés dans le dessin à chaque faute. Par exemple, si on autorise 2 essais, il faut afficher la moitié des traits à la première faute, et tout le reste a la deuxième faute.
Ajoutez une méthode qui affiche le mot à trous (selon l'état d'avancement de la partie).
Ajoutez une méthode qui prend un caractère en paramètre et renvoie un booléen indiquant si oui ou non le caractère appartient au mot recherché. Cette méthode doit modifier l'état du tableau représentant le mot a trous.
Ajoutez une méthode qui teste l'état du jeu et détermine si oui ou non on a trouvé toutes les lettres (donc si on a gagné).
Ajoutez une méthode boolean jouer(Scanner sc) qui prend en paramètre un 
flux de caractères pour récupérer les caractères à tester. Cette méthode effectue
un tour de jeu : elle affiche le mot a trous puis demande au joueur d'entrer un caractère pour le compléter. Ensuite elle teste la présence du caractère dans le mot puis teste la fin du jeu (un message est affiché si la partie est gagnée ou perdue). Enfin elle renvoie un booléen qui vaut true si le jeu continu (il reste des lettres à découvrir) et false si le jeu s'arrête (gagné ou perdu).
Ajouter une méthode initialisation, permettant de charger une liste de mots à partir d’un fichier plat. Le mot doit être choisi au hasard. Sans oublier le nombre d’essai possible pour chaque mot. Un mot court à moins d’essai qu’un mot long.
Implémentation de l’interface du joueur

Faire une classe Jeu qui contient la méthode main dans laquelle une partie est initialisée. Mettre en place le système en utilisant les méthodes que vous avez implémenté.
Ensuite, l'entrée standard est enveloppée dans un flux de caractères et passée en argument de la méthode jouer. Cette methode est appelee tant qu'elle renvoie true.

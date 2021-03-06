# JavaWatchlist
Projet de programmation avancée 2016 - Romane Marchal, Frédéric Meinnel, Elie Dutheil

![alt tag](https://raw.githubusercontent.com/edwandr/java-watchlist/master/main.png)

## 1. Installation :
* Télécharger le zip dans votre espace de travail d'Eclipse. L'utilisation d'Eclipse n'est pas obligatoire mais les indications suivantes se rapportent à cet IDE.
* Importer la bibliothèque *jfxrt.jar* en cliquant sur *Add External Jars* depuis l'onglet Librairies puis naviguer jusqu'au répertoire d’installation du JDK dans son répertoire jre/lib/ext. Sélectionner le fichier jfxrt.jar et cliquer sur Open.
* Installer e(fx)eclipse dans le menu *Help → Install New Software*. À droite du champ *Work with*, cliquer sur le bouton *Add* pour ajouter une nouvelle source. Donner un nouveau nom pour ce dépôt (par exemple : e(fx)clipse) et saisir l'URL http://download.eclipse.org/efxclipse/updates-released/1.0.0/site puis cliquer sur OK. Sélectionner le nœud *e(fx)clipse - install* et redémarrer Eclipse.
* Exécuter la classe Main.java pour lancer le programme.

## 2. Fonctionnalités
* Liste des séries en vogue dans l'onglet **"Featured shows"**
* Recherche d'une série par la barre de recherche
* Liste des séries favorites dans l'onglet **"Favorite shows"**
* Le clic sur une série donne des détails sur cette dernière ainsi que la date de diffusion du prochain episode
* Lorsqu'une série est dans les favoris, une **pastille rouge notifie l'utilisateur** que la diffusion du prochain épisode
  arrive dans moins d'une semaine et il est possible de **consulter la liste des épisodes par saison** avec descriptif.

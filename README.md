# intersigale-gwt

Intersigale est un outil permettant de mémoriser n'importe quoi (Au départ c'était fait pour appendre du vocabulaire espagnol).
Il permet de savoir .. ce que l'on sait, ce que l'on ne sait pas, ce que l'on a su, ce que l'on a oublié.
Ce monitoring (de ce que l'on retient) est motivant (en tout cas pour moi). 
Le site pour acceder à l'application est :
http://sigale-1267.appspot.com/
Si cela peut être utile à quelqu'un, qu'il me le fasse savoir (bertrand.guiral@gmail.com), ce sera bon pour ma motivation.

Licence : Tout est libre de droit

Le deuxième but de cette application, c'est de mieux prendre en main certaines  techniques :
1- gwt/html5 (storage local, canvas), test unitaires gwt, architecture
Voir les sources.

2 - Api Translate 
Il faut quelques configurations coté google:
a- aller dans la console google:
https://console.cloud.google.com/apis/
Choisir un projet existant ou en créer un . (Dans mon cas sigale est un projet appengine que je retrouve donc)
b- Rechercher l'api "Google translate api" (utiliser la recherche, à a main c'est difficile)
c- Credentials (Créer une clé sans restriction dans un premier temps, dans un deuxiemme temps, il faudra mettre kes restrictions qui conviennent)


3 - requete Restfull cross site
Voir http://www.gwtproject.org/doc/latest/tutorial/Xsite.html
# aquarium
java aquarium game

# technos
java JDK 11.0.12.7-hotspot

javaFX SDK 20.0.2

apache maven 3.9.4

ItelliJ IDEA 2023.2

# architecture
MVC :
- model : la logique du jeu (fish, coordinate, ..)
- view : aquarium-view.fxml (vue principale, une seule fenêtre)
- controller : AquariumController pour aquarium-view.fxml.

#-

Singleton :
- classes Config, FishService, SoundManager

#-

Observer :
- interface AquariumPaneObserver
- classe Config implémente AquariumPaneObserver
- classe AquariumController notifie les observateurs des changements de aquariumPane

#-

Factory :
- classe FishFactory : fabrique de poissons (potentielle évolution du jeu avec plusieurs types de poissons)

#-

Strategy :
- classe abstraite SoundStrategy
- classes concrètes FishPopInSoundStrategy, FishPopOutSoundStrategy,MaxFishReachedSoundStrategy
- classe SoundManager pour la gestion des bruitages

# Logique du jeu

- Bouton spawnFishy : permet d'ajouter des poissons jusqu'à l'atteinte d'une nombre max qui est défini dans la config
- plus l'aquarium se remplie et plus il sera difficile de trouver un espace libre pour ajouter un poisson (ça peut demander plusieurs tentatives)
- A l'atteinte du nombre max de poissons, une alerte est émise
- Le bouton supprimer supprime aléatoirement un poisson
- Les collisions avec les bordures de l'aquarium ou avec d'autres poissons sont gérées
- Lorsque la taille de la fenêtre s'étend, les poissons s'adaptent au nouvel espace
- Le cas de la taille de la fenêtre qui diminue reste à gérer 

# remains to be done
- implémenter le reste des tests
- gérer le use case réduction taille de la fenêtre
(le use case fenêtre qui s'étend est géré)

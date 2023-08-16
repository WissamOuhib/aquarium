# aquarium
java aquarium game

# technos
java JDK 11.0.12.7-hotspot
javaFX SDK 20.0.2
apache maven 3.9.4
ItelliJ IDEA 2023.2

# architecture
MVC :

-model : la logique du jeu (fish, coordinate, ..)

-view : aquarium-view.fxml (vue principale, une seule fenêtre)

-controller : AquariumController pour aquarium-view.fxml.

#-

Singleton :

-classes Config, FishService, SoundManager

#-

Observer :

-interface AquariumPaneObserver 

-classe Config implémente AquariumPaneObserver

-classe AquariumController notifie les observateurs des changements de aquariumPane

#-

Factory :

-classe FishFactory : fabrique de poissons (potentielle évolution du jeu avec plusieurs types de poissons)

#-

Strategy :

-classe abstraite SoundStrategy

-classes concrètes FishPopInSoundStrategy, FishPopOutSoundStrategy,MaxFishReachedSoundStrategy

-classe SoundManager pour la gestion des bruitages

# remains to be done
-implémenter le reste des tests

-gérer le use case réduction taille de la fenêtre
(le use case fenêtre qui s'étend est géré)

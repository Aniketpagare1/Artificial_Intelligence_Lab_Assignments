% Define facts about animals and their habitats.
habitat(dog, land).
habitat(cat, land).
habitat(human, land).
habitat(snake, land).
habitat(bird, land).
habitat(fish, water).
habitat(dolphin, water).
habitat(eagle, air).
habitat(bat, air).

% Define rules to determine the category of an animal.
land_animal(X) :-
  habitat(X, land).

water_animal(X) :-
  habitat(X, water).

flying_animal(X) :-
  habitat(X, air).

% Define a predicate for user interaction.
identify_animal_category :-
  write('Welcome to the Animal Classifier!'), nl,
  write('Please enter the name of an animal to identify its category.'), nl,
  write('Animal name: '),
  read(Animal),
  classify_animal(Animal).

% Define a rule to identify and classify an animal.
classify_animal(Animal) :-
  land_animal(Animal),
  write(Animal), write(' is a land animal.'), nl.

classify_animal(Animal) :-
  water_animal(Animal),
  write(Animal), write(' is a water animal.'), nl.

classify_animal(Animal) :-
  flying_animal(Animal),
  write(Animal), write(' is a flying animal.'), nl.

classify_animal(Animal) :-
  write(Animal), write(' is not a known animal.'), nl.

% Main loop.
main :-
  identify_animal_category,
  write('To continue, enter another animal name or type "quit" to exit.'), nl,
  read(Input),
  (Input == quit) -> halt
  ; main.



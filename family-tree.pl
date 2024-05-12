female(trisha). 
female(joseph). 
male(rahil). 
male(karim).
male(kabil). 
parent(rahil,karim). 
parent(rahil,kabil). 
parent(karim,trisha). 
parent(karim,joseph).
son(X,Y):-male(X) ,parent(Y,X).
daughter(X, Y):- female(X) ,parent(Y ,X). 
sibling(X, Y):-
parent(Z, X) ,parent(Z,Y), X \= Y.

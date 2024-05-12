% Define facts about symptoms and diseases
symptom(john, fever).
symptom(john, cough).
symptom(jane, headache).
symptom(jane, fever).

% Define rules to determine diseases based on symptoms
has_flu(X) :- symptom(X, fever), symptom(X, cough).

has_migraine(X) :- symptom(X, headache), not(symptom(X, fever)).

% Query the expert system
diagnose_patient(X) :-
    has_flu(X),
    write(X), write(' has the flu.').

diagnose_patient(X) :-
    has_migraine(X),
    write(X), write(' has a migraine.').

diagnose_patient(X) :-
    write(X), write(' does not have a recognized illness.').

% Example queries
% diagnose_patient(john).   % Output: john has the flu.
% diagnose_patient(jane).   % Output: jane has a migraine.
% diagnose_patient(mary).   % Output: mary does not have a recognized illness.

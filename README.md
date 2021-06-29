# World-Simulation-Game
A simulation of a turn based game with animals and plants occupying tiles, and moving randomly

The aim of the project is to implement a virtual world simulator program,
which is to have the structure of a two-dimensional lattice of any given by the user
size NxM. In this world they will be
there were simple life forms with different behavior. Each of the organisms deals exactly
one field in the table, each field may contain at most one organism (v
in the event of a collision, one of them should be removed or moved).
The simulator is to be turn-based. In each turn, all organisms existing on
world are to take an action appropriate to their type. Some of them will be moving (organisms
animals), some will be stationary (plant organisms). In the event of a collision (one of
organisms will be in the same space as the other one) one of the organisms wins by killing (e.g.
wolf) or chasing away (e.g. turtle) a competitor. The order in which organisms move in a turn depends on them
initiatives. The animals with the highest initiative move first. In the case of animals
the order of the same initiative is decided by the principle of seniority (the first one moves longer
living). Winning an encounter depends on the strength of the organism, although there will be exceptions to this rule
(see Table 2). With equal strength, the attacked organism wins. A specific kind
the animal is to be a Man. Unlike animals, man does not move in a way
random. The direction of his movement is determined by the use of keys before the start of the turn
keyboard arrows. Man also has a special skill (see Appendix 1) which
can be activated with a separate button. The activated skill remains active for 5
consecutive turns, followed by its deactivation. Once deactivated, the skill cannot be
activated before the end of 5 consecutive turns. When starting the program on the board should be
appear several pieces of all kinds of animals and plants. The program window should
include a field in which information about the results of fighting, consumption of plants and others will be written
events taking place in the world.

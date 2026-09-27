package fr.esisar.in450.gomoku.gamecore.enums;

/**Liste des états possibles pour la fin d'une partie*/
public enum WinnerState
{
    /**Victoire du joueur blanc*/
    WHITE,

    /**Victoire du joueur noir*/
    BLACK,

    /**Partie non finie*/
    NONE,

    /**Egalité (plus de place sur le plateau)*/
    TIE
}

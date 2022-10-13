package binairo;

public class Binairo {
    private int nbrRangées, nbrColonnes;
    private char[][] contenuBinairo;

    public Binairo(char[][] contenuBinairo) { //CONSTRUCTEUR D'UN BINAIRO, DÉTERMINE LE NBR DE RANGÉES ET DE COLONNES DU BINAIRO À PARTIR DU TABLEAU
        this.nbrRangées = contenuBinairo.length;
        this.nbrColonnes = contenuBinairo.length;
        this.contenuBinairo = contenuBinairo;
    }

    private int getNbrRangées() {
        return nbrRangées;
    } //LES GETTERS ET SETTERS DES ATTRIBUTS D'UN BINAIRO

    private void setNbrRangées(int nbrRangées) {
        this.nbrRangées = nbrRangées;
    }

    private int getNbrColonnes() {
        return nbrColonnes;
    }

    private void setNbrColonnes(int nbrColonnes) {
        this.nbrColonnes = nbrColonnes;
    }

    public char[][] getContenuBinairo() {
        return contenuBinairo;
    }

    private void setContenuBinairo(char[][] contenuBinairo) {
        this.contenuBinairo = contenuBinairo;
    }

    public void ChangerDeBinairo(char[][] tableauContenuBinairo) {
        setContenuBinairo(tableauContenuBinairo);
    }

    /*RÉSOUDRE UN BINAIRO
     * 1 : LIRE CHAQUE RANGÉE
     * 2 : QUAND ON TROUVE UNE INCONNUE, LIRE LA RANGÉE ENTIÈRE ET LA COLONNE ENTIÈRE ET COMPTER LE NOMBRE DE 1 ET DE 0
     * 3 : SI L'INCONNUE EST LA SEULE INCONNUE DE LA LIGNE/RANGÉE, COMPLÉTER
     * 4 : SINON, REGARDER LES 2 CARACTÈRES À CÔTÉ
     * 5 : SI AUCUNE POSSIBILITÉ NE MARCHE, ON IGNORE ET ON MOVE ON */

    public void resoudre(Binairo binairo) { //LA MÉGA-MÉTHODE DE RÉSOLUTION D'UN BINAIRO. ELLE CONTIENT 4 MÉTHODES PRINCIPALES
        boolean binairoComplété = false;
        char[][] contenuBinairo = binairo.getContenuBinairo(); //GET LE CONTENU DU BINAIRO ET LE STOCK DANS UN TABLEAU DE CARACTÈRES
        int nbrRangées = binairo.getNbrRangées(); //GET LE NBR DE RANGÉES DU BINAIRO
        int nbrColonnes = binairo.getNbrColonnes(); //GET LE NBR DE COLONNES DU BINAIRO
        while (!binairoComplété) { //TANT QUE LE BINAIRO N'EST PAS FINI, ÉXÉCUTER LES MÉTHODES DE RÉSOLUTION
            for (int i = 0; i < nbrRangées; i++) {
                for (int j = 0; j < nbrColonnes; j++) { //DÉFILE LE BINAIRO CASE PAR CASE JUSQU'À CE QUE LE PROGRAMME DÉTECTE UN "?"
                    if (contenuBinairo[i][j] == '?') { //QUAND LE PROGRAMME DÉTECTE UN "?" --> IL NOTE SON "ADRESSE" (SA POSITION DANS LA COLONNE ET DANS LA RANGÉE)
                        int rangée = i;
                        int colonne = j;
                        compteurRangée(rangée, colonne, contenuBinairo, nbrRangées); //APPELLE LA MÉTHODE QUI COMPTE LE NBR DE 1 ET DE 0 DANS LA RANGÉE DE L'INCONNU
                        compteurColonne(rangée, colonne, contenuBinairo, nbrColonnes); //MÊME MÉTHODE QU'AU DESSUS, MAIS POUR LES COLONNES
                        verifCasesAffilees(rangée, colonne, contenuBinairo); //MÉTHODE POUR VÉRIFIER QU'IL N'Y A PAS PLUS QUE 2 CASES DE MÊME CONTENU D'AFFILÉ
                        contenuBinairo = binairo.getContenuBinairo(); //UPDATE LE CONTENU DU BINAIRO (JE SAIS PAS SI C'EST 100% NÉCESSAIRE MAIS JE L'AI MIS AU CAS OU)
                    }
                }
            }
            binairoComplété = verifBinairoComplet(contenuBinairo, nbrRangées, nbrColonnes); //MÉTHODE POUR VÉRIFIER SI LE BINAIRO EST COMPLET OU S'IL RESTE DES "?"
        }
    }

    private void verifCasesAffilees(int rangée, int colonne, char[][] contenuBinairo) { //MÉTHODE POUR VÉRIFIER S'IL N'Y A PAS PLUS QUE 2 CASES IDENTIQUES COLLÉES
        if (contenuBinairo[rangée][colonne] == '?') { //VÉRIFIE UNE DERNIÈRE FOIS QUE LE PROGRAMME A BIEN ENVOYÉ L'ADRESSE D'UNE INCONNUE (POUR NE PAS BRISER LE BINAIRO SI LE DÉFILEMENT DANS LE MAIN S'EST TROMPÉ)
            if ((rangée - 2 >= 0) && (contenuBinairo[rangée - 1][colonne] == contenuBinairo[rangée - 2][colonne]) && (contenuBinairo[rangée - 1][colonne] != '?')) { //PREMIER IF --> VÉRIFIE LES DEUX CASES À GAUCHE
                char contenuCase = contenuBinairo[rangée - 1][colonne];
                if (contenuCase == '0')
                    contenuBinairo[rangée][colonne] = '1';
                else if (contenuCase == '1')
                    contenuBinairo[rangée][colonne] = '0';
                setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
            }

            if ((rangée + 2 < contenuBinairo.length) && (contenuBinairo[rangée + 1][colonne] == contenuBinairo[rangée + 2][colonne]) && (contenuBinairo[rangée + 1][colonne] != '?')) { //DEUXIÈME IF --> VÉRIFIE LES DEUX CASES À DROITE
                char contenuCase = contenuBinairo[rangée + 1][colonne];
                if (contenuCase == '0')
                    contenuBinairo[rangée][colonne] = '1';
                else if (contenuCase == '1')
                    contenuBinairo[rangée][colonne] = '0';
                setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
            }

            if ((colonne - 2 >= 0) && (contenuBinairo[rangée][colonne - 1] == contenuBinairo[rangée][colonne - 2]) && (contenuBinairo[rangée][colonne - 1] != '?')) { //TROISIÈME IF --> VÉRIFIE LES DEUX CASES EN BAS
                char contenuCase = contenuBinairo[rangée][colonne - 1];
                if (contenuCase == '0')
                    contenuBinairo[rangée][colonne] = '1';
                else if (contenuCase == '1')
                    contenuBinairo[rangée][colonne] = '0';
                setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
            }

            if ((colonne + 2 < contenuBinairo.length) && (contenuBinairo[rangée][colonne + 1] == contenuBinairo[rangée][colonne + 2]) && (contenuBinairo[rangée][colonne + 1] != '?')) { //QUATRIÈME IF --> VÉRIFIE LES DEUX CASES EN HAUT
                char contenuCase = contenuBinairo[rangée][colonne + 1];
                if (contenuCase == '0')
                    contenuBinairo[rangée][colonne] = '1';
                else if (contenuCase == '1')
                    contenuBinairo[rangée][colonne] = '0';
                setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
            }

            if ((rangée - 1 >= 0) && (rangée + 1 < contenuBinairo.length) && (contenuBinairo[rangée - 1][colonne] == contenuBinairo[rangée + 1][colonne]) && (contenuBinairo[rangée - 1][colonne] != '?')) {
                char contenuCase = contenuBinairo[rangée - 1][colonne]; //CINQUIÈME IF --> VÉRIFIE LA CASE DIRECTEMENT À DROITE ET DIRECTEMENT À GAUCHE DE L'INCONNUE
                if (contenuCase == '0')
                    contenuBinairo[rangée][colonne] = '1';
                if (contenuCase == '1')
                    contenuBinairo[rangée][colonne] = '0';
                setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
            }

            if ((colonne - 1 >= 0) && (colonne + 1 < contenuBinairo.length) && (contenuBinairo[rangée][colonne - 1] == contenuBinairo[rangée][colonne + 1]) && (contenuBinairo[rangée][colonne - 1] != '?')) {
                char contenuCase = contenuBinairo[rangée][colonne - 1]; //SIXIÈME IF --> VÉRIFIE LA CASE DIRECTEMENT EN HAUT ET EN BAS DE L'INCONNUE
                if (contenuCase == '0')
                    contenuBinairo[rangée][colonne] = '1';
                if (contenuCase == '1')
                    contenuBinairo[rangée][colonne] = '0';
                setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
            }
        }
    }

    private boolean verifBinairoComplet(char[][] contenuBinairo, int nbrRangées, int nbrColonnes) {
        boolean binairoComplet = true;
        for (int i = 0; i < nbrRangées; i++)
            for (int j = 0; j < nbrColonnes; j++)
                if (contenuBinairo[i][j] == '?')
                    binairoComplet = false;
        return binairoComplet;
    }

    private void compteurRangée(int rangée, int colonne, char[][] contenuBinairo, int nbrRangées) { //MÉTHODE POUR VÉRIFIER LA RANGÉE DE L'INCONNUE
        if (contenuBinairo[rangée][colonne] == '?') { //VÉRIFIE UNE DERNIÈRE FOIS QUE LE PROGRAMME A BIEN ENVOYÉ L'ADRESSE D'UNE INCONNUE (POUR NE PAS BRISER LE BINAIRO SI LE DÉFILEMENT DANS LE MAIN S'EST TROMPÉ)
            int cpt1 = 0;
            int cpt0 = 0;
            for (int i = 0; i < nbrRangées; i++) { //DÉFILE LA RANGÉE ET COMPTE LE NOMBRE DE 1 ET DE 0
                if (contenuBinairo[i][colonne] == '1')
                    cpt1++;
                else if (contenuBinairo[i][colonne] == '0')
                    cpt0++;
            }
            if (cpt1 == nbrRangées / 2) //SI LA MOITIÉ DE LA RANGÉE EST D'UN TYPE, ALORS TOUTES LES CASES MANQUANTES SERONT DE L'AUTRE
                contenuBinairo[rangée][colonne] = '0';
            else if (cpt0 == nbrRangées / 2)
                contenuBinairo[rangée][colonne] = '1';
            setContenuBinairo(contenuBinairo); //UPDATE LE CONTENU DU BINAIRO
        }
    }

    private void compteurColonne(int rangée, int colonne, char[][] contenuBinairo, int nbrColonnes) { //EXACTEMENT LA MÊME MÉTHODE QU'AU DESSUS MAIS AVEC LES COLONNES
        if (contenuBinairo[rangée][colonne] == '?') {
            int cpt1 = 0;
            int cpt0 = 0;
            for (int i = 0; i < nbrColonnes; i++) {
                if (contenuBinairo[rangée][i] == '1')
                    cpt1++;
                else if (contenuBinairo[rangée][i] == '0')
                    cpt0++;
            }
            if (cpt1 == nbrColonnes / 2)
                contenuBinairo[rangée][colonne] = '0';
            else if (cpt0 == nbrColonnes / 2)
                contenuBinairo[rangée][colonne] = '1';
            setContenuBinairo(contenuBinairo);
        }
    }
}

package binairo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class TestBinairo {
    //PROGRAMME ÉCRIT ET TESTÉ PAR GABRIEL COMBY, NUMÉRO DE DA = 2055984
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        System.out.println("Bienvenue sur ce programme de résolution de Binairo!");
        boolean recommencerMenu = true;
        int numéroFichier;
        try { //TRY CATCH QUI PERMET D'ÉVITER DE FAIRE CRASHER LE PROGRAMME SI L'UTILISATEUR RENTRE AUTRE CHOSE QU'UN CHIFFRE DANS LE MENU
            while (recommencerMenu) {
                System.out.print("Veuillez entrer le numéro du binairo que vous souhaitez résoudre (1 à 9 inclus) ou 0 pour quitter : ");
                numéroFichier = Integer.parseInt(clavier.nextLine()); //ON UTILISE UN PARSE INT POUR NE PAS RENTRER DANS UNE LOOP DANS LE CATCH
                if (numéroFichier == 0) { //ACTIONS À EFFECTUER SI L'UTILISATEUR VEUT QUITTER LE PROGRAMME
                    System.out.println("Merci beaucoup d'avoir utilisé ce programme!");
                    recommencerMenu = false;
                } else { //UTILISATION NORMALE DU PROGRAMME (POUR RÉSOUDRE UN BINAIRO)
                    char[][] tabCharBinairo = chargerBinairo(numéroFichier); //APPELLE LA MÉTHODE POUR TRANSFORMER UN FICHIER EN TABLEAU DE CARACTÈRES
                    Binairo binairo = new Binairo(tabCharBinairo); //CRÉE UN BINAIRO À PARTIR DU TABLEAU OBTENU À LA LIGNE DU DESSUS
                    System.out.println("Résolution du binairo #" + numéroFichier);
                    System.out.println("----------------------------------------------");
                    System.out.println("Binairo non-résolu : ");
                    afficherBinairo(binairo); //AFFICHE LE BINAIRO AVANT SA RÉSOLUTION
                    System.out.println("----------------------------------------------");
                    binairo.resoudre(binairo); //APPELLE LA METHODE RESOUDRE DE LA CLASSE BINAIRO
                    System.out.println("Binairo résolu : ");
                    afficherBinairo(binairo); //AFFICHE LE BINAIRO RESOLU
                    exporterBinairo(binairo, numéroFichier); //EXPORTE LE BINAIRO DANS UN FICHIER
                    System.out.println("----------------------------------------------");
                    System.out.println("----------------------------------------------");
                }
            }
        } catch (NumberFormatException e) { //EMPECHE LE PROGRAMME DE CRASHER SI L'UTILISATEUR RENTRE AUTRE CHOSE QU'UN CHIFFRE DANS LE MENU
            System.out.println("Il semblerait que vous n'ayez pas rentré un chiffre! Veuillez relancer le programme!");
        }
    }

    public static char[][] chargerBinairo(int numéroFichier) { //MÉTHODE POUR TRANSFORMER UN FICHIER EN TABLEAU DE CARACTÈRES
        String nomDuFichier = numéroFichier + ".txt"; //GÉNÈRE LE NOM DU FICHIER DEPUIS LE NUMÉRO RENTRÉ PAR L'UTILISATEUR
        char[][] tableauChar = null;
        try {
            int numéroColonne = 0;
            BufferedReader lireFichier = new BufferedReader(new FileReader(nomDuFichier)); //OUVRE UN BUFFERED READER POUR LE FICHIER
            String ligne = lireFichier.readLine();
            int longueurTableau = ligne.length();
            tableauChar = new char[longueurTableau][longueurTableau]; //SET LA TAILLE DU TABLEAU À LA MÊME TAILLE QUE LE BINAIRO INSCRIT DANS LE FICHIER
            while (ligne != null) {
                char[] tableauSéparationString = ligne.toCharArray(); //TRANSFORME LA LIGNE LUE DANS LE FICHIER EN TABLEAU DE CARACTÈRES
                for (int i = 0; i < longueurTableau; i++)
                    tableauChar[numéroColonne][i] = tableauSéparationString[i]; //INSCRIT LA LIGNE DANS LE TABLEAU 2D DU BINAIRO
                numéroColonne++;
                ligne = lireFichier.readLine();
            }
            lireFichier.close();
        } catch (java.io.IOException erreurFichier) { //SI LE FICHIER N'EXISTE PAS, LE PROGRAMME AFFICHE CE MESSAGE ET SE FERME
            System.out.println("Le programme n'a pas réussi à charger le fichier! Veuillez le relancer!");
            System.exit(0);
        }
        return tableauChar; //RETURN LE TABLEAU 2D QUI CONTIENT LE BINAIRO
    }

    public static void afficherBinairo(Binairo binairo) { //MÉTHODE SIMPLE POUR AFFICHER LE CONTENU D'UN BINAIRO DANS LA CONSOLE
        char[][] contenuBinairo = binairo.getContenuBinairo(); //UTILISE LA MÉTHODE GET DE LA CLASSE BINAIRO
        for (int i = 0; i < contenuBinairo.length; i++) {
            for (int j = 0; j < contenuBinairo.length; j++) {
                System.out.print(contenuBinairo[i][j]);
            }
            System.out.println();
        }
    }

    public static void exporterBinairo(Binairo binairo, int numéroFichier) { //MÉTHODE POUR ÉCRIRE LE BINAIRO RÉSOLU DANS UN FICHIER
        try {
            char[][] contenuBinairo = binairo.getContenuBinairo();
            String nomDuFichier = numéroFichier + "solution.txt"; //GÉNÈRE LE NOM DU FICHIER À PARTIR DU NUMÉRO DU BINAIRO
            PrintWriter ecrireFichier = new PrintWriter(new FileWriter(nomDuFichier)); //PRINTWRITER POUR ÉCRIRE DANS UN FICHIER
            for (int i = 0; i < contenuBinairo.length; i++) {
                for (int j = 0; j < contenuBinairo.length; j++) {
                    ecrireFichier.print(contenuBinairo[i][j]);
                }
                ecrireFichier.println();
            }
            ecrireFichier.close();
        } catch (java.io.IOException erreurEcriture) { //SI POUR UNE RAISON QUELCONQUE LE PROGRAMME NE PEUT PAS ÉCRIRE, IL AFFICHE CE MESSAGE ET SE FERME
            System.out.println("Il semblerait que le programme n'a pas pu écrire le résultat dans un fichier! Veuillez le relancer!");
            System.exit(0);
        }
    }
}

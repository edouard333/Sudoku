package com.phenix.sudoku;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Edouard Jeanjean
 */
public class Sudoku {

    /**
     * Vérifie que le sudoku respecte les règles.
     *
     * @param sudoku
     *
     * @return
     */
    public static boolean verifier(int[][] sudoku) {
        // Vérifie que les colonnes contiennent bien des chiffres uniques :
        ArrayList<ArrayList<Integer>> liste = new ArrayList<ArrayList<Integer>>();
        for (int y = 0; y < sudoku.length; y++) {
            liste.add(new ArrayList<Integer>());
            for (int x = 0; x < sudoku[y].length; x++) {
                // Vérifie qu'il n'existe pas (zéro on s'en fout) :
                if (!liste.get(liste.size() - 1).contains(sudoku[y][x]) || sudoku[y][x] == 0) {
                    liste.get(liste.size() - 1).add(sudoku[y][x]);
                } // S'il existe déjà, il y a un souci :
                else {
                    return false;
                }
            }
        }

        // Vérifie que les colonnes contiennent bien des chiffres uniques :
        // Réinitialise :        
        liste = new ArrayList<ArrayList<Integer>>();
        for (int x = 0; x < sudoku.length; x++) {
            liste.add(new ArrayList<Integer>());
            for (int y = 0; y < sudoku.length; y++) {
                // Vérifie qu'il n'existe pas (zéro on s'en fout) :
                if (!liste.get(liste.size() - 1).contains(sudoku[y][x]) || sudoku[y][x] == 0) {
                    liste.get(liste.size() - 1).add(sudoku[y][x]);
                } // S'il existe déjà, il y a un souci :
                else {
                    return false;
                }
            }
        }

        // Vérifie que par bloque de 3 c'est unique les chiffres :
        for (int y = 0; y < sudoku.length; y += 3) {
            for (int x = 0; x < sudoku.length; x += 3) {

                // S'il y a un souci, c'est que ce n'est pas bon...
                if (!verifiebloc3x3(x, y, sudoku)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Vérifie par bloque de 3x3.
     *
     * @param offset_x
     * @param offset_y
     * @param bloc3x3
     *
     * @return
     */
    private static boolean verifiebloc3x3(int offset_x, int offset_y, int[][] bloc3x3) {
        ArrayList<Integer> liste = new ArrayList<Integer>();

        for (int y = offset_y; y < (offset_y + 3); y++) {
            for (int x = offset_x; x < (offset_x + 3); x++) {
                // Vérifie qu'il n'existe pas (zéro on s'en fout) :
                if (!liste.contains(bloc3x3[y][x]) || bloc3x3[y][x] == 0) {
                    liste.add(bloc3x3[y][x]);
                } // S'il existe déjà, il y a un souci :
                else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Complète s'il y deux nombres (ligne ou colonne).
     *
     * @param sudoku
     */
    public static void complete2(int[][] sudoku) {

    }

    /**
     * Vérifie si le sudoku est complet.
     *
     * @param sudoku
     * @return
     */
    public static boolean isComplete(int[][] sudoku) {
        for (int y = 0; y < sudoku.length; y++) {
            for (int x = 0; x < sudoku.length; x++) {
                if (sudoku[y][x] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Dans le cas où il y a qu'une case à remplire pour une ligne ou colonne.
     *
     * @param sudoku
     */
    public static void complete1(int[][] sudoku) {

        for (int y = 0; y < sudoku.length; y++) {
            System.out.println("L" + y + " : " + nombreCaseVidePourLigne(sudoku, y));

            if (nombreCaseVidePourLigne(sudoku, y) == 1) {
                System.out.println("ICI 1");
                ArrayList<Integer> liste_deja = new ArrayList<Integer>();
                int index_x = -1;

                for (int x = 0; x < sudoku[y].length; x++) {
                    if (sudoku[y][x] == 0) {
                        index_x = x;
                    } else {
                        liste_deja.add(sudoku[y][x]);
                    }
                }

                // Tri ASC.
                Collections.sort(liste_deja);

                liste_deja.add(0);

                parcoure:
                for (int i = 0; i < liste_deja.size(); i++) {
                    System.out.println(liste_deja.get(i) + " !=" + (i + 1));
                    if (liste_deja.get(i) != (i + 1)) {
                        sudoku[y][index_x] = (i + 1);
                        break parcoure;
                    }
                }

            }
        }

        for (int x = 0; x < sudoku.length; x++) {
            System.out.println("C" + x + " : " + nombreCaseVidePourColonne(sudoku, x));

            if (nombreCaseVidePourColonne(sudoku, x) == 1) {
                System.out.println("ICI 2");
                ArrayList<Integer> liste_deja = new ArrayList<Integer>();
                int index_y = -1;

                for (int y = 0; y < sudoku.length; y++) {
                    if (sudoku[y][x] == 0) {
                        index_y = y;
                    } else {
                        liste_deja.add(sudoku[y][x]);
                    }
                }

                // Tri ASC.
                Collections.sort(liste_deja);

                liste_deja.add(0);

                parcoure:
                for (int i = 0; i < liste_deja.size(); i++) {
                    System.out.println(liste_deja.get(i) + " !=" + (i + 1));
                    if (liste_deja.get(i) != (i + 1)) {
                        sudoku[index_y][x] = (i + 1);
                        break parcoure;
                    }
                }
            }
        }

    }

    /**
     * Vérifie qu'il reste qu'une place pour une colonne.
     *
     * @param sudoku
     * @param index_colonne
     * @return
     */
    public static int nombreCaseVidePourColonne(int[][] sudoku, int index_colonne) {
        int zero = 0;

        for (int y = 0; y < sudoku.length; y++) {

            if (sudoku[y][index_colonne] == 0) {
                zero++;
            }

        }

        return zero;
    }

    /**
     * Vérifie qu'il reste qu'une place pour une ligne.
     *
     * @param sudoku
     * @param index_ligne
     * @return
     */
    public static int nombreCaseVidePourLigne(int[][] sudoku, int index_ligne) {
        int zero = 0;

        for (int x = 0; x < sudoku.length; x++) {

            if (sudoku[index_ligne][x] == 0) {
                zero++;
            }

        }

        return zero;
    }

    /**
     * Affiche le Sudoku.
     *
     * @param sudoku
     */
    public static void affiche(int[][] sudoku) {
        for (int y = 0; y < sudoku.length; y++) {

            if (y % 3 == 0) {
                for (int x = 0; x < sudoku.length; x++) {
                    System.out.print("- ");
                }
                System.out.print("- ");
                System.out.print("- ");
                System.out.print("- ");
                System.out.println();
            }

            for (int x = 0; x < sudoku.length; x++) {
                if (x % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(sudoku[y][x] + " ");
            }

            System.out.println();
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int[][] sudoku = new int[][]{
            {1, 3, 0, 0, 5, 6, 9, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}, // Manque "4".
            {2, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0},
            {6, 0, 0, 0, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 0, 0, 0, 0, 0, 0, 0, 0},
            {7, 5, 3, 0, 2, 8, 6, 1, 4}
        };

        complete1(sudoku);

        affiche(sudoku);

        System.out.println("Sudoku complet : " + isComplete(sudoku));

        System.out.println("Sudoku valide : " + verifier(sudoku));
    }
}

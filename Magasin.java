// 
import java.util.Scanner;
import java.io.*;

public class Magasin {


    static final int MAX = 100;
    static String[] noms = new String[MAX];
    static double[] prix = new double[MAX];
    static int[] quantites = new int[MAX];

    static int nbProduits = 0;

    static Scanner sc = new Scanner(System.in);
    static void afficherMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Afficher inventaire");
        System.out.println("3. Rechercher un produit");
        System.out.println("4. Réaliser une vente");
        System.out.println("5. Afficher alertes stock (<5)");
        System.out.println("6. Sauvegarder données");
        System.out.println("7. Quitter");
        System.out.print("Votre choix : ");
    }

    static int saisirEntier(String message) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.println("Erreur ! Entrez un nombre valide.");
            sc.next();
            System.out.print(message);
        }
        return sc.nextInt();
    }

    
    static void ajouterProduit() {
        if (nbProduits >= MAX) {
            System.out.println("Stock plein !");
            return;
        }

        sc.nextLine(); 
        System.out.print("Nom du produit : ");
        noms[nbProduits] = sc.nextLine();

        System.out.print("Prix : ");
        prix[nbProduits] = sc.nextDouble();

        quantites[nbProduits] = saisirEntier("Quantité : ");

        nbProduits++;
        System.out.println("Produit ajouté !");
    }

    static int rechercherProduit(String nom) {
        for (int i = 0; i < nbProduits; i++) {
            if (noms[i].equalsIgnoreCase(nom)) {
                return i;
            }
        }
        return -1;
    }

    static void modifierPrix() {
        sc.nextLine();
        System.out.print("Nom du produit : ");
        String nom = sc.nextLine();

        int index = rechercherProduit(nom);

        if (index != -1) {
            System.out.print("Nouveau prix : ");
            prix[index] = sc.nextDouble();
            System.out.println("Prix modifié !");
        } else {
            System.out.println("Produit introuvable !");
        }
    }

    
    static void effectuerVente() {
        sc.nextLine();
        System.out.print("Nom du produit : ");
        String nom = sc.nextLine();

        int index = rechercherProduit(nom);

        if (index == -1) {
            System.out.println("Produit introuvable !");
            return;
        }

        int qte = saisirEntier("Quantité demandée : ");

        if (quantites[index] < qte) {
            System.out.println("Stock insuffisant !");
            return;
        }

        double total = prix[index] * qte;

        
        if (total > 1000) {
            total = total * 0.9;
            System.out.println("Remise de 10% appliquée !");
        }

        quantites[index] -= qte;

        System.out.println("\n===== TICKET =====");
        System.out.println("Produit : " + noms[index]);
        System.out.println("Quantité : " + qte);
        System.out.println("Total à payer : " + total + " DH");
        System.out.println("==================");
    }

   
    static void afficherStock() {
        System.out.println("\nNOM\tPRIX\tQTE\tVALEUR");
        for (int i = 0; i < nbProduits; i++) {
            double valeur = prix[i] * quantites[i];
            System.out.println(noms[i] + "\t" + prix[i] + "\t" + quantites[i] + "\t" + valeur);
        }
    }

    static void etatAlerte() {
        System.out.println("\nProduits en alerte (stock < 5)");
        for (int i = 0; i < nbProduits; i++) {
            if (quantites[i] < 5) {
                System.out.println(noms[i] + " - Stock : " + quantites[i]);
            }
        }
    }

   
    static void sauvegarderStock() {
        try {
            PrintWriter writer = new PrintWriter("stock.txt");

            for (int i = 0; i < nbProduits; i++) {
                writer.println(noms[i] + ";" + prix[i] + ";" + quantites[i]);
            }

            writer.close();
            System.out.println("Données sauvegardées !");
        } catch (Exception e) {
            System.out.println("Erreur sauvegarde !");
        }
    }

    static void chargerStock() {
        try {
            File file = new File("stock.txt");
            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String ligne = fileScanner.nextLine();
                String[] data = ligne.split(";");

                noms[nbProduits] = data[0];
                prix[nbProduits] = Double.parseDouble(data[1]);
                quantites[nbProduits] = Integer.parseInt(data[2]);

                nbProduits++;
            }

            fileScanner.close();
            System.out.println("Stock chargé !");
        } catch (Exception e) {
            System.out.println("Erreur chargement !");
        }
    }

   
    public static void main(String[] args) {

        chargerStock();

        int choix;

        do {
            afficherMenu();
            choix = saisirEntier("");

            switch (choix) {
                case 1: ajouterProduit(); break;
                case 2: afficherStock(); break;
                case 3:
                    sc.nextLine();
                    System.out.print("Nom à rechercher : ");
                    String nom = sc.nextLine();
                    int index = rechercherProduit(nom);
                    if (index != -1)
                        System.out.println("Trouvé : Prix = " + prix[index] + ", QTE = " + quantites[index]);
                    else
                        System.out.println("Introuvable !");
                    break;
                case 4: effectuerVente(); break;
                case 5: etatAlerte(); break;
                case 6: sauvegarderStock(); break;
                case 7:
                    sauvegarderStock();
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }

        } while (choix != 7);
    }
}
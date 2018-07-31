package com.example.angel.testmusical;

public class Compositores {


    public enum IMAGENES {
        INCOGNITA,
        DESCONOCIDO,
        SIN_IMAGEN
    }


    public static String getComposerImageName(String compositor) {

        switch (compositor) {
            case "Antonio Vivaldi":
                return "vivaldi.jpg";
            case "Antonín Leopold Dvořák":
                return "dvorak.jpg";
            case "Bedřich Smetana":
                return "smetana.jpg";
            case "Carl Maria Von Weber":
                return "weber.jpg";
            case "Christoph Willibald Von Gluck":
                return "gluck.jpg";
            case "Clément Philibert Léo Delibes":
                return "delibes_leo.jpg";
            case "Edvard Hagerup Grieg":
                return "grieg.jpg";
            case "Edward William Elgar":
                return "elgar.jpg";
            case "Felix Mendelssohn":
                return "mendelssohn.jpg";
            case "Franz Joseph Haydn":
                return "haydn.jpg";
            case "Franz Liszt":
                return "liszt.jpg";
            case "Franz Peter Schubert":
                return "schubert.jpg";
            case "Franz Von Suppé":
                return "suppe.jpg";
            case "Fryderyk Franciszek Chopin":
                return "chopin.jpg";
            case "George Frideric Handel":
                return "handel.jpg";
            case "Georges Bizet":
                return "bizet.jpg";
            case "Gioachino Rossini":
                return "rossini.jpg";
            case "Giuseppe Verdi":
                return "verdi.jpg";
            case "Gustav Mahler":
                return "mahler.jpg";
            case "Henry Purcell":
                return "purcell.jpg";

            case "Jacques Offenbach":
                break;

            case "Jean Sibelius":
                return "sibelius.jpg";
            case "Johann Pachelbel":
                return "pachelbel.jpeg";
            case "Johann Sebastian Bach":
                return "bach.jpg";
            case "Johann Strauss Jr.":
                return "strauss-jr.jpg";
            case "Johannes Brahms":
                return "brahms.jpg";
            case "Jules Massenet":
                return "massenet.jpg";
            case "Ludwig van Beethoven":
                return "beethoven.jpg";
            case "Maurice Ravel":
                return "ravel.jpg";
            case "Nikolai Rimsky-Korsakov":
                return "rimsky-korsakov.jpg";
            case "Nikolai Rubinstein":
                return "rubinstein.jpg ";
            case "Peter Ilyich Tchaikovsky":
                return "tchaikovsky.jpg";
            case "Richard Strauss":
                return "strauss.jpg";
            case "Richard Wagner":
                return "wagner.jpg";
            case "Robert Schumann":
                return "schumann.jpg";
            case "Tomaso Giovanni Albinoni":
                return "albinoni.jpg";
            case "Wilhelm Richard Wagner":
                return "wagner.jpg";
            case "Wolfgang Amadeus Mozart":
                return "mozart.jpg";


        }
        return getComposerImageName(IMAGENES.INCOGNITA);
    }

    public static String getComposerImageName(IMAGENES imagen) {

        switch (imagen) {

            case INCOGNITA:
                return "pregunta.png";
        }
        return "pregunta.png";
    }
}

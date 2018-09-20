package com.example.angel.testmusical;

public class TrakItem {

    public String compositor;
    public String musicalAsset;
    public String genere;
    public String albumImageArt;
    public String trakname;
    public String anho;


    public TrakItem(String trakname, String compositor, String musicalAsset, String genere, String anho) {
        this.trakname = trakname;
        this.compositor = compositor;
        this.genere = genere;
        this.musicalAsset = musicalAsset;
        this.anho = anho;
        this.albumImageArt = Compositores.getComposerImageName(compositor);
    }


    public String getDescription() {

        return trakname + "\n" + compositor + " (" + anho + ")";
    }

}

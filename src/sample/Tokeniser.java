package sample;

import java.io.*;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;


public class Tokeniser {
    public String fichier_stop_list="/home/ramzi/IdeaProjects/projet_RI/stoplist.txt";
    public String fichier_in;
    public String fichier_tokeniser;
    public ArrayList<String> array_stoplist = new ArrayList<String>();
    public String ligne_SL;
    public BufferedReader buff_SL = (BufferedReader) lire_fichier(fichier_stop_list);
    public String ligne;
    public String ligne_eliminer_mot_vide;
    public ArrayList<String> array_tokens = new ArrayList<String>();
    public ArrayList<String> array_mots_non_vides = new ArrayList<String>();
    public ArrayList<String> array_normalise = new ArrayList<String>();



    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getFichier_stop_list() {
        return fichier_stop_list;
    }

    public void setFichier_stop_list(String fichier_stop_list) {
        this.fichier_stop_list = fichier_stop_list;
    }

    public String getFichier_in() {
        return fichier_in;
    }

    public void setFichier_in(String fichier_in) {
        this.fichier_in = fichier_in;
    }

    public String getFichier_tokeniser() {
        return fichier_tokeniser;
    }

    public void setFichier_tokeniser(String fichier_tokeniser) {
        this.fichier_tokeniser = fichier_tokeniser;
    }

    public ArrayList<String> getArray_stoplist() {
        return array_stoplist;
    }

    public void setArray_stoplist(ArrayList<String> array_stoplist) {
        this.array_stoplist = array_stoplist;
    }

    public String getLigne_SL() {
        return ligne_SL;
    }

    public void setLigne_SL(String ligne_SL) {
        this.ligne_SL = ligne_SL;
    }

    public BufferedReader getBuff_SL() {
        return buff_SL;
    }

    public String getLigne() {
        return ligne;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    public String getLigne_eliminer_mot_vide() {
        return ligne_eliminer_mot_vide;
    }

    public void setLigne_eliminer_mot_vide(String ligne_eliminer_mot_vide) {
        this.ligne_eliminer_mot_vide = ligne_eliminer_mot_vide;
    }

    public ArrayList<String> getArray_tokens() {
        return array_tokens;
    }

    public void setArray_tokens(ArrayList<String> array_tokens) {
        this.array_tokens = array_tokens;
    }

    public ArrayList<String> getArray_mots_non_vides() {
        return array_mots_non_vides;
    }

    public void setArray_mots_non_vides(ArrayList<String> array_mots_non_vides) {
        this.array_mots_non_vides = array_mots_non_vides;
    }

    public ArrayList<String> getArray_normalise() {
        return array_normalise;
    }

    public void setArray_normalise(ArrayList<String> array_normalise) {
        this.array_normalise = array_normalise;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////



    public static BufferedReader lire_fichier(String f)
    {
        try {
            InputStream flux = new FileInputStream(f);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            return buff;
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }
    public static boolean is_voyelle(char x)
    {
        if (x == 'a' || x=='e' || x=='u' || x=='i' || x=='o' )
            return true;
        else return false;
    }

    public static int calculer_m(String mot) //probleme quand par ex abcde<fer
    {
        int n=mot.length(),m=0;
        char t[]=new char[n];
        for(int i=0;i<n;i++)
        {
            if(  (is_voyelle(mot.charAt(i))) )
                t[i]='V';
            else
                t[i]='C';
        }

        for(int i=0;i<n-1;i++)
            if(t[i]=='V' && t[i+1]=='C') m++;

        return m;
    }


    public void tok() throws IOException {

        BufferedReader buff = (BufferedReader) lire_fichier(fichier_in);
        BufferedReader buff_fichier_tokenise = (BufferedReader) lire_fichier(fichier_tokeniser);

        while ((ligne_SL = buff_SL.readLine())!= null){
            array_stoplist.add(ligne_SL);
        }
        while ((ligne = buff.readLine())!=null)
        {
            StringTokenizer mots = new StringTokenizer(ligne," _.:[]{}(),;");

            while (mots.hasMoreElements()) {
                array_tokens.add( (String)mots.nextElement()+"\n");
                System.out.println(array_tokens);
            }

        }
        buff.close();
        buff_SL.close();



        File fichier = new File(fichier_tokeniser);
        PrintWriter out = new PrintWriter(new FileWriter(fichier));

        for (int i=0; i<array_tokens.size(); i++)
        {
            if ( ! ( array_tokens.get(i).length()==1)  )
            {
                if (array_tokens.get(i).endsWith("</TITLE>") )
                {
                    if (array_tokens.get(i).substring(0, array_tokens.get(i).length()-8).endsWith("."))
                    {
                        out.write(array_tokens.get(i).substring(0, array_tokens.get(i).length()-9));
                        out.println();
                        out.write("</TITLE>");
                        out.println();
                    }

                    else
                    {
                        out.write(array_tokens.get(i).substring(0, array_tokens.get(i).length()-8));
                        out.println();
                        out.write("</TITLE>");
                        out.println();
                    }

                }

                else if (array_tokens.get(i).endsWith("</ABSTRACT>") )
                {

                    if (array_tokens.get(i).substring(0, array_tokens.get(i).length()-11).endsWith("."))
                    {
                        out.write(array_tokens.get(i).substring(0, array_tokens.get(i).length()-12));
                        out.println();
                        out.write("</ABSTRACT>");
                        out.println();
                    }

                    else
                    {
                        out.write(array_tokens.get(i).substring(0, array_tokens.get(i).length()-11));
                        out.println();
                        out.write("</ABSTRACT>");
                        out.println();
                    }

                }

                else if (array_tokens.get(i).endsWith("</DOCNO>"))
                {
                    if (array_tokens.get(i).substring(0, array_tokens.get(i).length()-8).endsWith("."))
                    {
                        out.write(array_tokens.get(i).substring(0, array_tokens.get(i).length()-9));
                        out.println();
                        out.write("</DOCNO>");
                        out.println();
                    }

                    else
                    {
                        out.write(array_tokens.get(i).substring(0, array_tokens.get(i).length()-8));
                        out.println();
                        out.write("</DOCNO>");
                        out.println();
                    }
                }

                else
                {
                    out.write(array_tokens.get(i));
                    out.println();
                }
            }
        }

        out.close();

//        elimination des motes vides



        while ((ligne_eliminer_mot_vide = buff_fichier_tokenise.readLine())!= null){
            array_mots_non_vides.add(ligne_eliminer_mot_vide.toLowerCase());
        }


        for (int i=0; i<array_mots_non_vides.size(); i++)
        {
            boolean exist = false;

            for (int j=0; j<array_stoplist.size(); j++)
            {
                if ( (array_mots_non_vides.get(i).toLowerCase().equals(array_stoplist.get(j).toLowerCase())) )
                {
                    exist = true;
                    j=array_stoplist.size();
                }
            }

            if(exist == false && array_mots_non_vides.get(i).length()>0)
            {
                //on appliques les 11 regles du sujet 3

                //regle 1

                if (array_mots_non_vides.get(i).toLowerCase().endsWith("sses"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0,array_mots_non_vides.get(i).length()-4).concat("es"));


                    //regle 2

                else if (array_mots_non_vides.get(i).toLowerCase().endsWith("ies"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0,array_mots_non_vides.get(i).length()-3).concat("i"));


                    //regle 3

                else if (array_mots_non_vides.get(i).toLowerCase().endsWith("s"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-1));


                    //regle4

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>0 && array_mots_non_vides.get(i).toLowerCase().endsWith("ed"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-2));


                    //regles5

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>0 && array_mots_non_vides.get(i).toLowerCase().endsWith("ing"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-3));

                    //regles6

                else if ( array_mots_non_vides.get(i).toLowerCase().endsWith("y"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-1).concat("i"));

                    //regles7

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>0 && array_mots_non_vides.get(i).toLowerCase().endsWith("ational"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-7).concat("ate"));

                    //regles8

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>0 && array_mots_non_vides.get(i).toLowerCase().endsWith("tional"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-6).concat("tion"));

                    //regles9

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>0 && array_mots_non_vides.get(i).toLowerCase().endsWith("izer"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-4).concat("ize"));

                    //regles10

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>0 && array_mots_non_vides.get(i).toLowerCase().endsWith("alize"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-5).concat("al"));

                    //regles11

                else if (calculer_m(array_mots_non_vides.get(i).toLowerCase())>1 && array_mots_non_vides.get(i).toLowerCase().endsWith("ize"))
                    array_normalise.add(array_mots_non_vides.get(i).toLowerCase().substring(0, array_mots_non_vides.get(i).length()-3));


                else array_normalise.add(array_mots_non_vides.get(i).toLowerCase());
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////




}

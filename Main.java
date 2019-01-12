package projet_RI;

import sample.Tokeniser;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Main  {



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


    public static ArrayList<Integer> pos_mot(ArrayList<String> list, String s){ //fonction qui prendre en parametre une liste de mot
        ArrayList<Integer> pos = new ArrayList();                    // et un mot est returne les position de mot dans la liste
        String str;                                         //sous forme d'une list (list des position)
        int i = 1;
        for (Iterator it2 = list.iterator(); it2.hasNext();) {
            str = (String) it2.next();
            if(s.equals(str))
                pos.add(i);
            i++;
        }
        return pos;
    }

    public  static class Freq
    {
        int f;
        String mot;

        Freq(int f,String mot)
        {
            this.f=f;
            this.mot=mot;
        }
    }

    public  static class Doc_freq
    {
        int f;
        String mot;
        Doc_freq(int f,String mot)
        {
            this.f=f;
            this.mot=mot;
        }
    }


    public static void main(String[] args) throws IOException {

       // Tokeniser tokeniser=new Tokeniser();
        //tokeniser.tok();

            ArrayList<String> array_tokens = new ArrayList<String>();
            ArrayList<String> array_stoplist = new ArrayList<String>();
            ArrayList<String> array_mots_non_vides = new ArrayList<String>();
            ArrayList<String> array_normalise = new ArrayList<String>();

            BufferedReader buff = (BufferedReader) lire_fichier("Corpus_OHSUMED.txt");
            BufferedReader buff_SL = (BufferedReader) lire_fichier("stoplist.txt");


            String ligne, ligne_SL, ligne_eliminer_mot_vide;
            String[] motss, mots_SL ;


            while ((ligne_SL = buff_SL.readLine())!= null){
                array_stoplist.add(ligne_SL);
            }

            while ((ligne = buff.readLine())!=null)
            {
//                mots = ligne.split(" ");

                StringTokenizer mots = new StringTokenizer(ligne," _.:[]{}(),;");

                while (mots.hasMoreElements()) {
                    array_tokens.add( (String)mots.nextElement()+"\n");

                }

            }


            buff.close();
            buff_SL.close();

        //        tokenisation

        File fichier = new File("/home/ramzi/IdeaProjects/projet_RI/fichier_indexation/tokenisation.txt");
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


        BufferedReader buff_fichier_tokenise = (BufferedReader) lire_fichier("/home/ramzi/IdeaProjects/projet_RI/fichier_indexation/tokenisation.txt");


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


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        File fich_inverse = new File("/home/ramzi/IdeaProjects/projet_RI/fichier_indexation/fich_inverse.txt");
        PrintWriter out_fichier_inverse = new PrintWriter(new FileWriter(fich_inverse));

        for (int i=0; i<array_normalise.size();i++)
        {

            out_fichier_inverse.write(array_normalise.get(i));
            out_fichier_inverse.println();

        }

        out_fichier_inverse.close();


        BufferedReader par1 = (BufferedReader) lire_fichier("/home/ramzi/IdeaProjects/projet_RI/fichier_indexation/fich_inverse.txt");


        String x;
        ArrayList<String> doc = new ArrayList<>();
        int freq;
        ArrayList<Freq> array_freq = new ArrayList<Freq>();


        while ((x = par1.readLine())!= null){
            doc.add(x);
        }

        // fichier inverse riche //////////////////////////////////////////////////////////////////////////////////////////////////

        int nbr_doc = 1, nbr_doc_i = 1;
        int nbr_occur=1;
        String balise ="title", balise_i;
        boolean appartient=false;
        int position = 0;

        ArrayList<Fichier_inverse> array_fich_inverse = new ArrayList<>();

        for (int i=0;i<doc.size();i++)
        {

            if (doc.get(i).equals("<doc>"))
            {
                nbr_doc_i ++;
                appartient = false;
                position = 0;
            }
            else if( doc.get(i).equals("<abstract>"))
            {
                balise_i = "abstract";
                position = 0;

            }
            else if( doc.get(i).equals("<docno>"))
            {
                balise = "docno";
                position = 0;

            }
            else if( doc.get(i).equals("<title>"))
            {
                balise = "title";
                position = 0;
            }

                for (int j=i+1;j<doc.size();j++) {


                    if (doc.get(j).equals("<doc>"))
                    {
                        nbr_doc ++;
                        appartient = false;
                        position = 0;
                    }

                    else if( doc.get(j).equals("<abstract>"))
                    {
                        balise = "abstract";
                        position = 0;

                    }

                    else if( doc.get(j).equals("<docno>"))
                    {
                        balise = "docno";
                        position = 0;

                    }

                    else if (doc.get(j).equals("<title>"))
                    {
                        balise = "title";
                        position = 0;

                    }
                    else if (doc.get(i).equals(doc.get(j)) )
                    {
                        Posting_item pos_item = new Posting_item(nbr_doc,0,position,balise);

                        Posting pos = new Posting(pos_item);

                        Fichier_inverse f_inverse = new Fichier_inverse(doc.get(i), nbr_occur, pos);

                        array_fich_inverse.add(f_inverse);

                        appartient = false;

                        appartient = true;
                        doc.remove(j);
                    }
                    position ++;
                }

        }

        for (int i = 0; i<array_fich_inverse.size(); i++)
        {
            for (int j=0;j<array_fich_inverse.get(i).posting.posting.size();j++)
            {
               /* System.out.println(array_fich_inverse.get(i).terme +" || "+array_fich_inverse.get(i).id_doc
                        +" || "+array_fich_inverse.get(i).posting.posting.get(j).id_doc
                        +" - "+array_fich_inverse.get(i).posting.posting.get(j).poids
                        +" - "+array_fich_inverse.get(i).posting.posting.get(j).position
                        +" - "+array_fich_inverse.get(i).posting.posting.get(j).balise);

               */

            }


        }

    }

    public static class Posting_item
    {
        int id_doc, poids, position;
        String balise;

        Posting_item(int id_doc, int poids, int position, String balise)
        {
            this.id_doc = id_doc;
            this.poids = poids;
            this.position = position;
            this.balise = balise;
        }
    }

    public static class Posting
    {
        ArrayList<Posting_item> posting = new ArrayList<Posting_item>();

        Posting(Posting_item positing)
        {
            this.posting.add(positing);
        }
    }

    public  static class Fichier_inverse
    {
        String terme;
        int id_doc;
        Posting posting ;

        Fichier_inverse(String terme, int id_doc, Posting posting)
        {
            this.terme = terme;
            this.id_doc = id_doc;
            this.posting = posting;
        }
    }



}
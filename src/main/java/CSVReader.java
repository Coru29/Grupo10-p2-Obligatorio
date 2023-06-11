import TADs.Hash.*;
import TADs.Heap.Heap;
import TADs.Lista.*;

import Entidades.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static void leerCSV(String[] args) {
        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
//                String user_name = record.get("user_name");
//                String user_location = record.get("user_location");
//                String user_description = record.get("user_description");
//                String user_created = record.get("user_created");
//                String user_followers = record.get("user_followers");
//                String user_friends = record.get("user_friends");
//                String user_favourites = record.get("user_favourites");
//                String user_verified = record.get("user_verified");
//                String date = record.get("date");
//                String text = record.get("text");
//                String hashtags = record.get("hashtags");
//                String source = record.get("source");
//                String is_retweet = record.get("is_retweet");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // ----------  ----------  Primer funcion ----------  ----------
    public static void topPilotos(int mesInput, int anoInput){

        // ----------  ----------  esto es para leer los pilotos del .txt  ----------  ----------
        LinearProbingHashTable<String, Integer> contadorPilotos = new LinearProbingHashTable<>();

        String archivo = "/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/drivers.txt";
        List<String> listaPilotos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listaPilotos.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convierte la lista de pilotos a un array
        String[] pilotos = listaPilotos.toArray(new String[0]);


        // Inicializar los contadores de todos los pilotos a 0
        for (String piloto : pilotos) {
            contadorPilotos.put(piloto, 0);
        }

        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int contadorTweets = 0;
            int conRecord = 0;

            for (CSVRecord record : records){

                conRecord ++;


                //recorro cada tupla
                String text = record.get("text");
                String date = record.get("date");

                //hago el split de la fecha por - para sacar dia mes y el año con la hora
                String[] parts = date.split("-");

                if (parts.length != 3) {
                    System.out.println("Fecha mal formada: " + date);
                    continue;
                }

                //saco las partes q me interesan
                String ano = parts[0];
                String mes = parts[1];

                int anoInt = Integer.parseInt(ano);
                int mesInt = Integer.parseInt(mes);

                if (anoInt == anoInput && mesInt == mesInput) contadorTweets ++;

                // Comprueba cada piloto
                for (String piloto : pilotos) {

                    String[] partesPiloto = piloto.split(" ");
                    String nombrePiloto = partesPiloto[0];
                    String apePiloto = partesPiloto[1];


                    if (text.contains(nombrePiloto) || text.contains(apePiloto) && anoInt == anoInput && mesInt == mesInput){
                        // Incrementa el contador para este piloto
                        contadorPilotos.put(piloto, contadorPilotos.get(piloto) + 1);
                    }
                }

            }
            System.out.println("tweets en ese mes: "+contadorTweets);
            System.out.println("tuplas leidas: "+conRecord);

            LL<Piloto> pilotosOrdenados = new LL<>();


// Imprimir los contadores
            for (String piloto : pilotos) {
//                System.out.println(piloto + ": " + contadorPilotos.get(piloto));
                int contPiloto = contadorPilotos.get(piloto);
                Piloto tempPiloto = new Piloto(piloto,contPiloto);
                pilotosOrdenados.add(tempPiloto);
            }

            pilotosOrdenados.sort();

            for (int i = 0; i < 10; i++) {
                System.out.println( i+1 +"." +pilotosOrdenados.get(i));
            }


        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ----------  ----------  Segunda funcion ----------  ----------
    public static void topUsuariosTweets() {
        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int contUsuarios = 0;

            LinearProbingHashTable<User, Integer> usuariosReg = new LinearProbingHashTable<>();

            for (CSVRecord record : records) {
                //recorro cada tupla y obtengo las datos q necesito

                String column = record.get("");
                int column1 = Integer.parseInt(column); // id del tweet

                String user_name = record.get("user_name"); //nombre

                String user_verified = record.get("user_verified");




                //creo el usuario y el tweet temporal
                User tempUsuario = new User(user_name,user_verified);
//                Tweet tempTweet = new Tweet(column1);


                if (!usuariosReg.contains(tempUsuario)){
                    usuariosReg.put(tempUsuario,1);
                    contUsuarios++;
                }else {
                    usuariosReg.put(tempUsuario, usuariosReg.get(tempUsuario) + 1);
                }


            }

            Heap<User, Integer> heapPrueba = new Heap<>();

            for (Entry<User, Integer> entry : usuariosReg.getEntries()) {
                User user = entry.getKey();
                int tweets = entry.getValue();
                heapPrueba.insert(user,tweets);

//                System.out.println("Usuario: " + user.getName() + ", Cantidad de Tweets: " + tweets + ", Verificado: " + user.getVerificado() );
            }



            heapPrueba.heapSort();
            LL<User> aver = heapPrueba.inOrder();


            for (int i = 0; i < 15; i++) {   //el coso esta al reves!!!!!
                System.out.println(i +1 +"." + aver.get(i).getName() + "Cantidad de Tweets: " + heapPrueba.find(aver.get(i)) + " verificado: " + aver.get(i).getVerificado());
            }
            System.out.println("cantidad de usuarios: "+contUsuarios);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // ----------  ----------  Tercera funcion ----------  ----------
    public static void hashtagDistintos (String fecha){
        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);


            LinearProbingHashTable<HashTag, Integer> hashTagsReg = new LinearProbingHashTable<>();

            for (CSVRecord record : records) {
                String date = record.get("date");
                String hashtags = record.get("hashtags");


                String[] hashtagsParts = hashtags.split(",");

                for (String parteHashtag : hashtagsParts) {
                    // le saco los corchetes
                    String parteHashSinCorcheteIzq = parteHashtag.replace("[", "");
                    String parteHashSinCorcheteDer = parteHashSinCorcheteIzq.replace("]", "");

                    // le saco las comillas simples y los espacios
                    String hashTagLimpio = parteHashSinCorcheteDer.replace("'", " ");
                    String hasTagReLimpio = hashTagLimpio.replace(" ", "");

                    HashTag hashTagIndividual = new HashTag(hasTagReLimpio);

                    if (!hashTagsReg.contains(hashTagIndividual) && date.contains(fecha)){ //en el caso que no este registrado
                        hashTagsReg.put(hashTagIndividual,1);
                    }else if (hashTagsReg.contains(hashTagIndividual) && date.contains(fecha)){ //en el caso q ya este registrado
                        hashTagsReg.put(hashTagIndividual, hashTagsReg.get(hashTagIndividual) +1);
                    }

                }

            }

            int contHashtags = 0;

            for (Entry<HashTag, Integer> entry : hashTagsReg.getEntries()){
                contHashtags ++;
                System.out.println(entry.getKey().getText());
            }

            System.out.println("Cantidad de hashtags: " + contHashtags);





        }catch (IOException e) {
            e.printStackTrace();
        }
    }



}

import TADs.Hash.*;
import TADs.Lista.*;
import TADs.MyArrayList;

import Entidades.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVReader {

    public static void leerCSV(String[] args) {
        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
//                String tweetID = record.get("");
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

        long startTime = System.nanoTime();



        // ----------  ----------  esto es para leer los pilotos del .txt  ----------  ----------
        LinearProbingHashTable<String, Integer> contadorPilotos = new LinearProbingHashTable<>();

        String archivo = "src/main/resources/drivers.txt";
        MyArrayList<String> listaPilotos = new MyArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listaPilotos.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// Inicializar los contadores de todos los pilotos a 0
        for (int i = 0; i < listaPilotos.size(); i++) {
            contadorPilotos.put(listaPilotos.get(i), 0);
        }


        try {
            Reader in = new FileReader("src/main/resources/f1_dataset.csv");
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
                for (int i = 0; i < listaPilotos.size(); i++) {
                    String piloto = listaPilotos.get(i);

                    //inicializo el nombre y ape
                    String nombrePiloto;
                    String apePiloto;
                    String[] partesPiloto = piloto.split(" ");

                    //los asigno y si es Nyck de Vries le saco el "de"
                    if (partesPiloto.length >= 3){
                         nombrePiloto = partesPiloto[0];
                         apePiloto = partesPiloto[2];
                    }else{
                         nombrePiloto = partesPiloto[0];
                         apePiloto = partesPiloto[1];
                    }


                    if ((text.contains(nombrePiloto) || text.contains(apePiloto)) && anoInt == anoInput && mesInt == mesInput){
                        // Incrementa el contador para este piloto
                        contadorPilotos.put(piloto, contadorPilotos.get(piloto) + 1);
                    }
                }

            }
            System.out.println("tweets en ese mes: "+contadorTweets);
            System.out.println("tuplas leidas: "+conRecord);

            LL<Piloto> pilotosOrdenados = new LL<>();


            for (int i = 0; i < listaPilotos.size(); i++) {
                String piloto = listaPilotos.get(i);
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

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();

    }


    // ----------  ----------  Segunda funcion ----------  ----------
    public static void topUsuariosTweets() {
        long startTime = System.nanoTime();

        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

//            LinearProbingHashTable<UsuarioConTweets, Integer> usuariosAOrdenarHash = new LinearProbingHashTable<>();

            LinearProbingHashTable<User, LL<Tweet> >usuariosReg = new LinearProbingHashTable<>();
            for (CSVRecord record : records) {
                // saco las 3 columnas q quiero
                String tweetIDstr = record.get("");
                int tweetId = Integer.parseInt(tweetIDstr);

                String user_name = record.get("user_name");
                String user_verified = record.get("user_verified");

                //creo un usuario y un tweet
                User tempUser = new User(user_name,user_verified);
                Tweet tempTweet = new Tweet(tweetId);

                //cargo todos los usuarios con su lista de tweets
                User registeredUser = usuariosReg.getUser(tempUser);

                if (registeredUser == null) {

                    tempUser.getListaTweets().add(tempTweet);
                    usuariosReg.put(tempUser, tempUser.getListaTweets());
                } else {
                    // Si el usuario ya existe, simplemente agrega el nuevo tweet al usuario registrado
                    registeredUser.getListaTweets().add(tempTweet);
                }

            }

            // ordeno los usuarios de a grupos para q no haga overflow
            int totalUsuarios = usuariosReg.getEntries().size();
            int lote = 3000;  //hay que buscar el mejor tamaño de lote
            LL<UsuarioConTweets> usuariosAOrdenar = new LL<>();

            UsuarioConTweets usuario15 = null;
            int sizeUsuario15 = 0;  // el tamaño de la lista del usuario 15

            for (int subLote = 0; subLote < totalUsuarios; subLote += lote) {
                System.out.println("Procesando lote que comienza en el usuario " + subLote);

                for (int i = subLote; i < Math.min(subLote + lote, totalUsuarios); i++) {
                    Entry<User, LL<Tweet>> tempUser = usuariosReg.getEntries().get(i);
                    LL<Tweet> listaTweetsAgregados = tempUser.getKey().getListaTweets();

                    // si el usuario q estoy comparando tiene size mas chico lo paso de largo
                    if (usuario15 == null || listaTweetsAgregados.size() > sizeUsuario15) {
                        // si no, creo un usuarios y lo añado
                        String name = tempUser.getKey().getName();
                        String verif = tempUser.getKey().getVerificado();
                        UsuarioConTweets tempUsuarioConTweets = new UsuarioConTweets(name,verif,listaTweetsAgregados);

                        usuariosAOrdenar.add(tempUsuarioConTweets);
                        usuariosAOrdenar.sort();

                        // actualizo el usuario 15
                        if (usuariosAOrdenar.size() > 15) {
                            usuario15 = usuariosAOrdenar.get(14);
                            sizeUsuario15 = usuario15.getListaTweetsCargados().size();
                        }
                    }
                }
            }




            System.out.println();
            System.out.println();

            for (int i = 0; i < 15; i++) {
                System.out.println(i+1 + "." +"Usuario: "+usuariosAOrdenar.get(i).getName() + " Verificado: " + usuariosAOrdenar.get(i).getIsVerified() + " Tweets: " + usuariosAOrdenar.get(i).getListaTweetsCargados().size());
            }
            System.out.println("Cantidad total de usuarios: " +usuariosReg.getEntries().size());


        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();
    }

    // ----------  ----------  Tercera funcion ----------  ----------
    public static void hashtagDistintos (String fecha){

        long startTime = System.nanoTime();

        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset.csv");
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

            System.out.println( "Cantidad de hashtags en el dia " + fecha+ ": "+hashTagsReg.getEntries().size());

            System.out.println();
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println();
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
            System.out.println("La función tardó " + roundedDuration + " segundos.");
            System.out.println();


        }catch (IOException e) {
            e.printStackTrace();
        }
    }



}


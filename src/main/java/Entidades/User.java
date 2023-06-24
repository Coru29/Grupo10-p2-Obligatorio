package Entidades;


import TADs.Hash.LinearProbingHashTable;
import TADs.Lista.*;
import TADs.MyArrayList;

public class User implements Comparable<User> {

    LL<Tweet> listaTweets = new LL<>();

    public LL<Tweet> getListaTweets() {
        return listaTweets;
    }

    private long id;

    private String name;

    private String verificado;

    private int CantidadTweets;




    public String getVerificado() {
        return verificado;
    }

    public String getName() {
        return name;
    }


    public int getCantidadTweets() {
        return CantidadTweets;
    }

    public void setCantidadTweets(int cantidadTweets) {
        CantidadTweets = cantidadTweets;
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.name);
    }


    public User(String name, String verificado) {
        this.name = name;
        this.verificado = verificado;
    }

//    public User(MyArrayList<Tweet> arrayTweets, String name, String verificado) {
//        this.arrayTweets = arrayTweets;
//        this.name = name;
//        this.verificado = verificado;
//    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return CantidadTweets == user.CantidadTweets;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }
}

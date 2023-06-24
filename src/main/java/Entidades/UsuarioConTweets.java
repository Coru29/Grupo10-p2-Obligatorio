package Entidades;

import TADs.Lista.LL;
import TADs.MyArrayList;

import java.util.Objects;

public class UsuarioConTweets   implements Comparable<UsuarioConTweets>{
    private String name;

    private String isVerified;

     LL<Tweet> listaTweetsCargados;

     private int cantidadTweetTotal;

    public int getCantidadTweetTotal() {
        return cantidadTweetTotal;
    }

    public UsuarioConTweets(String name, String isVerified, int cantidadTweetTotal) {
        this.name = name;
        this.isVerified = isVerified;
        this.cantidadTweetTotal = cantidadTweetTotal;
//        this.listaTweetsCargados = listaTweetsCargados;
    }

    public String getName() {
        return name;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public LL<Tweet> getListaTweetsCargados() {
        return listaTweetsCargados;
    }

    @Override
    public int compareTo(UsuarioConTweets o) {
        return Integer.compare(this.cantidadTweetTotal, o.cantidadTweetTotal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioConTweets that = (UsuarioConTweets) o;
        return Objects.equals(cantidadTweetTotal, that.cantidadTweetTotal);
    }


}

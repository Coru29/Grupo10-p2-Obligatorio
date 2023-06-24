package Entidades;

import TADs.Lista.LL;

public class Tweet implements Comparable<Tweet>{

    private long id;
    private String content;
    private String  source;
    private boolean isRetweet;
    private User usuarioDelTweet;

    private LL<HashTag> hashTagsTweet;


    // getters and setters...

    public Tweet(long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Tweet(long id, String content, User usuarioDelTweet, LL<HashTag> hashTagsTweet) {
//        this.id = id;
//        this.content = content;
//        this.usuarioDelTweet = usuarioDelTweet;
//        this.hashTagsTweet = hashTagsTweet;
//    }

    @Override
    public int compareTo(Tweet otherTweet) {
        return Long.compare(this.id, otherTweet.id);
    }
}

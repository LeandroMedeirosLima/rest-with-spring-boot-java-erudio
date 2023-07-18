package br.com.erudio;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(Long id, String content) {
        super();
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}

package Src;

public class ActorDirector {
    public String name;
    public String symbol;
    public String dob;

    public ActorDirector() {
        this.name = "";
        this.symbol = "";
        this.dob = "";
    }
    
    public ActorDirector(String name, String symbol, String dob) {
        this.name = name;
        this.symbol = symbol;
        this.dob = dob;
    }
    
    public ActorDirector(ActorDirector actor) {
        this.name = actor.name;
        this.symbol = actor.symbol;
        this.dob = actor.dob;
    }
    
}

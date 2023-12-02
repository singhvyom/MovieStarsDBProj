package Src;

public class Stock {
    public String symbol;
    public float price;
    public float closePrice;

    public ActorDirector getActorProfile(){
        ActorDirector actor = new ActorDirector();
        actor.name = "Robert Downey Jr.";
        actor.symbol = "RDJ";
        actor.dob = "April 4, 1965";
        return actor;
    };
    
}

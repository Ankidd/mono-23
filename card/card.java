package card;


import java.util.ArrayList;
import java.util.List;

import player.Player;
import main.MainBoard;
import manager.GameManager;

public class card {
    private String description;
    private String effect;
    private int amount;

    public card(String description,String effect,int amount){
        this.description=description;
        this.effect=effect;
        this.amount=amount;

    }

    public card(String description, String effect) {
        this.description=description;
        this.effect=effect;
        this.amount=0;
    }

    public String getDescription(){
        return this.description;
    }

    public String getEffect(){
        return this.effect;
    }

    public int getAmount(){
        return this.amount;
    }

    public void addMoney(Player player){
        player.addMoney(amount);
    }

    public void chargeMoney(Player player){
        player.chargeMoney(amount);
    }

    public void goStart(Player player,GameManager gameManager){
        player.goToStart(MainBoard.getInstance());
        gameManager.moveProcess();
    }

    public void goJail(Player player,GameManager gameManager){
        player.goToJail(MainBoard.getInstance());
        gameManager.moveProcess();
    }

    public void randomMove(Player player,GameManager gameManager){
        player.goToRandom(MainBoard.getInstance());
        gameManager.moveProcess();
    }

    public void outOfJailCard(Player player){
        player.setOutJailCard(true);
    }


    public void applyEffect(Player player,GameManager gameManager) {
        switch (effect) {
            case "add_money":
                addMoney(player);
                break;
            case "charge_money":
                chargeMoney(player);
                break;
            case "go_start":
                goStart(player,gameManager);
                break;
            case "go_to_jail":
                goJail(player,gameManager);
                break;
            case "get_out_of_jail_card":
                outOfJailCard(player);
                break;
            case "random_move":
                randomMove(player,gameManager);
                break;
        }
    }
    
    public static List<card> chanceCard(){
        List<card> ChanceCard=new ArrayList<>();
        ChanceCard.add(new card("Get $2000 from the Bank", "add_money", 2000));
        ChanceCard.add(new card("Lose $1000 to pay taxes", "charge_money", 10000));
        ChanceCard.add(new card("Go to START and get $3000", "go_start"));
        ChanceCard.add(new card("Go to Jail. Don't go through START.", "go_to_jail"));
        ChanceCard.add(new card("Get a Get Out of Jail Free card", "get_out_of_jail_card"));
        ChanceCard.add(new card("Move to any square", "random_move"));
        ChanceCard.add(new card("You won the Startup prize! Get $5000!", "add_money", 5000));
        ChanceCard.add(new card("You lost $700 due to online fraud", "charge_money", 700));

        return ChanceCard;
    }

    public static List<card> communityCard() {
    List<card> CommunityCard = new ArrayList<>();

    CommunityCard.add(new card("You received $1000 for your charitable act.", "add_money", 1000));
    CommunityCard.add(new card("You had to pay $500 for hospital fees.", "charge_money", 500));
    CommunityCard.add(new card("You received $1500 for your efforts at work.", "add_money", 1500));
    CommunityCard.add(new card("Pay $800 for tuition for a new course.", "charge_money", 800));
    CommunityCard.add(new card("You get to go to any unclaimed plot.", "random_move"));
    CommunityCard.add(new card("You get a surprise gift worth $1200!", "add_money", 1200));
    CommunityCard.add(new card("You get a Get Out of Jail Free card!", "get_out_of_jail_card"));
    CommunityCard.add(new card("You win the city lottery! Get $4000!", "add_money", 4000));

    return CommunityCard;
}
}

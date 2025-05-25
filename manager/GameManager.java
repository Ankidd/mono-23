package manager;
import java.util.*;
import player.Player;
import Property.Property;

public class GameManager {
    private List<Player> players;
    private List<Property> tiles;
    private int currentPlayerIndex;
    private int turnCounter;
    private String activeEvent;

    public GameManager(List<Player> players, List<Property> properties) {
        this.players = players;
        this.tiles = properties;
        this.currentPlayerIndex = 0;
        this.turnCounter = 0;
        this.activeEvent = null;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn(UIManager uiManager) {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        turnCounter++;
        if (turnCounter % 5 == 0) {
            triggerRandomEvent(uiManager);
        }
    }

    public boolean handleInsufficientFunds(Player player, int amountNeeded, UIManager uiManager) {
        uiManager.showMessage(player.getName() + " does not have enough money. Please sell property", 1500);

        int totalAsset = player.getMoney();
        for (Property p : player.getOwnedProperties()) {
            totalAsset += p.getValue() / 2;
        }

        if (totalAsset < amountNeeded) {
            uiManager.pauseAndShowMessage(player.getName() + " bankrup!");
            removePlayer(player);
            if (isGameOver()) {
                Player winner = getWinner();
                uiManager.pauseAndShowMessage(winner.getName() + " WIN!", 3000);
                System.exit(0);
            }
            return true;
        }

        uiManager.setSellMode(true);
        uiManager.setSellPlayer(player);
        uiManager.setSellTargetMoney(amountNeeded);
        return false;
    }

    public void removePlayer(Player player) {
        for (Property p : player.getOwnedProperties()) {
            p.setOwned(false);
            p.setOwner(null);
        }
        players.remove(player);
    }

    public boolean isGameOver() {
        return players.size() == 1;
    }

    public Player getWinner() {
        return isGameOver() ? players.get(0) : null;
    }

    public void triggerRandomEvent(UIManager uiManager) {
        String[] events = {"storm", "inflation", "crisis", "boom"};
        Random random = new Random();
        activeEvent = events[random.nextInt(events.length)];

        uiManager.playEventAnimation(activeEvent);

        switch (activeEvent) {
            case "storm":
                for (Property tile : tiles) {
                    tile.setValue((int)(tile.getValue() * 0.5));
                }
                break;
            case "inflation":
                for (Property tile : tiles) {
                    tile.setValue((int)(tile.getValue() * 1.3));
                }
                break;
            case "crisis":
                for (Player p : players) {
                    p.chargeMoney((int)(0.5 * p.getMoney()));
                }
                break;
            case "boom":
                for (Player p : players) {
                    p.addMoney((int)(0.1 * p.getMoney()));
                }
                break;
        }
    }

    public Property movePlayer(int steps) {
        Player player = getCurrentPlayer();
        player.setIndex((player.getIndex() + steps) % tiles.size());
        return tiles.get(player.getIndex());
    }

    public boolean upgradeProperty(Property property, Player player, UIManager uiManager) {
        if (!property.getOwner().equals(player)) {
            uiManager.pauseAndShowMessage("Can not upgrade property not owned by you!", 1500);
            return false;
        }

        int cost = (int)property.getUpgradeCost();
        if (player.getMoney() >= cost) {
            if (property.upgrade()) {
                player.chargeMoney(cost);
                uiManager.showUpgradeAnimation(property.getName(), property.getLevel());
                uiManager.pauseAndShowMessage("Upgrade " + property.getName() + " to level " + property.getLevel());
                return true;
            } else {
                uiManager.pauseAndShowMessage("Maximum level reached!", 1500);
            }
        } else {
            uiManager.pauseAndShowMessage("Not enough money to upgrade!", 1500);
        }
        return false;
    }

    public void animateTransfer(Player fromPlayer, Player toPlayer, String propertyName, DrawBoard drawBoard, Player player1, Player player2, UIManager uiManager) {
        String text = fromPlayer.getName() + " sold " + propertyName + " to " + toPlayer.getName();
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 1500) {
            drawBoard.draw(tiles, Arrays.asList(player1, player2), uiManager);
            for (Player p : players) {
                p.drawWithAnimation();
            }
            System.out.println(text);  // Thay thế cho vẽ GUI thực tế
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
package manager;

import java.util.*;
import java.awt.*;

import Property.Property;
import player.Player;

public class UIManager {
    private Object screen;
    private Object image;
    private Rectangle imageRect;
    private List<Property> properties;
    private List<Player> players;
    private GameManager gameManager;

    private Rectangle buyButton;
    private Rectangle skipButton;
    private Rectangle upgradeButton;
    private Rectangle skipUpgradeButton;

    private Property selectedProperty;
    private Player currentPlayer;
    private boolean showMenu = false;
    private boolean upgradeMenu = false;
    private boolean infoMode = false;
    private int scrollOffset = 0;
    private final int SCROLL_STEP = 30;

    private Rectangle infoButton = new Rectangle(1025, 570, 160, 50);
    private Rectangle closeButton = new Rectangle(865, 170, 50, 40);

    private long startTime = System.currentTimeMillis();
    private final long countdownDuration = 60 * 60 * 1000;

    private boolean sellMode = false;
    private Player sellPlayer;
    private int sellTargetMoney = 0;

    private List<AbstractMap.SimpleEntry<Rectangle, Object>> sellButtons = new ArrayList<>();

    public UIManager(Object screen, Object image, Rectangle imageRect, List<Property> properties, List<Player> players, GameManager gameManager) {
        this.screen = screen;
        this.image = image;
        this.imageRect = imageRect;
        this.properties = properties;
        this.players = players;
        this.gameManager = gameManager;
    }

    public void showSellMenu(Player player) {
        // TODO: implement rendering of sell menu
    }

    public void drawButton(Rectangle rect, Color color, String text, Color hoverColor) {
        // TODO: implement button drawing
    }

    public void drawCountdownTimer() {
        long elapsed = System.currentTimeMillis() - startTime;
        long timeLeft = Math.max(0, countdownDuration - elapsed);
        long minutes = timeLeft / 60000;
        long seconds = (timeLeft % 60000) / 1000;
        String timerText = String.format("%02d:%02d", minutes, seconds);
        // TODO: render timerText on screen
    }

    public void showMessage(String message, int delay) {
        // TODO: implement message box with delay
    }

    public void showPlayerInfoScrollable(Player player) {
        // TODO: implement scrollable info menu
    }

    public void showPurchaseMenu(Property property) {
        // TODO: implement purchase UI
    }

    public void showUpgradeMenu(Property property) {
        // TODO: implement upgrade UI
    }

    public void showUpgradeAnimation(String propertyName, int level) {
        // TODO: implement upgrade animation
    }

    public void pauseAndShowMessage(String message, Object screen, int delay) {
        // TODO: implement pause and message show
    }
}


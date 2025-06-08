    package main;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.util.List;

    import Property.Property;
    import Tile_base.*;
    import Define.*;
    import player.Player;

    public class MainBoard extends JPanel {
        private static MainBoard instance;
        private List<Property> properties;
        private List<Player> players;

        private MainBoard(List<Property> properties, List<Player> players) {
            this.properties = properties;
            this.players = players;
            this.setBackground(Color.WHITE);
        }

        public static MainBoard getInstance(List<Property> properties, List<Player> players) {
            if (instance == null) {
                instance = new MainBoard(properties, players);
            }
            return instance;
        }

        public static MainBoard getInstance() {
            if (instance == null) {
                throw new IllegalStateException("MainBoard has not been initialized yet!");
            }
            return instance;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard((Graphics2D) g);
        }

        private void drawBoard(Graphics2D g) {
    for (Property prop : properties) {
            BufferedImage icon = null;

            // Gán icon nếu là special tile
            switch (prop.getName()) {
                case "CHANCE" -> icon = image.chanceIcon;
                case "COMMUNITY CHEST" -> icon = image.CommunityChestIcon;
                case "TAX" -> icon = image.Taximg;
            }

            Small_Tile smallTile = new Small_Tile(
                    prop.getColor(), prop.getX(), prop.getY(), prop.getName(), prop.getValue(), icon
            );

            int id = prop.getId();

            if (id >= 1 && id <= 6) {
                smallTile.drawLeftTile(g);
            } else if (id >= 8 && id <= 20) {
                smallTile.drawUpTile(g);
            } else if (id >= 22 && id <= 27) {
                smallTile.drawRightTile(g);
            } else if (id >= 29 && id <= 41) {
                smallTile.drawDownTile(g);
            } else {
                // Big tile
                BufferedImage bigTileImage = switch (id) {
                    case 0 -> image.StartPointImg;
                    case 7 -> image.PrisonPointImg;
                    case 21 -> image.ParkImg;
                    case 28 -> image.visitPrisonImg;
                    default -> null;
                };

                if (bigTileImage != null) {
                    int angle = switch (id) {
                        case 0 -> 45;
                        case 7 -> 135;
                        case 21 -> 225;
                        case 28 -> 315;
                        default -> 0;
                    };
                    Big_Tile bigTile = new Big_Tile(prop.getX(), prop.getY(), prop.getName(), bigTileImage);
                    bigTile.drawBigTile(g, Define.BIG_TILE_SIZE, Define.BIG_TILE_SIZE, angle);
                }
            }

            // Vẽ nhà
            for (int i = 0; i < prop.getLevel(); i++) {
                int x = (id >= 1 && id <= 6 || id >= 22 && id <= 27)
                        ? prop.getX() - 12 + 5 * i
                        : prop.getX() + 5 * i;
                int y = (id >= 1 && id <= 6 || id >= 22 && id <= 27)
                        ? prop.getY() + 18
                        : prop.getY();
                g.drawImage(image.houseImg, x, y, null);
            }

            // Vẽ lễ hội
            if (prop.hasFestival()) {
                int posX = prop.getX() + (Define.SMALL_TILE_SIZE_X - image.festivalImg.getWidth()) / 2;
                int posY = prop.getY() + (Define.SMALL_TILE_SIZE_Y - image.festivalImg.getHeight()) / 2 + 5;
                g.drawImage(image.festivalImg, posX, posY, null);
            }
        }

        // Vẽ người chơi
        if (players != null) {
            for (Player player : players) {
                player.DrawPlayer(g);
            }
        }

        // Vẽ logo Monopoly giữa màn hình
        g.drawImage(image.getScaledImage(image.MonopolyImg, 1000, 263),
                Define.WIDTH / 2 - 500, Define.HEIGHT / 2 - 130, instance);
    }

}


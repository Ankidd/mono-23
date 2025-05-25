    package Property;
    import java.util.ArrayList;
    import java.util.List;
    import java.awt.*;

    import player.Player;
    import Define.Define;

    public class Property {
        private int id;
        private Color color;
        private int value;
        private String name;
        private boolean ownedFlag;
        private Player owner;  // Assume you have a Player class
        private int x,y;
        private int level;
        private boolean festival;

        // Constructor
        public Property(int id,int x,int y,Color color, int value, String name) {
            this.id = id;
            this.x=x;
            this.y=y;
            this.color = color;
            this.value = value;
            this.name = name;
            this.ownedFlag = false;
            this.owner = null;
            this.level=0;
            this.festival=false;
        }

        // Getters
        public int getId() {
            return id;
        }

        public Color getColor() {
            return color;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public boolean isOwned() {
            return ownedFlag;
        }

        public Player getOwner() {
            return owner;
        }

        public int getLevel(){
            return this.level;
        }

        public double getUpgradeCost(){
            return (this.value*0.6);
        }

        public boolean hasFestival(){
            return festival;
        }

        public int getX(){
            return this.x;
        }

        public int getY(){
            return this.y;
        }

        public void setOwner(Player owner) {
            this.owner = owner;
        }

        public void setOwned(boolean ownedFlag) {
            this.ownedFlag = ownedFlag;
        }
        
        public void setFestival(Boolean type){
            this.festival=true;
        }

        public void setValue(int value){
            this.value=value;
        }
    
        public boolean equals(Property other) {
            if (other == null) return false;
            return this.id == other.id &&
                this.color.equals(other.color) &&
                this.value == other.value &&
                this.name.equals(other.name);
        }

        public boolean upgrade(){
            if (this.level<5){
                this.level+=1;
                this.value+=(int)value*0.6;
                return true;
            }
            return false;
        }

        public static List<Property> createProperties() {
            List<Property> properties = new ArrayList<>();

            properties.add(new Property(0, 0, Define.HEIGHT-Define.BIG_TILE_SIZE, null, 0, "START"));
            // left
            properties.add(new Property(1,18,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X-12), Define.PASTEL_PINK, 5000, "Seoul"));
            properties.add(new Property(2,18,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*2-12), Define.PASTEL_PINK, 5000, "Busan"));
            properties.add(new Property(3,18,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*3-12), Define.PASTEL_PINK, 5000, "CHANCE"));
            properties.add(new Property(4,18,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*4-12), Define.PASTEL_ORANGE, 5000, "Ha Noi"));
            properties.add(new Property(5,18,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*5-12), Define.PASTEL_ORANGE, 5000, "Ho Chi Minh City"));
            properties.add(new Property(6,18,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*6-12), Define.PASTEL_ORANGE, 5000, "Bangkok"));

            properties.add(new Property(7, 0,0, null, 0, "PRISON"));
            // top
            properties.add(new Property(8,Define.BIG_TILE_SIZE,0, Define.PASTEL_YELLOW, 5000, "Dubai"));
            properties.add(new Property(9,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X,0, Define.PASTEL_YELLOW, 5000, "Sydney"));
            properties.add(new Property(10,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*2,0, Define.LAVENDER_MIST, 5000, "SPECIAL"));
            properties.add(new Property(11,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*3,0, Define.LAVENDER_MIST, 5000, "Chicago"));
            properties.add(new Property(12,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*4,0, Define.LIGHT_PEACH, 5000, "Las Vegas"));
            properties.add(new Property(13,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*5,0, Define.LIGHT_PEACH, 5000, "New York"));
            properties.add(new Property(14,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*6,0, Define.PASTEL_GREEN, 5000, "TAX"));
            properties.add(new Property(15,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*7,0, Define.PASTEL_GREEN, 5000, "Lyon"));
            properties.add(new Property(16,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*8,0, Define.PASTEL_GREEN, 5000, "Paris"));
            properties.add(new Property(17,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*9,0, Define.SOFT_LILAC, 5000, "CHANCE"));
            properties.add(new Property(18,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*10,0, Define.SOFT_LILAC, 5000, "Osaka"));
            properties.add(new Property(19,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*11,0, Define.PASTEL_BLUE, 5000, "Tokyo"));
            properties.add(new Property(20,Define.BIG_TILE_SIZE+Define.SMALL_TILE_SIZE_X*12,0, Define.PASTEL_BLUE, 5000, "Granada"));

            properties.add(new Property(21, Define.WIDTH-Define.BIG_TILE_SIZE, 0, null, 0, "PARK"));
            // right
            properties.add(new Property(22,Define.WIDTH-Define.SMALL_TILE_SIZE_Y+20,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X-12),Define.PALE_AQUA, 5000, "Madrid"));
            properties.add(new Property(23,Define.WIDTH-Define.SMALL_TILE_SIZE_Y+20,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*2-12), Define.PALE_AQUA, 5000, "Bali Beach"));
            properties.add(new Property(24,Define.WIDTH-Define.SMALL_TILE_SIZE_Y+20,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*3-12), Define.PASTEL_PURPLE, 5000, "HongKong"));
            properties.add(new Property(25,Define.WIDTH-Define.SMALL_TILE_SIZE_Y+20,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*4-12), Define.PASTEL_PURPLE, 5000, "Beijing"));
            properties.add(new Property(26,Define.WIDTH-Define.SMALL_TILE_SIZE_Y+20,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*5-12), Define.PASTEL_PURPLE, 5000, "Bandung"));
            properties.add(new Property(27,Define.WIDTH-Define.SMALL_TILE_SIZE_Y+20,(Define.HEIGHT-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*6-12), Define.LIGHT_CORAL, 5000, "Jakarta"));

            properties.add(new Property(28, Define.WIDTH-Define.BIG_TILE_SIZE, Define.HEIGHT-Define.BIG_TILE_SIZE, null, 0, "VISIT"));
            // bottom
            properties.add(new Property(29,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.LIGHT_CORAL, 5000, "Delhi"));
            properties.add(new Property(30,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*2,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.LIGHT_PERIWINKLE, 5000, "Mumbai"));
            properties.add(new Property(31,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*3,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.LIGHT_PERIWINKLE, 5000, "Berlin"));
            properties.add(new Property(32,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*4,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.PALE_ROSE, 5000, "Cyprus"));
            properties.add(new Property(33,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*5,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.PALE_ROSE, 5000, "Hamburg"));
            properties.add(new Property(34,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*6,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.PASTEL_BEIGE, 5000, "CHANCE"));
            properties.add(new Property(35,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*7,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.PASTEL_BEIGE, 5000, "Rome"));
            properties.add(new Property(36,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*8,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.MINT_CREAM, 5000, "Milan"));
            properties.add(new Property(37,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*9,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.MINT_CREAM, 5000, "Venice"));
            properties.add(new Property(38,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*10,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.MINT_CREAM, 5000, "Shanghai"));
            properties.add(new Property(39,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*11,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.MINT_CREAM, 5000, "London"));
            properties.add(new Property(40,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*12,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.MINT_CREAM, 5000, "Seville"));
            properties.add(new Property(41,Define.WIDTH-Define.BIG_TILE_SIZE-Define.SMALL_TILE_SIZE_X*13,Define.HEIGHT-Define.SMALL_TILE_SIZE_Y, Define.MINT_CREAM, 5000, "Ahmedabad"));

            return properties;
    }
    }

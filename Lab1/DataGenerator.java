import java.io.*;
import java.util.*;

public class DataGenerator {

    static final int NUM_STORES     = 100;
    static final int NUM_CUSTOMERS  = 1000;
    static final int NUM_SALES      = 2000;
    static final int NUM_PRODUCTS   = 100;
    static final int NUM_LINE_ITEMS = 4000;

    static final String OUTPUT_DIR = "/Users/aryanbaldua/CSC_369/";
    static final Random RAND = new Random(42);

    static final String[][] LOCATIONS = {
        {"New York",          "NY", "10001"}, {"Los Angeles",       "CA", "90001"},
        {"Chicago",           "IL", "60601"}, {"Houston",           "TX", "77001"},
        {"Phoenix",           "AZ", "85001"}, {"Philadelphia",      "PA", "19101"},
        {"San Antonio",       "TX", "78201"}, {"San Diego",         "CA", "92101"},
        {"Dallas",            "TX", "75201"}, {"San Jose",          "CA", "95101"},
        {"Austin",            "TX", "73301"}, {"Jacksonville",      "FL", "32099"},
        {"Fort Worth",        "TX", "76101"}, {"Columbus",          "OH", "43085"},
        {"Charlotte",         "NC", "28201"}, {"Indianapolis",      "IN", "46201"},
        {"San Francisco",     "CA", "94102"}, {"Seattle",           "WA", "98101"},
        {"Denver",            "CO", "80201"}, {"Nashville",         "TN", "37201"},
        {"Oklahoma City",     "OK", "73101"}, {"El Paso",           "TX", "79901"},
        {"Boston",            "MA", "02101"}, {"Portland",          "OR", "97201"},
        {"Las Vegas",         "NV", "89101"}, {"Memphis",           "TN", "38101"},
        {"Louisville",        "KY", "40201"}, {"Baltimore",         "MD", "21201"},
        {"Milwaukee",         "WI", "53201"}, {"Albuquerque",       "NM", "87101"},
        {"Tucson",            "AZ", "85701"}, {"Fresno",            "CA", "93701"},
        {"Sacramento",        "CA", "94203"}, {"Mesa",              "AZ", "85201"},
        {"Kansas City",       "MO", "64101"}, {"Atlanta",           "GA", "30301"},
        {"Omaha",             "NE", "68101"}, {"Colorado Springs",  "CO", "80901"},
        {"Raleigh",           "NC", "27601"}, {"Long Beach",        "CA", "90801"},
        {"Virginia Beach",    "VA", "23450"}, {"Minneapolis",       "MN", "55401"},
        {"Tampa",             "FL", "33601"}, {"New Orleans",       "LA", "70112"},
        {"Arlington",         "TX", "76001"}, {"Wichita",           "KS", "67201"},
        {"Aurora",            "CO", "80010"}, {"Bakersfield",       "CA", "93301"},
        {"Anaheim",           "CA", "92801"}, {"Santa Ana",         "CA", "92701"},
        {"Corpus Christi",    "TX", "78401"}, {"Riverside",         "CA", "92501"},
        {"Lexington",         "KY", "40501"}, {"St. Louis",         "MO", "63101"},
        {"Pittsburgh",        "PA", "15201"}, {"Stockton",          "CA", "95201"},
        {"Anchorage",         "AK", "99501"}, {"Cincinnati",        "OH", "45201"},
        {"St. Paul",          "MN", "55101"}, {"Greensboro",        "NC", "27401"},
        {"Toledo",            "OH", "43601"}, {"Newark",            "NJ", "07101"},
        {"Plano",             "TX", "75023"}, {"Henderson",         "NV", "89002"},
        {"Lincoln",           "NE", "68501"}, {"Buffalo",           "NY", "14201"},
        {"Fort Wayne",        "IN", "46801"}, {"Jersey City",       "NJ", "07302"},
        {"Chandler",          "AZ", "85224"}, {"St. Petersburg",    "FL", "33701"},
        {"Laredo",            "TX", "78040"}, {"Norfolk",           "VA", "23501"},
        {"Madison",           "WI", "53701"}, {"Durham",            "NC", "27701"},
        {"Lubbock",           "TX", "79401"}, {"Winston-Salem",     "NC", "27101"},
        {"Garland",           "TX", "75040"}, {"Glendale",          "AZ", "85301"},
        {"Hialeah",           "FL", "33010"}, {"Reno",              "NV", "89501"},
        {"Baton Rouge",       "LA", "70801"}, {"Irvine",            "CA", "92602"},
        {"Chesapeake",        "VA", "23320"}, {"Irving",            "TX", "75061"},
        {"Scottsdale",        "AZ", "85250"}, {"North Las Vegas",   "NV", "89030"},
        {"Fremont",           "CA", "94536"}, {"Gilbert",           "AZ", "85233"},
        {"San Bernardino",    "CA", "92401"}, {"Birmingham",        "AL", "35201"},
        {"Rochester",         "NY", "14601"}, {"Richmond",          "VA", "23218"},
        {"Spokane",           "WA", "99201"}, {"Des Moines",        "IA", "50301"},
        {"Montgomery",        "AL", "36101"}, {"Modesto",           "CA", "95351"},
        {"Fayetteville",      "NC", "28301"}, {"Tacoma",            "WA", "98401"},
        {"Shreveport",        "LA", "71101"}, {"Fontana",           "CA", "92335"},
        {"Moreno Valley",     "CA", "92551"}, {"Akron",             "OH", "44301"},
        {"Yonkers",           "NY", "10701"}, {"Little Rock",       "AR", "72201"},
        {"San Luis Obispo",   "CA", "93401"}, {"Providence",        "RI", "02901"},
        {"Oxnard",            "CA", "93030"}, {"Glendale",          "CA", "91201"},
    };

    static final String[] FIRST_NAMES = {
        "James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph",
        "Thomas", "Charles", "Christopher", "Daniel", "Matthew", "Anthony", "Mark",
        "Donald", "Steven", "Paul", "Andrew", "Kenneth", "Joshua", "Kevin", "Brian",
        "George", "Timothy", "Ronald", "Edward", "Jason", "Jeffrey", "Ryan",
        "Mary", "Patricia", "Jennifer", "Linda", "Barbara", "Elizabeth", "Susan",
        "Jessica", "Sarah", "Karen", "Lisa", "Nancy", "Betty", "Margaret", "Sandra",
        "Ashley", "Dorothy", "Kimberly", "Emily", "Donna", "Michelle", "Carol", "Amanda",
        "Melissa", "Deborah", "Stephanie", "Rebecca", "Sharon", "Laura", "Cynthia",
        "Katherine", "Amy", "Angela", "Shirley", "Anna", "Brenda", "Pamela", "Emma",
        "Nicole", "Helen", "Samantha", "Christine", "Debra", "Rachel", "Carolyn",
        "Janet", "Catherine", "Maria", "Heather", "Diane", "Julie", "Joyce",
        "Victoria", "Kelly", "Christina", "Lauren", "Joan", "Evelyn", "Olivia",
        "Judith", "Megan", "Cheryl", "Andrea", "Hannah", "Martha", "Jacqueline",
        "Frances", "Gloria", "Ethan", "Noah", "Liam", "Mason", "Logan", "Lucas"
    };

    static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
        "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson",
        "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
        "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen",
        "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera",
        "Campbell", "Mitchell", "Carter", "Roberts", "Gomez", "Phillips", "Evans",
        "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes", "Stewart",
        "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan",
        "Cooper", "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim",
        "Cox", "Ward", "Richardson", "Watson", "Brooks", "Chavez", "Wood", "James",
        "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes", "Price", "Alvarez", "Castillo",
        "Sanders", "Patel", "Myers", "Long", "Ross", "Foster", "Jimenez", "Powell"
    };

    static final String[] STREET_NAMES = {
        "Main", "Oak", "Pine", "Maple", "Cedar", "Elm", "Washington", "Lake", "Hill",
        "Sunset", "Park", "Forest", "Meadow", "Ridge", "Valley", "Spring", "River",
        "Liberty", "Highland", "Willow", "Lincoln", "Jefferson", "Madison", "Monroe",
        "Adams", "Jackson", "Cherry", "Walnut", "Chestnut", "Birch", "Magnolia",
        "Poplar", "Sycamore", "Hickory", "Ash", "Spruce", "Laurel", "Mulberry",
        "Dogwood", "Cypress", "Linden", "Alder", "Beech", "Hawthorn", "Locust",
        "Peach", "Plum", "Pecan", "Cottonwood", "Juniper", "Aspen", "Sequoia",
        "College", "University", "Market", "Church", "Harbor", "Ocean", "Bay",
        "Mountain", "Canyon", "Prairie", "Heritage", "Colonial", "Patriot", "Eagle",
        "Falcon", "Hawk", "Cardinal", "Sparrow", "Sunrise", "Moonlight", "Starlight"
    };

    static final String[] STREET_TYPES = {
        "St", "Ave", "Blvd", "Dr", "Ln", "Ct", "Pl", "Rd", "Way", "Cir"
    };

    static final String[] STORE_PREFIXES = {
        "Best", "Super", "Prime", "Grand", "Metro", "City", "National", "American",
        "United", "Pacific", "Atlantic", "Central", "Capital", "Crown", "Royal",
        "Elite", "Premier", "Select", "Choice", "Value", "Vista", "Summit", "Apex",
        "Beacon", "Sterling", "Cornerstone", "Heritage", "Liberty", "Diamond", "Keystone"
    };

    static final String[] STORE_SUFFIXES = {
        "Buy", "Mart", "Shop", "Store", "Market", "Depot", "Center", "Plaza",
        "Hub", "Zone", "World", "Place", "Square", "Outlet", "Express",
        "Direct", "Source", "Warehouse", "Gallery", "Exchange"
    };

    static final String[][] PRODUCT_DATA = {
        {"Samsung 65-inch 4K QLED TV",               "1299.99"},
        {"Apple MacBook Pro 14-inch",                 "1999.00"},
        {"Sony WH-1000XM5 Headphones",                "349.99"},
        {"Apple iPhone 15 Pro",                       "999.00"},
        {"Samsung Galaxy S24",                        "799.99"},
        {"Dell XPS 15 Laptop",                       "1749.99"},
        {"Apple iPad Air",                            "599.00"},
        {"LG 27-inch 4K Monitor",                    "449.99"},
        {"Bose QuietComfort 45 Headphones",           "279.99"},
        {"Nintendo Switch OLED",                      "349.99"},
        {"PlayStation 5 Console",                     "499.99"},
        {"Xbox Series X",                             "499.99"},
        {"Canon EOS R50 Camera",                      "679.99"},
        {"Nikon Z30 Camera",                          "759.95"},
        {"GoPro HERO12 Black",                        "399.99"},
        {"Apple Watch Series 9",                      "399.00"},
        {"Garmin Forerunner 265",                     "449.99"},
        {"Fitbit Charge 6",                           "159.95"},
        {"Amazon Echo Dot 5th Gen",                    "49.99"},
        {"Google Nest Hub Max",                       "229.99"},
        {"Apple AirPods Pro 2nd Gen",                 "249.00"},
        {"Samsung Galaxy Buds2 Pro",                  "179.99"},
        {"Anker Soundcore Liberty 4",                  "99.99"},
        {"Kindle Paperwhite 11th Gen",                "139.99"},
        {"Amazon Fire HD 10 Tablet",                  "149.99"},
        {"Logitech MX Master 3S Mouse",                "99.99"},
        {"Logitech MX Keys Keyboard",                 "109.99"},
        {"Microsoft Surface Pro 9",                  "1299.99"},
        {"HP Spectre x360 Laptop",                   "1399.99"},
        {"Asus ROG Zephyrus G14 Laptop",             "1599.99"},
        {"Razer BlackWidow V4 Keyboard",              "159.99"},
        {"SteelSeries Arctis Nova Pro Headset",       "249.99"},
        {"Corsair Vengeance RGB RAM 32GB",            "109.99"},
        {"Samsung 990 Pro SSD 2TB",                   "179.99"},
        {"WD Black SN850X SSD 1TB",                   "119.99"},
        {"Seagate Backup Plus 4TB HDD",                "89.99"},
        {"TP-Link AXE5400 WiFi 6E Router",            "249.99"},
        {"Netgear Orbi 960 Mesh WiFi",                "699.99"},
        {"Eero Pro 6E Mesh Router",                   "299.99"},
        {"Philips Hue Starter Kit",                   "199.99"},
        {"Ring Video Doorbell Pro 2",                 "249.99"},
        {"Arlo Pro 4 Security Camera",                "199.99"},
        {"iRobot Roomba j7+ Robot Vacuum",            "599.99"},
        {"Dyson V15 Detect Vacuum",                   "749.99"},
        {"Shark IQ Robot Vacuum",                     "399.99"},
        {"Instant Pot Duo 7-in-1",                     "99.95"},
        {"Ninja Foodi 9-in-1 Air Fryer",              "199.99"},
        {"KitchenAid Stand Mixer",                    "449.99"},
        {"Breville Barista Express Espresso",         "699.95"},
        {"Nespresso Vertuo Next Coffee Maker",        "159.00"},
        {"Cuisinart 14-Cup Food Processor",           "199.99"},
        {"Vitamix E310 Explorian Blender",            "349.95"},
        {"Keurig K-Cafe Coffee Maker",                "179.99"},
        {"Weber Spirit II E-310 Gas Grill",           "499.00"},
        {"Traeger Pro 575 Pellet Grill",              "799.99"},
        {"Coleman 54-Qt Steel Belted Cooler",         "149.99"},
        {"Yeti Tundra 45 Cooler",                     "349.99"},
        {"Hydro Flask 32oz Water Bottle",              "49.95"},
        {"Stanley Adventure Quencher 40oz",            "44.99"},
        {"The North Face Apex Flex Jacket",           "199.00"},
        {"Columbia Bugaboo II Fleece Jacket",         "169.99"},
        {"Patagonia Nano Puff Vest",                  "149.00"},
        {"Levi's 501 Original Fit Jeans",              "69.50"},
        {"Nike Air Max 270 Sneakers",                 "150.00"},
        {"Adidas Ultraboost 22 Shoes",                "189.99"},
        {"New Balance 990v5 Shoes",                   "174.99"},
        {"Timberland 6-inch Premium Boots",           "198.00"},
        {"Seiko 5 Sports Automatic Watch",            "249.99"},
        {"Casio G-Shock GA2100 Watch",                 "99.99"},
        {"Ray-Ban Wayfarer Sunglasses",               "193.00"},
        {"Oakley Holbrook Sunglasses",                "196.00"},
        {"Samsonite Checked Luggage 28-inch",         "279.99"},
        {"Away The Carry-On Suitcase",                "295.00"},
        {"Osprey Farpoint 40 Travel Pack",            "179.99"},
        {"Thule Crossover 2 Backpack 30L",            "209.95"},
        {"Ergotron LX Desk Monitor Arm",              "159.99"},
        {"BenQ PD2705U 4K Monitor",                   "549.99"},
        {"VIVO Dual Monitor Stand",                    "59.99"},
        {"Elgato Stream Deck MK.2",                   "149.99"},
        {"Blue Yeti USB Microphone",                  "129.99"},
        {"Elgato 4K60 Pro MK.2 Capture Card",        "199.99"},
        {"Anker 737 Power Bank 24000mAh",             "149.99"},
        {"Mophie Powerstation USB-C XXL",              "99.95"},
        {"Belkin MagSafe 3-in-1 Charger",             "149.99"},
        {"Anker 65W 4-Port USB-C Charger",             "45.99"},
        {"Cable Matters USB-C Hub 11-in-1",            "64.99"},
        {"Tile Mate Bluetooth Tracker 4-Pack",         "59.99"},
        {"Apple AirTag 4-Pack",                        "99.00"},
        {"Roku Streaming Stick 4K",                    "49.99"},
        {"Amazon Fire TV Stick 4K Max",                "59.99"},
        {"Chromecast with Google TV 4K",               "49.99"},
        {"Epson EcoTank ET-4850 Printer",             "349.99"},
        {"Brother HL-L2350DW Laser Printer",          "129.99"},
        {"HP DeskJet 4155e Printer",                   "84.99"},
        {"Fujifilm Instax Mini 12 Camera",             "79.99"},
        {"Polaroid Now+ Instant Camera",              "149.99"},
        {"Leatherman Wave+ Multi-Tool",               "109.95"},
        {"Victorinox Swiss Army Knife",                "49.99"},
    };

    // Returns a random {city, state, zip} from the locations table
    static String[] randomLocation() {
        return LOCATIONS[RAND.nextInt(LOCATIONS.length)];
    }

    // Returns a random street address string like "4521 Maple Ave"
    static String randomStreetAddress() {
        int number        = 100 + RAND.nextInt(9900);
        String streetName = STREET_NAMES[RAND.nextInt(STREET_NAMES.length)];
        String streetType = STREET_TYPES[RAND.nextInt(STREET_TYPES.length)];
        return number + " " + streetName + " " + streetType;
    }

    // Returns a random phone number in "XXX XXX XXXX" format
    static String randomPhone() {
        int areaCode = 200 + RAND.nextInt(800);
        int prefix   = 200 + RAND.nextInt(800);
        int line     = 1000 + RAND.nextInt(9000);
        return areaCode + " " + prefix + " " + line;
    }

    // Returns a random date string in YYYY/MM/DD format within the given year range
    static String randomDate(int startYear, int endYear) {
        int year  = startYear + RAND.nextInt(endYear - startYear + 1);
        int month = 1 + RAND.nextInt(12);
        int maxDay;
        if (month == 2) {
            maxDay = 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        } else {
            maxDay = 31;
        }
        int day = 1 + RAND.nextInt(maxDay);
        return String.format("%04d/%02d/%02d", year, month, day);
    }

    // Returns a random time string in HH:MM:SS format
    static String randomTime() {
        int hour   = RAND.nextInt(24);
        int minute = RAND.nextInt(60);
        int second = RAND.nextInt(60);
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    // Returns a random store name by combining a prefix and suffix word
    static String randomStoreName() {
        String prefix = STORE_PREFIXES[RAND.nextInt(STORE_PREFIXES.length)];
        String suffix = STORE_SUFFIXES[RAND.nextInt(STORE_SUFFIXES.length)];
        return prefix + " " + suffix;
    }

    // Writes stores.txt: ID, storeName, address, city, ZIP, state, phoneNumber
    static void generateStores() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_DIR + "stores.txt"))) {
            for (int id = 1; id <= NUM_STORES; id++) {
                String   name    = randomStoreName();
                String   address = randomStreetAddress();
                String[] loc     = randomLocation();
                String   phone   = randomPhone();
                pw.println(id + ", " + name + ", " + address + ", "
                         + loc[0] + ", " + loc[2] + ", " + loc[1] + ", " + phone);
            }
        }
        System.out.println("Generated stores.txt  (" + NUM_STORES + " records)");
    }

    // Writes customers.txt: ID, name, birthDate, address, city, ZIP, state, phoneNumber
    static void generateCustomers() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_DIR + "customers.txt"))) {
            for (int id = 1; id <= NUM_CUSTOMERS; id++) {
                String firstName = FIRST_NAMES[RAND.nextInt(FIRST_NAMES.length)];
                String lastName  = LAST_NAMES[RAND.nextInt(LAST_NAMES.length)];
                String name      = firstName + " " + lastName;
                String birthDate = randomDate(1950, 2005);
                String address   = randomStreetAddress();
                String[] loc     = randomLocation();
                String phone     = randomPhone();
                pw.println(id + ", " + name + ", " + birthDate + ", " + address + ", "
                         + loc[0] + ", " + loc[2] + ", " + loc[1] + ", " + phone);
            }
        }
        System.out.println("Generated customers.txt (" + NUM_CUSTOMERS + " records)");
    }

    // Writes sales.txt: ID, date, time, storeID, customerID (every store and customer covered at least once)
    static void generateSales() throws IOException {
        List<Integer> shuffledCustomers = new ArrayList<>();
        for (int i = 1; i <= NUM_CUSTOMERS; i++) shuffledCustomers.add(i);
        Collections.shuffle(shuffledCustomers, RAND);

        List<Integer> shuffledStores = new ArrayList<>();
        for (int i = 1; i <= NUM_STORES; i++) shuffledStores.add(i);
        Collections.shuffle(shuffledStores, RAND);

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_DIR + "sales.txt"))) {
            for (int id = 1; id <= NUM_SALES; id++) {
                int storeID, customerID;
                if (id <= NUM_STORES) {
                    storeID    = shuffledStores.get(id - 1);
                    customerID = shuffledCustomers.get(id - 1);
                } else if (id <= NUM_CUSTOMERS) {
                    storeID    = 1 + RAND.nextInt(NUM_STORES);
                    customerID = shuffledCustomers.get(id - 1);
                } else {
                    storeID    = 1 + RAND.nextInt(NUM_STORES);
                    customerID = 1 + RAND.nextInt(NUM_CUSTOMERS);
                }
                String date = randomDate(2015, 2024);
                String time = randomTime();
                pw.println(id + ", " + date + ", " + time + ", " + storeID + ", " + customerID);
            }
        }
        System.out.println("Generated sales.txt     (" + NUM_SALES + " records)");
    }

    // Writes products.txt: ID, description, price
    static void generateProducts() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_DIR + "products.txt"))) {
            for (int id = 1; id <= NUM_PRODUCTS; id++) {
                String[] product = PRODUCT_DATA[(id - 1) % PRODUCT_DATA.length];
                pw.println(id + ", " + product[0] + ", " + product[1]);
            }
        }
        System.out.println("Generated products.txt  (" + NUM_PRODUCTS + " records)");
    }

    // Writes lineItems.txt: ID, salesID, productID, quantity (every sale covered at least once)
    static void generateLineItems() throws IOException {
        List<Integer> shuffledSales = new ArrayList<>();
        for (int i = 1; i <= NUM_SALES; i++) shuffledSales.add(i);
        Collections.shuffle(shuffledSales, RAND);

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_DIR + "lineItems.txt"))) {
            for (int id = 1; id <= NUM_LINE_ITEMS; id++) {
                int salesID;
                if (id <= NUM_SALES) {
                    salesID = shuffledSales.get(id - 1);
                } else {
                    salesID = 1 + RAND.nextInt(NUM_SALES);
                }
                int productID = 1 + RAND.nextInt(NUM_PRODUCTS);
                int quantity  = 1 + RAND.nextInt(10);
                pw.println(id + ", " + salesID + ", " + productID + ", " + quantity);
            }
        }
        System.out.println("Generated lineItems.txt (" + NUM_LINE_ITEMS + " records)");
    }

    // Entry point: runs all five generators in order
    public static void main(String[] args) throws IOException {
        System.out.println("Generating data files in: " + OUTPUT_DIR);
        System.out.println("-------------------------------------------");
        generateStores();
        generateCustomers();
        generateSales();
        generateProducts();
        generateLineItems();
        System.out.println("-------------------------------------------");
        System.out.println("Done.");
    }
}

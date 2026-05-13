public class Record implements Comparable<Record> {
    private int id;
    private String name;
    private double price;

    public Record(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + price;
    }

    @Override
    public int compareTo(Record other) {
        if (this.price > other.price) return -1;
        if (this.price < other.price) return 1;
        if (this.id > other.id) return 1;
        if (this.id < other.id) return -1;
        return 0;
    }
}

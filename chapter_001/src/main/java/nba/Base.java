package nba;

public class Base {
    private int id;
    private int version;
    private String name;


    public Base(int id, int version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void incVersion() {
        this.version += 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

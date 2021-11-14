package Models;

/**
 * An abstract class for the model People.
 * @author  @zhiheng97
 * @since   10 October 2021
 */
public abstract class People {

    private int id;
    private String name;

    /**
     * Constructs a People object by the id and name of the person
     * @param id The id of the person
     * @param name The name of the person
     */
    public People(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for attribute id;
     * @return id of object
     */
    public int getId() { return id; }

    /**
     * Getter for attribute name
     * @return name of object
     */
    public String getName() { return name; }
}

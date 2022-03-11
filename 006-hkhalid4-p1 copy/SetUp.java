import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;

/**
* This class runs the prototype menu.
*/
public class SetUp {
    /**
    * The main method calls the Menu class and passes in the input text file.
    * @param args String - the name of the input text file
    * @throws FileNotFoundException if input file is not found
    */
    public static void main(String[] args) throws FileNotFoundException {
        String usage = "Usage: SetUp [inputFile] ";

        if (args.length != 1) {
            System.out.println(usage);
            return;
        }

        Menu menu = new Menu(args[0]);
        if (menu.init()) {
            menu.step();
            menu.finish();
        }
    }
}

/**
* This class is one record from the housing survey.
*/
class SurveyRecord {
    /**
    * An 8 digit unique id.
    */
    private String control;
    /**
    * The total number of rooms in the unit.
    */
    private String totalRooms;
    /**
    * A flag indicating if the unit has a garage.
    */
    private String garage;
    /**
    * The number of stories in the building.
    */
    private String stories;
    /**
    * A flag indicating whether the unit is a condo.
    */
    private String condo;
    /**
    * A flag indicating whether people bike to work.
    */
    private String bike;
    /**
    * A flag indicating whether people walk to work.
    */
    private String walk;
    /**
    * Type of building (detached, multi-unit, RV, etc.).
    */
    private String bld;
    /**
    * Number of people living in the unit.
    */
    private String numPeople;
    /**
    * Flags indicating multple generations living in unit.
    */
    private String multiGen;
    /**
    * A year indicating the decade in which the unit was built.
    */
    private String yrBuilt;
    /**
    * How many floors are in the unit.
    */
    private String unitFloors;
    /**
    * How many bedrooms are in the unit.
    */
    private String bedrooms;

    /**
    * Constructor.
    * @param line String - a line from the input text file.
    */
    public SurveyRecord(String line) {
        try (Scanner scanRecord = new Scanner(line)) {
            scanRecord.useDelimiter(",");
            if (scanRecord.hasNext()) {
                this.control = scanRecord.next();
                this.totalRooms = scanRecord.next();
                this.garage = scanRecord.next();
                this.stories = scanRecord.next();
                this.condo = scanRecord.next();
                this.bike = scanRecord.next();
                this.walk = scanRecord.next();
                this.bld = scanRecord.next();
                this.numPeople = scanRecord.next();
                this.multiGen = scanRecord.next();
                this.yrBuilt = scanRecord.next();
                this.unitFloors = scanRecord.next();
                this.bedrooms = scanRecord.next();
            }
        }
    }

    /**
    * method to print one record.
    */
    public void printRecord() {
        System.out.println("control: " + control);
        System.out.println("totalRooms: " + totalRooms);
        System.out.println("garage: " + garage);
        System.out.println("stories: " + stories);
        System.out.println("condo: " + condo);
        System.out.println("bike: " + bike);
        System.out.println("walk: " + walk);
        System.out.println("bld: " + bld);
        System.out.println("numPeople: " + numPeople);
        System.out.println("multiGen: " + multiGen);
        System.out.println("yrBuilt: " + yrBuilt);
        System.out.println("unitFloors: " + unitFloors);
        System.out.println("bedrooms: " + bedrooms);
        System.out.println();
    }


    /**
    * get method.
    *@return - control 
    */
    public String getControl() {
        return control;
    }

    /**
    * get totalRooms.
    *@return - totalRooms 
    */
    public String getTotalRooms() {
        return totalRooms;
    }
    /**
    * get numPeople.
    *@return - numPeople 
    */
    public String getNumPeople() {
        return numPeople;
    }
    /**
    * get yrBuilt.
    *@return - yrBuilt 
    */
    public String getYrBuilt() {
        return yrBuilt;
    }
    /**
    * get bedRooms.
    *@return - bedRooms 
    */
    public String getBedrooms() {
        return bedrooms;
    }
    /**
    * get multiGen.
    *@return - multiGen 
    */
    public String getMultiGen() {
        return multiGen;
    }
    /**
    * get unitFloors.
    *@return - unitFloors 
    */
    public String getUnitFloors() {
        return unitFloors;
    }
    /**
    * get bld.
    *@return - bld 
    */
    public String getBld() {
        return bld;
    }
    /**
    * get walk.
    *@return - walk 
    */
    public String getWalk() {
        return walk;
    }
    /**
    * get bike.
    *@return - bike 
    */
    public String getBike() {
        return bike;
    }
    /**
    * get condo.
    *@return - condo 
    */
    public String getCondo() {
        return condo;
    }
    /**
    * get garage.
    *@return - garage 
    */
    public String getGarage() {
        return garage;
    }
    /**
    * get stories.
    *@return - stories 
    */
    public String getStories() {
        return stories;
    }
}
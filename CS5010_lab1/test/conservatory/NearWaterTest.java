package conservatory;

import conservatory.subclass.ShoreBird;
import conservatory.subclass.Waterfowl;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class NearWaterTest {

    @Test
    public void testWaterfowlImplementsNearWater() {
        Waterfowl duck = new Waterfowl("Mallard", "common duck", false,
                Set.of(Food.insects), 2, "Lake Superior");

        assertTrue("Waterfowl should implement NearWater", duck instanceof NearWater);
    }

    @Test
    public void testShoreBirdImplementsNearWater() {
        ShoreBird sandpiper = new ShoreBird("Sandpiper", "small wading bird", false,
                Set.of(Food.insects), 2, "Atlantic Ocean");

        assertTrue("ShoreBird should implement NearWater", sandpiper instanceof NearWater);
    }

    @Test
    public void testWaterfowlBodyOfWater() {
        Waterfowl goose = new Waterfowl("Canada Goose", "migratory bird", false,
                Set.of(Food.vegetation), 2, "Mississippi River");

        NearWater nearWater = goose;
        assertEquals("Should return correct body of water", "Mississippi River",
                nearWater.getBodyOfWater());
    }

    @Test
    public void testShoreBirdBodyOfWater() {
        ShoreBird plover = new ShoreBird("Piping Plover", "endangered shore bird", false,
                Set.of(Food.insects), 2, "Cape Cod Bay");

        NearWater nearWater = plover;
        assertEquals("Should return correct body of water", "Cape Cod Bay",
                nearWater.getBodyOfWater());
    }

    @Test
    public void testNearWaterPolymorphism() {
        NearWater bird1 = new Waterfowl("Swan", "elegant bird", false,
                Set.of(Food.vegetation), 2, "Lake Geneva");
        NearWater bird2 = new ShoreBird("Heron", "wading bird", false,
                Set.of(Food.fish), 2, "Florida Everglades");

        assertEquals("Swan should be at Lake Geneva", "Lake Geneva", bird1.getBodyOfWater());
        assertEquals("Heron should be at Florida Everglades", "Florida Everglades",
                bird2.getBodyOfWater());
    }

    @Test
    public void testDifferentWaterTypes() {
        // Test various water body types
        NearWater lakebird = new Waterfowl("Duck", "lake duck", false,
                Set.of(Food.insects), 2, "Great Lakes");
        NearWater riverbird = new ShoreBird("Kingfisher", "river bird", false,
                Set.of(Food.fish), 2, "Amazon River");
        NearWater oceanbird = new Waterfowl("Albatross", "ocean bird", false,
                Set.of(Food.fish), 2, "Pacific Ocean");
        NearWater pondbird = new ShoreBird("Moorhen", "pond bird", false,
                Set.of(Food.vegetation), 2, "Local Pond");

        assertEquals("Should be at Great Lakes", "Great Lakes", lakebird.getBodyOfWater());
        assertEquals("Should be at Amazon River", "Amazon River", riverbird.getBodyOfWater());
        assertEquals("Should be at Pacific Ocean", "Pacific Ocean", oceanbird.getBodyOfWater());
        assertEquals("Should be at Local Pond", "Local Pond", pondbird.getBodyOfWater());
    }

    @Test
    public void testMultipleBirdsSameWater() {
        String sharedWater = "Chesapeake Bay";

        NearWater bird1 = new Waterfowl("Teal", "small duck", false,
                Set.of(Food.seeds), 2, sharedWater);
        NearWater bird2 = new ShoreBird("Sandpiper", "wader", false,
                Set.of(Food.insects), 2, sharedWater);
        NearWater bird3 = new Waterfowl("Merganser", "diving duck", false,
                Set.of(Food.fish), 2, sharedWater);

        assertEquals("All should be at same water", sharedWater, bird1.getBodyOfWater());
        assertEquals("All should be at same water", sharedWater, bird2.getBodyOfWater());
        assertEquals("All should be at same water", sharedWater, bird3.getBodyOfWater());
    }

    @Test
    public void testEmptyWaterBodyName() {
        NearWater bird = new Waterfowl("Duck", "test", false,
                Set.of(Food.seeds), 2, "");

        assertEquals("Should handle empty string", "", bird.getBodyOfWater());
    }

    @Test
    public void testLongWaterBodyName() {
        String longName = "The Very Long Name Of A Specific Body Of Water In A Remote Location";
        NearWater bird = new ShoreBird("Curlew", "large wader", false,
                Set.of(Food.aquatic_invertebrates), 2, longName);

        assertEquals("Should handle long names", longName, bird.getBodyOfWater());
    }

    @Test
    public void testWaterBodyWithSpecialCharacters() {
        String specialName = "Lake O'Hara - North America's Gem!";
        NearWater bird = new Waterfowl("Loon", "diving bird", false,
                Set.of(Food.fish), 2, specialName);

        assertEquals("Should handle special characters", specialName, bird.getBodyOfWater());
    }

    @Test
    public void testNearWaterInterfaceMethod() {
        // Verify interface can be used directly
        NearWater nearWater = new Waterfowl("Pelican", "large water bird", false,
                Set.of(Food.fish), 2, "Gulf of Mexico");

        assertNotNull("getBodyOfWater should not return null", nearWater.getBodyOfWater());
        assertFalse("getBodyOfWater should not return empty for this bird",
                nearWater.getBodyOfWater().isEmpty());
    }
}
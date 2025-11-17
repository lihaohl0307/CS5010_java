package conservatory;

import conservatory.subclass.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class AviaryTest {
    private Aviary aviary;
    private Bird parrot;
    private Bird eagle;
    private Bird duck;
    private Bird penguin;
    private Bird owl;
    private Bird extinctBird;

    @Before
    public void setUp() {
        aviary = new Aviary("Test Location");

        parrot = new Parrot("African Grey", "intelligent", false,
                Set.of(Food.seeds), 2, 100, "Hello");

        eagle = new BirdOfPrey("Bald Eagle", "predator", false,
                Set.of(Food.fish), 2);

        duck = new Waterfowl("Duck", "waterfowl", false,
                Set.of(Food.insects), 2, "Lake");

        penguin = new FlightlessBird("Penguin", "flightless", false,
                Set.of(Food.fish), 0);

        owl = new Owl("Barn Owl", "nocturnal", false,
                Set.of(Food.small_mammals), 2);

        extinctBird = new Parrot("Dodo", "extinct", true,
                Set.of(Food.seeds), 2, 0, "");
    }

    @Test
    public void testNewAviaryIsEmpty() {
        assertTrue("New aviary should be empty", aviary.isEmpty());
        assertFalse("New aviary should not be full", aviary.isFull());
    }

    @Test
    public void testAddBirdToEmptyAviary() {
        assertTrue("Should add bird to empty aviary", aviary.addBird(parrot));
        assertFalse("Aviary should not be empty", aviary.isEmpty());
        assertEquals("Aviary should have 1 bird", 1, aviary.getBirds().size());
    }

    @Test
    public void testCannotAddExtinctBird() {
        assertFalse("Should not accept extinct bird", aviary.acceptBird(extinctBird));
        assertFalse("Should not add extinct bird", aviary.addBird(extinctBird));
    }

    @Test
    public void testAviaryCapacity() {
        // Add 5 birds
        for (int i = 0; i < 5; i++) {
            Bird p = new Parrot("Parrot" + i, "test", false,
                    Set.of(Food.seeds), 2, 10, "Hi");
            assertTrue("Should add bird " + i, aviary.addBird(p));
        }

        assertTrue("Aviary should be full", aviary.isFull());

        // Try to add the 6th bird
        Bird extraBird = new Parrot("Extra", "test", false,
                Set.of(Food.seeds), 2, 10, "No");
        assertFalse("Should not add 6th bird", aviary.addBird(extraBird));
    }

    @Test
    public void testBirdOfPreyExclusive() {
        aviary.addBird(eagle);

        assertTrue("Should accept another bird of prey", aviary.acceptBird(
                new BirdOfPrey("Hawk", "hunter", false, Set.of(Food.small_mammals), 2)));

        assertFalse("Should not accept parrot with bird of prey", aviary.acceptBird(parrot));
        assertFalse("Should not accept owl with bird of prey", aviary.acceptBird(owl));
    }

    @Test
    public void testWaterfowlExclusive() {
        aviary.addBird(duck);

        assertTrue("Should accept another waterfowl", aviary.acceptBird(
                new Waterfowl("Goose", "waterfowl", false, Set.of(Food.vegetation), 2, "River")));

        assertFalse("Should not accept parrot with waterfowl", aviary.acceptBird(parrot));
    }

    @Test
    public void testFlightlessBirdExclusive() {
        aviary.addBird(penguin);

        assertTrue("Should accept another flightless bird", aviary.acceptBird(
                new FlightlessBird("Emu", "large flightless", false, Set.of(Food.vegetation), 0)));

        assertFalse("Should not accept flying bird with flightless bird", aviary.acceptBird(parrot));
    }

    @Test
    public void testNormalBirdsMixing() {
        aviary.addBird(parrot);

        assertTrue("Owls should mix with parrots", aviary.acceptBird(owl));

        // Add the owl
        aviary.addBird(owl);

        // Create another normal bird
        Bird shoreBird = new ShoreBird("Sandpiper", "shore bird", false,
                Set.of(Food.insects), 2, "Ocean");

        assertTrue("Shore birds should mix with parrots and owls", aviary.acceptBird(shoreBird));
    }

    @Test
    public void testNormalBirdsCannotMixWithRestricted() {
        aviary.addBird(parrot);

        assertFalse("Should not accept bird of prey with normal birds", aviary.acceptBird(eagle));
        assertFalse("Should not accept waterfowl with normal birds", aviary.acceptBird(duck));
        assertFalse("Should not accept flightless bird with normal birds", aviary.acceptBird(penguin));
    }

    @Test
    public void testSignEmptyAviary() {
        String sign = aviary.sign();
        assertTrue("Sign should contain location", sign.contains("Test Location"));
        assertTrue("Sign should indicate no birds", sign.contains("No birds"));
    }

    @Test
    public void testSignWithParrot() {
        aviary.addBird(parrot);
        String sign = aviary.sign();

        assertTrue("Sign should contain bird type", sign.contains("African Grey"));
        assertTrue("Sign should contain vocab info", sign.contains("vocab"));
        assertTrue("Sign should contain favorite saying", sign.contains("Hello"));
    }

    @Test
    public void testSignWithShoreBird() {
        Bird shoreBird = new ShoreBird("Sandpiper", "small shore bird", false,
                Set.of(Food.insects), 2, "Pacific Ocean");
        aviary.addBird(shoreBird);

        String sign = aviary.sign();
        assertTrue("Sign should contain body of water", sign.contains("Pacific Ocean"));
    }

    @Test
    public void testHasFlightlessBird() {
        assertFalse("Should not have flightless bird initially", aviary.hasFlightlessBird());

        aviary.addBird(penguin);
        assertTrue("Should have flightless bird after adding one", aviary.hasFlightlessBird());
    }

    @Test
    public void testHasBirdOfPrey() {
        assertFalse("Should not have bird of prey initially", aviary.hasBirdOfPrey());

        aviary.addBird(eagle);
        assertTrue("Should have bird of prey after adding one", aviary.hasBirdOfPrey());
    }

    @Test
    public void testHasWaterfowl() {
        assertFalse("Should not have waterfowl initially", aviary.hasWaterfowl());

        aviary.addBird(duck);
        assertTrue("Should have waterfowl after adding one", aviary.hasWaterfowl());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Should return correct location", "Test Location", aviary.getLocation());
    }

    @Test
    public void testGetBirds() {
        assertEquals("Should have 0 birds initially", 0, aviary.getBirds().size());

        aviary.addBird(parrot);
        aviary.addBird(owl);

        assertEquals("Should have 2 birds", 2, aviary.getBirds().size());
        assertTrue("List should contain parrot", aviary.getBirds().contains(parrot));
        assertTrue("List should contain owl", aviary.getBirds().contains(owl));
    }
}
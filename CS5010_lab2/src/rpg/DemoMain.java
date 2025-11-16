package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Minimal demo to show everything working. */
public class DemoMain {
    public static void main(String[] args) {
        Random rng = new Random(42);

        List<Gear> items = new ArrayList<>(List.of(
                new HeadGear(new Name("Sturdy", "Helmet"), 3),
                new HeadGear(new Name("Shiny", "Visor"), 2),
                new HandGear(new Name("Savage", "Sword"), 4),
                new HandGear(new Name("Swift", "Dagger"), 3),
                new HandGear(new Name("Heavy", "Shield"), 2),
                new Footwear(new Name("Scurrying", "Sandals"), 0, 1),
                new Footwear(new Name("Happy", "HoverBoard"), 1, 3),
                new Footwear(new Name("Spiky", "Cleats"), 2, 0),
                new Footwear(new Name("Reinforced", "Boots"), 0, 4),
                new Footwear(new Name("Balanced", "Sneakers"), 1, 1)
        ));

        Character p1 = new Character(3, 3, new Outfit());
        Character p2 = new Character(4, 2, new Outfit());

        new Battle(p1, p2, items, rng).run();
    }
}

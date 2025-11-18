import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class DepartmentImpl implements Department {

    private static final int TEAM_COUNT = 4;
    private static final int TEAM_CAPACITY = 3;

    private static DepartmentImpl instance;

    @SuppressWarnings("unchecked")
    private final OrderedList<Engineer>[] teams =
            (OrderedList<Engineer>[]) new OrderedList[TEAM_COUNT];

    // constructor is private: singleton
    private DepartmentImpl() {
        for (int i = 0; i < TEAM_COUNT; i++) {
            teams[i] = new OrderedListImpl<>();
        }
    }

    // Singleton accessor
    public static DepartmentImpl getInstance() {
        if (instance == null) {
            instance = new DepartmentImpl();
        }
        return instance;
    }

    // Method 1: hire
    @Override
    public boolean hire(Engineer e, int teamId) {
        if (teamId < 0 || teamId >= TEAM_COUNT) {
            throw new IndexOutOfBoundsException("Wrong team id");
        }
        OrderedList<Engineer> team = teams[teamId];
        if (team.size() >= TEAM_CAPACITY) {
            return false; // team is full
        }
        // OrderedListImpl uses Engineer.compareTo -> name alphabetical order
        team.add(e);
        return true;
    }

    // Method 2: giveOutBonus
    @Override
    public void giveOutBonus() {
        for (int t = 0; t < TEAM_COUNT; t++) {
            OrderedList<Engineer> team = teams[t];
            int sz = team.size();
            for (int i = 0; i < sz; i++) {
                Engineer e = team.get(i);
                // everyone gets EXCEED_EXPECTATION
                e.setBonus(Rating.EXCEED_EXPECTATION);
            }
        }
    }

    // Method 4: layoff, using subList + lambda
    @Override
    public void layoff(double bonusThreshold) {
        for (int t = 0; t < TEAM_COUNT; t++) {
            OrderedList<Engineer> team = teams[t];
            // keep only employees whose bonus >= threshold
            Predicate<Engineer> keep = e -> e.getBonus() >= bonusThreshold;
            teams[t] = team.subList(keep);
        }
    }

    // Method 3: iterator, by team then by name within each team
    @Override
    public Iterator<Engineer> iterator() {
        return new Iterator<Engineer>() {
            private int teamIdx = 0;
            private int idxInTeam = 0;

            private void advance() {
                while (teamIdx < TEAM_COUNT) {
                    OrderedList<Engineer> team = teams[teamIdx];
                    if (team == null || team.size() == 0) {
                        teamIdx++;
                        idxInTeam = 0;
                    } else if (idxInTeam >= team.size()) {
                        teamIdx++;
                        idxInTeam = 0;
                    } else {
                        break; // we are at a valid position
                    }
                }
            }

            @Override
            public boolean hasNext() {
                advance();
                if (teamIdx >= TEAM_COUNT) {
                    return false;
                }
                OrderedList<Engineer> team = teams[teamIdx];
                return team != null && idxInTeam < team.size();
            }

            @Override
            public Engineer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Engineer e = teams[teamIdx].get(idxInTeam);
                idxInTeam++;
                return e;
            }
        };
    }
}

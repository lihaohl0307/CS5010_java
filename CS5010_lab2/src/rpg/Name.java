package rpg;
import java.util.Objects;

/** Immutable gear name: adjective + noun. */
public final class Name {
    private final String adjective;
    private final String noun;

    public Name(String adjective, String noun) {
        if (adjective == null || adjective.isBlank() || noun == null || noun.isBlank()) {
            throw new IllegalArgumentException("adjective and noun must be non-empty");
        }
        this.adjective = adjective;
        this.noun = noun;
    }

    public String getAdjective() { return adjective; }
    public String getNoun() { return noun; }
    /** e.g., "Happy HoverBoard" */
    public String full() { return adjective + " " + noun; }

    /** "{weakAdj}, {strongAdj} {strongNoun}" */
    public static Name combine(Name weaker, Name stronger) {
        Objects.requireNonNull(weaker, "weaker");
        Objects.requireNonNull(stronger, "stronger");
        return new Name(weaker.adjective + ", " + stronger.adjective, stronger.noun);
    }

    @Override public String toString() { return full(); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name n = (Name) o;
        return adjective.equals(n.adjective) && noun.equals(n.noun);
    }
    @Override public int hashCode() { return Objects.hash(adjective, noun); }
}

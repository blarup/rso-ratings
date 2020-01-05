package si.thoughts.ratings.lib;

import java.time.Instant;

public class RatingMetadata {

    private Integer textId;
    private Integer rating;
    private Instant created;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Integer getTextId() {
        return textId;
    }

    public void setTextId(Integer textId) {
        this.textId = textId;
    }
}

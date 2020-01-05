package si.thoughts.ratings.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "rating_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "TextMetadataEntity.getAll",
                        query = "SELECT rt FROM RatingMetadataEntity rt")
        })
public class RatingMetadataEntity {

    @Column(name = "textId")
    private Integer id;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created")
    private Instant created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
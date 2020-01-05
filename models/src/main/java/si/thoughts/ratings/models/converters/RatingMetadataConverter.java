package si.thoughts.ratings.models.converters;

import si.thoughts.ratings.lib.RatingMetadata;
import si.thoughts.ratings.models.entities.RatingMetadataEntity;

public class RatingMetadataConverter {

    public static RatingMetadata toDto(RatingMetadataEntity entity) {

        RatingMetadata dto = new RatingMetadata();
        dto.setTextId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setRating(entity.getRating());
        return dto;
    }

    public static RatingMetadataEntity toEntity(RatingMetadata dto) {

        RatingMetadataEntity entity = new RatingMetadataEntity();
        entity.setCreated(dto.getCreated());
        entity.setId(dto.getTextId());
        entity.setRating(dto.getRating());
        return entity;

    }

}

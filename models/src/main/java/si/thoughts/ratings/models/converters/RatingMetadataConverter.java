package si.thoughts.ratings.models.converters;

import si.thoughts.ratings.lib.RatingMetadata;
import si.thoughts.ratings.models.entities.RatingMetadataEntity;

public class RatingMetadataConverter {

    public static RatingMetadata toDto(RatingMetadataEntity entity) {

        RatingMetadata dto = new RatingMetadata();
        dto.setId(entity.getId());
        dto.setTextId(entity.getTextId());
        dto.setCreated(entity.getCreated());
        dto.setRating(entity.getRating());
        return dto;
    }

    public static RatingMetadataEntity toEntity(RatingMetadata dto) {

        RatingMetadataEntity entity = new RatingMetadataEntity();
        entity.setCreated(dto.getCreated());
        entity.setTextId(dto.getTextId());
        entity.setRating(dto.getRating());
        return entity;
    }

}

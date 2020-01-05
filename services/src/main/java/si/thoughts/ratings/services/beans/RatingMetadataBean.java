package si.thoughts.ratings.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.thoughts.ratings.lib.RatingMetadata;
import si.thoughts.ratings.models.converters.RatingMetadataConverter;
import si.thoughts.ratings.models.entities.RatingMetadataEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class RatingMetadataBean {

    private Logger log = Logger.getLogger(RatingMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    private Client httpClient;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    public List<RatingMetadata> getRatingMetadata() {

        TypedQuery<RatingMetadataEntity> query = em.createNamedQuery("TextMetadataEntity.getAll",
                RatingMetadataEntity.class);

        return query.getResultList().stream().map(RatingMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<RatingMetadata> getRatingMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, RatingMetadataEntity.class, queryParameters).stream()
                .map(RatingMetadataConverter::toDto).collect(Collectors.toList());
    }

    public RatingMetadata createRatingMetadata(RatingMetadata ratingMetadata) {

        RatingMetadataEntity ratingMetadataEntity = RatingMetadataConverter.toEntity(ratingMetadata);

        try {
            beginTx();
            em.persist(ratingMetadataEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (ratingMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return RatingMetadataConverter.toDto(ratingMetadataEntity);
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

    public void loadOrder(Integer n) {


    }
}

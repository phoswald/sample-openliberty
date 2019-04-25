package com.github.phoswald.sample.microprofile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/task")
public class TaskResource {

    @PersistenceContext(name = "sampleUnit")
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        TypedQuery<TaskEntity> query = em.createQuery("select t from TaskEntity t order by t.timestamp desc", TaskEntity.class);
        query.setMaxResults(100);
        List<TaskEntity> entities = query.getResultList();
        return Response.ok(entities).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response add() {
        TaskEntity entity = new TaskEntity();
        entity.setEventId(UUID.randomUUID().toString());
        entity.setUserId("guest");
        entity.setTimestamp(new Date());
        entity.setText("New task...");
        em.persist(entity);
        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        TaskEntity entity = em.find(TaskEntity.class, id);
        return Response.ok(entity).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putById(@PathParam("id") String id, TaskEntity request) {
        TaskEntity entity = em.find(TaskEntity.class, id);
        entity.setText(request.getText());
        entity.setDone(request.isDone());
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") String id) {
        TaskEntity entity = em.find(TaskEntity.class, id);
        em.remove(entity);
        return Response.noContent().build();
    }
}

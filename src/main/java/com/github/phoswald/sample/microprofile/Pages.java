package com.github.phoswald.sample.microprofile;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/pages")
public class Pages {

    @PersistenceContext(name = "sampleUnit")
    private EntityManager em;

    @Inject
    private Thymeleaf tl;

    @GET
    @Path("/greeting")
    @Produces(MediaType.TEXT_HTML)
    public Response getGreetingPage() {
        return Response.ok(tl.render("greeting", "message", "Hello, World!")).build();
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.TEXT_HTML)
    public Response getTasksPage() {
        TypedQuery<TaskEntity> query = em.createQuery("select t from TaskEntity t order by t.timestamp desc", TaskEntity.class);
        query.setMaxResults(100);
        List<TaskEntity> entities = query.getResultList();
        return Response.ok(tl.render("tasks", "tasks", entities)).build();
    }

    @GET
    @Path("/tasks/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getTaskPage(@PathParam("id") String id) {
        TaskEntity entity = em.find(TaskEntity.class, id);
        return Response.ok(tl.render("task", "task", entity)).build();
    }
}

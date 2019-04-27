package com.github.phoswald.sample.microprofile.task;

import java.time.Instant;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
@Path("/tasks")
public class TaskResource {

    @Inject
    private TaskRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        List<TaskEntity> entities = repository.selectAllTasks();
        return Response.ok(entities).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTasks(TaskEntity request) {
        TaskEntity entity = new TaskEntity();
        entity.setNewTaskId();
        entity.setUserId("guest");
        entity.setTimestamp(Instant.now());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setDone(request.isDone());
        repository.createTask(entity);
        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") String id) {
        TaskEntity entity = repository.selectTaskById(id);
        return Response.ok(entity).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putTask(@PathParam("id") String id, TaskEntity request) {
        TaskEntity entity = repository.selectTaskById(id);
        entity.setTimestamp(Instant.now());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setDone(request.isDone());
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") String id) {
        TaskEntity entity = repository.selectTaskById(id);
        repository.deleteTask(entity);
        return Response.noContent().build();
    }
}

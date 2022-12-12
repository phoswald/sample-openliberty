package com.github.phoswald.sample.task;

import java.time.Instant;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Transactional
@Path("/rest/tasks")
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

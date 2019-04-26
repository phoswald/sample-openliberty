package com.github.phoswald.sample.microprofile.task;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/pages/tasks")
public class TaskController {

    @Inject
    private TaskRepository repository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getTasksPage() {
        List<TaskEntity> entities = repository.selectAllTasks();
        List<TaskViewModel> viewModel = TaskViewModel.newList(entities);
        return Response.ok(new TaskListView().render(viewModel)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response addTaskPage( //
            @FormParam("title") String title, //
            @FormParam("description") String description) {
        TaskEntity entity = new TaskEntity();
        entity.setNewTaskId();
        entity.setUserId("guest");
        entity.setTimestamp(Instant.now());
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setDone(false);
        repository.createTask(entity);
        return getTasksPage();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getTaskPage( //
            @PathParam("id") String id, //
            @QueryParam("action") String action) {
        TaskEntity entity = repository.selectTaskById(id);
        TaskViewModel viewModel = new TaskViewModel(entity);
        if (Objects.equals(action, "edit")) {
            return Response.ok(new TaskEditView().render(viewModel)).build();
        } else {
            return Response.ok(new TaskView().render(viewModel)).build();
        }
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response postTaskPage( //
            @PathParam("id") String id, //
            @FormParam("action") String action, //
            @FormParam("title") String title, //
            @FormParam("description") String description, //
            @FormParam("done") String done) throws URISyntaxException {
        TaskEntity entity = repository.selectTaskById(id);
        if (Objects.equals(action, "delete")) {
            repository.deleteTask(entity);
            return Response.seeOther(new URI("pages/tasks")).build();
        }
        if (Objects.equals(action, "store")) {
            entity.setTimestamp(Instant.now());
            entity.setTitle(title);
            entity.setDescription(description);
            entity.setDone(Objects.equals(done, "on"));
            repository.updateChanges();
        }
        return getTaskPage(id, "edit");
    }
}

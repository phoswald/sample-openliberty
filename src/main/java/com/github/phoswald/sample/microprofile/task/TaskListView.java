package com.github.phoswald.sample.microprofile.task;

import java.util.List;

import com.github.phoswald.sample.microprofile.AbstractView;

public class TaskListView extends AbstractView<List<TaskViewModel>> {

    public TaskListView() {
        super("task-list", "tasks");
    }
}

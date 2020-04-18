package com.github.phoswald.sample.openliberty.task;

import java.util.List;

import com.github.phoswald.sample.openliberty.AbstractView;

public class TaskListView extends AbstractView<List<TaskViewModel>> {

    public TaskListView() {
        super("task-list", "tasks");
    }
}

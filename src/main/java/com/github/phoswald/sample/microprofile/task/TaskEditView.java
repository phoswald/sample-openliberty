package com.github.phoswald.sample.microprofile.task;

import com.github.phoswald.sample.microprofile.AbstractView;

public class TaskEditView extends AbstractView<TaskViewModel> {

    public TaskEditView() {
        super("task-edit", "task");
    }
}

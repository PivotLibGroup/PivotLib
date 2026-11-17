package com.pivotgroup.pivotlib.core;

import org.opencv.core.Mat;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

final class TemplateRepository {

    private final Map<String, Mat> templates = new ConcurrentHashMap<>();

    void put(String name, Mat template) {
        templates.put(Objects.requireNonNull(name, "name"), Objects.requireNonNull(template, "template"));
    }

    Mat get(String name) {
        Objects.requireNonNull(name, "name");
        Mat template = templates.get(name);
        if (template == null) {
            throw new NoSuchElementException("No template named " + name);
        }
        return template;
    }
}

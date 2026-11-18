package com.pivotgroup.pivotlib.vision;

import com.pivotgroup.pivotlib.action.routing.Feature;
import com.pivotgroup.pivotlib.action.routing.FeatureInspector;
import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.model.ResultLoc;
import org.opencv.core.Mat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Inspects feature templates on one captured canvas in map iteration order.
 *
 * @author xinliu
 */
public final class OpenCvFeatureInspector implements FeatureInspector {

    private final Identifier identifier;

    private final List<Feature> features;

    private final List<Mat> templates;

    private final Supplier<Optional<Rect>> roiSupplier;

    public OpenCvFeatureInspector(Identifier identifier, Map<Feature, Mat> templates) {
        this(identifier, templates, Optional::empty);
    }

    public OpenCvFeatureInspector(
            Identifier identifier,
            Map<Feature, Mat> templates,
            Supplier<Optional<Rect>> roiSupplier) {
        this.identifier = Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(templates, "templates");
        Map<Feature, Mat> orderedTemplates = new LinkedHashMap<>(templates);
        features = List.copyOf(orderedTemplates.keySet());
        this.templates = List.copyOf(orderedTemplates.values());
        this.roiSupplier = Objects.requireNonNull(roiSupplier, "roiSupplier");
    }

    @Override
    public Optional<Feature> inspect() {
        Optional<Rect> roi = Objects.requireNonNull(
                roiSupplier.get(), "roiSupplier result");
        List<ResultLoc> results = roi
                .map(region -> identifier.identifyAll(templates, region))
                .orElseGet(() -> identifier.identifyAll(templates));
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).isPresent()) {
                return Optional.of(features.get(i));
            }
        }
        return Optional.empty();
    }
}

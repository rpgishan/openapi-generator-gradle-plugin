package com.sundance.plugins.generator.gradle.extensions;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.jetbrains.annotations.NotNull;

/**
 * @author rpgishan
 * @project openapispec-generator-gradle-plugin
 * @date 03/02/2024
 */
public class ApiSpecCopyExtension {
    @Input
    private final Property<String> apiSpecDir;
    private final Property<Boolean> isSpecGenerate;

    public ApiSpecCopyExtension(@NotNull Project project) {
        this.apiSpecDir = project.getObjects().property(String.class);
        this.isSpecGenerate = project.getObjects().property(Boolean.class);
    }

    public Property<String> getApiSpecDir() {
        return apiSpecDir;
    }

    public Property<Boolean> getIsSpecGenerate() {
        return isSpecGenerate;
    }
}

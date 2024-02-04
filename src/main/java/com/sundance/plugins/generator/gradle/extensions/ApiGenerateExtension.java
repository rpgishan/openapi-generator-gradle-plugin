package com.sundance.plugins.generator.gradle.extensions;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.jetbrains.annotations.NotNull;
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension;

/**
 * @author rpgishan
 * @project openapispec-generator-gradle-plugin
 * @date 03/02/2024
 */
public class ApiGenerateExtension extends OpenApiGeneratorGenerateExtension {

    private final Property<String> apiSpecDir;
    private final Property<String> apiSpecFile;
    private final Property<Boolean> isSpecGenerate;

    public ApiGenerateExtension(@NotNull Project project) {
        super(project);
        this.apiSpecDir = project.getObjects().property(String.class);
        this.apiSpecFile = project.getObjects().property(String.class);
        this.isSpecGenerate = project.getObjects().property(Boolean.class);
    }

    public Property<String> getApiSpecDir() {
        return apiSpecDir;
    }

    public Property<String> getApiSpecFile() {
        return apiSpecFile;
    }

    public Property<Boolean> getIsSpecGenerate() {
        return isSpecGenerate;
    }
}

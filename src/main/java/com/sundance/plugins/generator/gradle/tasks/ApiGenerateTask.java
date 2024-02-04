package com.sundance.plugins.generator.gradle.tasks;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.CacheableTask;
import org.jetbrains.annotations.NotNull;
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask;

import javax.inject.Inject;

/**
 * @author rpgishan
 * @project openapispec-generator-gradle-plugin
 * @date 31/01/2024
 */
@CacheableTask
public class ApiGenerateTask extends GenerateTask {
    @Inject
    public ApiGenerateTask(@NotNull ObjectFactory objectFactory) {
        super(objectFactory);
    }
}

package com.sundance.plugins.generator.gradle.tasks;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * @author rpgishan
 * @project openapispec-generator-gradle-plugin
 * @date 31/01/2024
 */
public class ApiSpecCopyTask extends Copy {

    @Input
    private final Property<String> apiSpecDir;
    @Input
    private final Property<Boolean> isSpecGenerate;

    public ApiSpecCopyTask() {
        this.apiSpecDir = getObjectFactory().property(String.class);
        this.isSpecGenerate = getObjectFactory().property(Boolean.class);
    }

    @TaskAction
    public void doTaskWork() {
        String apiSpecDirPath = getApiSpecDir().getOrElse(getProject().getLayout().getBuildDirectory().getAsFile().get().getPath());
        Configuration apisource = getProject().getConfigurations().getByName("apiSource");
        File destinationDir = new File(apiSpecDirPath);
        String versionRegex = "(?!\\.)([-.]?)(\\d+(\\.\\d+)+)(?:[-.][A-Z]+)?(?![\\d.])(?:[-.][a-z]+)?";

        from(apisource)
                .into(destinationDir)
                .rename(fileName -> fileName.replaceAll(versionRegex, ""));

        boolean isSpec = getIsSpecGenerate().getOrElse(false);
        if (isSpec) {
            from("src")
                    .include("*.yaml")
                    .into(destinationDir);
        }
    }

    public Property<String> getApiSpecDir() {
        return apiSpecDir;
    }

    public Property<Boolean> getIsSpecGenerate() {
        return isSpecGenerate;
    }
}

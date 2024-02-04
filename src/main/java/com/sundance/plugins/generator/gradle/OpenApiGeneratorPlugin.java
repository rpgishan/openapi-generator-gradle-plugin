package com.sundance.plugins.generator.gradle;

import com.sundance.plugins.generator.gradle.extensions.ApiGenerateExtension;
import com.sundance.plugins.generator.gradle.extensions.ApiSpecCopyExtension;
import com.sundance.plugins.generator.gradle.tasks.ApiGenerateTask;
import com.sundance.plugins.generator.gradle.tasks.ApiSpecCopyTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask;

/**
 * @author rpgishan
 * @project openapispec-generator-gradle-plugin
 * @date 31/01/2024
 */
public class OpenApiGeneratorPlugin implements Plugin<Project> {

    String pluginGroup = "Sundance OpenAPI Tools";

    @Override
    public void apply(Project project) {

        project.getPluginManager().apply(OpenApiGeneratorPlugin.class);

        project.getConfigurations().register("apiSource");

        String apiSpecCopyTaskName = "apiSpecCopy";
        String apiGenerateTaskName = "apiGenerate";

        ApiSpecCopyExtension apiSpecCopy = project.getExtensions().create(apiSpecCopyTaskName, ApiSpecCopyExtension.class, project);
        ApiGenerateExtension apiGenerate = project.getExtensions().create(apiGenerateTaskName, ApiGenerateExtension.class, project);

        project.getTasks().register(apiSpecCopyTaskName, ApiSpecCopyTask.class).configure(apiSpecCopyTask -> {
            apiSpecCopyTask.setProperty("apiSpecDir", apiSpecCopy.getApiSpecDir());
            apiSpecCopyTask.setProperty("isSpecGenerate", apiSpecCopy.getIsSpecGenerate());
            apiSpecCopyTask.doTaskWork();
        });

        project.getTasks().register(apiGenerateTaskName, ApiGenerateTask.class).configure(apiClassGenerateTask -> {
            apiClassGenerateTask.dependsOn(project.getTasks().getByName(apiSpecCopyTaskName));
            Boolean isSpecGenerate = apiGenerate.getIsSpecGenerate().getOrElse(false);
            setProperties(apiClassGenerateTask, pluginGroup, apiGenerate, isSpecGenerate);
        });

        project.getTasks().getByName("compileJava").dependsOn(apiGenerateTaskName);
    }

    private static void setProperties(GenerateTask generateTask, String pluginGroup, ApiGenerateExtension apiGenerateExtension, boolean isApiSpec) {
        Property<String> apiSpecDir = apiGenerateExtension.getApiSpecDir();
        if (apiSpecDir.isPresent()) {
            Property<String> apiSpecFile = apiGenerateExtension.getApiSpecFile();
            if (apiSpecFile.isPresent()) {
                apiGenerateExtension.getInputSpec().set(String.format("%s/%s", apiSpecDir.get(), apiSpecFile.get()));
            } else {
                apiGenerateExtension.getInputSpecRootDirectory().set(apiSpecDir.get());
            }
        }

        if (isApiSpec) {
            apiGenerateExtension.getGeneratorName().set("spring");
        }

        generateTask.setGroup(pluginGroup);
        generateTask.setDescription("Generate code via Open API Tools Generator for Open API 2.0 or 3.x specification documents.");
        generateTask.setProperty("verbose", apiGenerateExtension.getVerbose());
        generateTask.setProperty("validateSpec", apiGenerateExtension.getValidateSpec());
        generateTask.setProperty("outputDir", apiGenerateExtension.getOutputDir());
        generateTask.setProperty("generatorName", apiGenerateExtension.getGeneratorName());
        generateTask.setProperty("inputSpec", apiGenerateExtension.getInputSpec());
        generateTask.setProperty("inputSpecRootDirectory", apiGenerateExtension.getInputSpecRootDirectory());
        generateTask.setProperty("remoteInputSpec", apiGenerateExtension.getRemoteInputSpec());
        generateTask.setProperty("templateDir", apiGenerateExtension.getTemplateDir());
        generateTask.setProperty("auth", apiGenerateExtension.getAuth());
        generateTask.setProperty("globalProperties", apiGenerateExtension.getGlobalProperties());
        generateTask.setProperty("configFile", apiGenerateExtension.getConfigFile());
        generateTask.setProperty("skipOverwrite", apiGenerateExtension.getSkipOverwrite());
        generateTask.setProperty("packageName", apiGenerateExtension.getPackageName());
        generateTask.setProperty("apiPackage", apiGenerateExtension.getApiPackage());
        generateTask.setProperty("modelPackage", apiGenerateExtension.getModelPackage());
        generateTask.setProperty("modelNamePrefix", apiGenerateExtension.getModelNamePrefix());
        generateTask.setProperty("modelNameSuffix", apiGenerateExtension.getModelNameSuffix());
        generateTask.setProperty("apiNameSuffix", apiGenerateExtension.getApiNameSuffix());
        generateTask.setProperty("instantiationTypes", apiGenerateExtension.getInstantiationTypes());
        generateTask.setProperty("typeMappings", apiGenerateExtension.getTypeMappings());
        generateTask.setProperty("additionalProperties", apiGenerateExtension.getAdditionalProperties());
        generateTask.setProperty("serverVariables", apiGenerateExtension.getServerVariables());
        generateTask.setProperty("languageSpecificPrimitives", apiGenerateExtension.getLanguageSpecificPrimitives());
        generateTask.setProperty("importMappings", apiGenerateExtension.getImportMappings());
        generateTask.setProperty("schemaMappings", apiGenerateExtension.getSchemaMappings());
        generateTask.setProperty("inlineSchemaNameMappings", apiGenerateExtension.getInlineSchemaNameMappings());
        generateTask.setProperty("inlineSchemaOptions", apiGenerateExtension.getInlineSchemaOptions());
        generateTask.setProperty("nameMappings", apiGenerateExtension.getNameMappings());
        generateTask.setProperty("modelNameMappings", apiGenerateExtension.getModelNameMappings());
        generateTask.setProperty("parameterNameMappings", apiGenerateExtension.getParameterNameMappings());
        generateTask.setProperty("openapiNormalizer", apiGenerateExtension.getOpenapiNormalizer());
        generateTask.setProperty("invokerPackage", apiGenerateExtension.getInvokerPackage());
        generateTask.setProperty("groupId", apiGenerateExtension.getGroupId());
        generateTask.setProperty("id", apiGenerateExtension.getId());
        generateTask.setProperty("version", apiGenerateExtension.getVersion());
        generateTask.setProperty("library", apiGenerateExtension.getLibrary());
        generateTask.setProperty("gitHost", apiGenerateExtension.getGitHost());
        generateTask.setProperty("gitUserId", apiGenerateExtension.getGitUserId());
        generateTask.setProperty("gitRepoId", apiGenerateExtension.getGitRepoId());
        generateTask.setProperty("releaseNote", apiGenerateExtension.getReleaseNote());
        generateTask.setProperty("httpUserAgent", apiGenerateExtension.getHttpUserAgent());
        generateTask.setProperty("reservedWordsMappings", apiGenerateExtension.getReservedWordsMappings());
        generateTask.setProperty("ignoreFileOverride", apiGenerateExtension.getIgnoreFileOverride());
        generateTask.setProperty("removeOperationIdPrefix", apiGenerateExtension.getRemoveOperationIdPrefix());
        generateTask.setProperty("skipOperationExample", apiGenerateExtension.getSkipOperationExample());
        generateTask.setProperty("apiFilesConstrainedTo", apiGenerateExtension.getApiFilesConstrainedTo());
        generateTask.setProperty("modelFilesConstrainedTo", apiGenerateExtension.getModelFilesConstrainedTo());
        generateTask.setProperty("supportingFilesConstrainedTo", apiGenerateExtension.getSupportingFilesConstrainedTo());
        generateTask.setProperty("generateModelTests", apiGenerateExtension.getGenerateModelTests());
        generateTask.setProperty("generateModelDocumentation", apiGenerateExtension.getGenerateModelDocumentation());
        generateTask.setProperty("generateApiTests", apiGenerateExtension.getGenerateApiTests());
        generateTask.setProperty("generateApiDocumentation", apiGenerateExtension.getGenerateApiDocumentation());
        generateTask.setProperty("withXml", apiGenerateExtension.getWithXml());
        generateTask.setProperty("configOptions", apiGenerateExtension.getConfigOptions());
        generateTask.setProperty("logToStderr", apiGenerateExtension.getLogToStderr());
        generateTask.setProperty("enablePostProcessFile", apiGenerateExtension.getEnablePostProcessFile());
        generateTask.setProperty("skipValidateSpec", apiGenerateExtension.getSkipValidateSpec());
        generateTask.setProperty("generateAliasAsModel", apiGenerateExtension.getGenerateAliasAsModel());
        generateTask.setProperty("engine", apiGenerateExtension.getEngine());
        generateTask.setProperty("cleanupOutput", apiGenerateExtension.getCleanupOutput());
        generateTask.setProperty("dryRun", apiGenerateExtension.getDryRun());
    }
}

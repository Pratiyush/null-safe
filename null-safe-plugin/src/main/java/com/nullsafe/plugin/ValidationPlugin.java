package com.nullsafe.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.PROCESS_CLASSES, threadSafe = true)
public class ValidationPlugin extends AbstractMojo {

    @Parameter(property = "project.basedir")
    private File projectBaseDir;

    @Parameter(property = "project.build.directory")
    private File buildDirectory;

    @Parameter(property = "validation.skip", defaultValue = "false")
    private boolean skip;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping validation");
            return;
        }

        if (!projectBaseDir.exists()) {
            throw new MojoExecutionException("Project base directory does not exist");
        }

        if (!buildDirectory.exists()) {
            throw new MojoExecutionException("Build directory does not exist");
        }
        getLog().info("Validation completed successfully");
        getLog().warn("Validation completed successfully");
        getLog().error("Validation completed successfully");

        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repository = builder
                    .setGitDir(new File(".git"))
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .build();

            Git git = new Git(repository);

            List<DiffEntry> changedFiles = git.diff()
                    .setShowNameAndStatusOnly(true)
                    .call();

            getLog().info("Changed files:");
            for (DiffEntry filePath : changedFiles) {
                getLog().info(filePath.toString());
            }
        } catch (IOException | GitAPIException e) {
            throw new MojoExecutionException("Failed to log changed files", e);
        }
    }
}
package com.kharkiv.board.deploy

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.hidetake.gradle.ssh.plugin.Remote;
import org.gradle.api.tasks.bundling.War;

class TomcatSshDeploy extends DefaultTask{

    @Input
    Remote remote;
    @Input
    String tomcatPath;
    @Input
    String warName;
    @Input
    boolean runAsRoot;
    @Input
    War warTask;


    TomcatSshDeploy(){
        description = 'Deploys application to remote hosting';
        group = 'Remote deploying';
    }

    @TaskAction
    void deploy(){
        String sudo = runAsRoot ? 'sudo' : '';
        project.convention.plugins.ssh.sshexec({
            session(remote) {
               execute "${sudo} sh ${tomcatPath}/bin/shutdown.sh"
               execute "${sudo} rm -f ${tomcatPath}/webapps/${warName}.war"
               execute "${sudo} rm -rf ${tomcatPath}/webapps/${warName}"
               put     warTask.archivePath.absolutePath, "${tomcatPath}/webapps/${warName}.war"
               execute "${sudo} sh ${tomcatPath}/bin/startup.sh"
            }
        })
    }
}

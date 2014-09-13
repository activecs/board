package com.kharkiv.board.deploy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

class TomcatSshDeploy extends DefaultTask{

    @Input
    Object remote;
    @Input
    String tomcatPath;
    @Input
    String warName;
    @Input
    String warAbsolutePath;

    TomcatSshDeploy(){
        description = 'Deploys application to remote hosting';
        group = 'Remote deploying';
        dependsOn: 'war';
    }

    @TaskAction
    void deploy(){
        project.convention.plugins.ssh.sshexec({
            session(remote) {
                execute "sh ${tomcatPath}/bin/shutdown.sh"
                execute "rm -f ${tomcatPath}/webapps/${warName}.war"
                execute "rm -rf ${tomcatPath}/webapps/${warName}"
                put     warAbsolutePath, "${tomcatPath}/webapps/${warName}.war"
                execute "sh ${tomcatPath}/bin/startup.sh"
            }
        })
    }
}

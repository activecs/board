package com.kharkiv.board.deploy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class TomcatSshRestart extends DefaultTask {

    @Input
    Object remote;
    @Input
    String tomcatPath;

    TomcatSshRestart ( ) {
        description = 'Restart application to remote hosting';
        group = 'Remote deploying';
    }

    @TaskAction
    void deploy() {
        project.convention.plugins.ssh.sshexec({
            session(remote) {
                execute "sh ${tomcatPath}/bin/shutdown.sh"
                execute "sh ${tomcatPath}/bin/startup.sh"
            }
        })
    }
}



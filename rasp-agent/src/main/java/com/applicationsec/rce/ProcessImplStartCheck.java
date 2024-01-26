package com.applicationsec.rce;

import com.applicationsec.RASPSecurityException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProcessImplStartCheck {

    private static final List<String> blockedCommands = List.of("calc", "etc", "var", "opt", "apache", "bin", "passwd", "login", "cshrc", "profile", "ifconfig", "tcpdump", "chmod",
            "cron", "open", "sudo", "su", "rm", "wget", "sz", "kill", "apt-get", "find", "touch");

    static public void check(String[] commands) {

        if( !Collections.disjoint(Arrays.stream(commands).toList(), blockedCommands)){
            throw new RASPSecurityException("Commands :" + Arrays.toString(commands) +" has been blocked by RASP");
        }
    }
}

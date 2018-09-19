package javat.agent.transformer;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 19, 2018
 */
public class AgentMainRunAttach {


    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, InterruptedException {

        // args[0]传入的是某个jvm进程的pid
        String targetPid = args[0];
        String agentJar = args[1];

        VirtualMachine vm = VirtualMachine.attach(targetPid);
        vm.loadAgent(agentJar, "toagent");

        int sleep = 600;
        System.out.println("sleep " + sleep + " seconds!!");
        TimeUnit.SECONDS.sleep(sleep);

        vm.detach();
    }

}

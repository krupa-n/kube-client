package kubeapi;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.dsl.LogWatch;
import kubeapi.model.ClusterData;

import java.io.IOException;
import java.io.OutputStream;

public class LogApiTest {

//    Logger logger = LoggerFactory.getLogger(LogApiTest.class);

    public LogApiTest() {
       // watchPodChange();
    }

    private static final String HELLO_MINIKUBE_6C47C66D8_29MGT = "hello-minikube-6c47c66d8-29mgt";

    public void getLog(ClusterData clusterData, OutputStream fileOutputStream) throws IOException {
        KubernetesClient client = new DefaultKubernetesClient();
        Config config = new ConfigBuilder().withMasterUrl("https://192.168.99.100:8443\n").build();

        LogWatch logWatch = client.pods().inNamespace(clusterData.getNamespace()).withName(clusterData.getPod()).tailingLines(clusterData.getNumLogLines()).watchLog(fileOutputStream);
        //InputStream inputStream = logWatch.getOutput();
        //logWatch.close();
        System.out.println("Closing kube client connection");
        //client.close();
    }

    public LogWatch getLog(ClusterData clusterData) throws IOException {
        KubernetesClient client = new DefaultKubernetesClient();
        Config config = new ConfigBuilder().withMasterUrl("https://192.168.99.100:8443\n").build();

        LogWatch logWatch = client.pods().inNamespace(clusterData.getNamespace()).withName(clusterData.getPod()).tailingLines(clusterData.getNumLogLines()).watchLog();

        //client.close();
        return logWatch;
    }

    private void watchPodChange() {
        KubernetesClient client = new DefaultKubernetesClient();
        ReplicationController replicationController;
        System.out.println("Watching");
        Watch watch = client.pods().inNamespace("default").withLabel("new-label=log-test").watch(new Watcher<Pod>() {

            public void eventReceived(Action action, Pod resource) {
                System.out.println("Pod " + resource.getMetadata().getName() + " " + action.name());
            }


            public void onClose(KubernetesClientException cause) {
                System.out.println("Closed watch");
            }


        });
    }
}

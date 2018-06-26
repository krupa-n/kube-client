package kubeapi;

import kubeapi.model.ClusterData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
//
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //initLogService();
    }

    private static void initLogService() {
        LogApiTest logApiTest = new LogApiTest();
        try {
            ClusterData clusterData = new ClusterData();
            clusterData.setNamespace("default");
            clusterData.setNumLogLines(10);
            clusterData.setPod("hello-minikube-6c47c66d8-xb4bn");
            logApiTest.getLog(clusterData,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package kubeapi;

import io.fabric8.kubernetes.client.dsl.LogWatch;
import kubeapi.model.ClusterData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LogServiceImpl implements LogService {
    ConcurrentHashMap<ClusterData,LogWatch> connectionPool = new ConcurrentHashMap<>();

    public void getLogs(ClusterData clusterData, OutputStream outputStream) {
        if(clusterData != null) {
            if (!connectionPool.containsKey(clusterData)) {
                LogApiTest logApiTest = new LogApiTest();
                try {
                   logApiTest.getLog(clusterData,outputStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public InputStream getLogs1(ClusterData clusterData, OutputStream outputStream) {
        if(clusterData != null) {
            if (!connectionPool.containsKey(clusterData)) {
                LogApiTest logApiTest = new LogApiTest();
                try {
                    return logApiTest.getLog(clusterData).getOutput();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return null;

    }



}

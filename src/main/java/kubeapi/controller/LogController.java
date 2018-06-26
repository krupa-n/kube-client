package kubeapi.controller;


import kubeapi.LogServiceImpl;
import kubeapi.model.ClusterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class LogController {
    @Autowired
    LogServiceImpl logService;

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @RequestMapping("/log")
    public void getLogLines(@RequestBody ClusterData clusterData, HttpServletResponse response, HttpSession session) throws IOException {
        OutputStream outputStream = response.getOutputStream();
       logService.getLogs(clusterData,response.getOutputStream());

            System.out.println("wrote log lines");
    }


    @RequestMapping("/log1")
    public StreamingResponseBody getLogLines1(@RequestBody ClusterData clusterData, HttpServletResponse response, HttpSession session) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = logService.getLogs1(clusterData, response.getOutputStream());
        response.setHeader("Transfer-Enoding","chunked");


        StreamingResponseBody body = null;
//        while(!Thread.currentThread().isInterrupted()) {
//            inputStream.read(bytes);
//             body = out -> out.write(bytes);
//        }
        //return body;

        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        ExecutorService service = Executors.newSingleThreadExecutor();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream out) throws IOException {
                while(true) {
                    int x = bufferedInputStream.available();
                    if (x == -1)
                        break;
                    if (x == 0)
                        continue;;
                    byte[] bytes = new byte[x];
                    int num = bufferedInputStream.read(bytes,0,x);
                     out.write(bytes);
                     out.flush();

//                        while ((bufferedInputStream.read(bytes,0,x)) != -1) {
//                            emitter.send(bytes);
//
//                        }
                }
            }
        };

        }

}




//        try {
//            OutputStream outputStream = response.getOutputStream();
//            WriteListener writeListener;
//            ((ServletOutputStream) outputStream).setWriteListener();
//            new LogApiTest().getLog(clusterData,outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new StreamingResponseBody() {
//            @Override
//            public void writeTo(OutputStream out) throws IOException {
//                    new LogApiTest().getLog(clusterData,out);
//                    out.write("krupa".getBytes());
//                    out.flush();
//                    try {
//                        Thread.sleep(5);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//            }
//        };


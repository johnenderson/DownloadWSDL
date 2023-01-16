package com.utilities;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final String extension = ".xml";
    private static final int TIMEOUT = 30;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        String domain = "";
        String destinationPath = "";

        try {
            domain = args[0];
            destinationPath = args[1];

        } catch (Exception e) {
            System.out.println(
                    "Ex.: java -jar downloadwsdl.jar <domain> <host>");
            return;
        }

        Map<String, String> urlWsdl = getUrlWsdl();
        for (Map.Entry<String, String> map : urlWsdl.entrySet()) {
            String requestURL = "";
            String nameWsdl = map.getKey() + extension;
            String urlTemplate = map.getValue();
            requestURL = urlTemplate.replaceAll("<domain>", domain);
            System.out.println("ResquestURL final: " + requestURL);
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(requestURL))
                    .timeout(Duration.ofSeconds(TIMEOUT))
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(TIMEOUT))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            try {
                httpClient.sendAsync(request,
                                HttpResponse.BodyHandlers.ofFile(Path.of(destinationPath + nameWsdl)))
                        .thenApply(HttpResponse::body)
                        .thenAccept(System.out::println)
                        .get(TIMEOUT, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                System.out.println(e);
            }

        }

    }

    public static Map<String, String> getUrlWsdl() {
        Map<String, String> urlWsdl = new HashMap<>();
        urlWsdl.put("adm_ws", new String("<domain>/se/ws/adm_ws.php?wsdl"));
        urlWsdl.put("stm_ws", new String("<domain>/se/ws/stm_ws.php?wsdl"));
        urlWsdl.put("arch_ws", new String("<domain>/se/ws/arch_ws.php?wsdl"));
        urlWsdl.put("ast_ws", new String("<domain>/se/ws/ast_ws.php?wsdl"));
        urlWsdl.put("kb_ws", new String("<domain>/se/ws/kb_ws.php?wsdl"));
        urlWsdl.put("cl_ws", new String("<domain>/se/ws/cl_ws.php?wsdl"));
        urlWsdl.put("capt_ws", new String("<domain>/se/ws/capt_ws.php?wsdl"));
        urlWsdl.put("spc_ws", new String("<domain>/se/ws/spc_ws.php?wsdl"));
        urlWsdl.put("wr_ws", new String("<domain>/se/ws/wr_ws.php?wsdl"));
        urlWsdl.put("st_ws", new String("<domain>/se/ws/st_ws.php?wsdl"));
        urlWsdl.put("dc_ws", new String("<domain>/se/ws/dc_ws.php?wsdl"));
        urlWsdl.put("fme_ws", new String("<domain>/se/ws/fme_ws.php?wsdl"));
        urlWsdl.put("fm_ws", new String("<domain>/se/ws/fm_ws.php?wsdl"));
        urlWsdl.put("gn_ws", new String("<domain>/se/ws/gn_ws.php?wsdl"));
        urlWsdl.put("in_ws", new String("<domain>/se/ws/in_ws.php?wsdl"));
        urlWsdl.put("ip_ws", new String("<domain>/se/ws/ip_ws.php?wsdl"));
        urlWsdl.put("sp_ws", new String("<domain>/se/ws/sp_ws.php?wsdl"));
        urlWsdl.put("tsk_ws", new String("<domain>/se/ws/tsk_ws.php?wsdl"));
        urlWsdl.put("mn_ws", new String("<domain>/se/ws/mn_ws.php?wsdl"));
        urlWsdl.put("it_ws", new String("<domain>/se/ws/it_ws.php?wsdl"));
        urlWsdl.put("tmc_ws", new String("<domain>/se/ws/tmc_ws.php?wsdl"));
        urlWsdl.put("pb_ws", new String("<domain>/se/ws/pb_ws.php?wsdl"));
        urlWsdl.put("pr_ws", new String("<domain>/se/ws/pr_ws.php?wsdl"));
        urlWsdl.put("ri_ws", new String("<domain>/se/ws/ri_ws.php?wsdl"));
        urlWsdl.put("sr_ws", new String("<domain>/se/ws/sr_ws.php?wsdl"));
        urlWsdl.put("tr_ws", new String("<domain>/se/ws/tr_ws.php?wsdl"));
        urlWsdl.put("wf_ws", new String("<domain>/se/ws/wf_ws.php?wsdl"));
        return urlWsdl;
    }
}


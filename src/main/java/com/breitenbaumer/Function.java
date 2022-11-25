package com.breitenbaumer;

import com.breitenbaumer.shared.CognitiveServiceClientProvider;
import com.breitenbaumer.shared.FileUploadService;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.util.logging.Logger;

public class Function {

  private Logger logger;

  @FunctionName("blobStorageUpload")
  public HttpResponseMessage run(
    /*
     * TODO: (0)
     * Function header
     * Trigger: HTTP
     * Input: binary array, Context
     * Authentication: Function Key
     */

    @HttpTrigger(name = "req", methods = {HttpMethod.POST},
    authLevel = AuthorizationLevel.FUNCTION,
    dataType = "binary") HttpRequestMessage<Optional<byte[]>> request,
    final ExecutionContext context

  ) throws Exception {

    // TODO: (1) init upload service
    logger = context.getLogger();
    logger.info("Java HTTP file upload started with headers " + request.getHeaders());
    FileUploadService uploadSerivce = new FileUploadService(logger);

    // TODO: (2) upload image
    byte[] bs = request.getBody().get();
    String fileName = uploadSerivce.getFileName(request.getHeaders());
    String url = uploadSerivce.upload(bs, fileName);
    
    // TODO: (3) generate SAS token and url
    String sas = uploadSerivce.generateUserDelegationSASToken(fileName);
    String blobUrl = url + "?" + sas;

    // TODO: (4) send image to cognitive service and upload result as JSON
    CognitiveServiceClientProvider cognitiveServiceClientProvider = new CognitiveServiceClientProvider(logger);
    String analysisResultBody = cognitiveServiceClientProvider.sendRequest(blobUrl);
    byte[] data = analysisResultBody.getBytes();
    uploadSerivce.upload(data, fileName + ".json");

    // TODO: (5) return response
    logger.info("Java HTTP file upload ended. Length: " + bs.length);
    return request.createResponseBuilder(HttpStatus.OK).body("Successfully analyzed " + fileName).build();
  }

}

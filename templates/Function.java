package com.breitenbaumer;

import com.breitenbaumer.shared.CognitiveService;
import com.breitenbaumer.shared.FileUploadService;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class Function {

  private Logger logger;

  @FunctionName("blobStorageUpload")
  public HttpResponseMessage run(
    /*
     * Function header
     * Trigger: HTTP
     * Input: binary array, Context
     * Authentication: Function Key
     */
  ) throws Exception {

    // TODO: (1) init upload service

    // TODO: (2) upload image
    
    // TODO: (3) generate SAS token and url

    // TODO: (4) send image to cognitive service and upload result as JSON
    
    // TODO: (5) return response

  }

}

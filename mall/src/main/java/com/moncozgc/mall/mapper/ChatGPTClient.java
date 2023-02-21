package com.moncozgc.mall.mapper;

import com.moncozgc.mall.dto.chatGPT.request.CompletionRequest;
import com.moncozgc.mall.dto.chatGPT.response.CompletionResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@Component
@FeignClient(url = "https://api.openai.com", name = "chatGPT")
public interface ChatGPTClient {

    @PostMapping(value = "/v1/completions")
    CompletionResult invokeOpenAI(@RequestHeader HttpHeaders headers, @RequestBody CompletionRequest completionRequest);

}

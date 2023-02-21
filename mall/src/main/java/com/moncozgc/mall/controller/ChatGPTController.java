package com.moncozgc.mall.controller;

import com.alibaba.fastjson.JSON;
import com.moncozgc.mall.common.CommonResult;
import com.moncozgc.mall.dto.chatGPT.request.ChatGPTDto;
import com.moncozgc.mall.dto.chatGPT.request.CompletionRequest;
import com.moncozgc.mall.dto.chatGPT.response.ChatGPTResult;
import com.moncozgc.mall.dto.chatGPT.response.CompletionChoice;
import com.moncozgc.mall.dto.chatGPT.response.CompletionResult;
import com.moncozgc.mall.mapper.ChatGPTClient;
import com.moncozgc.mall.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by MoncozGC on 2023/2/19
 */
@Api(tags = "ChatGPT")
@Slf4j
@RestController
@RequestMapping("/chatGpt")
public class ChatGPTController {
    private static final AtomicLong atomicLong = new AtomicLong(0);

//    @Autowired
//    private UserSessionManager userSessionManager;
//
//    @Autowired
//    private ChatGptDetailService chatGptDetailService;

    @Autowired
    private ChatGPTClient chatGptClient;

    @Value("${openai.tokens}")
    private String tokens;

    private static final int SYMPTOM = 1;
    private static final int DISEASE = 2;
    private static final int DRUG_1 = 3;
    private static final int DRUG_2 = 4;
    private static final int DRUG_3 = 5;

    //症状
    private static final int HAS_SYMPTOM = 1;
    //病种
    private static final int HAS_DISEASE = 2;
    //药品
    private static final int HAS_DRUG = 3;
    //症状 + 病种
    private static final int HAS_SYMPTOM_DISEASE = 4;
    //症状 + 药品
    private static final int HAS_SYMPTOM_DRUG = 5;
    //病种 + 药品
    private static final int HAS_DISEASE_DRUG = 6;
    //症状 +  病种 + 药品
    private static final int HAS_SYMPTOM_DISEASE_DRUG = 7;

    @ApiOperation(value = "Q&A接口")
    @PostMapping(value = "/question_and_answer")
    public CommonResult<ChatGPTResult> questionAndAnswer(@RequestBody ChatGPTDto chatGPTDto) {
        ChatGPTResult chatGPTResult = new ChatGPTResult();
        log.info("[AI智能-Q&A接口]接收到的参数:{}", JSON.toJSONString(chatGPTDto));
        if (chatGPTDto == null) {
            return CommonResult.failed("请求对象不能为空");
        }

        if (CollectionUtils.isEmpty(chatGPTDto.getSymptomList()) && CollectionUtils.isEmpty(chatGPTDto.getDiseaseList())
                && CollectionUtils.isEmpty(chatGPTDto.getDrugList())) {
            return CommonResult.failed("查询的病状、病种、药品不能都为空！");
        }

        if (1 != chatGPTDto.getQuestionType() && 2 != chatGPTDto.getQuestionType()) {
            return CommonResult.failed("请求的问题类型参数不对，必须为【1-智能询药 或者 2-智能组方】！");
        }

        String question = getQuestion(chatGPTDto);
        log.info("[AI智能-Q&A接口]调用接口的提问内容:{}", question);
        String answer = invoke(question);

        // 组装返回结果
        chatGPTResult.setQuestion(question);
        chatGPTResult.setAnswer(answer);
        chatGPTResult.setQuestionType(chatGPTDto.getQuestionType());
        return CommonResult.success(chatGPTResult);
    }

    /**
     * 轮询open ai token
     *
     * @param question
     * @return
     */
    private String invoke(String question) {
        String[] split = tokens.split(",");
        long count = atomicLong.getAndIncrement();
        int index = (int) (count % split.length);
        String token = "Bearer " + split[index];

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(question)
                .temperature(0.9)
                .max_tokens(1024)
                .top_p(1d)
                .frequency_penalty(0.0)
                .presence_penalty(0.6)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
        CompletionResult completionResult = chatGptClient.invokeOpenAI(httpHeaders, completionRequest);
        log.info("[AI智能-Q&A接口]调用接口返回的结果：" + JSON.toJSONString(completionResult));
        List<CompletionChoice> choices = completionResult.getChoices();
        if (!CollectionUtils.isEmpty(choices)) {
            return choices.get(0).getText();
        }
        return "openai接口返回无结果，请重新查询！";
    }

    private String getQuestion(ChatGPTDto chatGPTDto) {
        // 症状集合
        List<String> symptomList = chatGPTDto.getSymptomList();
        // 病种集合
        List<String> diseaseList = chatGPTDto.getDiseaseList();
        // 药品集合
        List<String> drugList = chatGPTDto.getDrugList();

        // 提问类型: 1-智能询药, 2-智能组方
        int questionType = chatGPTDto.getQuestionType();
        int relationType = getRelationType(symptomList, diseaseList, drugList);

        String symptomDesc = getDesc(symptomList, SYMPTOM);
        String diseaseDesc = getDesc(diseaseList, SYMPTOM);

        StringBuffer buffer = new StringBuffer();
        if (1 == questionType) {
            String drugDesc = getDesc(drugList, DRUG_2);
            String otherDrugDesc = getDesc(drugList, DRUG_1);
            if (HAS_SYMPTOM == relationType) {
                return buffer.append(symptomDesc).append("可以用哪些西药和中药?").toString();
            } else if (HAS_DISEASE == relationType) {
                return buffer.append(diseaseDesc).append("可以用哪些西药和中药进行治疗?").toString();
            } else if (HAS_DRUG == relationType) {
                return buffer.append(otherDrugDesc).toString();
            } else if (HAS_SYMPTOM_DISEASE == relationType) {
                return buffer.append(symptomDesc).append(diseaseDesc).append("可以用哪些西药和中药进行治疗?").toString();
            } else if (HAS_SYMPTOM_DRUG == relationType) {
                return buffer.append(symptomDesc).append(drugDesc).append("还可以吃什么药进行配合治疗?").toString();
            } else if (HAS_DISEASE_DRUG == relationType) {
                return buffer.append(diseaseDesc).append(drugDesc).append("还可以吃什么药进行配合治疗?").toString();
            } else if (HAS_SYMPTOM_DISEASE_DRUG == relationType) {
                return buffer.append(symptomDesc).append(diseaseDesc).append(drugDesc).append("还可以吃什么药进行配合治疗?").toString();
            }
        } else {
            String drugDesc = getDesc(drugList, DRUG_3);
            if (StringUtils.isEmpty(drugDesc)) {
                drugDesc = "可用使用哪些方剂进行治疗，";
            }
            return buffer.append(symptomDesc).append(diseaseDesc).append(drugDesc).append("并告诉我这些方剂的配方?").toString();
        }
        return "";

    }

    private int getRelationType(List<String> symptomList, List<String> diseaseList, List<String> drugList) {
        // 是否有症状
        boolean hasSymptom = !CollectionUtils.isEmpty(symptomList);
        // 是否有病种
        boolean hasDisease = !CollectionUtils.isEmpty(diseaseList);
        // 是否有药品
        boolean hasDrug = !CollectionUtils.isEmpty(drugList);

        if (hasSymptom) {
            if (hasDisease) {
                //症状 +  病种 + 药品
                if (hasDrug) {
                    return HAS_SYMPTOM_DISEASE_DRUG;
                } else {//症状 + 病种
                    return HAS_SYMPTOM_DISEASE;
                }
            } else {
                //症状 + 药品
                if (hasDrug) {
                    return HAS_SYMPTOM_DRUG;
                } else {//症状
                    return HAS_SYMPTOM;
                }
            }
        } else {
            if (hasDisease) {
                //病种 + 药品
                if (hasDrug) {
                    return HAS_DISEASE_DRUG;
                } else {//病种
                    return HAS_DISEASE;
                }
            } else {
                // 药品
                if (hasDrug) {
                    return HAS_DRUG;
                }
            }
        }
        return 0;
    }

    //拼接症状说明
    private String getDesc(List<String> names, int type) {
        if (CollectionUtils.isEmpty(names)) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        if (SYMPTOM == type) {
            buffer.append("出现");
        } else if (DISEASE == type) {
            buffer.append("得了");
        } else if (DRUG_1 == type) {
            buffer.append("了解");
        } else if (DRUG_2 == type) {
            buffer.append("吃了");
        } else if (DRUG_3 == type) {
            buffer.append("使用");
        }
        for (String name : names) {
            buffer.append(name).append("、");
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        if (SYMPTOM == type) {
            buffer.append("的症状");
        } else if (DISEASE == type) {
            buffer.append("的病");
        } else if (DRUG_1 == type) {
            buffer.append("等药品的全部详细信息");
        } else if (DRUG_2 == type) {
            buffer.append("的药");
        } else if (DRUG_3 == type) {
            buffer.append("可以组成哪些方剂");
        }
        buffer.append("，");
        return buffer.toString();
    }
}



















package com.sparta.twitNation.config;

import com.sparta.twitNation.util.api.ApiError;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class SwaggerConfig {
    protected OpenAPI createBaseOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }


    @Bean
    public OpenAPI openAPI() {
        return createBaseOpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    public Info apiInfo(){
        return new Info()
                .title("TwitNation")
                .description("기능 요청을 위한 API 명세")
                .version("1.0");
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            this.addCommonResponseBody(operation);
            return operation;
        };
    }

    private void addCommonResponseBody(Operation operation) {

        operation.getResponses().forEach((responseCode, apiResponse) -> {
            Content content = apiResponse.getContent();

            if(content != null){
                content.forEach((mediaTypeKey, mediaType) -> {
                    Schema originalSchema = mediaType.getSchema();
                    Schema<?> wrappedSchema = wrapSchema(originalSchema, responseCode);
                    mediaType.setSchema(wrappedSchema);
                });
            }
        });
    }

    protected Schema<?> wrapSchema(Schema originalSchema, String responseCode) {
        final Schema<?> wrapperSchema = new Schema<>();

        wrapperSchema.addProperty("success", new Schema<>().type("boolean").example("200".equals(responseCode)));
        wrapperSchema.addProperty("timeStamp", new Schema<>().type("string").format("date-time").example(
                LocalDateTime.now().toString()));

        if("200".equals(responseCode)) {
            wrapperSchema.addProperty("data", originalSchema);
            wrapperSchema.addProperty("apiError", new Schema<>().type(ApiError.class.toString()).example(null));
        } else if ("400".equals(responseCode)) {
            Schema dataSchema = new Schema<>();

            wrapperSchema.addProperty("data", dataSchema);

            Schema apiErrorSchema = new Schema<>();
            apiErrorSchema.addProperty("status", new Schema<>().type("integer").example(400));
            apiErrorSchema.addProperty("msg", new Schema<>().type("string").example("유효성 검사 실패")); //유효성 검사 실패
            wrapperSchema.addProperty("apiError", apiErrorSchema);
        }
        else if("401".equals(responseCode)){ //토큰 기간 만료에 대한 401 에러

            wrapperSchema.addProperty("data", null);

            Schema apiErrorSchema = new Schema<>();
            apiErrorSchema.addProperty("status", new Schema<>().type("integer").example(401));
            apiErrorSchema.addProperty("msg", new Schema<>().type("string").example("인증이 필요합니다"));
            wrapperSchema.addProperty("apiError", apiErrorSchema);
        }
        else if("403".equalsIgnoreCase(responseCode)){ //해당 사용자가 접근할 수 있는 자원이 아닌 경우
            wrapperSchema.addProperty("data", null);

            Schema apiErrorSchema = new Schema<>();
            apiErrorSchema.addProperty("status", new Schema<>().type("integer").example(403));
            apiErrorSchema.addProperty("msg", new Schema<>().type("string").example("권한이 없습니다" ));
            wrapperSchema.addProperty("apiError", apiErrorSchema);
        }
        else if("404".equals(responseCode)){
            wrapperSchema.addProperty("data", null);

            Schema apiErrorSchema = new Schema<>();
            apiErrorSchema.addProperty("status", new Schema<>().type("integer").example(404));
            apiErrorSchema.addProperty("msg", new Schema<>().type("string").example("잘못된 요청입니다"));
            wrapperSchema.addProperty("apiError", apiErrorSchema);
        }
        else if("500".equals(responseCode)) {
            wrapperSchema.addProperty("data", null);

            Schema apiErrorSchema = new Schema<>();
            apiErrorSchema.addProperty("status", new Schema<>().type("integer").example(500));
            apiErrorSchema.addProperty("msg", new Schema<>().type("string").example("서버 내부에 오류가 발생헀습니다. 잠시 후에 시도해주세요"));
            wrapperSchema.addProperty("apiError", apiErrorSchema);
        }
        else if("409".equals(responseCode)){
            wrapperSchema.addProperty("data", null);

            Schema apiErrorSchema = new Schema<>();
            apiErrorSchema.addProperty("status", new Schema<>().type("integer").example(409));
            apiErrorSchema.addProperty("msg", new Schema<>().type("string").example("이미 존재하는 값입니다"));
            wrapperSchema.addProperty("apiError", apiErrorSchema);
        }
        return wrapperSchema;
    }
}
package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    //  регистрация нового модуля Jackson. критично для обработки LocalDate и прочих классов
    public ObjectMapperContextResolver() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}


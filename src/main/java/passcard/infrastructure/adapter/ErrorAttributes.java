package passcard.infrastructure.adapter;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {

    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);

        // Add custom error attributes
        errorAttributes.put("timestamp", LocalDateTime.now());

        // Remove unwanted error attributes
        errorAttributes.remove("path");

        return errorAttributes;
    }
}

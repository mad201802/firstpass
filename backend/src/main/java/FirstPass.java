import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.models.FrontendRequest;

public class FirstPass {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FrontendRequest request = new FrontendRequest();
        request.type = "unlock";
        request.data = "new Object()";
        String json = mapper.writeValueAsString(request);
        System.out.println(json);
    }

}

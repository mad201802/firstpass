import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipc.messages.BaseRequest;
import ipc.messages.UnlockRequest;

public class Fistpass {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        long startTime = System.nanoTime();

        String example = "{\"type\": \"UNLOCK\",\"data\": {\"master_password\": \"12345\"}}";
        BaseRequest request = mapper.readValue(example, BaseRequest.class);

        if (request.type.equals("UNLOCK")) {
            BaseRequest<UnlockRequest> unlockRequest = mapper.readValue(example, new TypeReference<BaseRequest<UnlockRequest>>() {});
            System.out.println(unlockRequest.data.master_password);
        }

        long endTime = System.nanoTime();
        float duration = (endTime - startTime) / 1_000_000f;
        System.out.println(duration);

    }

}

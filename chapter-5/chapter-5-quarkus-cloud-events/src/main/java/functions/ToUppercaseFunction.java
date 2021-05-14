package functions;

import functions.domain.Input;
import functions.domain.Output;
import io.quarkus.funqy.Context;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;

public class ToUppercaseFunction {

    @Funq("uppercase")
    public Output function(Input input, @Context CloudEvent cloudEvent) {
        if (cloudEvent != null) {
            System.out.println(
                    "CloudEvent{" +
                            "id='" + cloudEvent.id() + '\'' +
                            ", specVersion='" + cloudEvent.specVersion() + '\'' +
                            ", source='" + cloudEvent.source() + '\'' +
                            ", subject='" + cloudEvent.subject() + '\'' +
                            '}');
        }
        return new Output(input.getMessage().toUpperCase());
    }

}

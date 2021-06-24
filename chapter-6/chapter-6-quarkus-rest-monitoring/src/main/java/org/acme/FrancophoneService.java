package org.acme;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.opentracing.Traced;

@ApplicationScoped
public class FrancophoneService {

    @Traced
    public String bonjour() {
        return "bonjour";
    }
}
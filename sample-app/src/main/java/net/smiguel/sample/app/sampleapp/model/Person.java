package net.smiguel.sample.app.sampleapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    @Builder.Default
    private String name = "sergio";

    @Builder.Default
    private String userName = "smiguelnet";

    @Builder.Default
    private String email = "sergio@smiguel.net";
}

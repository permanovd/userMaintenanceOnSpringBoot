package com.permanovd.user_maintainance.User.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @Value("${user_maintainance.app.version}")
    private String applicationVersion;

    @ModelAttribute("applicationVersion")
    public String getApplicationVersion() {
        // todo this param appears in get params on every submit.
        return applicationVersion;
    }
}

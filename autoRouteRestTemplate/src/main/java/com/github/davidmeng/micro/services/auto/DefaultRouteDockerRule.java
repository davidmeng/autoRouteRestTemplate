package com.github.davidmeng.micro.services.auto;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;

public class DefaultRouteDockerRule implements RouteDockerRule {

    public boolean isDockerHost(URI url) {

        String host = url.getHost();

        if (StringUtils.endsWith("localhost", host)) {
            return false;
        }

        /*
         * the docker host always with out "."
         */
        return !StringUtils.contains(host, ".");
    }
}

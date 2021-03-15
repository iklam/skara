/*
 * Copyright (c) 2019, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.skara.forge.gitlab;

import org.openjdk.skara.forge.*;
import org.openjdk.skara.host.Credential;
import org.openjdk.skara.json.JSONObject;
import org.openjdk.skara.json.JSONValue;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GitLabForgeFactory implements ForgeFactory {
    @Override
    public String name() {
        return "gitlab";
    }

    @Override
    public Set<String> knownHosts() {
        return Set.of("gitlab.com");
    }

    @Override
    public Forge create(URI uri, Credential credential, JSONObject configuration) {
        var name = "GitLab";
        if (configuration != null && configuration.contains("name")) {
            name = configuration.get("name").asString();
        }
        Set<String> groups = new HashSet<>();
        if (configuration != null && configuration.contains("groups")) {
            groups = configuration.get("groups")
                                  .stream()
                                  .map(JSONValue::asString)
                                  .collect(Collectors.toSet());
        }
        if (credential != null) {
            return new GitLabHost(name, uri, credential, groups);
        } else {
            return new GitLabHost(name, uri, groups);
        }
    }
}

/********************************************************************************
 * Copyright (c) 2020 TypeFox and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package org.eclipse.openvsx;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.eclipse.openvsx.entities.FileResource;
import org.junit.jupiter.api.Test;

public class ExtensionProcessorTest {

    @Test
    public void testTodoTree() throws Exception {
        try (
            var stream = getClass().getResourceAsStream("util/todo-tree.zip");
            var processor = new ExtensionProcessor(stream);
        ) {
            assertThat(processor.getNamespace()).isEqualTo("Gruntfuggly");
            assertThat(processor.getExtensionName()).isEqualTo("todo-tree");

            var metadata = processor.getMetadata();
            assertThat(metadata.getVersion()).isEqualTo("0.0.160");
            assertThat(metadata.getDisplayName()).isEqualTo("Todo Tree");
            assertThat(metadata.getDescription()).isEqualTo("Show TODO, FIXME, etc. comment tags in a tree view");
            assertThat(metadata.getEngines()).isEqualTo(Arrays.asList("vscode@^1.5.0"));
            assertThat(metadata.getCategories()).isEqualTo(Arrays.asList("Other"));
            assertThat(metadata.getTags()).isEqualTo(Arrays.asList("todo", "task", "tasklist", "multi-root ready"));
            assertThat(metadata.getLicense()).isEqualTo("MIT");
            assertThat(metadata.getRepository()).isEqualTo("https://github.com/Gruntfuggly/todo-tree");

            var resources = processor.getResources(metadata);
            var readmeFile = resources.stream()
                    .filter(res -> res.getType().equals(FileResource.README))
                    .findFirst();
            assertThat(readmeFile).isPresent();
            assertThat(readmeFile.get().getName()).isEqualTo("README.md");
            var iconFile = resources.stream()
                    .filter(res -> res.getType().equals(FileResource.ICON))
                    .findFirst();
            assertThat(iconFile).isPresent();
            assertThat(iconFile.get().getName()).isEqualTo("todo-tree.png");
            var licenseFile = resources.stream()
                    .filter(res -> res.getType().equals(FileResource.LICENSE))
                    .findFirst();
            assertThat(licenseFile).isPresent();
            assertThat(licenseFile.get().getName()).isEqualTo("LICENSE.txt");
        }
    }

}
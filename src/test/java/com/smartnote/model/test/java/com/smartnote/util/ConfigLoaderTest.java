// src/test/java/util/ConfigLoaderTest.java
package com.smartnote.model.test.java.com.smartnote.util;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import util.ConfigLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigLoaderTest {

    @Test
    void shouldMockStaticGet() {
        try (MockedStatic<ConfigLoader> mocked = Mockito.mockStatic(ConfigLoader.class)) {
            mocked.when(() -> ConfigLoader.get("app.name")).thenReturn("MockedApp");

            assertEquals("MockedApp", ConfigLoader.get("app.name"));
        }
    }
}
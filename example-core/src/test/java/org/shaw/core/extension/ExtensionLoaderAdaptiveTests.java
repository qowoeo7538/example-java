package org.shaw.core.extension;

import org.junit.Test;
import org.shaw.core.extension.adaptive.HasAdaptiveExt;
import org.shaw.core.extension.adaptive.impl.HasAdaptiveExtManualAdaptive;

import static org.junit.Assert.assertTrue;

/**
 * @create: 2018-03-01
 * @description:
 */
public class ExtensionLoaderAdaptiveTests {
    @Test
    public void testUseAdaptiveClass() throws Exception {
        ExtensionLoader<HasAdaptiveExt> loader = ExtensionLoader.getExtensionLoader(HasAdaptiveExt.class);
        HasAdaptiveExt ext = loader.getAdaptiveExtension();
        assertTrue(ext instanceof HasAdaptiveExtManualAdaptive);
    }
}

/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.syntax;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.xwiki.component.internal.StackingComponentEventManager;
import org.xwiki.observation.ObservationManager;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxRegistry;
import org.xwiki.rendering.syntax.SyntaxRegistryInitializer;
import org.xwiki.rendering.syntax.SyntaxType;
import org.xwiki.test.annotation.AllComponents;
import org.xwiki.test.junit5.mockito.ComponentTest;
import org.xwiki.test.junit5.mockito.InjectComponentManager;
import org.xwiki.test.mockito.MockitoComponentManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AllComponents
@ComponentTest
class SyntaxRegistryListenerTest
{
    @InjectComponentManager
    private MockitoComponentManager componentManager;

    private class MySyntaxRegistryInitializer implements SyntaxRegistryInitializer
    {
        @Override
        public List<Syntax> initialize()
        {
            return Collections.singletonList(new Syntax(new SyntaxType("myid", "My Id"), "1.0"));
        }
    }

    @Test
    void registerAndUnregister() throws Exception
    {
        // Prepare the ComponentManager to be configured to emit ComponentDescriptorAddedEvent events.
        StackingComponentEventManager eventManager = new StackingComponentEventManager();
        eventManager.shouldStack(false);
        ObservationManager observationManager = this.componentManager.getInstance(ObservationManager.class);
        eventManager.setObservationManager(observationManager);
        this.componentManager.setComponentEventManager(eventManager);

        // Register the new syntax
        this.componentManager.registerComponent(SyntaxRegistryInitializer.class, "my",
            new MySyntaxRegistryInitializer());

        SyntaxRegistry registry = this.componentManager.getInstance(SyntaxRegistry.class);
        assertEquals(new Syntax(new SyntaxType("myid", "My Id"), "1.0"), registry.getSyntax("myid/1.0").get());
        assertEquals(new Syntax(new SyntaxType("myid", "My Id"), "1.0"), registry.getSyntaxes().get("myid/1.0"));

        // Unregister the new syntax
        this.componentManager.unregisterComponent(SyntaxRegistryInitializer.class, "my");

        assertFalse(registry.getSyntax("myid/1.0").isPresent());
        assertTrue(registry.getSyntaxes().isEmpty());
    }
}

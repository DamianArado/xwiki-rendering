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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.descriptor.ComponentDescriptor;
import org.xwiki.component.event.ComponentDescriptorAddedEvent;
import org.xwiki.component.event.ComponentDescriptorRemovedEvent;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.observation.EventListener;
import org.xwiki.observation.event.Event;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxRegistry;
import org.xwiki.rendering.syntax.SyntaxRegistryInitializer;

/**
 * Listen to any component registration of type {@link SyntaxRegistryInitializer} and register its syntax in the
 * Syntax Registry.
 *
 * @version $Id$
 * @since 12.10RC1
 */
@Component
@Singleton
@Named("syntaxregistry")
public class SyntaxRegistryListener implements EventListener
{
    @Inject
    private SyntaxRegistry syntaxRegistry;

    /**
     * Used to remember which components have registered which syntaxes. Used when removing
     */
    private Map<ComponentDescriptor<?>, List<Syntax>> componentSyntaxes = new HashMap<>();

    @Override
    public String getName()
    {
        return "syntaxregistry";
    }

    @Override
    public List<Event> getEvents()
    {
        return Arrays.asList(new ComponentDescriptorAddedEvent(SyntaxRegistryInitializer.class),
            new ComponentDescriptorRemovedEvent(SyntaxRegistryInitializer.class));
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        ComponentManager componentManager = (ComponentManager) source;
        ComponentDescriptor<?> descriptor = (ComponentDescriptor<?>) data;

        if (event instanceof ComponentDescriptorAddedEvent) {
            SyntaxRegistryInitializer initializer = getSyntaxRegistryInitializer(componentManager, descriptor);
            List<Syntax> syntaxes = initializer.initialize();
            this.syntaxRegistry.registerSyntaxes(syntaxes);
            this.componentSyntaxes.put(descriptor, syntaxes);
        } else {
            // If the descriptor is not found then this means that the syntaxes were registered manually against the
            // Syntax Registry. Thus they'll also need to be unregistered manually.
            if (this.componentSyntaxes.containsKey(descriptor)) {
                this.syntaxRegistry.unregisterSyntaxes(this.componentSyntaxes.get(descriptor));
            }
        }
    }

    private SyntaxRegistryInitializer getSyntaxRegistryInitializer(ComponentManager componentManager,
        ComponentDescriptor<?> descriptor)
    {
        try {
            return componentManager.getInstance(SyntaxRegistryInitializer.class, descriptor.getRoleHint());
        } catch (ComponentLookupException e) {
            // This shouldn't happen since the component has just been registered in the CM!
            throw new RuntimeException(String.format("Failed to lookup Syntax Registry Initializer for role [%s]",
                descriptor.getRoleHint()), e);
        }
    }
}

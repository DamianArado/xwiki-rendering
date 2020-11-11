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
package org.xwiki.rendering.syntax;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.xwiki.component.annotation.Role;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.stability.Unstable;

/**
 * Register, unregister and list syntaxes available in the wiki. Syntaxes can be automatically registered by
 * implementing the {@link SyntaxRegistryInitializer} component role (and they are automatically unregistered when the
 * component is unregistered from the Component Manager). If a syntax is registered manually by calling a register
 * method from this class, then it also needs to be unregistered manually when the extension bringing it is uninstalled.
 *
 * @version $Id$
 * @since 12.10RC1
 */
@Unstable
@Role
public interface SyntaxRegistry
{
    void registerSyntaxes(List<Syntax> syntaxes);

    void unregisterSyntaxes(List<Syntax> syntaxes);

    void registerSyntax(Syntax syntax);

    void unregisterSyntax(Syntax syntax);

    Map<String, Syntax> getSyntaxes();

    Optional<Syntax> getSyntax(String syntaxId);

    Syntax resolveSyntax(String syntaxId) throws ParseException;
}

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
package org.xwiki.rendering.internal.xwiki21;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.internal.xwiki20.XWiki20SyntaxRegistryInitializer;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxRegistryInitializer;

/**
 * @since 12.10RC1
 */
@Component
@Named("xwiki/2.1")
@Singleton
public class XWiki21SyntaxRegistryInitializer implements SyntaxRegistryInitializer
{
    /**
     * XWiki 2.1 syntax.
     */
    public static final Syntax XWIKI_2_1 = new Syntax(XWiki20SyntaxRegistryInitializer.XWIKI, "2.1");

    @Override
    public List<Syntax> initialize()
    {
        return Arrays.asList(XWIKI_2_1);
    }
}

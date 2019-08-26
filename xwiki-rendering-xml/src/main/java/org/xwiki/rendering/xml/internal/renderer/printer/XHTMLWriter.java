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
package org.xwiki.rendering.xml.internal.renderer.printer;

import java.io.Writer;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * XHTMLWriter is an helper to configure XMLWriter to format a DOM4J tree as XHTML.
 *
 * @version $Id$
 * @since 5.2M1
 */
public class XHTMLWriter extends XMLWriter
{
    protected static final OutputFormat DEFAULT_XHTML_FORMAT;

    static {
        DEFAULT_XHTML_FORMAT = new OutputFormat();
        DEFAULT_XHTML_FORMAT.setXHTML(true);
    }

    public XHTMLWriter(Writer writer)
    {
        super(writer, DEFAULT_XHTML_FORMAT);

        // escape all non US-ASCII to have as less encoding problems as possible
        setMaximumAllowedCharacter(-1);
    }

    /**
     * Escapes a string to be used as an attribute value. Unlike the original method in {@link XMLWriter}, apostrophes
     * are replaced by a numerical entity &amp;#38;, since &amp;apos; is not valid in HTML documents.
     *
     * @param text the attribute value to escape
     * @return the text with all occurrences of special XML characters replaced by entity references.
     */
    @Override
    protected String escapeAttributeEntities(String text)
    {
        return super.escapeAttributeEntities(text).replace("&apos;", "&#38;");
    }

    /**
     * Add left curly to the set of characters that should be encoded. This to prepare the generated html for use
     * between {{html}} {{/html}} in XWiki 2.x syntax. (The literal string '{{/html}}' must not occur in the generated
     * html.)
     *
     * @param c Character to encode.
     * @return {@code true} if the character should be encoded.
     */
    @Override
    protected boolean shouldEncodeChar(int c)
    {
        return super.shouldEncodeChar(c) || c == '{';
    }

}

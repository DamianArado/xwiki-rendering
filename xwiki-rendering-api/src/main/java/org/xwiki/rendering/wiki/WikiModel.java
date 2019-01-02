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
package org.xwiki.rendering.wiki;

import java.util.Map;

import org.xwiki.component.annotation.Role;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.listener.reference.ResourceReference;

/**
 * Bridge between the Rendering module and a Wiki Model. Contains wiki APIs required by Rendering classes such as
 * Renderers. For example the XHTML Link Renderer needs to know if a wiki document exists in order to know how to
 * generate the HTML (in order to display a question mark for non existing documents) and it also needs to get the URL
 * pointing the wiki document.
 *
 * @version $Id$
 * @since 2.0M1
 */
@Role
public interface WikiModel
{
    /**
     * @param linkReference the reference to the link resource
     * @return the URL to the link resource (the resource could be a document, a URL, a path, etc)
     * @since 2.5RC1
     */
    String getLinkURL(ResourceReference linkReference);

    /**
     * @param imageReference the reference to the image resource
     * @param parameters the optional parameters passed to the image reference (width, height, etc)
     * @return the URL to the image resource (the resource could be an attacment in a document, an icon, etc)
     * @since 2.5RC1
     */
    String getImageURL(ResourceReference imageReference, Map<String, String> parameters);

    /**
     * @param resourceReference the reference pointing to a wiki document
     * @return true if the wiki document exists and can be viewed or false otherwise
     */
    boolean isDocumentAvailable(ResourceReference resourceReference);

    /**
     * @param resourceReference the reference pointing to a wiki document
     * @return the URL to view the specified wiki document
     */
    String getDocumentViewURL(ResourceReference resourceReference);

    /**
     * @param resourceReference the reference pointing to a wiki document
     * @return the URL to edit the specified wiki document
     */
    String getDocumentEditURL(ResourceReference resourceReference);

    /**
     * @param resourceReference the resource reference for which to return the XDOM (note that only some reference
     *                          type can be supported, such as DocumentResourceReference since some resources have
     *                          no XDOM content)
     * @return the XDOM
     * @throws WikiModelException if the XDOM content for the passed resource cannot be retrieved
     * @since 9.6RC1
     */
    default XDOM getXDOM(ResourceReference resourceReference) throws WikiModelException
    {
        throw new WikiModelException("Not implemented");
    }
}

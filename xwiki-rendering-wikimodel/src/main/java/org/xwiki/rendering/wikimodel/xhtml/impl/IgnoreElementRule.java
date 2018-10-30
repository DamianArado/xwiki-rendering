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
package org.xwiki.rendering.wikimodel.xhtml.impl;

import java.util.function.Predicate;

/**
 * Define a rule to know if the elements should be ignored in {@link TagStack}.
 * If the rule is active, then all the elements should be ignored. But if a given element match the given predicate
 * then the active status is switched.
 *
 * @version $Id$
 * @since 10.10RC1
 */
public class IgnoreElementRule
{
    private boolean isActive;

    private Predicate<TagContext> activateWhen;

    /**
     * Default constructor for an IgnoreElementRule.
     * @param tagContextPredicate the predicate used to switch the active flag.
     * @param isActive the default flag to set the rule as active or not.
     */
    public IgnoreElementRule(Predicate<TagContext> tagContextPredicate, boolean isActive)
    {
        this.activateWhen = tagContextPredicate;
        this.isActive = isActive;
    }

    /**
     * @return if true the elements will be ignored.
     */
    public boolean isActive()
    {
        return isActive;
    }

    /**
     * Switch the active value (see {@link #isActive()} if the predicate of the rule match the given tagContext.
     * @param tagContext The tag context which can match the predicate.
     */
    public void switchRule(TagContext tagContext)
    {
        if (this.activateWhen.test(tagContext)) {
            this.isActive = !this.isActive;
        }
    }
}

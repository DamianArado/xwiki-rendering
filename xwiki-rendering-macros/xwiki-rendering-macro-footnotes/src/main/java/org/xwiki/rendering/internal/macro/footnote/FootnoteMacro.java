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
package org.xwiki.rendering.internal.macro.footnote;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.MacroBlock;
import org.xwiki.rendering.block.match.BlockMatcher;
import org.xwiki.rendering.block.match.MacroBlockMatcher;
import org.xwiki.rendering.block.match.MacroMarkerBlockMatcher;
import org.xwiki.rendering.block.match.OrBlockMatcher;
import org.xwiki.rendering.macro.AbstractMacro;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.macro.descriptor.DefaultContentDescriptor;
import org.xwiki.rendering.macro.footnote.FootnoteMacroParameters;
import org.xwiki.rendering.transformation.MacroTransformationContext;

/**
 * Generate footnotes, listed at the end of the page. A reference to the generated footnote is inserted at the location
 * where the macro is called.
 *
 * @version $Id$
 * @since 2.0M2
 */
@Component
@Named(FootnoteMacro.MACRO_NAME)
@Singleton
public class FootnoteMacro extends AbstractMacro<FootnoteMacroParameters>
{
    /**
     * The name of this macro.
     */
    public static final String MACRO_NAME = "footnote";

    /**
     * The description of the macro.
     */
    private static final String DESCRIPTION = "Generates a footnote to display at the end of the page.";

    /**
     * The description of the macro content.
     */
    private static final String CONTENT_DESCRIPTION = "the text to place in the footnote";

    /**
     * Matches MacroBlocks or MacroMarkerBlocks having a macro id of {@link PutFootnotesMacro#MACRO_NAME}.
     */
    private static final BlockMatcher PUTFOOTNOTE_MATCHER = new OrBlockMatcher(
        new MacroBlockMatcher(PutFootnotesMacro.MACRO_NAME),
        new MacroMarkerBlockMatcher(PutFootnotesMacro.MACRO_NAME));

    /**
     * Create and initialize the descriptor of the macro.
     */
    public FootnoteMacro()
    {
        super("Footnote", DESCRIPTION, new DefaultContentDescriptor(CONTENT_DESCRIPTION),
            FootnoteMacroParameters.class);
        setDefaultCategory(DEFAULT_CATEGORY_CONTENT);
    }

    @Override
    public boolean supportsInlineMode()
    {
        return true;
    }

    @Override
    public int getPriority()
    {
        return 500;
    }

    @Override
    public List<Block> execute(FootnoteMacroParameters parameters, String content, MacroTransformationContext context)
        throws MacroExecutionException
    {
        Block root = context.getXDOM();

        // Only add a putfootnote macro at the end of the document if there's not already one (either already executed
        // or not).
        Block matchingBlock = root.getFirstBlock(PUTFOOTNOTE_MATCHER, Block.Axes.DESCENDANT);
        if (matchingBlock == null) {
            Block putFootnotesMacro = new MacroBlock(PutFootnotesMacro.MACRO_NAME, Collections.emptyMap(), false);
            root.addChild(putFootnotesMacro);
        }
        return Collections.emptyList();
    }
}

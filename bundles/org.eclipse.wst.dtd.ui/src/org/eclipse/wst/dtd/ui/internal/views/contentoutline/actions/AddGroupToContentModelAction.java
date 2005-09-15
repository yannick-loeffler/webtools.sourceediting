/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jens Lukowski/Innoopract - initial renaming/restructuring
 *     
 *******************************************************************************/


package org.eclipse.wst.dtd.ui.internal.views.contentoutline.actions;

import org.eclipse.wst.dtd.core.internal.CMGroupNode;
import org.eclipse.wst.dtd.core.internal.DTDNode;
import org.eclipse.wst.dtd.core.internal.Element;
import org.eclipse.wst.sse.ui.StructuredTextEditor;


public class AddGroupToContentModelAction extends BaseAction {

	// private Object element;

	public AddGroupToContentModelAction(StructuredTextEditor editor, String label) {
		super(editor, label);
	}

	public void run() {
		DTDNode node = getFirstNodeSelected();

		if (node instanceof CMGroupNode) {
			((CMGroupNode) node).addGroup();
		}
		else if (node instanceof Element) {
			((Element) node).addGroup();
		}
	}
}

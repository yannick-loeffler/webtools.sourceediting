/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xsd.ui.internal.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class ModelReconcileAdapter extends DocumentAdapter
{
  protected boolean handlingNotifyChanged = false;
  protected List listeners = new ArrayList();
  
  public ModelReconcileAdapter(Document document)
  {
    super(document);
  }
  
  public void addListener(INodeAdapter listener)
  {
    if (!listeners.contains(listener))
    {
      listeners.add(listener);
    }  
  }
  
  public void removeListener(INodeAdapter listener)
  {
    listeners.remove(listener);
  }  
  
  protected void notifyListeners(INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue, Object newValue, int pos)
  {
    List list = new ArrayList(listeners);
    for (Iterator i = list.iterator(); i.hasNext(); )
    {
      INodeAdapter adapter = (INodeAdapter)i.next();
      adapter.notifyChanged(notifier, eventType, changedFeature, oldValue, newValue, pos);
    }  
  }
      
  public void notifyChanged(INodeNotifier notifier, int eventType, Object feature, Object oldValue, Object newValue, int index)
  {
    if (!handlingNotifyChanged)
    {
      handlingNotifyChanged = true;
      try
      {
        handleNotifyChange(notifier, eventType, feature, oldValue, newValue, index);
        notifyListeners(notifier, eventType, feature, oldValue, newValue, index);
      }
      catch (Exception e)
      {
//        XSDEditorPlugin.getPlugin().getMsgLogger().write(e);
      }
      handlingNotifyChanged = false;
    }
  }
  
  protected void handleNodeChanged(Node node)
  {    
  }
  
  public void handleNotifyChange(INodeNotifier notifier, int eventType, Object feature, Object oldValue, Object newValue, int index)
  {
    switch (eventType)
    {
      case INodeNotifier.ADD:
      {
        if (newValue instanceof Element)
        {
          Element element = (Element)newValue;
          adapt(element);
        }  
        break;
      }
      case INodeNotifier.REMOVE:
      case INodeNotifier.CHANGE:
      case INodeNotifier.STRUCTURE_CHANGED:
      case INodeNotifier.CONTENT_CHANGED:
      {
        Node node = (Node)notifier;
        handleNodeChanged(node);
        break;
      }             
    }
  }
}
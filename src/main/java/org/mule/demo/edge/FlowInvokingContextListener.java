/*
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 * 
 */

package org.mule.demo.edge;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleException;
import org.mule.api.MuleRuntimeException;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.config.i18n.MessageFactory;
import org.mule.construct.Flow;
import org.mule.context.notification.MuleContextNotification;

public class FlowInvokingContextListener implements MuleContextNotificationListener<MuleContextNotification>
{
    private Flow startingFlow;
    private Flow stoppingFlow;

    public void onNotification(final MuleContextNotification notification)
    {
        if (notification.getAction() == MuleContextNotification.CONTEXT_STARTED)
        {
            sendNotificationToFlow(notification, startingFlow);
        }
        else if (notification.getAction() == MuleContextNotification.CONTEXT_STOPPING)
        {
            sendNotificationToFlow(notification, stoppingFlow);
        }
    }

    private void sendNotificationToFlow(final MuleContextNotification notification, final Flow flow)
    {
        try
        {
            final DefaultMuleEvent event = new DefaultMuleEvent(new DefaultMuleMessage(notification,
                notification.getMuleContext()), MessageExchangePattern.REQUEST_RESPONSE, startingFlow);
            flow.process(event);
        }
        catch (final MuleException me)
        {
            throw new MuleRuntimeException(MessageFactory.createStaticMessage("Failed to invoke: "
                                                                              + startingFlow), me);
        }
    }

    public void setStartingFlow(final Flow startingFlow)
    {
        this.startingFlow = startingFlow;
    }

    public void setStoppingFlow(final Flow stoppingFlow)
    {
        this.stoppingFlow = stoppingFlow;
    }
}

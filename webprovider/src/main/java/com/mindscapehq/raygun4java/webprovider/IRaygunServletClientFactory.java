package com.mindscapehq.raygun4java.webprovider;

import com.mindscapehq.raygun4java.core.IRaygunClientFactory;
import com.mindscapehq.raygun4java.core.IRaygunOnAfterSend;
import com.mindscapehq.raygun4java.core.IRaygunOnAfterSendFactory;
import com.mindscapehq.raygun4java.core.IRaygunOnBeforeSend;
import com.mindscapehq.raygun4java.core.IRaygunOnBeforeSendFactory;
import com.mindscapehq.raygun4java.core.RaygunOnAfterSendChain;
import com.mindscapehq.raygun4java.core.RaygunOnBeforeSendChain;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface IRaygunServletClientFactory extends IRaygunClientFactory {
    RaygunServletClient newClient(HttpServletRequest request);
    IRaygunServletClientFactory withVersionFrom(ServletContext context);

    IRaygunServletClientFactory withBeforeSend(IRaygunOnBeforeSendFactory onBeforeSend);
    IRaygunServletClientFactory withAfterSend(IRaygunOnAfterSendFactory onAfterSend);

    RaygunOnBeforeSendChain getRaygunOnBeforeSendChain();
    RaygunOnAfterSendChain getRaygunOnAfterSendChain();
}

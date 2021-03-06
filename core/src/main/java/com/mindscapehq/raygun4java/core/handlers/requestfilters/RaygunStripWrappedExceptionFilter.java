package com.mindscapehq.raygun4java.core.handlers.requestfilters;

import com.mindscapehq.raygun4java.core.IRaygunOnBeforeSend;
import com.mindscapehq.raygun4java.core.IRaygunSendEventFactory;
import com.mindscapehq.raygun4java.core.RaygunClient;
import com.mindscapehq.raygun4java.core.messages.RaygunErrorMessage;
import com.mindscapehq.raygun4java.core.messages.RaygunMessage;

/**
 * Given a set of class names, this filter will remove matching exceptions from the exception chain.
 * The intention is to remove generic "wrapping" exceptions like ServletException
 */
public class RaygunStripWrappedExceptionFilter implements IRaygunOnBeforeSend, IRaygunSendEventFactory<IRaygunOnBeforeSend> {

    private Class[] stripClasses;

    public RaygunStripWrappedExceptionFilter(Class<?>... stripClasses) {
        this.stripClasses = stripClasses;
    }

    public RaygunMessage onBeforeSend(RaygunClient client, RaygunMessage message) {

        if(message.getDetails() != null
                && message.getDetails().getError() != null
                && message.getDetails().getError().getInnerError() != null
                && message.getDetails().getError().getThrowable() != null) {

            for (Class<?> stripClass : stripClasses) {
                if (stripClass.isAssignableFrom(message.getDetails().getError().getThrowable().getClass())) {
                    RaygunErrorMessage innerError = message.getDetails().getError().getInnerError();
                    message.getDetails().setError(innerError);

                    // rerun check on the reassigned error
                    onBeforeSend(client, message);
                    return message;
                }
            }
        }

        return message;
    }

    public IRaygunOnBeforeSend create() {
        return this; // this is ok as this filter does not hold any state
    }
}

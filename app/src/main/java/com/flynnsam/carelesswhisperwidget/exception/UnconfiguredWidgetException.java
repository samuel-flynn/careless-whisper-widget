package com.flynnsam.carelesswhisperwidget.exception;

/**
 * An exception to be thrown when a widget has a configuration problem, such as:
 * <ul>
 *     <li>No widget ID assigned</li>
 *     <li>No looping option assigned</li>
 * </ul>
 * Created by sam on 2017-07-30.
 */

public class UnconfiguredWidgetException extends RuntimeException {

    /**
     * Create an exception with a message
     * @param message The message to use
     */
    public UnconfiguredWidgetException(String message) {
        super(message);
    }

    /**
     * Create an exception with a message and underlying cause
     * @param message The message to use
     * @param cause The underlying cause of this exception
     */
    public UnconfiguredWidgetException(String message, Throwable cause) {
        super(message, cause);
    }
}

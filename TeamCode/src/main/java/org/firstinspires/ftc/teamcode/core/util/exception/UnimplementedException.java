/**
 * @author Arsen Berezin
 */

package org.firstinspires.ftc.teamcode.core.util.exception;


/**
 * Thrown to indicate that the logic or requested operation is not yet implemented.
 *
 * @see UnsupportedOperationException
 */
public class UnimplementedException extends UnsupportedOperationException {
    public UnimplementedException(String message) {
        super(message);
    }

    public UnimplementedException() {
        this("Not yet implemented");
    }
}

package by.epam.xmlvoucher.exception;

public class XMLVoucherException extends Exception {

    public XMLVoucherException() {
        super();
    }

    public XMLVoucherException(String message) {
        super(message);
    }

    public XMLVoucherException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLVoucherException(Throwable cause) {
        super(cause);
    }

}
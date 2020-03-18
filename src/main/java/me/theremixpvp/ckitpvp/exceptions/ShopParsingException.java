package me.theremixpvp.ckitpvp.exceptions;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class ShopParsingException extends Exception {

    public ShopParsingException() {
    }

    public ShopParsingException(String message) {
        super(message);
    }

    public ShopParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}

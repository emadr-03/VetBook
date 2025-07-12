package it.unina.vetbook.utils;

public class LoginResult {

    private final boolean success;
    private final Object controller;
    private final String errorMessage;

    private LoginResult(boolean success, Object controller, String errorMessage) {
        this.success = success;
        this.controller = controller;
        this.errorMessage = errorMessage;
    }

    public static LoginResult success(Object controller) {
        return new LoginResult(true, controller, null);
    }

    public static LoginResult failure(String message) {
        return new LoginResult(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getController() {
        return controller;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}


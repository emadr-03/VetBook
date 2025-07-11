package it.unina.vetbook.dto;

public class LoginResultDTO {

    private final boolean success;
    private final Object controller;
    private final String errorMessage;

    private LoginResultDTO(boolean success, Object controller, String errorMessage) {
        this.success = success;
        this.controller = controller;
        this.errorMessage = errorMessage;
    }

    public static LoginResultDTO success(Object controller) {
        return new LoginResultDTO(true, controller, null);
    }

    public static LoginResultDTO failure(String message) {
        return new LoginResultDTO(false, null, message);
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


package it.unina.vetbook.dto;

import it.unina.vetbook.control.ProprietarioController;

public class RegistrationResult {
    private final ProprietarioController controller;
    private final String errorMessage;

    private RegistrationResult(ProprietarioController controller, String errorMessage) {
        this.controller = controller;
        this.errorMessage = errorMessage;
    }

    public static RegistrationResult success(ProprietarioController controller) {
        return new RegistrationResult(controller, null);
    }

    public static RegistrationResult failure(String errorMessage) {
        return new RegistrationResult(null, errorMessage);
    }

    public boolean isSuccess() {
        return controller != null;
    }

    public ProprietarioController getController() {
        return controller;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}


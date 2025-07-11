package it.unina.vetbook.dto;

import it.unina.vetbook.control.ProprietarioController;

public class RegistrationResultDTO {
    private final ProprietarioController controller;
    private final String errorMessage;

    private RegistrationResultDTO(ProprietarioController controller, String errorMessage) {
        this.controller = controller;
        this.errorMessage = errorMessage;
    }

    public static RegistrationResultDTO success(ProprietarioController controller) {
        return new RegistrationResultDTO(controller, null);
    }

    public static RegistrationResultDTO failure(String errorMessage) {
        return new RegistrationResultDTO(null, errorMessage);
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


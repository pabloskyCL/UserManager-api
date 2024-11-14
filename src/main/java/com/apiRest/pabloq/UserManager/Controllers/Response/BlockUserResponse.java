package com.apiRest.pabloq.UserManager.Controllers.Response;

public class BlockUserResponse {
    private boolean success;
    private String message;

    // Constructors
    public BlockUserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package model.response;

import controller.connection.RequestVisitor;
import controller.connection.ResponseVisitor;

public class SignInLoginResponse extends Response{
    private boolean isOk;
    private String massage;
    public SignInLoginResponse() {
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.visit(this);
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
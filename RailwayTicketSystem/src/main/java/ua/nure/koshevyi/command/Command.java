package ua.nure.koshevyi.command;

import ua.nure.koshevyi.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class Command implements Serializable {

    private static final long serialVersionUID = 2055249335086204712L;

    public abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException, SQLException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }
}